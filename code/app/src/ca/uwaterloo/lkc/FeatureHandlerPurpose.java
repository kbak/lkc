package ca.uwaterloo.lkc;

import java.io.IOException;
import java.util.TreeMap;
import java.util.Map;
import java.util.Vector;

import org.gnome.gtk.Button;
import org.gnome.gtk.RadioButton;
import org.gnome.gtk.RadioButtonGroup;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public class FeatureHandlerPurpose extends FeatureHandler {

    private static RadioButtonGroup rg = new RadioButtonGroup();
    
    public static final Map<FeatureScreenHandler.Features, RadioButton> buttonMap = new TreeMap<FeatureScreenHandler.Features, RadioButton>() {{ 
        put(Features.Desktop, new RadioButton(rg, "Desktop"));
        put(Features.Server, new RadioButton(rg, "Server"));
        put(Features.Minimum, new RadioButton(rg, "Minimal Configuration"));
    }};
    
    FeatureHandlerPurpose(final FeatureScreenHandler fsh)
    {
        for (int i = 0; i < buttonMap.size(); ++i)
        {
            fsh.layOption.put((RadioButton) buttonMap.values().toArray()[i], 0, i * 23);
        }
        
        selectedOptions.add(Features.Desktop);
        
        buttonMap.get(Features.Desktop).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                fsh.updateFeatureDescription("Desktop Description");
                fsh.updateSize(200000);
                fsh.updateStability(Stability.Warning);
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
                fsh.updateFeatureDescription("Server Description");
                fsh.updateSize(5000);
                fsh.updateStability(Stability.Stable);
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
                fsh.updateFeatureDescription("Minimal Description");
                fsh.updateSize(20);
                fsh.updateStability(Stability.Stable);
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
