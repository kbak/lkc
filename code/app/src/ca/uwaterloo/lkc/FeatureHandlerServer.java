package ca.uwaterloo.lkc;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.gnome.gtk.Button;
import org.gnome.gtk.CheckButton;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;
import ca.uwaterloo.lkc.IFeatureHandler.Stability;

public class FeatureHandlerServer extends FeatureHandler {

    public static final Map<FeatureScreenHandler.Features, CheckButton> buttonMap = new TreeMap<FeatureScreenHandler.Features, CheckButton>() {{ 
        put(Features.IPv6, new CheckButton("IPv6"));
        put(Features.Netfilter, new CheckButton("Netfilter"));
        put(Features.Qos, new CheckButton("Qos"));
    }};
        
    FeatureHandlerServer(final FeatureScreenHandler fsh)
    {
        for (int i = 0; i < buttonMap.size(); ++i)
        {
            fsh.layOption.put((CheckButton) buttonMap.values().toArray()[i], 0, i * 23);
        }
        
        selectedOptions.add(Features.None);
        selectedOptions.add(Features.None);
        selectedOptions.add(Features.None);
        
        buttonMap.get(Features.IPv6).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                fsh.updateFeatureDescription("IPv6 Description");
                fsh.updateSize(200000);
                fsh.updateStability(Stability.Stable);
                selectedOptions.set(0, Features.IPv6);
            }
        });
        
        buttonMap.get(Features.Netfilter).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                fsh.updateFeatureDescription("Netfilter Description");
                fsh.updateSize(5000);
                fsh.updateStability(Stability.Stable);
                selectedOptions.set(1, Features.Netfilter);
            }
        });
        
        buttonMap.get(Features.Qos).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                fsh.updateFeatureDescription("Qos Description");
                fsh.updateSize(5000);
                fsh.updateStability(Stability.Stable);
                selectedOptions.set(2, Features.Qos);
            }
        });

    }
    
    @Override
    public String getQuestion() {
        return "Select server features";
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

    @Override
    public boolean isRelevant(Vector<Features> v) {
        return v.contains(Features.Server);
    }

}
