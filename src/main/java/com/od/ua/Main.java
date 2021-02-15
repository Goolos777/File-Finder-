package com.od.ua;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class Main {

    private static String read() {
        String s = "";
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        try {
            s = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static void main(String[] args) {

        String rootPath = null;
        int port = 0;
        if (args.length == 0) {
            // using console
            System.out.print("enter port ");
            port = Integer.parseInt(read());
            System.out.print("enter path ");
            rootPath = read();
        } else if (args.length == 2) {
            rootPath = args[1];
            port = Integer.parseInt(args[0]);
        } else {
            System.out.print("invalid parametrs");
        }
        Listner.builder()
                .engine(new Engine())
                .path(rootPath)
                .port(port)
                .build()
                .run();
    }
}
