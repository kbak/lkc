package ca.uwaterloo.lkc;

import java.util.Vector;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public class FeatureHandlerServer implements IFeatureHandler {

    private FeatureScreenHandler fsh;
    
    FeatureHandlerServer(FeatureScreenHandler fsh)
    {
        this.fsh = fsh;
    }
    
    @Override
    public String getQuestion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector<Features> getSelectedOptions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        
    }

}
