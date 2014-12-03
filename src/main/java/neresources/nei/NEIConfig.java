package neresources.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import neresources.reference.Reference;

public class NEIConfig implements IConfigureNEI
{
    public static final String MOB = Reference.ID + ".mob";
    public static final String DUNGEON = Reference.ID + ".dungeon";
    public static final String ORE = Reference.ID + ".ore";
    public static final String PLANT = Reference.ID + ".plant";

    @Override
    public void loadConfig()
    {
        NEIEnchantmentHandler neiEnchantmentHandler = new NEIEnchantmentHandler();
        API.registerUsageHandler(neiEnchantmentHandler);

        NEIMobHandler neiMobHandler = new NEIMobHandler();
        API.registerRecipeHandler(neiMobHandler);
        API.registerUsageHandler(neiMobHandler);

        NEIOreHandler neiOreHandler = new NEIOreHandler();
        API.registerRecipeHandler(neiOreHandler);

        NEIDungeonHandler neiDungeonHandler = new NEIDungeonHandler();
        API.registerRecipeHandler(neiDungeonHandler);

        NEIPlantHandler neiPlantHandler = new NEIPlantHandler();
        API.registerRecipeHandler(neiPlantHandler);
        API.registerUsageHandler(neiPlantHandler);

        NEIAdvSeedHandler neiAdvSeedHandler = new NEIAdvSeedHandler();
        API.registerRecipeHandler(neiAdvSeedHandler);
        API.registerUsageHandler(neiAdvSeedHandler);
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
