package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;

public class DBServer {

    final static char EOT = 4;
    private DBController controller;
    public static void main(String[] args) throws ParseException {
        new DBServer(8888);
    }

    public DBServer(int portNumber) throws ParseException {
        try {
            this.controller=new DBController();

            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            Socket socket = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true) processNextCommand(in,out);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void processNextCommand (BufferedReader in, BufferedWriter out) throws IOException, NoSuchMethodError
    {
        String line = in.readLine();
        this.controller.readCommand(line,out);
        out.write(""+EOT+"\n");
        out.flush();
    }
}