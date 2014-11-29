package neresources.utils;

import neresources.api.NEResourcesAPI;
import neresources.api.distributions.DistributionCustom;
import neresources.api.messages.utils.MessageHelper;
import neresources.api.utils.ColorHelper;
import neresources.api.utils.DropItem;
import neresources.api.utils.LightLevel;
import neresources.api.utils.conditionals.Conditional;
import neresources.api.messages.utils.MessageKeys;
import neresources.registry.ChangeMobDrop;
import neresources.registry.ChangeOreDrop;
import neresources.registry.MobEntry;
import neresources.registry.OreEntry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IMCHandler
{
    public static void registerIMCMessage(String key, NBTTagCompound tagCompound)
    {
        if (key.equals(MessageKeys.registerMob)) registerMob(tagCompound);
        else if (key.equals(MessageKeys.registerOre)) registerOre(tagCompound);
        else if (key.equals(MessageKeys.modifyMob)) modifyMobDrops(tagCompound);
        else if (key.equals(MessageKeys.modifyOre)) modifyOreDrops(tagCompound);
    }

    public static void registerMob(NBTTagCompound tagCompound)
    {
        String className = tagCompound.getString("className");
        if (className.equals("")) return;
        Class clazz = ReflectionHelper.findClass(className);
        if (clazz == null || !checkInstanceOf(clazz, EntityLivingBase.class)) return;
        EntityLivingBase entity = (EntityLivingBase) ReflectionHelper.initialize(clazz,World.class,null);
        if (entity==null) return;
        String lightLevel = tagCompound.getString("lightLevel");
        NBTTagList biomeTags = tagCompound.getTagList("biomes", 8);
        NBTTagList dropItemTags = tagCompound.getTagList("dropItems", 10);
        LightLevel level = lightLevel.equals("") ? LightLevel.any : LightLevel.decodeLightLevel(lightLevel);
        List<String> biomes = new ArrayList<String>();
        if (biomeTags != null)
        {
            for (int i = 0; i < biomeTags.tagCount(); i++)
            {
                String tag = biomeTags.getStringTagAt(i);
                if (tag.equals("")) biomes.add(tag);
            }
        } else biomes.add("Any");

        NEResourcesAPI.registerEntry(new MobEntry(entity, level, biomes.toArray(new String[biomes.size()]), MessageHelper.getDropItems(dropItemTags)));
    }

    private static boolean checkInstanceOf(Class clazz, Class checkClass)
    {
        for (Object instanceOf : ClassScraper.getGeneralizations(clazz))
            if (instanceOf == checkClass) return true;
        return false;
    }

    public static void registerOre(NBTTagCompound tagCompound)
    {
        ItemStack ore = ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag("ore"));
        if (ore == null) return;
        if (tagCompound.hasKey("addDrops", 9)) modifyOreDrops(tagCompound);
        int colour = tagCompound.hasKey("colour") ? tagCompound.getInteger("colour") : ColorHelper.BLACK;
        boolean hasST = tagCompound.hasKey("silkTouch");
        int[] intArray = tagCompound.getIntArray("distribution");
        intArray = Arrays.copyOf(intArray, 256);
        float[] distribution = new float[256];
        for (int i = 0; i < 256; i++)
            distribution[i] = (float) intArray[i] / 100000;
        if (hasST)
            NEResourcesAPI.registerEntry(new OreEntry(ore, new DistributionCustom(distribution), colour, tagCompound.getBoolean("silkTouch")));
        else
            NEResourcesAPI.registerEntry(new OreEntry(ore, new DistributionCustom(distribution), colour));
    }

    public static void modifyMobDrops(NBTTagCompound tagCompound)
    {
        String className = tagCompound.getString("className");
        if (className.equals("")) return;
        Class clazz = ReflectionHelper.findClass(className);
        if (clazz == null) return;
        boolean wither = tagCompound.getBoolean("wither");
        boolean strict = tagCompound.getBoolean("strict");
        DropItem[] addDrops = MessageHelper.getDropItems(tagCompound.getTagList("dropItems", 10));
        ItemStack[] removeDrops = MessageHelper.getItemStacks(tagCompound.getTagList("removeDrops", 10));
        NEResourcesAPI.registerEntry(new ChangeMobDrop(clazz, strict, wither, removeDrops, addDrops));
    }

    public static void modifyOreDrops(NBTTagCompound tagCompound)
    {
        ItemStack ore = ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag("ore"));
        ItemStack[] addDrops = MessageHelper.getItemStacks(tagCompound.getTagList("addDrops", 10));
        ItemStack[] removeDrops = MessageHelper.getItemStacks(tagCompound.getTagList("removeDrops", 10));
        if (ore == null || (addDrops.length == 0 && removeDrops.length == 0)) return;
        if (addDrops.length == 0) NEResourcesAPI.registerEntry(new ChangeOreDrop(ore, false, removeDrops));
        else if (removeDrops.length == 0) NEResourcesAPI.registerEntry(new ChangeOreDrop(ore, true, addDrops));
        else NEResourcesAPI.registerEntry(new ChangeOreDrop(ore, removeDrops, addDrops));
    }
}
