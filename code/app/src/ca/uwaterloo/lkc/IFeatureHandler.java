package ca.uwaterloo.lkc;

import java.util.Vector;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public interface IFeatureHandler {
    
    public static enum Stability { Stable, Warning, Unstable };
    
    public String getQuestion();
    
    public void show();
    
    void load(Vector<Features> features);
    
    Vector<Features> save();
}