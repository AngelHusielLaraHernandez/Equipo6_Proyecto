package func;
import java.awt.*;

public class Plataforma {
    private int x;
    private int y;
    public static final int ANCHO = 60;
    public static final int ALTO = 20;
    public boolean yaPuntuada = false;

    public Plataforma(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ANCHO, ALTO);
    }
}
