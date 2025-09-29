package me.kyllian.captcha.spigot;

import me.kyllian.captcha.spigot.captchas.Captcha;
import me.kyllian.captcha.spigot.captchas.SolveState;
import me.kyllian.captcha.spigot.commands.CaptchaCommand;
import me.kyllian.captcha.spigot.handlers.*;
import me.kyllian.captcha.spigot.listeners.*;
import me.kyllian.captcha.spigot.listeners.login.LoginListener;
import me.kyllian.captcha.spigot.listeners.login.PlayerJoinListener;
import me.kyllian.captcha.spigot.map.MapHandlerFactory;
import me.kyllian.captcha.spigot.utilities.SafeArea;

import java.io.File;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CaptchaPlugin extends JavaPlugin {

    public static CaptchaPlugin INSTANCE;

    private CaptchaHandler captchaHandler;
    private FontHandler fontHandler;
    private MapHandler mapHandler;
    private MessageHandler messageHandler;
    private PlayerDataHandler playerDataHandler;
    private SafeArea safeArea;

    @Override
    public void onEnable() {
        INSTANCE = this;
        getServer().getMessenger().registerOutgoingPluginChannel(this, "kyllian:captcha");

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        // Check if file exists in Captcha folder
        if (!new File(getDataFolder(), "background.png").exists()) {
            // If not, save it from jar
            saveResource("background.png", false);
        }
        captchaHandler = new CaptchaHandler(this);
        fontHandler = new FontHandler(this);
        mapHandler = new MapHandlerFactory(this).getMapHandler();
        messageHandler = new MessageHandler(this);
        playerDataHandler = new PlayerDataHandler(this);
        safeArea = new SafeArea(this);
        loadListeners();

        new CaptchaCommand(this);

        Bukkit.getOnlinePlayers().forEach(playerDataHandler::loadPlayerDataFromPlayer);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        captchaHandler.removeAllCaptchas();
    }

    public void loadListeners() {
        new PlayerChatListener(this);
        new PlayerCommandPreprocessListener(this);
        new PlayerDropItemListener(this);
        new PlayerItemHeldListener(this);
        //if (Bukkit.getPluginManager().getPlugin("AuthMe") != null) new LoginListener(this);
        //else new PlayerJoinListener(this);
        new PlayerJoinListener(this);
        new PlayerSwapHandItemsListener(this);
    }

    public CaptchaHandler getCaptchaHandler() {
        return captchaHandler;
    }

    public FontHandler getFontHandler() {
        return fontHandler;
    }

    public MapHandler getMapHandler() {
        return mapHandler;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public PlayerDataHandler getPlayerDataHandler() {
        return playerDataHandler;
    }

    public SafeArea getSafeArea() {
        return safeArea;
    }

    public static Captcha startCaptcha(Player player) {
        return INSTANCE.captchaHandler.assignCaptcha(player);
    }

    public static boolean captchaInput(Player player, String input) {
        return INSTANCE.captchaHandler.captchaInput(player, input);
    }

    public static void closeCaptcha(Player player, SolveState solveState) {
        INSTANCE.captchaHandler.removeAssignedCaptcha(player, solveState);
    }

}
