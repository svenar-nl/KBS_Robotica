package nl.windesheim.ictm2f;

import nl.windesheim.ictm2f.gui.GUIManager;
import nl.windesheim.ictm2f.gui.Splash;
import nl.windesheim.ictm2f.serial.SerialManager;
import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.util.Dimension;
import nl.windesheim.ictm2f.util.OSManager;

public class Main {

    private Dimension screenDimension = new Dimension(800, 600);

    private static Main instance;

    private SerialManager serialManager;
    private GUIManager guiManager;
    private Splash splash;

    public static void main(String[] args) {
        instance = new Main();
        instance.setup();
    }

    public void setup() {
        this.splash = new Splash();

        this.serialManager = new SerialManager(OSManager.getOS());

        this.guiManager = new GUIManager(this.screenDimension);
        this.guiManager.setTitle("KBS Robotica");
        this.guiManager.changeTheme(GUIThemes.Theme.DARK);
        this.guiManager.show();

        this.splash.close();
    }
}
