package com.od.ua;

import lombok.Builder;

import java.net.ServerSocket;
import java.net.Socket;

@Builder
public class Listner implements Runnable {

    private final int port;
    private final Engine engine;
    private final String path;

    public Listner(int port, Engine engine, String path) {
        this.port = port;
        this.engine = engine;
        this.path = path;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                System.out.print("Server starts!\n");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected\n");
                Thread thr = new Thread(new TelnetClient(socket, engine, path));
                thr.start();

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}