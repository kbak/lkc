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
                
                try {
					fsh.save(new File("history").toURI());
					fsh.currentFeaturesIndex++;
				} catch (IOException e) {
					e.printStackTrace();
				}
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

    @Override
    public boolean isRelevant(Vector<Features> v) {
        return !v.contains(Features.Minimum);
    }

}
