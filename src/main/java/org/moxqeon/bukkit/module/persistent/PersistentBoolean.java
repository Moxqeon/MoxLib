package org.moxqeon.bukkit.module.persistent;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

/**
 * 布尔持久化数据类型。
 * <p>
 * 将布尔值以字节形式（0 / 1）存储。
 */
@Deprecated(since = "MC1.19.4")
public final class PersistentBoolean implements PersistentDataType<Byte, Boolean> {
    public static final PersistentBoolean INSTANCE = new PersistentBoolean();

    @NotNull
    @Override
    public Class<Byte> getPrimitiveType() {
        return Byte.class;
    }

    @NotNull
    @Override
    public Class<Boolean> getComplexType() {
        return Boolean.class;
    }

    @NotNull
    @Override
    public Byte toPrimitive(@NotNull Boolean aBoolean, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
        return (byte) (aBoolean ? 1 : 0);
    }

    @NotNull
    @Override
    public Boolean fromPrimitive(@NotNull Byte aByte, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
        return aByte == 1;
    }
}