package fileManager;

import org.apache.log4j.Logger;
import list.List;
import object.dog.Dog;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * Class for managing file operations with multithreading support
 */
public class FileManager {
    
    /** Logger for this class */
    private static final Logger log = Logger.getLogger(FileManager.class);
    
    /**
     * Loads data from XML file with callback (multithreaded)
     * @param filePath path to the XML file
     * @param callback callback for handling operation result
     */
    public void loadFromXML(String filePath, FileOperationCallback<List<Dog>> callback) {
        Thread loadThread = new Thread(() -> {
            try {
                List<Dog> dogs = inputFromXML(filePath);
                log.info("Successfully loaded " + dogs.getSize() + " dogs from: " + filePath);
                callback.onSuccess(dogs);
            } catch (Exception e) {
                log.error("Error loading XML from " + filePath + ": " + e.getMessage(), e);
                callback.onError(e);
            }
        });
        loadThread.setName("Data-Loader-Thread");
        loadThread.start();
    }
    
    /**
     * Saves data to XML file with callback (multithreaded)
     * @param filePath path to the XML file
     * @param dogs list of dogs to save
     * @param callback callback for handling operation result
     */
    public void saveToXML(String filePath, List<Dog> dogs, FileOperationCallback<Void> callback) {
        Thread saveThread = new Thread(() -> {
            try {
                outputToXML(filePath, dogs);
                log.info("Successfully saved " + dogs.getSize() + " dogs to: " + filePath);
                callback.onSuccess(null);
            } catch (Exception e) {
                log.error("Error saving XML to " + filePath + ": " + e.getMessage(), e);
                callback.onError(e);
            }
        });
        saveThread.setName("Data-Saved-Thread");
        saveThread.start();
    }
    
    
    /**
     * Interface for file operation callbacks
     * @param <T> type of operation result
     */
    public interface FileOperationCallback<T> {
        /**
         * Called when operation succeeds
         * @param result operation result
         */
        void onSuccess(T result);
        
        /**
         * Called when operation fails
         * @param e exception that occurred
         */
        void onError(Exception e);
    }
    
    /**
     * Reads dog data from XML file
     * @param filePath path to the XML file
     * @return list of dogs read from file
     * @throws IOException if there is an I/O error
     */
    public List<Dog> inputFromXML(String filePath) throws IOException {
        List<Dog> dogList = new List<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(Files.newInputStream(Paths.get(filePath)));
            
            doc.getDocumentElement().normalize();
            NodeList dogNodes = doc.getElementsByTagName("dog");
            
            for(int i = 0; i < dogNodes.getLength(); i++) {
                Element dogElem = (Element) dogNodes.item(i);
                String name = dogElem.getElementsByTagName("name").item(0).getTextContent();
                String breed = dogElem.getElementsByTagName("breed").item(0).getTextContent();
                boolean hasAward = Boolean.parseBoolean(dogElem.getElementsByTagName("awards").item(0).getTextContent());
                
                dogList.push_back(new Dog(name, breed, hasAward));
            } 
        } catch (ParserConfigurationException | SAXException e) {
            log.error("XML parsing error for file " + filePath + ": " + e.getMessage(), e);
            throw new IOException("XML parsing error: " + e.getMessage(), e);
        }
        return dogList;
    }
    
    /**
     * Writes dog data to XML file
     * @param filePath path to the XML file
     * @param dogs list of dogs to write
     * @throws IOException if there is an I/O error
     */
    public void outputToXML(String filePath, List<Dog> dogs) throws IOException {
        try {
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
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(Files.newOutputStream(Paths.get(filePath)));
            transformer.transform(source, result);
            
        } catch (ParserConfigurationException | TransformerException e) {
            log.error("XML writing error for file " + filePath + ": " + e.getMessage(), e);
            throw new IOException("XML writing error: " + e.getMessage(), e);
        }
    }
    
    /**
     * Reads dog data from CSV file
     * @param filePath path to the CSV file
     * @return list of dogs read from file
     * @throws IOException if there is an I/O error
     */
    public List<Dog> inputFromCSV(String filePath) throws IOException {
        List<Dog> dogList = new List<>();
        java.util.List<String> lines = Files.readAllLines(Paths.get(filePath));
        
        for(String line:lines) {
            String[] dogInfo = line.split(";");
            boolean hasAward = Integer.parseInt(dogInfo[2]) == 1;
            dogList.push_back(new Dog(dogInfo[0], dogInfo[1], hasAward));
        }
        return dogList;
    }

    /**
     * Writes dog data to CSV file
     * @param filePath path to the CSV file
     * @param dogs list of dogs to write
     * @throws IOException if there is an I/O error
     */
    public void outputToCSV(String filePath, List<Dog> dogs) throws IOException {
        String[] listString = dogs.convToStr();
        Files.write(Paths.get(filePath), Arrays.asList(listString));
    }
}