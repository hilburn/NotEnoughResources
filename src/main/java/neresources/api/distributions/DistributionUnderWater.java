package neresources.api.distributions;

import neresources.api.utils.DistributionHelpers;

public class DistributionUnderWater extends DistributionBase{

    public DistributionUnderWater(double maxChance) {
        super(DistributionHelpers.getUnderwaterDistribution(maxChance));
        this.bestHeight = 61;
    }
}
