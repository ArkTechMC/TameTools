package com.iafenvoy.tametools.registry;

import net.minecraft.world.GameRules;

public class TTGameRules {
    public static final GameRules.Key<GameRules.BooleanRule> ENABLE_TAME_STAFF = register("tame:enable_tame_staff", GameRules.BooleanRule.create(true));
    public static final GameRules.Key<GameRules.BooleanRule> TAME_TAMEABLE = register("tame:tame_tameable", GameRules.BooleanRule.create(false));
    public static final GameRules.Key<GameRules.BooleanRule> TAME_USE_EXP = register("tame:tame_use_exp", GameRules.BooleanRule.create(true));
    public static final GameRules.Key<GameRules.BooleanRule> ENABLE_RELEASE_STONE = register("tame:enable_release_stone", GameRules.BooleanRule.create(true));
    public static final GameRules.Key<GameRules.BooleanRule> ENABLE_STORE_STONE = register("tame:enable_store_stone", GameRules.BooleanRule.create(true));
    public static final GameRules.Key<GameRules.BooleanRule> ENABLE_TRANSFER_STONE = register("tame:enable_transfer_stone", GameRules.BooleanRule.create(true));
    public static final GameRules.Key<GameRules.BooleanRule> ENABLE_LEASH_TRANSFER = register("tame:enable_leash_transfer", GameRules.BooleanRule.create(false));

    public static <T extends GameRules.Rule<T>> GameRules.Key<T> register(String id, GameRules.Type<T> rule) {
        return GameRules.register(id, GameRules.Category.PLAYER, rule);
    }

    public static void init() {
    }
}
