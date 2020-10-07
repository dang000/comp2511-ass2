package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import unsw.dungeon.entities.*;
import unsw.dungeon.entities.floorswitch.FloorSwitch;
import unsw.dungeon.entities.items.Key;
import unsw.dungeon.entities.items.Potion;
import unsw.dungeon.entities.items.Sword;
import unsw.dungeon.entities.items.Treasure;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;

    //Images
    private Image playerImage;
    private Image wallImage;
    private Image enemyImage;
    private Image houndImage;
    private Image gnomeImage;
    private Image keyImage;
    private Image portalImage;
    private Image treasureImage;
    private Image boulderImage;
    private Image doorImageClosed;
    private Image doorImageOpened;
    private Image potionImage;
    private Image superpotionImage;
    private Image floorSwitchImage;
    private Image swordImage;
    private Image exitImage;

    /** 
     * Class constructor for DungeonController
     * 
     * Parse in a file
     * Generate images
     * @param filename
     * @throws FileNotFoundException
     */
    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        playerImage = new Image("/human_new.png");
        wallImage = new Image("/brick_brown_0.png");
        enemyImage = new Image("/deep_elf_master_archer.png");
        houndImage = new Image("/hound.png");
        gnomeImage = new Image("/gnome.png");
        keyImage = new Image("/key.png");
        portalImage = new Image("/portal.png");
        treasureImage = new Image("/gold_pile.png");
        boulderImage = new Image("/boulder.png");
        doorImageClosed = new Image("/closed_door.png");
        doorImageOpened = new Image("/open_door.png");
        potionImage = new Image("/bubbly.png");
        superpotionImage = new Image("/brilliant_blue_new.png");
        floorSwitchImage = new Image("/pressure_plate.png");
        swordImage = new Image("/greatsword_1_new.png");
        exitImage = new Image("/exit.png");
    }

    @Override
    public void onLoad(Enemy enemy, String type) {
        ImageView view;

        switch(type) {
            case "hound":
                view = new ImageView(houndImage);
                break;
            case "gnome":
                view = new ImageView(gnomeImage);
                break;
            default:
                view = new ImageView(enemyImage);
                break;
        }

        addEntity(enemy, view);
    }

    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key, view);
    }

    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);
    }

    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        addEntity(treasure, view);
    }

    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
    }

    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(doorImageClosed);
        addEntity(door, view);
    }

    @Override
    public void onLoad(OpenDoorDummy openDoorDummy) {
        ImageView view = new ImageView(doorImageOpened);
        addEntity(openDoorDummy, view);
    }

    @Override
    public void onLoad(Potion potion, String type) {
        ImageView view;

        switch(type) {
            case "superpotion":
                view = new ImageView(superpotionImage);
                break;
            default:
                view = new ImageView(potionImage);
                break;
        }

        addEntity(potion, view);
    }

    @Override
    public void onLoad(FloorSwitch floorSwitch) {
        ImageView view = new ImageView(floorSwitchImage);
        addEntity(floorSwitch, view);
    }

    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addEntity(sword, view);
    }

    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }

    @Override
    public void onLoad(Player player) {
        ImageView view = new ImageView(playerImage);
        view.setViewOrder(-1000);
        addEntity(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }

    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController() throws FileNotFoundException {
        return new DungeonController(load(), entities);
    }
}
