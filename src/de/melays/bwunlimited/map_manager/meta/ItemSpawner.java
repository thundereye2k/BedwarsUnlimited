/*******************************************************************************
 * Copyright (C) Philipp Seelos - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Philipp Seelos <seelos@outlook.com>, December 2017
 ******************************************************************************/
package de.melays.bwunlimited.map_manager.meta;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import de.melays.bwunlimited.Main;
import de.melays.bwunlimited.map_manager.FineRelativeLocation;

public class ItemSpawner {
	
	public int id;
	public FineRelativeLocation loc;
	public Material m;
	public int ticks;
	public String displayname;
	public ArrayList<String> disabled_categories;
	
	Main main;
	
	public ItemSpawner(Main main , int id , FineRelativeLocation loc , Material m , int ticks , String displayname , ArrayList<String> disabled_categories) {
		this.id = id;
		this.loc = loc;
		this.m = m;
		this.ticks = ticks;
		this.displayname = displayname;
		this.main = main;
		this.disabled_categories = disabled_categories;
	}
	
	int scheudler = -1;
	
	public void startGenerating(Location relative) {
		scheudler = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {

			@Override
			public void run() {
				ItemStack drop = new ItemStack(m);
				ItemMeta meta = drop.getItemMeta();
				meta.setDisplayName(main.c(displayname));
				drop.setItemMeta(meta);
				relative.getWorld().dropItem(loc.toLocation(relative), drop).setVelocity(new Vector(0, 0.25 , 0));
			}
			
		}, ticks, ticks);
	}
	
	public void stop() {
		if (scheudler != -1) {
			Bukkit.getScheduler().cancelTask(scheudler);
		}
	}
	
}
