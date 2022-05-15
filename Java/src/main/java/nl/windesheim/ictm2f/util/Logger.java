package nl.windesheim.ictm2f.util;

import nl.windesheim.ictm2f.gui.GUIManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Logger {

    private static boolean logToSysOut = false;

    private static List<String> log = new ArrayList<String>();

    public Logger() {

    }

    public static void logToSysOut(boolean doLog) {
        logToSysOut = doLog;
    }

    /**
     * Log a message with level 'info'
     * 
     * @param message
     */
    public static void info(String message) {
        log("INFO", message);
    }

    /**
     * Log a message with level 'warning'
     * 
     * @param message
     */
    public static void warning(String message) {
        log("WARN", message);
    }

    /**
     * Log a message with level 'severe'
     * 
     * @param message
     */
    public static void severe(String message) {
        log("SEVERE", message);
    }

    private static void log(String type, String message) {
        log.add(String.format("%s [%s] %s", getPrefix(), type, message));

        if (logToSysOut) {
            switch (type.toLowerCase()) {
                default:
                case "info":
                    System.out.println(String.format("[%s] %s", type, message));
                    break;

                case "warn":
                case "severe":
                    System.err.println(String.format("[%s] %s", type, message));
                    break;
            }
        }

        GUIManager.getStatusPanel().repaint();
    }

    public static List<String> getLog() {
        return log;
    }

    /**
     * Basic exception message logger
     * 
     * @param exception
     */
    public static void exception(Exception exception) {
        warning("===-------------------------===");
        warning("An exception occured!");
        warning("");
        warning("Exception:");
        severe(exception.getMessage());
        warning("===-------------------------===");
    }

    public static String getPrefix(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return String.format("%s >", formatter.format(date));
    }
}
