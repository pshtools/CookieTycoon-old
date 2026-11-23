package com.tools.cookieTycoon.utils;

import com.tools.cookieTycoon.CookieTycoon;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ActionBar {
    private final CookieTycoon plugin;
    private final Map<UUID, BukkitTask> updateTasks = new HashMap<>();
    
    public ActionBar(CookieTycoon plugin) {
        this.plugin = plugin;
    }

public void start(Player player) {
    stop(player);

    BukkitTask task = new BukkitRunnable() {
        @Override
        public void run() {
            if (!player.isOnline()) {
                stop(player);
                return;
            }

            update(player);
        }
    }.runTaskTimer(plugin, 0L, 5L);

    updateTasks.put(player.getUniqueId(), task);
}

public void stop(Player player) {
    BukkitTask task = updateTasks.remove(player.getUniqueId());

    if (task != null) {
        task.cancel();
    }
}
public void update(Player p) {
    send(p, ChatColor.GOLD + ChatColor.BOLD.toString()
        + NumberFormatter.format(plugin.getCookieManager().get(p))
        + " ⃝");
}

    private void clear(Player p) {
        send(p, "");
    }

    private void send(Player p, String msg) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
    }

}
