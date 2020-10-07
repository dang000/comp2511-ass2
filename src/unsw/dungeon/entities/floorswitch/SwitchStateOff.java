package unsw.dungeon.entities.floorswitch;

/**
 * Off state for floor switch
 */
public class SwitchStateOff implements SwitchState {

    @Override
    public boolean getIsSwitched() {
        return false;
    }

    @Override
    public void toggleSwitch(FloorSwitch floorSwitch) {
        floorSwitch.setState(new SwitchStateOn());
        floorSwitch.getDungeon().toggleSwitchCount(true);
    }
}
