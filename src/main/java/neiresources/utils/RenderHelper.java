package neiresources.utils;

import neiresources.reference.Resources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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

    public static void renderEntity(int x, int y, float scale, float yaw, float pitch, EntityLivingBase entityLivingBase)
    {
        if (entityLivingBase.worldObj == null) entityLivingBase.worldObj = Minecraft.getMinecraft().theWorld;
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, 50.0F);
        GL11.glScalef(-scale, scale, scale);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float renderYawOffset = entityLivingBase.renderYawOffset;
        float rotationYaw = entityLivingBase.rotationYaw;
        float rotationPitch = entityLivingBase.rotationPitch;
        float prevRotationYawHead = entityLivingBase.prevRotationYawHead;
        float rotationYawHead = entityLivingBase.rotationYawHead;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float)Math.atan((double)(pitch / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        entityLivingBase.renderYawOffset = (float)Math.atan((double)(yaw / 40.0F)) * 20.0F;
        entityLivingBase.rotationYaw = (float)Math.atan((double)(yaw / 40.0F)) * 40.0F;
        entityLivingBase.rotationPitch = -((float)Math.atan((double)(pitch / 40.0F))) * 20.0F;
        entityLivingBase.rotationYawHead = entityLivingBase.rotationYaw;
        entityLivingBase.prevRotationYawHead = entityLivingBase.rotationYaw;
        GL11.glTranslatef(0.0F, entityLivingBase.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180.0F;
        RenderManager.instance.renderEntityWithPosYaw(entityLivingBase, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        entityLivingBase.renderYawOffset = renderYawOffset;
        entityLivingBase.rotationYaw = rotationYaw;
        entityLivingBase.rotationPitch = rotationPitch;
        entityLivingBase.prevRotationYawHead = prevRotationYawHead;
        entityLivingBase.rotationYawHead = rotationYawHead;
        GL11.glPopMatrix();
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public static void renderChest(float x, float y, float rotate, float scale, float lidAngle)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(Resources.Vanilla.CHEST);
        ModelChest modelchest = new ModelChest();

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef(x, y, 50.0F);
        GL11.glRotatef(-160.0F, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(scale, -scale, -scale);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        GL11.glRotatef(rotate, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        float lidAngleF = lidAngle / 180;

        lidAngleF = 1.0F - lidAngleF;
        lidAngleF = 1.0F - lidAngleF * lidAngleF * lidAngleF;
        modelchest.chestLid.rotateAngleX = -(lidAngleF * (float)Math.PI / 2.0F);
        modelchest.chestKnob.offsetX += 0.1F;
        modelchest.chestKnob.offsetZ += 0.12F;
        modelchest.chestBelow.offsetX -= 0.755F;
        modelchest.chestBelow.offsetY -= 0.4F;
        modelchest.chestBelow.offsetZ -= 0.9F;
        modelchest.renderAll();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
