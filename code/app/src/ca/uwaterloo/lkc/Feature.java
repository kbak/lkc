package ca.uwaterloo.lkc;

public class Feature {

    private FeatureScreenHandler fsh;
    public String description;
    public int size;
    public IFeatureHandler.Stability stability;
    
    Feature (FeatureScreenHandler f, String d, int sz, IFeatureHandler.Stability s)
    {
        fsh = f;
        description = d;
        size = sz;
        stability = s;
    }
    
    void updateUI()
    {
        fsh.updateFeatureDescription(description);
        fsh.updateSize(size);
        fsh.updateStability(stability);
    }
}
