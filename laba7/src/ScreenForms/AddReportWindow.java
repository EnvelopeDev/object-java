package ScreenForms;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Window for generating reports in PDF and HTML formats
 */
public class AddReportWindow extends InputOutputWindow
{
    private static final String XML_FILE_PATH = "src/data/dogs.xml";
    
    private JRadioButton pdfRadio;
    private JRadioButton htmlRadio;
    private JRadioButton bothRadio;
    private JCheckBox openAfterCheck;
    
    /**
     * Creates window for report generation
     */
    public AddReportWindow()
    {
        super("Select report type and options", "Generate Report", 0, 0);
        
        // Сделать диалог изменяемым по размеру
        super.IODialog.setResizable(true);
        super.IODialog.setPreferredSize(new Dimension(500, 300));
        
        // Убрать стандартные поля
        super.fieldsPanel.removeAll();
        
        // Создать панель для опций с отступами
        JPanel reportOptionsPanel = new JPanel();
        reportOptionsPanel.setLayout(new BoxLayout(reportOptionsPanel, BoxLayout.Y_AXIS));
        reportOptionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Заголовок
        JLabel titleLabel = new JLabel("Generate Dog Festival Report");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Радиокнопки для типа отчета
        JPanel radioPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        radioPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1), 
            "Report Type"
        ));
        radioPanel.setMaximumSize(new Dimension(400, 120));
        
        ButtonGroup reportTypeGroup = new ButtonGroup();
        pdfRadio = new JRadioButton("PDF Report");
        htmlRadio = new JRadioButton("HTML Report");
        bothRadio = new JRadioButton("Both Formats");
        
        pdfRadio.setSelected(true);
        reportTypeGroup.add(pdfRadio);
        reportTypeGroup.add(htmlRadio);
        reportTypeGroup.add(bothRadio);
        
        Font radioFont = new Font("Arial", Font.PLAIN, 14);
        pdfRadio.setFont(radioFont);
        htmlRadio.setFont(radioFont);
        bothRadio.setFont(radioFont);
        
        radioPanel.add(pdfRadio);
        radioPanel.add(htmlRadio);
        radioPanel.add(bothRadio);
        
        // Панель опций
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        optionsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1), 
            "Options"
        ));
        optionsPanel.setMaximumSize(new Dimension(400, 80));
        
        openAfterCheck = new JCheckBox("Open report after generation");
        openAfterCheck.setFont(new Font("Arial", Font.PLAIN, 14));
        openAfterCheck.setSelected(true);
        
        // Информационная панель
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setMaximumSize(new Dimension(400, 80));
        
        JLabel infoLabel = new JLabel("<html><center>XML source: " + XML_FILE_PATH + 
                                     "<br>Reports will be saved in project folder</center></html>");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        infoLabel.setForeground(Color.DARK_GRAY);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Добавить компоненты на главную панель
        reportOptionsPanel.add(titleLabel);
        reportOptionsPanel.add(Box.createVerticalStrut(20));
        reportOptionsPanel.add(radioPanel);
        reportOptionsPanel.add(Box.createVerticalStrut(15));
        reportOptionsPanel.add(optionsPanel);
        optionsPanel.add(openAfterCheck);
        reportOptionsPanel.add(Box.createVerticalStrut(15));
        reportOptionsPanel.add(infoPanel);
        infoPanel.add(infoLabel);
        
        super.fieldsPanel.add(reportOptionsPanel);
        
        // Убрать старую метку
        super.IOPanel.remove(super.IOLabel);
        super.IOPanel.add(reportOptionsPanel, BorderLayout.CENTER);
        
        // Установить минимальный размер окна
        super.IODialog.setMinimumSize(new Dimension(450, 350));
        
        // Центрировать окно
        super.IODialog.setLocationRelativeTo(null);
        
        // Пересобрать окно для правильного отображения
        super.IODialog.pack();
    }
    
    /**
     * Shows the report generation window
     * @throws InputException if there's an error
     */
    @Override
    public void show() throws InputException
    {
        // Центрировать окно при каждом показе
        super.IODialog.setLocationRelativeTo(null);
        super.IODialog.setVisible(true);
        
        String[] reportOptions = getData();
        
        if (reportOptions != null && reportOptions[0] != null)
        {
            try {
                String reportType = reportOptions[0];
                boolean openAfter = reportOptions.length > 1 && reportOptions[1].equals("true");
                
                boolean success = false;
                String generatedFile = null;
                
                // Реальная генерация отчетов
                switch(reportType) {
                    case "pdf":
                        success = report.ReportGenerator.generatePDFReport(XML_FILE_PATH, "dog_report.pdf");
                        generatedFile = "dog_report.pdf";
                        break;
                        
                    case "html":
                        success = report.ReportGenerator.generateHTMLReport(XML_FILE_PATH, "dog_report.html");
                        generatedFile = "dog_report.html";
                        break;
                        
                    case "both":
                        boolean pdfSuccess = report.ReportGenerator.generatePDFReport(XML_FILE_PATH, "dog_report.pdf");
                        boolean htmlSuccess = report.ReportGenerator.generateHTMLReport(XML_FILE_PATH, "dog_report.html");
                        success = pdfSuccess && htmlSuccess;
                        generatedFile = "both";
                        break;
                }
                
                if (success) {
                    showSuccessMessage(generatedFile, openAfter);
                } else {
                    // Если генерация не удалась
                    JOptionPane.showMessageDialog(
                        super.IODialog,
                        "Failed to generate report.\n" +
                        "Possible reasons:\n" +
                        "1. XML file not found\n" +
                        "2. Required libraries missing\n" +
                        "3. Write permissions issue",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                    super.IODialog,
                    "Report generation failed: " + e.getMessage() +
                    "\n\nMake sure:\n" +
                    "1. XML file exists: " + XML_FILE_PATH + 
                    "\n2. ReportGenerator class is available",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    /**
     * Gets selected report options
     * @return array with selected options
     */
    @Override
    public String[] getData()
    {
        if (super.IOPane.getValue() == null) {
            return null;
        }
        
        if (super.IOPane.getValue() instanceof Integer) {
            int option = ((Integer) super.IOPane.getValue()).intValue();
            
            if (option == JOptionPane.YES_OPTION || option == JOptionPane.OK_OPTION) 
            {
                String[] results = new String[2];
                
                // Определить выбранный тип отчета
                if (pdfRadio.isSelected()) {
                    results[0] = "pdf";
                } else if (htmlRadio.isSelected()) {
                    results[0] = "html";
                } else if (bothRadio.isSelected()) {
                    results[0] = "both";
                } else {
                    results[0] = null;
                }
                
                // Проверить, выбрано ли открытие после генерации
                results[1] = openAfterCheck.isSelected() ? "true" : "false";
                
                return results;
            }
        }
        
        return null;
    }
    
    /**
     * Shows success message
     * @param generatedFile name of generated file
     * @param openAfter whether to open file after generation
     */
    private void showSuccessMessage(String generatedFile, boolean openAfter) {
        String message;
        if (generatedFile.equals("both")) {
            message = "Reports generated successfully!\n" +
                     "• PDF: dog_report.pdf\n" +
                     "• HTML: dog_report.html\n\n" +
                     "Files saved in project folder.";
        } else {
            message = "Report generated successfully: " + generatedFile + 
                     "\n\nFile saved in project folder.";
        }
        
        JOptionPane.showMessageDialog(
            super.IODialog,
            message,
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Открыть отчет если запрошено
        if (openAfter && !generatedFile.equals("both")) {
            openGeneratedReport(generatedFile);
        }
    }
    
    /**
     * Opens generated report
     * @param filePath path to the report file
     */
    private void openGeneratedReport(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                if (java.awt.Desktop.isDesktopSupported()) {
                    java.awt.Desktop.getDesktop().open(file);
                }
            } else {
                JOptionPane.showMessageDialog(
                    super.IODialog,
                    "Report file not found: " + filePath,
                    "File Not Found",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        } catch (Exception e) {
            System.err.println("Cannot open report: " + e.getMessage());
        }
    }
}