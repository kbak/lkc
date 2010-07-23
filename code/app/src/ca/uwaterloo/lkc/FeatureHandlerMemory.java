package ca.uwaterloo.lkc;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.gnome.gtk.Button;
import org.gnome.gtk.RadioButton;
import org.gnome.gtk.RadioButtonGroup;
import org.gnome.gtk.Stock;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public class FeatureHandlerMemory extends FeatureHandler {

    private static RadioButtonGroup rg = new RadioButtonGroup();
    
    public static final Map<FeatureScreenHandler.Features, RadioButton> buttonMap = new TreeMap<FeatureScreenHandler.Features, RadioButton>() {{ 
        put(Features.HighMem, new RadioButton(rg, "Yes"));
        put(Features.NoHighMem, new RadioButton(rg, "No"));
    }};
    
    final static String description = "Linux can use up to 64 Gigabytes of physical memory on x86 systems. " +
    "However, the address space of 32-bit x86 processors is only 4 " +
    "Gigabytes large. That means that, if you have a large amount of " +
    "physical memory, not all of it can be \"permanently mapped\" by the " +
    "kernel. The physical memory that's not permanently mapped is called " +
    "\"high memory\".\n\n" +
    "If the machine has between 1 and 4 Gigabytes physical RAM, then answer \"No\" here.\n\n" +
    "If more than 4 Gigabytes is used then answer \"Yes\" here. This selection turns Intel PAE (Physical Address Extension) mode on. " +
    "PAE implements 3-level paging on IA32 processors. PAE is fully supported by Linux, PAE mode is implemented on all recent Intel " +
    "processors (Pentium Pro and better). NOTE: If you say \"Yes\" here, then the kernel will not boot on CPUs that don't support PAE!\n\n" +
    "The actual amount of total physical memory will either be auto detected or can be forced by using a kernel command line option " +
    "such as \"mem=256M\". (Try \"man bootparam\" or see the documentation of your boot loader (lilo or loadlin) about how to pass options to the " +
    "kernel at boot time.)\n\n" +
    "If unsure, say \"No\".";
    
    FeatureHandlerMemory(final FeatureScreenHandler fsh)
    {
        this.fsh = fsh;
        
        for (int i = 0; i < buttonMap.size(); ++i)
        {
            fsh.layOption.put((RadioButton) buttonMap.values().toArray()[i], 0, i * 23);
        }
        
        selectedOptions.add(Features.NoHighMem);
        
        featureMap.put(Features.HighMem, new Feature(fsh, description, 1000, Stability.Stable));
        
        featureMap.put(Features.NoHighMem, new Feature(fsh, description, 500, Stability.Stable));
        
        buttonMap.get(Features.HighMem).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                featureMap.get(Features.HighMem).updateUI();
                selectedOptions.set(0, Features.HighMem);
            }
        });
        
        buttonMap.get(Features.NoHighMem).connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                featureMap.get(Features.NoHighMem).updateUI();
                selectedOptions.set(0, Features.NoHighMem);
            }
        });

    }
    
    @Override
    public String getQuestion() {
        return "Are you planning to use more than 4GB of RAM?";
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
        return Stock.SAVE;
    }

    @Override
    public String getName() {
        return "Memory";
    }

    @Override
    public void setDefault() {
        Vector<Features> v = new Vector<Features>();
        v.add(Features.NoHighMem);
        load(v);
    }
}
