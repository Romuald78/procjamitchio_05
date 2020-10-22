/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.core.utils;


/**
 *
 * @author GRIGNON FAMILY
 */
public class CoreUtils
{
    //================================================
    // STATIC PROPERTIES 
    //================================================
    // Unique identifier
    static private long lastID = 0;
    
    
    
    //================================================
    // STATIC METHODS
    //================================================
    // Unique Identifier generator
    static public  long getNewID()
    {
    	CoreUtils.lastID++;
        return CoreUtils.lastID;
    }
    

    
    //================================================
    // END OF CLASS 
    //================================================
}
