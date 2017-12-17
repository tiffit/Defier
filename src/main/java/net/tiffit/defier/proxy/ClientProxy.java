package net.tiffit.defier.proxy;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import codechicken.lib.model.ModelRegistryHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.tiffit.defier.ModItems;
import net.tiffit.defier.client.render.RenderDefierItem;
import net.tiffit.defier.client.render.RenderEnergyProviderItem;
import net.tiffit.defier.client.tesr.DefierTESR;
import net.tiffit.defier.client.tesr.EnergyProviderTESR;
import net.tiffit.defier.tileentity.DefierTileEntity;
import net.tiffit.defier.tileentity.EnergyProviderTileEntity;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	public static int defierSphereIdOutside;
	public static int defierSphereIdInside;
	
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
    	super.init(e);
    	{
    		Sphere sphere = new Sphere();
    		sphere.setDrawStyle(GLU.GLU_FILL);
    		sphere.setNormals(GLU.GLU_SMOOTH);
    		sphere.setOrientation(GLU.GLU_OUTSIDE);

    		defierSphereIdOutside = GL11.glGenLists(1);
    		GL11.glNewList(defierSphereIdOutside, GL11.GL_COMPILE);
    		sphere.draw(0.5F, 30, 30);
    		GL11.glEndList();

    		sphere.setOrientation(GLU.GLU_INSIDE);
    		defierSphereIdInside = GL11.glGenLists(1);
    		GL11.glNewList(defierSphereIdInside, GL11.GL_COMPILE);
    		sphere.draw(0.5F, 30, 30);
    		GL11.glEndList();
    	}
    	ClientRegistry.bindTileEntitySpecialRenderer(DefierTileEntity.class, new DefierTESR());
    	ClientRegistry.bindTileEntitySpecialRenderer(EnergyProviderTileEntity.class, new EnergyProviderTESR());
    }
    
	@SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
    	registerItem(ModItems.largemass);
    	registerItem(ModItems.defiercore);
    	registerItem(ModItems.pattern);
    	registerItem(ModItems.defierstar);
    	registerItem(ModItems.energystar);
    	registerItem(ModItems.energystar, 1);
    	registerItem(ModItems.energystar, 2);
    	registerItem(ModItems.energystar, 3);
    	registerItem(ModItems.strongstar);
    	registerItem(ModItems.speedstar);
    	registerItem(Item.getItemFromBlock(ModItems.compressor));
    	registerItem(Item.getItemFromBlock(ModItems.patternmolder));
    	
    	ModelRegistryHelper.registerItemRenderer(Item.getItemFromBlock(ModItems.defier), new RenderDefierItem());
    	ModelRegistryHelper.registerItemRenderer(Item.getItemFromBlock(ModItems.energyprovider), new RenderEnergyProviderItem());
    }
    
    private static void registerItem(Item item){
    	registerItem(item, 0);
    }
    
    private static void registerItem(Item item, int meta){
    	ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName() + (meta == 0 ? "" : meta + ""), "inventory"));
    }
    
}
