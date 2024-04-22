package net.carnien.api.input.module.essential.permissionmanager.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Map;

public class CarnienScoreboardUpdateEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled = false;
    private Map<Player, Scoreboard> scoreboards;

    public CarnienScoreboardUpdateEvent(Map<Player, Scoreboard> scoreboards) {
        this.scoreboards = scoreboards;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Map<Player, Scoreboard> getScoreboards() {
        return scoreboards;
    }

    public void setScoreboards(Map<Player, Scoreboard> scoreboards) {
        this.scoreboards = scoreboards;
    }

}
