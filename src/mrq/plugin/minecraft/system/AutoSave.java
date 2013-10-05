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
        pje.getPlayer().sendMessage(getColorNameV() + " 가 적용된 서버입니다!");
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
//                    server.broadcastMessage(getColorName() + " 서버를 자동으로 저장합니다...");
                    server.savePlayers();
                    for (World world: server.getWorlds()) {
                        for (Player player: world.getPlayers()) {
                            player.sendMessage(getColorName() + " 서버를 자동으로 저장합니다...");
                        }
                        world.save();
                        for (Player player: world.getPlayers()) {
                            player.sendMessage(getColorName() + " 서버의 저장이 완료되었습니다.");
                        }
                    }
//                    server.broadcastMessage(getColorName() + " 서버의 저장이 완료되었습니다.");
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