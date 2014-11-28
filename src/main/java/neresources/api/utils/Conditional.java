package neresources.api.utils;

import net.minecraft.util.StatCollector;

public class Conditional
{
    public static Conditional playerKill = new Conditional("ner.playerkill.text");
    public static Conditional magmaCream = new Conditional("ner.magmacream.text",Modifier.darkRed);
    public static Conditional slimeBall = new Conditional("ner.slimeball.text",Modifier.lightGreen);
    public static Conditional rareDrop = new Conditional("ner.raredrop.text",Modifier.purple);
    public static Conditional burning = new Conditional("ner.burning.text",Modifier.lightRed);
    public static Conditional silkTouch = new Conditional("ner.ore.silkTouch",Modifier.darkCyan);
    public static Conditional equipmentDrop = new Conditional("ner.equipmentDrop.text",Modifier.lightCyan);

    private String text;
    private String colour = "";


    public Conditional(String text, Modifier... modifiers)
    {
        this.text=text;
        for (Modifier modifier:modifiers)
            colour+=modifier.toString();
    }

    @Override
    public String toString()
    {
        return colour + StatCollector.translateToLocal(text);
    }
}
