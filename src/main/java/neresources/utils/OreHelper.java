package neresources.utils;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class OreHelper
{
    public static enum OreClasses
    {
        minecraftOre("net.minecraft.block.BlockOre"),
        minecraftRedStoneOre("net.minecraft.block.BlockRedstoneOre"),
        thermalFoundation("thermalfoundation.block.BlockOre"),
        netherOre("powercrystals.netherores.ores.BlockNetherOres"),
        netherOreOverride("powercrystals.netherores.ores.BlockNetherOverrideOre"),
        metallurgy("com.teammetallurgy.metallurgy.metals.MetalBlock");

        public String className;

        private OreClasses(String className)
        {
            this.className = className;
        }

        public String getClassNameToString()
        {
            return "class " + className;
        }
    }

    public static boolean isOreBlock(ItemStack itemStack)
    {
        Block block = Block.getBlockFromItem(itemStack.getItem());
        for (OreClasses oreClass : OreClasses.values())
            if (block.getClass().toString().equals(oreClass.getClassNameToString())) return true;
        return false;
    }
}
