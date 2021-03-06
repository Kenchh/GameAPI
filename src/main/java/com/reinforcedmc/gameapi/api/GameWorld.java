package com.reinforcedmc.gameapi.api;

import com.reinforcedmc.gameapi.GameAPI;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameWorld {

    public World world;
    public Location spawn;

    private int chunkPreLoadRange = 8;

    public ArrayList<Location> tpLocations = new ArrayList<>();

    public GameWorld(String worldname, long maxRadius, boolean generateNether, boolean generateEnd) {

        GameAPI.getInstance().currentGame.worldname = worldname;
        System.out.println(GameAPI.getInstance().currentGame.getName() + " > Generating world " + worldname + "..");

        if(Bukkit.getWorld(worldname) != null) {
            Bukkit.unloadWorld(worldname, false);
        }
        File folder = new File(Bukkit.getWorldContainer() + "/" + worldname);
        try {
            FileUtils.deleteDirectory(folder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        WorldCreator creator = new WorldCreator(worldname);
        creator.environment(World.Environment.NORMAL);
        creator.generateStructures(true);
        world = creator.createWorld();

        spawn = new Location(world, 0, 0, 0);

        for (int i = 0; i<GameAPI.getInstance().currentGame.getMaxPlayers(); i++) {

            boolean notocean = false;

            Location location = Bukkit.getWorld(worldname).getSpawnLocation();

            while(!notocean) {
                location = new Location(world, 0, 0, 0); // New Location in the right World you want
                location.setX(spawn.getX() + Math.random() * maxRadius * 2 - maxRadius); // This get a Random with a MaxRange
                location.setZ(spawn.getZ() + Math.random() * maxRadius * 2 - maxRadius);

                Block highest = world.getHighestBlockAt(location.getBlockX(), location.getBlockZ());

                if(highest.isLiquid()) {
                    maxRadius += 100;
                    continue;
                }

                notocean = true;
                location.setY(highest.getY() + 1); // Get the Highest Block of the Location for Save Spawn.
            }

            int chunkcount = 0;
            for(int cx=-(chunkPreLoadRange/2); cx<chunkPreLoadRange/2; cx++) {
                for(int cz=-(chunkPreLoadRange/2); cz<chunkPreLoadRange/2; cz++) {
                    chunkcount++;
                    System.out.println(GameAPI.getInstance().currentGame.getName() + " > Preloading chunk " + chunkcount + "/" + (chunkPreLoadRange*chunkPreLoadRange) + " for location " + (i+1) + "/" + GameAPI.getInstance().currentGame.getMaxPlayers());
                    Location loc = new Location(location.getWorld(), location.getX() + cx * 16, location.getY(), location.getZ() + cz * 16);
                    Chunk chunk = world.getChunkAt(loc);
                    chunk.load();
                }
            }

            tpLocations.add(location);
        }

        if(generateNether)
            generateNether(worldname);

        if(generateEnd)
            generateEnd(worldname);

    }

    private void generateNether(String worldname) {
        System.out.println(GameAPI.getInstance().currentGame.getName() + " > Generating Nether..");

        if(Bukkit.getWorld(worldname + "_nether") != null) {
            Bukkit.unloadWorld(worldname + "_nether", false);
        }
        File folder = new File(Bukkit.getWorldContainer() + "/" + worldname + "_nether");
        try {
            FileUtils.deleteDirectory(folder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        WorldCreator creator = new WorldCreator(worldname + "_nether");
        creator.environment(World.Environment.NETHER);
        creator.generateStructures(true);
        world = creator.createWorld();
    }

    private void generateEnd(String worldname) {
        System.out.println(GameAPI.getInstance().currentGame.getName() + " > Generating End..");

        if(Bukkit.getWorld(worldname + "_the_end") != null) {
            Bukkit.unloadWorld(worldname + "_the_end", false);
        }
        File folder = new File(Bukkit.getWorldContainer() + "/" + worldname + "_the_end");
        try {
            FileUtils.deleteDirectory(folder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        WorldCreator creator = new WorldCreator(worldname + "_the_end");
        creator.environment(World.Environment.THE_END);
        creator.generateStructures(true);
        world = creator.createWorld();
    }

    public void teleportPlayers() {
        for(int i=0;i<tpLocations.size();i++) {

            if(i+1 > GameAPI.getInstance().ingame.size()) break;

            Player p = Bukkit.getPlayer(GameAPI.getInstance().ingame.get(i));
            p.teleport(tpLocations.get(i));
        }
    }

}
