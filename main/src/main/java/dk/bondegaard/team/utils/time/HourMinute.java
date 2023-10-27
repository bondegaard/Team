package dk.bondegaard.team.utils.time;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;

@Setter
@Getter
@AllArgsConstructor
public class HourMinute {

    private byte hour;

    private byte minute;

    public static HourMinute from(ConfigurationSection config) {
        return new HourMinute((byte) config.getInt("hour"), (byte) config.getInt("minute"));
    }

    public static String getTimefromSeconds(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int second = (seconds % 3600) % 60;

        return hours + ":" + minutes + ":" + second;

    }

    public static String getTextTimefromSeconds(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int second = (seconds % 3600) % 60;

        if (hours > 0) {
            if (minutes > 0 && second > 0)
                return hours + " " + (hours > 1 ? "TIMER" : "TIME") + " " + minutes + " MIN " + second + " SEK";

            if (minutes > 0)
                return hours + " " + (hours > 1 ? "TIMER" : "TIME") + " " + minutes + " MIN";
            if (second > 0)
                return hours + " " + (hours > 1 ? "TIMER" : "TIME") + " " + second + " SEK";
        }
        if (minutes > 0) {
            if (second > 0)
                return minutes + " MIN " + second + " SEK";
            return second + " SEK";
        }
        if (second > 0)
            return second + " SEK";
        return "NU";
    }

}
