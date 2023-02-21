package doggytalents.common.network.packet.data;

public class DogRegardTeamPlayersData extends DogData {

    public final boolean regardTeamPlayers;

    public DogRegardTeamPlayersData(int entityId, boolean val) {
        super(entityId);
        this.regardTeamPlayers = val;
    }
}