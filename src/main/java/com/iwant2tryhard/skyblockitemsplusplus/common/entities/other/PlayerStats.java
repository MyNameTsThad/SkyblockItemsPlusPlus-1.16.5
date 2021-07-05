package com.iwant2tryhard.skyblockitemsplusplus.common.entities.other;

import com.iwant2tryhard.skyblockitemsplusplus.client.util.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;

public class PlayerStats {
    private static int manaReductionPercent;
    private static int strengthPercent;
    private static int defense;
    public static boolean debugLogging = false;
    private static int ultWiseLvl;
    private static int coins;
    private PlayerEntity owner;

    public PlayerStats(PlayerEntity owner)
    {
        this.owner = owner;
        coins = 0;
        ultWiseLvl = 0;
        defense = 0;
        manaReductionPercent = 0;
        strengthPercent = 0;
    }

    public PlayerStats(PlayerEntity owner, int coins, int ultWiseLvl, int defense, int manaReductionPercent, int strengthPercent)
    {
        this.owner = owner;
        this.coins = coins;
        this.ultWiseLvl = ultWiseLvl;
        this.defense = defense;
        this.manaReductionPercent = manaReductionPercent;
        this.strengthPercent = strengthPercent;
    }

    public static int getManaReductionPercent() {
        return manaReductionPercent;
    }
    public static void setManaReductionPercent(int manaReductionPercent) {
        PlayerStats.manaReductionPercent = manaReductionPercent;
    }
    public static void addManaReductionPercent(int manaReductionPercentToAdd) {
        PlayerStats.manaReductionPercent += manaReductionPercentToAdd;
    }
    public static void removeManaReductionPercent(int manaReductionPercentToRemove) {
        PlayerStats.manaReductionPercent -= manaReductionPercentToRemove;
    }

    public static int getStrength() {
        return strengthPercent;
    }
    public static void setStrength(int strengthPercent) {
        PlayerStats.strengthPercent = strengthPercent;
    }
    public static void addStrength(int strengthPercentToAdd) {
        PlayerStats.strengthPercent += strengthPercentToAdd;
    }
    public static void removeStrength(int strengthPercentToRemove) {
        PlayerStats.strengthPercent -= strengthPercentToRemove;
    }

    public static int getDefense() {
        return defense;
    }
    public static void setDefense(int defense) {
        PlayerStats.defense = defense;
    }

    public static int getUltWiseLvl() {
        return ultWiseLvl;
    }

    public static void setUltWiseLvl(int ultWiseLvl) {
        PlayerStats.ultWiseLvl = ultWiseLvl;
    }

    public static int getCoins() {
        return coins;
    }
    public static void setCoins(int coins) {
        PlayerStats.coins = coins;
    }

    public static float damageEntity(float srcDamage, float targetDefense, float targetMaxHealth, boolean hasOFA, boolean hasEmeraldBlade)
    {
        float actualSrcDamage;
        actualSrcDamage = hasOFA ? srcDamage + 20 : srcDamage;
        actualSrcDamage += hasEmeraldBlade ? calcEmeraldBladeBoost() : 0f;
        float targetEHP = targetDefense * 10;
        return (strengthPercent / 100f) * (actualSrcDamage / (targetEHP + (targetMaxHealth * 5)) * targetMaxHealth);

    }

    public static int calcManaUsage(float manaUsage)
    {
        int returnValue = Math.round((manaUsage * (1f - (manaReductionPercent / 100f)) * (1f - (ultWiseLvl * 0.1f))));

        if (PlayerStats.debugLogging)
        {
            Minecraft.getInstance().player.displayClientMessage(ITextComponent.nullToEmpty("ultWiseLvl :" + ultWiseLvl), false);
            Minecraft.getInstance().player.displayClientMessage(ITextComponent.nullToEmpty("ultWiseLvl :" + ultWiseLvl * 0.1f), false);
            Minecraft.getInstance().player.displayClientMessage(ITextComponent.nullToEmpty("ultWiseLvl :" + (manaUsage * (1f - (manaReductionPercent / 100f)) * (1f - (ultWiseLvl * 0.1f)))), false);
            Minecraft.getInstance().player.displayClientMessage(ITextComponent.nullToEmpty("returnValue :" + returnValue), false);
        }
        return Math.max(returnValue, 1);
    }
    public static boolean isEnoughMana(float manaUsage, PlayerEntity player)
    {
        return player.getFoodData().getFoodLevel() - PlayerStats.calcManaUsage(manaUsage) >= 0f;
    }

    public static float getLifeStealDamageMultiplier(float armorDamageReductionPercent)
    {
        int fullRoundedPercent = Math.round((armorDamageReductionPercent / 80f) * 100f);
        if (PlayerStats.debugLogging) {
            ClientUtils.SendPrivateMessage(fullRoundedPercent >= 50f ?
                "((50f - (" + fullRoundedPercent + " - 50f)) + 50) / 100" :
                "((50f - " + fullRoundedPercent + ") + 100) / 100)");
        }
        return fullRoundedPercent >= 50f ?
                ((50f - (fullRoundedPercent - 50f)) + 50) / 100 :
                ((50f - fullRoundedPercent) + 100) / 100;
    }

    public static int calcEmeraldBladeBoost()
    {
        return (int) Math.round(2.5D * Math.sqrt(Math.sqrt(coins)));
    }

}
