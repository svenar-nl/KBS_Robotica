package nl.windesheim.ictm2f;

import nl.windesheim.ictm2f.gui.GUIManager;
import nl.windesheim.ictm2f.gui.Splash;
import nl.windesheim.ictm2f.serial.SerialManager;
import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.util.Dimension;
import nl.windesheim.ictm2f.util.Logger;
import nl.windesheim.ictm2f.util.OSManager;
import nl.windesheim.ictm2f.util.Solver;

public class Main {

    private Dimension screenDimension = new Dimension(800, 600);

    private static Main instance;

    private SerialManager serialManager;
    private Solver solver;
    private GUIManager guiManager;
    private Splash splash;

    public static void main(String[] args) {
        instance = new Main();
        instance.setup();
    }

    public void setup() {
        this.splash = new Splash();

        Logger.logToSysOut(true);

        this.serialManager = new SerialManager();
        this.solver = new Solver();

        this.guiManager = new GUIManager(this.screenDimension);
        this.guiManager.setTitle("KBS Robotica");
        this.guiManager.changeTheme(GUIThemes.Theme.DARK);
        this.guiManager.display();

        Logger.info("Running on OS: " + OSManager.getOS());
        Logger.info(String.format("Found %s serial port%s", this.serialManager.getAvailablePorts().size(),
                this.serialManager.getAvailablePorts().size() == 1 ? "" : "s"));

        this.splash.close();
    }

    public SerialManager getSerialManager() {
        return this.serialManager;
    }
    public Solver getSolver(){
        return this.solver;
    }

    public static Main getInstance() {
        return instance;
    }
}
