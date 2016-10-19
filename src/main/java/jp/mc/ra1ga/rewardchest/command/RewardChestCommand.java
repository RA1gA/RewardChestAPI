package jp.mc.ra1ga.rewardchest.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import jp.mc.ra1ga.rewardchest.Main;
import jp.mc.ra1ga.rewardchest.api.RewardChestAPI;
import jp.mc.ra1ga.rewardchest.config.RewardChest;

public class RewardChestCommand implements CommandExecutor, CommandPermission {
	public RewardChestCommand(Main plugin) {
		this.plugin = plugin;
	}

	private Main plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		String command = args[0];

		if(command.equalsIgnoreCase("set")){
			if(sender.hasPermission(getPermission(command))){
				if(args.length >= 6){
					try{
						String rewardName = args[1];
						int limitedDuration = Integer.parseInt(args[2]);
						int limitedDistance = Integer.parseInt(args[3]);
						int minAmount = Integer.parseInt(args[4]);
						int maxAmount = Integer.parseInt(args[5]);

						plugin.getConfig().set("Rewards." + rewardName + ".LimitedDuration", Integer.valueOf(limitedDuration));
						plugin.getConfig().set("Rewards." + rewardName + ".LimitedDistance", Integer.valueOf(limitedDistance));
						plugin.getConfig().set("Rewards." + rewardName + ".MinAmount", Integer.valueOf(minAmount));
						plugin.getConfig().set("Rewards." + rewardName + ".MaxAmount", Integer.valueOf(maxAmount));
						plugin.saveConfig();

						sender.sendMessage(ChatColor.GREEN + "RewardName:" + rewardName + ", LimitedDuration:" + limitedDuration + ", LimitedDistance" + limitedDistance + ", MinAmount" + minAmount + ", MaxAmount" + maxAmount);
						sender.sendMessage(ChatColor.GREEN + "上記の内容で正常に保存しました");
					}catch (NumberFormatException e){
						sender.sendMessage(ChatColor.RED + "数値が不正です");
					}
				}else{
					sender.sendMessage(ChatColor.RED + "/rw set <RewardName> <LimitedDuration> <LimitedDistance> <MinAmount> <MaxAmount>");
					sender.sendMessage(ChatColor.RED + "RewardをItems以外の情報と共に保存します");
				}
			}
		}else if(command.equalsIgnoreCase("add")){
			if(sender.hasPermission(getPermission(command))){
				if(args.length >= 3){
					String rewardName = args[1];
					List<String> adds = plugin.getConfig().getStringList("Rewards." + rewardName + ".Items") != null ? plugin.getConfig().getStringList("Rewards." + rewardName + ".Items") : new ArrayList();
					for (int x = 2; x < args.length; x++) {
						adds.add(args[x]);
					}
					plugin.getConfig().set("Rewards." + rewardName + ".Items", adds);
					plugin.saveConfig();
					sender.sendMessage(ChatColor.GREEN + adds.toString());
					sender.sendMessage(ChatColor.GREEN + "上記の項目を追加しました");
				}else{
					sender.sendMessage(ChatColor.RED + "/rw add <RewardName> <ItemNames>");
					sender.sendMessage(ChatColor.RED + "ItemNameはスペースを空けることで一度に複数追加することができます");
				}
			}
		}else if(command.equalsIgnoreCase("reset")){
			if(sender.hasPermission(getPermission(command))){
				if(args.length >= 2){
					String rewardName = args[1];

					plugin.getConfig().set("Rewards." + rewardName + ".Items", null);
					plugin.saveConfig();
					sender.sendMessage(ChatColor.GREEN + rewardName + "のItemsを削除しました");
				}else{
					sender.sendMessage(ChatColor.RED + "/rw reset <RewardName>");
					sender.sendMessage(ChatColor.RED + "全てのアイテムを削除します");
				}
			}
		}else if(command.equalsIgnoreCase("remove")){
			if(sender.hasPermission(getPermission(command))){
				if(args.length <= 2){
					String rewardName = args[1];
					plugin.getConfig().set("Rewards." + rewardName, null);
					plugin.saveConfig();
					sender.sendMessage(ChatColor.GREEN + rewardName + "を削除しました");
				}else{
					sender.sendMessage(ChatColor.RED + "/rw remove <RewardName>");
					sender.sendMessage(ChatColor.RED + "<RewardName>の全ての情報を削除します");
				}
			}
		}else if(command.equalsIgnoreCase("give")){
			if(sender.hasPermission(getPermission(command))){
				if(args.length >= 6){
					Player p = Bukkit.getPlayer(args[1]);
					if(p != null){
						try{
							String rewardName = args[2];
							if(plugin.getConfig().getConfigurationSection("Rewards") != null){
								if(plugin.getConfig().getConfigurationSection("Rewards").getKeys(false).contains(rewardName)){
									double x = Double.parseDouble(args[3]);
									double y = Double.parseDouble(args[4]);
									double z = Double.parseDouble(args[5]);

									RewardChest rc = new RewardChest(this.plugin, rewardName);
									RewardChestAPI.give(this.plugin, p, new Location(p.getWorld(), x, y, z), rc.getRewardName(), false, rc.getLimitedDuration(), rc.getLimitedDistance(), rc.getMinAmount(), rc.getMaxAmount(), rc.getItems());

									sender.sendMessage(ChatColor.GREEN + "プレイヤーにRewardChestを送りました");
								}else{
									sender.sendMessage(ChatColor.RED + rewardName + "が見つかりません");
								}
							}else{
								sender.sendMessage(ChatColor.RED + "パスが見つかりません");
							}
						}catch (NumberFormatException e){
							sender.sendMessage(ChatColor.RED + "数値が不正です");
						}
					}else{
						sender.sendMessage(ChatColor.RED + "プレイヤーが存在しません");
					}
				}else{
					sender.sendMessage(ChatColor.RED + "/rw give <PlayerName> <RewardName> x y z");
					sender.sendMessage(ChatColor.RED + "PlayerにRewardを与えます");
				}
			}
		}else if(command.equalsIgnoreCase("list")){
			if(sender.hasPermission(getPermission(command))){
				if(plugin.getConfig().getConfigurationSection("Rewards") != null){
					sender.sendMessage(ChatColor.GREEN + plugin.getConfig().getConfigurationSection("Rewards").getKeys(false).toString());
				}else{
					sender.sendMessage(ChatColor.RED + "パスが見つかりません");
				}
			}
		}else if(command.equalsIgnoreCase("reload")){
			if(sender.hasPermission(getPermission(command))){
				plugin.reloadConfig();
				sender.sendMessage(ChatColor.GREEN + "リロードしました。");
			}
		}else if(command.equalsIgnoreCase("help")){
			sender.sendMessage(ChatColor.GREEN + "/rw set <RewardName> <LimitedDuration> <LimitedDistance> <MinAmount> <MaxAmount>");
			sender.sendMessage(ChatColor.YELLOW + "RewardをItems以外の情報と共に保存します");
			sender.sendMessage(ChatColor.GREEN + "/rw add <RewardName> <ItemNames>");
			sender.sendMessage(ChatColor.YELLOW + "ItemNameはスペースを空けることで一度に複数追加することができます");
			sender.sendMessage(ChatColor.GREEN + "/rw reset <RewardName>");
			sender.sendMessage(ChatColor.YELLOW + "全てのアイテムを削除します");
			sender.sendMessage(ChatColor.GREEN + "/rw remove <RewardName>");
			sender.sendMessage(ChatColor.YELLOW + "<RewardName>の全ての情報を削除します");
			sender.sendMessage(ChatColor.GREEN + "/rw give <PlayerName> <RewardName> x y z");
			sender.sendMessage(ChatColor.YELLOW + "PlayerにRewardを与えます");
			sender.sendMessage(ChatColor.GREEN + "/rw list");
			sender.sendMessage(ChatColor.YELLOW + "Rewardのリストを表示します");
			sender.sendMessage(ChatColor.GREEN + "/rw reload");
			sender.sendMessage(ChatColor.YELLOW + "コンフィグをリロードします");
		}

		return false;
	}

	@Override
	public String getPermission(String command){
		return "reward.command.reward." + command.toLowerCase();
	}

}
