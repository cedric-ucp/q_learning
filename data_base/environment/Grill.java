package q_learning.data_base.environment;


public class Grill {
    private int column ;
    private int line ;
    private int state ;
    private int reward ;

    public Grill (int line , int column , int state){
        this.line = line ;
        this.column = column ;
        this.state = state ;
    }
    public int getColumn (){
        return this.column ;
    }
    public void setLine (int line){
        this.line = line ;
    }
    public int getLine (){
        return this.line ;
    }
    public void setColumn (int column){
        this.column = column ;
    }
    public String toString (){
        return "Grill : ("+this.line+" , "+this.column+") "+"State : "+getState ()+ " Reward : "+getReward () ;
    }

    public void setReward (int reward){
       this.reward = reward ;
    }
    public int getReward (){
        return this.reward ;
    }
    public void setState (int state){
        this.state = state ;
    }
    public int getState (){
        return this.state ;
    }
}
