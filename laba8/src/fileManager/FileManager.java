package fileManager;

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
 * Manages file operations with multithreading support.
 */
public class FileManager {
    
    /**
     * Loads data from XML file asynchronously.
     * @param filePath Path to the XML file
     * @param callback Callback for handling the result
     */
    public void loadFromXML(String filePath, FileOperationCallback<List<Dog>> callback) {
        Thread loadThread = new Thread(() -> {
            try {
                List<Dog> dogs = inputFromXML(filePath);
                callback.onSuccess(dogs);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
        loadThread.setName("Data-Loader-Thread");
        loadThread.start();
    }
    
    /**
     * Saves data to XML file asynchronously.
     * @param filePath Path to the XML file
     * @param dogs List of dogs to save
     * @param callback Callback for handling the result
     */
    public void saveToXML(String filePath, List<Dog> dogs, FileOperationCallback<Void> callback) {
        Thread saveThread = new Thread(() -> {
            try {
                outputToXML(filePath, dogs);
                callback.onSuccess(null);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
        saveThread.setName("Data-Saver-Thread");
        saveThread.start();
    }
    
    /**
     * Saves modified dog data to XML for reporting purposes.
     * @param filePath Path to the XML file
     * @param dogs List of dogs to process
     * @param callback Callback for handling the result
     */
    public void saveModifiedForReport(String filePath, List<Dog> dogs, FileOperationCallback<Void> callback) {
        Thread saveThread = new Thread(() -> {
            try {
                List<Dog> reportDogs = new List<>();
                for (int i = 0; i < dogs.getSize(); i++) {
                    Dog original = dogs.at(i);
                    Dog modifiedDog = new Dog(
                        "[Report] " + original.getName(),
                        original.getBreed(),
                        original.hasAward()
                    );
                    reportDogs.push_back(modifiedDog);
                }
                
                outputToXML(filePath, reportDogs);
                callback.onSuccess(null);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
        saveThread.setName("Data-Editor-Thread");
        saveThread.start();
    }
    
    /**
     * Callback interface for asynchronous file operations.
     * @param <T> Type of operation result
     */
    public interface FileOperationCallback<T> {
        /**
         * Called when operation completes successfully.
         * @param result Operation result
         */
        void onSuccess(T result);
        
        /**
         * Called when operation fails.
         * @param e Exception that occurred
         */
        void onError(Exception e);
    }
    
    /**
     * Reads dog data from XML file.
     * @param filePath Path to the XML file
     * @return List of dogs read from file
     * @throws IOException If an I/O error occurs
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
            throw new IOException("XML parsing error: " + e.getMessage(), e);
        }
        return dogList;
    }
    
    /**
     * Writes dog data to XML file.
     * @param filePath Path to the XML file
     * @param dogs List of dogs to write
     * @throws IOException If an I/O error occurs
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
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(Files.newOutputStream(Paths.get(filePath)));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            throw new IOException("XML writing error: " + e.getMessage(), e);
        }
    }
    
    /**
     * Reads dog data from CSV file.
     * @param filePath Path to the CSV file
     * @return List of dogs read from file
     * @throws IOException If an I/O error occurs
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
     * Writes dog data to CSV file.
     * @param filePath Path to the CSV file
     * @param dogs List of dogs to write
     * @throws IOException If an I/O error occurs
     */
    public void outputToCSV(String filePath, List<Dog> dogs) throws IOException {
        String[] listString = dogs.convToStr();
        Files.write(Paths.get(filePath), Arrays.asList(listString));
    }
}