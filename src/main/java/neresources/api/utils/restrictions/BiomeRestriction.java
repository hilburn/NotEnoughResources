package neresources.api.utils.restrictions;

import neresources.api.messages.utils.MessageKeys;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BiomeRestriction
{
    public static BiomeRestriction NONE = new BiomeRestriction();

    private List<BiomeGenBase> biomes = new ArrayList<BiomeGenBase>();
    private Type type;

    public BiomeRestriction()
    {
        this.type = Type.NONE;
    }

    public BiomeRestriction(BiomeGenBase biome)
    {
        this(Type.WHITELIST, biome);
    }

    public BiomeRestriction(Type type, BiomeGenBase biome)
    {
        this.biomes.add(biome);
        this.type = type;
    }

    public BiomeRestriction(BiomeDictionary.Type type, BiomeDictionary.Type... biomeTypes)
    {
        this(Type.WHITELIST,type,biomeTypes);
    }

    public BiomeRestriction(Type type, BiomeDictionary.Type biomeType, BiomeDictionary.Type... biomeTypes)
    {
        this.type = type;
        biomes.addAll(Arrays.asList(BiomeDictionary.getBiomesForType(biomeType)));
        for (int i = 1; i< biomeTypes.length; i++)
        {
            List<BiomeGenBase> newBiomes = new ArrayList<BiomeGenBase>();
            for (BiomeGenBase biome : BiomeDictionary.getBiomesForType(biomeTypes[i]))
            {
                if (biomes.remove(biome)) newBiomes.add(biome);
            }
            biomes = newBiomes;
        }
    }

    public BiomeRestriction(NBTTagCompound tagCompound)
    {
        int[] ids = tagCompound.getIntArray(MessageKeys.biomeArray);
        for (int id:ids)
            biomes.add(BiomeGenBase.getBiome(id));
        type = Type.values()[tagCompound.getByte(MessageKeys.type)];
    }

    public List<BiomeGenBase> getBiomes()
    {
        return biomes;
    }

    public Type getType()
    {
        return type;
    }

    public NBTTagCompound writeToNBT()
    {
        return writeToNBT(new NBTTagCompound());
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        int[] ids = new int[biomes.size()];
        for (int i=0;i<ids.length;i++)
        {
            ids[i] = biomes.get(i).biomeID;
        }
        tagCompound.setIntArray(MessageKeys.biomeArray,ids);
        tagCompound.setByte(MessageKeys.type,(byte)type.ordinal());
        return tagCompound;
    }
}
