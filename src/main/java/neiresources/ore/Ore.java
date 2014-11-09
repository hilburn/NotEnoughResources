package neiresources.ore;

import net.minecraft.block.BlockOre;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Ore
{
    public Item oreItem;
    public int maxY, minY;
    public int minDrop, maxDrop;
    public List<String> biomes = new ArrayList<String>();

    public Ore(Item oreItem, int minY, int maxY, int minDrop, int maxDrop)
    {
        this(oreItem, minY, maxY, minDrop, maxDrop, "all");
    }

    public Ore(BlockOre blockOre, int minY, int maxY, int minDrop, int maxDrop)
    {
        this(blockOre, minY, maxY, minDrop, maxDrop, "all");
    }

    public Ore(Item oreItem, int minY, int maxY, int minDrop, int maxDrop, String... biomes)
    {
        this.oreItem = oreItem;
        this.minY = minY;
        this.maxY = maxY;
        this.minDrop = minDrop;
        this.maxDrop = maxDrop;
        for (String biome : biomes)
            this.biomes.add(biome);
    }

    public Ore(BlockOre blockOre, int minY, int maxY, int minDrop, int maxDrop, String... biomes)
    {
        this.oreItem = Item.getItemFromBlock(blockOre);
        this.minY = minY;
        this.maxY = maxY;
        this.minDrop = minDrop;
        this.maxDrop = maxDrop;
        for (String biome : biomes)
            this.biomes.add(biome);
    }
}
