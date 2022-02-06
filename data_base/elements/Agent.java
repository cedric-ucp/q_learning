package q_learning.data_base.elements;

import q_learning.data_base.environment.Grill;

public class Agent extends Element{
    public Agent (Grill position){
        super (position) ;
    }
    public boolean isAgent (Grill position){
        return position.getState () == this.getPosition ().getState () ;
    }
}
