package neresources.api.messages.utils;

import neresources.api.distributions.DistributionCustom;
import neresources.api.utils.DropItem;
import neresources.api.utils.conditionals.Conditional;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

public class MessageHelper
{
    public static final int NBT_MULTIPLIER = 100000;

    public static NBTTagList getItemStackList(ItemStack... stacks)
    {
        NBTTagList result = new NBTTagList();
        for (ItemStack stack:stacks)
            result.appendTag(stack.writeToNBT(new NBTTagCompound()));
        return result;
    }

    public static  NBTTagList getDropItemList(DropItem... dropItems)
    {
        NBTTagList result = new NBTTagList();
        for (DropItem dropItem:dropItems)
            result.appendTag(dropItem.getNBTTagCompound());
        return result;
    }

    public static ItemStack[] getItemStacks(NBTTagCompound tagCompound, String key)
    {
        return getItemStacks(tagCompound.getTagList(key, 10));
    }

    public static ItemStack[] getItemStacks(NBTTagList list)
    {
        List<ItemStack> result = new ArrayList<ItemStack>();
        if (list != null)
        {
            for (int i = 0; i < list.tagCount(); i++)
            {
                ItemStack item = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i));
                if (item != null) result.add(item);
            }
        }
        return result.toArray(new ItemStack[result.size()]);
    }

    public static DropItem[] getDropItems(NBTTagCompound tagCompound, String key)
    {
        return getDropItems(tagCompound.getTagList(key, 10));
    }

    public static DropItem[] getDropItems(NBTTagList list)
    {
        List<DropItem> dropItems = new ArrayList<DropItem>();
        if (list != null)
        {
            for (int i = 0; i < list.tagCount(); i++)
            {
                DropItem item = decodeDropItem(list.getCompoundTagAt(i));
                if (item != null) dropItems.add(item);
            }
        }
        return dropItems.toArray(new DropItem[dropItems.size()]);
    }

    public static DropItem decodeDropItem(NBTTagCompound tagCompound)
    {
        NBTTagCompound item = tagCompound.getCompoundTag("stack");
        if (item.hasNoTags()) return null;
        ItemStack stack = ItemStack.loadItemStackFromNBT(item);
        if (stack == null || stack.getItem() == null) return null;
        int max = Math.max(tagCompound.getInteger("max"), 1);
        int min = tagCompound.getInteger("min");
        max = Math.max(max, stack.stackSize);
        stack.stackSize = 1;
        float chance = tagCompound.getFloat("chance");
        if (chance == 0) chance = 1F;
        Conditional[] conditionals = decodeConditionals(tagCompound.getTagList("conditionals", 8));
        return new DropItem(stack, min, max, chance, conditionals);
    }

    public static Conditional[] decodeConditionals(NBTTagList conditional)
    {
        List<Conditional> result = new ArrayList<Conditional>();
        for (int i = 0; i < conditional.tagCount(); i++)
        {
            String condition = conditional.getStringTagAt(i);
            if (!condition.equals("")) result.add(new Conditional(condition));
        }
        return result.toArray(new Conditional[result.size()]);
    }

    public static int[] getIntArray(float[] distribution)
    {
        int[] array = new int[distribution.length];
        for (int i=0;i<array.length;i++)
            array[i] = (int)(distribution[i]*NBT_MULTIPLIER);
        return array;
    }

    public static DistributionCustom getDistribution(NBTTagCompound tagCompound)
    {
        if (!tagCompound.hasKey(MessageKeys.distribution)) return null;
        int[] array = tagCompound.getIntArray(MessageKeys.distribution);
        float[] distribution = new float[256];
        for (int i=0;i<array.length && i<distribution.length;i++)
            distribution[i] = (float)array[i]/NBT_MULTIPLIER;
        if (tagCompound.hasKey(MessageKeys.bestHeight))
            return new DistributionCustom(distribution,tagCompound.getInteger(MessageKeys.bestHeight));
        else
            return new DistributionCustom(distribution);
    }

    public static String getClass(Object clazz)
    {
        if (clazz instanceof String)
            return (String) clazz;
        if (clazz instanceof Class)
            return ((Class)clazz).getName();
        return "";
    }
}
