package ca.uwaterloo.lkc;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public abstract class FeatureHandler implements IFeatureHandler {

    public Vector<Features> selectedOptions = new Vector<Features>();
    
    public Map<FeatureScreenHandler.Features, Feature> featureMap = new TreeMap<FeatureScreenHandler.Features, Feature>();
    
    @Override
    public void load(Vector<Features> features) {
        selectedOptions = features;
        updateUI();
    }

    @Override
    public Vector<Features> save() {
        return selectedOptions;
    }
}
