package nl.windesheim.ictm2f.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import nl.windesheim.ictm2f.util.Logger;

public class ConfigManager {

    private final DumperOptions yamlOptions = new DumperOptions();
    private final LoaderOptions loaderOptions = new LoaderOptions();
    private final Representer yamlRepresenter = new Representer();
    private final Yaml yaml = new Yaml(new Constructor(), yamlRepresenter, yamlOptions, loaderOptions);

    private String applicationDirectory = "RobotManager";
    private String baseDataPath = System.getProperty("user.home") + File.separator + applicationDirectory
            + File.separator;
    private String configFileName = "config.yml";
    private File configFile;

    private String storageMethod = "SQLITE";

    public ConfigManager() {
        load();
    }

    public void load() {
        Logger.info("Loading configuration!");

        File configDir = new File(this.baseDataPath);
        this.configFile = new File(configDir, this.configFileName);

        if (!configDir.exists()) {
            Logger.info("Config directory doesn't exist! Creating...");
            configDir.mkdirs();
        }

        if (!this.configFile.exists()) {
            Logger.info("Config file doesn't exist! Creating...");
            save();
        }

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            InputStream inputStream = new FileInputStream(this.configFile);
            data = this.yaml.load(inputStream);
            if (data == null) {
                data = new HashMap<String, Object>();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (data.containsKey("storage-method")) {
            this.storageMethod = String.valueOf(data.get("storage-method"));
        }
    }

    public void save() {
        Map<String, Object> data = new HashMap<>();
        data.put("storage-method", storageMethod);

        try {
            this.yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            this.yamlRepresenter.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

            // this.yamlOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.DOUBLE_QUOTED);
            // this.yamlRepresenter.setDefaultScalarStyle(DumperOptions.ScalarStyle.DOUBLE_QUOTED);

            PrintWriter writer = new PrintWriter(this.configFile);
            this.yaml.dump(data, writer);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getStoragelocation() {
        return this.baseDataPath;
    }

    public String getStorageMethod() {
        return this.storageMethod;
    }

    public void setStorageMethod(String storageMethod) {
        this.storageMethod = storageMethod;
    }
}
