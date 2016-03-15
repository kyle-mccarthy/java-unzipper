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

/**
 * FXML Controller class
 *
 * @author kylemccarthy
 */
public class MainUIController extends UIScene implements Unzippable {
    
    @FXML
    public Button selectZipButton;
    
    @FXML
    public Button selectDestButton;
    
    @FXML
    public Text zipLocationText;
    
    @FXML
    public Text destLocationText;
    
    public Unzipper unzipper;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.unzipper = new Unzipper();
        System.out.println("unzipper created");
    };

    @Override
    public void setZipper(Unzipper zipper) {
        this.unzipper = zipper;
    }

    @Override
    public Unzipper getZipper() {
        return unzipper;
    }
    
}
