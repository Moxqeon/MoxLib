package org.moxqeon.bukkit.module.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.flattener.FlattenerListener;
import net.kyori.adventure.text.format.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

final class LegacyComponentSerializerImpl implements LegacyComponentSerializer {
    static final Pattern DEFAULT_URL_PATTERN = Pattern.compile("(?:(https?)://)?([-\\w_.]+\\.\\w{2,})(/\\S*)?");

    static final Pattern URL_SCHEME_PATTERN = Pattern.compile("^[a-z][a-z0-9+\\-.]*:");

    private static final TextDecoration[] DECORATIONS = TextDecoration.values();

    private static final char LEGACY_BUNGEE_HEX_CHAR = 'x';

    private static final List<TextFormat> FORMATS;

    private static final String LEGACY_CHARS;

    static {
        Map<TextFormat, String> formats = new LinkedHashMap<>(22);
        formats.put(NamedTextColor.BLACK, "0");
        formats.put(NamedTextColor.DARK_BLUE, "1");
        formats.put(NamedTextColor.DARK_GREEN, "2");
        formats.put(NamedTextColor.DARK_AQUA, "3");
        formats.put(NamedTextColor.DARK_RED, "4");
        formats.put(NamedTextColor.DARK_PURPLE, "5");
        formats.put(NamedTextColor.GOLD, "6");
        formats.put(NamedTextColor.GRAY, "7");
        formats.put(NamedTextColor.DARK_GRAY, "8");
        formats.put(NamedTextColor.BLUE, "9");
        formats.put(NamedTextColor.GREEN, "a");
        formats.put(NamedTextColor.AQUA, "b");
        formats.put(NamedTextColor.RED, "c");
        formats.put(NamedTextColor.LIGHT_PURPLE, "d");
        formats.put(NamedTextColor.YELLOW, "e");
        formats.put(NamedTextColor.WHITE, "f");
        formats.put(TextDecoration.OBFUSCATED, "k");
        formats.put(TextDecoration.BOLD, "l");
        formats.put(TextDecoration.STRIKETHROUGH, "m");
        formats.put(TextDecoration.UNDERLINED, "n");
        formats.put(TextDecoration.ITALIC, "o");
        formats.put(Reset.INSTANCE, "r");
        FORMATS = List.copyOf(formats.keySet());
        LEGACY_CHARS = String.join("", formats.values());
        if (FORMATS.size() != LEGACY_CHARS.length())
            throw new IllegalStateException("FORMATS length differs from LEGACY_CHARS length");
    }

    static final LegacyComponentSerializer SECTION_SERIALIZER = new LegacyComponentSerializerImpl('§', '#', null, false, false, ComponentFlattener.basic());

    static final LegacyComponentSerializer AMPERSAND_SERIALIZER = new LegacyComponentSerializerImpl('&', '#', null, false, false, ComponentFlattener.basic());

    private final char character;

    private final char hexCharacter;

    private final TextReplacementConfig urlReplacementConfig;

    private final boolean hexColours;

    private final boolean useTerriblyStupidHexFormat;

    private final ComponentFlattener flattener;

    LegacyComponentSerializerImpl(char character, char hexCharacter, TextReplacementConfig urlReplacementConfig, boolean hexColours, boolean useTerriblyStupidHexFormat, ComponentFlattener flattener) {
        this.character = character;
        this.hexCharacter = hexCharacter;
        this.urlReplacementConfig = urlReplacementConfig;
        this.hexColours = hexColours;
        this.useTerriblyStupidHexFormat = useTerriblyStupidHexFormat;
        this.flattener = flattener;
    }

    private FormatCodeType determineFormatType(char legacy, String input, int pos) {
        if (pos >= 14) {
            int expectedCharacterPosition = pos - 14;
            int expectedIndicatorPosition = pos - 13;
            if (input.charAt(expectedCharacterPosition) == this.character && input.charAt(expectedIndicatorPosition) == 'x')
                return FormatCodeType.BUNGEECORD_UNUSUAL_HEX;
        }
        if (legacy == this.hexCharacter && input.length() - pos >= 6) return FormatCodeType.KYORI_HEX;
        if (LEGACY_CHARS.indexOf(legacy) != -1) return FormatCodeType.MOJANG_LEGACY;
        return null;
    }

