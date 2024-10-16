package com.iafenvoy.tametools.registry;

import com.iafenvoy.tametools.TameTools;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TTTags {
    public static final TagKey<EntityType<?>> TAME_BLACKLIST = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(TameTools.MOD_ID, "tame_blacklist"));
}
