package ca.uwaterloo.lkc;

import java.util.Vector;

import org.gnome.gtk.Label;
import org.gnome.gtk.Stock;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public class FeatureHandlerWelcome extends FeatureHandler {

    final Label instruction = new Label();
    
    FeatureHandlerWelcome(final FeatureScreenHandler fsh)
    {
        instruction.setLineWrap(true);
        instruction.setUseMarkup(true);
        instruction.setLabel("This wizard will guide you through the configuration process.\n\n" +
        		"At any time you can click Finish to use the <b>default</b> options.\n\n" +
        		"The wizard will show you <b>only</b> options that cannot be configured automatically, thus it will <b>skip</b> some categories from the left list.\n\n" +
        		"If you prefer to set all the options <b>manually</b>, navigate through the list of categories on the <b>left</b>. " +
        		"Previous and Next buttons (above) skip to previous and next steps from the left list.");
        fsh.layOption.put(instruction, 5, 5);
    }
    @Override
    public String getQuestion() {
        return "Welcome to the configurator!";
    }

    @Override
    public void show() {
        instruction.show();
    }
    @Override
    public boolean isRelevant(Vector<Features> v) {
        return true;
    }
	@Override
	public void updateUI() {
		// TODO Auto-generated method stub
		
	}
    @Override
    public String getInstruction() {
        return "Read the instructions <span foreground=\"red\"><b><u>carefully</u></b></span> and click Next";
    }
    @Override
    public int getSize() {
        return 0;
    }
    @Override
    public Stability getStability() {
        return Stability.Stable;
    }
    @Override
    public int getNum() {
        return 0;
    }
    @Override
    public Stock getImage() {
        return Stock.HOME;
    }
    @Override
    public String getName() {
        return "Welcome";
    }
    @Override
    public void setDefault() {
    }
}
