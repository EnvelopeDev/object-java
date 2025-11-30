package fileManager;

import list.List;
import object.dog.Dog;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;

/**
 * Class for managing file operations with dog data
 * Implements reading and writing data in CSV format
 * @author Vadim Ustinov
 * @version 1.0
 */
public class FileManager implements interfaceFM
{
    /**
     * Default Constructor
     */
    public FileManager() {
    }

    /**
     * Reads dog data from CSV file and creates a list of Dog objects
     * CSV format: name;breed;awards (where awards is 0 or 1)
     * @param filePath the path to the CSV file to read from
     * @return List of Dog objects populated from the CSV file
     */
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

    /**
     * Writes dog data to CSV file in format: name;breed;awards
     * Awards are converted to 1 (true) or 0 (false)
     * @param filePath the path to the CSV file to write to
     * @param dogs the list of Dog objects to write to file
     * @throws IOException if there's an error writing to the file
     */
    @Override
    public void outputToCSV(String filePath, List<Dog> dogs) throws IOException 
    {
        String[] listString = dogs.convToStr();
        Files.write(Paths.get(filePath), Arrays.asList(listString));
    }
    
    /**
     * Reads dog data from XML file and creates a list of Dog objects
     * @param filePath the path to the XML file to read from
     * @return List of Dog objects populated from the XML file
     * @throws IOException if there's an error reading the file or file not found
     */
    @Override
    public List<Dog> inputFromXML(String filePath) throws IOException 
    {
    	List<Dog> dogList = new List();
    	try
    	{	
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(Files.newInputStream(Paths.get(filePath)));
			
			doc.getDocumentElement().normalize();
			
			NodeList dogNodes = doc.getElementsByTagName("dog");
			
			for(int i = 0; i < dogNodes.getLength(); i++)
			{
				Element dogElem = (Element) dogNodes.item(i);
				
				String name = dogElem.getElementsByTagName("name").item(0).getTextContent();
				String breed = dogElem.getElementsByTagName("breed").item(0).getTextContent();
				boolean hasAward = Boolean.parseBoolean(dogElem.getElementsByTagName("awards").item(0).getTextContent());
				
				dogList.push_back(new Dog(name,breed, hasAward));
			} 
    	} catch (ParserConfigurationException | SAXException e) {
            throw new IOException("XML parsing error: " + e.getMessage(), e);
        }
    	
		return dogList;
    }
    
    /**
     * Writes dog data to XML file
     * @param filePath the path to the XML file to write to
     * @param dogs the list of Dog objects to write to file
     * @throws IOException if there's an error writing to the file
     */
    @Override
	public void outputToXML(String filePath, List<Dog> dogs) throws IOException 
	{
    	try 
    	{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document doc = builder.newDocument();
	        
	        Element rootElement = doc.createElement("dogs");
	        doc.appendChild(rootElement);
	        
	        for (int i = 0; i < dogs.getSize(); i++) {
	            Dog dog = dogs.at(i);
	            
	            Element dogElement = doc.createElement("dog");
	            rootElement.appendChild(dogElement);
	            
	            Element nameElement = doc.createElement("name");
	            nameElement.appendChild(doc.createTextNode(dog.getName()));
	            dogElement.appendChild(nameElement);
	            
	            Element breedElement = doc.createElement("breed");
	            breedElement.appendChild(doc.createTextNode(dog.getBreed()));
	            dogElement.appendChild(breedElement);
	            
	            Element awardsElement = doc.createElement("awards");
	            awardsElement.appendChild(doc.createTextNode(String.valueOf(dog.hasAward())));
	            dogElement.appendChild(awardsElement);
	        }
	        
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult(Files.newOutputStream(Paths.get(filePath)));
	        transformer.transform(source, result);
    	} catch (ParserConfigurationException | TransformerException e) {
            throw new IOException("XML writing error: " + e.getMessage(), e);
        }
	}
}