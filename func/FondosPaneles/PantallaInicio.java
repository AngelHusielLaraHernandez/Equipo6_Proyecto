package func.FondosPaneles;
import javax.swing.*;
import func.PanelConFondo;
import func.Personaje;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PantallaInicio extends JPanel {
    JButton botonIniciar, botonSeleccionarPersonaje, botonCambiarFondo;
    private DoodleJump doodleJump; 
    public PantallaInicio(DoodleJump doodleJump) {
        this.doodleJump = doodleJump;
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 0, 10, 0);

        botonIniciar = new JButton("Iniciar Juego");
        botonIniciar.addActionListener(e -> doodleJump.iniciarJuego());
        add(botonIniciar, gbc);

        botonSeleccionarPersonaje = new JButton("Seleccionar Personaje");
        botonSeleccionarPersonaje.addActionListener(e -> mostrarSeleccionPersonaje());
        add(botonSeleccionarPersonaje, gbc);

        botonCambiarFondo = new JButton("Cambiar Fondo");
        botonCambiarFondo.addActionListener(e -> mostrarCambioFondo());
        add(botonCambiarFondo, gbc);
    }

    private void mostrarSeleccionPersonaje() {
        JDialog dialogoSeleccion = new JDialog(doodleJump.getFrame(), "Seleccionar Personaje", true);
        PanelConFondo panelConFondo = new PanelConFondo("Fondosmenu","fondo_personajes.png");
        panelConFondo.setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel();
        panelCentral.setOpaque(false);
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));

        JLabel leyenda = new JLabel("Selecciona un personaje con cual jugar");
        leyenda.setAlignmentX(Component.CENTER_ALIGNMENT);
        leyenda.setForeground(Color.WHITE);
        panelCentral.add(leyenda);
        String RutaImageTemp1= obtenerRutaImagen("Image", "personaje.png");
        String RutaImageTemp2= obtenerRutaImagen("Image", "personaje2.png");
        String RutaImageTemp3= obtenerRutaImagen("Image", "personaje3.png");
        String RutaImageTemp4= obtenerRutaImagen("Image", "personaje4.png");
        ImageIcon vistaPrevia1 = cargarImagenRedimensionada(RutaImageTemp1, 100, 100);
        ImageIcon vistaPrevia2 = cargarImagenRedimensionada(RutaImageTemp2, 100, 100);
        ImageIcon vistaPrevia3 = cargarImagenRedimensionada(RutaImageTemp3, 100, 100); // Nuevo personaje 3
        ImageIcon vistaPrevia4 = cargarImagenRedimensionada(RutaImageTemp4, 100, 100); // Nuevo personaje 4

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new FlowLayout());

        JButton[] botonesPersonaje = new JButton[4];
        for (int i = 0; i < botonesPersonaje.length; i++) {
            ImageIcon vistaPrevia = i == 0 ? vistaPrevia1 : (i == 1 ? vistaPrevia2 : (i == 2 ? vistaPrevia3 : vistaPrevia4));
            botonesPersonaje[i] = new JButton(vistaPrevia);
            int seleccion = i + 1;
            botonesPersonaje[i].addActionListener(e -> {
                Personaje.seleccionarPersonaje(seleccion);
                dialogoSeleccion.dispose();
            });
            panelBotones.add(botonesPersonaje[i]);
        }

        panelCentral.add(panelBotones);
        panelConFondo.add(panelCentral, BorderLayout.CENTER);

        dialogoSeleccion.setContentPane(panelConFondo);
        dialogoSeleccion.pack();
        dialogoSeleccion.setSize(400, 800);
        dialogoSeleccion.setLocationRelativeTo(doodleJump.getFrame());
        dialogoSeleccion.setVisible(true);
    }

    private void mostrarCambioFondo() {
        JDialog dialogoCambioFondo = new JDialog(doodleJump.getFrame(), "Cambiar Fondo", true);
        dialogoCambioFondo.setLayout(new BoxLayout(dialogoCambioFondo.getContentPane(), BoxLayout.Y_AXIS));

        JLabel leyenda = new JLabel("Selecciona el fondo del juego");
        leyenda.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialogoCambioFondo.add(leyenda);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        String[] rutasFondos = new String[3];
        rutasFondos[0] = obtenerRutaImagen("Fondosmenu", "fondo1.png");
        rutasFondos[1] = obtenerRutaImagen("Fondosmenu", "fondo2.png");
        rutasFondos[2] = obtenerRutaImagen("Fondosmenu", "fondo3.png");
        for (String rutaFondo : rutasFondos) {
            ImageIcon iconoFondo = cargarImagenRedimensionada(rutaFondo, 250, 500);
            JButton botonFondo = new JButton(iconoFondo);
            botonFondo.addActionListener(e -> {
                FondoPanel.setImagenFondo(rutaFondo);
                dialogoCambioFondo.dispose();
            });
            panelBotones.add(botonFondo);
        }

        dialogoCambioFondo.add(panelBotones);
        dialogoCambioFondo.pack();
        dialogoCambioFondo.setSize(1000, 600);
        dialogoCambioFondo.setLocationRelativeTo(doodleJump.getFrame());
        dialogoCambioFondo.setVisible(true);
    }

    private ImageIcon cargarImagenRedimensionada(String ruta, int ancho, int alto) {
        try {
            BufferedImage imgOriginal = ImageIO.read(new File(ruta));
            Image imgRedimensionada = imgOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imgRedimensionada);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String obtenerRutaImagen(String carpeta, String nombreArchivo) {
        return carpeta + File.separator + nombreArchivo;
    }
}