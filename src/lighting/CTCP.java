/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lighting;

/**
 *
 * @author StompingBrokenGlass <stompingbrokenglass@gmail.com>
 * @since 2013-06-20
 */
public class CTCP {
    
    
    
    public String processCTCP(String query){
        
        String response = "";
        int space = query.indexOf(" ");
        
        String command ;
        String parameters = "";
        if (space < 0){
            command = query;
        } else {
            command = query.substring(0,space);
            parameters = query.substring(space+1);
        }
        
        switch (command){
            
            case "CLIENTINFO":
                response = clientInfo(parameters);
                break;
                
            case "PING" :
                response = "PING " + parameters;
                break;
                
            case "VERSION" :
                response = "VERSION Lighting-catbot ALPHA";
                break;

            default:
                // send an error message identifying an unknown command
                response = "ERRMSG " + query + " :Unknown CTCP query";
        }
        
        return response;
    }
    
    private String clientInfo (String parameters){
        
        String message = "CLIENTINFO :"; 
        String commands = "CLIENTINFO,PING,VERSION";
        
        switch (parameters){
            
            case "":
                // Default message
                message += "I can support the follow commands (" + commands 
                        + "), also you can request the help by"
                        + " passing the command as an agrument";
                break;
                
            default:
                // respond with Error
                message = "ERRMSG CLIENTINFO " + parameters 
                        + " :Unknown CTCP query";
        }
        return message;
    }
}
