package fr.rphstudio.utils.rng;

public class Prng
{
	//-----------------------------------------------------
	// PUBLIC PROPERTIES
	//-----------------------------------------------------
	private final static int  DEF_LIN_CONGR_SEED = 123456789;
	private final static long DEF_LIN_CONGR_COEF = 22696477; //1000381; //16807;    // (int)Math.pow(2, 19)-1;     //22695477;
	private final static long DEF_LIN_CONGR_OF7  = 1;
	private final static long DEF_LIN_CONGR_MOD  = (long)(Math.pow(2, 61))-1;
	
	
	
	//-----------------------------------------------------
	// PRIVATE PROPERTIES
	//-----------------------------------------------------
	private int        linCongrSeed;
	private final long linCongrCoef;
	private final long linCongrOf7;
	private final long linCongrMod;

	
	
	//-----------------------------------------------------
	// CONSTRUCTOR
	//-----------------------------------------------------
	public Prng()
	{
		// Store parameters
		this.linCongrSeed = Prng.DEF_LIN_CONGR_SEED;
		this.linCongrCoef = Prng.DEF_LIN_CONGR_COEF;
		this.linCongrOf7  = Prng.DEF_LIN_CONGR_OF7;
		this.linCongrMod  = Prng.DEF_LIN_CONGR_MOD;
	}
        public Prng(int seed)
        {
            this();
            this.setSeed(seed);
        }
	
	
	//-----------------------------------------------------
	// PRIVATE METHODS
	//-----------------------------------------------------
	private int _getRandomValue()
	{
		// Local variables
		long value = 0;
		
		// Start with seed
		value  = (long)this.linCongrSeed;
		// Multiply
		value *= this.linCongrCoef;
		// Offset
		value += this.linCongrOf7;
		// Modulo
		value %= this.linCongrMod;
		// Keep the middle 32 bits
		this.linCongrSeed = (int)((value >> 16) & 0x7FFFFFFF);		
		// return value
		return this.linCongrSeed;
	}
	
	
	
	//-----------------------------------------------------
	// PUBLIC METHODS
	//-----------------------------------------------------
	// Set seed
	public void setSeed(int seed)
	{
		this.linCongrSeed = seed;
	}
	// Get random value (between 0.0 and 1.0)
	public double random()
	{
		long v;
		v = this._getRandomValue();
		return ((double)(v&0x7FFFFFFF))/0x7FFFFFFF;
	}
        
        public static void main (String[] args)
        {
            Prng rng    = new Prng();
            long   prngTime;
            long   mathTime;
            double ratio;
            int    nbLoops = 10000000; 
            
            System.out.println("=================== DEBUT ===================");
            
            prngTime = System.nanoTime();
            for(int i=0;i<nbLoops;i++)
            {
                rng.random();
            }
            prngTime = System.nanoTime()-prngTime;
            System.out.println("Prng time = "+Long.toString(prngTime)+" usec");
            
            mathTime = System.nanoTime();
            for(int i=0;i<nbLoops;i++)
            {
                Math.random();
            }
            mathTime = System.nanoTime()-mathTime;
            System.out.println("Math time = "+Long.toString(mathTime)+" usec");
            
            ratio = ((double)prngTime)/((double)mathTime);
            if(ratio <= 1.0)
            {
                System.out.println("Our libray is "+Double.toString(ratio)+" times FASTER :-)");
            }
            else
            {
                System.out.println("Our libray is "+Double.toString(ratio)+" times SLOWER :-(");
            }
            
            /*
            System.out.println("===================== Display Math.random values ========================");
            for(int i=0;i<100;i++)
            {
                System.out.println( Math.random() );
            }
            System.out.println("===================== Display Prng.random values ========================");
            for(int i=0;i<100;i++)
            {
                System.out.println( rng.random() );
            }
            
            System.out.println("===================  FIN  ===================");
            */
        }
        
}


