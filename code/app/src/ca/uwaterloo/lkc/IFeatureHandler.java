package ca.uwaterloo.lkc;

import java.util.Vector;

public interface IFeatureHandler {
    
    public static enum Stability { Stable, Warning, Unstable };
    
    public String getQuestion();
    
    public void show();
    
    public Vector<FeatureScreenHandler.Features> getSelectedOptions();
}