package ca.uwaterloo.lkc;

import java.util.Vector;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public class FeatureHandlerWelcome extends FeatureHandler {

    FeatureHandlerWelcome(final FeatureScreenHandler fsh)
    {
        
    }
    @Override
    public String getQuestion() {
        return "yo, back, next, finish";
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

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
