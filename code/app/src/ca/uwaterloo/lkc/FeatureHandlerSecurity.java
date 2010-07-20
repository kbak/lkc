package ca.uwaterloo.lkc;


public class FeatureHandlerSecurity extends FeatureHandler {

    private FeatureScreenHandler fsh;
    
    FeatureHandlerSecurity(FeatureScreenHandler fsh)
    {
        this.fsh = fsh;
    }
    
    @Override
    public String getQuestion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        
    }

}
