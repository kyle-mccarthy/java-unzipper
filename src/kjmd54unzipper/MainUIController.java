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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

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
    protected Text extractionMessage;
    
    @FXML
    protected FileChooser fileChooser;
    
    @FXML
    protected DirectoryChooser dirChooser;
    
    private Unzipper unzipper;
    private Boolean validSource;
    private Boolean validDest;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.unzipper = new Unzipper();
        this.fileChooser = new FileChooser();
        this.dirChooser = new DirectoryChooser();
        this.validSource = false;
        this.validDest = false;
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
            this.validSource = false;
        } else {
            this.zipLocationText.setFill(Color.BLACK);
            this.validSource = true;
        }
        this.zipLocationText.setText("Source: " + source);
    }
    
    /**
     * Set the value of the destination on the UI.  Default the valid flag to true
     * 
     * @param dest 
     */
    public void setDestination(String dest) {
        this.setDestination(dest, true);
    }
    
    /**
     * Set the value of the destination text on the UI.  If the path is valid set
     * the color to black, and if it isn't set the color to red to warn the user.
     * 
     * @param dest
     * @param valid 
     */
    public void setDestination(String dest, Boolean valid) {
        if (!valid) {
            this.destLocationText.setFill(Color.RED);
            this.validDest = false;
        } else {
            this.destLocationText.setFill(Color.BLACK);
            this.validDest = true;
        }
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
        if (file != null) {
            if (file.exists() && file.isFile() && file.getName().substring(file.getName().lastIndexOf('.') + 1).equals("zip")) {
                this.unzipper.setSource(file.getAbsolutePath());
                this.setSource(this.unzipper.getSource());
            } else {
                this.setSource(file.getAbsolutePath(), false);
            }
        }
    }
    
    /**
     * Handle the action of click the dest button.  Will open up a dialog window to 
     * allow for the user to select a desired source and will validate the selection.
     */
    public void onSelectDestButtonClick() {
        File file = dirChooser.showDialog(this.selectDestButton.getScene().getWindow());
        // check if the file exsits, we want to make sure that the selected file is a folder
        if (file != null) {
            if (file.exists() && file.isDirectory()) {
                this.unzipper.setDestination(file.getAbsolutePath());
                this.setDestination(this.unzipper.getDestination());
            } else {
                this.setDestination(file.getAbsolutePath(), false);
            }
        }
    }
    
    /**
     * When the extract button is clicked validate the source and destination.  IFF
     * both are valid then perform the extraction process.  Otherwise display an
     * appropriate error message.
     */
    public void onExtractButtonClick() {
        if (!this.validSource) {
            this.extractionMessage.setText("Invalid source.  Must be existing zip file.");
            this.extractionMessage.setFill(Color.RED);
        } else if (!this.validDest) {
            this.extractionMessage.setText("Invalid destination.  Must be directory.");
            this.extractionMessage.setFill(Color.RED);
        } else {
            this.extractionMessage.setText("");
            UIScene extract;
            try {
                extract = this.getUIStage().loadScene("ExtractionUI", getClass().getResource("ExtractionUI.fxml"));
            } catch (Exception ex) {
                System.out.println("Error loading the exraction scene");
                return;
            }
            ((ExtractionUIController)extract).loadUnzipper(this.unzipper);
            this.getUIStage().displayScene(extract);
        }
    }
    
}
