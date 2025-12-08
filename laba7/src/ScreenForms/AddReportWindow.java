package ScreenForms;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Window for generating reports in PDF and HTML formats
 * Allows user to select report type and generate reports from XML data
 * @author Ваше Имя
 * @version 1.0
 */
public class AddReportWindow extends InputOutputWindow
{
    private static final String XML_FILE_PATH = "src/data/dogs.xml";
    private static final String PDF_TEMPLATE = "dog_report_1.jrxml";
    private static final String HTML_TEMPLATE = "dog_report_2.jrxml";
    
    private JRadioButton pdfRadio;
    private JRadioButton htmlRadio;
    private JRadioButton bothRadio;
    private JCheckBox openAfterCheck;
    private JLabel statusLabel;
    
    /**
     * Creates window for report generation
     */
    public AddReportWindow()
    {
        super("Select report type and options", "Generate Report", 0, 0);
        
        // Make dialog resizable
        IODialog.setResizable(true);
        IODialog.setSize(450, 350);
        
        // Remove default text fields since we'll use custom components
        fieldsPanel.removeAll();
        
        // Create custom panel for report options
        JPanel reportOptionsPanel = new JPanel();
        reportOptionsPanel.setLayout(new BoxLayout(reportOptionsPanel, BoxLayout.Y_AXIS));
        reportOptionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title label
        JLabel titleLabel = new JLabel("<html><b>🐶 Generate Dog Festival Report</b><br>" +
                                      "<font size='-1'>Select report format and options</font></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(75, 0, 130));
        
        // Radio buttons for report type
        JPanel radioPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        radioPanel.setBorder(BorderFactory.createTitledBorder("Report Type"));
        
        ButtonGroup reportTypeGroup = new ButtonGroup();
        pdfRadio = new JRadioButton("📄 PDF Report - Formal table format");
        htmlRadio = new JRadioButton("🌐 HTML Report - Cards view");
        bothRadio = new JRadioButton("📊 Both Formats - Generate PDF and HTML");
        
        pdfRadio.setSelected(true);
        reportTypeGroup.add(pdfRadio);
        reportTypeGroup.add(htmlRadio);
        reportTypeGroup.add(bothRadio);
        
        pdfRadio.setFont(ioDefaultFont);
        htmlRadio.setFont(ioDefaultFont);
        bothRadio.setFont(ioDefaultFont);
        
        radioPanel.add(pdfRadio);
        radioPanel.add(htmlRadio);
        radioPanel.add(bothRadio);
        
        // Options panel
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
        
        openAfterCheck = new JCheckBox("Open report after generation");
        openAfterCheck.setFont(ioDefaultFont);
        openAfterCheck.setSelected(true);
        optionsPanel.add(openAfterCheck);
        
        // Status label
        statusLabel = new JLabel("");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setForeground(Color.GRAY);
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        
        // Add components to main panel
        reportOptionsPanel.add(titleLabel);
        reportOptionsPanel.add(Box.createVerticalStrut(15));
        reportOptionsPanel.add(radioPanel);
        reportOptionsPanel.add(Box.createVerticalStrut(10));
        reportOptionsPanel.add(optionsPanel);
        reportOptionsPanel.add(Box.createVerticalStrut(10));
        reportOptionsPanel.add(statusLabel);
        
        fieldsPanel.add(reportOptionsPanel);
        
        // Remove the old label since we have a new one
        IOPanel.remove(IOLabel);
        IOPanel.add(reportOptionsPanel, BorderLayout.CENTER);
        
        // Update status
        updateLibraryStatus();
    }
    
    /**
     * Updates the library status label
     */
    private void updateLibraryStatus() {
        try {
            // Используем рефлексию для проверки библиотек
            Class.forName("net.sf.jasperreports.engine.JasperCompileManager");
            statusLabel.setText("<html><font color='green'>✅ JasperReports libraries available</font></html>");
        } catch (ClassNotFoundException e) {
            statusLabel.setText("<html><font color='red'>❌ JasperReports libraries not found in libs/</font></html>");
        }
    }
    
    /**
     * Shows the report generation window
     */
    @Override
    public void show() throws InputException
    {
        showReportDialog();
    }
    
    /**
     * Displays the report dialog and handles generation
     */
    private void showReportDialog() throws InputException
    {
        IODialog.setVisible(true);
        String[] reportOptions = getData();
        
        if (reportOptions != null && reportOptions[0] != null)
        {
            try {
                String reportType = reportOptions[0];
                boolean openAfter = reportOptions.length > 1 && reportOptions[1].equals("true");
                
                // Проверяем библиотеки перед генерацией
                if (!report.ReportGenerator.checkJasperLibraries()) {
                    showError("JasperReports libraries not found!\n" +
                             "Please add JAR files to libs/ folder.");
                    return;
                }
                
                boolean success = false;
                
                switch(reportType) {
                    case "pdf":
                        success = report.ReportGenerator.generateReport(
                            XML_FILE_PATH, PDF_TEMPLATE, "dogs_report.pdf", "pdf");
                        if (success) {
                            showSuccess("PDF report generated: dogs_report.pdf", 
                                      openAfter, "dogs_report.pdf");
                        } else {
                            showError("Failed to generate PDF report");
                        }
                        break;
                        
                    case "html":
                        success = report.ReportGenerator.generateReport(
                            XML_FILE_PATH, HTML_TEMPLATE, "dogs_report.html", "html");
                        if (success) {
                            showSuccess("HTML report generated: dogs_report.html", 
                                      openAfter, "dogs_report.html");
                        } else {
                            showError("Failed to generate HTML report");
                        }
                        break;
                        
                    case "both":
                        boolean pdfSuccess = report.ReportGenerator.generateReport(
                            XML_FILE_PATH, PDF_TEMPLATE, "dogs_report.pdf", "pdf");
                        boolean htmlSuccess = report.ReportGenerator.generateReport(
                            XML_FILE_PATH, HTML_TEMPLATE, "dogs_report.html", "html");
                        
                        if (pdfSuccess && htmlSuccess) {
                            showSuccess("Both reports generated successfully!", 
                                      openAfter, null);
                        } else if (pdfSuccess || htmlSuccess) {
                            showSuccess("One report generated (check errors above)", 
                                      openAfter, pdfSuccess ? "dogs_report.pdf" : "dogs_report.html");
                        } else {
                            showError("Failed to generate both reports");
                        }
                        break;
                }
                
            } catch (Exception e) {
                throw new InputException("Report generation failed: " + e.getMessage(), 
                    InputException.ErrorType.INVALID_FORMAT);
            }
        } else {
            // User cancelled or closed the window
            System.out.println("Report generation cancelled by user");
        }
    }
    
    /**
     * Gets the selected report options
     */
    @Override
    public String[] getData()
    {
        Object value = IOPane.getValue();
        
        if (value != null && value instanceof Integer) {
            int option = ((Integer) value).intValue();
            
            if (option == JOptionPane.YES_OPTION || option == JOptionPane.OK_OPTION) 
            {
                String[] results = new String[2];
                
                // Determine selected report type
                if (pdfRadio.isSelected()) {
                    results[0] = "pdf";
                } else if (htmlRadio.isSelected()) {
                    results[0] = "html";
                } else if (bothRadio.isSelected()) {
                    results[0] = "both";
                } else {
                    results[0] = null;
                }
                
                // Check if open after generation is selected
                results[1] = openAfterCheck.isSelected() ? "true" : "false";
                
                return results;
            }
        }
        
        // User cancelled or closed the window
        return null;
    }
    
    /**
     * Shows a success message
     */
    private void showSuccess(String message, boolean openAfter, String fileToOpen) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel label = new JLabel("<html><center><b>✅ Success!</b><br><br>" + 
                                 message + "</center></html>", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(label, BorderLayout.CENTER);
        
        JOptionPane.showMessageDialog(
            IODialog,
            panel,
            "Report Generated",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Open the report if requested
        if (openAfter && fileToOpen != null) {
            openGeneratedReport(fileToOpen);
        }
    }
    
    /**
     * Opens the generated report in default application
     */
    private void openGeneratedReport(String filePath)
    {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                if (java.awt.Desktop.isDesktopSupported()) {
                    java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                    if (desktop.isSupported(java.awt.Desktop.Action.OPEN)) {
                        desktop.open(file);
                    }
                }
            } else {
                showError("Report file not found: " + filePath);
            }
        } catch (Exception e) {
            showError("Cannot open report: " + e.getMessage());
        }
    }
    
    /**
     * Shows an error message
     */
    private void showError(String errorMessage)
    {
        JOptionPane.showMessageDialog(
            IODialog,
            errorMessage,
            "Report Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}