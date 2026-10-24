package net.nospectatetp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * /nospectatetp 命令处理器。
 *
 * <p>子命令：</p>
 * <ul>
 *     <li>{@code reload} - 重新加载配置文件（需要 {@code nospectatetp.admin} 权限）</li>
 *     <li>{@code help} - 显示帮助信息</li>
 * </ul>
 */
public class NoSpectateTpCommand implements CommandExecutor, TabCompleter {

    /** 管理权限节点。 */
    public static final String PERMISSION_ADMIN = "nospectatetp.admin";

    private static final List<String> SUBCOMMANDS = Arrays.asList("reload", "help");

    private final NoSpectateTp plugin;

    public NoSpectateTpCommand(NoSpectateTp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "reload" -> handleReload(sender);
            case "help", "?" -> sendHelp(sender);
            default -> {
                plugin.getMessageService().send(sender, "&c未知的子命令。使用 /" + label + " help 查看帮助。");
            }
        }
        return true;
    }

    /**
     * 处理 reload 子命令。
     */
    private void handleReload(CommandSender sender) {
        if (!sender.hasPermission(PERMISSION_ADMIN)) {
            plugin.getMessageService().send(sender, plugin.getMessageService().getNoPermission());
            return;
        }
        plugin.reload();
        plugin.getMessageService().send(sender, plugin.getMessageService().getReloadSuccess());
    }

    /**
     * 向发送者发送帮助信息。
     */
    private void sendHelp(CommandSender sender) {
        for (String line : plugin.getMessageService().getHelp()) {
            plugin.getMessageService().send(sender, line);
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                      @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> result = new ArrayList<>();
            String prefix = args[0].toLowerCase();
            for (String sub : SUBCOMMANDS) {
                if (!sub.startsWith(prefix)) {
                    continue;
                }
                // reload 需要管理员权限
                if (sub.equals("reload") && !sender.hasPermission(PERMISSION_ADMIN)) {
                    continue;
                }
                result.add(sub);
            }
            return result;
        }
        return Collections.emptyList();
    }
}
