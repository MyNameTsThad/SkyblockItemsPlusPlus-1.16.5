package com.iwant2tryhard.skyblockitemsplusplus.common.items.armoritems.farm_suit;

import com.iwant2tryhard.skyblockitemsplusplus.client.util.ColorText;
import com.iwant2tryhard.skyblockitemsplusplus.common.util.CustomRarity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class Farm_Suit_Chestplate extends ArmorItem {
    private final CustomRarity rarity;
    public Farm_Suit_Chestplate(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties, CustomRarity rarity) {
        super(armorMaterial, slotType, properties);
        this.rarity = rarity;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("\u00A77" + "Defense: " + ColorText.GREEN + "+15"));
        tooltip.add(new StringTextComponent(""));
        tooltip.add(new StringTextComponent(ColorText.GOLD + "Full Set Bonus: Bonus Speed"));
        tooltip.add(new StringTextComponent(ColorText.GRAY + "Gain " + ColorText.WHITE + "Speed 3 " + ColorText.GRAY + "if over crops or farmland."));
        tooltip.add(new StringTextComponent(""));
        tooltip.add(new StringTextComponent("\u00A77" + "This item can be reforged!"));
        tooltip.add(new StringTextComponent(rarity + "CHESTPLATE"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
