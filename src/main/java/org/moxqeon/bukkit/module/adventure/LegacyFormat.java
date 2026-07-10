package org.moxqeon.bukkit.module.adventure;

import java.util.Objects;
import java.util.stream.Stream;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;

public final class LegacyFormat implements Examinable {
    static final LegacyFormat RESET = new LegacyFormat(true);

    private final NamedTextColor color;

    private final TextDecoration decoration;

    private final boolean reset;

    LegacyFormat(NamedTextColor color) {
        this.color = color;
        this.decoration = null;
        this.reset = false;
    }

    LegacyFormat(TextDecoration decoration) {
        this.color = null;
        this.decoration = decoration;
        this.reset = false;
    }

    private LegacyFormat(boolean reset) {
        this.color = null;
        this.decoration = null;
        this.reset = reset;
    }

    public TextColor color() {
        return this.color;
    }

    public TextDecoration decoration() {
        return this.decoration;
    }

    public boolean reset() {
        return this.reset;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        LegacyFormat that = (LegacyFormat)other;
        return (this.color == that.color && this.decoration == that.decoration && this.reset == that.reset);
    }

    public int hashCode() {
        int result = Objects.hashCode(this.color);
        result = 31 * result + Objects.hashCode(this.decoration);
        result = 31 * result + Boolean.hashCode(this.reset);
        return result;
    }

    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("color", this.color),
                ExaminableProperty.of("decoration", this.decoration),
                ExaminableProperty.of("reset", this.reset));
    }
}

