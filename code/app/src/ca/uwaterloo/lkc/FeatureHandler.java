package ca.uwaterloo.lkc;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.gnome.gtk.Stock;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public abstract class FeatureHandler implements IFeatureHandler {

    public FeatureScreenHandler fsh;
    
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
    
    public static Stability minStability(Stability s1, Stability s2)
    {
        if (Stability.Unstable == s1 || Stability.Unstable == s2)
        {
            return Stability.Unstable;
        }
        else if (Stability.Warning == s1 || Stability.Warning == s2)
        {
            return Stability.Warning;
        }
        else
        {
            return Stability.Stable;
        }
    }
}
