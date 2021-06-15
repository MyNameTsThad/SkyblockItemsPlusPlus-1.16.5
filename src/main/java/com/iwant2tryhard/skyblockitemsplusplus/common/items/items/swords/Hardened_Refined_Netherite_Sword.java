package com.iwant2tryhard.skyblockitemsplusplus.common.items.items.swords;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class Hardened_Refined_Netherite_Sword extends SwordItem {
    public Hardened_Refined_Netherite_Sword(IItemTier itemTier, int damage, float attackSpeed, Properties properties) {
        super(itemTier, damage, attackSpeed, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("\u00A77" + "Damage: " + "\u00A7c" + "+350"));
        tooltip.add(new StringTextComponent(""));
        tooltip.add(new StringTextComponent("\u00A77" + "This item can be reforged!"));
        tooltip.add(new StringTextComponent("\u00A7c" + "\u00A7l" + "SUPREME SWORD"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
