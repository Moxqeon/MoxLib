package org.moxqeon.bukkit.module.persistent

import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType
import org.moxqeon.module.reflect.cast
import org.moxqeon.util.EncodeUtil
import org.moxqeon.util.JsonUtil

abstract class PersistentJsonType<T> protected constructor(
    manager: PersistentJsonTypeManager, protected val tClass: Class<T>
) : PersistentDataType<String, T> {
    init {
        manager.registerType(this)
    }

    override fun getPrimitiveType(): Class<String> {
        return String::class.java
    }

    override fun getComplexType(): Class<T> {
        return tClass
    }

    override fun toPrimitive(t: T & Any, persistentDataAdapterContext: PersistentDataAdapterContext): String {
        return EncodeUtil.base64Encode(JsonUtil.GSON.toJson(t))
    }

    override fun fromPrimitive(s: String, persistentDataAdapterContext: PersistentDataAdapterContext): T & Any {
        return JsonUtil.GSON.fromJson<T>(EncodeUtil.base64Decode(s), tClass)!!
    }
}