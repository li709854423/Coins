/**
 * This file is part of Coins
 *
 * Copyright (C) 2017 Beelzebu
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.nifheim.beelzebu.coins.bukkit.utils;

import java.util.ArrayList;
import java.util.List;
import net.nifheim.beelzebu.coins.CoinsAPI;
import net.nifheim.beelzebu.coins.bukkit.utils.gui.BaseGUI;
import net.nifheim.beelzebu.coins.core.Core;
import net.nifheim.beelzebu.coins.core.multiplier.MultiplierData;
import net.nifheim.beelzebu.coins.core.multiplier.MultiplierType;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Beelzebu
 */
public class ConfirmGUI extends BaseGUI {

    private final Core core = Core.getInstance();
    private final MultiplierData multiplierData;
    private final Player p;

    public ConfirmGUI(Player player, String name, MultiplierData data) {
        super(9, name);
        p = player;
        multiplierData = data;
        setItems();
    }

    private void setItems() {
        if (p == null) {
            return;
        }
        {
            ItemStack is = new ItemStack(Material.STAINED_GLASS, 1, (short) 5);
            ItemMeta meta = is.getItemMeta();
            meta.setDisplayName(core.getString("Multipliers.Menu.Confirm.Accept", p.spigot().getLocale()));
            is.setItemMeta(meta);
            setItem(2, is, player -> {
                if (CoinsAPI.getMultiplier(multiplierData.getServer()).useMultiplier(multiplierData.getID(), MultiplierType.SERVER)) {
                    player.playSound(player.getLocation(), Sound.valueOf(core.getConfig().getString("Multipliers.GUI.Use.Sound", "PLAYER_LEVELUP")), 10, 2);
                } else {
                    player.playSound(player.getLocation(), Sound.valueOf(core.getConfig().getString("Multipliers.GUI.Use.Fail.Sound", "VILLAGER_NO")), 10, 1);
                    player.sendMessage(core.getString("Multipliers.Already active", player.spigot().getLocale()));
                }
                player.closeInventory();
            });
        }
        {
            ItemStack is = new ItemStack(Material.POTION);
            PotionMeta meta = (PotionMeta) is.getItemMeta();
            meta.setMainEffect(PotionEffectType.FIRE_RESISTANCE);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            meta.setDisplayName(rep(core.getString("Multipliers.Menu.Multipliers.Name", p.spigot().getLocale()), multiplierData));
            List<String> lore = new ArrayList<>();
            core.getMessages(p.spigot().getLocale()).getStringList("Multipliers.Menu.Multipliers.Lore").forEach(line -> {
                lore.add(rep(line, multiplierData));
            });
            meta.setLore(lore);
            is.setItemMeta(meta);
            setItem(4, is);
        }
        {
            ItemStack is = new ItemStack(Material.STAINED_GLASS, 1, (short) 14);
            ItemMeta meta = is.getItemMeta();
            meta.setDisplayName(core.getString("Multipliers.Menu.Confirm.Decline", p.spigot().getLocale()));
            is.setItemMeta(meta);
            setItem(6, is, player -> {
                player.playSound(player.getLocation(), Sound.valueOf(core.getConfig().getString("Multipliers.GUI.Use.Fail.Sound", "VILLAGER_NO")), 10, 1);
                player.closeInventory();
            });
        }
    }

    private String rep(String str, MultiplierData data) {
        return core.rep(
                str
                        .replaceAll("%amount%", String.valueOf(data.getAmount()))
                        .replaceAll("%server%", data.getServer())
                        .replaceAll("%minutes%", String.valueOf(data.getMinutes()))
                        .replaceAll("%id%", String.valueOf(data.getID()))
        );
    }
}