    static LegacyFormat legacyFormat(char character) {
        int index = LEGACY_CHARS.indexOf(character);
        if (index != -1) {
            TextFormat format = FORMATS.get(index);
            if (format instanceof NamedTextColor) return new LegacyFormat((NamedTextColor) format);
            if (format instanceof TextDecoration) return new LegacyFormat((TextDecoration) format);
            if (format instanceof Reset) return LegacyFormat.RESET;
        }
        return null;
    }

    private DecodedFormat decodeTextFormat(char legacy, String input, int pos) {
        FormatCodeType foundFormat = determineFormatType(legacy, input, pos);
        if (foundFormat == null) return null;
        if (foundFormat == FormatCodeType.KYORI_HEX)
            return new DecodedFormat(foundFormat, TextColor.fromHexString('#' + input.substring(pos, pos + 6)));
        if (foundFormat == FormatCodeType.MOJANG_LEGACY)
            return new DecodedFormat(foundFormat, FORMATS.get(LEGACY_CHARS.indexOf(legacy)));
        if (foundFormat == FormatCodeType.BUNGEECORD_UNUSUAL_HEX) {
            StringBuilder foundHex = new StringBuilder();
            for (int i = pos - 1; i >= pos - 11; i -= 2)
                foundHex.append(input.charAt(i));
            foundHex.append('#');
            return new DecodedFormat(foundFormat, TextColor.fromHexString(foundHex.reverse().toString()));
        }
        return null;
    }

    private static boolean isHexTextColor(TextFormat format) {
        return (format instanceof TextColor && !(format instanceof NamedTextColor));
    }

    private String toLegacyCode(TextFormat format) {
        NamedTextColor namedTextColor = null;
        if (isHexTextColor(format)) {
            TextColor color = (TextColor) format;
            if (this.hexColours) {
                String hex = String.format("%06x", color.value());
                if (this.useTerriblyStupidHexFormat) {
                    StringBuilder legacy = new StringBuilder(String.valueOf('x'));
                    for (char character : hex.toCharArray())
                        legacy.append(this.character).append(character);
                    return legacy.toString();
                }
                return this.hexCharacter + hex;
            }
            namedTextColor = NamedTextColor.nearestTo(color);
        }
        int index = FORMATS.indexOf(namedTextColor);
        return Character.toString(LEGACY_CHARS.charAt(index));
    }

    private TextComponent extractUrl(TextComponent component) {
        if (this.urlReplacementConfig == null) return component;
        Component newComponent = component.replaceText(this.urlReplacementConfig);
        if (newComponent instanceof TextComponent) return (TextComponent) newComponent;
        return Component.textOfChildren(newComponent);
    }

    @NotNull
    public TextComponent deserialize(String input) {
        int next = input.lastIndexOf(this.character, input.length() - 2);
        if (next == -1) return extractUrl(Component.text(input));
        List<TextComponent> parts = new ArrayList<>();
        TextComponent.Builder current = null;
        boolean reset = false;
        int pos = input.length();
        do {
            DecodedFormat decoded = decodeTextFormat(input.charAt(next + 1), input, next + 2);
            if (decoded != null) {
                int from = next + ((decoded.encodedFormat == FormatCodeType.KYORI_HEX) ? 8 : 2);
                if (from != pos) {
                    if (current != null) {
                        if (reset) {
                            parts.add(current.build());
                            reset = false;
                            current = Component.text();
                        } else {
                            current = Component.text().append(current.build());
                        }
                    } else {
                        current = Component.text();
                    }
                    current.content(input.substring(from, pos));
                } else if (current == null) {
                    current = Component.text();
                }
                if (!reset) reset = applyFormat(current, decoded.format);
                if (decoded.encodedFormat == FormatCodeType.BUNGEECORD_UNUSUAL_HEX) next -= 12;
                pos = next;
            }
            next = input.lastIndexOf(this.character, next - 1);
        } while (next != -1);
        if (current != null) parts.add(current.build());
        String remaining = (pos > 0) ? input.substring(0, pos) : "";
        if (parts.size() == 1 && remaining.isEmpty()) return extractUrl(parts.get(0));
        Collections.reverse(parts);
        return extractUrl(Component.text().content(remaining).append(parts).build());
    }

