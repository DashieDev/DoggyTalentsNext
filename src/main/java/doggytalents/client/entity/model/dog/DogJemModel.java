package doggytalents.client.entity.model.dog;

import java.util.function.Consumer;

import net.minecraft.client.model.geom.ModelPart;

public class DogJemModel {


    public static class ModelPartPrepare {

        private final ModelPart target;
        protected Consumer<ModelPart> func;

        public ModelPartPrepare(ModelPart target, Consumer<ModelPart> func) {
            this.target = target;
            this.func = func;
        }

        public void apply() {
            this.func.accept(target);
        }

        public static class SET_POS extends ModelPartPrepare {

            private float[] pos;

            public SET_POS(ModelPart target, float[] pos) {
                super(target, null);
                this.pos = pos;
                this.func = part -> {
                    var newPos = getPos();
                    part.x = newPos[0];
                    part.y = newPos[1];
                    part.z = newPos[2];
                };
            }

            private float[] getPos() {
                return this.pos;
            }
    
        }

        public static class SET_RX extends ModelPartPrepare {

            private float rx;

            public SET_RX(ModelPart target, float rx) {
                super(target, null);
                this.rx = rx;
                this.func = part -> {
                    part.xRot = getRX();
                };
            }

            private float getRX() {
                return this.rx;
            }
    
        }

        public static class SET_RY extends ModelPartPrepare {

            private float ry;

            public SET_RY(ModelPart target, float ry) {
                super(target, null);
                this.ry = ry;
                this.func = part -> {
                    part.yRot = getRY();
                };
            }

            private float getRY() {
                return this.ry;
            }
    
        }

    }

    
    
}
