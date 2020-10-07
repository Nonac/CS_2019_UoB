import com.alexmerz.graphviz.ParseException;

import java.io.*;
import java.net.*;
import java.util.*;

class StagServer
{
    private World world;
    private Controller controller;
    public static void main(String[] args) throws ParseException, org.json.simple.parser.ParseException {
        if(args.length != 2) System.out.println("Usage: java StagServer <entity-file> <action-file>");
        else new StagServer(args[0], args[1], 8888);
    }

    public StagServer(String entityFilename, String actionFilename, int portNumber) throws ParseException,
            org.json.simple.parser.ParseException {
        try {
        world=new World(entityFilename,actionFilename);
        controller=new Controller(world);

            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            while(true) acceptNextConnection(ss);
        } catch(IOException ioe) {
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

    private void processNextCommand(BufferedReader in, BufferedWriter out) throws IOException,NoSuchMethodError
    {
        String line = in.readLine();
        controller.readCommand(line,out);
    }
}
