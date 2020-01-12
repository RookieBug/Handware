package xyz.tulling.hardware;

import xyz.tulling.hardware.server.HardwareServer;

public class App {

    public static void main(String[] args) {
        new HardwareServer(8080).start();
    }
}
