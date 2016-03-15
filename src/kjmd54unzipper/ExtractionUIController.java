/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjmd54unzipper;

import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.UIScene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

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

    @Override
    public void setZipper(Unzipper zipper) {
        this.unzipper = zipper;
    }

    @Override
    public Unzipper getZipper() {
        return unzipper;
    }
    
    public void setSource(String source) {
        this.sourceText.setText("Source: " + source);
    }
    
    public void setDest(String dest) {
        this.destText.setText("Destination: " + dest);
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
    
    public void extractZip() {
        this.progressBar.setProgress(this.unzipper.getProgress());
        this.unzipper.setOnNotification((progress, status, file) -> {
            System.out.println(progress);
            System.out.println(status);
            System.out.println(file);
        });
        this.unzipper.start();
    }
}
