package net.notvergin.jmresurrected.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.notvergin.jmresurrected.items.JMItems;
import net.notvergin.jmresurrected.mobeffects.JMMobEffects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static net.notvergin.jmresurrected.blocks.PerennialBlock.ACTIVE;

public class PerennialBlockEntity extends BaseContainerBlockEntity {
    public static Logger LOGGER = LoggerFactory.getLogger("PerennialBlockEntity");

    private static final int defaultEffectTicks = 60;
    private int effectTicks = 0;

    private ItemStack gemStack = ItemStack.EMPTY;

    public PerennialBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(JMBlockEntities.PERENNIAL.get(), pPos, pBlockState);
        this.addGemStack();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PerennialBlockEntity perennial) {
        if(!level.isClientSide) {

            if(!perennial.isActive() && perennial.getGemStack() > 0) {
                perennial.setActive(true);
                level.playSound(null, perennial.worldPosition, SoundEvents.WITHER_SPAWN, SoundSource.BLOCKS, 0.3F, 0.3F);
                LOGGER.info("Perennial Activated");
            }

            if(perennial.isActive() && perennial.getGemStack() <= 0) {
                perennial.setActive(false);
                level.playSound(null, perennial.worldPosition, SoundEvents.WITHER_DEATH, SoundSource.BLOCKS, 0.3F, 0.4F);
                perennial.effectTicks = 0;

                LOGGER.info("Perennial Deactivated");
            }

            if(perennial.isActive()) {
                if(perennial.effectTicks > 0) {
                    perennial.effectTicks--;
                }
                if(perennial.effectTicks <= 0) {
                    doApplyEffect(level, pos, perennial.getGemStack(), perennial);
                    perennial.effectTicks = defaultEffectTicks;
                }
            }
        }
    }

    private static void doApplyEffect(Level level, BlockPos pos, int gems, PerennialBlockEntity perennial) {
        if(!level.isClientSide) {
            double range = 16 * (gems + 2);

            AABB aabb = new AABB(pos).inflate(range).expandTowards(0.0D, level.getHeight(), 0.0D);
            List<Player> players = level.getEntitiesOfClass(Player.class, aabb);

            for(Player player : players) {
                player.addEffect(new MobEffectInstance(JMMobEffects.IMMORTAL_EFFECT.get(), 300));
            }
        }
    }

    private void setActive(boolean flip) {
        level.setBlockAndUpdate(this.worldPosition, this.getBlockState().setValue(ACTIVE, flip));
    }

    public boolean isActive() {
        return this.getBlockState().getValue(ACTIVE);
    }


    public int getGemStack() {
        return gemStack.isEmpty() ? 0 : gemStack.getCount();
    }

    public void addGemStack() {
        if(gemStack.isEmpty()) {
            gemStack = new ItemStack(JMItems.IMMORTALITY_GEM.get());
        }
        else
            gemStack.grow(1);

        setChanged();
    }

    public void decrementGemStack() {
        if(!gemStack.isEmpty()) {
            gemStack.shrink(1);
            if(gemStack.isEmpty()) {
                gemStack = ItemStack.EMPTY;
            }
        }
        setChanged();
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        int i = pTag.getInt("GemCount");
        for(int  j = 0; j < i; j++) {
            this.addGemStack();
        }
        effectTicks = pTag.getInt("effectTicks");

        LOGGER.info("Perennial loaded :: GemCount = {}", gemStack);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.putInt("GemCount", gemStack.getCount());
        pTag.putInt("effectTicks", effectTicks);

        LOGGER.info("Perennial saved :: GemCount = {}", gemStack);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.perennial");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return gemStack.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return slot == 0 ? gemStack : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        if (slot == 0 && !gemStack.isEmpty()) {
            return gemStack.split(amount);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        if(slot == 0) {
            ItemStack items = gemStack;
            gemStack = ItemStack.EMPTY;
            return items;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if(slot == 0)
            gemStack = stack;

        setChanged();
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public void clearContent() {
        gemStack = ItemStack.EMPTY;
        setChanged();
    }
}
