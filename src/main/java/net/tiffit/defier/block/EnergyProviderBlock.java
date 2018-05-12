package net.tiffit.defier.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.tiffit.defier.Defier;
import net.tiffit.defier.ModItems;
import net.tiffit.defier.tileentity.EnergyProviderTileEntity;
import net.tiffit.defier.tileentity.EnergyProviderTileEntity.ProviderColor;

public class EnergyProviderBlock extends Block implements ITileEntityProvider {

	protected static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0D, 0D, 0D, 1D, 2 / 16D, 1D);
	protected static final AxisAlignedBB POLE_AABB = new AxisAlignedBB(7 / 16D, 0 / 16D, 7 / 16D, 9 / 16D, 12 / 16D, 9 / 16D);

	public EnergyProviderBlock() {
		super(Material.IRON);
		setUnlocalizedName(Defier.MODID + ".energyprovider");
		setRegistryName("energyprovider");
		setHardness(10.0F);
		setResistance(100.0F);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(Defier.CTAB);
		BlockCactus e;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		AxisAlignedBB basebb = BASE_AABB.offset(pos);

		if (entityBox.intersects(basebb)) {
			collidingBoxes.add(basebb);
		}
		AxisAlignedBB polebb = POLE_AABB.offset(pos);

		if (entityBox.intersects(polebb)) {
			collidingBoxes.add(polebb);
		}
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
		if (!player.inventory.getCurrentItem().isEmpty()) {
			if (te.getSpeedUpgrades() < 8 && player.inventory.getCurrentItem().getItem() == ModItems.speedstar) {
				te.addSpeedUpgrade();
				player.inventory.getCurrentItem().setCount(player.inventory.getCurrentItem().getCount() - 1);
				player.sendMessage(new TextComponentString("Added Speed Upgrade " + te.getSpeedUpgrades() + "/8"));
			}
			if (te.getSpeedUpgrades() < 4 && player.inventory.getCurrentItem().getItem() == ModItems.energystar && te.getStorageUpgrades() == player.inventory.getCurrentItem().getMetadata()) {
				te.addStorageUpgrade();
				player.inventory.getCurrentItem().setCount(player.inventory.getCurrentItem().getCount() - 1);
				player.sendMessage(new TextComponentString("Added Storage Upgrade " + te.getStorageUpgrades() + "/4"));
			}
			ProviderColor color = ProviderColor.getDye(player.inventory.getCurrentItem());
			if (color != null) {
				te.setColor(color);
				player.sendMessage(new TextComponentString("Changed color to " + color.getDyeName()));
			}
		}
		return true;
	}

	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		EnergyProviderTileEntity te = (EnergyProviderTileEntity) worldIn.getTileEntity(pos);
		ItemStack upgrade = new ItemStack(ModItems.speedstar, te.getSpeedUpgrades());
		if (!upgrade.isEmpty())
			InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), upgrade);
		for (int i = 0; i < te.getStorageUpgrades(); i++) {
			InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.energystar, 1, i));
		}
		super.breakBlock(worldIn, pos, state);
	}

}
