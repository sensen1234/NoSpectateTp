package net.nospectatetp;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * 监听玩家传送事件，限制旁观者模式的数字键传送功能。
 *
 * <p>当玩家在旁观者模式下按下数字键传送到其他玩家时，Bukkit 会触发
 * {@link PlayerTeleportEvent}，其 {@link PlayerTeleportEvent.TeleportCause}
 * 为 {@link PlayerTeleportEvent.TeleportCause#SPECTATE SPECTATE}。</p>
 *
 * <p>参考：<a href="https://jd.papermc.io/paper/26.2/org/bukkit/event/player/PlayerTeleportEvent.TeleportCause.html">
 * PlayerTeleportEvent.TeleportCause</a></p>
 */
public class SpectateTeleportListener implements Listener {

    /** 允许使用旁观者传送的权限节点。 */
    public static final String PERMISSION_USE = "nospectatetp.use";

    private final NoSpectateTp plugin;

    public SpectateTeleportListener(NoSpectateTp plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        // 仅处理旁观者传送（数字键选择玩家传送 / /spectate 命令）
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.SPECTATE) {
            return;
        }

        Player player = event.getPlayer();

        // 拥有权限的玩家可以正常使用旁观者传送
        if (player.hasPermission(PERMISSION_USE)) {
            return;
        }

        // 阻止传送
        event.setCancelled(true);

        // 发送提示消息
        String message = plugin.getMessageService().getDeniedMessage();
        Component component = plugin.getMessageService().parse(message);
        if (component != null) {
            player.sendMessage(component);
        }
    }
}
