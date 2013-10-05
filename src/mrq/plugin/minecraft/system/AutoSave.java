package mrq.plugin.minecraft.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class AutoSave extends JavaPlugin implements Listener {
    private boolean run = false;
    
    @Override
    public void onEnable() {
        System.out.println("[" + getDescription().getName() + "] is enabled!");
        
        run = true;
        getServer().getScheduler().scheduleSyncDelayedTask(this, new SaveSchedule());
    }
    @Override
    public void onDisable() {
        System.out.println("[" + getDescription().getName() + "] is disabled!");
        
        run = false;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent pje) {
        pje.getPlayer().sendMessage(getColorNameV() + " �� ����� �����Դϴ�!");
    }
    
    private class SaveSchedule implements Runnable {
        private BukkitScheduler bs = null;
        private SimpleDateFormat sdf = null;
        private int beforeHour = 0;
        
        private SaveSchedule() {
            bs = getServer().getScheduler();
            sdf = new SimpleDateFormat("HH", Locale.KOREA);
            try {
                beforeHour = Integer.parseInt(sdf.format(new Date()));
            } catch (NumberFormatException e) {
                beforeHour = 0;
            }
        }
        
        @Override
        public void run() {
            if (run) {
                int hour = Integer.parseInt(sdf.format(new Date()));
                if (beforeHour != hour) {
                    beforeHour = hour;
                    
                    Server server = getServer();
//                    server.broadcastMessage(getColorName() + " ������ �ڵ����� �����մϴ�...");
                    server.savePlayers();
                    for (World world: server.getWorlds()) {
                        for (Player player: world.getPlayers()) {
                            player.sendMessage(getColorName() + " ������ �ڵ����� �����մϴ�...");
                        }
                        world.save();
                        for (Player player: world.getPlayers()) {
                            player.sendMessage(getColorName() + " ������ ������ �Ϸ�Ǿ����ϴ�.");
                        }
                    }
//                    server.broadcastMessage(getColorName() + " ������ ������ �Ϸ�Ǿ����ϴ�.");
                }
                bs.scheduleSyncDelayedTask(AutoSave.this, this, 100);
            }
        }
    }
    
    private String getColorName() {
        return ChatColor.WHITE + "[" + ChatColor.GRAY + getDescription().getName() + ChatColor.WHITE + "]";
    }
    private String getColorNameV() {
        return ChatColor.WHITE + "[" + ChatColor.GRAY + getDescription().getName() + ChatColor.WHITE + " v" + getDescription().getVersion() + "]";
    }
}