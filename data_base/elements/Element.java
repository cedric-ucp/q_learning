package q_learning.data_base.elements;

import q_learning.data_base.environment.Grill;

public abstract class Element {
    private Grill position ;

    public Element (Grill position){
        this.position = position ;
    }
    public void setPosition (Grill position){
        this.position = position ;
    }
    public Grill getPosition (){
        return this.position ;
    }

}
