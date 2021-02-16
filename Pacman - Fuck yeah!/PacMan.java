import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class PacMan extends JPanel implements KeyListener {
  /*Laver variablen SIZE, sŒ vi kan n¿jedes med at ¾ndre 
   * den et enkelt sted */
 private final static int SIZE = 30;
 
 private AffineTransform tx = new AffineTransform();
 private AffineTransform ty = new AffineTransform();
 private AffineTransform tz = new AffineTransform();
 private Shape pacman = null;
 private Shape pacman2 = null;
 private Shape monster = null;
 private int x = 0; //Pacman1s x-startposition
 private int y = 0; //Pacman1s y-startposition
 public int w = 0; 
 public int h = 0;
 private int x1 = 0; //Pacman2s x-startposition
 private int y1 = 0; //Pacman2s y-startposition
 private int x2 = 0; //Monster 
 private int y2 = 0; //Monster
 private Board board = null;

 /* Her er keyPressed. Her tjekkes samtidig om Pacman er pŒ vej
  * ind i en v¾g. Hvis der ikke er en v¾g, bev¾ges Pacman */
 
 //Pacman1
 public void keyPressed(KeyEvent e) {   
        switch (e.getKeyCode() ) {
     case KeyEvent.VK_UP: 
       if ( y > 0 && !board.isWall(x,y-1))  y--; 
       break;
     case KeyEvent.VK_DOWN: 
       if ( y < board.size-1 && !board.isWall(x,y+1)) y++;
       break;
     case KeyEvent.VK_LEFT: 
       if ( x > 0 && !board.isWall(x-1,y))  x--; 
       break;
     case KeyEvent.VK_RIGHT : 
       if ( x < board.size-1 && !board.isWall(x+1,y)) x++; 
       break;
 //Pacman2
     case KeyEvent.VK_W: 
       if ( y1 > 0 && !board.isWall(x1,y1-1))  y1--; 
       break;
     case KeyEvent.VK_S: 
       if ( y1 < board.size-1 && !board.isWall(x1,y1+1)) y1++;
       break;
     case KeyEvent.VK_A: 
       if ( x1 > 0 && !board.isWall(x1-1,y1))  x1--; 
       break;
     case KeyEvent.VK_D: 
       if ( x1 < board.size-1 && !board.isWall(x1+1,y1)) x1++; 
       break;  
        default:
        }
        
        /* Her flyttes Pacman1 og Pacman2 til hhv (x,y) og (x1, y1) og tegner ham igen.
         * NŒr Pacman er flyttet, tjekkes feltet om det er 
         * piller eller en bombe. Er der en pille eller en bombe
         k¿res hhv. funktionerne "eat" og "boom".*/    
        tx.setToTranslation(x*SIZE,y*SIZE);
        if (board.isFood(x,y)) board.eat(x,y);
        if (board.isBomb(x,y)) board.boom(x,y); 
        
        ty.setToTranslation(x1*SIZE,y1*SIZE);
        if (board.isFood(x1,y1)) board.eat(x1,y1);
        if (board.isBomb(x1,y1)) board.boom(x1,y1);
        
        /*Bruges en while-l¿kke eller udelukkende if-s¾tninger, bev¾ger monstret
         * sig skrŒt, idet at den bev¾ger sig til horisontalt og vertikalt samtidig. 
         * Det g¿r monstret ikke nu*/
        if(x>x2 &&!board.isWall(x2+1,y2)){ x2++;}
        else if (x<x2 &&!board.isWall(x2-1,y2)) {x2--;}
        else if(y>y2 &&!board.isWall(x2,y2+1)) {y2++;}
        else if(y<y2 &&!board.isWall(x2,y2-1)) {y2--;}
        tz.setToTranslation(x2*SIZE,y2*SIZE); 
       
        repaint();
             
       /* Hvis begge Pacmen gŒr ind i hinanden eller en af dem gŒr ind i monstret, 
        * er spillet tabt og vinduet lukkes.*/
        
        if(x1==x && y1==y || x1==x2 && y1==y2 || x==x2 && y==y2){
         System.out.print("Du har tabt");     
         System.exit(0); //Lukker vinduet med det samme
         

                  }
        }
         public void keyReleased(KeyEvent e) {}
         public void keyTyped(KeyEvent e) {}
         
    
 
 public PacMan(Board board) {
  super();
  this.board = board;
  /*Tegner Pacman som en arc*/
  pacman = new Arc2D.Double(0,0,SIZE-5,SIZE-5,35,290,Arc2D.PIE);
  pacman2 = new Arc2D.Double(0,0,SIZE-5,SIZE-5,35,290,Arc2D.PIE);
  monster = new Ellipse2D.Double(0,0,SIZE-5, SIZE-5);

 /* Her findes et tilf¾ldigt tomt felt, som Pacman kan starte pŒ. */
  Random random = new Random();
  
  
do {
    x=random.nextInt(board.size);
    y=random.nextInt(board.size);
    x1=random.nextInt(board.size);
    y1=random.nextInt(board.size);
    x2=random.nextInt(board.size);
    y2=random.nextInt(board.size);
 
  }
  while (board.board[x][y] != 0 || board.board[x1][y1] != 0 || board.board[x2][y2] != 0); 
  
  
  tx.setToTranslation(x*SIZE,y*SIZE);
  ty.setToTranslation(x1*SIZE, y1*SIZE);
  tz.setToTranslation(x2*SIZE, y2*SIZE); 
 
  setFocusable(true);
  

/*Keylistner s¿rger for at Pacman bev¾ger sig med tryk pŒ piletasterne 
 * Pacman bev¾ger sig ud af spillepladen, men bev¾ger sig ikke ud af 
 * vinduet*/
  
  addKeyListener(this);
       requestFocus();
 }
 
   /* I paintComponent tegnes v¾gge, piller og bomber 
    * afh¾ngigt af den tilf¾ldige v¾rdi arrayet er blevet tildelt i
    klassen Board */
    public void paintComponent(Graphics g) { 
       Graphics2D g2 = (Graphics2D) g;
       h = getHeight() - 1;
       w = getWidth() - 1;
           
       g.setColor(Color.BLACK);
       g.fillRect(0, 0, w, h);
        for( int i = 0 ; i < board.board.length ; i++ ) { 
           for ( int j = 0 ; j < board.board[i].length ; j++ ) {
             
             switch (board.board[i][j]){
               case 1: //V¾g
                 g2.setColor(Color.BLUE);
                 g2.fillRect(i*SIZE, j*SIZE,SIZE, SIZE);
               break;
               case 2: //Pille
                 g2.setColor(Color.RED);
                 g2.fill (new Ellipse2D.Double(i*SIZE+10, j*SIZE+10, SIZE-15, SIZE-15));
               break;
               case 3: //Ubrydelig v¾g
                 g2.setColor(Color.GREEN);
                 g2.fillRect(i*SIZE, j*SIZE,SIZE, SIZE);
                 break;
               case 4: //Bombe
                 g2.setColor(Color.WHITE);
                 g2.fill (new Ellipse2D.Double(i*SIZE+10, j*SIZE+10, SIZE-15, SIZE-15));
                 break;
             }
       
       /* Her tegnes Pacman og gemmes som den transformerede Shape s.
        * Herefter farves Pacman gul */
       g.setColor(Color.YELLOW);
       Shape s = tx.createTransformedShape(pacman);
       g2.fill(s);
       g.setColor(Color.ORANGE);
       Shape p = ty.createTransformedShape(pacman2);
       g2.fill(p);
       g.setColor(Color.MAGENTA);
       Shape m = tz.createTransformedShape(monster);
       g2.fill(m);

        }
    }
    }
    

    /* Her k¿res main. Her er blandt andet funktionen, der giver mulighed for at indtaste 
     * st¿rrelsen pŒ boardet*/
 public static void main(String[] args) {
  JFrame f = new JFrame("Pacman"); // opret vindue
  Board board;
  if(args.length != 0){
    board = new Board(Integer.parseInt(args[0]));
  } else {
    board = new Board(10);
  }
  f.add(new PacMan(board));     
  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  f.setSize(board.size*SIZE,board.size*SIZE+SIZE);
  f.setVisible(true);

 }
}