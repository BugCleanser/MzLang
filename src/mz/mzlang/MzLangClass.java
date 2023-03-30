package mz.mzlang;

import java.util.*;

public class MzLangClass
{
	public UUID uuid=UUID.randomUUID();
	public String name;
	public Class<? extends MzLangObject> jvmClass;
	public String getName()
	{
		return name;
	}
	public Class<? extends MzLangObject> getJvmClass()
	{
		if(jvmClass==null)
			load();
		return jvmClass;
	}
	
	public void load()
	{
	}
}
