package org.moxqeon.platform

import org.moxqeon.ext.subFile
import java.io.File
import java.util.logging.Logger

/**
 * 平台插件统一接口。
 *
 * 抽象了不同平台插件共有的数据目录、配置文件与日志记录器。
 */
interface PlatformPlugin {
    val dataFolder: File


    val configFile: File
        get() = dataFolder.subFile("config.yml")

    val logger: Logger
}