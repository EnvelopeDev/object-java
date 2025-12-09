package ScreenForms;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Window for generating reports in PDF and HTML formats
 * Allows user to select report type and generate reports from XML data
 */
public class AddReportWindow extends InputOutputWindow
{
    private static final String XML_FILE_PATH = "src/data/dogs.xml";
    
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
        statusLabel.setText("<html><font color='green'>✅ Ready to generate reports</font></html>");
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
                
                boolean success = false;
                String generatedFile = null;
                
                switch(reportType) {
                    case "pdf":
                        success = generateReport("pdf");
                        generatedFile = "dog_report_jasper.pdf";
                        break;
                        
                    case "html":
                        success = generateReport("html");
                        generatedFile = "dog_report_jasper.html";
                        break;
                        
                    case "both":
                        boolean pdfSuccess = generateReport("pdf");
                        boolean htmlSuccess = generateReport("html");
                        success = pdfSuccess && htmlSuccess;
                        generatedFile = "both";
                        break;
                }
                
                if (success) {
                    showSuccess("Report generated successfully!", openAfter, generatedFile);
                } else {
                    showError("Failed to generate report");
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
     * Генерирует отчет в указанном формате
     */
    private boolean generateReport(String format) {
        try {
            // Используем рефлексию для вызова методов ReportGenerator
            Class<?> reportGeneratorClass = Class.forName("report.ReportGenerator");
            
            if ("pdf".equals(format)) {
                java.lang.reflect.Method method = reportGeneratorClass.getMethod(
                    "generatePDFReport", String.class, String.class
                );
                return (boolean) method.invoke(null, XML_FILE_PATH, "dog_report_jasper.pdf");
            } else if ("html".equals(format)) {
                java.lang.reflect.Method method = reportGeneratorClass.getMethod(
                    "generateHTMLReport", String.class, String.class
                );
                return (boolean) method.invoke(null, XML_FILE_PATH, "dog_report_jasper.html");
            }
            
            return false;
        } catch (Exception e) {
            System.err.println("Error generating report: " + e.getMessage());
            e.printStackTrace();
            return false;
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
        if (openAfter && !"both".equals(fileToOpen) && fileToOpen != null) {
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