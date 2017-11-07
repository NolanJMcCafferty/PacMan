import objectdraw.RandomIntGenerator;

public class Direction {
    public static final Direction UP = new Direction(0, -1);
    public static final Direction DOWN = new Direction(0, 1);
    public static final Direction LEFT = new Direction(-1, 0);
    public static final Direction RIGHT = new Direction(1, 0);
    public static final Direction NONE = new Direction(0, 0);
    private int xchange;
    private int ychange;
    private RandomIntGenerator dirGenerator = new RandomIntGenerator(1, 4);

    private Direction(int xchange, int ychange) {
        this.xchange = xchange;
        this.ychange = ychange;
    }

    public int getXchange() {
        return xchange;
    }

    public int getYchange() {
        return ychange;
    }

    public boolean isOpposite(Direction newDirection) {
        if (xchange == - newDirection.getXchange() && ychange == - newDirection.getYchange()) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "(" + this.xchange + ", " + this.ychange + ")";
    }

    public Direction randomDirection() {
        switch (dirGenerator.nextValue()) {
            case 1: {
                return UP;
            }
            case 2: {
                return DOWN;
            }
            case 3: {
                return LEFT;
            }
        }
        return RIGHT;
    }
}