/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjmd54unzipper;

import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void setZipper(Unzipper zipper) {
        this.unzipper = zipper;
    }

    @Override
    public Unzipper getZipper() {
        return unzipper;
    }    
    
}
