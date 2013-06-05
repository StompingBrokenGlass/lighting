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
    
    public void setName (String name){
        this.name = name;
    }
    
    public String getName (){
        return this.name;
        
    }
    
    public boolean isAway(){
        return away;
    }
    
    public void toggleAway (){
        this.away = (! this.away);
    }
}
