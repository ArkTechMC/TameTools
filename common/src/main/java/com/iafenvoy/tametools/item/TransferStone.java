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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TransferStone extends TTItem {
    private static final String SAVE_ENTITY_ID_KEY = "entity_id";

    public TransferStone() {
        super(new Settings().maxCount(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.getEntityWorld().getGameRules().getBoolean(TTGameRules.ENABLE_TRANSFER_STONE)) {
            user.sendMessage(Text.translatable("item.tame_tools.tools.disabled"));
            return ActionResult.PASS;
        }
        if (entity.getType().isIn(TTTags.TAME_BLACKLIST)) {
            user.sendMessage(Text.translatable("item.tame_tools.tools.blacklist"));
            return ActionResult.PASS;
        }
        if (entity instanceof MobEntity mob && stack.isOf(this)) {
            if (user.getEntityWorld().isClient) return ActionResult.SUCCESS;
            if (!TameUtil.isOwner(mob, user)) return ActionResult.FAIL;
            stack.getOrCreateNbt().putUuid(SAVE_ENTITY_ID_KEY, mob.getUuid());
            return ActionResult.SUCCESS;
        }
        if (entity instanceof PlayerEntity target && stack.isOf(this) && stack.getOrCreateNbt().contains(SAVE_ENTITY_ID_KEY)) {
            if (!(user.getEntityWorld() instanceof ServerWorld serverWorld)) return ActionResult.SUCCESS;
            Entity pet = serverWorld.getEntity(stack.getOrCreateNbt().getUuid(SAVE_ENTITY_ID_KEY));
            if (!(pet instanceof MobEntity mob) || !TameUtil.isOwner(mob, user)) return ActionResult.FAIL;
            stack.setCount(0);
            serverWorld.sendEntityStatus(mob, (byte) 7);
            TameUtil.setOwner(mob, user.getUuid());
            user.sendMessage(Text.translatable("action.tame_tools.transfer.from", target.getDisplayName(), mob.getDisplayName()));
            target.sendMessage(Text.translatable("action.tame_tools.transfer.to", user.getDisplayName(), mob.getDisplayName()));
            return ActionResult.SUCCESS;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("item.tame_tools.transfer_stone.tooltip1"));
        tooltip.add(Text.translatable("item.tame_tools.transfer_stone.tooltip2"));
    }
}
