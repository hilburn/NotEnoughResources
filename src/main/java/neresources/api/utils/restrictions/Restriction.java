package neresources.api.utils.restrictions;

import neresources.api.messages.utils.MessageKeys;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class Restriction
{
    public static final Restriction OVERWORLD_LIKE = new Restriction();
    public static final Restriction NETHER_LIKE = new Restriction(BlockRestriction.NETHER);
    public static final Restriction END_LIKE = new Restriction(BlockRestriction.END);

    public static final Restriction OVERWORLD = new Restriction(DimensionRestriction.OVERWORLD);
    public static final Restriction NETHER = new Restriction(BlockRestriction.NETHER,DimensionRestriction.NETHER);
    public static final Restriction END = new Restriction(BlockRestriction.END,DimensionRestriction.END);

    private BlockRestriction blockRestriction;
    private BiomeRestriction biomeRestriction;
    private DimensionRestriction dimensionRestriction;

    public Restriction()
    {
        this(BlockRestriction.STONE, BiomeRestriction.NONE, DimensionRestriction.NONE);
    }

    public Restriction(BlockRestriction blockRestriction)
    {
        this(blockRestriction,BiomeRestriction.NONE, DimensionRestriction.NONE);
    }

    public Restriction(BiomeRestriction biomeRestriction)
    {
        this(BlockRestriction.STONE,biomeRestriction,DimensionRestriction.NONE);
    }

    public Restriction(DimensionRestriction dimensionRestriction)
    {
        this(BlockRestriction.STONE,BiomeRestriction.NONE,dimensionRestriction);
    }

    public Restriction(BlockRestriction blockRestriction, BiomeRestriction biomeRestriction)
    {
        this(blockRestriction,biomeRestriction,DimensionRestriction.NONE);
    }

    public Restriction(BlockRestriction blockRestriction, DimensionRestriction dimensionRestriction)
    {
        this(blockRestriction,BiomeRestriction.NONE,dimensionRestriction);
    }

    public Restriction(BiomeRestriction biomeRestriction, DimensionRestriction dimensionRestriction)
    {
        this(BlockRestriction.STONE,biomeRestriction,dimensionRestriction);
    }

    public Restriction(BlockRestriction blockRestriction, BiomeRestriction biomeRestriction, DimensionRestriction dimensionRestriction)
    {
        this.blockRestriction = blockRestriction;
        this.biomeRestriction = biomeRestriction;
        this.dimensionRestriction = dimensionRestriction;
    }

    public Restriction(NBTTagCompound tagCompound)
    {
        blockRestriction = new BlockRestriction(tagCompound.getCompoundTag(MessageKeys.blockRestriction));
        biomeRestriction = new BiomeRestriction(tagCompound.getCompoundTag(MessageKeys.biomeRestriction));
        dimensionRestriction = new DimensionRestriction(tagCompound.getCompoundTag(MessageKeys.dimensionRestriction));
    }

    public List<String> getStringList()
    {
        List<String> result = new ArrayList<String>();
        result.add(dimensionRestriction.getValidDimensions(blockRestriction));
        result.addAll(biomeRestriction.toStringList());
        return result;
    }

    public NBTTagCompound writeToNBT()
    {
        return writeToNBT(new NBTTagCompound());
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setTag(MessageKeys.blockRestriction,blockRestriction.writeToNBT());
        tagCompound.setTag(MessageKeys.dimensionRestriction,dimensionRestriction.writeToNBT());
        tagCompound.setTag(MessageKeys.biomeRestriction,biomeRestriction.writeToNBT());
        return tagCompound;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Restriction)) return false;
        Restriction other = (Restriction) obj;
        if (!other.biomeRestriction.equals(biomeRestriction)) return false;
        if (!other.blockRestriction.equals(blockRestriction)) return false;
        if (!other.dimensionRestriction.equals(dimensionRestriction)) return false;
        return true;
    }
}
