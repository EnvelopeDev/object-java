package fileManager;
import list.interfaceList;

import java.io.IOException;

import list.List;
import object.dog.*;

public interface interfaceFM 
{
	List<Dog> inputFromCSV(String filePath) throws IOException;
	public void outputToCSV(String filePath, List<Dog> dogs) throws IOException;
}
