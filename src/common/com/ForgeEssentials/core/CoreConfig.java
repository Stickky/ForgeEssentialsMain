package com.ForgeEssentials.core;

import java.io.File;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import com.ForgeEssentials.commands.CommandBackup;
import com.ForgeEssentials.commands.CommandMotd;
import com.ForgeEssentials.commands.CommandRules;
import com.ForgeEssentials.permissions.ModulePermissions;
import com.ForgeEssentials.playerLogger.ModulePlayerLogger;
import com.ForgeEssentials.util.OutputHandler;

public class CoreConfig
{
	public static final File	mainconfig	= new File(ForgeEssentials.FEDIR, "core.cfg");

	public final Configuration	config;

	// this is designed so it will work for any class.
	public CoreConfig()
	{
		OutputHandler.SOP("Loading configs");
		config = new Configuration(mainconfig, true);
		// config.load -- Configurations are loaded on Construction.

		// load the modules
		loadModules();
		loadCore();
		loadPerms();

		// Finish init and save.
		config.save();
	}

	private void loadModules()
	{
		config.addCustomCategoryComment("Modules", "Toggles Forge Essentials modules on or off. Set to true to turn on, false to turn off.");

		Property prop = config.get("Modules", "Commands_Enabled", true);
		prop.comment = "Disabling this will remove non-essential commands. ie: /home, /motd, /rules, etc...";
		ModuleLauncher.cmdEnabled = prop.getBoolean(true);

		prop = config.get("Modules", "WorldControl_Enabled", true);
		prop.comment = "Disabling this will remove Selections and selection editing commands such as //set, //copy, etc... Note that this is force disabled if WEIntegration is loaded.";
		ModuleLauncher.wcEnabled = prop.getBoolean(true);

		prop = config.get("Modules", "Permissions_Enabled", true);
		prop.comment = "Disabling this will remove any and all permissions integration. Other mods that use this may be affected.";
		ModuleLauncher.permsEnabled = prop.getBoolean(true);
		
		prop = config.get("Modules", "Property_Enabled", true);
		prop.comment = "Disabling this will remove Properties.";
		ModuleLauncher.propEnabled = prop.getBoolean(true);
		
		prop = config.get("Modules", "WorldBorder_Enabled", false);
		prop.comment = "Disabling this will remove Any WorldBorder setup.";
		ModuleLauncher.borderEnabled = prop.getBoolean(false);
		
		prop = config.get("Modules", "PlayerLogger_Enabled", false);
		prop.comment = "Enabling this will enable the logger. Make sure to check the settings!";
		ModuleLauncher.loggerEnabled = prop.getBoolean(false);
		
		prop = config.get("Modules", "WEIntegration_Enabled", false);
		prop.comment = "Enabling this will enable WorldEdit/WorldGuard compatibility. Does nothing at the moment.";
		ModuleLauncher.skEnabled = prop.getBoolean(false);
	}

	private void loadCore()
	{
		config.addCustomCategoryComment("Core", "Configure ForgeEssentials Core.");

		Property prop = config.get("Core", "versionCheck", true);
		prop.comment = "Check for newer versions of ForgeEssentials on load?";
		ForgeEssentials.verCheck = prop.getBoolean(true);
		
		prop = config.get("Core", "modlistLocation", "modlist.txt");
		prop.comment = "Specify the file where the modlist will be written to. This path is relative to the ForgeEssentials folder.";
		ForgeEssentials.modlistLocation = prop.value;

	}

	private void loadPerms()
	{
		config.addCustomCategoryComment("Permissions", "Configure ForgeEssentials Permissions. Only implemented if Permissions module is enabled.");

		Property prop = config.get("Permissions", "verbose", false);
		prop.comment = "Specify if Verbose mode for Permissions module is enabled. If enabled, every permission registered is printed to the console. Only useful in debugging.";
		ModulePermissions.permsVerbose = prop.getBoolean(false);
	}

	/**
	 * will overwrite the current physical file.
	 */
	public void forceSave()
	{
		config.save();
	}

	/**
	 * @param name
	 * : ie WorldControl, Commands, Permissions, WorldEditCompat, WorldGuardCompat, etc... whatever comes after Module
	 * @return boolean
	 */
	public boolean isModuleEnabled(String name)
	{
		Property prop = config.get("Modules", name + " Enabled", true);
		return prop.getBoolean(true);
	}

	public void changeProperty(String category, String property, String newValue)
	{
		Property prop = config.get(category, property, newValue);
		String oldVal = prop.value;
		prop.value = newValue;

		OutputHandler.logConfigChange(category, property, oldVal, newValue);
	}
}
