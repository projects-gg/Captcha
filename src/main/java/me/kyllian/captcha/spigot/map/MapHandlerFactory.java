package me.kyllian.captcha.spigot.map;

import me.kyllian.captcha.spigot.CaptchaPlugin;
import me.kyllian.captcha.spigot.handlers.MapHandler;
import org.bukkit.Bukkit;

public class MapHandlerFactory {

    private CaptchaPlugin plugin;

    public MapHandlerFactory(CaptchaPlugin plugin) {
        this.plugin = plugin;
    }

    public MapHandler getMapHandler() {
        return isModern() ? new MapHandlerNew(plugin) : new MapHandlerOld(plugin);
    }

    // Modern map API (FILLED_MAP + MapMeta) exists since 1.13.
    // Legacy "1.x" scheme: modern means minor >= 13.
    // New year-based scheme (e.g. 26.1.2): major > 1 is always modern.
    private boolean isModern() {
        String version = Bukkit.getBukkitVersion().split("-")[0]; // strip "-R0.1-SNAPSHOT"
        String[] parts = version.split("\\.");
        try {
            int major = Integer.parseInt(parts[0]);
            int minor = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
            return major > 1 || (major == 1 && minor >= 13);
        } catch (NumberFormatException exception) {
            return true; // unknown/future format -> assume modern
        }
    }
}
