package dk.bondegaard.team.common.util.compmaterial;

import lombok.Getter;
import org.bukkit.Bukkit;

/**
 * Represents the current Minecraft version the plugin loaded on
 */
public final class MinecraftVersion {

    /**
     * The string representation of the version, for example V1_13
     */
    private static String serverVersion;

    /**
     * The wrapper representation of the version
     */
    @Getter
    private static Version current;

    // Initialize the version
    static {
        try {

            final String packageName = Bukkit.getServer() == null ? "" : Bukkit.getServer().getClass().getPackage().getName();
            final String curr = packageName.substring(packageName.lastIndexOf('.') + 1);
            final boolean hasGatekeeper = !"craftbukkit".equals(curr) && !"".equals(packageName);

            serverVersion = curr;

            if (hasGatekeeper) {
                int pos = 0;

                for (final char ch : curr.toCharArray()) {
                    pos++;

                    if (pos > 2 && ch == 'R')
                        break;
                }

                final String numericVersion = curr.substring(1, pos - 2).replace("_", ".");

                int found = 0;

                for (final char ch : numericVersion.toCharArray())
                    if (ch == '.')
                        found++;

                current = Version.parse(Integer.parseInt(numericVersion.split("\\.")[1]));

            } else
                current = Version.v1_3_AND_BELOW;

        } catch (final Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Does the current Minecraft version equal the given version?
     *
     * @param version
     * @return
     */
    public static boolean equals(Version version) {
        return compareWith(version) == 0;
    }

    /**
     * Is the current Minecraft version older than the given version?
     *
     * @param version
     * @return
     */
    public static boolean olderThan(Version version) {
        return compareWith(version) < 0;
    }

    /**
     * Is the current Minecraft version newer than the given version?
     *
     * @param version
     * @return
     */
    public static boolean newerThan(Version version) {
        return compareWith(version) > 0;
    }

    /**
     * Is the current Minecraft version at equals or newer than the given version?
     *
     * @param version
     * @return
     */
    public static boolean atLeast(Version version) {
        return equals(version) || newerThan(version);
    }

    // Compares two versions by the number
    private static int compareWith(Version version) {
        try {
            return getCurrent().minorVersionNumber - version.minorVersionNumber;

        } catch (final Throwable t) {
            t.printStackTrace();

            return 0;
        }
    }

    /**
     * Return the class versioning such as v1_14_R1
     *
     * @return
     */
    public static String getServerVersion() {
        return serverVersion.equals("craftbukkit") ? "" : serverVersion;
    }

    /**
     * The version wrapper
     */
    public enum Version {
        v1_20(20, false),
        v1_19(19),
        v1_18(18),
        v1_17(17),
        v1_16(16),
        v1_15(15),
        v1_14(14),
        v1_13(13),
        v1_12(12),
        v1_11(11),
        v1_10(10),
        v1_9(9),
        v1_8(8),
        v1_7(7),
        v1_6(6),
        v1_5(5),
        v1_4(4),
        v1_3_AND_BELOW(3);

        /**
         * The numeric version (the second part of the 1.x number)
         */
        private final int minorVersionNumber;

        /**
         * Is this library tested with this Minecraft version?
         */
        @Getter
        private final boolean tested;

        /**
         * Creates new enum for a MC version that is tested
         *
         * @param version
         */
        Version(int version) {
            this(version, true);
        }

        /**
         * Creates new enum for a MC version
         *
         * @param version
         * @param tested
         */
        Version(int version, boolean tested) {
            this.minorVersionNumber = version;
            this.tested = tested;
        }

        /**
         * Attempts to get the version from number
         *
         * @param number
         * @return
         * @throws RuntimeException if number not found
         */
        private static Version parse(int number) {
            for (final Version version : values()) {
                if (version.minorVersionNumber == number) {
                    return version;
                }
            }
            return null;
        }

        /**
         * @see Enum#toString()
         */
        @Override
        public String toString() {
            return "1." + this.minorVersionNumber;
        }
    }
}
