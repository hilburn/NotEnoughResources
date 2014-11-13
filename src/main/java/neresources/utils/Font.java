package neresources.utils;

import neresources.reference.Resources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.IReloadableResourceManager;

public class Font
{
    private FontRenderer fontRenderer;

    public Font(boolean small)
    {
        Minecraft mc = Minecraft.getMinecraft();
        fontRenderer = new FontRenderer(mc.gameSettings, Resources.Vanilla.FONT, mc.getTextureManager(), small);
        ((IReloadableResourceManager) mc.getResourceManager()).registerReloadListener(fontRenderer);
    }

    public void print(Object o, int x, int y)
    {
        fontRenderer.drawString(String.valueOf(o), x, y, 8, false);
    }

    public void print(Object o, int x, int y, int color)
    {
        fontRenderer.drawString(String.valueOf(o), x, y, color, false);
    }

    public void print(Object o, int x, int y, int color, boolean shadow)
    {
        fontRenderer.drawString(String.valueOf(o), x, y, color, shadow);
    }
}
