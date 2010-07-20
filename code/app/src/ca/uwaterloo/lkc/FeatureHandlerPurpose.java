package ca.uwaterloo.lkc;

import java.util.Vector;

import org.gnome.gtk.Button;
import org.gnome.gtk.RadioButton;
import org.gnome.gtk.RadioButtonGroup;
import org.gnome.gtk.RadioGroup;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public class FeatureHandlerPurpose implements IFeatureHandler {

    private final FeatureScreenHandler fsh;
    private RadioGroup rg = new RadioGroup();
    private RadioButton rbDesktop = new RadioButton(rg, "Desktop");
    private RadioButton rbServer = new RadioButton(rg, "Server");
    private RadioButton rbMinimal = new RadioButton(rg, "Minimal Kernel");
    
    FeatureHandlerPurpose(final FeatureScreenHandler fsh)
    {
        this.fsh = fsh;
        
        fsh.layOption.add(rbDesktop);
        fsh.layOption.add(rbServer);
        fsh.layOption.add(rbMinimal);
        
        rbDesktop.connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                // TODO Auto-generated method stub
                fsh.updateFeatureDescription("aa");
                fsh.updateSize(20);
                fsh.updateStability(Stability.Stable);
            }
        });
    }
    
    @Override
    public String getQuestion() {
        return "How are you going to use this computer?";
    }

    @Override
    public Vector<Features> getSelectedOptions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void show() {
        rbDesktop.show();
        rbServer.show();
        rbMinimal.show();
    }

}
