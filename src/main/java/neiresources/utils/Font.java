package neiresources.utils;

import neiresources.reference.Resources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.IReloadableResourceManager;

public class Font
{
    private FontRenderer fontRenderer;

    public Font(boolean small)
    {
        Minecraft mc = Minecraft.getMinecraft();
        fontRenderer = new FontRenderer(mc.gameSettings, Resources.Font.MC, mc.getTextureManager(), small);
        ((IReloadableResourceManager)mc.getResourceManager()).registerReloadListener(fontRenderer);
    }

    public void print(String string, int x, int y)
    {
        fontRenderer.drawString(string, x, y, 8, false);
    }

    public void print(String string, int x, int y, int color)
    {
        fontRenderer.drawString(string, x, y, color, false);
    }

    public void print(String string, int x, int y, int color, boolean shadow)
    {
        fontRenderer.drawString(string, x, y, color, shadow);
    }
}
