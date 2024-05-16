/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.realtimechatsocket;

/**
 *
 * @author chath
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Client {

    private static final String HOST = "localhost";
    private static final int PORT = 12345;
    
   
    /* PRIVATE_MESSAGE_PREFIX is a private static final variable. It is a constant used to specify the prefix for private messages. */   
    private static final String PRIVATE_MESSAGE_PREFIX = "/pm";
    
    
    public static void main(String[] args) throws IOException {
        /* A new Socket object named 'socket' is created to connect to the server at the given host and port. */
        Socket socket = new Socket(HOST, PORT);
        /* A message is printed to the console indicating the successful connection to the server. */
        System.out.println("Connected to server on " + HOST + ":" + PORT);

        /* A BufferedReader object named 'input' is created to read data from the server. It is initialized with an InputStreamReader which is constructed with the input stream of the socket. */
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        /* A PrintWriter object named 'output' is created to send data to the server. It is initialized with the output stream of the socket. The second argument 'true' passed to the PrintWriter indicates that the PrintWriter should automatically flush its buffer after every write operation. */
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        /* The server asks for the client's name, which is read from the console and sent to the server. */
        System.out.println(input.readLine());
        /* A Scanner object named 'scanner' is created to read the client's input from the console. */
        Scanner scanner = new Scanner(System.in);
        /* A String variable named 'name' is declared and initialized with the next line of input from the scanner. */
        String name = scanner.nextLine();
        /* The client's name is sent to the server. */
        output.println(name);
        

        
        /* A new Thread named 'serverListener' is created to read and display messages from the server. */
        Thread serverListener = new Thread(() -> {
            try {
                /* This is a loop where the client continuously reads messages from the server and prints them to the console. */
                String serverMessage;
                while ((serverMessage = input.readLine()) != null) {
                    System.out.println(serverMessage);
                }
            } catch (IOException e) {
                /* Any IOExceptions are caught and their stack trace is printed to the console. */
                e.printStackTrace();
            }
        });
        /* The serverListener thread is started, which invokes the run method of the thread. */
        serverListener.start();

        /* This is a loop where the client can continuously enter and send messages to the server. */
        String message;
        while (true) {
            
            /* The client is prompted to enter a message. */
            System.out.println("Enter your message (type 'exit' to leave or '/pm [name] [message]' for private chat):");
            
            /* The String variable 'message' is declared and initialized with the next line of input from the scanner. */
            message = scanner.nextLine();
            /* The message is sent to the server. */
            output.println(message);

            /* If the client types 'exit', the loop breaks and the client disconnects from the server. */
            if (message.equalsIgnoreCase("exit")) {
                break;
            }
        }

        /* All resources are closed after use. */
        input.close();
        output.close();
        socket.close();
    }
}