    @NotNull
    public String serialize(Component component) {
        Cereal state = new Cereal();
        this.flattener.flatten(component, state);
        return state.toString();
    }

    private static boolean applyFormat(TextComponent.Builder builder, TextFormat format) {
        if (format instanceof TextColor) {
            builder.colorIfAbsent((TextColor) format);
            return true;
        }
        if (format instanceof TextDecoration) {
            builder.decoration((TextDecoration) format, TextDecoration.State.TRUE);
            return false;
        }
        if (format instanceof Reset) return true;
        throw new IllegalArgumentException(String.format("unknown format '%s'", format.getClass()));
    }

    @NotNull
    public LegacyComponentSerializer.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    private enum Reset implements TextFormat {
        INSTANCE
    }

    private final class Cereal implements FlattenerListener {
        private final StringBuilder sb = new StringBuilder();

        private final StyleState style = new StyleState();

        private TextFormat lastWritten;

        private StyleState[] styles = new StyleState[8];

        private int head = -1;

        public void pushStyle(@NotNull Style pushed) {
            int idx = ++this.head;
            if (idx >= this.styles.length) this.styles = Arrays.copyOf(this.styles, this.styles.length * 2);
            StyleState state = this.styles[idx];
            if (state == null) this.styles[idx] = state = new StyleState();
            if (idx > 0) {
                state.set(this.styles[idx - 1]);
            } else {
                state.clear();
            }
            state.apply(pushed);
        }

        public void component(String text) {
            if (!text.isEmpty()) {
                if (this.head < 0) throw new IllegalStateException("No style has been pushed!");
                this.styles[this.head].applyFormat();
                this.sb.append(text);
            }
        }

        public void popStyle(@NotNull Style style) {
            if (this.head-- < 0) throw new IllegalStateException("Tried to pop beyond what was pushed!");
        }

        void append(TextFormat format) {
            if (this.lastWritten != format)
                this.sb.append(LegacyComponentSerializerImpl.this.character).append(LegacyComponentSerializerImpl.this.toLegacyCode(format));
            this.lastWritten = format;
        }

        public String toString() {
            return this.sb.toString();
        }

        private Cereal() {
        }

        private final class StyleState {
            private TextColor color;

            private final Set<TextDecoration> decorations;

            private boolean needsReset;

            StyleState() {
                this.decorations = EnumSet.noneOf(TextDecoration.class);
            }

            void set(StyleState that) {
                this.color = that.color;
                this.decorations.clear();
                this.decorations.addAll(that.decorations);
            }

            public void clear() {
                this.color = null;
                this.decorations.clear();
            }

            void apply(Style component) {
                TextColor color = component.color();
                if (color != null) this.color = color;
                for (int i = 0, length = LegacyComponentSerializerImpl.DECORATIONS.length; i < length; i++) {
                    TextDecoration decoration = LegacyComponentSerializerImpl.DECORATIONS[i];
                    switch (component.decoration(decoration)) {
                        case TRUE:
                            this.decorations.add(decoration);
                            break;
                        case FALSE:
                            if (this.decorations.remove(decoration)) this.needsReset = true;
                            break;
                    }
                }
            }

            void applyFormat() {
                boolean colorChanged = (this.color != LegacyComponentSerializerImpl.Cereal.this.style.color);
                if (this.needsReset) {
                    if (!colorChanged)
                        LegacyComponentSerializerImpl.Cereal.this.append(LegacyComponentSerializerImpl.Reset.INSTANCE);
                    this.needsReset = false;
                }
                if (colorChanged || LegacyComponentSerializerImpl.Cereal.this.lastWritten == LegacyComponentSerializerImpl.Reset.INSTANCE) {
                    applyFullFormat();
                    return;
                }
                if (!this.decorations.containsAll(LegacyComponentSerializerImpl.Cereal.this.style.decorations)) {
                    applyFullFormat();
                    return;
                }
                for (TextDecoration decoration : this.decorations) {
                    if (LegacyComponentSerializerImpl.Cereal.this.style.decorations.add(decoration))
                        LegacyComponentSerializerImpl.Cereal.this.append(decoration);
                }
            }

