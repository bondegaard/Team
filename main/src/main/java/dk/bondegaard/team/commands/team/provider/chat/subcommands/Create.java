package dk.bondegaard.team.commands.team.provider.chat.subcommands;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.commands.team.provider.chat.TeamChatSubCommand;
import dk.bondegaard.team.teams.TeamHandler;
import dk.bondegaard.team.teams.TeamUtils;
import dk.bondegaard.team.utils.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class Create extends TeamChatSubCommand {
    public Create(String command) {
        super(command);
    }

    @Override
    public void execute(Player player, List<String> args) {
        TeamHandler teamHandler = Main.getInstance().getTeamHandler();

        if (args.size() < 1) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §4* §c/team create <name>");
            return;
        }
        String teamName = args.get(0);

        if (TeamUtils.isPlayerInTeam(player)) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are already a member of a team!");
            return;
        }

        if (TeamUtils.existTeam(teamName)) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cTeam name is already being used!");
            return;
        }

        boolean success = teamHandler.createTeam(player, teamName);
        if (!success) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cFailed to create team!");
            return;
        }

        PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou created a team with the name " + teamName + "!");
    }
}
