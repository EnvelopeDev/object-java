package report;

import org.apache.log4j.Logger;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import fileManager.FileManager;
import list.List;
import object.dog.Dog;
import java.util.*;

/**
 * Report generation tool with multithreading support
 * Generates reports in PDF and HTML formats from XML data
 */
public class ReportGenerator {
    
    /** Logger for this class */
    private static final Logger log = Logger.getLogger(ReportGenerator.class);
    
    /** Path to PDF report template file */
    private static final String PDF_TEMPLATE = "src/report/pdf_report.jrxml";
    /** Path to HTML report template file */
    private static final String HTML_TEMPLATE = "src/report/html_report.jrxml";
    
    /**
     * Generates HTML report from XML data (multithreaded version)
     * Now returns boolean directly without CountDownLatch
     * @param xmlPath Path to source XML file with dog data
     * @param outputPath Path where HTML file will be saved
     * @return true if generation successful, false otherwise
     */
    public static boolean generateHTMLReport(String xmlPath, String outputPath) {
        log.info("Starting HTML report generation from: " + xmlPath + " to: " + outputPath);
        try {
            //Load data from XML file
            FileManager fileManager = new FileManager();
            List<Dog> dogs = fileManager.inputFromXML(xmlPath);
            
            if (dogs.getSize() == 0) {
                log.warn("No data loaded from XML file: " + xmlPath);
                return false;
            }
            
            //Convert data to Map format for JasperReports
            java.util.List<java.util.Map<String, Object>> dataList = convertToMapList(dogs);
            
            //Calculate statistics for report
            int awardsCount = countAwards(dogs);
            double percentage = calculatePercentage(awardsCount, dogs.getSize());
            
            //Create report parameters (metadata and statistics)
            java.util.Map<String, Object> params = createParams(xmlPath, dogs.getSize(), 
                                                              awardsCount, percentage);
            
            //Check if template file exists
            java.io.File templateFile = new java.io.File(HTML_TEMPLATE);
            if (!templateFile.exists()) {
                log.error("Template file not found: " + HTML_TEMPLATE);
                return false;
            }
            
            //Generate HTML report using JasperReports
            JasperReport report = JasperCompileManager.compileReport(HTML_TEMPLATE);
            JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource((java.util.Collection) dataList);
            JasperPrint print = JasperFillManager.fillReport(report, params, dataSource);
            JasperExportManager.exportReportToHtmlFile(print, outputPath);
            
            log.info("HTML report generated successfully: " + outputPath);
            return true;
            
        } catch (Exception e) {
            log.error("HTML generation error: " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Generates PDF report from XML data (multithreaded version)
     * Now returns boolean directly
     * @param xmlPath Path to source XML file with dog data
     * @param outputPath Path where PDF file will be saved
     * @return true if generation successful, false otherwise
     */
    public static boolean generatePDFReport(String xmlPath, String outputPath) {
        log.info("Starting PDF report generation from: " + xmlPath + " to: " + outputPath);
        try {
            //Load data from XML file
            FileManager fileManager = new FileManager();
            List<Dog> dogs = fileManager.inputFromXML(xmlPath);
            
            if (dogs.getSize() == 0) {
                log.warn("No data loaded from XML file: " + xmlPath);
                return false;
            }
            
            //Convert data to Map format for JasperReports
            java.util.List<java.util.Map<String, Object>> dataList = convertToMapList(dogs);
            
            //Calculate statistics for report
            int awardsCount = countAwards(dogs);
            double percentage = calculatePercentage(awardsCount, dogs.getSize());
            
            //Create report parameters (metadata and statistics)
            java.util.Map<String, Object> params = createParams(xmlPath, dogs.getSize(), 
                                                              awardsCount, percentage);
            
            //Check if template file exists
            java.io.File templateFile = new java.io.File(PDF_TEMPLATE);
            if (!templateFile.exists()) {
                log.error("Template file not found: " + PDF_TEMPLATE);
                return false;
            }
            
            //Generate PDF report using JasperReports
            JasperReport report = JasperCompileManager.compileReport(PDF_TEMPLATE);
            JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource((java.util.Collection) dataList);
            JasperPrint print = JasperFillManager.fillReport(report, params, dataSource);
            JasperExportManager.exportReportToPdfFile(print, outputPath);
            
            log.info("PDF report generated successfully: " + outputPath);
            return true;
            
        } catch (Exception e) {
            log.error("PDF generation error: " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Counts how many dogs have awards
     * @param dogs List of Dog objects to analyze
     * @return Number of dogs with awards
     */
    private static int countAwards(List<Dog> dogs) {
        int count = 0;
        for (int i = 0; i < dogs.getSize(); i++) {
            if (dogs.at(i).hasAward()) count++;
        }
        return count;
    }

    /**
     * Calculates percentage of dogs with awards
     * @param awardsCount Number of dogs with awards
     * @param total Total number of dogs
     * @return Percentage value (0.0 to 100.0)
     */
    private static double calculatePercentage(int awardsCount, int total) {
        return total > 0 ? (awardsCount * 100.0 / total) : 0;
    }

    /**
     * Converts List<Dog> to List<Map> format for JasperReports
     * Each Map represents one row in the report with field names matching JRXML template
     * @param dogs List of Dog objects to convert
     * @return List of Maps with report data
     */
    private static java.util.List<java.util.Map<String, Object>> convertToMapList(List<Dog> dogs) {
        java.util.List<java.util.Map<String, Object>> dataList = new java.util.ArrayList<>();
        
        for (int i = 0; i < dogs.getSize(); i++) {
            Dog dog = dogs.at(i);
            java.util.Map<String, Object> row = new java.util.HashMap<>();
            
            // Map keys must match field names in JRXML templates
            row.put("id", i + 1);                    
            row.put("name", dog.getName());        
            row.put("breed", dog.getBreed());       
            row.put("awardsText", dog.hasAward() ? "✅ YES" : "❌ NO");
            row.put("awards", dog.hasAward());    
            
            dataList.add(row);
        }
        
        return dataList;
    }

    /**
     * Creates parameters for JasperReports templates
     * These parameters are used in report headers, footers, and summary sections
     * @param xmlPath Source XML file path
     * @param totalDogs Total number of dogs
     * @param awardsCount Number of dogs with awards
     * @param percentage Percentage of dogs with awards
     * @return Map of report parameters
     */
    private static java.util.Map<String, Object> createParams(String xmlPath, int totalDogs, 
                                                              int awardsCount, double percentage) {
        java.util.Map<String, Object> params = new java.util.HashMap<>();
        
        // Report metadata
        params.put("REPORT_TITLE", "Dog Festival Report");
        params.put("LAB_NUMBER", "Laboratory Work #7");
        params.put("GENERATION_DATE", new Date()); 
        params.put("SOURCE_FILE", xmlPath);         
        
        // Report statistics
        params.put("TOTAL_DOGS", totalDogs);       
        params.put("AWARDS_COUNT", awardsCount);    
        params.put("AWARDS_PERCENTAGE", percentage); 
        
        return params;
    }
}