package neresources.utils;

public enum LightLevel
{
    any(0, Relative.above),
    bat(4, Relative.below),
    hostile(8, Relative.below),
    blaze(12, Relative.below);

    int lightLevel;
    Relative relative;

    LightLevel(int level, Relative relative)
    {
        this.lightLevel = level;
        this.relative = relative;
    }

    public String getString()
    {
        if (this == any) return "Light level: any";
        return "Light Level: " + relative.toString() + " " + lightLevel;
    }

    private enum Relative
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
