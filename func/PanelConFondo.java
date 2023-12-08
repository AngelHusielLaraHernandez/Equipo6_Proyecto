package func;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PanelConFondo extends JPanel {
    private BufferedImage imagenFondo;

    public PanelConFondo(String carpeta, String nombreArchivo) {
        try {
            String rutaCompleta = carpeta + File.separator + nombreArchivo;
            imagenFondo = ImageIO.read(new File(rutaCompleta));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}