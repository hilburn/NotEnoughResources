package neiresources.utils;

import net.minecraft.client.Minecraft;;
import net.minecraft.client.gui.ScaledResolution;;
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
                y--;
                error--;
            }
        }
    }

    public static void drawPoint(int x, int y)
    {
        Minecraft mc = Minecraft.getMinecraft();
        int scale = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight).getScaleFactor();
        GL11.glColor3f(120 / 255F, 120 / 255F, 120 / 255F);
        GL11.glPointSize(scale*1.3F);
        GL11.glBegin(GL11.GL_POINTS);
        GL11.glVertex2i(x, y);
        GL11.glEnd();
    }
}
