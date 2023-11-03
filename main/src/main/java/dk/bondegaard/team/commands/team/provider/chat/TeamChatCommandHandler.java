package dk.bondegaard.team.commands.team.provider.chat;

import dk.bondegaard.team.commands.team.provider.TeamCommandProvider;
import dk.bondegaard.team.commands.team.provider.chat.subcommands.*;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
public class TeamChatCommandHandler implements TeamCommandProvider {


    private final HashMap<String, TeamChatSubCommand> commands = new HashMap<>();
    private final List<TeamChatSubCommand> commandHandlers = Arrays.asList(
            new Default("default"),
            new Create("create"),
            new Delete("delete"),
            new Info("info"),
            new Invite("invite"),
            new RemoveInvite("removeinvite"),
            new Invites("invites"),
            new Join("join"),
            new Kick("kick"),
            new Leave("leave"),
            new Chat("chat"),
            new Bank("bank"),
            new Deposit("deposit"),
            new Withdraw("withdraw"),
            new Promote("promote"),
            new Demote("demote")
    );

    public TeamChatCommandHandler() {
        for (TeamChatSubCommand cH : commandHandlers) {
            commands.put(cH.getCommand(), cH);
        }
    }

    @Override
    public void execute(Player player) {
        commands.get("default").handle(player, null);
    }

    @Override
    public void create(Player player, List<String> args) {
        commands.get("command").handle(player, args);
    }

    @Override
    public void delete(Player player, List<String> args) {
        commands.get("delete").handle(player, args);
    }

    @Override
    public void info(Player player) {
        commands.get("info").handle(player, null);
    }

    @Override
    public void invite(Player player, List<String> args) {
        commands.get("invite").handle(player, args);
    }

    @Override
    public void removeInvite(Player player, List<String> args) {
        commands.get("removeinvite").handle(player, args);
    }

    @Override
    public void invites(Player player) {
        commands.get("invites").handle(player, null);
    }

    @Override
    public void join(Player player, List<String> args) {
        commands.get("join").handle(player, args);
    }

    @Override
    public void kick(Player player, List<String> args) {
        commands.get("kick").handle(player, args);
    }

    @Override
    public void leave(Player player) {
        commands.get("leave").handle(player, null);
    }

    @Override
    public void chat(Player player, List<String> args) {
        commands.get("chat").handle(player, args);
    }

    @Override
    public void bank(Player player) {
        commands.get("bank").handle(player, null);
    }

    @Override
    public void deposit(Player player, List<String> args) {
        commands.get("deposit").handle(player, args);
    }

    @Override
    public void withdraw(Player player, List<String> args) {
        commands.get("withdraw").handle(player, args);
    }

    @Override
    public void promote(Player player, List<String> args) {
        commands.get("promote").handle(player, args);
    }

    @Override
    public void demote(Player player, List<String> args) {
        commands.get("demote").handle(player, args);
    }
}
