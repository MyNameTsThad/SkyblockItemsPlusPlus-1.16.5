package com.iwant2tryhard.skyblockitemsplusplus.common.items;

import com.iwant2tryhard.skyblockitemsplusplus.client.util.ColorText;
import com.iwant2tryhard.skyblockitemsplusplus.common.entities.PlayerStats;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.SoundEvent;

import javax.annotation.Nullable;
import java.util.List;

public class Aspect_Of_The_End extends SwordItem {
    private static float manaUsage = 10f;
    public Aspect_Of_The_End(IItemTier itemTier, int damage, float attackSpeed, Properties properties) {
        super(itemTier, damage, attackSpeed, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("\u00A77" + "Damage: " + "\u00A7c" + "+100"));
        tooltip.add(new TranslationTextComponent("\u00A77" + "Strength: " + "\u00A7c" + "+100"));
        tooltip.add(new TranslationTextComponent(""));
        tooltip.add(new TranslationTextComponent("\u00A76" + "Item Ablity: Instant Transmission " + "\u00A7e" + "\u00A7l" + "RIGHT CLICK"));
        tooltip.add(new TranslationTextComponent("\u00A77" + "Teleport " + ColorText.GREEN.toString() + "8 blocks " + ColorText.GRAY.toString() + "ahead of"));
        tooltip.add(new TranslationTextComponent(ColorText.GRAY.toString() + "you and gain +50 " + ColorText.WHITE.toString() + "Speed"));
        tooltip.add(new TranslationTextComponent(ColorText.GRAY.toString() + "for " + ColorText.GREEN.toString() + "3 seconds."));
        tooltip.add(new TranslationTextComponent(ColorText.GRAY.toString() + "Mana Cost: " + ColorText.AQUA.toString() + "50"));
        tooltip.add(new TranslationTextComponent("\u00A77" + "This item can be reforged!"));
        tooltip.add(new TranslationTextComponent(ColorText.BLUE + "\u00A7l" +"RARE SWORD"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand hand) {
        if (Math.round(player.getFoodData().getFoodLevel() - manaUsage * ((100f - PlayerStats.getManaReductionPercent()) / 100f)) >= 0f)
        {
            player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() - Math.round(manaUsage * ((100f - PlayerStats.getManaReductionPercent()) / 100f)));

            Minecraft.getInstance().player.displayClientMessage(ITextComponent.nullToEmpty("\u00A73" + "Used " + "\u00A76" + "Instant Transmission! " + "\u00A73" + "(" + Math.round(manaUsage * ((100f - PlayerStats.getManaReductionPercent()) / 100f)) + " Mana)"), false);

            player.setPos(player.position().x, player.position().y + 8, player.position().z);
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 60, 4));
            worldIn.playSound(player, player, SoundEvents.ENDERMAN_TELEPORT,SoundCategory.NEUTRAL, 1.0f, 1.0f);
        }
        //return super.use(worldIn, player, hand);
        return ActionResult.success(player.getItemInHand(hand));
    }
}