package ca.uwaterloo.lkc;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.gnome.gtk.Button;
import org.gnome.gtk.CheckButton;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

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
        
        featureMap.put(Features.IPv6, new Feature(fsh, "ipv6 Description", 200000, Stability.Warning));
        featureMap.put(Features.Netfilter, new Feature(fsh, "no high mem Description", 5000, Stability.Stable));
        featureMap.put(Features.Qos, new Feature(fsh, "no high mem Description", 5000, Stability.Stable));
        
        buttonMap.get(Features.IPv6).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                featureMap.get(Features.IPv6).updateUI();
                selectedOptions.set(0, buttonMap.get(Features.IPv6).getActive() ? Features.None : Features.IPv6);
                
                try {
					fsh.rememberForUndoRedo();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
        
        buttonMap.get(Features.Netfilter).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                featureMap.get(Features.Netfilter).updateUI();
                selectedOptions.set(1, buttonMap.get(Features.Netfilter).getActive() ? Features.None : Features.Netfilter);
                
                try {
					fsh.rememberForUndoRedo();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
        
        buttonMap.get(Features.Qos).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                featureMap.get(Features.Qos).updateUI();
                selectedOptions.set(2, buttonMap.get(Features.Qos).getActive() ? Features.None : Features.Qos);
                
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
        return "Select server features";
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
        return v.contains(Features.Server);
    }

    @Override
    public String getInstruction() {
        return "Select options and click Next";
    }

}
