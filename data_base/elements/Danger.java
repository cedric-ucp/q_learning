package q_learning.data_base.elements;

import q_learning.data_base.environment.Grill;

public class Danger extends Element {
    public Danger(Grill position) {
        super(position);
    }
    public boolean isDanger (Grill grill){
        return grill.getState () == this.getPosition ().getState () ;
    }
}

