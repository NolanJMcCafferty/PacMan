import java.awt.Image;
import javax.swing.JLabel;
import objectdraw.ActiveObject;
import objectdraw.DrawingCanvas;
import objectdraw.VisibleImage;

public class PacMan extends ActiveObject{
	
	//constants for the smooth motion of the pacman (EC)
	private static final int PAUSE1 = 70;
	private static final int PAUSE2 = 20;
    private final int STEPS = 7;
    
    //constants for the size of each cell
//    public static final double CELL_WIDTH = 25;
//    public static final double CELL_HEIGHT = 27;
    
    //starting position of the pacman
    private static final Position STARTING_P = new Position(12, 9);
    
    //boolean to check if the pacman is alive
	private boolean alive = true;
	
	//the visible image of the pacman
	private VisibleImage pacman;
	
	//the different images for each direction of the pacman (EC)
	private Image aImage1, aImage2, aImage3, aImage4;
	
	//the position of the pacman
	private Position pos;
	
	//directions to move the pacman around the maze
	private Direction curDir, newDir;
	
	//maze for the pacman to move around
	private Maze aMaze;
	
	//label to set the player's score (EC)
	private JLabel aScore;
	
	//the player's score (EC)
	private int points = 0;
	
	//canvas to draw on
	private DrawingCanvas aCanvas;
	
	//create a pacman at the starting position facing upwards
	public PacMan(Image image1, Image image2, Image image3, Image image4, Maze maze, DrawingCanvas canvas, JLabel score) {
		//variables for the images of the pacman (EC)
		aImage1 = image1;
		aImage2 = image2;
		aImage3 = image3;
		aImage4 = image4;
		
		//JLabel of the player's score (EC)
		aScore = score;
		
		//the maze
		aMaze = maze;
		
		//the canvas
		aCanvas = canvas;
		
		//initialize the position and directions
		pos = STARTING_P;
		curDir = Direction.NONE;
		newDir = Direction.NONE;
		
		//initialize the pacman image
		pacman = new VisibleImage(image1, maze.getLocOfCell(pos), canvas);
		start();
	}

	//while the pacman is alive, move the pacman around the maze and eat the food
	public void run() {
		//check if the pacman is alive
		while (alive) {
			//declare the new position by translating the current position in the new direction
			
			if (pos.getRow() == 10 && pos.getCol() == 0 && curDir == Direction.LEFT) {
				pacman.moveTo(aMaze.getLocOfCell(new Position(10, 18)));
				pos = new Position(10, 18);
				aMaze.visitCell(pos);
				curDir = Direction.LEFT;
			}
			if (pos.getRow() == 10 && pos.getCol() == 18 && curDir == Direction.RIGHT) {
				pacman.moveTo(aMaze.getLocOfCell(new Position(10, 0)));
				pos = new Position(10, 0);
				aMaze.visitCell(pos);
				curDir = Direction.RIGHT;
			}
			Position newPos = pos.translate(newDir);

			//if the maze does not contain a wall at this new position, change the pacman image (EC)
			if (!aMaze.containsWall(newPos)) {
				setImage();
				curDir = newDir;	
			}
			//if the maze does contain a wall at this new position, change the new position by translating the current position in the current direction
			//necessary for the easier steering of the pacman (EC)
			else {
				newPos = pos.translate(curDir);
			}
			
			//if the maze does not contain a wall at this new position, move the pacman in this direction
			if (!aMaze.containsWall(newPos)) {
				pos = newPos;
				
				//smooth motion for the pacman between cells (EC)
				for (int i = 0; i < STEPS; i++) {
					pacman.move(curDir.getXchange()*Cell.CELL_WIDTH/STEPS, curDir.getYchange()*Cell.CELL_HEIGHT/STEPS);
					pause(PAUSE1);
				}

				//visit the cell that the pacman is in, so that it eats the food
				if (alive) {
					aMaze.visitCell(pos);
				}
				
				//set the player's score when the pacman eats the food (EC)
				if (!pos.equals(STARTING_P) && aMaze.getCell(pos.getRow(), pos.getCol()).newScore()) {
					setScore();
				}
			}
			pause(PAUSE2);
		}
	}
	
	//method to set the direction of the pacman
	public void setDirection(Direction dir) {
		newDir = dir;
	}
	
	//method to change the player's score (EC)
	public void setScore() {	
		points = points + 10;
		
		//set the JLabel of the score that is passed into the constructor (EC)
		aScore.setText("Score:" + points);
	}
	
	//method to remove the pacman from the canvas
	public void removeFromCanvas() {
		kill();
		pacman.removeFromCanvas();
		points = 0;
	}
	
	//method to check if the ghost kills the pacman
	public boolean overlaps(VisibleImage image) {
		return pacman.overlaps(image);
	}
	
	//method to check if the pacman is alive
	public boolean isLiving() {
		return alive;
	}
	
	//method to kill the pacman
	public void kill() {
		alive = false;
	}
	
	//method to set the image of the pacman depending on which direction it is moving (EC)
	public void setImage() {
		if (newDir == Direction.UP && curDir != Direction.UP) {
			pacman.setImage(aImage1);
		}
		if (newDir == Direction.DOWN && curDir != Direction.DOWN) {
			pacman.setImage(aImage2);
		}
		if (newDir == Direction.LEFT && curDir != Direction.LEFT) {
			pacman.setImage(aImage3);
		}
		if (newDir == Direction.RIGHT && curDir != Direction.RIGHT) {
			pacman.setImage(aImage4);
		}
	}
	
}
