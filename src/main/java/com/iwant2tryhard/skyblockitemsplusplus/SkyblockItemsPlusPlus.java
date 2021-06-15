package com.iwant2tryhard.skyblockitemsplusplus;

import com.iwant2tryhard.skyblockitemsplusplus.core.init.BlockInit;
import com.iwant2tryhard.skyblockitemsplusplus.core.init.EnchantmentInit;
import com.iwant2tryhard.skyblockitemsplusplus.core.init.ItemInit;
import com.iwant2tryhard.skyblockitemsplusplus.world.OreGeneration;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SkyblockItemsPlusPlus.MOD_ID)
public class SkyblockItemsPlusPlus
{
    public static final String MOD_ID = "skyblockitemsplusplus";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final ItemGroup SkyblockItems_Combat = new SkyblockItemsCombat();
    public static final ItemGroup SkyblockItems_Materials = new SkyblockItemsMaterials();
    public static final ItemGroup SkyblockItems_Armors = new SkyblockItemsArmors();
    public static final ItemGroup SkyblockItems_Tools = new SkyblockItemsTools();

    public SkyblockItemsPlusPlus() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);

        EnchantmentInit.ENCHANTMENTS.register(bus);
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGeneration::generateOres);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }

    public static class SkyblockItemsCombat extends ItemGroup
    {
        public SkyblockItemsCombat()
        {
            super("SkyblockItems_Combat");
        }

        @Override
        public ItemStack makeIcon() {
            return ItemInit.ROGUE_SWORD.get().getDefaultInstance();
        }
    }
    public static class SkyblockItemsMaterials extends ItemGroup
    {
        public SkyblockItemsMaterials()
        {
            super("SkyblockItems_Materials");
        }

        @Override
        public ItemStack makeIcon() {
            return ItemInit.REFINED_NETHERITE_BLOCK.get().getDefaultInstance();
        }
    }
    public static class SkyblockItemsArmors extends ItemGroup
    {
        public SkyblockItemsArmors()
        {
            super("SkyblockItems_Armors");
        }

        @Override
        public ItemStack makeIcon() {
            return ItemInit.FARM_SUIT_HELMET.get().getDefaultInstance();
        }
    }
    public static class SkyblockItemsTools extends ItemGroup
    {
        public SkyblockItemsTools()
        {
            super("SkyblockItems_Tools");
        }

        @Override
        public ItemStack makeIcon() {
            return ItemInit.NETHERITE_PLATED_DIAMOND_PICKAXE.get().getDefaultInstance();
        }
    }
}


