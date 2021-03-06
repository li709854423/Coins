/**
 * This file is part of Coins
 *
 * Copyright (C) 2017 Beelzebu
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package net.nifheim.beelzebu.coins.bukkit.events;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * @author Beelzebu
 */
public class CoinsChangeEvent extends PlayerEvent {

    private final static HandlerList handlers = new HandlerList();
    private final double oldCoins;
    private final double newCoins;

    public CoinsChangeEvent(UUID uuid, double oc, double nc) {
        super(Bukkit.getPlayer(uuid));
        oldCoins = oc;
        newCoins = nc;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Get the coins that the player involved in this event had before this change.
     *
     * @return the old coins of the player.
     */
    public double getOldCoins() {
        return oldCoins;
    }

    /**
     * Get the balance of the player involved in this event after this change.
     *
     * @return the actual balance of the player.
     */
    public double getNewCoins() {
        return newCoins;
    }

    /**
     * Get the difference between the old coins and the new coins, this value can be possitive if we added coins to the
     * player or negative if we've taken coins from him.
     *
     * @return the difference in coins.
     */
    public double getAmount() {
        return oldCoins - newCoins;
    }
}
