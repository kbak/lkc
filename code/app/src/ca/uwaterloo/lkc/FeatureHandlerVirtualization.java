package ca.uwaterloo.lkc;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.gnome.gtk.Button;
import org.gnome.gtk.CheckButton;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;
import ca.uwaterloo.lkc.IFeatureHandler.Stability;

public class FeatureHandlerVirtualization extends FeatureHandler {

    public static final Map<FeatureScreenHandler.Features, CheckButton> buttonMap = new TreeMap<FeatureScreenHandler.Features, CheckButton>() {{ 
        put(Features.KVM, new CheckButton("KVM"));
        put(Features.XEN, new CheckButton("XEN"));
    }};
     
    FeatureHandlerVirtualization(final FeatureScreenHandler fsh)
    {
        for (int i = 0; i < buttonMap.size(); ++i)
        {
            fsh.layOption.put((CheckButton) buttonMap.values().toArray()[i], 0, i * 23);
        }
        
        selectedOptions.add(Features.None);
        selectedOptions.add(Features.None);
        
        buttonMap.get(Features.KVM).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                fsh.updateFeatureDescription("KVM Description");
                fsh.updateSize(200000);
                fsh.updateStability(Stability.Stable);
                selectedOptions.set(0, Features.KVM);
            }
        });
        
        buttonMap.get(Features.XEN).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                fsh.updateFeatureDescription("XEN Description");
                fsh.updateSize(5000);
                fsh.updateStability(Stability.Stable);
                selectedOptions.set(1, Features.XEN);
            }
        });


    }
    
    @Override
    public String getQuestion() {
        return "Do you want to support virtualization?";
    }
    public void updateUI()
    {
        for (Features f : selectedOptions)
        {
            if (Features.None != f)
            {
                buttonMap.get(f).emitClicked();
            }
        }
    }
    
    @Override
    public void show() {
        updateUI();
        for (CheckButton rb : buttonMap.values())
        {
            rb.show();
        }
    }
}
