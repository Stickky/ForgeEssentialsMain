package com.ForgeEssentials.permission;

import com.ForgeEssentials.util.FEChatFormatCodes;

/**
 * RegistrationGroups
 * 
 * @author AbrarSyed
 * 
 */
public enum RegGroup
{
	/**
	 * This is automatically assigned to the server owner when they make a world available to the LAN. This is also best kept for players that have direct
	 * access to the server's console and filesystem.
	 */
	OWNERS("Owners", "ZoneAdmins", FEChatFormatCodes.RED + "[OWNER]", " ", 999),

	/**
	 * This is usually for players that are admins or owners of a given zone They will most likely have WorldEdit access, as well as the power to edit
	 * permissions in the zone.
	 */
	ZONE_ADMINS("ZoneAdmins", "Members", FEChatFormatCodes.RED + "[ZoneAdmin]", " ", 0),

	/**
	 * This is usually for players that are actually members of the server. They will most likely be able to use basic commands as well as break blocks and
	 * stuff in the world.
	 */
	MEMBERS("Members", "Guests", " ", " ", 0),

	/**
	 * This is the group, b default, that all players are assigned to when they first log in. The players in this group are usually denied commands and breaking
	 * blocks before they are promoted to members.
	 */
	GUESTS("Guests", null, FEChatFormatCodes.GREY + "[GUEST]", " ", 0),

	/**
	 * Used for blankets permissions tied to no particular player or group in a zone. All players are part of this group This group is guaranteed existence
	 */
	ZONE("_DEFAULT_", null, null, null, 0)
	{
		@Override
		public Group getGroup()
		{
			return PermissionsAPI.DEFAULT;
		}
	};

	private RegGroup(String name, String parent, String prefix, String suffix, int priority)
	{
		this.name = name;
		group = new Group(name, parent, prefix, suffix, ZoneManager.GLOBAL.getZoneID(), priority);
	}

	@Override
	public String toString()
	{
		return name;
	}

	public Group getGroup()
	{
		return group;
	}

	private Group group;
	private String name;
}
