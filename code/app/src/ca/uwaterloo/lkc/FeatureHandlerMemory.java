package ca.uwaterloo.lkc;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.gnome.gtk.Button;
import org.gnome.gtk.RadioButton;
import org.gnome.gtk.RadioButtonGroup;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;
import ca.uwaterloo.lkc.IFeatureHandler.Stability;

public class FeatureHandlerMemory extends FeatureHandler {

    private static RadioButtonGroup rg = new RadioButtonGroup();
    
    public static final Map<FeatureScreenHandler.Features, RadioButton> buttonMap = new TreeMap<FeatureScreenHandler.Features, RadioButton>() {{ 
        put(Features.HighMem, new RadioButton(rg, "Yes"));
        put(Features.NoHighMem, new RadioButton(rg, "No"));
    }};
    
    FeatureHandlerMemory(final FeatureScreenHandler fsh)
    {
        for (int i = 0; i < buttonMap.size(); ++i)
        {
            fsh.layOption.put((RadioButton) buttonMap.values().toArray()[i], 0, i * 23);
        }
        
        selectedOptions.add(Features.NoHighMem);
        
        buttonMap.get(Features.HighMem).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                fsh.updateFeatureDescription("4gb Description");
                fsh.updateSize(200000);
                fsh.updateStability(Stability.Warning);
                selectedOptions.set(0, Features.HighMem);
            }
        });
        
        buttonMap.get(Features.NoHighMem).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                fsh.updateFeatureDescription("no high mem Description");
                fsh.updateSize(5000);
                fsh.updateStability(Stability.Stable);
                selectedOptions.set(0, Features.NoHighMem);
            }
        });

    }
    
    @Override
    public String getQuestion() {
        return "Are you planning to use more than 4gb of ram?";
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

}
