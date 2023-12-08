package func.FondosPaneles;
import javax.swing.*;
import func.Personaje;
import func.Plataforma;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

class FondoPanel extends JPanel implements KeyListener {
    private final int anchoPanel = 450;
    private final int altoPanel = 800;
    private ArrayList<Plataforma> plataformas;
    private final Random random = new Random();
    private Personaje personaje;
    private boolean juegoEnProgreso;
    private static BufferedImage imagenFondo;
    private BufferedImage imagenPlataforma;
    private BufferedImage imagenFondoFinal;
    private Clip sonidoSalto;
    private JLabel contadorLabel;
    private int contadorTiempo = 3;
    private Timer timer;
    private DoodleJump doodleJump;
    private String nombreCarpetaImagenes = "Fondosmenu";
    private static String nombreCarpetaImagenes3 = "Fondosmenu";
    private String nombreCarpetaImagenes2 = "Recursosjuego";
    private String nombreCarpetaMusica = "Music";
    private static int puntuacion;

        public FondoPanel(DoodleJump doodleJump) {
            this.doodleJump = doodleJump;
            crearPlataformas();
            personaje = new Personaje(anchoPanel / 2, altoPanel / 2);
            cargarRecursos();
            setFocusable(true);
            addKeyListener(this);
            juegoEnProgreso = false;
            timer = new Timer(17, e -> actualizarJuego());
            timer.start();
            iniciarCuentaRegresiva();
        }

    private void crearPlataformas() {
        plataformas = new ArrayList<>();
        int y = altoPanel - 50;
        while (y > 0) {
            int x = random.nextInt(anchoPanel - Plataforma.ANCHO);
            Plataforma plataforma = new Plataforma(x, y);
            plataformas.add(plataforma);
            y -= (Plataforma.ALTO + 100);
        }
    }

    private String obtenerRutaImagen(String carpeta, String nombreArchivo) {
        return carpeta + File.separator + nombreArchivo;
    }

    private static String obtenerRutaImagen2(String nombreArchivo) {
        return nombreCarpetaImagenes3 + File.separator + nombreArchivo;
    }
    
    private static String imagenFondoRuta = obtenerRutaImagen2("fondo1.png");

    private void cargarRecursos() {
        try {
            String rutaImagenFondoFinal = obtenerRutaImagen(nombreCarpetaImagenes, "fondo_final.png");
            imagenFondoFinal = ImageIO.read(new File(rutaImagenFondoFinal));
            rutaImagenFondoFinal = obtenerRutaImagen(nombreCarpetaImagenes2, "Plataforma.png");
            imagenPlataforma = ImageIO.read(new File(rutaImagenFondoFinal));
            String rutaImagenMusicaFinal = obtenerRutaImagen(nombreCarpetaMusica, "saltosound.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(rutaImagenMusicaFinal));
            sonidoSalto = AudioSystem.getClip();
            sonidoSalto.open(audioInputStream);
            cargarFondo();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void iniciarCuentaRegresiva() {
        contadorLabel = new JLabel("3", SwingConstants.CENTER);
        contadorLabel.setFont(new Font("Arial", Font.BOLD, 48));
        contadorLabel.setForeground(Color.WHITE);
        contadorLabel.setBounds(0, altoPanel / 2 - 50, anchoPanel, 100);
        this.setLayout(null);
        this.add(contadorLabel);

        Timer contadorTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (contadorTiempo > 0) {
                    contadorLabel.setText(String.valueOf(--contadorTiempo));
                } else {
                    ((Timer)e.getSource()).stop();
                    remove(contadorLabel);
                    juegoEnProgreso = true;
                    personaje.saltar();
                    setFocusable(true);
                    requestFocusInWindow();
                }
                repaint();
            }
        });
        contadorTimer.setInitialDelay(0);
        contadorTimer.start();
    }

    public static void setImagenFondo(String ruta) {
        imagenFondoRuta = ruta;
        cargarFondo();
    }

    private static void cargarFondo() {
        try {
            imagenFondo = ImageIO.read(new File(imagenFondoRuta));
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
        if (juegoEnProgreso) {
            for (Plataforma plataforma : plataformas) {
                g.drawImage(imagenPlataforma, plataforma.getX(), plataforma.getY(), Plataforma.ANCHO, Plataforma.ALTO, this);
            }
            personaje.dibujar(g);
            dibujarPuntuacion(g);
        } else {
            if (puntuacion > 0) {
                g.drawImage(imagenFondoFinal, 0, 0, this.getWidth(), this.getHeight(), this);
                juegoTerminado(g);
            }
        }
    }

    private void dibujarPuntuacion(Graphics g) {
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + puntuacion, 20, 30);
    }

    private void juegoTerminado(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Juego Terminado. Puntuacion: " + puntuacion, 80, altoPanel / 2);
        SwingUtilities.invokeLater(() -> {
            juegoEnProgreso = false;
            timer.stop();
            mostrarBotonReinicio();
        });
    }

    private void mostrarBotonReinicio() {
        JButton botonReinicio = new JButton("Volver al menu");
        botonReinicio.setBounds(150, 450, 150, 30);
        botonReinicio.addActionListener(e -> {
            timer.stop();
            this.removeAll();
            puntuacion=0;
            doodleJump.mostrarPantallaInicio();
            this.revalidate();
            this.repaint();
        });
        this.setLayout(null);
        this.add(botonReinicio);
        this.revalidate();
        this.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (juegoEnProgreso) {
            personaje.mover(e.getKeyCode(), anchoPanel);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            personaje.imagenActual = personaje.getImagenNormal();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    private void checkColisiones() {
        for (Plataforma plataforma : plataformas) {
            if (personaje.getY() + personaje.alto < plataforma.getY() + Plataforma.ALTO &&
                personaje.getY() + personaje.alto + personaje.getVelocidadY() >= plataforma.getY() &&
                personaje.getX() + personaje.ancho > plataforma.getX() &&
                personaje.getX() < plataforma.getX() + Plataforma.ANCHO &&
                personaje.getVelocidadY() >= 0) {
                personaje.saltar();
                if (!plataforma.yaPuntuada) {
                    puntuacion++;
                    plataforma.yaPuntuada = true;
                }
                if (sonidoSalto != null && !sonidoSalto.isRunning()) {
                    sonidoSalto.setFramePosition(0);
                    sonidoSalto.start();
                }
            }
        }
    }
    
    public void actualizarJuego() {
        if (juegoEnProgreso) {
            personaje.actualizar();
            checkColisiones();
            plataformas.forEach(plataforma -> {
                if (personaje.getVelocidadY() < 0 && personaje.getY() < 300) {
                    plataforma.setY(plataforma.getY() - personaje.getVelocidadY());
                    if (plataforma.getY() > altoPanel) {
                        plataforma.setY(-Plataforma.ALTO);
                        plataforma.setX(random.nextInt(anchoPanel - Plataforma.ANCHO));
                        plataforma.yaPuntuada = false; 
                    }
                }
            });
            if (personaje.getY() + personaje.alto >= altoPanel) {
                juegoTerminado(getGraphics());
            }
        }
        repaint();
    }
}
