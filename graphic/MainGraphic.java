package q_learning.graphic ;

import q_learning.configuration.GameConfig;
import q_learning.process.AgentAction;
import q_learning.process.BuildEnvironment;
import q_learning.process.BuildGame;
import q_learning.process.Qlearning;
import q_learning.graphic.DisplayIntels ;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

public class MainGraphic extends JFrame implements Runnable{

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Font font = new Font(Font.MONOSPACED, Font.BOLD, 20) ;


    private final static Dimension preferredSize = new Dimension(GameConfig.WINDOW_WIDTH +100 , GameConfig.WINDOW_HEIGHT);
    private final static Dimension dashboardSize = new Dimension (GameConfig.WINDOW_WIDTH  , GameConfig.WINDOW_HEIGHT) ;

    private DisplayStart dashboardStart;
    private DisplayBuild dashboardBuild ;
    private BuildEnvironment environment ;
    private AgentAction agentAction ;
    private Qlearning q ;
    public  MainGraphic  instance = this ;
    
    private final JButton start = new JButton (" START ") ;
    private final JButton reset = new JButton (" RESET ") ;
    private final JButton train = new JButton (" BUILD Q-TABLE ") ;
    private final JButton speed = new JButton (" CHANGE SPEED ") ;

    private DisplayIntels displayIntels ;
    private boolean go = true ;
    private boolean yes = true ;
    private JLabel jlabel ;
    private Thread thread , start_thread ;

    public MainGraphic(String title) {
        super(title);
        this.startGame () ;
    }
    

    private void startGame() {
    	Container contentPane = getContentPane() ;
    	contentPane.setLayout (new BorderLayout ()) ;
        this.setIconImage (BuildGame.readImage ("images/robot_icon.png")) ;

    	this.start.addActionListener (new StartStopAction ()) ;
        this.start.setFont (font) ;

    	this.reset.addActionListener (new ResetAction ()) ;
        this.reset.setFont (font) ;

        this.train.addActionListener (new BuildAction ()) ;
        this.train.setFont (font) ;

        this.speed.addActionListener (new ChangeSpeed ()) ;
        this.speed.setFont (font) ;

        JPanel remote = new JPanel () ;
    	remote.add (this.train) ;
    	remote.add (this.start) ;
    	remote.add (this.reset) ;
    	remote.add (this.speed) ;


    	remote.setBackground (new Color (128, 162, 190)) ;

        this.environment = BuildGame.initEnvironment () ;
        this.dashboardStart = new DisplayStart(environment) ;
        this.dashboardBuild = new DisplayBuild (environment) ;
        this.agentAction = BuildGame.initAction () ;


        this.jlabel = new JLabel (agentAction.getAgent ().getPosition ().toString ()) ;
        jlabel.setFont (new Font ("font" , Font.PLAIN , 30));

        this.displayIntels = new DisplayIntels () ;
        JScrollPane pane = new JScrollPane (this.displayIntels.getTextPane ()) ;
        pane.setLayout (new ScrollPaneLayout ());
        pane.setPreferredSize (preferredSize) ;
        q =  new Qlearning  (agentAction , dashboardBuild, displayIntels , jlabel);

        this.dashboardStart.setPreferredSize (dashboardSize) ;
        this.dashboardBuild.setPreferredSize (dashboardSize) ;
        contentPane.setBackground (new Color ( 128, 162, 190 )) ;
        displayIntels.printQtable (q.getQtable ()) ;

        contentPane.add (remote , BorderLayout.NORTH) ;
        contentPane.add (pane , BorderLayout.EAST) ;
        contentPane.add (this.dashboardStart , BorderLayout.WEST) ;
        contentPane.add (jlabel , BorderLayout.SOUTH) ;

        this.setDefaultCloseOperation (DISPOSE_ON_CLOSE) ;
        pack () ;
        setVisible (true) ;
        setResizable (false) ;
    }

    @Override
    public void run() {

        while (!BuildGame.endGame (this.agentAction.getAgent ()) && !go) {
            try {
                Thread.sleep(GameConfig.GAME_SPEED);
            } catch (InterruptedException e) {
                System.out.println () ;
            }
            this.agentAction.getToThePoint(this.q);
            this.dashboardStart.repaint () ;
            this.jlabel.setText(this.agentAction.getAgent().getPosition().toString());
            this.dashboardStart.add (this.agentAction.getAgent ().getPosition ());
        }
            dashboardStart.getPath ().removeAll (dashboardStart.getPath ()) ;

        if (BuildGame.endGame (this.agentAction.getAgent ())){
            this.agentAction.resetAgent () ;
            start.setText (" START ") ;
            this.jlabel.setText(this.agentAction.getAgent().getPosition().toString());
            this.dashboardStart.getPath ().removeAll (this.dashboardStart.getPath ()) ;
            this.dashboardStart.repaint () ;
        }
    }

