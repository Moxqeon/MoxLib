package org.moxqeon.bukkit.util

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.moxqeon.bukkit.api.CommandSenderType

object CommandUtil {
    @JvmStatic
    fun runCommand(sender: CommandSender, command: String, senderType: CommandSenderType): Boolean {
        val result: Boolean
        when (senderType) {
            CommandSenderType.CONSOLE -> return Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command)
            CommandSenderType.OP -> {
                if (sender.isOp) return Bukkit.dispatchCommand(sender, command)
                sender.isOp = true
                result = Bukkit.dispatchCommand(sender, command)
                sender.isOp = false
                return result
            }

            CommandSenderType.PLAYER -> return Bukkit.dispatchCommand(sender, command)
        }
    }
}

fun CommandSender.runCommand(command: String, senderType: CommandSenderType) =
    CommandUtil.runCommand(this, command, senderType)