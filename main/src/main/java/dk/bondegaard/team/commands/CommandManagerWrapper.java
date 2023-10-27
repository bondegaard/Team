package dk.bondegaard.team.commands;

import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.message.MessageKey;
import dk.bondegaard.team.Main;
import dk.bondegaard.team.utils.PlayerUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CommandManagerWrapper {

    private static Field bukkitCommandsField;

    private static Field commandMapField;

    static {
        try {
            bukkitCommandsField = BukkitCommandManager.class.getDeclaredField("bukkitCommands");
            bukkitCommandsField.setAccessible(true);

            commandMapField = BukkitCommandManager.class.getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private final Main plugin;

    @Getter
    private final BukkitCommandManager<CommandSender> triumphCommandManager;

    private final Map<String, org.bukkit.command.Command> allBukkitCommands;

    private final CommandMap commandMap;

    @SneakyThrows
    public CommandManagerWrapper(Main plugin) {
        this.plugin = plugin;
        this.triumphCommandManager = BukkitCommandManager.create(plugin);

        this.allBukkitCommands = (Map<String, org.bukkit.command.Command>) bukkitCommandsField.get(this.triumphCommandManager);
        this.commandMap = (CommandMap) commandMapField.get(this.triumphCommandManager);
    }

    public void register(final @NotNull BaseCommand baseCommand) {
        this.triumphCommandManager.registerCommand(baseCommand);
    }

    public void unregister(final @NotNull BaseCommand baseCommand) {
        this.unregisterSingle(this.getCommandName(baseCommand));
        this.getCommandAliases(baseCommand).forEach(this::unregisterSingle);
    }

    private void unregisterSingle(final String commandName) {
        if (commandName == null) return;

        org.bukkit.command.Command bukkitCommand = this.commandMap.getCommand(commandName);
        if (bukkitCommand == null) return;

        bukkitCommand.unregister(this.commandMap);
        this.allBukkitCommands.remove(commandName);
    }

    private String getCommandName(final @NotNull BaseCommand baseCommand) {
        if (baseCommand.getCommand() != null) return baseCommand.getCommand();

        Command commandInfo = this.getCommandAnnotation(baseCommand);
        if (commandInfo == null) return null;

        return commandInfo.value();
    }

    private List<String> getCommandAliases(final @NotNull BaseCommand baseCommand) {
        if (!baseCommand.getAlias().isEmpty()) return baseCommand.getAlias();

        Command commandInfo = this.getCommandAnnotation(baseCommand);
        if (commandInfo == null) return Collections.emptyList();

        return Arrays.asList(commandInfo.alias());
    }

    private Command getCommandAnnotation(final @NotNull BaseCommand baseCommand) {
        if (baseCommand.getClass().isAnnotationPresent(Command.class))
            return baseCommand.getClass().getAnnotation(Command.class);

        return null;
    }

    public void loadMessages() {

        this.triumphCommandManager.registerMessage(MessageKey.INVALID_ARGUMENT,
                (sender, context) -> this.resolveDefaultMessage(sender, "lang.cmd-invalid-argument"));

        this.triumphCommandManager.registerMessage(MessageKey.NOT_ENOUGH_ARGUMENTS,
                (sender, context) -> this.resolveDefaultMessage(sender, "lang.cmd-not-enough-args"));

        this.triumphCommandManager.registerMessage(MessageKey.TOO_MANY_ARGUMENTS,
                (sender, context) -> this.resolveDefaultMessage(sender, "lang.cmd-too-many-args"));

        this.triumphCommandManager.registerMessage(MessageKey.UNKNOWN_COMMAND,
                (sender, context) -> this.resolveDefaultMessage(sender, "lang.unknown-sub-command"));
    }

    private void resolveDefaultMessage(CommandSender sender, String langPath) {
        PlayerUtil.sendMessage(sender, Main.getInstance().getConfig().getString(langPath));
    }

}
