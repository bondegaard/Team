package dk.bondegaard.team.commands.team.provider;

import dev.triumphteam.cmd.core.annotation.Join;
import org.bukkit.entity.Player;

import java.util.List;

public interface TeamCommandProvider {

    void execute(Player player);

    void create(Player player, List<String> args);

    void delete(Player player, List<String> args);

    void info(Player player);

    void load(Player player);

    void save(Player player);

    void list(Player player);

    void invite(Player player, List<String> args);

    void removeInvite(Player player, List<String> args);

    void invites(Player player);

    void join(Player player, List<String> args);

    void kick(Player player, List<String> args);

    void leave(Player player);

    void chat(Player player, String message);
}
