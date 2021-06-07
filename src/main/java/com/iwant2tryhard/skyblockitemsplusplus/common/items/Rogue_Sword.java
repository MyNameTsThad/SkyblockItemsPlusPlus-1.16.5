package com.iwant2tryhard.skyblockitemsplusplus.common.items;

import com.iwant2tryhard.skyblockitemsplusplus.common.entities.PlayerStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class Rogue_Sword extends SwordItem {
    private static float manaUsage = 10f;
    public Rogue_Sword(IItemTier itemTier, int damage, float attackSpeed, Properties properties) {
        super(itemTier, damage, attackSpeed, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("\u00A77" + "Damage: " + "\u00A7c" + "+20"));
        tooltip.add(new TranslationTextComponent(""));
        tooltip.add(new TranslationTextComponent("\u00A76" + "Item Ablity: Speed Boost " + "\u00A7e" + "\u00A7l" + "RIGHT CLICK"));
        tooltip.add(new TranslationTextComponent("\u00A77" + "Gain " + "\u00A7a" +"1 speed " + "\u00A77" + "for"));
        tooltip.add(new TranslationTextComponent("\u00A7f" +"30 " + "\u00A77" + "seconds."));
        tooltip.add(new TranslationTextComponent("\u00A77" + "Mana Cost: " + "\u00A7b" + "50" + "\u00A77" + " (Mana Reduction -" + PlayerStats.getManaReductionPercent() + "\u00A77" + "%)"));
        tooltip.add(new TranslationTextComponent(""));
        tooltip.add(new TranslationTextComponent("\u00A77" + "This item can be reforged!"));
        tooltip.add(new TranslationTextComponent("\u00A7l" +"COMMON SWORD"));
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand hand) {
        System.out.println(player.getMainHandItem().getMaxDamage());
        if (Math.round(player.getFoodData().getFoodLevel() - 10f * ((100f - PlayerStats.getManaReductionPercent()) / 100f)) >= 0f)
        {
            //Minecraft.getInstance().player.chat("reducedHunger : " + (10f * ((100f - PlayerStats.getManaReductionPercent()) / 100f)));
            //Minecraft.getInstance().player.chat("currentHunger : " + player.getFoodData().getFoodLevel());

            player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() - Math.round(10f * ((100f - PlayerStats.getManaReductionPercent()) / 100f)));

            Minecraft.getInstance().player.displayClientMessage(ITextComponent.nullToEmpty("\u00A73" + "Used " + "\u00A76" + "Speed Boost! " + "\u00A73" + "(" + Math.round(manaUsage * ((100f - PlayerStats.getManaReductionPercent()) / 100f)) + " Mana)"), false);
            worldIn.playSound(player, player, SoundEvents.LAVA_POP,SoundCategory.NEUTRAL, 1.0f, 1.0f);
            if (player.hasEffect(Effects.MOVEMENT_SPEED))
            {
                player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 600, 2));
            }else
            {
                player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 600, 1));
            }
        }
        //return super.use(worldIn, player, hand);
        return ActionResult.success(player.getItemInHand(hand));
    }
}
