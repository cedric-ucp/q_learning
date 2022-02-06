package q_learning.data_base.environment;

public class Environment {
    public Grill [] [] environment ;
    private final int lineNumber ;
    private final int columnNumber ;
    

    public Environment (int lineNumber , int columnNumber , int states){
        this.lineNumber = lineNumber ;
        this.columnNumber  = columnNumber ;
        this.environment = new Grill [this.lineNumber][this.columnNumber] ;
        int st = 1 ;
        for (int line = 0 ; line < lineNumber ; line++){
            for (int column = 0 ; column < columnNumber ; column++){
                this.environment [line][column] = new Grill (line , column , st) ;
                st++ ;
            }
        }
    }
    public Grill [][] getEnvironment (){
        return this.environment ;
    }
    public void setEnvironment (Grill grill){

    }
    public Grill getGrill(int line , int column){
        return this.environment [line] [column] ;
    }
    public Grill getGrill (int state){
        for (int i = 0 ; i < this.lineNumber ; i++){
            for (int j = 0 ; j < this.columnNumber ; j++){
                if (this.environment [i] [j].getState() == state)
                    return this.environment [i] [j] ;
            }
        }
        return null ;
    }
    public Grill getGrill(double reward){
        for (int i = 0 ; i < this.lineNumber ; i++){
            for (int j = 0 ; j < columnNumber ; j++){
                if (this.environment [i] [j].getReward() == reward)
                    return this.environment [i] [j] ;
            }
        }
        return null ;
    }

    /***Define mapsLimits***/

    public boolean CanGoRight (Grill position){
        return position.getLine () < lineNumber - 1 ;
    }
    public boolean CanGoLeft (Grill position){
        return position.getLine () > 0 ;
    }
    public boolean CanGoUp (Grill position){
        return position.getColumn () > 0 ;
    }
    public boolean CanGoDown (Grill position){
        return position.getColumn () < columnNumber - 1 ;
    }
    public int getLineNumber() {
        return this.lineNumber;
    }
    public int getColumnNumber() {
        return this.columnNumber;
    }
}