    public class StartStopAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if(!go && !BuildGame.endGame (agentAction.getAgent ())){
                go = true;
                start.setText(" START ");
            } else{
                instance.getContentPane().add (dashboardStart, BorderLayout.WEST) ;
                go = false;
                start.setText(" PAUSE ");
                if (start_thread == null){
                    q.updateQtable () ;
                }

                displayIntels.getTextPane ().setText (" ") ;
                displayIntels.printQtable_v2 (q.getQtable ()) ;
                start_thread = new Thread (instance);
                start_thread.start();
            }
        }
        public void actionPerformed (){
            if(!go && !BuildGame.endGame (agentAction.getAgent ())){
                go = true;
                start.setText(" START ");
            } else{
                instance.getContentPane().add (dashboardStart, BorderLayout.WEST) ;
                go = false;
                start.setText(" PAUSE ");
                if (start_thread == null){
                    q.updateQtable () ;
                }

                displayIntels.getTextPane ().setText (" ") ;
                displayIntels.printQtable_v2 (q.getQtable ()) ;
                start_thread = new Thread (instance);
                start_thread.start();
            }
        }
    }
    public class ResetAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            go = true;
            start.setText(" START ");
            agentAction.resetAgent();
            dashboardStart.getPath().removeAll(dashboardStart.getPath());
            environment.resetDangersOnMap();
            q.initQtable();
            if (start_thread != null){
                start_thread = null ;
            }
            displayIntels.getTextPane ().setText (" ");
            displayIntels.printQtable (q.getQtable ());
            jlabel.setText(agentAction.getAgent().getPosition().toString());

            if (thread != null) {
                if (thread.isAlive()) {
                    train.setText(" BUILD Q-TABLE ");
                    thread.interrupt();
                    q.initQtable () ;
                }
                dashboardBuild.repaint();
            } else {
                dashboardStart.repaint();
            }
        }
    }
    public class BuildAction implements ActionListener{
        @Override

        public void actionPerformed(ActionEvent e) {
            if (!go) {
                go = true;
                train.setText(" BUILD Q-TABLE ");
                if (thread != null) {
                    if (thread.isAlive()) {
                        thread.interrupt();
                    }
                }
                jlabel.setText("Construction of the Q-table in pause");
            } else {
                instance.getContentPane().remove(dashboardStart);
                instance.getContentPane().add(dashboardBuild, BorderLayout.WEST);
                if (yes) {
                    String[] speed = {"Slow", "Medium", "Fast"};
                    String s = (String) JOptionPane.showInputDialog(null,
                            "Choose Speed", "Speed Selection", JOptionPane.QUESTION_MESSAGE, null,
                            speed, speed[1]);
                    if (s != null) {
                        if (s.equals("Slow"))
                            GameConfig.BUILDING_SPEED = 1000;
                        else if (s.equals("Medium"))
                            GameConfig.BUILDING_SPEED = 500;
                        else
                            GameConfig.BUILDING_SPEED = 100;
                    } else {
                        GameConfig.BUILDING_SPEED = 1000;
                    }
                }
                go = false;
                train.setText(" PAUSE ");
                displayIntels.getTextPane().setText(" ");
                displayIntels.printQtable(q.getQtable());
                thread = new Thread(q);
                thread.start();
            }
            yes = false;
        }
        public void actionPerformed (){
            if (!go) {
                go = true;
                train.setText(" BUILD Q-TABLE ");
                if (thread.isAlive()) {
                    thread.interrupt();
                }
                jlabel.setText("Construction of the Q-table in pause");
            } else {
                instance.getContentPane().remove(dashboardStart);
                instance.getContentPane().add(dashboardBuild, BorderLayout.WEST);
                if (yes) {
                    String[] speed = {"Slow", "Medium", "Fast"};
                    String s = (String) JOptionPane.showInputDialog(null,
                            "Choose Speed", "Speed Selection", JOptionPane.QUESTION_MESSAGE, null,
                            speed, speed[1]);
                    if (s != null) {
                        if (s.equals("Slow"))
                            GameConfig.BUILDING_SPEED = 1000;
                        else if (s.equals("Medium"))
                            GameConfig.BUILDING_SPEED = 500;
                        else
                            GameConfig.BUILDING_SPEED = 100;
                    } else {
                        GameConfig.BUILDING_SPEED = 1000;
                    }
                }
                go = false;
                train.setText(" PAUSE ");
                displayIntels.getTextPane().setText(" ");
                displayIntels.printQtable(q.getQtable());
                thread = new Thread(q);
                thread.start();
            }
            yes = false;
        }
    }
    public class ChangeSpeed implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            go = false ;
            boolean launch = false ;

            if (thread != null){
                if (thread.isAlive ()){
                    train.setText(" BUILD Q-TABLE ");
                    thread.interrupt ();
                    launch = true ;
                }
            }
            else{
                go = true;
                start.setText(" START ");
            }

            String[] speed = {"Slow", "Medium", "Fast"} ;
            String s = (String) JOptionPane.showInputDialog(null,
                    "Choose Speed", "Speed Selection", JOptionPane.QUESTION_MESSAGE, null,
                    speed, speed[1]);
            if (s != null) {
                if (s.equals("Slow"))
                    GameConfig.BUILDING_SPEED = 1000;
                else if (s.equals("Medium"))
                    GameConfig.BUILDING_SPEED = 500;
                else
                    GameConfig.BUILDING_SPEED = 100;
            }else {
                GameConfig.BUILDING_SPEED = 1000;
            }
            go = true ;
            if (launch){
                new BuildAction ().actionPerformed () ;
            }
            else{
                new StartStopAction ().actionPerformed () ;
            }
        }
    }

    public static void main (String [] args){
    	 new MainGraphic ("Q-learning") ;
    }
    	  
}


























































































