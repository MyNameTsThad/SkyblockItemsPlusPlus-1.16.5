package com.iwant2tryhard.skyblockitemsplusplus.capabilities;

public interface IPlayerSkills {
    void setCombatXp(int xp);
    int getCombatXp();
    void setCombatLvl(int lvl);
    int getCombatLvl();

    void setMiningXp(int xp);
    int getMiningXp();
    void setMiningLvl(int lvl);
    int getMiningLvl();

    void setFarmingXp(int xp);
    int getFarmingXp();
    void setFarmingLvl(int lvl);
    int getFarmingLvl();

    void setForagingXp(int xp);
    int getForagingXp();
    void setForagingLvl(int lvl);
    int getForagingLvl();

    void setEnchantingXp(int xp);
    int getEnchantingXp();
    void setEnchantingLvl(int lvl);
    int getEnchantingLvl();

    void setFishingXp(int xp);
    int getFishingXp();
    void setFishingLvl(int lvl);
    int getFishingLvl();

    void setAlchemyXp(int xp);
    int getAlchemyXp();
    void setAlchemyLvl(int lvl);
    int getAlchemyLvl();


    void AwardCombatXp(int xp);
    void AwardMiningXp(int xp);
    void AwardFarmingXp(int xp);
    void AwardForagingXp(int xp);
    void AwardEnchantingXp(int xp);
    void AwardFishingXp(int xp);
    void AwardAlchemyXp(int xp);
}
