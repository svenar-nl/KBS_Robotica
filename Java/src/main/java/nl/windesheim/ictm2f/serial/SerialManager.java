package nl.windesheim.ictm2f.serial;

import java.util.ArrayList;
import java.util.List;

import com.fazecast.jSerialComm.SerialPort;

public class SerialManager {

    private int DEFAULT_BAUD = 9600; // 115200

    private SerialPort currentPort;

    private boolean stateHasRX, stateHasTX;

    public SerialManager() {
    }

    public List<String> getAvailablePorts() {
        List<String> portPaths = new ArrayList<>();

        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            portPaths.add(port.getSystemPortPath());
        }

        return portPaths;
    }

    public boolean isConnected() {
        return this.currentPort != null && this.currentPort.isOpen();
    }

    public void connect(String portDescriptor) {
        if (isConnected()) {
            disconnect();
        }

        this.currentPort = SerialPort.getCommPort(portDescriptor);
        this.currentPort.setBaudRate(DEFAULT_BAUD);
        this.currentPort.openPort();
        this.currentPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 100);
    }

    public void disconnect() {
        if (!isConnected()) {
            return;
        }

        this.currentPort.closePort();
        this.currentPort = null;
    }

    public SerialPort getPort() {
        return this.currentPort;
    }

    public String read() {
        if (!isConnected()) {
            return "";
        }

        String data = "";

        this.stateHasRX = true;
        return data;
    }

    public void write(String data) {
        data = data.endsWith("\n") ? data : data + "\n";
        if (!isConnected()) {
            return;
        }

        this.currentPort.writeBytes(data.getBytes(), data.length());
        this.stateHasTX = true;
    }

    public boolean pingRX() {
        boolean ret = this.stateHasRX;
        this.stateHasRX = false;
        return ret;
    }

    public boolean pingTX() {
        boolean ret = this.stateHasTX;
        this.stateHasTX = false;
        return ret;
    }
}
