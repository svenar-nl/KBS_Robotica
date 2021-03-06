package nl.windesheim.ictm2f.util;

public class OSManager {
    public enum OS {
        WINDOWS, LINUX, MAC, SOLARIS, BSD
    };

    private static OS os = null;

    public static OS getOS() {
        if (os == null) {
            String operSys = System.getProperty("os.name").toLowerCase();
            if (operSys.contains("win")) {
                os = OS.WINDOWS;
            } else if (operSys.contains("nix") || operSys.contains("nux")
                    || operSys.contains("aix")) {
                os = OS.LINUX;
            } else if (operSys.contains("mac")) {
                os = OS.MAC;
            } else if (operSys.contains("sunos")) {
                os = OS.SOLARIS;
            } else if (operSys.contains("bsd")) {
                os = OS.BSD;
            }
        }
        return os;
    }
}
