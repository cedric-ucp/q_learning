package q_learning.graphic ;

import q_learning.configuration.GameConfig;
import q_learning.data_base.elements.Agent;
import q_learning.data_base.elements.Danger;
import q_learning.data_base.elements.Goal;
import q_learning.data_base.environment.Environment;
import q_learning.data_base.environment.Grill;
import q_learning.process.BuildGame;

import java.awt.*;
import java.util.ArrayList;

public class PaintPath {
    public void paint(Environment environment , Graphics graphics) {
        int grillSize = GameConfig.GRILL_SIZE;
        Grill[] [] map = environment.getEnvironment () ;

        for (int line = 0; line < environment.getLineNumber () ; line++) {
            for (int column = 0; column < environment.getColumnNumber (); column++) {
                Grill grill = map[line][column];
                if ((line + column) % 2 == 0) {
                    graphics.setColor(Color.GREEN) ;
                }
                else{
                    graphics.setColor (Color.WHITE) ;
                }
                graphics.fillRect(grill.getColumn() * grillSize, grill.getLine() * grillSize, grillSize, grillSize);

            }
        }
    }
    public void paint(Agent agent , Graphics2D graphics , ArrayList<Grill> path) {
        Grill position = agent.getPosition() ;
        int grillSize = GameConfig.GRILL_SIZE ;
        int y = position.getLine () ;
        int x = position.getColumn () ;

        if (!path.isEmpty()){
            for (Grill grill : path) {
                int y0 = grill.getLine();
                int x0 = grill.getColumn();
                graphics.setColor(Color.cyan);
                graphics.fillRect(x0 * grillSize, y0 * grillSize, grillSize, grillSize);
            }
        }
        graphics.drawImage (BuildGame.readImage("images/agent.png") , x * grillSize  , y * grillSize , 100 , 100 , null) ;
    }

    public void paint(Danger danger , Graphics2D graphics){
        Grill position = danger.getPosition () ;
        int grillSize = GameConfig.GRILL_SIZE ;
        int x = position.getLine () ;
        int y = position.getColumn () ;

        graphics.drawImage (BuildGame.readImage("images/bomb.png") , y * grillSize  , x * grillSize , 100 , 100 , null) ;


    }

    public void paint(Goal goal , Graphics2D graphics) {
        Grill position = goal.getPosition();
        int grillSize = GameConfig.GRILL_SIZE;
        int x = position.getLine();
        int y = position.getColumn();

        graphics.drawImage (BuildGame.readImage("images/treasure.png") , x * grillSize  , y * grillSize , 100 , 100 , null) ;

    }
}
