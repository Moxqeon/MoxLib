package org.moxqeon

import org.moxqeon.bukkit.MoxBukkit
import org.moxqeon.bungee.MoxBungee
import org.moxqeon.ext.createDir
import org.moxqeon.ext.writeResource
import org.moxqeon.platform.Platform
import org.moxqeon.platform.PlatformPlugin
import org.moxqeon.util.FileUtil
import java.io.File
import java.nio.charset.StandardCharsets
import java.util.logging.Logger


/**
 * MoxLib 库的核心入口类。
 *
 * 在对应平台（Bukkit / Bungee）加载时进行初始化：创建数据目录与配置文件，
 * 并根据当前平台获取对应的日志记录器。
 */
class MoxLib : PlatformPlugin {
    override val dataFolder: File
    override val logger: Logger


    constructor(platform: Platform) {
        Platform.set(platform)
        this.dataFolder = File(System.getProperty("user.dir"), "${FileUtil.SLASH}plugins${FileUtil.SLASH}MoxLib")
        this.dataFolder.createDir()
        if (!configFile.exists()) this.configFile.writeResource(
            MoxLib::class.java.getClassLoader(),
            "config.yml",
            StandardCharsets.UTF_8
        )
        when (platform) {
            Platform.BUKKIT -> logger = MoxBukkit.instance().logger
            Platform.BUNGEE -> logger = MoxBungee.instance().logger
        }
        logger.info("Plugin load complete")
    }


    companion object {

        @JvmStatic
        fun getAccessor() = when (Platform.get()) {
            Platform.BUKKIT -> MoxBukkit.instance()
            Platform.BUNGEE -> MoxBungee.instance()
        }

        @JvmStatic
        fun instance() = getAccessor().access
    }


}