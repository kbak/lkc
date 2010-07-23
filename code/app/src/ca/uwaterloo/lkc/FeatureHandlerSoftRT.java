package ca.uwaterloo.lkc;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.gnome.gtk.Button;
import org.gnome.gtk.RadioButton;
import org.gnome.gtk.RadioButtonGroup;
import org.gnome.gtk.Stock;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public class FeatureHandlerSoftRT extends FeatureHandler {

    private static RadioButtonGroup rg = new RadioButtonGroup();
    
    public static final Map<FeatureScreenHandler.Features, RadioButton> buttonMap = new TreeMap<FeatureScreenHandler.Features, RadioButton>() {{ 
        put(Features.SoftRT, new RadioButton(rg, "Yes"));
        put(Features.NoSoftRT, new RadioButton(rg, "No"));
    }};
    
    final static String description = "This option reduces the latency of the kernel by making " +
    		"all kernel code (that is not executing in a critical section) " +
    		"preemptible. This allows reaction to interactive events by " +
    		"permitting a low priority process to be preempted involuntarily " +
    		"even if it is in kernel mode executing a system call and would " +
    		"otherwise not be about to reach a natural preemption point. " +
    		"This allows applications to run more 'smoothly' even when the " +
    		"system is under load, at the cost of slightly lower throughput " +
    		"and a slight runtime overhead to kernel code.\n\n" +
    		"Select this if you are building a kernel for a desktop or " +
    		"embedded system with latency requirements in the milliseconds " +
    		"range. It is recommended to select this option if you plan to do " +
    		"DSP (Digital Signal Processing) on this computer, e.g. by processing signal from the guitar or synthesizer.";
    
    FeatureHandlerSoftRT(final FeatureScreenHandler fsh)
    {
        this.fsh = fsh;
        
        for (int i = 0; i < buttonMap.size(); ++i)
        {
            fsh.layOption.put((RadioButton) buttonMap.values().toArray()[i], 0, i * 23);
        }
        
        selectedOptions.add(Features.NoSoftRT);
        
        featureMap.put(Features.SoftRT, new Feature(fsh, description, 200000, Stability.Warning));
        featureMap.put(Features.NoSoftRT, new Feature(fsh, description, 100000, Stability.Stable));
        
        buttonMap.get(Features.SoftRT).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                featureMap.get(Features.SoftRT).updateUI();
                selectedOptions.set(0, Features.SoftRT);
            }
        });
        
        buttonMap.get(Features.NoSoftRT).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                featureMap.get(Features.NoSoftRT).updateUI();
                selectedOptions.set(0, Features.NoSoftRT);
            }
        });

    }
    
    @Override
    public String getQuestion() {
        return "Do you want to enable soft Real-Time computations?";
    }

    public void updateUI()
    {
        buttonMap.get(selectedOptions.elementAt(0)).grabFocus();
        featureMap.get(selectedOptions.elementAt(0)).updateUI();
        buttonMap.get(selectedOptions.elementAt(0)).setActive(true);
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
        return v.contains(Features.Desktop);
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
        return Stock.PREFERENCES;
    }

    @Override
    public String getName() {
        return "Software Real Time";
    }

    @Override
    public void setDefault() {
        Vector<Features> v = new Vector<Features>();
        v.add(Features.NoSoftRT);
        load(v);
    }

}
