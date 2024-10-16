package com.iafenvoy.tametools.registry;

import com.iafenvoy.tametools.TameTools;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class TTItemGroups {
    public static final DeferredRegister<ItemGroup> REGISTRY = DeferredRegister.create(TameTools.MOD_ID, RegistryKeys.ITEM_GROUP);

    public static final RegistrySupplier<ItemGroup> MAIN = register("main", () -> CreativeTabRegistry.create(Text.translatable("itemGroup." + TameTools.MOD_ID + ".main"), () -> new ItemStack(TTItems.COMMON_TAME_STAFF.get())));

    public static <T extends ItemGroup> RegistrySupplier<T> register(String id, Supplier<T> itemGroup) {
        return REGISTRY.register(id, itemGroup);
    }
}
