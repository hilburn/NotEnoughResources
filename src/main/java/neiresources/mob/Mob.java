package neiresources.mob;

import neiresources.drop.DropItem;

import java.util.ArrayList;
import java.util.List;

public class Mob
{
    public String name;
    public List<DropItem> drops = new ArrayList<DropItem>();
    public int lightLevel;
    public List<String> biomes = new ArrayList<String>();

    public Mob(String name, int lightLevel, String[] biomes, DropItem... drops)
    {
        this.name = name;
        this.lightLevel = lightLevel;
        for (String biome : biomes)
            this.biomes.add(biome);
        for (DropItem drop : drops)
            this.drops.add(drop);
    }

    public Mob(String name, int lightLevel, DropItem... drops)
    {
        this.name = name;
        this.lightLevel = lightLevel;
        this.biomes.add("all");
        for (DropItem drop : drops)
            this.drops.add(drop);
    }
}