            private void applyFullFormat() {
                if (this.color != null) {
                    LegacyComponentSerializerImpl.Cereal.this.append(this.color);
                } else {
                    LegacyComponentSerializerImpl.Cereal.this.append(LegacyComponentSerializerImpl.Reset.INSTANCE);
                }
                LegacyComponentSerializerImpl.Cereal.this.style.color = this.color;
                for (TextDecoration decoration : this.decorations)
                    LegacyComponentSerializerImpl.Cereal.this.append(decoration);
                LegacyComponentSerializerImpl.Cereal.this.style.decorations.clear();
                LegacyComponentSerializerImpl.Cereal.this.style.decorations.addAll(this.decorations);
            }
        }
    }

    static final class BuilderImpl implements LegacyComponentSerializer.Builder {
        private char character = '§';

        private char hexCharacter = '#';

        private TextReplacementConfig urlReplacementConfig = null;

        private boolean hexColours = false;

        private boolean useTerriblyStupidHexFormat = false;

        private ComponentFlattener flattener = ComponentFlattener.basic();

        BuilderImpl(LegacyComponentSerializerImpl serializer) {
            this.character = serializer.character;
            this.hexCharacter = serializer.hexCharacter;
            this.urlReplacementConfig = serializer.urlReplacementConfig;
            this.hexColours = serializer.hexColours;
            this.useTerriblyStupidHexFormat = serializer.useTerriblyStupidHexFormat;
        }

        public LegacyComponentSerializer.Builder character(char legacyCharacter) {
            this.character = legacyCharacter;
            return this;
        }

        public LegacyComponentSerializer.Builder hexCharacter(char legacyHexCharacter) {
            this.hexCharacter = legacyHexCharacter;
            return this;
        }

        public LegacyComponentSerializer.Builder extractUrls() {
            return extractUrls(LegacyComponentSerializerImpl.DEFAULT_URL_PATTERN, null);
        }

        public LegacyComponentSerializer.Builder extractUrls(Pattern pattern) {
            return extractUrls(pattern, null);
        }

        public LegacyComponentSerializer.Builder extractUrls(Style style) {
            return extractUrls(LegacyComponentSerializerImpl.DEFAULT_URL_PATTERN, style);
        }

        public LegacyComponentSerializer.Builder extractUrls(Pattern pattern, Style style) {
            Objects.requireNonNull(pattern, "pattern");
            this

                    .urlReplacementConfig = TextReplacementConfig.builder().match(pattern).replacement(url -> {
                String clickUrl = url.content();
                if (!LegacyComponentSerializerImpl.URL_SCHEME_PATTERN.matcher(clickUrl).find())
                    clickUrl = "https://" + clickUrl;
                return ((style == null) ? url : url.style(style)).clickEvent(ClickEvent.openUrl(clickUrl));
            }).build();
            return this;
        }

        public LegacyComponentSerializer.Builder hexColors() {
            this.hexColours = true;
            return this;
        }

        public LegacyComponentSerializer.Builder useUnusualXRepeatedCharacterHexFormat() {
            this.useTerriblyStupidHexFormat = true;
            return this;
        }

        public LegacyComponentSerializer.Builder flattener(ComponentFlattener flattener) {
            this.flattener = Objects.requireNonNull(flattener, "flattener");
            return this;
        }

        @NotNull
        public LegacyComponentSerializer build() {
            return new LegacyComponentSerializerImpl(this.character, this.hexCharacter, this.urlReplacementConfig, this.hexColours, this.useTerriblyStupidHexFormat, this.flattener);
        }

        BuilderImpl() {
        }
    }

    enum FormatCodeType {
        MOJANG_LEGACY, KYORI_HEX, BUNGEECORD_UNUSUAL_HEX
    }

    static final class DecodedFormat {
        final LegacyComponentSerializerImpl.FormatCodeType encodedFormat;

        final TextFormat format;

        private DecodedFormat(LegacyComponentSerializerImpl.FormatCodeType encodedFormat, TextFormat format) {
            if (format == null) throw new IllegalStateException("No format found");
            this.encodedFormat = encodedFormat;
            this.format = format;
        }
    }
}
