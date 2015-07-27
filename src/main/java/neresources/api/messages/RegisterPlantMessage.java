package neresources.api.messages;

import neresources.api.messages.utils.MessageHelper;
import neresources.api.messages.utils.MessageKeys;
import neresources.api.utils.PlantDrop;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class RegisterPlantMessage extends RegistryMessage
{
    private ItemStack plant;
    private PlantDrop[] addDrops;

    public RegisterPlantMessage(NBTTagCompound tagCompound)
    {
        super(tagCompound);
        this.plant = ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag(MessageKeys.stack));
        this.addDrops = MessageHelper.getPlantDrops(tagCompound,MessageKeys.addDrops);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        tagCompound.setTag(MessageKeys.stack, plant.writeToNBT(new NBTTagCompound()));
        tagCompound.setTag(MessageKeys.addDrops,MessageHelper.getPlantDropList(addDrops));
        return tagCompound;
    }

    @Override
    public boolean isValid()
    {
        return plant!=null && addDrops.length>0;
    }
}
