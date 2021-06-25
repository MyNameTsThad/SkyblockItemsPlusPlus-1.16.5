package com.iwant2tryhard.skyblockitemsplusplus.common.materials.armor;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class NetheritePlatedDiamondArmorSource implements IArmorMaterial {
    private static final int[] baseDurability = {407, 592, 555, 481};
    private final int durabilityMultiplier = 1;
    private final int[] baseDefense = {3, 8, 6, 3};

    @Override
    public int getDurabilityForSlot(EquipmentSlotType slotType) {
        return baseDurability[slotType.getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType slotType) {
        return this.baseDefense[slotType.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return 25;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_NETHERITE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Items.NETHERITE_SCRAP);
    }

    @Override
    public String getName() {
        return "netherite_plated_diamond_armor";
    }

    @Override
    public float getToughness() {
        return 8f;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.5f;
    }
}
