package me.kyllian.captcha.spigot.handlers;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import me.kyllian.captcha.spigot.CaptchaPlugin;
import me.kyllian.captcha.spigot.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataHandler {

    private CaptchaPlugin plugin;
    private Map<UUID, PlayerData> playerDataMap;

    public PlayerDataHandler(CaptchaPlugin plugin) {
        this.plugin = plugin;
        this.playerDataMap = new Object2ObjectOpenHashMap<>();
    }

    public void loadPlayerDataFromPlayer(Player player) {
        playerDataMap.put(player.getUniqueId(), new PlayerData());
    }

    public PlayerData getPlayerDataFromUUID(UUID uuid) {
        return playerDataMap.computeIfAbsent(uuid, f -> new PlayerData());
    }

    public PlayerData getPlayerDataFromPlayer(Player player) {
        return getPlayerDataFromUUID(player.getUniqueId());
    }

}
