package ca.uwaterloo.lkc;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.gnome.gdk.EventCrossing;
import org.gnome.gdk.EventFocus;
import org.gnome.gtk.Button;
import org.gnome.gtk.RadioButton;
import org.gnome.gtk.RadioButtonGroup;
import org.gnome.gtk.Widget;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public class FeatureHandlerMemory extends FeatureHandler {

    private static RadioButtonGroup rg = new RadioButtonGroup();
    private final FeatureScreenHandler fsh;
    
    public static final Map<FeatureScreenHandler.Features, RadioButton> buttonMap = new TreeMap<FeatureScreenHandler.Features, RadioButton>() {{ 
        put(Features.HighMem, new RadioButton(rg, "Yes"));
        put(Features.NoHighMem, new RadioButton(rg, "No"));
    }};
    
    FeatureHandlerMemory(final FeatureScreenHandler fsh)
    {
        this.fsh = fsh;
        
        for (int i = 0; i < buttonMap.size(); ++i)
        {
            fsh.layOption.put((RadioButton) buttonMap.values().toArray()[i], 0, i * 23);
        }
        
        selectedOptions.add(Features.NoHighMem);
        
        featureMap.put(Features.HighMem, new Feature(fsh, "4gb Description", 200000, Stability.Warning));
        featureMap.put(Features.NoHighMem, new Feature(fsh, "no high mem Description", 5000, Stability.Stable));
        
        buttonMap.get(Features.HighMem).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                featureMap.get(Features.HighMem).updateUI();
                selectedOptions.set(0, Features.HighMem);
                
                try {
					fsh.rememberForUndoRedo();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
        
        buttonMap.get(Features.NoHighMem).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                featureMap.get(Features.NoHighMem).updateUI();
                selectedOptions.set(0, Features.NoHighMem);
                
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
        return "Are you planning to use more than 4gb of ram?";
    }

    public void updateUI()
    {
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
        return !v.contains(Features.Minimum);
    }

    @Override
    public String getInstruction() {
        return "Select an option and click Next";
    }
}
