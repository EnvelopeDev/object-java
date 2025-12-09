package report;

import java.util.*;
import java.io.*;
import java.util.Date;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportGenerator {
    
    // Пути к шаблонам
    private static final String PDF_TEMPLATE = "src/report/pdf_report.jrxml";
    private static final String HTML_TEMPLATE = "src/report/html_report.jrxml";
    
    // Класс для данных (вместо вашего Dog)
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
        
        public int getId() { return id; }
        public String getName() { return name; }
        public String getBreed() { return breed; }
        public boolean getAwards() { return awards; }
        public String getAwardsText() { return awards ? "YES" : "NO"; }
        public String getStatus() { return awards ? "AWARDED" : "NO AWARDS"; }
    }
    
    // Загрузка данных из XML (без FileManager)
    private static List<DogData> loadDogsFromXML(String xmlPath) throws Exception {
        List<DogData> dogs = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(xmlPath));
        String line;
        int id = 1;
        
        while ((line = reader.readLine()) != null) {
            if (line.trim().startsWith("<dog>")) {
                String name = "", breed = "";
                boolean awards = false;
                
                while ((line = reader.readLine()) != null && !line.trim().contains("</dog>")) {
                    line = line.trim();
                    if (line.startsWith("<name>")) {
                        name = line.replace("<name>", "").replace("</name>", "").trim();
                    } else if (line.startsWith("<breed>")) {
                        breed = line.replace("<breed>", "").replace("</breed>", "").trim();
                    } else if (line.startsWith("<awards>")) {
                        String awardStr = line.replace("<awards>", "").replace("</awards>", "").trim();
                        awards = awardStr.equalsIgnoreCase("true");
                    }
                }
                
                if (!name.isEmpty() && !breed.isEmpty()) {
                    dogs.add(new DogData(id++, name, breed, awards));
                }
            }
        }
        
        reader.close();
        return dogs;
    }
    
    public static boolean generateReport(String xmlPath, String template, String output, String format) {
        try {
            System.out.println("🎯 Starting report generation...");
            
            // Проверка файлов
            File templateFile = new File(template);
            if (!templateFile.exists()) {
                System.err.println("❌ Template not found: " + template);
                return false;
            }
            
            File xmlFile = new File(xmlPath);
            if (!xmlFile.exists()) {
                System.err.println("❌ XML not found: " + xmlPath);
                return false;
            }
            
            // Загрузка данных
            List<DogData> dogs = loadDogsFromXML(xmlPath);
            System.out.println("🐕 Loaded " + dogs.size() + " dogs from XML");
            
            if (dogs.isEmpty()) {
                System.err.println("⚠️ No dogs found in XML");
                return false;
            }
            
            // Подсчет статистики
            int awardsCount = 0;
            for (DogData dog : dogs) {
                if (dog.getAwards()) awardsCount++;
            }
            double percentage = dogs.size() > 0 ? (awardsCount * 100.0 / dogs.size()) : 0;
            
            // Создание datasource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dogs);
            
            // Параметры отчета
            Map<String, Object> params = new HashMap<>();
            params.put("REPORT_TITLE", "Dog Festival Report");
            params.put("LAB_NUMBER", "Laboratory Work #7");
            params.put("GENERATION_DATE", new Date());
            params.put("SOURCE_FILE", xmlPath);
            params.put("TOTAL_DOGS", dogs.size());
            params.put("AWARDS_COUNT", awardsCount);
            params.put("AWARDS_PERCENTAGE", percentage);
            
            // Компиляция и заполнение
            System.out.println("⚙️ Compiling template...");
            JasperReport report = JasperCompileManager.compileReport(template);
            
            System.out.println("🎨 Filling report with data...");
            JasperPrint print = JasperFillManager.fillReport(report, params, dataSource);
            
            // Экспорт
            if ("pdf".equalsIgnoreCase(format)) {
                JasperExportManager.exportReportToPdfFile(print, output);
                System.out.println("✅ PDF created: " + output);
                System.out.println("   📄 Type: Formal document (simple layout)");
                return true;
            } else if ("html".equalsIgnoreCase(format)) {
                JasperExportManager.exportReportToHtmlFile(print, output);
                System.out.println("✅ HTML created: " + output);
                System.out.println("   🎨 Type: Modern webpage (colorful design)");
                return true;
            }
            
            return false;
            
        } catch (Exception e) {
            System.err.println("💥 Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Методы для удобства
    public static boolean generatePDFReport(String xmlPath, String outputPath) {
        return generateReport(xmlPath, PDF_TEMPLATE, outputPath, "pdf");
    }
    
    public static boolean generateHTMLReport(String xmlPath, String outputPath) {
        return generateReport(xmlPath, HTML_TEMPLATE, outputPath, "html");
    }
    
    public static boolean generateBothReports(String xmlPath) {
        System.out.println("\n📊 Generating both report formats...");
        System.out.println("==================================");
        
        boolean pdfOk = generatePDFReport(xmlPath, "dog_festival_formal.pdf");
        
        System.out.println("\n----------------------------------");
        
        boolean htmlOk = generateHTMLReport(xmlPath, "dog_festival_colorful.html");
        
        System.out.println("\n==================================");
        System.out.println("📋 Generation Results:");
        System.out.println("  PDF (Formal): " + (pdfOk ? "✅ Success" : "❌ Failed"));
        System.out.println("  HTML (Colorful): " + (htmlOk ? "✅ Success" : "❌ Failed"));
        
        return pdfOk && htmlOk;
    }
    
    // Проверка библиотек
    public static boolean checkJasperLibraries() {
        try {
            Class.forName("net.sf.jasperreports.engine.JasperReport");
            System.out.println("✅ JasperReports library found");
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("❌ JasperReports not found");
            return false;
        }
    }
    
    // Главный метод
    public static void main(String[] args) {
        try {
            System.out.println("🐾 ==================================");
            System.out.println("🐾 Dog Festival Report Generator v2.0");
            System.out.println("🐾 ==================================\n");
            
            if (!checkJasperLibraries()) {
                System.err.println("Cannot generate reports. Missing libraries.");
                System.exit(1);
            }
            
            // Создание шаблонов если их нет
            createTemplatesIfNeeded();
            
            // Генерация отчетов
            boolean success = generateBothReports("src/data/dogs.xml");
            
            if (success) {
                System.out.println("\n🎊 All reports generated successfully!");
                System.out.println("   📄 dog_festival_formal.pdf - Formal PDF document");
                System.out.println("   🌐 dog_festival_colorful.html - Colorful HTML webpage");
            } else {
                System.out.println("\n⚠️ Some reports failed to generate");
            }
            
        } catch (Exception e) {
            System.err.println("💥 Error in main: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Создание шаблонов если их нет
    private static void createTemplatesIfNeeded() {
        try {
            // Создаем простые шаблоны если их нет
            if (!new File(PDF_TEMPLATE).exists()) {
                createSimplePDFTemplate();
            }
            if (!new File(HTML_TEMPLATE).exists()) {
                createSimpleHTMLTemplate();
            }
        } catch (Exception e) {
            System.err.println("Error creating templates: " + e.getMessage());
        }
    }
    
    private static void createSimplePDFTemplate() throws Exception {
        java.io.PrintWriter out = new java.io.PrintWriter(PDF_TEMPLATE, "UTF-8");
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println("<jasperReport name=\"DogReportPDF\" pageWidth=\"595\" pageHeight=\"842\">");
        out.println("  <parameter name=\"REPORT_TITLE\" class=\"java.lang.String\"/>");
        out.println("  <parameter name=\"TOTAL_DOGS\" class=\"java.lang.Integer\"/>");
        out.println("  <field name=\"id\" class=\"java.lang.Integer\"/>");
        out.println("  <field name=\"name\" class=\"java.lang.String\"/>");
        out.println("  <field name=\"breed\" class=\"java.lang.String\"/>");
        out.println("  <field name=\"awardsText\" class=\"java.lang.String\"/>");
        out.println("  <title>");
        out.println("    <band height=\"50\">");
        out.println("      <staticText>");
        out.println("        <reportElement x=\"0\" y=\"0\" width=\"555\" height=\"30\"/>");
        out.println("        <textElement>");
        out.println("          <font size=\"20\" isBold=\"true\"/>");
        out.println("        </textElement>");
        out.println("        <text>Dog Festival Report</text>");
        out.println("      </staticText>");
        out.println("    </band>");
        out.println("  </title>");
        out.println("  <detail>");
        out.println("    <band height=\"20\">");
        out.println("      <textField>");
        out.println("        <reportElement x=\"0\" y=\"0\" width=\"50\" height=\"20\"/>");
        out.println("        <textFieldExpression><![CDATA[$F{id}]]></textFieldExpression>");
        out.println("      </textField>");
        out.println("      <textField>");
        out.println("        <reportElement x=\"50\" y=\"0\" width=\"150\" height=\"20\"/>");
        out.println("        <textFieldExpression><![CDATA[$F{name}]]></textFieldExpression>");
        out.println("      </textField>");
        out.println("      <textField>");
        out.println("        <reportElement x=\"200\" y=\"0\" width=\"150\" height=\"20\"/>");
        out.println("        <textFieldExpression><![CDATA[$F{breed}]]></textFieldExpression>");
        out.println("      </textField>");
        out.println("      <textField>");
        out.println("        <reportElement x=\"350\" y=\"0\" width=\"100\" height=\"20\"/>");
        out.println("        <textFieldExpression><![CDATA[$F{awardsText}]]></textFieldExpression>");
        out.println("      </textField>");
        out.println("    </band>");
        out.println("  </detail>");
        out.println("</jasperReport>");
        out.close();
        System.out.println("📄 Created PDF template");
    }
    
    private static void createSimpleHTMLTemplate() throws Exception {
        java.io.PrintWriter out = new java.io.PrintWriter(HTML_TEMPLATE, "UTF-8");
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println("<jasperReport name=\"DogReportHTML\" pageWidth=\"595\" pageHeight=\"842\">");
        out.println("  <field name=\"id\" class=\"java.lang.Integer\"/>");
        out.println("  <field name=\"name\" class=\"java.lang.String\"/>");
        out.println("  <field name=\"breed\" class=\"java.lang.String\"/>");
        out.println("  <field name=\"awardsText\" class=\"java.lang.String\"/>");
        out.println("  <title>");
        out.println("    <band height=\"80\">");
        out.println("      <staticText>");
        out.println("        <reportElement x=\"0\" y=\"0\" width=\"555\" height=\"40\"/>");
        out.println("        <textElement markup=\"html\">");
        out.println("          <font size=\"24\" isBold=\"true\"/>");
        out.println("        </textElement>");
        out.println("        <text><![CDATA[<h1 style='color: #667eea; text-align: center;'>Dog Festival Report</h1>]]></text>");
        out.println("      </staticText>");
        out.println("    </band>");
        out.println("  </title>");
        out.println("  <detail>");
        out.println("    <band height=\"30\">");
        out.println("      <textField>");
        out.println("        <reportElement x=\"0\" y=\"0\" width=\"50\" height=\"30\"/>");
        out.println("        <textFieldExpression><![CDATA[$F{id}]]></textFieldExpression>");
        out.println("      </textField>");
        out.println("      <textField>");
        out.println("        <reportElement x=\"50\" y=\"0\" width=\"150\" height=\"30\"/>");
        out.println("        <textFieldExpression><![CDATA[\"<strong>\" + $F{name} + \"</strong>\"]]></textFieldExpression>");
        out.println("      </textField>");
        out.println("      <textField>");
        out.println("        <reportElement x=\"200\" y=\"0\" width=\"150\" height=\"30\"/>");
        out.println("        <textFieldExpression><![CDATA[$F{breed}]]></textFieldExpression>");
        out.println("      </textField>");
        out.println("      <textField>");
        out.println("        <reportElement x=\"350\" y=\"0\" width=\"100\" height=\"30\"/>");
        out.println("        <textFieldExpression><![CDATA[");
        out.println("          $F{awardsText}.equals(\"YES\") ? ");
        out.println("          \"<span style='color: green; font-weight: bold;'>✅ \" + $F{awardsText} + \"</span>\" : ");
        out.println("          \"<span style='color: #666;'>❌ \" + $F{awardsText} + \"</span>\"");
        out.println("        ]]></textFieldExpression>");
        out.println("      </textField>");
        out.println("    </band>");
        out.println("  </detail>");
        out.println("</jasperReport>");
        out.close();
        System.out.println("🌐 Created HTML template");
    }
}