package ca.uwaterloo.lkc;

import java.util.Vector;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public class FeatureHandlerSummary extends FeatureHandler {

    FeatureHandlerSummary(final FeatureScreenHandler fsh)
    {
        
    }
    
    @Override
    public String getQuestion() {
        return "Congratulations! You have successfully configured kernel";
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
        return "Click Save to save the configuration";
    }

}
