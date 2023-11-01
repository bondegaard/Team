package dk.bondegaard.team.commands.team.provider.chat.subcommands;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.commands.team.provider.chat.TeamChatSubCommand;
import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.teams.objects.TeamRole;
import dk.bondegaard.team.utils.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class Delete extends TeamChatSubCommand {
    public Delete(String command) {
        super(command);
        setRequireTeam(true);
    }

    @Override
    public void execute(Player player, List<String> args) {
        Team team = this.getTeam();

        if (args.size() < 1) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §4* §c/team delete <name>");
            return;
        }

        String teamName = args.get(0);

        // Check if member
        if (!team.isTeamMember(player)) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not a member of this team!");
            return;
        }

        // Check that player is the team leader
        if (team.getTeamMember(player).getRole() != TeamRole.LEADER) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cOnly the leader of a team can delete the team!");
            return;
        }

        // Check that the player wrote the correct team
        if (!team.getName().equalsIgnoreCase(teamName)) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou need to write the name of your team!");
            return;
        }

        boolean success = Main.getInstance().getDataProvider().deleteTeam(team);
        if (!success) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cFailed to delete team!");
            return;
        }

        PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou deleted your team with the name " + teamName + "!");
    }
}
