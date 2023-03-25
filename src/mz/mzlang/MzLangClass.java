package mz.mzlang;

import java.util.*;

public class MzLangClass
{
	public String name;
	public Class<? extends MzLangObject> jvmClass;
	public String getName()
	{
		return name;
	}
	public Class<? extends MzLangObject> getJvmClass()
	{
		return Objects.requireNonNull(jvmClass);
	}
	
	public void load()
	{
	}
}
