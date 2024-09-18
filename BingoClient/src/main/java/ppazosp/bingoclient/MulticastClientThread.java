package ppazosp.bingoclient;

import javafx.application.Platform;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastClientThread extends Thread{

    MainViewController windowController;
    MainApplication application;

    final String multicastAddress = "225.0.0.100";
    final int port = 6789;

    public MulticastClientThread(String name, MainViewController windowController, MainApplication application) {
        super(name);
        this.windowController = windowController;
        this.application = application;
    }

    @Override
    public void run() {

        try (MulticastSocket socket = new MulticastSocket(port)) {
            InetAddress group = InetAddress.getByName(multicastAddress);
            socket.joinGroup(group);

            byte[] buffer = new byte[8];

            while (!application.isBingo()) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Ball: " + message);

                Platform.runLater(() -> {
                    if (windowController.update(message)) {
                        bingo();
                    }
                });
            }

        } catch (IOException e) {
            System.out.println("Error receiving ball: " + e.getMessage());
        }
    }

    public void bingo()
    {
        application.bingo();

        String message = "bingo";

        try (DatagramSocket socket = new DatagramSocket()) {
            byte[] buffer = message.getBytes();

            InetAddress group = InetAddress.getByName(multicastAddress);

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
            socket.send(packet);

        } catch (IOException e) {
            System.out.println("Error sending bingo: " + e.getMessage());
        }

    }
}
