package com.iafenvoy.tametools.item;

import com.iafenvoy.tametools.registry.TTGameRules;
import com.iafenvoy.tametools.registry.TTTags;
import com.iafenvoy.tametools.util.TameUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StoreStoneItem extends TTItem {
    public static final String SAVE_ENTITY_KEY = "entity";
    private static final String SAVE_ENTITY_TYPE_KEY = "entity_type";

    public StoreStoneItem() {
        super(new Settings().maxCount(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public ActionResult useOnEntity(ItemStack itemStack, PlayerEntity user, LivingEntity entity, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!user.getEntityWorld().getGameRules().getBoolean(TTGameRules.ENABLE_STORE_STONE)) {
            user.sendMessage(Text.translatable("item.tame_tools.tools.disabled"));
            return ActionResult.PASS;
        }
        if (entity.getType().isIn(TTTags.TAME_BLACKLIST)) {
            user.sendMessage(Text.translatable("item.tame_tools.tools.blacklist"));
            return ActionResult.PASS;
        }
        if (entity instanceof MobEntity mob && stack.isOf(this) && !stack.getOrCreateNbt().contains(SAVE_ENTITY_KEY)) {
            if (user.getEntityWorld().isClient) return ActionResult.SUCCESS;
            if (!TameUtil.isOwner(mob, user)) return ActionResult.FAIL;
            NbtCompound nbt = new NbtCompound();
            mob.writeNbt(nbt);
            stack.getOrCreateNbt().put(SAVE_ENTITY_KEY, nbt);
            stack.getOrCreateNbt().putString(SAVE_ENTITY_TYPE_KEY, Registries.ENTITY_TYPE.getId(mob.getType()).toString());
            mob.remove(Entity.RemovalReason.DISCARDED);
            return ActionResult.SUCCESS;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity user = context.getPlayer();
        if (user != null) {
            if (!user.getEntityWorld().getGameRules().getBoolean(TTGameRules.ENABLE_STORE_STONE)) {
                user.sendMessage(Text.translatable("item.tame_tools.tools.disabled"));
                return ActionResult.PASS;
            }
            ItemStack stack = context.getStack();
            World world = context.getWorld();
            if (stack.isOf(this) && stack.getOrCreateNbt().contains(SAVE_ENTITY_KEY)) {
                Identifier type = Identifier.tryParse(stack.getOrCreateNbt().getString(SAVE_ENTITY_TYPE_KEY));
                Entity entity = Registries.ENTITY_TYPE.get(type).create(world);
                if (entity == null) return ActionResult.FAIL;
                NbtCompound compound = stack.getOrCreateNbt().getCompound(SAVE_ENTITY_KEY);
                entity.readNbt(compound);
                BlockPos pos = context.getBlockPos().up();
                entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                world.spawnEntity(entity);
                stack.getOrCreateNbt().remove(SAVE_ENTITY_KEY);
                stack.getOrCreateNbt().remove(SAVE_ENTITY_TYPE_KEY);
                return ActionResult.SUCCESS;
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("item.tame_tools.store_stone.tooltip1"));
        tooltip.add(Text.translatable("item.tame_tools.store_stone.tooltip2"));
        if (stack.getOrCreateNbt().contains(SAVE_ENTITY_TYPE_KEY)) {
            Identifier id = Identifier.tryParse(stack.getOrCreateNbt().getString(SAVE_ENTITY_TYPE_KEY));
            if (id != null)
                tooltip.add(Text.translatable("item.tame_tools.store_stone.stored").append(Text.translatable(id.toTranslationKey("entity"))));
        }
    }
}
