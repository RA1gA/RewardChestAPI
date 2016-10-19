package jp.mc.ra1ga.rewardchest.api;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import jp.mc.ra1ga.rewardchest.entity.EntityRewardChest;

public class RewardChestAPI {

	public static Entity give(JavaPlugin plugin, Player player, Location location, String rewardName, boolean useDisplay, int limitedDuration, int limitedDistance, int minAmount, int maxAmount, List<ItemStack> items){
		EntityRewardChest erc = new EntityRewardChest(rewardName, player, minAmount, maxAmount, items);
		ArmorStand as = useDisplay ? erc.getChestStand().spawn(location, rewardName) : erc.getChestStand().spawn(location);
		leaveChest(plugin, limitedDistance, as, player);
		countdown(plugin, limitedDuration, as);
		rotate(plugin, as);
		return as;
	}

	private static void leaveChest(JavaPlugin plugin, final int limitedDistance, final ArmorStand as, final Player player){
		new BukkitRunnable() {
			@Override
			public void run() {
				if(as!=null && !as.isDead()){
					if(as.getLocation().distance(player.getLocation()) >= limitedDistance){
						as.remove();
					}
				}else{
					cancel();
				}
			}
		}.runTaskTimer(plugin, 0, 0);
	}
	private static void countdown(JavaPlugin plugin, final int limitedDuration, final ArmorStand as){
		new BukkitRunnable() {
			@Override
			public void run() {
				if(as!=null && !as.isDead()){
					as.remove();
				}
			}
		}.runTaskLater(plugin, limitedDuration);
	}
	private static void rotate(JavaPlugin plugin, final ArmorStand as){
		new BukkitRunnable() {
			double y = 0;
			@Override
			public void run() {
				if(as!=null && !as.isDead()){
					as.setHeadPose(new EulerAngle(0, y, 0));
					y += 0.03;
				}else{
					cancel();
				}
			}
		}.runTaskTimer(plugin, 0, 0);
	}

}
