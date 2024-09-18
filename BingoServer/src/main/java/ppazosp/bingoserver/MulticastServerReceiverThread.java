package ppazosp.bingoserver;

import javafx.application.Platform;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastServerReceiverThread extends Thread {

    MainViewController windowController;
    MainApplication application;

    final String multicastAddress = "225.0.0.100";
    final int port = 6789;

    public MulticastServerReceiverThread(String name, MainViewController windowController, MainApplication application) {
        super(name);
        this.windowController = windowController;
        this.application = application;
    }

    @Override
    public void run() {

        while(!application.isBingo())
        {
            try (MulticastSocket socket = new MulticastSocket(port)) {
                InetAddress group = InetAddress.getByName(multicastAddress);
                socket.joinGroup(group);

                byte[] buffer = new byte[8];

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                if (message.equals("bingo"))
                {
                    Platform.runLater(() -> {
                        windowController.bingo(message);
                    });

                    application.bingo();
                }

            } catch (IOException e) {
                System.out.println("Error receiving ball: " + e.getMessage());
            }
        }
    }
}
