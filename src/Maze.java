import objectdraw.DrawingCanvas;
import objectdraw.Location;
import objectdraw.RandomIntGenerator;


public class Maze {
	
	//array of cells
    private Cell[][] maze;
    
    //declare the drawing canvas 
    private DrawingCanvas aCanvas;
    
    //declare the random number generators 
    private RandomIntGenerator rowGenerator;
    private RandomIntGenerator colGenerator;
   
    //declare the number of empty cells, which starts at 185
    private int numEmpty = 185;
    
    //declare the PacManController to win and lose the game
    private PacmanController aGame;
    
    //create the maze that the game is played on
    public Maze(int boardHeight, int boardWidth, DrawingCanvas canvas, PacmanController game) {
        //initialize the cell array with the dimensions passed in by the constructor
    	maze = new Cell[boardHeight][boardWidth];
    	        
        //initialize the random int generators with the board dimensions
        rowGenerator = new RandomIntGenerator(0, boardHeight - 1);
        colGenerator = new RandomIntGenerator(0, boardWidth - 1);
        
        aGame = game;
        
        aCanvas = canvas;
    }
    
    //method to add a cell to the cell array
    public void addCell(int type, int row, int col) {
        maze[row][col] = new Cell(type, row, col, aCanvas);
    }
    
    //method to check if the cell is a wall at a position
    public boolean containsWall(Position p) {
    	return maze[p.getRow()][p.getCol()].isWall();
    }
    
    //method to get the location of a cell at a position
    public Location getLocOfCell(Position p) {
    	return new Location (Cell.CELL_WIDTH*p.getCol(), Cell.CELL_HEIGHT*p.getRow());
    }
    
    //method to get a random empty position in the maze for the ghosts
    public Cell randomEmptyCell() {
    	int row = rowGenerator.nextValue();
    	int col = colGenerator.nextValue();
    	
    	//while the cell is a wall, generate another cell
    	while (maze[row][col].isWall()) {
    		row = rowGenerator.nextValue();
    		col = colGenerator.nextValue();
    	}
    	return maze[row][col];
    }
    
    //method to change the number of empty cells
    public void visitCell(Position p) {
    	//if it is the first visit to the cell
    	if (maze[p.getRow()][p.getCol()].newVisit()) {
    		//decrease the number of empty cells by 1
    		numEmpty--;
    	}
    	//determine if the player has won the game
    	if (numEmpty == 0) {
    		aGame.win();
    	}
    }
    
    //method to reset the maze to its original contents
    public void resetMaze(int n) {
    	  numEmpty = n;
    	  
    	  //reset each cell to its original contents
          for (int row = 0; row < MazeArray.map.length; row++) {
              for (int col = 0; col < MazeArray.map[row].length; col++) {
                      maze[row][col].reset();
              }     
          }
    }

    //method to return the cell at a specific row and column
    public Cell getCell(int row, int col) {
    	return maze[row][col];
    }
}