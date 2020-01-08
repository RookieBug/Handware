package xyz.tulling.esp8266;

import xyz.tulling.esp8266.server.HardwareServer;

public class App {

    public static void main(String[] args) {
        new HardwareServer(8080).start();
    }
}
