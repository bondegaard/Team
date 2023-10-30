package dk.bondegaard.team.commands.team.provider;

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
}
