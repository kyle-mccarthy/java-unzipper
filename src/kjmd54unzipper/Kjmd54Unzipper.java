/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjmd54unzipper;

import ui.UIScene;
import ui.UIStage;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author kylemccarthy
 */
public class Kjmd54Unzipper extends Application {
    
    private UIStage mainUIStage;
    private UIScene startUIScene;
    private Unzipper unzipper;
    
    @Override
    public void start(Stage stage) throws Exception {
        this.mainUIStage = new UIStage(stage);
        
        try {
            this.startUIScene = mainUIStage.loadScene("MainUI", getClass().getResource("MainUI.fxml"));
        } catch (Exception e) {
            System.out.println("error loading the scene");
        }
        
        this.mainUIStage.displayScene(startUIScene);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
