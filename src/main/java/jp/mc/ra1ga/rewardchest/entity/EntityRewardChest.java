package jp.mc.ra1ga.rewardchest.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EntityRewardChest {
	public EntityRewardChest(String rewardName, Player player, int minAmount, int maxAmount, List<ItemStack> items) {
		this.rewardName = rewardName;
		this.player = player;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.items = items;

		this.chestStand = new ChestStand();
		this.inventory = setupInventory();

		ercs.add(this);
	}

	private static List<EntityRewardChest> ercs = new ArrayList<>();
	private String rewardName;
	private Player player;
	private int minAmount;
	private int maxAmount;
	private List<ItemStack> items;
	private ChestStand chestStand;
	private Inventory inventory;

	public static List<EntityRewardChest> getEntityRewardChests(){
		return ercs;
	}

	public Player getPlayer(){
		return player;
	}

	public ChestStand getChestStand(){
		return chestStand;
	}

	public Inventory getInventory(){
		return inventory;
	}

	private Inventory setupInventory(){

		int slot = 9 * 6;
		for(int x = 9; x < slot; x += 9){
			if(items.size() <= x){
				slot = x;
				break;
			}
		}

		Inventory inv = Bukkit.createInventory(null, slot, rewardName);
		if(minAmount <= maxAmount){
			Random random = new Random();
			Collections.shuffle(items);
			if(maxAmount < items.size()){
				int dif = maxAmount - minAmount;
				int rnd = random.nextInt(dif + 1) + minAmount;
				for(int x = 0; x < rnd; x++){
					inv.setItem(x, items.get(x));
				}
			}else if(minAmount >= items.size()){
				if(items.size() > 0){
					for(int x = 0; x < items.size(); x++){
						inv.setItem(x, items.get(x));
					}
				}
			}else{
				int dif = items.size() - minAmount;
				int rnd = random.nextInt(dif + 1) + minAmount;
				for(int x = 0; x < rnd; x++){
					inv.setItem(x, items.get(x));
				}
			}
		}

		return inv;

	}

}
