package org.moxqeon.bukkit.module.persistent

import com.google.gson.JsonObject
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType
import org.moxqeon.util.EncodeUtil
import org.moxqeon.util.JsonUtil

class PersistentJsonObject : PersistentDataType<String, JsonObject> {
    override fun getPrimitiveType(): Class<String> {
        return String::class.java
    }

    override fun getComplexType(): Class<JsonObject> {
        return JsonObject::class.java
    }

    override fun toPrimitive(
        jsonObject: JsonObject,
        persistentDataAdapterContext: PersistentDataAdapterContext
    ): String {
        return EncodeUtil.base64Encode(JsonUtil.GSON.toJson(jsonObject))
    }

    override fun fromPrimitive(s: String, persistentDataAdapterContext: PersistentDataAdapterContext): JsonObject {
        return JsonUtil.PARSER.parse(EncodeUtil.base64Decode(s)).getAsJsonObject()
    }


    companion object {
        @JvmField
        val INSTANCE = PersistentJsonObject()
    }
}