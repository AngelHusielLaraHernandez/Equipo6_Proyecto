package func;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Personaje {
    private int x;
    private int y;
    public final int ancho = 35;
    public final int alto = 30;
    private int velocidadY = -1;
    private final int gravedad = 1;
    private final int poderSalto = -22;
    private BufferedImage imagenNormal;
    private BufferedImage imagenDerecha;
    private BufferedImage imagenIzquierda;
    public BufferedImage imagenActual;
    private static int personajeSeleccionado = 1;
    String carpetaImagenes = "Image";
    public BufferedImage getImagenNormal() {
        return imagenNormal;
    }

    private String obtenerRutaImagen(String carpeta, String nombreArchivo) {
        return carpeta + File.separator + nombreArchivo;
    }
    public Personaje(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocidadY = 0;
        cargarImagenes();
        imagenActual = imagenNormal;
    }

    private void cargarImagenes() {
        try {
            String prefijo = "personaje";
            if (personajeSeleccionado > 1) {
                prefijo += personajeSeleccionado;
            }
            imagenNormal = ImageIO.read(new File(obtenerRutaImagen(carpetaImagenes, prefijo + ".png")));
            imagenDerecha = ImageIO.read(new File(obtenerRutaImagen(carpetaImagenes, prefijo + "_derecha.png")));
            imagenIzquierda = ImageIO.read(new File(obtenerRutaImagen(carpetaImagenes, prefijo + "_izquierda.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void seleccionarPersonaje(int seleccion) {
        personajeSeleccionado = seleccion;
    }

    public void dibujar(Graphics g) {
        g.drawImage(imagenActual, x, y, null);
    }

    public void mover(int keyCode, int anchoPanel) {
        if (keyCode == KeyEvent.VK_LEFT && x > 0) {
            x -= 25;
            imagenActual = imagenIzquierda;
        } else if (keyCode == KeyEvent.VK_RIGHT && x < anchoPanel - ancho) {
            x += 25;
            imagenActual = imagenDerecha;
        }
    }

    public void actualizar() {
        velocidadY += gravedad;
        y += velocidadY;
        if (y >= 800 - alto) {
            y = 800 - alto;
        }
    }

    public void saltar() {
        velocidadY = poderSalto;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
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

    public int getVelocidadY() {
        return velocidadY;
    }
}
