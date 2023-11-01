package dk.bondegaard.team.commands.team.provider.chat.subcommands;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.commands.team.provider.chat.TeamChatSubCommand;
import dk.bondegaard.team.utils.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class Default extends TeamChatSubCommand {
    public Default(String command) {
        super(command);
    }

    @Override
    public void execute(Player player, List<String> args) {
        PlayerUtil.sendMessage(player, Main.getPrefix() + " §c§lTeam commands:");
        PlayerUtil.sendMessage(player, Main.getPrefix() + " §4* §c/team");
        PlayerUtil.sendMessage(player, Main.getPrefix() + " §4* §c/team create <name>");
    }
}
