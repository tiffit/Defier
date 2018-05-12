package net.tiffit.defier.client.gui.config;

import java.util.Arrays;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.tiffit.defier.ConfigData;
import net.tiffit.defier.Defier;

public class DefierConfigGui extends GuiConfig {

	public DefierConfigGui(GuiScreen parent) {
		super(parent, new ConfigElement(ConfigData._CATEGORY_BALANCE).getChildElements(), Defier.MODID, true, true, Defier.NAME + " Configuration Editor");
		
	}

}
