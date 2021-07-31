package com.iwant2tryhard.skyblockitemsplusplus.capabilities.playerskills;

import com.iwant2tryhard.skyblockitemsplusplus.SkyblockItemsPlusPlus;
import com.iwant2tryhard.skyblockitemsplusplus.client.util.ClientUtils;
import com.iwant2tryhard.skyblockitemsplusplus.slayers.capability.CapabilitySlayerStatus;
import com.iwant2tryhard.skyblockitemsplusplus.slayers.capability.SlayerStatusProvider;
import com.iwant2tryhard.skyblockitemsplusplus.slayers.entity.slayerdealer.SlayerDealerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SkyblockItemsPlusPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerSkillsEventHandler {
    @SubscribeEvent
    public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            PlayerSkillsProvider providerskills = new PlayerSkillsProvider();
            event.addCapability(new ResourceLocation(SkyblockItemsPlusPlus.MOD_ID, "skills"), providerskills);
            event.addListener(providerskills::invalidate);
        }
    }

    @SubscribeEvent
    public static void AwardCombatStats(LivingDeathEvent event)
    {
        if (event.getSource().getEntity() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getSource().getEntity();
            player.getCapability(CapabilityPlayerSkills.PLAYER_STATS_CAPABILITY).ifPresent(skills -> {
                skills.AwardCombatXp(event.getEntityLiving().getExperienceReward((PlayerEntity) event.getSource().getEntity()));
                ClientUtils.SendPrivateMessage("Combat XP: " + skills.getCombatXp() + "/" + ((Math.pow((skills.getCombatLvl() + 3), 3) - Math.pow((skills.getCombatLvl() + 1), 2)) * 5));
                ClientUtils.SendPrivateMessage("Progress: " + Math.round(skills.getCombatXp() / ((Math.pow((skills.getCombatLvl() + 3), 3) - Math.pow((skills.getCombatLvl() + 1), 2)) * 5) * 100) + "%");
                ClientUtils.SendPrivateMessage("Combat LVL: " + skills.getCombatLvl());
            });
            //ClientUtils.SendPrivateMessage("calc: " + "(((" + PlayerSkills.combatLvl + " + 3) ^ 2) - ((" + PlayerSkills.combatLvl + " + 1) ^ 2))");
        }
    }

    @SubscribeEvent
    public static void AwardMiningStats(final BlockEvent.BreakEvent event)
    {
        if (!event.getPlayer().isCreative())
        {
            event.getPlayer().getCapability(CapabilityPlayerSkills.PLAYER_STATS_CAPABILITY).ifPresent(skills -> {
                if (event.getState().is(BlockTags.CROPS))
                {
                    skills.AwardFarmingXp(6);
                    ClientUtils.SendPrivateMessage("Farming XP: " + skills.getFarmingXp() + "/" + ((Math.pow((skills.getFarmingLvl() + 3), 3) - Math.pow((skills.getFarmingLvl() + 1), 2)) * 5));
                    ClientUtils.SendPrivateMessage("Progress: " + Math.round(skills.getFarmingXp() / ((Math.pow((skills.getFarmingLvl() + 3), 3) - Math.pow((skills.getFarmingLvl() + 1), 2)) * 5) * 100) + "%");
                    ClientUtils.SendPrivateMessage("Mining LVL: " + skills.getFarmingLvl());
                }
                else if (event.getState().is(BlockTags.LOGS))
                {
                    skills.AwardForagingXp(10);
                    ClientUtils.SendPrivateMessage("Foraging XP: " + skills.getForagingXp() + "/" + ((Math.pow((skills.getForagingLvl() + 3), 3) - Math.pow((skills.getForagingLvl() + 1), 2)) * 5));
                    ClientUtils.SendPrivateMessage("Progress: " + Math.round(skills.getForagingXp() / ((Math.pow((skills.getForagingLvl() + 3), 3) - Math.pow((skills.getForagingLvl() + 1), 2)) * 5) * 100) + "%");
                    ClientUtils.SendPrivateMessage("Mining LVL: " + skills.getForagingLvl());
                }
                else
                {
                    skills.AwardMiningXp(((event.getState().getHarvestLevel() + 1) * 5) + (event.getExpToDrop() * 2));
                    ClientUtils.SendPrivateMessage("Mining XP: " + skills.getMiningXp() + "/" + ((Math.pow((skills.getMiningLvl() + 3), 3) - Math.pow((skills.getMiningLvl() + 1), 2)) * 5));
                    ClientUtils.SendPrivateMessage("Progress: " + Math.round(skills.getMiningXp() / ((Math.pow((skills.getMiningLvl() + 3), 3) - Math.pow((skills.getMiningLvl() + 1), 2)) * 5) * 100) + "%");
                    ClientUtils.SendPrivateMessage("Mining LVL: " + skills.getMiningLvl());
                }
            });
        }
    }

    @SubscribeEvent
    public static void AwardAlchemyStats(final PotionBrewEvent event)
    {
        //skills.AwardAlchemyXP(10);
        /*ClientUtils.SendPrivateMessage("Mining XP: " + PlayerSkills.miningXp + "/" + ((Math.pow((PlayerSkills.miningLvl + 3), 3) - Math.pow((PlayerSkills.miningLvl + 1), 2)) * 5));
        ClientUtils.SendPrivateMessage("Progress: " + Math.round(PlayerSkills.miningXp / ((Math.pow((PlayerSkills.miningLvl + 3), 3) - Math.pow((PlayerSkills.miningLvl + 1), 2)) * 5) * 100) + "%");
        ClientUtils.SendPrivateMessage("Mining LVL: " + PlayerSkills.miningLvl);*/
    }


    @SubscribeEvent
    public static void onInteractSlayerDealerLeft(LivingAttackEvent event)
    {
        if (event.getSource().getEntity() instanceof PlayerEntity & event.getEntityLiving() instanceof SlayerDealerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getSource().getEntity();
            player.getCapability(CapabilityPlayerSkills.PLAYER_STATS_CAPABILITY).ifPresent(slayerStatus -> {
                ClientUtils.SendPrivateMessage("Your Slayer Stats is:");
                ClientUtils.SendPrivateMessage("    Your Current Slayer Stats is: " + slayerStatus.getSlayerStatus());
                ClientUtils.SendPrivateMessage("    Your Mob Count is: " + slayerStatus.getMobCount());
                ClientUtils.SendPrivateMessage("    Your Zombie Slayer is: " + slayerStatus.getZombieSlayerLvl());
                ClientUtils.SendPrivateMessage("    Your Spider Slayer is: " + slayerStatus.getSpiderSlayerLvl());
                ClientUtils.SendPrivateMessage("    Your Wolf Slayer is: " + slayerStatus.getWolfSlayerLvl());
                ClientUtils.SendPrivateMessage("    Your Enderman Slayer is: " + slayerStatus.getEndermanSlayerLvl());
                player.level.playSound((PlayerEntity) event.getSource().getEntity(), event.getSource().getEntity(), SoundEvents.VILLAGER_AMBIENT, SoundCategory.NEUTRAL, 1.0f, 1f);
            });
        }
    }
    @SubscribeEvent
    public static void onInteractSlayerDealerRight(PlayerInteractEvent.EntityInteract event)
    {
        PlayerEntity player = event.getPlayer();
        player.getCapability(CapabilityPlayerSkills.PLAYER_STATS_CAPABILITY).ifPresent(slayerStatus -> {
            ClientUtils.SendPrivateMessage("Switch Slayer coming soon!");
            player.level.playSound(event.getPlayer(), event.getPlayer(), SoundEvents.NOTE_BLOCK_BIT, SoundCategory.NEUTRAL, 1.0f, 1f);
        });
    }


    /*public static void addMana(PlayerItemConsume event)
    {
        PlayerEntity player = event.getPlayer();
        player.getCapability(CapabilityPlayerSkills.PLAYER_STATS_CAPABILITY).ifPresent(slayerStatus -> {
            ClientUtils.SendPrivateMessage("Switch Slayer coming soon!");
            player.level.playSound(event.getPlayer(), event.getPlayer(), SoundEvents.NOTE_BLOCK_BIT, SoundCategory.NEUTRAL, 1.0f, 1f);
        });
    }*/
}
