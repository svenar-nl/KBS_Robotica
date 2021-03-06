package nl.windesheim.ictm2f;

import nl.windesheim.ictm2f.gui.GUIManager;
import nl.windesheim.ictm2f.gui.Splash;
import nl.windesheim.ictm2f.order.OrderManager;
import nl.windesheim.ictm2f.pathsolver.Solver;
import nl.windesheim.ictm2f.serial.SerialManager;
import nl.windesheim.ictm2f.storage.CachedData;
import nl.windesheim.ictm2f.storage.ConfigManager;
import nl.windesheim.ictm2f.storage.IDatabase;
import nl.windesheim.ictm2f.storage.SQLite;
import nl.windesheim.ictm2f.storage.MySQL;
import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.util.Dimension;
import nl.windesheim.ictm2f.util.Logger;
import nl.windesheim.ictm2f.util.OSManager;

public class Main {

    private Dimension screenDimension = new Dimension(800, 600);

    private static Main instance;

    private SerialManager serialManager;
    private Solver solver;
    private GUIManager guiManager;
    private Splash splash;
    private CachedData cachedData;
    private IDatabase database;
    private ConfigManager configManager;
    private OrderManager orderManager;

    public static void main(String[] args) {
        instance = new Main();
        instance.setup();
    }

    public void setup() {
        this.splash = new Splash();

        Logger.logToSysOut(true);

        this.serialManager = new SerialManager();
        this.solver = new Solver();

        this.configManager = new ConfigManager();
        this.cachedData = new CachedData();

        this.guiManager = new GUIManager(this.screenDimension);
        this.guiManager.setTitle("KBS Robotica");
        this.guiManager.changeTheme(GUIThemes.Theme.DARK);
        this.guiManager.setResizable(false);
        this.guiManager.display();

        this.orderManager = new OrderManager();

        Logger.info("Running on OS: " + OSManager.getOS());
        Logger.info(String.format("Found %s serial port%s", this.serialManager.getAvailablePorts().size(),
                this.serialManager.getAvailablePorts().size() == 1 ? "" : "s"));

        switch (String.valueOf(this.configManager.get("storage-method")).toLowerCase()) {
            case "mysql":
                this.database = new MySQL();
                break;

            case "sqlite":
                this.database = new SQLite(String.valueOf(this.getConfigManager().get("sqlite-connection-dbfile")));
                break;

            default:
                Logger.severe(
                        String.format("Unknown DB type: %s. Using SQLite",
                                String.valueOf(this.configManager.get("storage-method"))));
                this.configManager.set("storage-method", "SQLite");
                this.configManager.save();
                this.database = new SQLite(String.valueOf(this.getConfigManager().get("sqlite-connection-dbfile")));
                break;
        }
        Logger.info(String.format("Loading database (%s)...", this.database.getType()));
        if (this.database.connect()) {
            Logger.info("Database connected!");
        } else {
            System.exit(-1);
        }
        this.cachedData.setData(this.database.load());

        if (!this.cachedData.hasKey("theme")) {
            this.guiManager.changeTheme(GUIThemes.Theme.DARK);
            this.cachedData.set("theme", this.guiManager.getTheme().getThemeName());
            this.database.save(this.cachedData.getData());
        }
        this.guiManager.changeTheme(GUIThemes.getTheme(this.cachedData.getString("theme")));

        this.orderManager.load();

        this.splash.close();
    }

    public SerialManager getSerialManager() {
        return this.serialManager;
    }

    public Solver getSolver() {
        return this.solver;
    }

    public CachedData getCachedData() {
        return this.cachedData;
    }

    public IDatabase getDatabase() {
        return this.database;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public GUIManager getGuiManager() {
        return this.guiManager;
    }

    public OrderManager getOrderManager() {
        return this.orderManager;
    }

    public static Main getInstance() {
        return instance;
    }
}
