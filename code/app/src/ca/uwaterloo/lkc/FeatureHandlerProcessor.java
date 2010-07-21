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

public class FeatureHandlerProcessor extends FeatureHandler {

    private static RadioButtonGroup rg = new RadioButtonGroup();
    
    public static final Map<FeatureScreenHandler.Features, RadioButton> buttonMap = new TreeMap<FeatureScreenHandler.Features, RadioButton>() {{ 
        put(Features.Proc32, new RadioButton(rg, "32-bit"));
        put(Features.Proc64, new RadioButton(rg, "64-bit"));
    }};
    
    FeatureHandlerProcessor(final FeatureScreenHandler fsh)
    {
        for (int i = 0; i < buttonMap.size(); ++i)
        {
            fsh.layOption.put((RadioButton) buttonMap.values().toArray()[i], 0, i * 23);
        }
        
        selectedOptions.add(Features.Proc32);
        
        buttonMap.get(Features.Proc32).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                fsh.updateFeatureDescription("32-bit Description");
                fsh.updateSize(2000);
                fsh.updateStability(Stability.Stable);
                selectedOptions.set(0, Features.Proc32);
                
                try {
					fsh.rememberForUndoRedo();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
        
        buttonMap.get(Features.Proc64).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                fsh.updateFeatureDescription("64-bitDescription");
                fsh.updateSize(500);
                fsh.updateStability(Stability.Stable);
                selectedOptions.set(0, Features.Proc64);
                
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
        return "Do you want to use 64-bit processor (not recommended for desktop";
    }

    public void updateUI()
    {
        buttonMap.get(selectedOptions.elementAt(0)).emitClicked();
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

}
