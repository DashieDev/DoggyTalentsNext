package doggytalents.common.network.packet.data;

public class WhisltleEditHotKeyData {
    public int hotkey_id;
    public int new_mode_id;

    public WhisltleEditHotKeyData(int hotkey_id, int new_mode_id) {
        this.hotkey_id = hotkey_id;
        this.new_mode_id = new_mode_id;
    }
}
