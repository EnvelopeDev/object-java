package ScreenForms;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import report.ReportGenerator;

/**
 * Window for generating reports in PDF and HTML formats
 */
public class AddReportWindow extends InputOutputWindow
{  
    /** Logger for this class */
    private static final Logger log = Logger.getLogger(AddReportWindow.class);
    
    /** Main panel containing report options */
    private JPanel mainPanel;
    /** Button group for radio buttons */
    private ButtonGroup RadioButtonsGroup;
    /** Panel containing radio buttons */
    private JPanel radioButtonsPanel;
    /** Radio button for PDF report */
    private JRadioButton pdfRadio;
    /** Radio button for HTML report */
    private JRadioButton htmlRadio;
    /** Radio button for both report formats */
    private JRadioButton bothRadio;
    /** Path to the XML file containing dog data */
    private static final String XML_FILE_PATH = "src/data/dogs.xml";
    
    /**
     * Creates window for report generation
     */
    public AddReportWindow()
    {
        super("Select report type", "Generate Report", 0, 0);
        log.debug("Initializing report generation window");
        
        //Create panel for options with padding
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        //Radio buttons for report type selection
        radioButtonsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        radioButtonsPanel.setMaximumSize(new Dimension(400, 100));
        
        RadioButtonsGroup = new ButtonGroup();
        pdfRadio = new JRadioButton("PDF Report");
        htmlRadio = new JRadioButton("HTML Report");
        bothRadio = new JRadioButton("Both Formats");
        
        pdfRadio.setSelected(true);
        RadioButtonsGroup.add(pdfRadio);
        RadioButtonsGroup.add(htmlRadio);
        RadioButtonsGroup.add(bothRadio);

        pdfRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        htmlRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        bothRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        
        radioButtonsPanel.add(pdfRadio);
        radioButtonsPanel.add(htmlRadio);
        radioButtonsPanel.add(bothRadio);

        //Add components to main panel
        mainPanel.add(radioButtonsPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        super.IOPanel.add(mainPanel, BorderLayout.CENTER);
        //Set minimum window size
        super.IODialog.setMinimumSize(new Dimension(450, 150));
        //Center the window
        super.IODialog.setLocationRelativeTo(null);
        //Repack window for proper display
        super.IODialog.pack();
        
        log.debug("Report window initialization completed");
    }
 
    /**
     * Shows the report generation window
     * @throws InputException if there's an error during report generation
     */
    @Override
    public void show() throws InputException
    {
        log.info("Showing report generation window");
        super.IODialog.setLocationRelativeTo(null);
        super.IODialog.setVisible(true);

        String[] reportOptions = getData();
        
        if (reportOptions != null && reportOptions[0] != null)
        {
            // Close selection dialog
            super.IODialog.dispose();
            
            String reportType = reportOptions[0];
            log.info("User selected report type: " + reportType);
            boolean success = generateReport(reportType);
            
            if (success) {
                String successMessage = "Report";
                if (reportOptions[0].equals("both")) {
                    successMessage = "Reports (PDF and HTML)";
                }
                successMessage += " generated successfully!";
                
                log.info("Report generation successful: " + successMessage);
                JOptionPane.showMessageDialog(
                    null,
                    successMessage,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                log.error("Report generation failed for type: " + reportType);
                JOptionPane.showMessageDialog(
                    null,
                    "Failed to generate report=(",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }  
        } else {
            log.debug("User cancelled report generation");
        }
    }
    
    /**
     * Generates report of selected type
     * @param reportType report type ("pdf", "html" or "both")
     * @return true if generation successful, false otherwise
     */
    private boolean generateReport(String reportType)
    {
        boolean success = false;
        
        try {
            switch(reportType) {
                case "pdf":
                    log.info("Generating PDF report");
                    success = ReportGenerator.generatePDFReport(XML_FILE_PATH, "dog_report.pdf");
                    break;
                    
                case "html":
                    log.info("Generating HTML report");
                    success = ReportGenerator.generateHTMLReport(XML_FILE_PATH, "dog_report.html");
                    break;
                    
                case "both":
                    log.info("Generating both PDF and HTML reports");
                    boolean pdfSuccess = ReportGenerator.generatePDFReport(XML_FILE_PATH, "dog_report.pdf");
                    boolean htmlSuccess = ReportGenerator.generateHTMLReport(XML_FILE_PATH, "dog_report.html");
                    success = pdfSuccess && htmlSuccess;
                    break;
                    
                default:
                    log.error("Unknown report type: " + reportType);
                    success = false;
            }
        } catch (Exception e) {
            log.error("Error generating report: " + e.getMessage(), e);
            success = false;
        }
        
        return success;
    }
}