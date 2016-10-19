package jp.mc.ra1ga.rewardchest.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import jp.mc.ra1ga.rewardchest.Main;
import net.minecraft.server.v1_10_R1.Material;

public class RewardItemCommand implements CommandExecutor, CommandPermission {
	public RewardItemCommand(Main plugin) {
		this.plugin = plugin;
	}

	private Main plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		String command = args[0];

		if(command.equalsIgnoreCase("set")){
			if(sender.hasPermission(getPermission(command))){
				if(args.length >= 2){
					if(sender instanceof Player){
						Player p = (Player)sender;
						ItemStack set = p.getInventory().getItemInMainHand();
						String itemName = args[1];
						if(set != null && !set.getType().equals(Material.AIR)){
							plugin.getConfig().set("Items." + itemName, set);
							plugin.saveConfig();
							sender.sendMessage(ChatColor.GREEN + itemName + "を保存しました");
						}else{
							sender.sendMessage(ChatColor.RED + "手に持っているアイテムを保存します");
						}
					}else{
						sender.sendMessage(ChatColor.RED + "このコマンドはプレイヤーが持っているアイテムを参照します");
					}
				}else{
					sender.sendMessage(ChatColor.RED + "/ri set <ItemName>");
					sender.sendMessage(ChatColor.RED + "持っているアイテムをRewardChest用に名前を付けて保存します");
				}
			}
		}else if(command.equalsIgnoreCase("remove")){
			if(sender.hasPermission(getPermission(command))){
				if(args.length >= 2){
					String itemName = args[1];
					plugin.getConfig().set("Items." + itemName, null);
					plugin.saveConfig();
					sender.sendMessage(ChatColor.GREEN + itemName + "を削除しました");
				}else{
					sender.sendMessage(ChatColor.RED + "/ri remove <ItemName>");
					sender.sendMessage(ChatColor.RED + "RewardChest用の保存されているアイテムを削除します");
				}
			}
		}else if(command.equalsIgnoreCase("list")){
			if(sender.hasPermission(getPermission(command))){
				if(plugin.getConfig().getConfigurationSection("Items") != null){
					sender.sendMessage(ChatColor.GREEN + this.plugin.getConfig().getConfigurationSection("Items").getKeys(false).toString());
				}else{
					sender.sendMessage(ChatColor.RED + "パスが見つかりません");
				}
			}
		}else if(command.equalsIgnoreCase("help")){
			sender.sendMessage(ChatColor.GREEN + "/ri set <ItemName>");
			sender.sendMessage(ChatColor.YELLOW + "持っているアイテムをRewardChest用に名前を付けて保存します");
			sender.sendMessage(ChatColor.GREEN + "/ri remove <ItemName>");
			sender.sendMessage(ChatColor.YELLOW + "RewardChest用の保存されているアイテムを削除します");
			sender.sendMessage(ChatColor.GREEN + "/ri list");
			sender.sendMessage(ChatColor.YELLOW + "保存したアイテムリストを表示します");
		}

		return false;
	}

	@Override
	public String getPermission(String command) {
		return "reward.command.rewarditem." + command.toLowerCase();
	}

}
