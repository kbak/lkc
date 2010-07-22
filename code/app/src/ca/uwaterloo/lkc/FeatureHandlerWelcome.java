package ca.uwaterloo.lkc;

import java.util.Vector;

import org.gnome.gtk.Label;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public class FeatureHandlerWelcome extends FeatureHandler {

    final Label instruction = new Label();
    
    FeatureHandlerWelcome(final FeatureScreenHandler fsh)
    {
        instruction.setLineWrap(true);
        instruction.setUseMarkup(true);
        instruction.setLabel("This wizard will guide you through the configuration process.\n\n" +
        		"At any time you can click Finish to use the <b>default</b> options.\n\n" +
        		"The wizard will show you <b>only</b> options that <b>cannot</b> be configured automatically.\n\n" +
        		"If you prefer to set all the options yourself, <b>navigate</b> through the list of categories on the <b>left</b>.");
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
        return "Read the instructions and click Next";
    }
}
