package ca.uwaterloo.lkc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.TreeMap;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.gnome.glade.XML;
import org.gnome.gtk.Button;
import org.gnome.gtk.HBox;
import org.gnome.gtk.IconSize;
import org.gnome.gtk.Image;
import org.gnome.gtk.Label;
import org.gnome.gtk.Layout;
import org.gnome.gtk.Stock;
import org.gnome.gtk.TextBuffer;
import org.gnome.gtk.TextView;
import org.gnome.gtk.ToolButton;
import org.gnome.gtk.Viewport;
import org.gnome.gtk.Widget;

import ca.uwaterloo.lkc.IFeatureHandler.Stability;

public class FeatureScreenHandler {

    public static enum Features { None, Desktop, Server, Minimum, NoSoftRT, SoftRT, Proc32, Proc64, NoHighMem, HighMem, IPv6, Netfilter, Qos, SELinux, CryptoAPI, KVM, XEN};
    
    public static final Map<Stability, Stock> stabilityMap = new TreeMap<IFeatureHandler.Stability, Stock>() {{ 
        put(IFeatureHandler.Stability.Stable, Stock.APPLY);
        put(IFeatureHandler.Stability.Warning, Stock.DIALOG_WARNING);
        put(IFeatureHandler.Stability.Unstable, Stock.STOP);
    }};
    
    private Vector<IFeatureHandler> featureHandlers = new Vector<IFeatureHandler>();

    private Label lblOption;
    public Layout layOption;
    private TextView tvFeatureDescription;
    private Label lblFeatureSizeN;
    private Image imgFeatureStability;
    private TextBuffer textBuffer = new TextBuffer();
    private Viewport vp;
    private HBox hbox1;
    final Button btnBackFeature;
    final Button btnNextFeature;
    
    // To manage the enable/disable of those buttons
    public final ToolButton tbtnUndo;
	public final ToolButton tbtnRedo;
    
    private Vector<Integer> featureHistory = new Vector<Integer>();
    
    public Vector<URI> featuresUndoRedo;
	public int currentFeaturesIndex;
	public int MAX_UNDO_REDO = 100;
    
