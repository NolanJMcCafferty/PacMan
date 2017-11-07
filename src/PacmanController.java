import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import objectdraw.*;
import java.applet.AudioClip;

/**
 * @author nolanmccafferty
 *
 * class that controls and runs the PacMan game 
 * adds a GIU button to start and restart the game and a
 * score counter to keep track of the player's score
 */

public class PacmanController extends WindowController implements ActionListener, KeyListener {

	//button to start a new game (EC)
    private JButton start = new JButton("Start new game");
    
    //number of ghosts on the screen
	private static final int NUM_GHOSTS = 4;
	
	//constants for the location and size of the losing/winning text (EC)
    private static final Location TEXT_LOC = new Location (180, 270);
	private static final int FONT_SIZE = 25;
	
	//declaring the pacman and initializing it to null
    private PacMan pacman = null;
    
    //number of empty cells
    private int numEmpty;
    
    //text to tell the user when they have won or lost
    private Text text;
    
    //array of ghosts  in order to remove them from the canvas
    private Ghost[] ghosts;
    
    //declaring the maze
    private Maze maze;
    
    //the background music to the game (pacman them song Trap remix, extremely annoying) (EC)
    private AudioClip music;
    
    //label to show the player's score (EC)
    private JLabel scoreLabel;
    
    //integer to change the player's score (EC)
    private int correctCount;

    //creates the maze and the new game button, and starts the score at zero
	  public void begin() {
		    //initialize the maze
	        initMaze();
	        
	        setFocusable(true);
	        requestFocus();
	        addKeyListener(this);
	        canvas.addKeyListener(this);
	        
	        //set up the GUI components and add them to the screen (EC)
	        JPanel bottom = new JPanel(new GridLayout(1, 2));
	        start.addActionListener(this);
	        bottom.add(start);
	        correctCount = 0;
	        scoreLabel = new JLabel("Score:" + correctCount);
	        bottom.add(scoreLabel);
	        getContentPane().add((Component)bottom, "South");
	        
	        //set up the text that will display the winning or losing text (EC)
	        text = new Text("", TEXT_LOC, canvas);
	        text.setFontSize(FONT_SIZE);
	     }
	  
	//set up the maze 
	public void initMaze() {
        maze = new Maze(MazeArray.rows(), MazeArray.cols(), canvas, this);

        for (int row = 0; row < MazeArray.map.length; row++) {
            for (int col = 0; col < MazeArray.map[row].length; col++) {
                if (MazeArray.map[row][col] == 'X') {
                    maze.addCell(1, row, col);
                } else {
                    maze.addCell(0, row, col);
                    ++numEmpty;
                }  
            }
        }
    }
	
	//set up the pacman and the ghosts
	public void players() {
		//initialize the pacman 
		pacman = new PacMan(getImage("images/pacman_up.gif"), getImage("images/pacman_down.gif"), getImage("images/pacman_left.gif"), getImage("images/pacman_right.gif"), maze, canvas, scoreLabel);
		
		//create an array of the four ghosts
		ghosts = new Ghost[NUM_GHOSTS];
		ghosts[0] = new Ghost(getImage("Images/blueghost.gif"), pacman, maze, this,  canvas); 
		ghosts[1] = new Ghost(getImage("Images/purpleghost.gif"), pacman, maze, this, canvas);
		ghosts[2] = new Ghost(getImage("Images/redghost.gif"), pacman, maze, this, canvas);
		ghosts[3] = new Ghost(getImage("Images/yellowghost.gif"), pacman, maze, this, canvas);
	}
	
	   public void keyTyped(KeyEvent arg0) {
	    }

	    public void keyReleased(KeyEvent arg0) {
	    }

	    //change the direction of the pacman by pressing the arrow keys
	    public void keyPressed(KeyEvent e) {
	    	if (pacman != null) {
	    		if (e.getKeyCode() == 38) {
	    			pacman.setDirection(Direction.UP);
	    		} else if (e.getKeyCode() == 40) {
	    			pacman.setDirection(Direction.DOWN);
	    		} else if (e.getKeyCode() == 37) {
	    			pacman.setDirection(Direction.LEFT);
	    		} else if (e.getKeyCode() == 39) {
	    			pacman.setDirection(Direction.RIGHT);
	        	}
	    	}
	   }
	    
	    //restart the game when the start new game button is pressed (EC)
	    public void actionPerformed(ActionEvent event) {
	    	if (pacman != null) {
	    		//remove each ghost from the canvas using the array
	    		for (int i = 0; i < NUM_GHOSTS; i++) {
	    			ghosts[i].removeFromCanvas();
	    		}
	    		pacman.removeFromCanvas();
	    	}
	    	
	    	//reset the maze, putting the food back in each cell
	    	maze.resetMaze(numEmpty);
	    	
	    	//reset the winning/losing text (EC)
	    	text.setText("");
	    	
	    	//set up the pacman and the ghosts in the maze
	    	players();
	    	
	    	//reset the score back to 0
	    	scoreLabel.setText("Score:" + 0);
	    	
	    	//play the extremely annoying background music (EC)
	        music = getAudio("pacman.au");
	    	music.play();

	    	canvas.requestFocus();
	        canvas.requestFocusInWindow();
	    }
	    
	    //stop the game when the player loses
	    public void lose() {
	    	//kill the pacman
	    	pacman.kill();
	    	
	    	//set the text to the losing text
	    	text.setText("You Lose");
	    	text.setColor(Color.RED);
	    	
	    	//stop the background music
	    	music.stop();
	    	
	    	//play the even more annoying razz sound from the Simon game
	    	AudioClip badSound = getAudio("razz.au");
	    	badSound.play();
	    }
	    
	    //stop the game when the player wins
	    public void win() {
	    	//kill the pacman
	    	pacman.kill();
	    	
	    	//set the text to the winning text
	    	text.setText("You Win!");
	    	text.setColor(Color.GREEN);
	    }
}
