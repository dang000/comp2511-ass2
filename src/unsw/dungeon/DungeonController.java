package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import unsw.dungeon.entities.Player;
import unsw.dungeon.ui.UIText;

/**
 * A JavaFX controller for the dungeon.
 * @author Anthony Dang, Mozamel Anwary
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;

    @FXML
    private StackPane stackPaneMain;

    @FXML
    private Button restartButton;

    private List<ImageView> initialEntities;
    private Player player;
    private Dungeon dungeon;

    private int uiDist = 3;
    private int uiDistSide = 12;
    private int gridSizeMain = 20;

    private boolean isKeyPressed = false;

    /**
     * Class constructor for DungeonController
     * @param dungeon
     * @param initialEntities
     */
    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
    }

    /**
     * Initialize the UI
     */
    @FXML
    public void initialize() {
        Image ground = new Image("/dirt_0_new.png");
        Image uiBg = new Image("/ui_bg_2.png");
        Image uiBgSecondary = new Image("/ui_bg.png");
        Image uiBgBlack = new Image("/ui_bg_black.png");
        Image uiSword = new Image("/greatsword_1_new.png");
        Image uiPotion = new Image("/potions.png");
        Image uiTreasure = new Image("/gold_pile.png");
        Image uiSwitch = new Image("/pressure_plate.png");
        Image uiEnemy = new Image("/enemy_dead.png");

        int widthAdd = gridSizeMain - dungeon.getWidth();
        int heightAdd = gridSizeMain - dungeon.getHeight();
        if(widthAdd < 0) widthAdd = 0;
        if(heightAdd < 0) heightAdd = 0;

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth() + widthAdd; x++) {
            for (int y = 0; y < dungeon.getHeight() + heightAdd; y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        //Create lower UI
        for (int x = 0; x < dungeon.getWidth() + widthAdd; x++) {
            for (int y = dungeon.getHeight() + heightAdd; y < dungeon.getHeight() + heightAdd + uiDist; y++) {
                squares.add(new ImageView(uiBg), x, y);
            }
        }

        //Create sidebar UI
        for (int x = dungeon.getWidth() + widthAdd; x < dungeon.getWidth() + widthAdd + uiDistSide; x++) {
            for (int y = 0; y < dungeon.getHeight() + heightAdd + uiDist; y++) {
                if(y < 20)
                    squares.add(new ImageView(uiBgBlack), x, y);
                else
                    squares.add(new ImageView(uiBgSecondary), x, y);
            }
        }

        //Create UI overlay
        for (int x = 1; x < dungeon.getWidth() + widthAdd - 1; x++) {
            squares.add(new ImageView(uiBgSecondary), x, dungeon.getHeight() + heightAdd + 1);
        }

        //Add UI Elements
        //Restart Button
        restartButton.setMaxSize(200, 48);
        stackPaneMain.setAlignment(restartButton, Pos.TOP_RIGHT);
        restartButton.setTranslateX(-uiDistSide * 16 + (restartButton.getMaxWidth() / 2));
        restartButton.setTranslateY(660);

        //Goals text interface
        UIText goalsText = new UIText(false);
        goalsText.setText("Goals:\n" + this.dungeon.getParentGoal().toString());

        dungeon.uiGoalsText().bindBidirectional(goalsText.textProperty());
        stackPaneMain.getChildren().add(goalsText);
        stackPaneMain.setAlignment(goalsText, Pos.TOP_RIGHT);
        goalsText.setTranslateY(50);
        goalsText.setTranslateX(-uiDistSide / 2 * 16);
        goalsText.setTextAlignment(TextAlignment.LEFT);

        //Levels Text
        UIText levelsText = new UIText(false, 16);
        levelsText.setText("Level " + this.dungeon.uiLevelsText.getValue() + " / 4");

        stackPaneMain.getChildren().add(levelsText);
        stackPaneMain.setAlignment(levelsText, Pos.CENTER_RIGHT);
        levelsText.setTranslateY(250);
        levelsText.setTranslateX(-140);
        levelsText.setTextAlignment(TextAlignment.LEFT);

        //Sword interface
        UIText invSwordText = new UIText(true);

        invSwordText.setText("-/-");
        ImageView invSword = new ImageView(uiSword);
        dungeon.getPlayer().uiSwordAttackCount().bindBidirectional(invSwordText.textProperty());

        squares.add(invSword, 1, dungeon.getHeight() + heightAdd + 1);
        squares.add(invSwordText, 2, dungeon.getHeight() + heightAdd + 1);
        squares.add(new ImageView(uiBg), 3, dungeon.getHeight() + heightAdd + 1);

        //Potion interface
        UIText invPotionText = new UIText(true);
        ImageView invPotion = new ImageView(uiPotion);

        invPotionText.setText("--");
        squares.add(invPotion, 4, dungeon.getHeight() + heightAdd + 1);
        squares.add(invPotionText, 5, dungeon.getHeight() + heightAdd + 1);
        squares.add(new ImageView(uiBg), 6, dungeon.getHeight() + heightAdd + 1);
        dungeon.getPlayer().uiPotionActive().bindBidirectional(invPotionText.textProperty());

        //Treasure interface
        UIText invTreasureText = new UIText(true);
        ImageView invTreasure = new ImageView(uiTreasure);
        
        invTreasureText.setText(dungeon.getTreasurePickedUp() + "/" + dungeon.getMaxTreasure());
        squares.add(invTreasure, 7, dungeon.getHeight() + heightAdd + 1);
        squares.add(invTreasureText, 8, dungeon.getHeight() + heightAdd + 1);
        squares.add(new ImageView(uiBg), 9, dungeon.getHeight() + heightAdd + 1);
        dungeon.uiTreasureCount().bindBidirectional(invTreasureText.textProperty());

        //Switches interface
        UIText invSwitchText = new UIText(true);
        ImageView invSwitch = new ImageView(uiSwitch);
        
        invSwitchText.setText(dungeon.getSwitchesActivated() + "/" + dungeon.getMaxSwitches());
        squares.add(invSwitch, 10, dungeon.getHeight() + heightAdd + 1);
        squares.add(invSwitchText, 11, dungeon.getHeight() + heightAdd + 1);
        squares.add(new ImageView(uiBg), 12, dungeon.getHeight() + heightAdd + 1);
        dungeon.uiSwitchCount().bindBidirectional(invSwitchText.textProperty());

        //Enemies killed interface
        UIText invEnemyText = new UIText(true);
        ImageView invEnemy = new ImageView(uiEnemy);
        
        invEnemyText.setText(dungeon.getEnemiesKilled() + "/" + dungeon.getMaxEnemies());
        squares.add(invEnemy, 13, dungeon.getHeight() + heightAdd + 1);
        squares.add(invEnemyText, 14, dungeon.getHeight() + heightAdd + 1);
        squares.add(new ImageView(uiBg), 15, dungeon.getHeight() + heightAdd + 1);
        dungeon.uiEnemyCount().bindBidirectional(invEnemyText.textProperty());
        
        for (ImageView entity : initialEntities) {
            squares.getChildren().add(entity);
        }

        //Death
        squares.add(new ImageView(uiBgSecondary), dungeon.getWidth() + 1, dungeon.getHeight() + 1);
    }

    /**
     * Restart the current level
     */
    @FXML
    public void actionRestartLevel() {
        dungeon.restartLevel();
    }

    /**
     * Checks for key presses and executes action
     * @param event
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        if(!isKeyPressed) {
            isKeyPressed = true;
            switch (event.getCode()) {
                case UP:
                    player.moveUp();
                    break;
                case DOWN:
                    player.moveDown();
                    break;
                case LEFT:
                    player.moveLeft();
                    break;
                case RIGHT:
                    player.moveRight();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Checks for key releases and executes action
     * @param event
     */
    @FXML
    public void handleKeyRelease(KeyEvent event) {
        isKeyPressed = false;
    }
}

