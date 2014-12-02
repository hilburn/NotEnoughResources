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
    public static final BiomeRestriction NONE = new BiomeRestriction();
    public static final BiomeRestriction OCEAN = new BiomeRestriction(BiomeDictionary.Type.OCEAN);
    public static final BiomeRestriction PLAINS = new BiomeRestriction(BiomeDictionary.Type.PLAINS);
    public static final BiomeRestriction FOREST = new BiomeRestriction(BiomeDictionary.Type.FOREST);
    public static final BiomeRestriction SANDY = new BiomeRestriction(BiomeDictionary.Type.FOREST);
    public static final BiomeRestriction SNOWY = new BiomeRestriction(BiomeDictionary.Type.FOREST);
    public static final BiomeRestriction HILLS = new BiomeRestriction(BiomeDictionary.Type.HILLS);
    public static final BiomeRestriction MUSHROOM = new BiomeRestriction(BiomeDictionary.Type.MUSHROOM);

    public static final BiomeRestriction HOT = new BiomeRestriction(BiomeDictionary.Type.HOT);
    public static final BiomeRestriction COLD = new BiomeRestriction(BiomeDictionary.Type.COLD);
    public static final BiomeRestriction TEMPERATE = new BiomeRestriction(Type.BLACKLIST, BiomeDictionary.Type.HOT,BiomeDictionary.Type.COLD);

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
        this(type, biome, new BiomeGenBase[0]);
    }

    public BiomeRestriction(BiomeGenBase biome, BiomeGenBase... moreBiomes)
    {
        this(Type.WHITELIST, biome, moreBiomes);
    }

    public BiomeRestriction(Type type, BiomeGenBase biome, BiomeGenBase... moreBiomes)
    {
        this.type = type;
        switch(type)
        {
            case NONE:
                break;
            case WHITELIST:
                this.biomes.add(biome);
                this.biomes.addAll(Arrays.asList(moreBiomes));
                break;
            default:
                biomes = Arrays.asList(BiomeGenBase.getBiomeGenArray());
                biomes.remove(biome);
                biomes.removeAll(Arrays.asList(moreBiomes));
        }
    }

    public BiomeRestriction(BiomeDictionary.Type type, BiomeDictionary.Type... biomeTypes)
    {
        this(Type.WHITELIST,type,biomeTypes);
    }

    public BiomeRestriction(Type type, BiomeDictionary.Type biomeType, BiomeDictionary.Type... biomeTypes)
    {
        this.type = type;
        switch(type)
        {
            case NONE:
                break;
            case WHITELIST:
                biomes = getBiomes(biomeType, biomeTypes);
                break;
            default:
                biomes = Arrays.asList(BiomeGenBase.getBiomeGenArray());
                biomes.removeAll(getBiomes(biomeType, biomeTypes));
        }
    }

    private List<BiomeGenBase> getBiomes(BiomeDictionary.Type biomeType, BiomeDictionary.Type... biomeTypes)
    {
        List<BiomeGenBase> biomes = new ArrayList<BiomeGenBase>();
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
        return biomes;
    }

    public BiomeRestriction(NBTTagCompound tagCompound)
    {
        int[] ids = tagCompound.getIntArray(MessageKeys.biomeArray);
        for (int id:ids)
            biomes.add(BiomeGenBase.getBiome(id));
        type = Type.values()[tagCompound.getByte(MessageKeys.type)];
    }

    public List<String> toStringList()
    {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i<biomes.size();)
        {
            String add = "";
            for (int j=i;j<i+3 && j<biomes.size();j++)
                add+=(add.isEmpty()?"":",")+biomes.get(j).biomeName;
            i+=3;
            result.add(add);
        }
        return result;
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
