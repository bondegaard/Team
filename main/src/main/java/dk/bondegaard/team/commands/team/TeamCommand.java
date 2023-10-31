package dk.bondegaard.team.commands.team;


import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import dk.bondegaard.team.Main;
import dk.bondegaard.team.commands.team.provider.CommandChatMessages;
import dk.bondegaard.team.commands.team.provider.CommandGui;
import dk.bondegaard.team.commands.team.provider.TeamCommandProvider;
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
                this.teamCommandProvider = new CommandGui();
                break;
            default:
                this.teamCommandProvider = new CommandChatMessages();
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

    @SubCommand(value = "load")
    public void load(Player player) {
        this.teamCommandProvider.load(player);
    }

    @SubCommand(value = "save")
    public void save(Player player) {
        this.teamCommandProvider.save(player);
    }

    @SubCommand(value = "list")
    public void list(Player player) {
        this.teamCommandProvider.list(player);
    }

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

}
