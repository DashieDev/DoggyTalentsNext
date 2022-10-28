package doggytalents.common.network.packet.data;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class SendSkinData extends DogData {

    public final byte[] image;

    public SendSkinData(int entityId, InputStream imageIn) throws IOException {
        this(entityId, IOUtils.toByteArray(imageIn));
    }

    public SendSkinData(int entityId, byte[] image) {
        super(entityId);
        this.image = image;
    }
}
