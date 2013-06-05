/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lighting;

/**
 *
 * @author StompingBrokenGlass <stompingbrokenglass@gmail.com>
 * @since 2013-06-05
 */
public class Server {
    private String hostname ;
    private int port ;
    private String nickname ;
    private String password ;
    
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
}
