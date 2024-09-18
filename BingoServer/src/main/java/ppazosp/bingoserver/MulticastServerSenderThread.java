package ppazosp.bingoserver;

import javafx.application.Platform;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

public class MulticastServerSenderThread extends Thread {

    MainViewController windowController;
    MainApplication application;

    final String multicastAddress = "225.0.0.100";
    final int port = 6789;

    public MulticastServerSenderThread(String name, MainViewController windowController, MainApplication application) {
        super(name);
        this.windowController = windowController;
        this.application = application;
    }

    @Override
    public void run() {

        ArrayList<Integer> balls = new ArrayList<Integer>();
        for(int i = 1; i <= 30; i++) {
            balls.add(i);
        }

        while(!balls.isEmpty() && !application.isBingo()){

            Random rand = new Random(System.currentTimeMillis());
            int currentRandomInt = rand.nextInt(balls.size());
            String message = String.format("%02d", balls.get(currentRandomInt));
            balls.remove(currentRandomInt);


            try (DatagramSocket socket = new DatagramSocket()) {
                byte[] buffer = message.getBytes();

                InetAddress group = InetAddress.getByName(multicastAddress);

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
                socket.send(packet);

                Platform.runLater(() -> {
                    windowController.update(message);
                });

                Thread.sleep(1000);
            } catch (IOException e) {
                System.out.println("Error sending ball: " + e.getMessage());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
