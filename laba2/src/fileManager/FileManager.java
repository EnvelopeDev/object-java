package fileManager;

import list.List;
import object.dog.Dog;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;

public class FileManager implements interfaceFM
{

	@Override
	public List<Dog> inputFromCSV(String filePath) throws IOException 
	{
		List<Dog> dogList = new List<>();
		java.util.List<String> lines = Files.readAllLines(Paths.get(filePath));
		boolean hasAward;
		
		for(String line:lines) 
		{
			String[] dogInfo = line.split(";");
			hasAward = Integer.parseInt(dogInfo[2])==1;
			dogList.push_back(new Dog(dogInfo[0], dogInfo[1], hasAward));
		}
		
		return dogList;
	}

	@Override
	public void outputToCSV(String filePath, List<Dog> dogs) throws IOException 
	{
		String[] listString = dogs.convToStr();
		Files.write(Paths.get(filePath), Arrays.asList(listString));
	}
}
