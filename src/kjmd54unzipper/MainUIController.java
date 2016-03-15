/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjmd54unzipper;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import ui.UIScene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import java.io.File;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kylemccarthy
 */
public class MainUIController extends UIScene implements Unzippable {
    
    @FXML
    protected Button selectZipButton;
    
    @FXML
    protected Button selectDestButton;
    
    @FXML
    protected Button extractButton;
    
    @FXML
    protected Text zipLocationText;
    
    @FXML
    protected Text destLocationText;
    
    @FXML
    protected FileChooser fileChooser;
    
    private Unzipper unzipper;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.unzipper = new Unzipper();
        this.fileChooser = new FileChooser();
    };

    @Override
    public void setZipper(Unzipper zipper) {
        this.unzipper = zipper;
    }

    @Override
    public Unzipper getZipper() {
        return unzipper;
    }
    
    /**
     * Set the value of the source text on the UI.  Default the valid flag to true
     * 
     * @param source 
     */
    public void setSource(String source) {
        this.setSource(source, true);
    }
    
    /**
     * Set the value of the source text on the UI and allows for a boolean variable
     * to be passed that indicates whether the path is invalid or valid
     * 
     * @param source
     * @param valid 
     */
    public void setSource(String source, Boolean valid) {
        if (!valid) {
            this.zipLocationText.setFill(Color.RED);
        } else {
            this.zipLocationText.setFill(Color.BLACK);
        }
        this.zipLocationText.setText("Source: " + source);
    }
    
    public void setDestination(String dest) {
        this.destLocationText.setText("Destination: " + dest);
    }
    
    /**
     * Handle the action of clicking the source button.  This will open a file picker
     * for the user to choose from, and will determine if the selected file was a valid
     * zip file.  It will then set the source text to the path of the zip.
     */
    public void onSelectZipButtonClick() {
        File file = fileChooser.showOpenDialog(this.selectZipButton.getScene().getWindow());
        // if the file exists, if the 'file' is a file, and if the file is a zip
        // set the source on the zipper, if it isn't display the path but color it
        // red to indicate that there was an error
        if (file.exists() && file.isFile() && file.getName().substring(file.getName().lastIndexOf('.') + 1).equals("zip")) {
            this.unzipper.setSource(file.getAbsolutePath());
            this.setSource(this.unzipper.getSource());
        } else {
            this.setSource(file.getAbsolutePath(), false);
        }
    }
    
}
