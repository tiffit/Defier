package net.tiffit.defier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;

import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.tiffit.defier.client.network.NetworkManager;
import net.tiffit.defier.event.DefierRecipeRegistryEvent;
import net.tiffit.defier.proxy.CommonProxy;

@Mod(modid = Defier.MODID, version = Defier.VERSION, name = Defier.NAME, useMetadata = true, dependencies = Defier.DEPENDENCIES, guiFactory = Defier.CONFIG_GUI_FACTORY)
@Mod.EventBusSubscriber
public class Defier {
	public static final String MODID = "defier";
	public static final String NAME = "Defier";
	public static final String VERSION = "1.3.1";
	public static final String DEPENDENCIES = "required-after:codechickenlib;required-after:redstoneflux;";
	public static final String CONFIG_GUI_FACTORY = "net.tiffit.defier.client.gui.config.ConfigGuiFactory";

	public static CreativeTabs CTAB = new CreativeTabs("defier") {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.defiercore);
		}

	};

	public static Logger logger;
	public static DecimalFormat LARGE_NUMBER = new DecimalFormat("#,###");
	public static File configFolder;
	public static Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

	@SidedProxy(clientSide = "net.tiffit.defier.proxy.ClientProxy", serverSide = "net.tiffit.defier.proxy.ServerProxy")
	public static CommonProxy proxy;

	@Instance(MODID)
	public static Defier INSTANCE;

	private Configuration config;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		configFolder = new File(event.getSuggestedConfigurationFile().getParentFile(), "defier");
		if (!configFolder.exists())
			configFolder.mkdir();
		config = new Configuration(new File(configFolder, "defier.cfg"), "1.0.0", true);
		ConfigData.load(config);
		logger = event.getModLog();
		NetworkManager.registerMessages();
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
		proxy.init(e);
		MinecraftForge.EVENT_BUS.post(new DefierRecipeRegistryEvent());
	}

	public void syncConfig() {
		if(config.hasChanged()){
			config.save();
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void registerRecipes(DefierRecipeRegistryEvent e) {
		e.registerRecipe(MODID, Item.getItemFromBlock(Blocks.COBBLESTONE), 1_000L);
		e.registerRecipe(MODID, Item.getItemFromBlock(Blocks.DIRT), 10_000L);
		e.registerRecipe(MODID, Item.getItemFromBlock(Blocks.SAND), 12_500L);
		e.registerRecipe(MODID, Items.REDSTONE, 50_000L);
		e.registerRecipe(MODID, Items.COAL, 75_000L);
		e.registerRecipe(MODID, Items.COOKIE, 200_000L);
		e.registerRecipe(MODID, Items.IRON_INGOT, 750_000L);
		e.registerRecipe(MODID, Items.BUCKET, 2_000_000L);
		e.registerRecipe(MODID, Items.SLIME_BALL, 1_000_000L);
		e.registerRecipe(MODID, Items.GOLD_INGOT, 3_000_000L);
		e.registerRecipe(MODID, Items.ENDER_EYE, 20_000_000L);
		e.registerRecipe(MODID, Items.BLAZE_ROD, 40_000_000L);
		e.registerRecipe(MODID, Items.GHAST_TEAR, 65_000_000L);
		e.registerRecipe(MODID, Items.DIAMOND, 500_000_000L);
		e.registerRecipe(MODID, Item.getItemFromBlock(Blocks.OBSIDIAN), 450_000_000L);
		e.registerRecipe(MODID, Items.EMERALD, 1_000_000_000L);
		e.registerRecipe(MODID, Items.DRAGON_BREATH, 5_000_000_000L);
		e.registerRecipe(MODID, Items.ELYTRA, 9_876_543_210L);
		e.registerRecipe(MODID, new DefierMetaRecipe(Items.SKULL, 1, 5_000_000_000L));
		e.registerRecipe(MODID, Items.NETHER_STAR, 75_000_000_000L);
		e.registerRecipe(MODID, Items.TOTEM_OF_UNDYING, 100_000_000_000L);
		if (Loader.isModLoaded("draconicevolution")) {
			e.registerRecipe(MODID, new DefierRecipe(new ResourceLocation("draconicevolution", "draconium_ingot"), 975_000_000L));
			e.registerRecipe(MODID, new DefierRecipe(new ResourceLocation("draconicevolution", "draconic_core"), 5_000_000_000L));
			e.registerRecipe(MODID, new DefierRecipe(new ResourceLocation("draconicevolution", "infused_obsidian"), 9_500_320_000L));
			e.registerRecipe(MODID, new DefierRecipe(new ResourceLocation("draconicevolution", "wyvern_core"), 100_009_000_000L));
			e.registerRecipe(MODID, new DefierRecipe(new ResourceLocation("draconicevolution", "draconic_ingot"), 257_000_000_000L));
		}
		if (Loader.isModLoaded("thermalfoundation")) {
			ResourceLocation material = new ResourceLocation("thermalfoundation", "material");
			e.registerRecipe(MODID, new DefierMetaRecipe(material, 128, 750_000L)); // copper
			e.registerRecipe(MODID, new DefierMetaRecipe(material, 129, 750_000L)); // tin
			e.registerRecipe(MODID, new DefierMetaRecipe(material, 130, 750_000L)); // silver
			e.registerRecipe(MODID, new DefierMetaRecipe(material, 131, 750_000L)); // lead
			e.registerRecipe(MODID, new DefierMetaRecipe(material, 132, 750_000L)); // aluminum
			e.registerRecipe(MODID, new DefierMetaRecipe(material, 134, 5_000_000L)); // platinum
			e.registerRecipe(MODID, new DefierMetaRecipe(material, 160, 7_500_000L)); // steel
			e.registerRecipe(MODID, new DefierMetaRecipe(material, 163, 4_000_000L)); // bronze
			e.registerRecipe(MODID, new DefierMetaRecipe(material, 165, 15_000_000L)); // signalum
			e.registerRecipe(MODID, new DefierMetaRecipe(material, 167, 30_000_000L)); // enderium
			e.registerRecipe(MODID, new DefierMetaRecipe(material, 1025, 25_000_000L)); // cryotheum
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerConfigRecipes(DefierRecipeRegistryEvent e) {
		File recipesFolder = new File(configFolder, "recipes");
		if (!recipesFolder.exists())
			recipesFolder.mkdir();
		for (File recipeFile : recipesFolder.listFiles()) {
			try {
				JsonObject obj = gson.fromJson(new FileReader(recipeFile), JsonObject.class);
				JsonArray array = obj.getAsJsonArray("recipes");
				if (array == null) {
					logger.error("Error reading recipe config file " + recipeFile.getName() + "! Invalid format!");
					continue;
				}
				for (int i = 0; i < array.size(); i++) {
					if (array.get(i).isJsonObject()) {
						JsonObject recipeDef = array.get(i).getAsJsonObject();
						if (recipeDef.get("item") == null || recipeDef.isJsonArray()) {
							logger.error("Error reading recipe #" + (i + 1) + " in config file " + recipeFile.getName() + "! No item specified! Skipping...");
							continue;
						}
						String itemS = recipeDef.get("item").getAsString();
						Item item = Item.REGISTRY.getObject(new ResourceLocation(itemS));
						if (item == null) {
							logger.error("Error reading recipe #" + (i + 1) + " in config file " + recipeFile.getName() + "! Invalid item! Skipping...");
							continue;
						}
						int meta = 0;
						if (recipeDef.has("meta")) {
							if (!recipeDef.get("meta").isJsonPrimitive()) {
								logger.error("Error reading recipe #" + (i + 1) + " in config file " + recipeFile.getName() + "! Invalid meta! Skipping...");
								continue;
							}
							meta = recipeDef.get("meta").getAsInt();
						}
						long cost = -1;
						if (recipeDef.has("cost")) {
							if (!recipeDef.get("cost").isJsonPrimitive()) {
								logger.error("Error reading recipe #" + (i + 1) + " in config file " + recipeFile.getName() + "! Invalid cost! Skipping...");
								continue;
							}
							cost = recipeDef.get("cost").getAsLong();
						}
						e.registerRecipe("minecraft", item, meta, cost);
					} else {
						logger.error("Error reading recipe #" + (i + 1) + " in config file " + recipeFile.getName() + "! Not an object! Skipping...");
					}
				}
			} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}
}
