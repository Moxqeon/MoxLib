package org.moxqeon.platform

import org.moxqeon.ext.subFile
import java.io.File
import java.util.logging.Logger

interface PlatformPlugin {
    val dataFolder: File


    val configFile: File
        get() = dataFolder.subFile("config.yml")

    val logger: Logger
}