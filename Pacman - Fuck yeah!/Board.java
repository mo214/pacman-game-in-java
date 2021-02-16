/* Her laves det board som Pacman skal spilles p�. 
 * Dette board er 10x10 celler og disse 100 celler fyldes med tilf�ldige tal
 * fra 0 til 4. Disse tal vises i DrJava, s�ledes at man kan se at det virker.
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
       
   /* Her printes v�rdierne for arrayet */
        System.out.println( Arrays.toString( a ));
        }
      
    }
    /* Her laves de tre funktioner isWall, isFood og isBomb.
     * Hver funktion er tilknyttet en v�rdi i arrayet, der 
     * passer med hhv. v�g, piller og bombe. */
    public boolean isWall(int x, int y){
      return (board[x][y]==1 || board[x][y] == 3); 
    }
    public boolean isFood (int x, int y){
      return (board[x][y]==2); 
    }
    public boolean isBomb(int x, int y){
    return (board[x][y]==4); 
    }
    /* Hvis Pacman lander p� en pille, s�ttes arrayet til nul
     * (dvs. at pillen bliver spist) og score t�lles �n op */
    public void eat(int x, int y){
    board[x][y]=0;
    score++;
    System.out.println("Score: "+ score);
    }
    public int getScore(){
    return score;
    }
    
    /* Her defineres hvilke ting, der kan springes i luften.
     * Dette er v�g, piller og selve bomben*/
    private void demolish (int x, int y){
         if (x>=0 && x<size && y>=0 && y<size) {
    switch(board[x][y]) {
    case 1: //v�g
    case 2: //piller
    case 4: //bombe
      board[x][y]=0;
      break;
    default:
    }
         }
    }
    
    /* Her fjernes de omkringst�ende felter, hvis Pacman lander
     * p� en bombe. Dette g�lder dog ikke ubrydelige v�gge (se 
     * demolish ovenover)*/
    public void boom(int x, int y){
          for (int i=-1; i<2; i++) {
      for (int j=-1; j<2; j++) {
        demolish(x+i, y+j);
      }
    }
    }
}

    

    
