package q_learning.data_base.elements;

import q_learning.data_base.environment.Grill;

public class Goal extends Element{

    public Goal (Grill position){
        super (position) ;
    }
    public boolean isGoal (Grill grill){
        return grill.getState () == this.getPosition().getState () ;
    }
}
