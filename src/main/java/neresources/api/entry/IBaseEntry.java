package neresources.api.entry;

public interface IBaseEntry {

    /**
     * @return String that will uniquely identify this entry within the mod use {@link neresources.api.utils.KeyGen)}
     */
    public String getKey();
}
