/* Her laves det board som Pacman skal spilles på. 
 * Dette board er 10x10 celler og disse 100 celler fyldes med tilfældige tal
 * fra 0 til 4. Disse tal vises i DrJava, således at man kan se at det virker.
 * Boardet er et to-dimensionelt array, hvor man har mulighed for at indtaste antal celler 
 * Boardet bliver kvadratisk i det antal celler man indtaster i command-linjen*/


import java.util.*;
class Board { 
  
public int size = 0;
public int[][] board;
public int score =0;

    public Board(int size) {       
        this.size=size;
        board = new int[size][size];
        
        Random random = new Random();

       
        for( int i = 0 ; i < board.length ; i++ ) { 
           for ( int j = 0 ; j < board[i].length ; j++ ) { 
             board[i][j] = random.nextInt(5);
           }
        }
          

        for( int[] a : board ) { 
       
   /* Her printes værdierne for arrayet */
        System.out.println( Arrays.toString( a ));
        }
      
    }
    /* Her laves de tre funktioner isWall, isFood og isBomb.
     * Hver funktion er tilknyttet en værdi i arrayet, der 
     * passer med hhv. væg, piller og bombe. */
    public boolean isWall(int x, int y){
      return (board[x][y]==1 || board[x][y] == 3); 
    }
    public boolean isFood (int x, int y){
      return (board[x][y]==2); 
    }
    public boolean isBomb(int x, int y){
    return (board[x][y]==4); 
    }
    /* Hvis Pacman lander på en pille, sættes arrayet til nul
     * (dvs. at pillen bliver spist) og score tælles én op */
    public void eat(int x, int y){
    board[x][y]=0;
    score++;
    System.out.println("Score: "+ score);
    }
    public int getScore(){
    return score;
    }
    
    /* Her defineres hvilke ting, der kan springes i luften.
     * Dette er væg, piller og selve bomben*/
    private void demolish (int x, int y){
         if (x>=0 && x<size && y>=0 && y<size) {
    switch(board[x][y]) {
    case 1: //væg
    case 2: //piller
    case 4: //bombe
      board[x][y]=0;
      break;
    default:
    }
         }
    }
    
    /* Her fjernes de omkringstående felter, hvis Pacman lander
     * på en bombe. Dette gælder dog ikke ubrydelige vægge (se 
     * demolish ovenover)*/
    public void boom(int x, int y){
          for (int i=-1; i<2; i++) {
      for (int j=-1; j<2; j++) {
        demolish(x+i, y+j);
      }
    }
    }
}

    

    
