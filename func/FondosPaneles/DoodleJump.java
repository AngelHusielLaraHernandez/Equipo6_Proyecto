package func.FondosPaneles;
import javax.swing.*;
import func.PanelConFondo;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class DoodleJump {
    private JFrame frame;
    private FondoPanel fondoPanel;
    private PantallaInicio pantallaInicio;
    private static int puntuacion = 0;
    private String nombreCarpetamusic = "Music";

    public DoodleJump() {
        crearYMostrarGUI();
    }

    public JFrame getFrame() {
        return frame;
    }

    private void crearYMostrarGUI() {
        frame = new JFrame("Doodle Jump");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mostrarPantallaInicio();
        frame.setSize(450, 800);
        frame.setVisible(true);
    }

    public void mostrarPantallaInicio() {
        PanelConFondo panelConFondo = new PanelConFondo("Fondosmenu", "Fondo_menu.png");
        pantallaInicio = new PantallaInicio(this);
        panelConFondo.setLayout(new BorderLayout());
        panelConFondo.add(pantallaInicio, BorderLayout.CENTER);
        frame.setContentPane(panelConFondo);
        frame.revalidate();
    }

    void iniciarJuego() {
        puntuacion = 0;
        fondoPanel = new FondoPanel(this);
        frame.setContentPane(fondoPanel);
        fondoPanel.requestFocusInWindow();
        frame.revalidate();
    }

    static void aumentarPuntuacion() {
        puntuacion++;
    }

    static int getPuntuacion() {
        return puntuacion;
    }

    private String obtenerRutaMusica(String carpeta, String nombreArchivo) {
        return carpeta + File.separator + nombreArchivo;
    }
    
    public void reproducirMusicaDeFondo() {
        try {
            String rutaMusicFinal = obtenerRutaMusica(nombreCarpetamusic, "musica_fondo.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(rutaMusicFinal));
            Clip clipMusica = AudioSystem.getClip();
            clipMusica.open(audioInputStream);
            FloatControl volumen = (FloatControl) clipMusica.getControl(FloatControl.Type.MASTER_GAIN);
            volumen.setValue(-20.0f);
            clipMusica.loop(Clip.LOOP_CONTINUOUSLY);
            clipMusica.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}