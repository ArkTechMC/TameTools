package com.iafenvoy.tametools.item;

import com.iafenvoy.tametools.registry.TTItemGroups;
import net.minecraft.item.Item;

public class TTItem extends Item {
    public TTItem(Settings settings) {
        super(settings.arch$tab(TTItemGroups.MAIN));
    }
}
