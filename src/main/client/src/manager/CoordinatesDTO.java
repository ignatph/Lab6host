package manager;

import java.io.Serializable;

public class CoordinatesDTO implements Serializable {
    private float x;
    private float y;

    // Геттеры и сеттеры
    public float getX() { return x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return y; }
    public void setY(float y) { this.y = y; }
}
