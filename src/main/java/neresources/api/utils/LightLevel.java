package neresources.api.utils;

public class LightLevel
{
    public static LightLevel any = new LightLevel(-1, Relative.above);
    public static LightLevel bat = new LightLevel(4, Relative.below);
    public static LightLevel hostile = new LightLevel(8, Relative.below);
    public static LightLevel blaze = new LightLevel(12, Relative.below);

    int lightLevel;
    Relative relative;

    LightLevel(int level, Relative relative)
    {
        this.lightLevel = level;
        this.relative = relative;
    }

    @Override
    public String toString()
    {
        if (lightLevel < 0) return "Light level: any";
        return "Light Level: " + relative.toString() + " " + lightLevel;
    }

    public int getLightLevel()
    {
        return this.lightLevel;
    }

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
