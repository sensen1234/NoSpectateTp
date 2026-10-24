package net.nospectatetp;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * NoSpectateTp 主类。
 *
 * <p>该插件用于限制旁观者模式下通过按下数字键传送至其他玩家的功能。
 * 默认情况下所有玩家的旁观者传送都会被阻止；
 * 拥有 {@code nospectatetp.use} 权限的玩家可以绕过此限制。</p>
 */
public final class NoSpectateTp extends JavaPlugin {

    private MessageService messageService;

    @Override
    public void onEnable() {
        // 保存默认配置
        saveDefaultConfig();

        // 初始化消息服务
        this.messageService = new MessageService(this);

        // 注册事件监听器
        getServer().getPluginManager().registerEvents(new SpectateTeleportListener(this), this);

        // 注册命令
        NoSpectateTpCommand command = new NoSpectateTpCommand(this);
        PluginCommand pluginCommand = getCommand("nospectatetp");
        if (pluginCommand != null) {
            pluginCommand.setExecutor(command);
            pluginCommand.setTabCompleter(command);
        } else {
            getLogger().severe("无法注册命令 nospectatetp，请检查 plugin.yml。");
        }

        getLogger().info("NoSpectateTp 已启用。");
    }

    @Override
    public void onDisable() {
        getLogger().info("NoSpectateTp 已禁用。");
    }

    /**
     * 重新加载配置并刷新消息服务。
     */
    public void reload() {
        reloadConfig();
        this.messageService.refresh();
    }

    /**
     * 获取消息服务实例。
     *
     * @return 消息服务
     */
    public MessageService getMessageService() {
        return messageService;
    }
}
