package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;

public class DBServer {

    private DBController controller;
    public static void main(String[] args) throws ParseException {
        new DBServer(8888);
    }

    public DBServer(int portNumber) throws ParseException {
        try {
            this.controller=new DBController();
            this.controller.test();
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            while (true) acceptNextConnection(ss);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void acceptNextConnection(ServerSocket ss) throws IOException {
        // Next line will block until a connection is received
        Socket socket = ss.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        processNextCommand(in, out);
        out.close();
        in.close();
        socket.close();
    }

        private void processNextCommand (BufferedReader in, BufferedWriter out) throws IOException, NoSuchMethodError
        {
            String line = in.readLine();
            this.controller.readCommand(line,out);


        }
    }