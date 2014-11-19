package neresources.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import neresources.reference.Reference;

public class NEIConfig implements IConfigureNEI
{
    public static final String MOB = Reference.ID + ".mob";
    public static final String DUNGEON = Reference.ID + ".dungeon";
    public static final String ORE = Reference.ID + ".ore";
    public static final String GRASS = Reference.ID + ".grass";

    @Override
    public void loadConfig()
    {
        NEIMobHandler neiMobHandler = new NEIMobHandler();
        API.registerRecipeHandler(neiMobHandler);

        NEIOreHandler neiOreHandler = new NEIOreHandler();
        API.registerRecipeHandler(neiOreHandler);

        NEIDungeonHandler neiDungeonHandler = new NEIDungeonHandler();
        API.registerRecipeHandler(neiDungeonHandler);

        NEIGrassHandler neiGrassHandler = new NEIGrassHandler();
        API.registerRecipeHandler(neiGrassHandler);
        API.registerUsageHandler(neiGrassHandler);

        NEISeedHandler neiSeedHandler = new NEISeedHandler();
        API.registerRecipeHandler(neiSeedHandler);
        API.registerUsageHandler(neiSeedHandler);

        NEIEnchantmentHandler neiEnchantmentHandler = new NEIEnchantmentHandler();
        API.registerUsageHandler(neiEnchantmentHandler);
    }

    @Override
    public String getName()
    {
        return Reference.NAME;
    }

    @Override
    public String getVersion()
    {
        return "v0.1";
    }
}
