package org.example;

import io.moquette.broker.Server;
import io.moquette.broker.config.MemoryConfig;

import java.util.Properties;

public class Broker {
    public static void main(String[] args) {
        Server mqttBroker = new Server();
        Properties configProps = new Properties();

        // org.example.Broker will run at this IPv4 address and at this port.
        configProps.setProperty("host", "0.0.0.0"); // Listening on all network interfaces
        configProps.setProperty("port", "1883");   // MQTT default port

        try {
            mqttBroker.startServer(new MemoryConfig(configProps));
            System.out.println("MQTT org.example.Broker started on 10.30.181.166:1883");

            // Stop broker while application is closed
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                mqttBroker.stopServer();
                System.out.println("MQTT org.example.Broker stopped.");
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
