package mz.mzlang;

import mz.*;

import java.util.*;
import java.util.concurrent.*;

public class MzLangClassLoader extends RemovableClassLoader
{
	public static MzLangClassLoader instance;
	public MzLangClassLoader(ClassLoader parent)
	{
		super(parent);
	}
	
	public Map<String,String> codeToLoad=new ConcurrentHashMap<>();
	public Map<MzMethodHead,Part> interfaces=new ConcurrentHashMap<>();
	
	
	public void addClass(String name,String code)
	{
		codeToLoad.put(name,code);
	}
	
	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException
	{
		codeToLoad.computeIfPresent(name,(k,v)->
		{
			//Compile
			return null;
		});
		return super.findClass(name);
	}
	
	@Override
	public void removeClass(String name)
	{
		if(codeToLoad.remove(name)==null)
			super.removeClass(name);
	}
}
