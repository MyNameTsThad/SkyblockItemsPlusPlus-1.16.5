package com.iwant2tryhard.skyblockitemsplusplus.common.materials;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public class UndeadSwordSource implements IItemTier {

    //ASPECT_OF_THE_JERRY_SOURCE(4000, 1.6, 5, 2, 17, null);
    @Override
    public int getUses() {
        return 1000;
    }

    @Override
    public float getSpeed() {
        return 0f;
    }

    @Override
    public float getAttackDamageBonus() {
        return 0;
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }
}