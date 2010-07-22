package ca.uwaterloo.lkc;

import java.util.Vector;

import org.gnome.gtk.Label;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public class FeatureHandlerWelcome extends FeatureHandler {

    final Label instruction = new Label();
    
    FeatureHandlerWelcome(final FeatureScreenHandler fsh)
    {
        instruction.setLineWrap(true);
        instruction.setLabel("bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla");
        fsh.layOption.put(instruction, 5, 5);
    }
    @Override
    public String getQuestion() {
        return "yo, back, next, finish";
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
