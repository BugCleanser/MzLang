package mz.mzlang;

import mz.mzlang.compiler.*;

import java.util.*;
import java.util.concurrent.*;

public class MzLangClassLoader extends ClassLoader
{
	public static MzLangClassLoader instance;
	public MzLangClassLoader(ClassLoader parent)
	{
		super(parent);
	}
	
	public Map<String,MzLangClass> loadedClasses=new ConcurrentHashMap<>();
	public Map<MzLangClass,Class<? extends MzLangObject>> classObjects=new ConcurrentHashMap<>();
	
	void load(String code)
	{
		MzLangClass mlc=MzLangCompiler.compile(code);
		if(loadedClasses.containsKey(mlc.getName()))
		{
			if(classObjects.containsKey(mlc))
				return;
			throw new RuntimeException("Class loading with the same name.");
		}
		mlc.load();
		loadedClasses.put(mlc.getName(),mlc);
		classObjects.put(mlc,mlc.getJvmClass());
	}
	
	@Override
	public Class<? extends MzLangObject> findClass(String name) throws ClassNotFoundException
	{
		MzLangClass mlc=loadedClasses.get(name);
		if(mlc!=null)
		{
			Class<? extends MzLangObject> ans=classObjects.get(mlc);
			if(ans!=null)
				return ans;
		}
		throw new ClassNotFoundException(name);
	}
}
