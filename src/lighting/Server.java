/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lighting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author StompingBrokenGlass <stompingbrokenglass@gmail.com>
 * @since 2013-06-05
 */
public class Server extends Thread {
    /* Connection configration */
    private String hostname ;
    private int port ;
    private String nickname ;
    private String password ;
    
    /* Connection stream */
    private Socket socket;
    private BufferedWriter outbound;
    private BufferedReader inbound;
    
    /*this Process status */
    private boolean running = true;
    
    Server () {
        // uses the local server
        hostname = "localhost";
        port = 6667;
        nickname = "Lighting";
        password = "";
    }
    
    Server (String hostname, int port, String nickname, String password){
        this.hostname = hostname;
        this.port = port;
        this.nickname = nickname;
        this.password = password;
    }
    
    /**
     * 
     * @param hostname 
     */
    public void setHostname (String hostname){
        this.hostname = hostname;
    }
    
    /**
     * 
     * @return Returns the server's hostname
     */
    public String getHostname () {
        return this.hostname;
    }
    
    /**
     * 
     * @param port 
     */
    public void setPort (int port){
        this.port = port;
    }
    
    /**
     * 
     * @return the server's port number
     */
    public int getPort (){
        return this.port;
    }
    
    /**
     * Set the nickname of the bot for DataOutputStreamthis server
     * @param nickname 
     */
    public void setNickname (String nickname){
        this.nickname = nickname;
    }
    
    /**
     * 
     * @return Nickname
     */
    public String getNickname (){
        return this.nickname;
    }
    
    /**
     * Sets the password of the bot for this server
     * @param password 
     */
    public void setPassword (String password){
        this.password = password;
    }
    
    public void initConnection (){
        
        System.out.println("Connecting to " + this.hostname + " on port " +
                this.port + " ...");
        try {
            
            this.socket = new Socket(this.hostname,this.port);
            System.out.println("Connected.");
            
            // Getting streams
            
            this.outbound = new BufferedWriter( new OutputStreamWriter(
                    this.socket.getOutputStream()));
            this.inbound = new BufferedReader( new InputStreamReader(
                    this.socket.getInputStream()));
            
            // Identifying our self to the server
            this.outbound.write("NICK " + this.nickname);
            this.outbound.newLine();
            this.outbound.write("USER " + this.nickname +
                    " 0 * :Lighting Cat-bot");
            this.outbound.newLine();
            this.outbound.flush();
            
        } catch (Exception e){
            e.printStackTrace();
        }
        
    }
    
    /**
     * Implementing run from Thread Class
     */
    public void run () {
        this.initConnection();
        try {
            
            String message;
            
            while(this.running){

                message = this.inbound.readLine();
                // checking if the buffer is not null
                if (message != null){
                    // Printing inboud messages
                    System.out.println("<-- " + message);
                    
                    // Sending the message to the processor
                    this.msgProcessor(message);
                    
                }else{
                    System.out.println("Disconnected from " + this.hostname + 
                            ":" + port);
                    this.running = false;
                }

            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Processor for inbound messages
     * @param message Received message to be processed
     */
    private void msgProcessor (String message){
        
        // Check if there is a prefex
        if (message.indexOf(':') == 0){
            System.out.println("Prefix found");
            
        } else {
            // Direct command
            System.out.println("Direct command");
            
        }
        
    }
    
    
    private void sendMsg (String message) {
        
        try {
            this.outbound.write(message);
            this.outbound.newLine();
            this.outbound.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
            
    }
    
    /**
     * Sends a PONG Message for keeping the connection alive
     * @param requester Server name from the ping
     */
    private void sendPong (String requester){
        String message = "PONG " + requester;
        sendMsg (message);
    }
}
