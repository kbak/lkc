package ca.uwaterloo.lkc;

import java.io.IOException;
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
        
        featureMap.put(Features.KVM, new Feature(fsh, "ipv6 Description", 200000, Stability.Warning));
        featureMap.put(Features.XEN, new Feature(fsh, "no high mem Description", 5000, Stability.Stable));

        buttonMap.get(Features.KVM).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                featureMap.get(Features.KVM).updateUI();
                selectedOptions.set(0, buttonMap.get(Features.KVM).getActive() ? Features.None : Features.KVM);

                try {
					fsh.rememberForUndoRedo();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
        
        buttonMap.get(Features.XEN).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                featureMap.get(Features.XEN).updateUI();
                selectedOptions.set(1, buttonMap.get(Features.XEN).getActive() ? Features.None : Features.XEN);
                
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
        return "Do you want to support virtualization?";
    }
    public void updateUI()
    {
        for (Features f : selectedOptions)
        {
            if (Features.None != f)
            {
                featureMap.get(f).updateUI();
                buttonMap.get(f).setActive(true);
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

    @Override
    public boolean isRelevant(Vector<Features> v) {
        return !v.contains(Features.Minimum);
    }

    @Override
    public String getInstruction() {
        return "Select options and click Next";
    }
}
