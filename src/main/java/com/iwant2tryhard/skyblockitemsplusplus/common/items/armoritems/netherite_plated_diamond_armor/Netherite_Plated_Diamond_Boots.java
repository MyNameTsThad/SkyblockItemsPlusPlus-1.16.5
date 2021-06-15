package com.iwant2tryhard.skyblockitemsplusplus.common.items.armoritems.netherite_plated_diamond_armor;

import com.iwant2tryhard.skyblockitemsplusplus.client.util.ColorText;
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

public class Netherite_Plated_Diamond_Boots extends ArmorItem {
    public Netherite_Plated_Diamond_Boots(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties) {
        super(armorMaterial, slotType, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("\u00A77" + "Defense: " + ColorText.GREEN.toString() + "+20"));
        tooltip.add(new StringTextComponent(""));
        tooltip.add(new StringTextComponent("\u00A77" + "This item can be reforged!"));
        tooltip.add(new StringTextComponent(ColorText.BLUE.toString() + "\u00A7l" +"RARE BOOTS"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
