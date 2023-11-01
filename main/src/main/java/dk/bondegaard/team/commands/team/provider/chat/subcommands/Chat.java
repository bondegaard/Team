package dk.bondegaard.team.commands.team.provider.chat.subcommands;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.commands.team.provider.chat.TeamChatSubCommand;
import dk.bondegaard.team.teams.TeamUtils;
import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamMember;
import dk.bondegaard.team.utils.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class Chat extends TeamChatSubCommand {
    public Chat(String command) {
        super(command);
        setRequireTeam(true);
    }

    @Override
    public void execute(Player player, List<String> args) {
        Team team = this.getTeam();

        TeamMember teamMember = TeamUtils.getPlayerTeamMember(player, team);
        if (teamMember == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cCould not find your team role!");
            return;
        }

        if (teamMember.getRole().getId() < team.getTeamPerms().getSendTeamMessages().getId()) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not allowed to send your team messages!");
            return;
        }
        team.sendMessageToTeam(Main.getPrefix() + "§c"+player.getName()+": "+combineArgs(args));
    }

    private String combineArgs(List<String> args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : args) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }
}
