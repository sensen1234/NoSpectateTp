# NoSpectateTp

一个适用于 **Paper 1.21.11** 的轻量级插件，用于限制旁观者模式下"按下数字键传送至玩家"的功能，并通过权限节点控制谁能使用。


## 工作原理

当玩家在旁观者模式下按数字键选择玩家进行传送时，Bukkit 会触发 `PlayerTeleportEvent`，
其传送原因为 `TeleportCause.SPECTATE`。本插件监听该事件，对没有权限的玩家取消传送。

> 参考：[PlayerTeleportEvent.TeleportCause](https://jd.papermc.io/paper/26.2/org/bukkit/event/player/PlayerTeleportEvent.TeleportCause.html)

## 权限节点

| 权限节点                  | 默认值 | 说明                                            |
|---------------------------|--------|-------------------------------------------------|
| `nospectatetp.command`    | true   | 允许使用 `/nospectatetp` 命令                   |
| `nospectatetp.use`        | false  | 允许使用旁观者数字键传送（绕过插件限制）        |
| `nospectatetp.admin`      | op     | 允许管理插件（重载配置等）                      |

## 命令

| 命令                       | 别名  | 说明                 | 权限                |
|----------------------------|-------|----------------------|---------------------|
| `/nospectatetp reload`     | nstp  | 重新加载配置文件     | `nospectatetp.admin`|
| `/nospectatetp help`       | nstp  | 显示帮助信息         | `nospectatetp.command` |

## 配置示例（config.yml）

```yaml
# 当玩家尝试使用旁观者数字键传送但缺少权限时显示的消息。
# 留空 "" 则不发送任何消息。
denied-message: "&c你没有权限使用旁观者数字键传送。"

# 当玩家执行管理命令但缺少 nospectatetp.admin 权限时显示的消息。
no-permission: "&c你没有权限执行此命令。"

# 配置重新加载成功时显示的消息。
reload-success: "&aNoSpectateTp 配置已重新加载。"

# 插件帮助信息（每行一条，支持 & 颜色代码）。
help:
  - "&6========== &eNoSpectateTp &6=========="
  - "&e/nospectatetp reload &7- &f重新加载配置文件"
  - "&e/nospectatetp help &7- &f显示此帮助信息"
```

## 构建

需要 JDK 21+ 和 Maven：

```bash
mvn clean package
```


## 兼容性

- PaperMC 1.21.11（`api-version: '1.21'`）
- Java 21+
