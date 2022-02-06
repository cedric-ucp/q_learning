package q_learning.graphic;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;


public class DisplayIntels {
    private final JTextPane textPane ;

    public DisplayIntels () {
        this.textPane = new JTextPane () ;
        this.textPane.setFont (new Font ("font" , Font.PLAIN , 15)) ;
        this.textPane.setAutoscrolls(true) ;
    }
    public void printQtable (double [][] qtable){
        this.textPane.setEditable (true) ;
        appendToPane (this.textPane ,"\n------------------------------[Qtable]-----------------------------\n\n" , Color.BLACK) ;
        for (int i = 1; i < qtable.length ; i++) {
            appendToPane (this.textPane , "[State "+i+"]\n\n" , Color.BLACK) ;
            for (int j = 0; j < qtable[i].length ; j++) {
                double action = (double) Math.round(qtable[i] [j] * 100) / 100 ;
                switch (j){
                    case 0 :
                        appendToPane(this.textPane ,"   Left : ["+action+"] " , Color.BLUE) ;
                        break ;
                    case 1 :
                        appendToPane(this.textPane ,"   Down : ["+action+"] " , Color.RED) ;
                        break ;
                    case 2 :
                        appendToPane(this.textPane ,"   Up : ["+action+"] " , Color.MAGENTA) ;
                        break ;
                    case 3 :
                        appendToPane(this.textPane ,"   Right : ["+action+"] " , Color.GREEN) ;
                        break ;
                    default :
                        break ;
                }
            }
            appendToPane (this.textPane , "\n\n" , Color.magenta ) ;
        }
        appendToPane (this.textPane , frontier () , Color.black) ;
        this.textPane.setEditable (false) ;
    }
    public void printQtable_v2 (double [][] table){
        this.textPane.setEditable (true) ;
        appendToPane (this.textPane ,"\n------------------------------[Qtable]-----------------------------\n\n" , Color.BLACK) ;
        for (int i = 1; i < table.length ; i++) {
            appendToPane (this.textPane , "[State "+i+"]\n\n" , Color.BLACK) ;
            for (int j = 0; j < table[i].length ; j++) {
                double action = (double) Math.round(table[i] [j] * 100) / 100 ;
                int max = this.getMax (table , i) ;
                switch (j){
                    case 0 :
                        if (j == max)
                            appendToPane (this.textPane , "   Left : ["+action+"] ") ;
                        else
                            appendToPane(this.textPane ,"   Left : ["+action+"] " , Color.BLUE) ;
                        break ;
                    case 1 :
                        if (j == max)
                            appendToPane (this.textPane , "   Down : ["+action+"] ") ;
                        else
                            appendToPane(this.textPane ,"   Down : ["+action+"] " , Color.RED) ;
                        break ;
                    case 2 :
                        if (j == max)
                            appendToPane (this.textPane , "   Up : ["+action+"] ") ;
                        else
                            appendToPane(this.textPane ,"   Up : ["+action+"] " , Color.MAGENTA) ;
                        break ;
                    case 3 :
                        if (j == max)
                            appendToPane (this.textPane , "   Right : ["+action+"] ") ;
                        else
                            appendToPane(this.textPane ,"   Right : ["+action+"] " , Color.GREEN) ;
                        break ;
                    default :
                        break ;
                }
            }
            appendToPane (this.textPane , "\n\n" , Color.magenta ) ;
        }
        appendToPane (this.textPane , frontier () , Color.black) ;
        this.textPane.setEditable (false) ;
    }
    public void appendToPane (JTextPane tp , String txt , Color c){
        StyleContext sc = StyleContext.getDefaultStyleContext () ;
        AttributeSet aset = sc.addAttribute (SimpleAttributeSet.EMPTY , StyleConstants.Foreground , c) ;
        aset = sc.addAttribute (aset , StyleConstants.FontFamily , "Lucida Console") ;
        aset = sc.addAttribute (aset , StyleConstants.Alignment , StyleConstants.ALIGN_CENTER) ;
        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, true);
        tp.replaceSelection (txt) ;

    }
    public void appendToPane (JTextPane tp , String txt){
       StyleContext sc =  StyleContext.getDefaultStyleContext () ;
        AttributeSet aset = sc.addAttribute (SimpleAttributeSet.EMPTY , StyleConstants.Background , new Color (  54, 255, 0 )) ;
        aset = sc.addAttribute (aset , StyleConstants.FontFamily , "Lucida Console") ;
        aset = sc.addAttribute (aset , StyleConstants.Alignment , StyleConstants.ALIGN_CENTER) ;
        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, true);
        tp.replaceSelection (txt) ;
    }

    public JTextPane getTextPane (){
        return this.textPane ;
    }
    public String frontier (){
        return "--------------------------------------------------------------------------\n";
    }
    public int getMax (double [] []qTable , int state){
        double rewardMax = 0.0;
        int actionMax = 0;
        for (int i = 0; i < 4  ; i++) {
            if (qTable[state][i] > rewardMax) {
                rewardMax = qTable[state][i];
                actionMax = i;
            }
        }
        return actionMax;
    }
}
