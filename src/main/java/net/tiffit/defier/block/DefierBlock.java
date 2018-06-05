package net.tiffit.defier.block;

import codechicken.lib.render.item.IItemRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.tiffit.defier.Defier;
import net.tiffit.defier.client.render.RenderDefierItem;
import net.tiffit.defier.tileentity.DefierTileEntity;
import net.tiffit.tiffitlib.RegistryHelper.ISpecialItemRender;

public class DefierBlock extends Block implements ITileEntityProvider, ISpecialItemRender{

	public DefierBlock() {
		super(Material.IRON);
		setUnlocalizedName(Defier.MODID + ".defier");
		setRegistryName("defier");
		setHardness(100.0F);
		setResistance(5000.0F);
		setSoundType(SoundType.GLASS);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(Defier.CTAB);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new DefierTileEntity();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof DefierTileEntity)) {
			return false;
		}
		player.openGui(Defier.INSTANCE, 0, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		DefierTileEntity te = (DefierTileEntity) worldIn.getTileEntity(pos);
		IItemHandler item = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		ItemStack slot1 = item.getStackInSlot(0);
		if (!slot1.isEmpty())InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), slot1);
		ItemStack slot2 = item.getStackInSlot(1);
		if (!slot2.isEmpty())InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), slot2);
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public IItemRenderer getItemRender() {
		return new RenderDefierItem();
	}
	
}
