import javax.swing.SwingUtilities;
import func.FondosPaneles.DoodleJump;

public class Juego{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DoodleJump juego = new DoodleJump();
            juego.reproducirMusicaDeFondo();
        });
    }
}
