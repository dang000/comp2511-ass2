package unsw.dungeon.entities.floorswitch;

/**
 * On state for floor switch
 */
public class SwitchStateOn implements SwitchState {

    @Override
    public boolean getIsSwitched() {
        return true;
    }

    @Override
    public void toggleSwitch(FloorSwitch floorSwitch) {
        floorSwitch.setState(new SwitchStateOff());
        floorSwitch.getDungeon().toggleSwitchCount(false);
    }
}
