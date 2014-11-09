package neiresources.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIConfig implements IConfigureNEI
{
    @Override
    public void loadConfig()
    {
        NEIMobHandler neiMobHandler = new NEIMobHandler();
        API.registerRecipeHandler(neiMobHandler);
    }

    @Override
    public String getName()
    {
        return "NEI Resources";
    }

    @Override
    public String getVersion()
    {
        return "v0.1";
    }
}
