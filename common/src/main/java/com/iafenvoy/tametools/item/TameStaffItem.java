package com.iafenvoy.tametools.item;

import com.iafenvoy.tameable.data.EntityTameData;
import com.iafenvoy.tametools.registry.TTGameRules;
import com.iafenvoy.tametools.registry.TTTags;
import com.iafenvoy.tametools.util.TameUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TameStaffItem extends TTItem {
    private final float health;

    protected TameStaffItem(Builder builder) {
        super(builder.parent);
        this.health = builder.health;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.getEntityWorld().getGameRules().getBoolean(TTGameRules.ENABLE_TAME_STAFF)) {
            user.sendMessage(Text.translatable("item.tame_tools.tools.disabled"));
            return ActionResult.PASS;
        }
        if (entity.getType().isIn(TTTags.TAME_BLACKLIST)) {
            user.sendMessage(Text.translatable("item.tame_tools.tools.blacklist"));
            return ActionResult.PASS;
        }
        boolean useExp = user.getEntityWorld().getGameRules().getBoolean(TTGameRules.TAME_USE_EXP);
        if (entity instanceof MobEntity mob && !TameUtil.hasOwner(mob) && entity.getHealth() <= this.health && (!useExp || user.isCreative() || user.experienceLevel >= entity.getHealth())) {
            EntityTameData.get(mob).setOwner(user.getUuid());
            if (useExp)
                user.addExperienceLevels((int) -entity.getHealth());
            mob.getEntityWorld().sendEntityStatus(mob, (byte) 7);
            return ActionResult.SUCCESS;
        } else return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("item.tame_tools.tame_staff.tooltip", this.health));
    }

    public static class Builder extends Settings {
        public static final Builder COMMON = new Builder(new Settings().maxDamage(5).rarity(Rarity.COMMON)).setHealth(10);
        public static final Builder EPIC = new Builder(new Settings().maxDamage(10).rarity(Rarity.UNCOMMON)).setHealth(30);
        public static final Builder LEGENDARY = new Builder(new Settings().maxDamage(15).rarity(Rarity.RARE)).setHealth(75);
        public static final Builder CREATIVE = new Builder(new Settings().maxDamage(100000).rarity(Rarity.EPIC)).setHealth(100000);
        private float health;
        private final Settings parent;

        public Builder(Settings parent) {
            this.parent = parent;
        }

        public Builder setHealth(float health) {
            this.health = health;
            return this;
        }

        public TameStaffItem build() {
            return new TameStaffItem(this);
        }
    }
}
