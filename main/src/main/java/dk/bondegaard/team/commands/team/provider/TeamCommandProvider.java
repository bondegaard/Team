package dk.bondegaard.team.commands.team.provider;

import org.bukkit.entity.Player;

import java.util.List;

public interface TeamCommandProvider {

    void execute(Player player);

    void create(Player player, List<String> args);

    void delete(Player player, List<String> args);

    void info(Player player);

    void invite(Player player, List<String> args);

    void removeInvite(Player player, List<String> args);

    void invites(Player player);

    void join(Player player, List<String> args);

    void kick(Player player, List<String> args);

    void leave(Player player);

    void chat(Player player, List<String> args);

    void bank(Player player);

    void deposit(Player player, List<String> args);
}
