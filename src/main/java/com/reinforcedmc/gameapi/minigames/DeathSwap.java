package com.reinforcedmc.gameapi.minigames;

import com.reinforcedmc.gameapi.game.Game;
import com.reinforcedmc.gameapi.game.GameFlags;
import com.reinforcedmc.gameapi.game.GameType;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;

import java.util.ArrayList;
import java.util.Arrays;

public class DeathSwap extends Game {

    public DeathSwap() {
        super("DeathSwap", ChatColor.GOLD + "" + ChatColor.BOLD + "Death" + ChatColor.YELLOW + ChatColor.BOLD + "Swap", Arrays.asList(
                "Compete against other players by",
                "setting up big brain traps before it",
                "too late!"
        ), Arrays.asList(GameType.CLASSIC), new GameFlags(), 2, 8, new String[]{
                "&6&lDeath&e&lSwap",
                "&6&lDeath&e&lSwap",
                "&6&lDeath&e&lSwap",
                "&6&lDeath&e&lSwap",
                "&6&lDeath&e&lSwap",
                "&6&lDeath&e&lSwap",
                "&6&lDeath&e&lSwap",
                "&6&lDeath&e&lSwap",
                "&6&lDeath&e&lSwap",
                "&e&lSwap&6&lDeath",
                "&e&lSwap&6&lDeath",
                "&e&lSwap&6&lDeath",
                "&e&lSwap&6&lDeath",
                "&e&lSwap&6&lDeath",
                "&e&lSwap&6&lDeath",
                "&e&lSwap&6&lDeath",
                "&e&lSwap&6&lDeath",
                "&e&lSwap&6&lDeath"
                });
    }

}
