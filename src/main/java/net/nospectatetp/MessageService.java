package net.nospectatetp;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息服务：负责从配置文件读取文本并将其解析为 Adventure Component。
 *
 * <p>所有消息支持 & 风格的颜色代码（legacy ampersand 格式），
 * 符合用户对可配置文本的偏好。</p>
 */
public class MessageService {

    private static final LegacyComponentSerializer SERIALIZER =
            LegacyComponentSerializer.legacyAmpersand();

    private final NoSpectateTp plugin;

    private String deniedMessage;
    private String noPermission;
    private String reloadSuccess;
    private List<String> help;

    public MessageService(NoSpectateTp plugin) {
        this.plugin = plugin;
        refresh();
    }

    /**
     * 从配置文件中重新加载所有消息。
     */
    public void refresh() {
        FileConfiguration config = plugin.getConfig();
        this.deniedMessage = config.getString("denied-message", "&c你没有权限使用旁观者数字键传送。");
        this.noPermission = config.getString("no-permission", "&c你没有权限执行此命令。");
        this.reloadSuccess = config.getString("reload-success", "&aNoSpectateTp 配置已重新加载。");
        this.help = config.getStringList("help");
        if (help.isEmpty()) {
            help = new ArrayList<>();
            help.add("&6========== &eNoSpectateTp &6==========");
            help.add("&e/nospectatetp reload &7- &f重新加载配置文件");
            help.add("&e/nospectatetp help &7- &f显示此帮助信息");
        }
    }

    /**
     * 将带 & 颜色代码的字符串解析为 Adventure Component。
     *
     * @param text 原始文本
     * @return 解析后的 Component，若文本为空则返回 null
     */
    public Component parse(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        return SERIALIZER.deserialize(text);
    }

    /**
     * 向发送者发送一条带 & 颜色代码的消息。
     *
     * @param sender  消息接收者
     * @param message 原始文本
     */
    public void send(CommandSender sender, String message) {
        Component component = parse(message);
        if (component != null) {
            sender.sendMessage(component);
        }
    }

    public String getDeniedMessage() {
        return deniedMessage;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getReloadSuccess() {
        return reloadSuccess;
    }

    public List<String> getHelp() {
        return help;
    }
}
