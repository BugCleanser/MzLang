package mz;

import java.util.*;

public class UnloadableClassLoader
{
	public class Part extends ClassLoader
	{
		public String className;
		public byte[] byteCode;
		public Class<?> contentClass;
		public Part(String className,byte[] byteCode)
		{
			super(UnloadableClassLoader.this.parent);
			this.className=className;
			this.byteCode=byteCode;
		}
		public Class<?> getContentClass()
		{
			if(contentClass!=null)
				return contentClass;
			return contentClass=this.defineClass(className,byteCode,0,byteCode.length);
		}
		@Override
		public Class<?> findClass(String name) throws ClassNotFoundException
		{
			return UnloadableClassLoader.this.findClass(name);
		}
	}
	public ClassLoader parent;
	public Map<String,Part> classes;
	public UnloadableClassLoader(ClassLoader parent)
	{
		this.parent=parent;
	}
	public void addClass(String className,byte[] byteCode)
	{
		classes.put(className,new Part(className,byteCode));
	}
	public Class<?> findClass(String name) throws ClassNotFoundException
	{
		if(classes.containsKey(name))
			return classes.get(name).getContentClass();
		else
			throw new ClassNotFoundException(name);
	}
}
