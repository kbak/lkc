package ca.uwaterloo.lkc;

import java.util.Vector;

import org.gnome.gtk.Stock;

import ca.uwaterloo.lkc.FeatureScreenHandler.Features;

public interface IFeatureHandler {
    
    public static enum Stability { Stable, Warning, Unstable };
    
    public String getQuestion();
    
    public String getInstruction();
    
    public int getSize();
    
    public Stability getStability();
    
    public int getNum();
    
    public void show();
    
    void load(Vector<Features> features);
    
    Vector<Features> save();
    
    boolean isRelevant(Vector<Features> v);
    
    public void updateUI();
    
    public Stock getImage();
    
    public String getName();
    
    public void setDefault();
}