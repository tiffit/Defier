package net.tiffit.defier.block;

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
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.tiffit.defier.Defier;
import net.tiffit.defier.ModItems;
import net.tiffit.defier.tileentity.EnergyProviderTileEntity;

public class EnergyProviderBlock extends Block implements ITileEntityProvider {

	public EnergyProviderBlock() {
		super(Material.IRON);
		setUnlocalizedName(Defier.MODID + ".energyprovider");
		setRegistryName("energyprovider");
		setHardness(10.0F);
		setResistance(100.0F);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(Defier.CTAB);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new EnergyProviderTileEntity();
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
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		EnergyProviderTileEntity te = (EnergyProviderTileEntity) world.getTileEntity(pos);
		if(te.getUpgrades() < 8 && !player.inventory.getCurrentItem().isEmpty() && player.inventory.getCurrentItem().getItem() == ModItems.speedstar){
			te.addUpgrade();
			player.inventory.getCurrentItem().setCount(player.inventory.getCurrentItem().getCount()-1);
			player.sendMessage(new TextComponentString("Added Speed Upgrade " + te.getUpgrades() + "/8"));
		}
		return true;
	}
	
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		EnergyProviderTileEntity te = (EnergyProviderTileEntity) worldIn.getTileEntity(pos);
		ItemStack upgrade = new ItemStack(ModItems.speedstar, te.getUpgrades());
		if (!upgrade.isEmpty())InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), upgrade);
		super.breakBlock(worldIn, pos, state);
	}
	
}
