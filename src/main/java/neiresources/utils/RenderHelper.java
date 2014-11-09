package neiresources.utils;

import codechicken.lib.gui.GuiDraw;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderHelper
{
    public static void drawLine(int x1, int y1, int x2, int y2)
    {
        int dx = x2 - x1;
        int dy = y2 - y1;

        double error = 0;
        double dError = Math.abs((dy *1D) / (dx *1D));

        int y = y1;
        for (int x = x1; x <= x2; x++)
        {
            drawPoint(x, y);
            error += dError;
            if (error >= 0.5)
            {
                y++;
                error--;
            }
        }
    }

    public static void drawPoint(int x, int y)
    {
        GL11.glPointSize(GuiDraw.displaySize().width / 100F);
        GL11.glBegin(GL11.GL_POINTS);
        GL11.glVertex2i(x, y);
        GL11.glEnd();
    }
}
