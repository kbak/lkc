package ca.uwaterloo.lkc;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.gnome.gtk.Button;
import org.gnome.gtk.CheckButton;
import org.gnome.gtk.RadioButton;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;
import ca.uwaterloo.lkc.IFeatureHandler.Stability;

public class FeatureHandlerSecurity extends FeatureHandler {

    public static final Map<FeatureScreenHandler.Features, CheckButton> buttonMap = new TreeMap<FeatureScreenHandler.Features, CheckButton>() {{ 
        put(Features.SELinux, new CheckButton("SELinux"));
        put(Features.CryptoAPI, new CheckButton("CryptoApi"));
    }};
    
    FeatureHandlerSecurity(final FeatureScreenHandler fsh)
    {
        for (int i = 0; i < buttonMap.size(); ++i)
        {
            fsh.layOption.put((CheckButton) buttonMap.values().toArray()[i], 0, i * 23);
        }
        
        selectedOptions.add(Features.None);
        selectedOptions.add(Features.None);
        
        buttonMap.get(Features.SELinux).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                fsh.updateFeatureDescription("SELinux Description");
                fsh.updateSize(200000);
                fsh.updateStability(Stability.Stable);

                selectedOptions.set(0, buttonMap.get(Features.SELinux).getActive() ? Features.None : Features.SELinux);
                
                try {
					fsh.rememberForUndoRedo();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
        
        buttonMap.get(Features.CryptoAPI).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                fsh.updateFeatureDescription("CryptoAPIDescription");
                fsh.updateSize(5000);
                fsh.updateStability(Stability.Stable);
                selectedOptions.set(1, buttonMap.get(Features.CryptoAPI).getActive() ? Features.None : Features.CryptoAPI);
                
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
        return "Select security features";
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
        return !v.contains(Features.Minimum);
    }
}
