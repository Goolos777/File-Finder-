package com.od.ua;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Slf4j
public class TelnetClient implements Runnable {

    private final Socket socket;
    private final Engine engine;
    private final String rootPath;

    public TelnetClient(Socket socket, Engine engine, String rootPath) {
        this.socket = socket;
        this.engine = engine;
        this.rootPath = rootPath;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            String line = "";
            while (true) {
                outputStream.writeChars("Enter depth mask:\r\n");
                outputStream.flush();

                System.out.println("Enter parameters: \n");
                line = in.readLine();
                try {
                    if (line.equals("exit")) {
                        break;
                    }
                    log.debug(line);
                    String param[] = line.split(" ");
                    log.debug(param[0]);
                    int depth = Integer.parseInt(param[0]);
                    String mask = param[1];


                    System.out.println("Parameters recieved \ndepth: " + depth + "\nmask: " + mask);
                    engine.addTask(InputData.builder()
                            .depth(depth)
                            .mask(mask)
                            .rootPath(rootPath)
                            .handler((val) -> {
                                try {
                                    outputStream.writeChars(val + "\n");
                                    outputStream.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            })
                            .build());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}