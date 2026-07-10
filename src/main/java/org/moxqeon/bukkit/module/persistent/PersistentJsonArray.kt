package org.moxqeon.bukkit.module.persistent

import com.google.gson.JsonArray
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType
import org.moxqeon.util.EncodeUtil
import org.moxqeon.util.JsonUtil

class PersistentJsonArray : PersistentDataType<String, JsonArray> {
    override fun getPrimitiveType(): Class<String> {
        return String::class.java
    }

    override fun getComplexType(): Class<JsonArray> {
        return JsonArray::class.java
    }

    override fun toPrimitive(jsonArray: JsonArray, persistentDataAdapterContext: PersistentDataAdapterContext): String {
        return EncodeUtil.base64Encode(JsonUtil.GSON.toJson(jsonArray))
    }

    override fun fromPrimitive(s: String, persistentDataAdapterContext: PersistentDataAdapterContext): JsonArray {
        return JsonUtil.PARSER.parse(EncodeUtil.base64Decode(s)).getAsJsonArray()
    }


    companion object {
        @JvmField
        val INSTANCE = PersistentJsonArray()
    }

}