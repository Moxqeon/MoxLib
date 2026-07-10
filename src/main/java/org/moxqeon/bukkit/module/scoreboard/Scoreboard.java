package org.moxqeon.bukkit.module.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.moxqeon.bukkit.MoxBukkit;

import java.util.ArrayList;
import java.util.List;

public final class Scoreboard {
    private final MoxBukkit moxBukkit;

    private final Player player;

    private FastBoard fastBoard;

    private boolean isHidden = true;

    private String title = "";

    private String[] lines = new String[15];

    public Scoreboard(@NotNull MoxBukkit moxBukkit, @NotNull Player player) {
        Bukkit.getScoreboardManager().getNewScoreboard();
        this.moxBukkit = moxBukkit;
        this.player = player;
    }

    @NotNull
    public Player player() {
        return this.player;
    }

    public void title(@NotNull String title) {
        if (!this.title.equals(title)) {
            this.title = title;
            if (this.fastBoard != null)
                this.fastBoard.updateTitle(this.title);
        }
    }

    @NotNull
    public String title() {
        return this.title;
    }

    public void line(int line, @NotNull String content) {
        if (this.lines[line] == null || !this.lines[line].equals(content)) {
            this.lines[line] = content;
            if (this.fastBoard != null)
                updateLines();
        }
    }

    @Nullable
    public String line(int index) {
        return this.lines[index];
    }

    public void lines(@NotNull String... lines) {
        System.arraycopy(lines, 0, this.lines, 0, Math.min(this.lines.length, lines.length));
        if (this.fastBoard != null)
            updateLines();
    }

    @NotNull
    public String[] lines() {
        return this.lines;
    }

    public void deleteLine(int index) {
        if (this.lines[index] != null) {
            this.lines[index] = null;
            if (this.fastBoard != null)
                updateLines();
        }
    }

    public void show() {
        if (this.isHidden) {
            if (fastBoard == null)
                this.fastBoard = new FastBoard(this.player);
            this.isHidden = false;
            this.moxBukkit.getScoreboardManager().addBoard(this);
            this.fastBoard.updateTitle(this.title);
            updateLines();
        }
    }

    public void hide() {
        if (!this.isHidden) {
            this.isHidden = true;
            this.moxBukkit.getScoreboardManager().removeBoard(this);
            this.fastBoard.delete();
            this.fastBoard = null;
        }
    }

    public void clear() {
        this.title = "";
        this.lines = new String[15];
        if (this.fastBoard != null) {
            this.fastBoard.updateTitle("");
            updateLines();
        }
    }

    public boolean isHidden() {
        return this.isHidden;
    }

    private void updateLines() {
        List<String> lines = new ArrayList<>();
        for (String line : this.lines) {
            if (line != null)
                lines.add(line);
        }
        this.fastBoard.updateLines(lines);
    }
}
