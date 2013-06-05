/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lighting;

import java.util.HashMap;
/**
 *
 * @author StompingBrokenGlass <stompingbrokenglass@gmail.com>
 * @since 2013-06-05
 */
public class Channel {
    private String name;
    private HashMap users;
    
    /**
     * Set the channel name.
     * @param channelName the channel name including the prefix (eg. #help)
     */
    public void setName (String channelName){
        char beginning = channelName.charAt(0);
        
        if (beginning == '&' || beginning == '#' || beginning == '+'
                || beginning =='!'){
            this.name = channelName;
        }
    }
    
    /** 
     * Gets the channel name
     * @return String
     */
    public String getName () {
        return name;
    }
    
    /**
     * Adds a user to the channel list
     * @param user User
     */
    public void addUser (User user){
        this.users.put(user.getName(), user);
    }
    
    /**
     * Adds a user to the channel list
     * @param username String
     */
    public void addUser (String username){
        this.users.put(username, new User(username));
    }
    
    /**
     * Gets a user using his username
     * @param username
     * @return User
     */
    public User getUser (String username){
        User user = (User)this.users.get(username);
        return user;
    }
}
