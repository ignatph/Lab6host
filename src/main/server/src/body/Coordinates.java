package body;
/**
 * Model of Coordinates, contains getters/setter for fields of clas
 */
public class Coordinates {
    private float x;
    private float y;

    /**
     *
     * Constructs a Coordinates object with specified x and y values.
     *
     * @param x The x-coordinate (must be > -848 and cannot be null)
     * @param y The y-coordinate (cannot be null)
     * @throws IllegalArgumentException If x ≤ -848
     * @throws NullPointerException If x or y is null
     */
    public Coordinates(float x, float y) {
        setX(x);
        setY(y);
    }


    public Coordinates() {
    }


    public float getX() {
        return x;
    }


    public void setX(Float x) {
        if (x <= -848) {
            throw new IllegalArgumentException("Значение x должно быть больше -848.");
        }
        this.x = x;
    }


    public float getY() {
        return y;
    }


    public void setY(Float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates: " +
                "x = " + x +
                ", y = " + y;
    }
}