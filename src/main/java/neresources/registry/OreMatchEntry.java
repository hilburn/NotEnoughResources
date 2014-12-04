package neresources.registry;

import neresources.api.distributions.DistributionBase;
import neresources.api.messages.RegisterOreMessage;
import neresources.api.utils.ColorHelper;
import neresources.api.utils.DistributionHelpers;
import neresources.api.utils.restrictions.Restriction;
import neresources.compatibility.Compatibility;
import neresources.config.Settings;
import neresources.utils.MapKeys;
import neresources.utils.SilkTouchHelper;
import net.minecraft.item.ItemStack;

import java.util.*;


public class OreMatchEntry
{
    private float[] chances;
    Map<String, Boolean> silkTouchMap = new LinkedHashMap<String, Boolean>();
    Map<ItemStack, DistributionBase> ores = new LinkedHashMap<ItemStack, DistributionBase>();
    private int minY;
    private int maxY;
    private int bestY;
    private boolean denseOre;
    private int colour;
    private Restriction restriction;
    List<ItemStack> drops = new ArrayList<ItemStack>();

    public OreMatchEntry(RegisterOreMessage message)
    {
        silkTouchMap.put(MapKeys.key(message.getOre()), message.needSilkTouch() || SilkTouchHelper.isOreBlock(message.getOre()));
        ores.put(message.getOre(), message.getDistribution());
        restriction = message.getRestriction();
        calcChances();
        if (colour == ColorHelper.BLACK) colour = message.getColour();
    }

    private void addMessage(RegisterOreMessage message)
    {
        silkTouchMap.put(MapKeys.key(message.getOre()), message.needSilkTouch() || SilkTouchHelper.isOreBlock(message.getOre()));
        ores.put(message.getOre(), message.getDistribution());
        calcChances();
        if (colour == ColorHelper.BLACK) colour = message.getColour();
    }

    public boolean add(RegisterOreMessage message)
    {
        if (!restriction.isMergeable(message.getRestriction())) return false;
        addMessage(message);
        return message.getRestriction().equals(this.restriction);
    }

    public void add(OreMatchEntry oreMatchEntry)
    {
        if (restriction.isMergeable(oreMatchEntry.restriction))
        {
            silkTouchMap.putAll(oreMatchEntry.silkTouchMap);
            ores.putAll(oreMatchEntry.ores);
            denseOre |= oreMatchEntry.denseOre;
            calcChances();
            if (colour == ColorHelper.BLACK) colour = oreMatchEntry.getColour();
        }
    }

    private void calcChances()
    {
        chances = new float[256];
        minY = 256;
        maxY = 0;
        for (DistributionBase distribution : ores.values())
        {
            int i = 0;
            for (float chance : distribution.getDistribution())
            {
                if (++i == chances.length) break;
                chances[i] += chance * (denseOre && i < 81 ? Compatibility.DENSE_ORES_MULTIPLIER : 1);
                if (chances[i] > 0)
                {
                    if (minY > i)
                        minY = i;
                    if (i > maxY)
                        maxY = i;
                }
            }
            bestY = distribution.getBestHeight();
        }
        if (minY == 256) minY = 0;
        if (maxY == 0) maxY = 255;
        if (ores.size() > 1) bestY = DistributionHelpers.calculateMeanLevel(chances, 40, 0, 1000);
    }

    public float[] getChances()
    {
        return getChances(Settings.EXTRA_RANGE);
    }

    public float[] getChances(int extraRange)
    {
        return Arrays.copyOfRange(chances, Math.max(minY - extraRange, 0), Math.min(maxY + extraRange + 2, 255));
    }

    public int getBestY()
    {
        return bestY;
    }

    public int getMinY()
    {
        return minY;
    }

    public int getMaxY()
    {
        return maxY;
    }

    public boolean isSilkTouchNeeded(ItemStack itemStack)
    {
        Boolean silkTouch = this.silkTouchMap.get(MapKeys.key(itemStack));
        return silkTouch == null ? false : silkTouch;
    }

    public int getColour()
    {
        return colour;
    }

    public void addDrop(ItemStack nonOre)
    {
        drops.add(nonOre);
        boolean silkTouch = false;
        if (MapKeys.getKey(nonOre).startsWith("denseore"))
        {
            denseOre = true;
            silkTouch = true;
            calcChances();
        }
        silkTouchMap.put(MapKeys.key(nonOre), silkTouch);
    }

    public void removeDrop(ItemStack removeDrop)
    {
        List<ItemStack> newDrops = new ArrayList<ItemStack>();
        for (ItemStack drop : drops)
        {
            if (drop.isItemEqual(removeDrop))
            {
                silkTouchMap.remove(MapKeys.key(removeDrop));
            } else newDrops.add(drop);
        }
        drops = newDrops;
        if (MapKeys.getKey(removeDrop).startsWith("denseore"))
        {
            denseOre = false;
            calcChances();
        }
    }

    public List<ItemStack> getDrops()
    {
        return drops;
    }

    public List<ItemStack> getOresAndDrops()
    {
        List<ItemStack> list = new LinkedList<ItemStack>(ores.keySet());
        list.addAll(drops);
        return list;
    }
}
