package q_learning.graphic ;

import q_learning.data_base.elements.Agent;
import q_learning.data_base.elements.Danger;
import q_learning.data_base.elements.Goal;
import q_learning.data_base.environment.Environment;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;

import q_learning.data_base.environment.Grill;
import q_learning.process.BuildEnvironment;
import q_learning.process.AgentAction;

public class DisplayStart extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L ;

    private final PaintPath paintPath = new PaintPath () ;
    private final BuildEnvironment environment ;
    private final AgentAction agentAction;
    private final Environment map ;
    private final ArrayList <Grill> path ;

    public DisplayStart(BuildEnvironment environment){
        this.environment = environment ;
        this.agentAction = new AgentAction(environment) ;
        this.map = environment.getMap () ;
        this.path = new ArrayList <> () ;

    }


    public void paintComponent (Graphics g) {
        super.paintComponent (g) ;
        Agent agent = this.agentAction.getAgent () ;


        Graphics2D g2 = (Graphics2D) g ;
        paintPath.paint (map , g2) ;

        paintPath.paint(agent , g2 , this.path) ;

        ArrayList <Danger> dangers = environment.getDangers () ;
        for (Danger danger : dangers){
            paintPath.paint (danger , g2) ;
        }
        Goal goal = environment.getGoal () ;
        paintPath.paint(goal , g2) ;

    }
    public ArrayList <Grill> getPath (){
        return this.path ;
    }
    public void add (Grill grill){
        this.path.add (grill) ;
    }
}
