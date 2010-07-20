package ca.uwaterloo.lkc;

import java.util.Vector;

import org.gnome.gtk.Layout;

public abstract class FeatureHandler {

    private Layout l;
    
    public static enum Stability { Stable, Warning, Unstable };
    
    String getQuestion() {
        return "";
    }
    
    String getDescription() {
        return "";
    }
    
    // size in bytes
    int getSize() {
        return 0;
    }
    
    Stability getStability()
    {
        return Stability.Unstable;
    }
    
    Vector<FeatureScreenHandler.Features> getSelectedOptions()
    {
        return new Vector<FeatureScreenHandler.Features>();
    }
}
