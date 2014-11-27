package neresources.compatibility.appliedenergistics2;

import appeng.api.AEApi;
import appeng.core.AEConfig;
import appeng.core.features.AEFeature;
import neresources.api.NEResourcesAPI;
import neresources.api.distributions.DistributionSquare;
import neresources.compatibility.CompatBase;
import neresources.registry.AddOreDrop;
import neresources.registry.OreEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class AE2Compat extends CompatBase {
    private static final AE2Compat instance = new AE2Compat();

    public static CompatBase instance() {
        return instance;
    }

    @Override
    protected void init() {
        ItemStack quartzOre = AEApi.instance().blocks().blockQuartzOre.stack(1);
        ItemStack chargedQuartz = AEApi.instance().blocks().blockQuartzOreCharged.stack(1);
        ItemStack itemQuartz = AEApi.instance().materials().materialCertusQuartzCrystal.stack(1);
        ItemStack itemCharged = AEApi.instance().materials().materialCertusQuartzCrystalCharged.stack(1);

        OreDictionary.registerOre("oreChargedCertusQuartz",chargedQuartz);
        OreDictionary.registerOre("crystalChargedCertusQuartz",itemCharged);

        NEResourcesAPI.registerEntry(new AddOreDrop(quartzOre,itemQuartz));
        NEResourcesAPI.registerEntry(new AddOreDrop(chargedQuartz,itemCharged));

        boolean spawn = AEConfig.instance.featureFlags.contains(AEFeature.CertusQuartzWorldGen);
        if (!spawn) return;
        int numVeins = AEConfig.instance.quartzOresClusterAmount;
        int veinSize = AEConfig.instance.quartzOresPerCluster;
        float spawnChargedChance = AEConfig.instance.spawnChargedChance;
        int minY = 52;
        int maxY = 74;
        float chance = (float)(numVeins*veinSize)/((maxY-minY+1)*256)*1.5F;

        registerOre(new OreEntry(quartzOre,new DistributionSquare(Math.max(0, minY - veinSize / 2), minY, maxY, Math.min(maxY + veinSize / 2, 255), chance*spawnChargedChance)));
        registerOre(new OreEntry(chargedQuartz,new DistributionSquare(Math.max(0, minY - veinSize / 2), minY, maxY, Math.min(maxY + veinSize / 2, 255), chance*(1F-spawnChargedChance))));
    }
}
