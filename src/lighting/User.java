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
public class User {
    private String name ;
    private boolean away;
    
    User (String name){
        this.name = name;
        this.away = false;
    }
    
    /**
     * Sets the name of the user
     * @param name 
     */
    public void setName (String name){
        this.name = name;
    }
    
    /**
     * Gets the name of the user
     * @return String
     */
    public String getName (){
        return this.name;
        
    }
    
    /**
     * Check away status
     * @return boolean
     */
    public boolean isAway(){
        return away;
    }
    
    /**
     * Changes the Away status
     */
    public void toggleAway (){
        this.away = (! this.away);
    }
}
