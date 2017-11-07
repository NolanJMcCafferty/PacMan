import java.awt.Image;
import objectdraw.ActiveObject;
import objectdraw.DrawingCanvas;
import objectdraw.VisibleImage;

public class Ghost extends ActiveObject {
	
	//constants for the smooth motion of the ghosts (EC)
	private static final int PAUSE_TIME = 100;
    private final int STEPS = 7;
    
    //the image of the ghost
	private VisibleImage ghost;
	
	//the cell that the ghost is in
	private Cell cell;
	
	//the position of the ghost
	private Position pos;
	
	//the current direction of the ghost
	private Direction curDir;
	
	//the maze to move around
	private Maze aMaze;
	
	//the pacman for the ghost to kill
	private PacMan aPacMan;
	
	//the controller of the game
	private PacmanController game;
	
	//create a ghost at a random empty cell in the maze 
	public Ghost(Image image, PacMan pacman, Maze maze, PacmanController controller, DrawingCanvas canvas) {
		//initialize the variables
		game = controller;
		aPacMan = pacman;
		aMaze = maze;
		
		//start the current direction as up
		curDir = Direction.UP;
		
		//find a random empty cell from the maze
		cell = maze.randomEmptyCell();
		
		//get the position of the cell
		pos = cell.getPosition();
		
		//initialize the ghost at the location of the cell
		ghost = new VisibleImage(image, maze.getLocOfCell(pos), canvas);
		start();
	}

	//while the pacman is alive, move the ghost randomly around the maze
	public void run() {
		//check if the pacman is alive
	     while (aPacMan.isLiving()) {
	    	 
	    	 //declare the new direction and new position of the ghost
	    	 Direction newDirection = curDir.randomDirection();
	    	 if (pos.getRow() == 10 && pos.getCol() == 0 && curDir == Direction.LEFT) {
					ghost.moveTo(aMaze.getLocOfCell(new Position(10, 18)));
					pos = new Position(10, 18);
					curDir = Direction.LEFT;
				}
				if (pos.getRow() == 10 && pos.getCol() == 18 && curDir == Direction.RIGHT) {
					ghost.moveTo(aMaze.getLocOfCell(new Position(10, 0)));
					pos = new Position(10, 0);
					curDir = Direction.RIGHT;
				}
             Position newPos = pos.translate(newDirection);
             
             //ensure that the ghost does not go through walls or turn around
             while (newDirection.isOpposite(curDir) || aMaze.containsWall(newPos)) {
            	 newDirection = curDir.randomDirection();
            	 newPos = pos.translate(newDirection);
             }
             //set the new direction and position to the current direction and position
             curDir = newDirection;
             pos = newPos;
             
			 //smooth motion for the ghost between cells (EC)
             for (int i = 0; i < STEPS; i++) {
           	  	ghost.move(curDir.getXchange()*Cell.CELL_WIDTH/STEPS, curDir.getYchange()*Cell.CELL_HEIGHT/STEPS);
           	  	Ghost.pause(PAUSE_TIME);
             }
             //if the ghost runs into pacman, the player loses the game
             if (aPacMan.overlaps(ghost)) {
            	 game.lose();
           }
       }
	}
	
	//method to remove the ghost from the canvas
	public void removeFromCanvas() {
		ghost.removeFromCanvas();
	}
}