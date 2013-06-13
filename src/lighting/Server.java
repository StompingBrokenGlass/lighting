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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    
    /* This Process status */
    private boolean running = false;
    
    /* Channels */
    private HashMap channels = new HashMap();
    
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
        
        this.running = true;
        
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
                    this.ircProcessor(message);
                    
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
    private void ircProcessor (String message){
        
        String prefix = " ";
        String command = " ";
        String parameters = " ";
        
        // Check if there is a prefex and sets it
        if (message.indexOf(':') == 0){
            // Finding Spaces
            int firstSpace = message.indexOf(" ");
            int secondSpace = message.indexOf(" ", firstSpace+1);
            //
            prefix = message.substring(1, firstSpace);
            command = message.substring(firstSpace +1, secondSpace);
            parameters = message.substring(secondSpace+1);
        }else{
            // no pefix
            int firstSpace = message.indexOf(" ");
            
            command = message.substring(0, firstSpace);
            parameters = message.substring(firstSpace+1);
        }
        
        // Check up the progress
        
        /*System.out.println("Prefix: " + prefix +" Command: " + command + 
                " Para:"+ parameters);*/
        
        // switch board of commands & replies
        switch(command){
            /* Replies */
            
            case "376" :    // End of motd
                            this.joinChannels();
                            break;
                
            case "366" :    // Greet after Name List
                            int space = parameters.indexOf(" ");
                            String chan = parameters.substring(space+1,
                                    parameters.indexOf(" ", space +1));
                            this.sendMessage(chan, "Meow!");
                            break;
            /* Comands */
            
            case "ERROR" :
                            System.out.println("Error from the server("+ 
                                    parameters +")");
                            break;
            case "PING" :
                            this.sendPong(parameters);
                            break;
                
            case "PRIVMSG" :    // Receiving a message
                            this.echoMessage(message);
                            break;
                
            default:        // unhandled replies and command
                            System.out.println ("Unhandled command");
        }
        
    }
    
    
    /**
     * Sends a message through the stream
     * @param message The message to be sent to the server
     */
    private void sendRaw (String message) {
        
        try {
            System.out.println("--> " + message);
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
        sendRaw (message);
    }
    
    public void addChannel (String channel){
        Channel chan = new Channel();
        chan.setName(channel);
        this.channels.put(channel, chan);
    }
    
    public void removeChannel (String channel){
        this.channels.remove(channel);
    }
    
    private void joinChannels(){
      Iterator it  = this.channels.entrySet().iterator();
      
      while(it.hasNext()){
          Map.Entry pairs = (Map.Entry) it.next();
          String chanName = (String) pairs.getKey();
          this.joinChannel(chanName);
          it.remove();
      }
    }
    
    private void joinChannel(String channel){
        this.sendRaw("JOIN " + channel);
    }
    
    public void sendMessage (String traget, String Message){
        this.sendRaw("PRIVMSG "+ traget + " :" + Message);
    }
    
    private void echoMessage (String rawMessage){
        
       Message message = new Message(rawMessage,this.nickname);
        
        if (message.isPM()){

            this.sendMessage(message.getSender(),message.getContext());
        }else{
            
            this.sendMessage(message.getChannel(),message.getContext());
            
        }
        
    }
}
