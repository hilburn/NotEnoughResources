package neresources.reference;

import net.minecraft.util.ResourceLocation;

public final class Resources
{
    public static final class Gui
    {
        public static final class Nei
        {
            public static final ResourceLocation MOB = new ResourceLocation(Reference.ID, Textures.Gui.Nei.MOB);
            public static final ResourceLocation ORE = new ResourceLocation(Reference.ID, Textures.Gui.Nei.ORE);
            public static final ResourceLocation DUNGEON = new ResourceLocation(Reference.ID, Textures.Gui.Nei.DUNGEON);
            public static final ResourceLocation PLANT = new ResourceLocation(Reference.ID, Textures.Gui.Nei.PLANT);
            public static final ResourceLocation ADV_PLANT = new ResourceLocation(Reference.ID, Textures.Gui.Nei.ADV_PLANT);
            public static final ResourceLocation ENCHANTMENT = new ResourceLocation(Reference.ID, Textures.Gui.Nei.ENCHANTMENT);
        }
    }

    public static final class Vanilla
    {
        public static final ResourceLocation FONT = new ResourceLocation("textures/font/ascii.png");
        public static final ResourceLocation CHEST = new ResourceLocation("textures/entity/chest/normal.png");
    }
}
