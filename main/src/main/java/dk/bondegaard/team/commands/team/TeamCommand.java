package dk.bondegaard.team.commands.team;


import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import dk.bondegaard.team.Main;
import dk.bondegaard.team.commands.team.provider.TeamCommandProvider;
import dk.bondegaard.team.commands.team.provider.chat.TeamChatCommandHandler;
import dk.bondegaard.team.commands.team.provider.gui.TeamGuiCommandHandler;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TeamCommand extends BaseCommand {

    private final TeamCommandProvider teamCommandProvider;


    public TeamCommand() {
        super("team", Collections.singletonList("teams"));

        String teamType = Main.getInstance().getConfig().contains("team") ? Main.getInstance().getConfig().getString("team") : "";
        switch (teamType) {
            case "gui":
            case "menu":
                this.teamCommandProvider = new TeamGuiCommandHandler();
                break;
            default:
                this.teamCommandProvider = new TeamChatCommandHandler();
        }
    }

    @Default
    public void execute(Player player) {
        this.teamCommandProvider.execute(player);
    }

    @SubCommand(value = "create", alias = {"opret", "StartANewAdventure"})
    public void create(Player player, List<String> args) {
        this.teamCommandProvider.create(player, args);
    }

    @SubCommand(value = "delete", alias = {"disband", "destroy"})
    public void delete(Player player, List<String> args) {
        this.teamCommandProvider.delete(player, args);
    }

    @SubCommand("info")
    public void info(Player player) {this.teamCommandProvider.info(player);}

    @SubCommand(value = "invite")
    public void invite(Player player, List<String> args) {
        this.teamCommandProvider.invite(player, args);
    }

    @SubCommand(value = "removeinvite", alias = {"delinvite", "uninvite"})
    public void removeInvite(Player player, List<String> args) {
        this.teamCommandProvider.removeInvite(player, args);
    }

    @SubCommand(value = "invites")
    public void invites(Player player) {
        this.teamCommandProvider.invites(player);
    }

    @SubCommand(value = "join")
    public void join(Player player, List<String> args) {
        this.teamCommandProvider.join(player, args);
    }

    @SubCommand(value = "kick")
    public void kick(Player player, List<String> args) {
        this.teamCommandProvider.kick(player, args);
    }

    @SubCommand(value = "leave")
    public void leave(Player player) {
        this.teamCommandProvider.leave(player);
    }

    @SubCommand(value = "chat", alias = {"c", "teamchat", "message"})
    public void chat(Player player, List<String> args) {
        this.teamCommandProvider.chat(player, args);
    }

    @SubCommand(value = "bank")
    public void bank(Player player) {
        this.teamCommandProvider.bank(player);
    }

    @SubCommand(value = "deposit")
    public void deposit(Player player, List<String> args) {
        this.teamCommandProvider.deposit(player, args);
    }

}
