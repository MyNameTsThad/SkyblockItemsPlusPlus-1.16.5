package com.iwant2tryhard.skyblockitemsplusplus.common.items.materials.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class RefinedIronIngot extends Item {

    public RefinedIronIngot(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        //tooltip.add(new StringTextComponent("tooltip.skyblockitemsplusplus.refined_netherite_ingot"));
        tooltip.add(new StringTextComponent("\u00A7a" + "\u00A7l" + "UNCOMMON"));
    }
}
