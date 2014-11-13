package neresources.gui;

import cpw.mods.fml.client.config.GuiConfig;
import neresources.config.ConfigHandler;
import neresources.reference.Reference;
import net.minecraft.client.gui.GuiScreen;

public class ModGuiConfig extends GuiConfig
{
    public ModGuiConfig(GuiScreen guiScreen)
    {
        super(guiScreen,
                ConfigHandler.getConfigElements(),
                Reference.ID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(ConfigHandler.config.toString()));
    }
}

