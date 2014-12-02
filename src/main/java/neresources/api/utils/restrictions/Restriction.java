package neresources.api.utils.restrictions;

public class Restriction
{
    BlockRestriction blockRestriction;
    BiomeRestriction biomeRestriction;
    DimensionRestriction dimensionRestriction;

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
}
