package org.moxqeon.bukkit.module.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.util.Buildable;

import java.util.regex.Pattern;

public interface LegacyComponentSerializer extends ComponentSerializer<Component, TextComponent, String>, Buildable<LegacyComponentSerializer, LegacyComponentSerializer.Builder> {
    public static final char SECTION_CHAR = '§';

    public static final char AMPERSAND_CHAR = '&';

    public static final char HEX_CHAR = '#';

    static LegacyComponentSerializer legacySection() {
        return LegacyComponentSerializerImpl.SECTION_SERIALIZER;
    }

    static LegacyComponentSerializer legacyAmpersand() {
        return LegacyComponentSerializerImpl.AMPERSAND_SERIALIZER;
    }

    static LegacyComponentSerializer legacy(char legacyCharacter) {
        if (legacyCharacter == '§')
            return legacySection();
        if (legacyCharacter == '&')
            return legacyAmpersand();
        return builder().character(legacyCharacter).build();
    }

    static LegacyFormat parseChar(char character) {
        return LegacyComponentSerializerImpl.legacyFormat(character);
    }

    static Builder builder() {
        return new LegacyComponentSerializerImpl.BuilderImpl();
    }

    TextComponent deserialize(String paramString);

    String serialize(Component paramComponent);

    public static interface Builder extends Buildable.Builder<LegacyComponentSerializer> {
        Builder character(char param1Char);

        Builder hexCharacter(char param1Char);

        Builder extractUrls();

        Builder extractUrls(Pattern param1Pattern);

        Builder extractUrls(Style param1Style);

        Builder extractUrls(Pattern param1Pattern, Style param1Style);

        Builder hexColors();

        Builder useUnusualXRepeatedCharacterHexFormat();

        Builder flattener(ComponentFlattener param1ComponentFlattener);

        LegacyComponentSerializer build();
    }
}
