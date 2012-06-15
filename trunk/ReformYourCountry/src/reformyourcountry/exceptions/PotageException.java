package reformyourcountry.exceptions;

public class PotageException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public PotageException()
    {
        super("potage exception");
        System.out.println(
                           "________________\n" +
        		           "\\             /\n "+
                           " \\           /\n"+
        		           "  \\         /\n"+
                           "   \\       /\n"+
        		           "    \\_____/ \"I want soupe\"");
        
    }
}
