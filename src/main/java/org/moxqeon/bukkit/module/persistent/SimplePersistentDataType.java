package org.moxqeon.bukkit.module.persistent;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.moxqeon.api.StringSerializable;

/**
 * 简单持久化数据类型抽象类。
 * <p>
 * 将可字符串序列化的对象以字符串形式存储。
 *
 * @param <T> 被持久化对象的类型
 */
public abstract class SimplePersistentDataType<T extends StringSerializable> implements PersistentDataType<String, T> {
    @NotNull
    protected final Class<T> tClass;

    protected SimplePersistentDataType(@NotNull Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    @NotNull
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    @NotNull
    public Class<T> getComplexType() {
        return tClass;
    }

    @Override
    @NotNull
    public String toPrimitive(@NotNull T t, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
        return t.serialize();
    }

    @Override
    public @NotNull T fromPrimitive(@NotNull String s, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
        return StringSerializable.deserialize(s, tClass);
    }
}