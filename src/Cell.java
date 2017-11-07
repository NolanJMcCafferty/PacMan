import java.awt.Color;
import objectdraw.DrawableInterface;
import objectdraw.DrawingCanvas;
import objectdraw.FilledOval;
import objectdraw.FilledRect;

public class Cell {
		
	   //constants for the size of the cell
	   public static final double CELL_WIDTH = 25;
	   public static final double CELL_HEIGHT = 27;
	   
	   //constants for the size of the food
	   private static final double INSTEP = 7.5;
	   private static final int FOOD_SIZE = 10;
	   
	   //declare the object that can be cell
	   private DrawableInterface cell;
	   
	   //the type of cell
	   private int aType;
	   
	   //the position of the cell
	   private Position pos;

	//create a cell with contents depending on which type of cell it is
    public Cell(int type, double row, double column, DrawingCanvas canvas) {
    	//initialize the position of the cell
    	pos = new Position((int)row, (int)column);
    	
    	aType = type;
    	
    	//if the cell is a wall, make it a black square
    	if (type == 1) {
    		cell = new FilledRect(column*CELL_WIDTH, row*CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT, canvas);
    	}
    	//if the cell is not a wall, make it white with a circular food in the middle
    		else {
    			cell = new FilledOval(column*CELL_WIDTH + INSTEP, row*CELL_HEIGHT + INSTEP, FOOD_SIZE, FOOD_SIZE, canvas);
    			cell.setColor(Color.CYAN);
    		}
    	}
    
    //method to determine if the cell is a wall
    public boolean isWall() {
    	 if (aType == 1) {
             return true;
         }
         return false;
    }
    
    //method to get the position of the cell
    public Position getPosition() {
    	return pos;
    }
    
    //method to change the cell if it is the first visit
    public boolean newVisit() {
    	if (aType == 0) {		
    		aType = 2;
    		cell.hide();
    		return true;
    	}
    	else {
    		return false;
    	}
    	
    }
    
    //method to change the cell when it has been counted for the score (EC)
    public boolean newScore() {
    	if (aType == 2) {
    		aType = 3;
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    //method to reset the cell to its original contents
    public void reset() {
    	if (aType == 3 || aType == 2) {
    		cell.show();
    		aType = 0;
    	}
    }
   
}