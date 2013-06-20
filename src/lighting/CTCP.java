/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lighting;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * CTCP protocol support according to 
 * http://www.irchelp.org/irchelp/rfc/ctcpspec.html
 * @author StompingBrokenGlass <stompingbrokenglass@gmail.com>
 * @since 2013-06-20
 */
public class CTCP {
    
    
    /**
     * Main CTCP Processor
     * @param query
     * @return Response to CTCP command
     */
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
            
            case "FINGER" :
                response = "FINGER :Please check my USERINFO instead"
                        + ", because I don't have a realname and don't count"
                        + " my idling time";
                break;
                
            case "PING" :
                response = "PING " + parameters;
                break;
            
            case "SOURCE" :
                 response = "SOURCE" 
                         + " https://github.com/StompingBrokenGlass/lighting";
                 break;
                
            case "TIME" :
                
                SimpleDateFormat dateFormat = 
                        new SimpleDateFormat(
                        "EEE yyyy-MM-dd hh:mm:ss z '(UTC'Z')'");
                
                String time = dateFormat.format(
                        Calendar.getInstance().getTime());
                
                response = "TIME :" + time ;
                
                break;
                
            case "VERSION" :
                response = "VERSION Lighting-catbot:ALPHA:Java 7";
                break;
                
            case "USERINFO" : 
                response = "USERINFO :Meow? I'm a kitty cat bot (=^.^=)";
                break;

            default:
                // send an error message identifying an unknown command
                response = "ERRMSG " + query + " :Unknown CTCP query";
        }
        
        return response;
    }
    
    /**
     * Handling CLIENTINFO request
     * @param parameters
     * @return Available commands in case no parameters or command help
     */
    private String clientInfo (String parameters){
        
        String message = "CLIENTINFO :"; 
        String commands = "CLIENTINFO,FINGER,PING,SOURCE,TIME,VERSION,USERINFO";
        
        switch (parameters){
            
            case "":
                // Default message
                message += "I can support the follow commands (" + commands 
                        + "), also you can request the help by"
                        + " passing the command as an agrument";
                break;
                
            case "CLIENTINFO" :
                message = "with 0 arguments gives a list of known client" 
                        +" query keywords. With 1 argument, a description of" 
                        +" the client query keyword is returned.";
                break;
            case "FINGER" :
                message = "Returns User real name and idling time";
                break;
                
            case "PING" :
                message ="Used to measure the time delay between clients on" 
                        + " the IRC network";
                break;
            
            case "SOURCE" :
                message = "Returns the github link of the bot";
                break;
                
            case "TIME" :
                message = "Returns the bot's system time in human readable form";
                break;
                
            case "VERSION" :
                message = "Returns the bot's Version";
                break;
                
            case "USERINFO" : 
                message = "Returns the user info";
                break;
                
            default:
                // respond with Error
                message = "ERRMSG CLIENTINFO " + parameters 
                        + " :Unknown CTCP query";
        }
        return message;
    }
}
