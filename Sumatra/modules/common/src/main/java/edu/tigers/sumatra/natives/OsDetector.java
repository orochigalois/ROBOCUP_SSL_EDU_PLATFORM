/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.natives;


public final class OsDetector
{
	// --------------------------------------------------------------------------
	// --- classes --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public enum EOsName
	{
		
		WINDOWS,
		
		MAC,
		
		UNIX,
		
		SOLARIS,
		
		UNKNOWN;
	}
	
	
	public enum EOsArch
	{
		
		x86,
		
		x64,
		
		SPARC,
		
		PPC,
		
		UNKNOWN;
	}
	
	
	private OsDetector()
	{
		
	}
	
	
	public static class OsIdentifier
	{
		private final EOsName	name;
		private final EOsArch	arch;
		
		
		
		public OsIdentifier(final EOsName name, final EOsArch arch)
		{
			super();
			this.name = name;
			this.arch = arch;
		}
		
		
		
		public EOsName getName()
		{
			return name;
		}
		
		
		
		public EOsArch getArch()
		{
			return arch;
		}
		
		
		@Override
		public String toString()
		{
			return name.name() + "/" + arch.name();
		}
		
		
		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = (prime * result) + ((arch == null) ? 0 : arch.hashCode());
			result = (prime * result) + ((name == null) ? 0 : name.hashCode());
			return result;
		}
		
		
		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			if (obj == null)
			{
				return false;
			}
			if (getClass() != obj.getClass())
			{
				return false;
			}
			final OsIdentifier other = (OsIdentifier) obj;
			if (arch != other.arch)
			{
				return false;
			}
			if (name != other.name)
			{
				return false;
			}
			return true;
		}
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public static OsIdentifier detectOs()
	{
		return new OsIdentifier(getOsName(), getOsArch());
	}
	
	
	
	public static EOsName getOsName()
	{
		final EOsName detName;
		
		if (isWindows())
		{
			detName = EOsName.WINDOWS;
		} else if (isMac())
		{
			detName = EOsName.MAC;
		} else if (isUnix())
		{
			detName = EOsName.UNIX;
		} else if (isSolaris())
		{
			detName = EOsName.SOLARIS;
		} else
		{
			detName = EOsName.UNKNOWN;
		}
		
		return detName;
	}
	
	
	
	public static EOsArch getOsArch()
	{
		final String arch = System.getProperty("os.arch").toLowerCase();
		
		final EOsArch detArch;
		
		if (arch.indexOf("64") >= 0)
		{
			detArch = EOsArch.x64;
		} else if (arch.indexOf("86") >= 0)
		{
			detArch = EOsArch.x86;
		} else if (arch.indexOf("ppc") >= 0)
		{
			detArch = EOsArch.PPC;
		} else if (arch.indexOf("sparc") >= 0)
		{
			detArch = EOsArch.SPARC;
		} else
		{
			detArch = EOsArch.UNKNOWN;
		}
		
		return detArch;
	}
	
	
	
	public static boolean isWindows()
	{
		final String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("win") >= 0);
	}
	
	
	
	public static boolean isMac()
	{
		final String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("mac") >= 0);
	}
	
	
	
	public static boolean isUnix()
	{
		final String os = System.getProperty("os.name").toLowerCase();
		return ((os.indexOf("nix") >= 0) || (os.indexOf("nux") >= 0));
	}
	
	
	
	public static boolean isSolaris()
	{
		final String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("sunos") >= 0);
	}
}
