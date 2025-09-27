package object.dog;

/**
 * A class representing a dog
 * It contains information about the nickname, breed, and awards.
 * @author Gushchin Kirill
 * @version 1.0
 */
public class Dog 
{
    private String name;
    private String poroda;
    private boolean hasAward;
    
    /**
     * Constructor for creating a Dog object
     * @param inpName is the dog's nickname
     * @param inpBreed dog breed
     * @param inpHasAward availability of awards
     */
    public Dog(String inpName, String inpBreed, boolean inpHasAward)
    {
        name = inpName;
        poroda = inpBreed;
        hasAward = inpHasAward;
    }
    
    /**
     * Function returns the dog's name
     * @return dog's name
     */
    public String getName(){return name;}
    
    /**
     * Function returns the dog's breed
     * @return dog's breed
     */
    public String getBreed(){return poroda;}
    
    /**
     * Function checks for the dog's awards
     * @return true if there are rewards, otherwise false
     */
    public boolean hasAward(){return hasAward;}
    
    /**
     * Function sets a new dog name
     * @param newName new nickname
     */
    public void setName(String newName){name = newName;}
    
    /**
     * Function sets a new breed of dog
     * @param newPoroda new breed
     */
    public void setBreed(String newPoroda){poroda = newPoroda;}
    
    /**
     * Function sets the dog's awards
     * @param newHasAward availability of awards
     */
    public void setAwards(boolean newHasAward){hasAward = newHasAward;}
    
    @Override
    public String toString() 
    {
        int hasAwardInt;
        if(hasAward) 
        {
            hasAwardInt = 1;
        }
        else
        {
            hasAwardInt = 0;
        }
        return name + ";" + poroda + ";" + hasAwardInt;
    }
}