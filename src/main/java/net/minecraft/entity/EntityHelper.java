package net.minecraft.entity;

import net.minecraft.entity.monster.EntitySlime;

public class EntityHelper
{
    public static String getEntityName(EntityLivingBase entity)
    {
        return entity.getEntityString();
    }

    public static int getExperience(EntityLiving entity)
    {
        return entity.experienceValue;
    }

}
