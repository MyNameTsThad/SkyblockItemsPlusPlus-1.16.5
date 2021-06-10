package com.iwant2tryhard.skyblockitemsplusplus.common.items.items.hoes;

import com.iwant2tryhard.skyblockitemsplusplus.client.util.ColorText;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class Netherite_Plated_Diamond_Hoe extends HoeItem {
    public Netherite_Plated_Diamond_Hoe(IItemTier itemTier, int damage, float attackSpeed, Properties properties) {
        super(itemTier, damage, attackSpeed, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("\u00A77" + "True Damage: " + "\u00A7c" + "+1"));
        tooltip.add(new TranslationTextComponent(""));
        tooltip.add(new TranslationTextComponent("\u00A77" + "This item can be reforged!"));
        tooltip.add(new TranslationTextComponent(ColorText.BLUE.toString() + "\u00A7l" + "RARE HOE"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
