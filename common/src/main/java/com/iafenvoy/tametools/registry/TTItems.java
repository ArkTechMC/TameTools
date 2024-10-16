package com.iafenvoy.tametools.registry;

import com.iafenvoy.tametools.TameTools;
import com.iafenvoy.tametools.item.*;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Supplier;

public class TTItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(TameTools.MOD_ID, RegistryKeys.ITEM);

    public static final RegistrySupplier<TameStaffItem> COMMON_TAME_STAFF = register("common_tame_staff", TameStaffItem.Builder.COMMON::build);
    public static final RegistrySupplier<TameStaffItem> EPIC_TAME_STAFF = register("epic_tame_staff", TameStaffItem.Builder.EPIC::build);
    public static final RegistrySupplier<TameStaffItem> LEGENDARY_TAME_STAFF = register("legendary_tame_staff", TameStaffItem.Builder.LEGENDARY::build);
    public static final RegistrySupplier<TameStaffItem> CREATIVE_TAME_STAFF = register("creative_tame_staff", TameStaffItem.Builder.CREATIVE::build);

    public static final RegistrySupplier<ReleaseStoneItem> RELEASE_STONE = register("release_stone", ReleaseStoneItem::new);
    public static final RegistrySupplier<StoreStoneItem> STORE_STONE = register("store_stone", StoreStoneItem::new);
    public static final RegistrySupplier<TransferStone> TRANSFER_STONE = register("transfer_stone", TransferStone::new);

    public static <T extends Item> RegistrySupplier<T> register(String id, Supplier<T> item) {
        return REGISTRY.register(id, item);
    }
}
