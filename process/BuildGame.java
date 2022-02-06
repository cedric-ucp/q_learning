package q_learning.process;

import q_learning.configuration.GameConfig;
import q_learning.data_base.elements.Agent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BuildGame {
    private static BuildEnvironment environment ;
    public static BuildEnvironment initEnvironment(){
        environment = new BuildEnvironment (GameConfig.LINE_COUNT , GameConfig.COLUMN_COUNT , GameConfig.states) ;
        return environment ;
    }
    public static AgentAction initAction (){
        return new AgentAction(environment) ;
    }
    public static boolean endGame (Agent agent){
        return agent.getPosition ().getReward () == GameConfig.reward ;
    }
    public static Image readImage (String path){
        try{
            return ImageIO.read (new File (path)) ;
        } catch (IOException e){
            System.err.println ("Can't read the image file") ;
            return null ;
        }
    }
}
