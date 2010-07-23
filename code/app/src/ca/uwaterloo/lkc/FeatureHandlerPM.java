package ca.uwaterloo.lkc;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.gnome.gtk.Button;
import org.gnome.gtk.RadioButton;
import org.gnome.gtk.RadioButtonGroup;
import org.gnome.gtk.Stock;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public class FeatureHandlerPM extends FeatureHandler {

    private static RadioButtonGroup rg = new RadioButtonGroup();
    
    public static final Map<FeatureScreenHandler.Features, RadioButton> buttonMap = new TreeMap<FeatureScreenHandler.Features, RadioButton>() {{ 
        put(Features.PM, new RadioButton(rg, "Yes"));
        put(Features.NoPM, new RadioButton(rg, "No"));
    }};
    
    final static String description = "Enable or disable power management options (such as CPU frequency scaling, system hibernation, etc.)";
    
    FeatureHandlerPM(final FeatureScreenHandler fsh)
    {
        this.fsh = fsh;
        
        for (int i = 0; i < buttonMap.size(); ++i)
        {
            fsh.layOption.put((RadioButton) buttonMap.values().toArray()[i], 0, i * 23);
        }
        
        selectedOptions.add(Features.PM);
        
        featureMap.put(Features.PM, new Feature(fsh, description, 2000000, Stability.Stable));
        featureMap.put(Features.NoPM, new Feature(fsh, description, 0, Stability.Stable));
        
        buttonMap.get(Features.PM).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                featureMap.get(Features.PM).updateUI();
                selectedOptions.set(0, Features.PM);                
            }
        });
        
        buttonMap.get(Features.NoPM).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                featureMap.get(Features.NoPM).updateUI();
                selectedOptions.set(0, Features.NoPM);
            }
        });
        
    }
    
    @Override
    public String getQuestion() {
        return "Do you want to enable power management?";
    }

    public void updateUI()
    {
        featureMap.get(selectedOptions.elementAt(0)).updateUI();
        buttonMap.get(selectedOptions.elementAt(0)).setActive(true);
        buttonMap.get(selectedOptions.elementAt(0)).grabFocus();
        fsh.updateFeatureDescription(description);
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

    @Override
    public int getSize() {
        return featureMap.get(selectedOptions.elementAt(0)).size;
    }

    @Override
    public Stability getStability() {
        return featureMap.get(selectedOptions.elementAt(0)).stability;
    }

    @Override
    public int getNum() {
        return 1;
    }

    @Override
    public Stock getImage() {
        return Stock.DISCONNECT;
    }

    @Override
    public String getName() {
        return "Power Management";
    }

    @Override
    public void setDefault() {
        Vector<Features> v = new Vector<Features>();
        v.add(Features.PM);
        load(v);
    }

}
