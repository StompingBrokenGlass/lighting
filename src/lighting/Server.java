/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lighting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    private DataOutputStream outbound;
    private DataInputStream inbound;
    
    Server () {
        // uses the local server
        hostname = "localhost";
        port = 6667;
        nickname = "lighting";
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
     * Set the nickname of the bot for this server
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
        
        System.out.println("Connecting to" + this.hostname + "on port " +
                this.port + " ...");
        try {
            
            this.socket = new Socket(this.hostname,this.port);
            System.out.println("Connected.");
            
            // Getting streams
            
            this.outbound = new DataOutputStream(this.socket.getOutputStream());
            this.inbound = new DataInputStream(this.socket.getInputStream());
            
        } catch (Exception e){
            e.printStackTrace();
        }
        
    }
    
    /**
     * Implementing run from Thread Class
     */
    public void run () {
        this.initConnection();
    }
}
