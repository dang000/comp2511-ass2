package unsw.dungeon;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Create dungeon JavaFx application
 * 
 * @author Anthony Dang
 * @author Mozamel Anwary
 *
 */

public class DungeonApplication extends Application {

    public static int levelIndex = 0;
    public static Stage mainStage;
    private static List<String> dungeons = Arrays.asList(
            "marking.json",
            "lvl2.json",
            "lvl3.json",
            "lvl4.json"
    );

    //Can also be used for restart
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.mainStage = primaryStage;
        processLoad(primaryStage, dungeons.get(levelIndex));
    }

    /**
     * Load the next dungeon level
     * @throws IOException
     */
    public static void loadNextLevel() throws IOException {
        if(++levelIndex < dungeons.size()) {
            processLoad(mainStage, dungeons.get(levelIndex));
        } else {
            mainStage.close();
        }
    }

    /**
     * Restart the current dungeon level
     * @throws IOException
     */
    public static void restartLevel() throws IOException {
        processLoad(mainStage, dungeons.get(levelIndex));
    }

    /**
     * Load application
     * @param primaryStage
     * @param dungeon
     * @throws IOException
     */
    public static void processLoad(Stage primaryStage, String dungeon) throws IOException {
        primaryStage.close();
        primaryStage.setTitle("Dungeon");
        DungeonControllerLoader dungeonLoader;

        dungeonLoader = new DungeonControllerLoader(dungeon);
        DungeonController controller = dungeonLoader.loadController();

        FXMLLoader loader = new FXMLLoader(DungeonApplication.class.getResource("DungeonView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
