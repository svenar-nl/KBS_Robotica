package nl.windesheim.ictm2f;

import nl.windesheim.ictm2f.gui.GUIManager;
import nl.windesheim.ictm2f.gui.Splash;
import nl.windesheim.ictm2f.util.Dimension;

public class Main {

    private static Main instance;
    private GUIManager guiManager;
    private Splash splash;
    private Dimension screenDimension = new Dimension(800, 600);

    public static void main(String[] args) {
        instance = new Main();
        instance.setup();
    }

    public void setup() {
        this.splash = new Splash();

        this.guiManager = new GUIManager(this.screenDimension);
        this.guiManager.setTitle("KBS Robotica");
        this.guiManager.setVisible(true); // TODO: not the best place for this tbh

        this.splash.close();
    }
}
