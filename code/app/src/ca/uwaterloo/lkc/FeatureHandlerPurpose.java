package ca.uwaterloo.lkc;

import java.util.HashMap;
import java.util.Map;

import org.gnome.gtk.Button;
import org.gnome.gtk.RadioButton;
import org.gnome.gtk.RadioButtonGroup;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public class FeatureHandlerPurpose extends FeatureHandler {

    private final FeatureScreenHandler fsh;
    private static RadioButtonGroup rg = new RadioButtonGroup();
    
    public static final Map<FeatureScreenHandler.Features, RadioButton> buttonMap = new HashMap<FeatureScreenHandler.Features, RadioButton>() {{ 
        put(Features.Desktop, new RadioButton(rg, "Desktop"));
        put(Features.Server, new RadioButton(rg, "Server"));
        put(Features.Minimum, new RadioButton(rg, "Minimal Configuration"));
    }};
    
    FeatureHandlerPurpose(final FeatureScreenHandler fsh)
    {
        this.fsh = fsh;
    
        for (int i = 0; i < buttonMap.size(); ++i)
        {
            fsh.layOption.put((RadioButton) buttonMap.values().toArray()[i], 0, i * 20);
        }
        
        selectedOptions.add(Features.Desktop);
        
        buttonMap.get(Features.Desktop).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                fsh.updateFeatureDescription("aa");
                fsh.updateSize(20);
                fsh.updateStability(Stability.Stable);
            }
        });
    }
    
    @Override
    public String getQuestion() {
        return "How are you going to use this computer?";
    }

    public void updateUI()
    {
        buttonMap.get(selectedOptions.elementAt(0)).activate();
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
