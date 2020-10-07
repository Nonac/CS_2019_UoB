package com.company;

import java.io.*;
import java.net.Socket;

public class DBTest {
    final static char EOT = 4;

    public static void main(String args[])
    {
        try {
            long startTime=System.currentTimeMillis();
            BufferedReader inputFromFile = new BufferedReader(new FileReader(args[0]));
            BufferedWriter outputFromFile = new BufferedWriter(new FileWriter("res.txt"));
            String str;
            //BufferedReader commandLine = new BufferedReader(new InputStreamReader(System.in));
            Socket socket = new Socket("127.0.0.1", 8888);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while((str=inputFromFile.readLine())!=null) handleNextCommand(str,outputFromFile, out, in);
            long endTime=System.currentTimeMillis();
            outputFromFile.write("\n\nRunning time: "+(endTime-startTime)+"ms\n");
            outputFromFile.close();
        } catch(IOException ioe) {
            System.out.println(ioe);
        }
    }

    private static void handleNextCommand(String command,BufferedWriter outputFromFile,
                                          BufferedWriter out, BufferedReader in)
    {
        try {

            //System.out.print("SQL:> ");
            out.write(command + "\n");
            out.flush();
            String incoming = in.readLine();
            while( ! incoming.contains("" + EOT + "")) {
                //System.out.println(incoming);
                outputFromFile.write(incoming+"\n");
                incoming = in.readLine();
            }
        } catch(IOException ioe) {
            System.out.println(ioe);
        }
    }
}
