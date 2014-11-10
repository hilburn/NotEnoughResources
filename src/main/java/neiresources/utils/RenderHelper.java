package neiresources.utils;

import net.minecraft.client.Minecraft;;
import net.minecraft.client.gui.ScaledResolution;;
import org.lwjgl.opengl.GL11;

public class RenderHelper
{
    public static void drawLine(int x1, int y1, int x2, int y2, int precision)
    {
        int dx = x2 - x1;
        int dy = y2 - y1;

        double add = y1 > y2 ? -1 : +1;

        double error = 0;
        double dError = (Math.abs((dy *1D) / (dx *1D)) / precision);

        double y = y1;
        for (double x = x1; x <= x2; x += (1D / precision))
        {
            drawPoint(x, y);
            error += dError;
            while (error >= (0.5D / precision))
            {
                y += add;
                error--;
            }
        }
    }

    public static void drawPoint(double x, double y)
    {
        Minecraft mc = Minecraft.getMinecraft();
        int scale = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight).getScaleFactor();
        GL11.glColor3f(120 / 255F, 120 / 255F, 120 / 255F);
        GL11.glPointSize(scale*1.3F);
        GL11.glBegin(GL11.GL_POINTS);
        GL11.glVertex2d(x, y);
        GL11.glEnd();
    }
}
