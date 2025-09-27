package fileManager;
import list.InterfaceList;

import java.io.IOException;

import list.List;
import object.Dog;

public interface InterfaceFM 
{
	List<Dog> inputFromCSV(String filePath) throws IOException;
	public void outputToCSV(String filePath, List<Dog> dogs) throws IOException;
}
