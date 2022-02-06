package q_learning.process;

import q_learning.data_base.elements.Agent;
import q_learning.data_base.environment.Environment;
import q_learning.data_base.environment.Grill;

public class AgentAction {
    private Environment map ;
    private Agent agent ;

    public AgentAction(BuildEnvironment environment){
        this.map = environment.getMap () ;
        this.agent = environment.getAgent () ;
    }

    public void goUp (){
        Grill position = agent.getPosition () ;
        if (map.CanGoUp (position)){
            Grill updatePosition = map.getGrill(position.getLine () , position.getColumn () - 1) ;
            this.agent.setPosition (updatePosition) ;
        }
        else
            this.agent.setPosition (agent.getPosition()) ;

    }
    public void goDown (){
        Grill position = this.agent.getPosition () ;
        if (map.CanGoDown (position)){
            Grill updatePosition = map.getGrill(position.getLine () , position.getColumn () + 1) ;
            this.agent.setPosition (updatePosition) ;
        }
        else
            this.agent.setPosition (position) ;

    }
    public void goLeft (){
        Grill position = this.agent.getPosition () ;
        if (map.CanGoLeft (position)){
            Grill updatePosition = map.getGrill(position.getLine () - 1 , position.getColumn ()) ;
            this.agent.setPosition (updatePosition) ;
        }
        else
            this.agent.setPosition (position) ;
    }
    public void goRight (){
        Grill position = this.agent.getPosition () ;
        if (map.CanGoRight (position)){
            Grill updatePosition = map.getGrill(position.getLine () + 1 , position.getColumn ()) ;
            this.agent.setPosition (updatePosition) ;
        }
        else
            this.agent.setPosition (position) ;

    }
    public void resetAgent (){
         this.agent.setPosition(map.getGrill(0,0)) ;
    }
    public Agent getAgent (){
        return this.agent ;
    }
    public Grill makeStep (int action){
        switch (action){
            case 0 :
                this.goLeft () ;
                break ;
            case 1 :
                this.goRight (); ;
                break ;
            case 2 :
                this.goUp () ;
                break ;
            case 3 :
                this.goDown (); ;
                break ;
            default :
                break ;
        }
        return this.agent.getPosition () ;
    }
    public void makeFinalStep (int action){
        switch (action){
            case 0 :
                this.goLeft () ;
                break ;
            case 1 :
                this.goRight (); ;
                break ;
            case 2 :
                this.goUp () ;
                break ;
            case 3 :
                this.goDown () ; ;
                break ;
            default :
                break ;
        }
    }
    public void getToThePoint (Qlearning qlearning){
        int state = this.agent.getPosition ().getState () ;
        int action = qlearning.getActionMax (state) ;
        this.makeFinalStep (action) ;
    }


}
