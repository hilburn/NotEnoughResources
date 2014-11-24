package neresources.api.utils;

public class LightLevel
{
    public static LightLevel any = new LightLevel(-1, Relative.above);
    public static LightLevel bat = new LightLevel(4);
    public static LightLevel hostile = new LightLevel(8);
    public static LightLevel blaze = new LightLevel(12);

    int lightLevel;
    Relative relative;

    /**
     * @param level    the level of light
     * @param relative an {@link neresources.api.utils.LightLevel.Relative}
     */
    LightLevel(int level, Relative relative)
    {
        this.lightLevel = level;
        this.relative = relative;
    }

    /**
     * @param level    the level of light
     * @param relative the relative positive is above, negative is below. Zero will also be below.
     */
    LightLevel(int level, int relative)
    {
        this.lightLevel = level;
        this.relative = relative > 0 ? Relative.above : Relative.below;
    }

    /**
     * @param level the maximum level light the mob can spawn (the {@link neresources.api.utils.LightLevel.Relative} will be below)
     */
    LightLevel(int level)
    {
        this(level, Relative.below);
    }

    @Override
    public String toString()
    {
        if (lightLevel < 0) return "Light level: any";
        return "Light Level: " + relative.toString() + " " + lightLevel;
    }

    /**
     * The {@link neresources.api.utils.LightLevel.Relative} enum holding an above and below entry
     */
    public enum Relative
    {
        above("Above"),
        below("Below");
        String text;

        Relative(String string)
        {
            this.text = string;
        }

        @Override
        public String toString()
        {
            return text;
        }
    }

}