    FeatureScreenHandler(final XML xmlWndConfig) throws FileNotFoundException
    {   
    	featuresUndoRedo = new Vector<URI>();
		currentFeaturesIndex = -1;
		
        lblOption = (Label) xmlWndConfig.getWidget("lblOption");
        layOption = (Layout) xmlWndConfig.getWidget("layOption");
        vp = (Viewport) xmlWndConfig.getWidget("viewport3");
        hbox1 = (HBox) xmlWndConfig.getWidget("hbox1");
        tvFeatureDescription = (TextView) xmlWndConfig.getWidget("tvFeatureDescription");
        lblFeatureSizeN = (Label) xmlWndConfig.getWidget("lblFeatureSizeN");
        imgFeatureStability = (Image) xmlWndConfig.getWidget("imgFeatureStability");
        
        featureHandlers.add(new FeatureHandlerWelcome(this));
        featureHandlers.add(new FeatureHandlerPurpose(this));
        featureHandlers.add(new FeatureHandlerSoftRT(this));
        featureHandlers.add(new FeatureHandlerProcessor(this));
        featureHandlers.add(new FeatureHandlerMemory(this));
        featureHandlers.add(new FeatureHandlerServer(this));
        featureHandlers.add(new FeatureHandlerSecurity(this));
        featureHandlers.add(new FeatureHandlerVirtualization(this));
        featureHandlers.add(new FeatureHandlerSummary(this));
        
        btnBackFeature = (Button) xmlWndConfig.getWidget("btnBackFeature");
        btnNextFeature = (Button) xmlWndConfig.getWidget("btnNextFeature");
        final Button btnFinishFeature = (Button) xmlWndConfig.getWidget("btnFinishFeature");
        
        featureHistory.add(0);
        
        btnBackFeature.connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                featureHistory.remove(featureHistory.size() - 1);
                showScreen();
            }
        });
        
        btnNextFeature.connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                run();
            }
        });
        
        btnFinishFeature.connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                featureHistory.add(featureHandlers.size() - 1);
                showScreen();
            }
        });
        
        tvFeatureDescription.setBuffer(textBuffer);
        
		tbtnUndo = (ToolButton) xmlWndConfig.getWidget("tbtnUndo");
		tbtnRedo = (ToolButton) xmlWndConfig.getWidget("tbtnRedo");
    }
    
    public void updateStability(IFeatureHandler.Stability s)
    {
        imgFeatureStability.setImage(stabilityMap.get(s), IconSize.SMALL_TOOLBAR);
    }
    
    public void updateSize(int size)
    {
        String str;
        
        if (0 <= size && size < 1024)
        {
            str = Integer.toString(size) + " b";
        }
        else if (1024 <= size & size < 1024 * 1024)
        {
            str = Double.toString(normalize(size / 1024.0)) + " kb";
        }
        else
        {
            str = Double.toString(normalize(size / 1024.0 / 1024.0)) + " mb";
        }
        
        lblFeatureSizeN.setLabel(str);
    }
    
    private double normalize(double d)
    {
        return ((double) Math.round(d * 100.0)) / 100.0;
    }
    
    public void updateFeatureDescription(String str)
    {
        textBuffer.setText(str);
    }
    
    public void showScreen()
    {
        if (0 == featureHistory.lastElement())
        {
            btnBackFeature.setSensitive(false);
            vp.hide();
            hbox1.hide();
            btnNextFeature.setSensitive(true);
        }
        else if (featureHandlers.size() - 1 == featureHistory.lastElement())
        {
            btnBackFeature.setSensitive(true);
            vp.hide();
            hbox1.hide();
            btnNextFeature.setSensitive(false);
        }
        else
        {
            btnBackFeature.setSensitive(true);
            btnNextFeature.setSensitive(true);
            vp.show();
            hbox1.show();
        }
        
        
        for (Widget c : layOption.getChildren())
        {
            c.hide();
        }
        IFeatureHandler fh = featureHandlers.elementAt(featureHistory.lastElement());
        lblOption.setLabel(fh.getQuestion());
        fh.show();
    }
    
    public void run()
    {
        pickNext();
        showScreen();
    }
    
    void pickNext()
    {
        int i = 1;
        
        Vector<Features> v = new Vector<Features>();
        
        for (IFeatureHandler fh : featureHandlers)
        {
            v.addAll(fh.save());
        }
        
        for (; !featureHandlers.elementAt(featureHistory.lastElement() + i).isRelevant(v); ++i);
        
        featureHistory.add(featureHistory.lastElement() + i);
    }
    
    public void load(URI file) throws IOException, ClassNotFoundException {
    	File outputFile = new File(file);
		
		if(outputFile.isFile() && outputFile.canRead()) {
			BufferedReader reader = new BufferedReader(new FileReader(outputFile));
			String line;
			while((line = reader.readLine()) != null ){
				StringTokenizer strTok = new StringTokenizer(line, "=,");
				String featureHandler = "";
				Vector<Features> features = new Vector<Features>();
				
				// Get the feature handler name to load the right class
				if(strTok.hasMoreTokens()) {
					featureHandler = strTok.nextToken();
				}
				
				// Get the features
				while(strTok.hasMoreTokens()){
					features.add(Features.values()[Integer.parseInt(strTok.nextToken())]);
				}
				
				// Load the features to the specific feature handler
				for(IFeatureHandler fh : featureHandlers){
					if(Class.forName(featureHandler).isInstance(fh)){
						fh.load(features);
					}
				}
			}
			reader.close();
		}
    }
    
    public void updateCurrentFeatures() throws IOException, ClassNotFoundException {
    	this.load(this.featuresUndoRedo.get(this.currentFeaturesIndex));
    }
    
    public void save(URI file) throws IOException {
    	File outputFile = new File(file);
    	outputFile.createNewFile();

		if(outputFile.isFile() && outputFile.canWrite()) {
			FileWriter writer = new FileWriter(outputFile);
			
			for(IFeatureHandler featureHandler : this.featureHandlers){
				writer.write(featureHandler.getClass().getCanonicalName() + "=");
				for(Features feature : featureHandler.save()) {
					writer.write(feature.ordinal() + ",");
				}
				writer.write("\n");
			}
			writer.close();
		}
    }
    
    public void rememberForUndoRedo() throws IOException {
    	// Add to the vector that holds the history for undo/redo
    	URI fileName = new File("history" + File.pathSeparator + System.currentTimeMillis()).toURI(); 
    	this.save(fileName);
    	this.featuresUndoRedo.add(fileName);
    	
    	// If we are over the max size of history, delete the first element in the vector
    	if(this.featuresUndoRedo.size() > this.MAX_UNDO_REDO) {
    		this.featuresUndoRedo.removeElementAt(0);
    	}
    	
    	this.incrementCurrentFeaturesIndex();
    	
    	tbtnUndo.setSensitive(this.currentFeaturesIndex > 0);
    }
    
    public boolean incrementCurrentFeaturesIndex() {
    	System.out.println("BEFORE IncrementIndex: " + this.currentFeaturesIndex);
    	this.currentFeaturesIndex++;
    	if (this.currentFeaturesIndex >= this.MAX_UNDO_REDO) {
            this.currentFeaturesIndex = this.MAX_UNDO_REDO - 1;
            System.out.println("AFTER IncrementIndex: " + this.currentFeaturesIndex);
            return true;
        }
    	
    	System.out.println("AFTER IncrementIndex: " + this.currentFeaturesIndex);
    	return false;
    }
    
    public boolean decrementCurrentFeaturesIndex() {
    	System.out.println("BEFORE DecrementIndex: " + this.currentFeaturesIndex);
    	this.currentFeaturesIndex--;
		if (this.currentFeaturesIndex <= 0) {
            this.currentFeaturesIndex = 0;
            System.out.println("AFTER DecrementIndex: " + this.currentFeaturesIndex);
            return true;
        }
		
        System.out.println("AFTER DecrementIndex: " + this.currentFeaturesIndex);
		return false;
    }
}
