package report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import fileManager.FileManager;
import list.List;
import object.dog.Dog;
import java.util.*;

/**
 * Инструмент для генерации отчетов - использует существующий FileManager
 */
public class ReportGenerator {
    
    // Пути к JRXML шаблонам
    private static final String PDF_TEMPLATE = "src/report/pdf_report.jrxml";
    private static final String HTML_TEMPLATE = "src/report/html_report.jrxml";
    
    /**
     * Класс данных для JasperReports
     */
    public static class DogData {
        private int id;
        private String name;
        private String breed;
        private boolean awards;
        
        public DogData(int id, String name, String breed, boolean awards) {
            this.id = id;
            this.name = name;
            this.breed = breed;
            this.awards = awards;
        }
        
        // Геттеры для JasperReports
        public int getId() { return id; }
        public String getName() { return name; }
        public String getBreed() { return breed; }
        public boolean getAwards() { return awards; }
        public String getAwardsText() { return awards ? "YES" : "NO"; }
    }
    
    /**
     * Генерирует PDF отчет
     */
    public static boolean generatePDFReport(String xmlPath, String outputPath) {
        try {
            // Используем FileManager для загрузки данных
            FileManager fileManager = new FileManager();
            List<Dog> dogs = fileManager.inputFromXML(xmlPath);
            
            if (dogs.getSize() == 0) return false;
            
            // Конвертируем в формат для JasperReports
            java.util.List<DogData> dogDataList = convertToJasperData(dogs);
            
            // Подсчет статистики
            int awardsCount = countAwards(dogDataList);
            double percentage = calculatePercentage(awardsCount, dogDataList.size());
            
            // Параметры для отчета
            Map<String, Object> params = createParams(xmlPath, dogDataList.size(), awardsCount, percentage);
            
            // Генерация отчета
            JasperReport report = JasperCompileManager.compileReport(PDF_TEMPLATE);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dogDataList);
            JasperPrint print = JasperFillManager.fillReport(report, params, dataSource);
            
            JasperExportManager.exportReportToPdfFile(print, outputPath);
            return true;
            
        } catch (Exception e) {
            System.err.println("PDF generation error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Генерирует HTML отчет
     */
    public static boolean generateHTMLReport(String xmlPath, String outputPath) {
        try {
            // Используем FileManager для загрузки данных
            FileManager fileManager = new FileManager();
            List<Dog> dogs = fileManager.inputFromXML(xmlPath);
            
            if (dogs.getSize() == 0) return false;
            
            // Конвертируем в формат для JasperReports
            java.util.List<DogData> dogDataList = convertToJasperData(dogs);
            
            // Подсчет статистики
            int awardsCount = countAwards(dogDataList);
            double percentage = calculatePercentage(awardsCount, dogDataList.size());
            
            // Параметры для отчета
            Map<String, Object> params = createParams(xmlPath, dogDataList.size(), awardsCount, percentage);
            
            // Генерация отчета
            JasperReport report = JasperCompileManager.compileReport(HTML_TEMPLATE);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dogDataList);
            JasperPrint print = JasperFillManager.fillReport(report, params, dataSource);
            
            JasperExportManager.exportReportToHtmlFile(print, outputPath);
            return true;
            
        } catch (Exception e) {
            System.err.println("HTML generation error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Конвертирует List<Dog> в List<DogData> для JasperReports
     */
    private static java.util.List<DogData> convertToJasperData(List<Dog> dogs) {
        java.util.List<DogData> dogDataList = new ArrayList<>();
        
        for (int i = 0; i < dogs.getSize(); i++) {
            Dog dog = dogs.at(i);
            dogDataList.add(new DogData(
                i + 1,               // ID
                dog.getName(),       // Name
                dog.getBreed(),      // Breed
                dog.hasAward()       // Awards
            ));
        }
        
        return dogDataList;
    }
    
    /**
     * Подсчитывает количество собак с наградами
     */
    private static int countAwards(java.util.List<DogData> dogs) {
        int count = 0;
        for (DogData dog : dogs) {
            if (dog.getAwards()) count++;
        }
        return count;
    }
    
    /**
     * Вычисляет процент собак с наградами
     */
    private static double calculatePercentage(int awardsCount, int total) {
        return total > 0 ? (awardsCount * 100.0 / total) : 0;
    }
    
    /**
     * Создает параметры для отчета
     */
    private static Map<String, Object> createParams(String xmlPath, int totalDogs, 
                                                   int awardsCount, double percentage) {
        Map<String, Object> params = new HashMap<>();
        params.put("REPORT_TITLE", "Dog Festival Report");
        params.put("LAB_NUMBER", "Laboratory Work #7");
        params.put("GENERATION_DATE", new Date());
        params.put("SOURCE_FILE", xmlPath);
        params.put("TOTAL_DOGS", totalDogs);
        params.put("AWARDS_COUNT", awardsCount);
        params.put("AWARDS_PERCENTAGE", percentage);
        return params;
    }
}