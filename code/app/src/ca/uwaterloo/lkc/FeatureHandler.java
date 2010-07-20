package ca.uwaterloo.lkc;

import java.util.Vector;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public abstract class FeatureHandler implements IFeatureHandler {

    public Vector<Features> selectedOptions = new Vector<Features>();


    @Override
    public void load(Vector<Features> features) {
        selectedOptions = features;
        
    }

    @Override
    public Vector<Features> save() {
        return selectedOptions;
    }
}
