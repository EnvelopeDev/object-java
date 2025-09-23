package object.dog;

/**
 * Класс, представляющий собаку
 * Содержит информацию о кличке, породе и наличии наград
 * @author Vadim Ustinov
 * @version 1.0
 */
public class Dog 
{
    private String name;
    private String poroda;
    private boolean hasAward;
    
    /**
     * Конструктор для создания объекта Dog
     * @param inpName кличка собаки
     * @param inpBreed порода собаки
     * @param inpHasAward наличие наград
     */
    public Dog(String inpName, String inpBreed, boolean inpHasAward)
    {
        name = inpName;
        poroda = inpBreed;
        hasAward = inpHasAward;
    }
    
    /**
     * Возвращает кличку собаки
     * @return кличка собаки
     */
    public String getName(){return name;}
    
    /**
     * Возвращает породу собаки
     * @return порода собаки
     */
    public String getBreed(){return poroda;}
    
    /**
     * Проверяет наличие наград у собаки
     * @return true если есть награды, иначе false
     */
    public boolean hasAward(){return hasAward;}
    
    /**
     * Устанавливает новую кличку собаки
     * @param newName новая кличка
     */
    public void setName(String newName){name = newName;}
    
    /**
     * Устанавливает новую породу собаки
     * @param newPoroda новая порода
     */
    public void setBreed(String newPoroda){poroda = newPoroda;}
    
    /**
     * Устанавливает наличие наград у собаки
     * @param newHasAward наличие наград
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