package org.moxqeon.bukkit.api;

/**
 * 命令执行者类型枚举。
 * <p>
 * 用于指定命令的执行身份，可配合
 * {@link org.moxqeon.bukkit.util.CommandUtil#runCommand} 使用。
 */
public enum CommandSenderType {
    /** 以玩家身份执行命令。 */
    PLAYER,

    /** 以控制台身份执行命令。 */
    CONSOLE,

    /** 临时赋予 OP 权限后以发送者身份执行命令。 */
    OP;
}