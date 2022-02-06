package q_learning.process;

import q_learning.configuration.GameConfig;
import q_learning.data_base.environment.Grill;
import q_learning.graphic.DisplayBuild;
import q_learning.graphic.DisplayIntels;
import javax.swing.*;
import java.util.Random;

public class Qlearning implements Runnable{
    private double learningRate = GameConfig.learningRate;
    private double discountFactor = GameConfig.discountFactor;
    private final double exploration = GameConfig.explorationGreedy;

    private final double[][] qTable;
    private final int states;
    private final int actions = 4;
    private final DisplayBuild dashboard ;
    private final DisplayIntels intels ;
    private final JLabel jlabel ;

    private final AgentAction agentAction;
    private int round = 0 ;

    public Qlearning(AgentAction agentAction , DisplayBuild dashboard , DisplayIntels intels , JLabel jlabel ) {
        this.agentAction = agentAction;
        this.states = GameConfig.states;
        this.dashboard = dashboard;
        this.intels = intels ;
        this.jlabel = jlabel ;

        this.qTable = new double[this.states][];
        for (int s = 1; s < this.states; s++) {
            this.qTable[s] = new double[actions];
        }
        initQtable();
    }

    public double[][] getQtable() {
        return this.qTable;
    }

    public void initQtable() {
        for (int i = 1; i < this.states; i++) {
            for (int j = 0; j < this.actions; j++) {
                this.qTable[i][j] = 0;
            }
        }
    }

    public void updateQtable() {
        for (int i = 0 ; i < GameConfig.roundCount ; i++) {
            this.agentAction.resetAgent();
            int state = this.agentAction.getAgent().getPosition().getState();
            while (!BuildGame.endGame(this.agentAction.getAgent())) {
                int action = takeAction(state , this.exploration);
                Grill newGrill = this.agentAction.makeStep(action);
                int nextState = newGrill.getState();
                double newReward = newGrill.getReward();
                int nextAction = takeAction(nextState , 0.0);
                this.updateReward(state, action, newReward, nextState, nextAction);
                state = nextState;
            }
        }

        this.agentAction.resetAgent();
    }

    public void updateReward(int state, int action, double reward, int nextState, int nextAction) {
        this.qTable[state][action] = this.qTable[state][action] + this.learningRate * (reward + this.discountFactor * this.qTable[nextState][nextAction] - this.qTable[state][action]);
    }

    public int takeAction(int state, double greedy) {
        Random random = new Random();
        if (Math.random() < greedy) {
            return random.nextInt(4);
        } else
            return getActionMax(state);
    }

    public void printQtable() {
        System.out.println("\n[Qtable]");
        for (int i = 1; i < this.qTable.length; i++) {
            System.out.print("From state " + i + ":  ");
            for (int j = 0; j < this.qTable[i].length; j++) {
                System.out.printf("%6.2f ", (qTable[i][j]));
            }
            System.out.println();
        }
    }

    public int getActionMax(int state) {
        double rewardMax = 0.0;
        int actionMax = 0;
        for (int i = 0; i < this.actions; i++) {
            if (this.qTable[state][i] > rewardMax) {
                rewardMax = this.qTable[state][i];
                actionMax = i;
            }
        }
        return actionMax;
    }

    public AgentAction getAgentAction() {
        return this.agentAction;
    }
    public void run (){
         boolean stop = true ;
        for (int i = 0 ; i < GameConfig.roundCount ; i++) {
                this.agentAction.resetAgent();
                this.dashboard.repaint () ;
                this.round++ ;

            int state = this.agentAction.getAgent ().getPosition ().getState () ;
            while (!BuildGame.endGame(this.agentAction.getAgent ()) && stop){
                try {
                    Thread.sleep(GameConfig.BUILDING_SPEED);
                } catch (InterruptedException e) {
                    stop = false ;
                }
                int action = takeAction (state ,this.exploration) ;
                Grill newGrill = this.agentAction.makeStep (action) ;
                this.dashboard.repaint () ;
                this.jlabel.setText (this.getAgentAction ().getAgent ().getPosition ().toString ());

                int nextState = newGrill.getState () ;
                double newReward = newGrill.getReward () ;
                int nextAction = takeAction (nextState , 0.0) ;
                this.updateReward (state, action, newReward, nextState, nextAction) ;
                this.intels.getTextPane ().setText (" ") ;
                this.intels.printQtable (this.qTable) ;
                state = nextState ;

            }
            if (!stop){
                this.jlabel.setText ("-----------------------------------------Construction of the Q-table in pause-----------------------------------------") ;
            }

        }
        if (this.round < GameConfig.roundCount){
            JOptionPane.showMessageDialog (this.dashboard , "Q-TABLE READY !"); ;
        }

    }
}



