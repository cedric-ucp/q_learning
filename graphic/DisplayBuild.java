package q_learning.graphic ;

import q_learning.data_base.elements.Agent;
import q_learning.data_base.elements.Danger;
import q_learning.data_base.elements.Goal;
import q_learning.data_base.environment.*;
import q_learning.process.AgentAction;
import q_learning.process.BuildEnvironment;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;

public class DisplayBuild extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L ;

    private final PaintElement paintElement = new PaintElement () ;
    private final BuildEnvironment environment ;
    private final AgentAction agentAction;
    private final Environment map ;

    public DisplayBuild (BuildEnvironment environment){
        this.environment = environment ;
        this.agentAction = new AgentAction (environment) ;
        this.map = environment.getMap () ;

    }


    public void paintComponent (Graphics g) {
        super.paintComponent (g) ;
        Graphics2D g2 = (Graphics2D) g ;
        paintElement.paint(map, g2) ;

        Agent agent = this.agentAction.getAgent () ;
        paintElement.paint(agent , g2) ;

        ArrayList <Danger> dangers = environment.getDangers () ;
        for (Danger danger : dangers){
            paintElement.paint (danger , g2) ;
        }
        Goal goal = environment.getGoal () ;
        paintElement.paint(goal , g2) ;

    }
}
