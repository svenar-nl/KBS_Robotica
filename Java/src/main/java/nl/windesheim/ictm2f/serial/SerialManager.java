package nl.windesheim.ictm2f.serial;

import nl.windesheim.ictm2f.util.OSManager.OS;

public class SerialManager {

    public SerialManager(OS os) {
        switch (os) {
            case WINDOWS:
                break;

            case SOLARIS:
            case BSD:
            case LINUX:
                break;

            case MAC:
                break;

            default:
                break;
        }
    }

}
