package com.iwant2tryhard.skyblockitemsplusplus.core.init;

import com.iwant2tryhard.skyblockitemsplusplus.common.commands.BaseCommand;
import com.iwant2tryhard.skyblockitemsplusplus.common.commands.impl.SBIPPStatsCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;

import java.util.ArrayList;

public class CommandInit {
    private static final ArrayList<BaseCommand> commands = new ArrayList<>();

    public static void registerCommands(final RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();

        commands.add(new SBIPPStatsCommand("sbippstats", 4, true));

        commands.forEach(command -> {
            if (command.isEnabled() && command.setExecution() != null) {
                dispatcher.register(command.getBuilder());
            }
        });
    }
}
