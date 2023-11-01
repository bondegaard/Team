package dk.bondegaard.team.commands.team.provider.chat;

import dk.bondegaard.team.Main;
import dk.bondegaard.team.teams.TeamUtils;
import dk.bondegaard.team.teams.objects.Team;
import dk.bondegaard.team.utils.PlayerUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class TeamChatSubCommand {

    @Getter
    private final String command;

    private final Map<Player, Long> cooldown = new HashMap<>();
    @Setter
    private int cooldownTime = 1;

    @Getter
    private Team team = null;

    @Setter
    private boolean requireTeam = false;

    public void execute(Player player, List<String> args) {}

    public void handle(Player player, List<String> args) {
        if (getCooldown(player)) return;
        if (!(requireTeam && setTeam(player))) return;
        execute(player, args);
    }


    private boolean setTeam(Player player) {
        Team team = TeamUtils.getPlayerTeam(player);
        if (team == null) {
            PlayerUtil.sendMessage(player, Main.getPrefix() + " §cYou are not in a team!");
            return false;
        }
        this.team = team;
        return true;
    }

    public boolean getCooldown(Player player) {
        if (player.hasPermission("team.command.cooldown.bypass")) return false;
        if (cooldown.containsKey(player)) {
            int time = (int) (cooldownTime - ((System.currentTimeMillis() - cooldown.get(player)) / 1000));
            if (time > 0) {
                PlayerUtil.sendMessage(player, Main.getPrefix()+ "There is " + time + " " + (time == 1 ? "second" : "seconds") + " cooldown. Try again in a moment.");
                return true;
            }
        }
        putCooldown(player);
        return false;
    }

    private void putCooldown(Player player) {
        if (cooldown.containsKey(player)) cooldown.replace(player, System.currentTimeMillis());
        else cooldown.put(player, System.currentTimeMillis());
    }

    public void setCooldownTime(int cooldownTime) {
        this.cooldownTime = cooldownTime;
    }
}
