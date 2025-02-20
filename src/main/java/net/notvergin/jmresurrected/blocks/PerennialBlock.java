package net.notvergin.jmresurrected.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.notvergin.jmresurrected.blocks.blockentities.JMBlockEntities;
import net.notvergin.jmresurrected.blocks.blockentities.PerennialBlockEntity;
import net.notvergin.jmresurrected.items.JMItems;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

import static net.minecraft.world.level.block.RenderShape.MODEL;

public class PerennialBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public static final ToIntFunction<BlockState> LIGHT_EMISSION = (blockState) -> blockState.getValue(ACTIVE) ? 10 : 0;

    protected PerennialBlock() {
        super(BlockBehaviour.Properties.of().mapColor(DyeColor.PURPLE).strength(200.0F).sound(SoundType.NETHERITE_BLOCK).lightLevel(LIGHT_EMISSION));
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, Boolean.valueOf(false)));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PerennialBlockEntity(pPos, pState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entity) {
        return createTickerHelper(entity, JMBlockEntities.PERENNIAL.get(), PerennialBlockEntity::tick);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            ItemStack handItem = player.getMainHandItem();

            if(blockEntity instanceof PerennialBlockEntity perennial && !player.isShiftKeyDown()) {
                if(!handItem.isEmpty() && handItem.getItem() == JMItems.REFINED_IMMORTAL_GEM.get() && perennial.getGemStack() <= 3) {
                    player.swing(hand);
                    if(!player.isCreative()) {
                        handItem.shrink(1);
                    }
                    perennial.addGemStack();
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
                    return InteractionResult.CONSUME;
                }
//                if(handItem.isEmpty() && perennial.getGemStack() > 0 && perennial.isActive()) {
//                    player.swing(hand);
//                    player.addItem(JMItems.REFINED_IMMORTAL_GEM.get().getDefaultInstance());
//                    perennial.decrementGemStack();
//                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.PLAYERS, 1.0F, 1.0F);
//                    return InteractionResult.CONSUME;
//                }
            }
        }
        return InteractionResult.CONSUME_PARTIAL;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        level.playSound(null, pos, SoundEvents.WITHER_DEATH, SoundSource.BLOCKS, 0.3F, 0.4F);
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ACTIVE);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return MODEL;
    }
}
