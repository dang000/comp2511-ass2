package unsw.dungeon.entities.floorswitch;

/**
 * State pattern interface for floor switch
 */
public interface SwitchState {
    /**
     * Get status of floor switch
     * @return
     */
    boolean getIsSwitched();

    /**
     * Switch status of floor switch
     * @param floorSwitch Floor switch to change status of
     */
    void toggleSwitch(FloorSwitch floorSwitch);
}
