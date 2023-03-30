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
	
	void load(String code)
	{
		MzLangClass mlc=MzLangCompiler.compile(code);
		if(loadedClasses.containsKey(mlc.getName()))
			throw new RuntimeException("Class loading with the same name.");
		mlc.load();
		loadedClasses.put(mlc.getName(),mlc);
	}
	
	@Override
	public Class<? extends MzLangObject> findClass(String name) throws ClassNotFoundException
	{
		MzLangClass mlc=loadedClasses.get(name);
		if(mlc!=null)
			return mlc.getJvmClass();
		throw new ClassNotFoundException(name);
	}
}
