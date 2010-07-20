package ca.uwaterloo.lkc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.gnome.glade.XML;
import org.gnome.gtk.Button;
import org.gnome.gtk.IconSize;
import org.gnome.gtk.Image;
import org.gnome.gtk.Label;
import org.gnome.gtk.Layout;
import org.gnome.gtk.Stock;
import org.gnome.gtk.TextBuffer;
import org.gnome.gtk.TextView;
import org.gnome.gtk.Widget;

import ca.uwaterloo.lkc.IFeatureHandler.Stability;

public class FeatureScreenHandler {

    public static enum Features { Desktop, Server, Minimum, SoftRT, Proc32, Proc64, HighMem, IPv6, Netfilter, Qos, SELinux, CryptoAPI, KVM, XEN};
    
    public static final Map<Stability, Stock> stabilityMap = new HashMap<IFeatureHandler.Stability, Stock>() {{ 
        put(IFeatureHandler.Stability.Stable, Stock.APPLY);
        put(IFeatureHandler.Stability.Warning, Stock.INFO);
        put(IFeatureHandler.Stability.Unstable, Stock.STOP);
    }};
    
    private Vector<IFeatureHandler> featureHandlers = new Vector<IFeatureHandler>();

    private Label lblOption;
    public Layout layOption;
    private TextView tvFeatureDescription;
    private Label lblFeatureSizeN;
    private Image imgFeatureStability;
    private TextBuffer textBuffer = new TextBuffer();
    
    private Vector<Integer> featureHistory = new Vector<Integer>();
    
    FeatureScreenHandler(final XML xmlWndConfig) throws FileNotFoundException
    {   
        lblOption = (Label) xmlWndConfig.getWidget("lblOption");
        layOption = (Layout) xmlWndConfig.getWidget("layOption");
        tvFeatureDescription = (TextView) xmlWndConfig.getWidget("tvFeatureDescription");
        lblFeatureSizeN = (Label) xmlWndConfig.getWidget("lblFeatureSizeN");
        imgFeatureStability = (Image) xmlWndConfig.getWidget("imgFeatureStability");
        
        featureHandlers.add(new FeatureHandlerPurpose(this));
        featureHandlers.add(new FeatureHandlerSoftRT(this));
        featureHandlers.add(new FeatureHandlerProcessor(this));
        featureHandlers.add(new FeatureHandlerMemory(this));
        featureHandlers.add(new FeatureHandlerServer(this));
        featureHandlers.add(new FeatureHandlerSecurity(this));
        featureHandlers.add(new FeatureHandlerVirtualization(this));
        
        final Button btnBackFeature = (Button) xmlWndConfig.getWidget("btnBackFeature");
        final Button btnNextFeature = (Button) xmlWndConfig.getWidget("btnNextFeature");
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
            str = Double.toString(size / 1024.0) + " kb";
        }
        else
        {
            str = Double.toString(size / 1024.0 / 1024.0) + " mb";
        }
        
        lblFeatureSizeN.setLabel(str);
    }
    
    public void updateFeatureDescription(String str)
    {
        textBuffer.setText(str);
    }
    
    public void showScreen()
    {
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
        featureHistory.add(featureHistory.lastElement() + 1);
    }
    
    public void load(URI file) throws IOException {
    	File outputFile = new File(file);
		
		if(outputFile.isFile() && outputFile.canRead()) {
			BufferedReader reader = new BufferedReader(new FileReader(outputFile));
			String line;
			while((line = reader.readLine()) != null ){
				StringTokenizer strTok = new StringTokenizer(line, "=,");
				String featureHandler;
				Vector<Features> features;
				
				if(strTok.hasMoreTokens()) {
					featureHandler = strTok.nextToken();
				}
				
				while(strTok.hasMoreTokens()){
					
				}
			}
		}
    }
    
    public void save(URI file) throws IOException {
    	File outputFile = new File(file);
		
		if(outputFile.isFile() && outputFile.canWrite()) {
			FileWriter writer = new FileWriter(outputFile);
			
			for(IFeatureHandler featureHandler : this.featureHandlers){
				writer.write(featureHandler.getClass().getCanonicalName() + "=");
				for(Features feature : featureHandler.save()) {
					writer.write(feature.ordinal() + ",");
				}
				writer.write("\n");
			}
		}
    }
}
