package neresources.compatibility.mobproperties;

import cpw.mods.fml.common.Optional;
import neresources.api.messages.ModifyMobMessage;
import neresources.api.utils.DropItem;
import neresources.compatibility.CompatBase;
import neresources.registry.MessageRegistry;
import neresources.utils.LoaderHelper;
import neresources.utils.LogHelper;
import neresources.utils.ModList;
import neresources.utils.ReflectionHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import toast.mobProperties.api.DropEntry;
import toast.mobProperties.api.MobPropertiesAPI;

import java.util.ArrayList;
import java.util.List;

public class MobPropertiesCompat extends CompatBase
{

    @Override
    protected void init()
    {
        if (LoaderHelper.isModVersionGreater(ModList.mobproperties.toString(),new int[]{0,3,5},"."))
        {
            loadAPIdata();
        } else
        {
            LogHelper.info(ModList.mobproperties.toString() + " is out of date");
        }
    }

    @Optional.Method(modid = ModList.Names.MOBPROPERTIES)
    private void loadAPIdata()
    {
        for (Object entity: EntityList.classToStringMapping.keySet())
        {
            List<DropEntry> drops = MobPropertiesAPI.getDrops((Class)entity);
            if (drops.size()>0)
            {
                for (DropEntry drop:drops)
                {
                    Item item = drop.getItem();
                    List<DropItem> dropItems = new ArrayList<DropItem>();
                    double[] amounts = (double[])ReflectionHelper.getObject(DropEntry.class,"attempts",drop);
                    if (amounts==null || amounts.length<2)
                        amounts=new double[]{0.0D,1.0D};
                    for (int i = (int)drop.getDamages()[0]; i<= (int)drop.getDamages()[1]; i++)
                        dropItems.add(new DropItem(new ItemStack(item,1,i),(int)amounts[0], (int)amounts[1], (float)drop.getCounts()[0]));
                    ModifyMobMessage message = new ModifyMobMessage((Class) entity, true, dropItems.toArray(new DropItem[dropItems.size()]));
                    message = StringParser.addConditionals(message, drop.getConditions());
                    MessageRegistry.addMessage(message);
                }
            }
        }
    }
}
