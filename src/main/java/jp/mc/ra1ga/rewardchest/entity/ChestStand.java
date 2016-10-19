package jp.mc.ra1ga.rewardchest.entity;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class ChestStand {
	public ChestStand() {
		this.stands = new ArrayList<>();
	}

	private List<ArmorStand> stands;

	public List<ArmorStand> getStands(){
		List<ArmorStand> rm = new ArrayList<>();
		for(ArmorStand as : stands){
			if(as == null || as.isDead()){
				rm.add(as);
			}
		}
		stands.removeAll(rm);
		return stands;
	}

	public ArmorStand spawn(Location loc){
		return spawn(loc, null);
	}
	public ArmorStand spawn(Location loc, String name){
		ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(new Location(loc.getWorld(), loc.getX(), loc.getY() - 0.5, loc.getZ()), EntityType.ARMOR_STAND);
		as.setBasePlate(false);
		as.setGravity(false);
		as.setSilent(true);
		as.setInvulnerable(true);
		as.setSmall(true);
		as.setVisible(false);
		as.setCanPickupItems(false);
		as.setHeadPose(new EulerAngle(0, 0, 0));
		as.setHelmet(new ItemStack(Material.CHEST));
		if(name != null){
			as.setCustomName(name);
			as.setCustomNameVisible(true);
		}
		stands.add(as);
		return as;
	}

}
