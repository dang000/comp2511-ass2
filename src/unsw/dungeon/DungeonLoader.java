package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import unsw.dungeon.entities.*;
import unsw.dungeon.entities.floorswitch.FloorSwitch;
import unsw.dungeon.entities.items.Key;
import unsw.dungeon.entities.items.Potion;
import unsw.dungeon.entities.items.Sword;
import unsw.dungeon.entities.items.Treasure;
import unsw.dungeon.goals.generate.*;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;
    private Dungeon dungeon;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        this.dungeon = new Dungeon(width, height);

        //Generate goals
        JSONObject jsonGoals = json.getJSONObject("goal-condition");
        Goal goalParent = generateGoals(jsonGoals);
        this.dungeon.setParentGoal(goalParent);
        System.out.println(goalParent.toString());

        //Generate Entities
        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            if(jsonEntities.getJSONObject(i).getString("type").equals("player")) {
                loadEntity(dungeon, jsonEntities.getJSONObject(i));
                break;
            }
        }

        for (int i = 0; i < jsonEntities.length(); i++) {
            if (!jsonEntities.getJSONObject(i).getString("type").equals("player")) {
                loadEntity(dungeon, jsonEntities.getJSONObject(i));
            }
        }

        return dungeon;
    }

    /**
     * Recursive goal generator
     * @param thisGoal
     * @return
     */
    public Goal generateGoals(JSONObject thisGoal) {
        JSONArray subgoals;

        switch (thisGoal.getString("goal")) {
            case "AND":
                subgoals = thisGoal.getJSONArray("subgoals");
                GoalCompositeAnd goalCompositeAnd = new GoalCompositeAnd();

                for(int i = 0; i < subgoals.length(); i++) {
                    goalCompositeAnd.addSubgoal(generateGoals(subgoals.getJSONObject(i)));
                }

                return goalCompositeAnd;
            case "OR":
                subgoals = thisGoal.getJSONArray("subgoals");
                GoalCompositeOr goalCompositeOr = new GoalCompositeOr();

                for(int i = 0; i < subgoals.length(); i++) {
                    goalCompositeOr.addSubgoal(generateGoals(subgoals.getJSONObject(i)));
                }

                return goalCompositeOr;
            default:
                Goal leafGoal = null;
                switch (thisGoal.getString("goal")) {
                    case "exit":
                        leafGoal = new GoalLeafExit(this.dungeon);
                        break;
                    case "enemies":
                        leafGoal = new GoalLeafEnemies(this.dungeon);
                        break;
                    case "boulders":
                        leafGoal = new GoalLeafSwitches(this.dungeon);
                        break;
                    case "treasure":
                        leafGoal = new GoalLeafTreasure(this.dungeon);
                        break;
                    default:
                        leafGoal = null;
                        break;
                }

            return leafGoal;
        }
    }

    /**
     * Loads entity into dungeon
     * @param dungeon
     * @param json
     */
    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;

        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(dungeon, x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "exit":
            Exit exit = new Exit(dungeon, x, y);
            onLoad(exit);
            entity = exit;
            break;
        case "sword":
            Sword sword = new Sword(dungeon, x, y);
            onLoad(sword);
            entity = sword;
            break;
        case "potion":
            Potion potion = new Potion(dungeon, x, y, 5);
            onLoad(potion, "potion");
            entity = potion;
            break;
        case "superpotion":
            Potion superpotion = new Potion(dungeon, x, y, 10);
            onLoad(superpotion, "superpotion");
            entity = superpotion;
            break;
        case "treasure":
            Treasure treasure = new Treasure(dungeon, x, y);
            onLoad(treasure);
            entity = treasure;
            dungeon.addMaxTreasure();
            break;
        case "boulder":
            Boulder boulder = new Boulder(dungeon, x, y);
            onLoad(boulder);
            entity = boulder;
            break;
        case "switch":
            FloorSwitch floorSwitch = new FloorSwitch(dungeon, x, y);
            onLoad(floorSwitch);
            entity = floorSwitch;
            dungeon.addMaxSwitches();
            break;
        case "door":
            //Create and link dummy door first
            OpenDoorDummy openDoorDummy =
                new OpenDoorDummy(dungeon, x, y, json.getInt("id"));
            onLoad(openDoorDummy);

            Door door = new Door(dungeon, x, y, json.getInt("id"), openDoorDummy);
            onLoad(door);

            entity = openDoorDummy;
            dungeon.addEntity(door);
            break;
        case "key":
            Key key = new Key(dungeon, x, y, json.getInt("id"));
            onLoad(key);
            entity = key;
            break;
        case "portal":
            Portal portal = new Portal(dungeon, x, y, json.getInt("id"));
            onLoad(portal);
            entity = portal;
            break;
        case "enemy":
            Enemy enemy = new Enemy(dungeon, x, y, 1, false);
            onLoad(enemy, "enemy");
            entity = enemy;
            dungeon.addMaxEnemies();
            break;
        case "hound":
            Enemy hound = new Enemy(dungeon, x, y, 0.5, false);
            onLoad(hound, "hound");
            entity = hound;
            dungeon.addMaxEnemies();
            break;
        case "gnome":
            Enemy gnome = new Enemy(dungeon, x, y, 1.5, true);
            onLoad(gnome, "gnome");
            entity = gnome;
            dungeon.addMaxEnemies();
            break;
        }

        dungeon.addEntity(entity);
    }

    /**
     * Loads imageView and adds to List of ImageView entities
     * 
     * @param entity, state entity type to create
     * @param type, for enemy and potion identify which type of enemy or potion
     */
    public abstract void onLoad(Enemy enemy, String type);
    public abstract void onLoad(Key key);
    public abstract void onLoad(Portal portal);
    public abstract void onLoad(Treasure treasure);
    public abstract void onLoad(Boulder boulder);
    public abstract void onLoad(Door door);
    public abstract void onLoad(OpenDoorDummy openDoorDummy);
    public abstract void onLoad(Potion potion, String type);
    public abstract void onLoad(FloorSwitch floorSwitch);
    public abstract void onLoad(Sword sword);
    public abstract void onLoad(Exit exit);
    public abstract void onLoad(Player player);
    public abstract void onLoad(Wall wall);
}
