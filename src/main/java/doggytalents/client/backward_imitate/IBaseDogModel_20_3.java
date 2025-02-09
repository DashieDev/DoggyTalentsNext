package doggytalents.client.backward_imitate;

public interface IBaseDogModel_20_3 {
    
    public void setDogYoung(boolean val);

    public boolean getDogYoung();

    default void copyPropertiesTo(IBaseDogModel_20_3 val) {
        this.setDogYoung(getDogYoung());
    }

}
