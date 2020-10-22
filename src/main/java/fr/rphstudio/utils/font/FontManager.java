//--------------------------------------------------------------------
// IMPORTS
//--------------------------------------------------------------------
package fr.rphstudio.utils.font;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


//--------------------------------------------------------------------
// CLASS
//--------------------------------------------------------------------

/**
 * Class used to blit some font sprites on the screen, using a string as input.
 * @author Romuald GRIGNON
 */
public class FontManager
{
    //--------------------------------------------------------------------
    // PROPERTIES
    //--------------------------------------------------------------------

    /**
     * Character information that helps to find a character from the sprite sheet. 
     */
    private class LetterInfo
    {   
        private char letter;
        private int  x;
        private int  y;
        private LetterInfo(char paramL, int paramX, int paramY)
        {
            this.letter = paramL;
            this.x      = paramX;
            this.y      = paramY;
        }
    }
    
    /**
     * Letter array, containing all information about the character area in the spritesheet.
     */
    final private LetterInfo[] letters =
    {
        // Letter #1
        new LetterInfo('A' , 0,0), new LetterInfo('B' , 1,0), new LetterInfo('C' , 2,0), new LetterInfo('D' , 3,0), new LetterInfo('E' , 4,0),  
        new LetterInfo('F' , 5,0), new LetterInfo('G' , 6,0), new LetterInfo('H' , 7,0), new LetterInfo('I' , 8,0), new LetterInfo('J' , 9,0),  
        new LetterInfo('K' ,10,0), new LetterInfo('L' ,11,0), new LetterInfo('M' ,12,0),
        // Letter #2
        new LetterInfo('N' , 0,1), new LetterInfo('O' , 1,1), new LetterInfo('P' , 2,1), new LetterInfo('Q' , 3,1), new LetterInfo('R' , 4,1),
        new LetterInfo('S' , 5,1), new LetterInfo('T' , 6,1), new LetterInfo('U' , 7,1), new LetterInfo('V' , 8,1), new LetterInfo('W' , 9,1),
        new LetterInfo('X' ,10,1), new LetterInfo('Y' ,11,1), new LetterInfo('Z' ,12,1),
        // Numbers #1
        new LetterInfo('0' , 0,2), new LetterInfo('1' , 1,2), new LetterInfo('2' , 2,2), new LetterInfo('3' , 3,2), new LetterInfo('4' , 4,2),
        new LetterInfo('5' , 5,2), new LetterInfo('6' , 6,2), new LetterInfo('7' , 7,2), new LetterInfo('8' , 8,2), new LetterInfo('9' , 9,2),
        // Characters #1
        new LetterInfo('.' , 0,3), new LetterInfo(':' , 1,3), new LetterInfo(';' , 2,3), new LetterInfo(',' , 3,3), new LetterInfo('/' , 4,3),
        new LetterInfo('\\', 5,3), new LetterInfo('+' , 6,3), new LetterInfo('-' , 7,3), new LetterInfo('*' , 8,3), new LetterInfo(' ' , 9,3),
        // Characters #2
        new LetterInfo('&' , 0,4), new LetterInfo('é' , 1,4), new LetterInfo('\"', 2,4), new LetterInfo('\'', 3,4), new LetterInfo('(' , 4,4),
        new LetterInfo('[' , 5,4), new LetterInfo('{' , 6,4), new LetterInfo('è' , 7,4), new LetterInfo('_' , 8,4), new LetterInfo('ç' , 9,4),
        new LetterInfo('à' ,10,4), new LetterInfo('}' ,11,4), new LetterInfo(']' ,12,4), new LetterInfo(')' ,13,4), new LetterInfo('=' ,14,4),
        // Characters #3
        new LetterInfo('<' , 0,5), new LetterInfo('>' , 1,5), new LetterInfo('?' , 2,5), new LetterInfo('!' , 3,5), new LetterInfo('§' , 4,5),
        new LetterInfo('ù' , 5,5), new LetterInfo('$' , 6,5), new LetterInfo('£' , 7,5), new LetterInfo('°' , 8,5), new LetterInfo('@' , 9,5),
        new LetterInfo('#' ,10,5), new LetterInfo('²' ,11,5),
    };
    
    /**
     * Private field containing a reference to the spritesheet
     */
    private SpriteSheet font;
 
    /**
     * Local constant used for character separation
     */
    final private static int CHAR_DELTA = 25;
    
    
    //--------------------------------------------------------------------
    // CONSTRUCTOR
    //--------------------------------------------------------------------
    
    /**
     * Constructor of the font manager.
     * It just loads a spritesheet into the memory, waiting for the public methods calls to use it.
     */
    public FontManager(String imgPath, int sizeX, int sizeY)
    {
        try
        {
            this.font = new SpriteSheet("./sprites/fontchess.png",sizeX,sizeY);
        }
        catch(SlickException ex)
        {
            throw new Error("Impossible to load sprite sheet in ChessFontManager !");
        }
    }
     
    
    //--------------------------------------------------------------------
    // PUBLIC METHODS
    //--------------------------------------------------------------------
    
    /**
     * Blits a string message on the screen, using the message input and the spritesheet private property.
     * @param g reference of the graphic buffer object used to blit things on the screen.
     * @param message input message string.
     * @param x left offset of the message display area.
     * @param y top offset of the message display area.
     * @param clr color filter to apply over the spritesheet.
     * @param ratio scale value to apply during blitting operation. Allows to configure the message character size.
     */
    public void displayString(Graphics g, String message, float x, float y, Color clr, float ratio)
    {
        // Loop for each character from the message
        for(int i=0;i<message.length();i++)
        {
            // Get letter
            char c = message.toUpperCase().charAt(i);
            // Get entry from the 'letters' array
            for(LetterInfo li:this.letters)
            {
                if(li.letter == c)
                {
                    // Draw character at the correct offset
                    g.drawImage( this.font.getSprite(li.x,li.y).getScaledCopy(ratio), x+(i*CHAR_DELTA*ratio)-((CHAR_DELTA/2)*ratio), y, clr);
                    // Go to next message character 
                    break;
                }
            }
        }
    }
    
    
    //--------------------------------------------------------------------
    // END OF CLASS
    //--------------------------------------------------------------------
}
