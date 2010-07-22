package ca.uwaterloo.lkc;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.gnome.gtk.Button;
import org.gnome.gtk.RadioButton;
import org.gnome.gtk.RadioButtonGroup;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;
import ca.uwaterloo.lkc.IFeatureHandler.Stability;

public class FeatureHandlerSoftRT extends FeatureHandler {

    private static RadioButtonGroup rg = new RadioButtonGroup();
    
    public static final Map<FeatureScreenHandler.Features, RadioButton> buttonMap = new TreeMap<FeatureScreenHandler.Features, RadioButton>() {{ 
        put(Features.SoftRT, new RadioButton(rg, "Soft Real-Time"));
        put(Features.NoSoftRT, new RadioButton(rg, "No Soft Real-Time"));
    }};
    
    FeatureHandlerSoftRT(final FeatureScreenHandler fsh)
    {
        for (int i = 0; i < buttonMap.size(); ++i)
        {
            fsh.layOption.put((RadioButton) buttonMap.values().toArray()[i], 0, i * 23);
        }
        
        selectedOptions.add(Features.NoSoftRT);
        
        featureMap.put(Features.SoftRT, new Feature(fsh, "4gb Description", 200000, Stability.Warning));
        featureMap.put(Features.NoSoftRT, new Feature(fsh, "no high mem Description", 5000, Stability.Stable));
        
        buttonMap.get(Features.SoftRT).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                featureMap.get(Features.SoftRT).updateUI();
                selectedOptions.set(0, Features.SoftRT);
                
                try {
					fsh.rememberForUndoRedo();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
        
        buttonMap.get(Features.NoSoftRT).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                featureMap.get(Features.NoSoftRT).updateUI();
                selectedOptions.set(0, Features.NoSoftRT);
                
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
        return "Do you want to support soft RT?";
    }

    public void updateUI()
    {
        buttonMap.get(selectedOptions.elementAt(0)).grabFocus();
        featureMap.get(selectedOptions.elementAt(0)).updateUI();
        buttonMap.get(selectedOptions.elementAt(0)).setActive(true);
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
        return v.contains(Features.Desktop);
    }

    @Override
    public String getInstruction() {
        return "Select an option and click Next";
    }

}
