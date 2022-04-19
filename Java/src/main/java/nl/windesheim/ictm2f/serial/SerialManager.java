package nl.windesheim.ictm2f.serial;

import java.util.ArrayList;
import java.util.List;

import com.fazecast.jSerialComm.SerialPort;

public class SerialManager {

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

}
