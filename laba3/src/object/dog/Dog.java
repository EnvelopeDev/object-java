package object.dog;

public class Dog 
{
	private String name;
	private String poroda;
	private boolean hasAward;
	
	public Dog(String inpName, String inpBreed, boolean inpHasAward)
	{
		name = inpName;
		poroda = inpBreed;
		hasAward = inpHasAward;
	}
	
	
	public String getName(){return name;}
    public String getBreed(){return poroda;}
    public boolean hasAward(){return hasAward;}
    
    public void setName(String newName){name = newName;}
    public void setBreed(String newPoroda){poroda = newPoroda;}
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
