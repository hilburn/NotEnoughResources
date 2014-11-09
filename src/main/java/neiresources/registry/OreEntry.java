package neiresources.registry;

public class OreEntry
{

    private String modName;
    public OreEntry(String modName)
    {
        this.modName = modName;
    }

    public double getChance(int y)
    {
        return 0;
    }

    public String getModName()
    {
        return modName;
    }
}
