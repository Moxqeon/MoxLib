package org.moxqeon.bungee.module.bridge

import com.google.gson.JsonObject
import net.md_5.bungee.api.event.PluginMessageEvent
import org.moxqeon.util.JsonUtil
import java.nio.charset.StandardCharsets
import java.util.function.Consumer

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

    class BridgePack {
        lateinit var handler: String

        lateinit var data: JsonObject
    }
}