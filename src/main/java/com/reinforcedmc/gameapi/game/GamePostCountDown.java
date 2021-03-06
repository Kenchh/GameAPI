package com.reinforcedmc.gameapi.game;

import com.reinforcedmc.gameapi.GameAPI;
import com.reinforcedmc.gameapi.events.api.GameStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class GamePostCountDown extends BukkitRunnable {

    int cooldown = 10;
    int currentCD = cooldown;

    public void start() {
        GameAPI.getInstance().preCountDown = null;
        GameAPI.getInstance().status = GameStatus.POSTCOUNTDOWN;
        GameAPI.getInstance().getJedisManager().updateGameServer();

        this.runTaskTimer(GameAPI.getInstance(), 0, 20);
    }

    @Override
    public void run() {

        if(currentCD > 0) {

            if(currentCD <= 3) {
                Bukkit.getOnlinePlayers().forEach((p) -> p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1F));
            }

            Bukkit.getOnlinePlayers().forEach((p) -> p.setLevel(currentCD));
            currentCD--;
        } else {
            Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent(GameAPI.getInstance().currentGame));
            this.cancel();
        }

    }

}
