/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjmd54unzipper;

import javafx.fxml.FXML;
import ui.UIScene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author kylemccarthy
 */
public class ExtractionUIController extends UIScene implements Unzippable {

    private Unzipper unzipper;
    
    @FXML
    protected Text sourceText;
    
    @FXML
    protected Text destText;
    
    @FXML
    protected Text statusText;
    
    @FXML
    protected ProgressBar progressBar;
    
    @FXML
    protected Button stopButton;
    
    @FXML
    protected TextFlow fileList;

    @Override
    public void setZipper(Unzipper zipper) {
        this.unzipper = zipper;
    }

    @Override
    public Unzipper getZipper() {
        return unzipper;
    }
    
    /**
     * Set the status of the application.  If the status is not set to started/RUNNING
     * disable the stop button because there is not thread to interrupt.
     * 
     * @param status 
     */
    public void setStatus(String status) {
        this.statusText.setText("Status: " + status);
        if (!status.equals("Started") && !status.equals("Running")) {
            this.stopButton.setDisable(true);
        }
    }
    
    public void setSource(String source) {
        this.sourceText.setText("Source: " + source);
    }
    
    public void setDest(String dest) {
        this.destText.setText("Destination: " + dest);
    }
    
    public void addFile(String filename) {
        this.fileList.getChildren().add(new Text(filename + "\n"));
    }

    /**
     * Load the zipper into the application and set some of the default attributes 
     * @param zipper 
     */
    public void loadUnzipper(Unzipper zipper) {
        this.unzipper = zipper;
        this.setSource(this.unzipper.getSource());
        this.setDest(this.unzipper.getDestination());
        try {
            this.unzipper.loadFile();
            this.extractZip();
        } catch (Exception ex) {
            System.out.println("Error loading the zip file");
        }
    }
    
    /**
     * Tell the thread to start the extraction process and listen for notifications
     */
    public void extractZip() {
        this.progressBar.setProgress(this.unzipper.getProgress());
        this.unzipper.setOnNotification((double progress, String status, String file) -> {
            this.progressBar.setProgress(progress);
            this.setStatus(status);
            this.addFile(file);
        });
        this.unzipper.start();
    }
    
    /**
     * Interrupt the unzipping process
     */
    public void onStopButtonClick() {
        this.unzipper.interrupt();
    }
}
