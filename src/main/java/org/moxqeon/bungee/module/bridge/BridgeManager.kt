package org.moxqeon.bungee.module.bridge

import com.google.gson.JsonObject
import net.md_5.bungee.api.event.PluginMessageEvent
import org.moxqeon.util.JsonUtil
import java.nio.charset.StandardCharsets
import java.util.function.Consumer

/**
 * 桥接管理器。
 *
 * 用于处理 Bungee 插件消息，并将消息数据分发到已注册的处理器。
 */
class BridgeManager {
    private val handlerMap: MutableMap<String, Consumer<JsonObject>> = mutableMapOf()

    fun onMessage(event: PluginMessageEvent) {
        val pack =
            JsonUtil.GSON.fromJson(String(event.data, StandardCharsets.UTF_8), BridgePack::class.java)!!

        handlerMap[pack.handler]!!.accept(pack.data)
    }

    fun registerHandler(handler: String, consumer: Consumer<JsonObject>) {
        handlerMap[handler] = consumer
    }

    /**
     * 桥接消息数据包，包含处理器标识与消息数据。
     */
    class BridgePack {
        lateinit var handler: String

        lateinit var data: JsonObject
    }
}