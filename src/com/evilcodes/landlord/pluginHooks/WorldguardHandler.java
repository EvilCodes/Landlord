package com.evilcodes.landlord.pluginHooks;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.sk89q.worldguard.bukkit.BukkitUtil.toVector;

/**
 * File created by jcdesimp on 3/15/14.
 * File edited by EvilCodes on 3/29/16.
 * Class for handling worldguard interactions
 */
public class WorldGuardHandler {

    private WorldGuardPlugin worldguard;

    public WorldGuardHandler(WorldGuardPlugin worldguard) {
        this.worldguard = worldguard;
    }

    /**
     * Determines if a player is allowed to claim or not by
     * checking region intersections with their current chunk
     *
     * @param player    trying to claim
     * @param currentChunk that is being claimed
     * @return boolean of allowed or not
     */
    public boolean canClaim(final Player player, final Chunk currentChunk) {
        final RegionManager regionManager = worldguard.getRegionManager(player.getWorld());
        // Check if we have a region manager for this world
        if (regionManager != null) {
            final ProtectedRegion regionToCheck = new ProtectedCuboidRegion("check", toVector(currentChunk.getBlock(0, 0, 0)), toVector(currentChunk.getBlock(15, 255, 15)));

            final List<ProtectedRegion> intersectedRegions = regionToCheck.getIntersectingRegions(new ArrayList<ProtectedRegion>(regionManager.getRegions().values()));
            for (final ProtectedRegion intersectedRegion : intersectedRegions) {
                //todo confront deprecation
                if (!regionManager.getApplicableRegions(intersectedRegion).canBuild(worldguard.wrapPlayer(player))) {
                    return false;
                }
            }
        }
        // Return true if the for loop did not return a false
        return true;
    }
}
