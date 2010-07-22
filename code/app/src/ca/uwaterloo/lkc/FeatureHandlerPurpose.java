package ca.uwaterloo.lkc;

import java.io.IOException;
import java.util.TreeMap;
import java.util.Map;
import java.util.Vector;

import org.gnome.gtk.Button;
import org.gnome.gtk.RadioButton;
import org.gnome.gtk.RadioButtonGroup;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;
import ca.uwaterloo.lkc.IFeatureHandler.Stability;

public class FeatureHandlerPurpose extends FeatureHandler {

    private static RadioButtonGroup rg = new RadioButtonGroup();
    
    public static final Map<FeatureScreenHandler.Features, RadioButton> buttonMap = new TreeMap<FeatureScreenHandler.Features, RadioButton>() {{ 
        put(Features.Desktop, new RadioButton(rg, "Desktop"));
        put(Features.Server, new RadioButton(rg, "Server"));
        put(Features.Minimum, new RadioButton(rg, "Minimal Configuration"));
    }};
    
    final static String description = "You can optimize kernel for different purposes.\n" +
    		"If you are a home user or use your computer as a workstation, choose Desktop.\n" +
    		"If you are building a network server, choose Server.\n" +
    		"If you want to build a minimal configuration that would run on your computer, choose Minimal Configuration.";
    
    FeatureHandlerPurpose(final FeatureScreenHandler fsh)
    {
        this.fsh = fsh;
        for (int i = 0; i < buttonMap.size(); ++i)
        {
            fsh.layOption.put((RadioButton) buttonMap.values().toArray()[i], 0, i * 23);
        }
        
        selectedOptions.add(Features.Desktop);
        
        featureMap.put(Features.Desktop, new Feature(fsh, description, 200000, Stability.Stable));
        featureMap.put(Features.Server, new Feature(fsh, description, 50000, Stability.Stable));
        featureMap.put(Features.Minimum, new Feature(fsh, description, 2000, Stability.Stable));
              
        buttonMap.get(Features.Desktop).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                featureMap.get(Features.Desktop).updateUI();
                selectedOptions.set(0, Features.Desktop);
                
                try {
					fsh.rememberForUndoRedo();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
        
        buttonMap.get(Features.Server).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                featureMap.get(Features.Server).updateUI();
                selectedOptions.set(0, Features.Server);
                
                try {
					fsh.rememberForUndoRedo();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
        
        buttonMap.get(Features.Minimum).connect(new Button.Clicked() {  
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                featureMap.get(Features.Minimum).updateUI();
                selectedOptions.set(0, Features.Minimum);
                
                try {
					fsh.rememberForUndoRedo();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
    }
    
    @Override
    public String getQuestion() {
        return "How are you going to use this computer?";
    }

    public void updateUI()
    {
        featureMap.get(selectedOptions.elementAt(0)).updateUI();
        buttonMap.get(selectedOptions.elementAt(0)).setActive(true);
        buttonMap.get(selectedOptions.elementAt(0)).grabFocus();
        fsh.updateFeatureDescription(description);
    }
    
    @Override
    public void show() {
        updateUI();
        for (RadioButton rb : buttonMap.values())
        {
            rb.show();
        }
    }

    @Override
    public boolean isRelevant(Vector<Features> v) {
        return true;
    }

    @Override
    public String getInstruction() {
        return "Select an option and click Next";
    }

    @Override
    public int getSize() {
        return featureMap.get(selectedOptions.elementAt(0)).size;
    }

    @Override
    public Stability getStability() {
        return featureMap.get(selectedOptions.elementAt(0)).stability;
    }

    @Override
    public int getNum() {
        return 666;
    }

}
