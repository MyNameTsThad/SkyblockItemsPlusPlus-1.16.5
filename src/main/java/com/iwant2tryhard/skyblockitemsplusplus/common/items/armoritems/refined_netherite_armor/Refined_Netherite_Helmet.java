package com.iwant2tryhard.skyblockitemsplusplus.common.items.armoritems.refined_netherite_armor;

import com.iwant2tryhard.skyblockitemsplusplus.client.util.ColorText;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class Refined_Netherite_Helmet extends ArmorItem {
    public Refined_Netherite_Helmet(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Item.Properties properties) {
        super(armorMaterial, slotType, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("\u00A77" + "Defense: " + ColorText.GREEN + "+75"));
        tooltip.add(new StringTextComponent(""));
        tooltip.add(new StringTextComponent(ColorText.GOLD + "Full Set Bonus: Power of The Netherite"));
        tooltip.add(new StringTextComponent(ColorText.GRAY + "Gain a " + ColorText.RED + " +20% Boost " + ColorText.GRAY + "on ALL netherite items in your inventory."));
        tooltip.add(new StringTextComponent(""));
        tooltip.add(new StringTextComponent(ColorText.GOLD + "Piece Bonus: Heavy"));
        tooltip.add(new StringTextComponent(ColorText.GRAY + "Fall damage is added by " + ColorText.BLUE + "+25%" + ColorText.GRAY + " per piece; Max: " + ColorText.BLUE + "+100%"));
        tooltip.add(new StringTextComponent(""));
        tooltip.add(new StringTextComponent("\u00A77" + "This item can be reforged!"));
        tooltip.add(new StringTextComponent(ColorText.GOLD + "\u00A7l" +"LEGENDARY HELMET"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean isFoil(ItemStack p_77636_1_) {
        return true;
    }
}
