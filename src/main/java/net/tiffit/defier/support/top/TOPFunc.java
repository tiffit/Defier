package net.tiffit.defier.support.top;

import java.util.function.Function;

import mcjty.theoneprobe.api.ITheOneProbe;
import net.tiffit.defier.tileentity.DefierTileEntity;
import net.tiffit.defier.tileentity.EnergyProviderTileEntity;

public class TOPFunc implements Function<ITheOneProbe, Void> {

	@Override
	public Void apply(ITheOneProbe p) {
		if(p == null)return null;
		registerInfo();
		TOPProvider prov = new TOPProvider();
		p.registerProbeConfigProvider(prov);
		p.registerProvider(prov);
		return null;
	}
	
	private void registerInfo(){
		TOPProvider.TE_MAP.clear();
		TOPProvider.TE_MAP.put(DefierTileEntity.class, new DefierTileEntityInfo());
		TOPProvider.TE_MAP.put(EnergyProviderTileEntity.class, new EnergyProviderTileEntityInfo());
	}

}
