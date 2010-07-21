package ca.uwaterloo.lkc;

import java.util.Vector;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public class FeatureHandlerSummary extends FeatureHandler {

    FeatureHandlerSummary(final FeatureScreenHandler fsh)
    {
        
    }
    
    @Override
    public String getQuestion() {
        return "Congratulations blablabla";
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isRelevant(Vector<Features> v) {
        return true;
    }

}
