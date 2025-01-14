package org.example;

import org.eclipse.paho.client.mqttv3.*;

public class Client {
    public static void main(String[] args) {
        // org.example.Broker address (IPv4 address of the computer of the org.example.Broker is on)
        String broker = "tcp://10.30.181.166:1883"; // IPv4 of org.example.Broker
        String clientId = "JavaClient";             // Unique client ID
        String topic = "test/topic";               // Topic to be used
        String messageContent = "Hello from Client!"; // The message.

        try {
            // Creating the MQTT Client
            MqttClient client = new MqttClient(broker, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);

            // Connect to broker
            System.out.println("Connecting to broker: " + broker);
            client.connect(options);
            System.out.println("Connected!");

            // Send Message
            MqttMessage message = new MqttMessage(messageContent.getBytes());
            message.setQos(2); // QoS level
            client.publish(topic, message);
            System.out.println("Message published: " + messageContent);

            // Listen to messages
            client.subscribe(topic, (receivedTopic, mqttMessage) -> {
                System.out.println("Message received:");
                System.out.println("\tTopic: " + receivedTopic);
                System.out.println("\tMessage: " + new String(mqttMessage.getPayload()));
            });

            // Time for the messages (10 secs here for example)
            Thread.sleep(10000);

            // Disconnect
            client.disconnect();
            System.out.println("Disconnected");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
