package com.iwant2tryhard.skyblockitemsplusplus.capabilities.reforges;

import java.util.UUID;

public class DefaultItemReforges implements IItemReforges {

    private int reforgeItemType = -1;
    private int reforgeType = -1;

    //private UUID owner;

    @Override
    public void setReforgeItemType(int reforgeItemType) {
        this.reforgeItemType = reforgeItemType;
    }
    @Override
    public int getReforgeItemType() {
        return this.reforgeItemType;
    }

    @Override
    public void setReforgeType(int reforgeType) {
        this.reforgeType = reforgeType;
    }
    @Override
    public int getReforgeType() {
        return this.reforgeType;
    }

    //@Override
    //public void setOwner(UUID owner) {
        //this.owner = owner;
    //}

    //@Override
    //public UUID getOwner() {
        //return owner;
    //}
}
