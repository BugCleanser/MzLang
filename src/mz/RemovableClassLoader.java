package mz;

import java.util.*;
import java.util.concurrent.*;

public class RemovableClassLoader
{
	public class Part extends ClassLoader
	{
		public String className;
		public byte[] byteCode;
		public Class<?> contentClass;
		public Part(String className,byte[] byteCode)
		{
			super(RemovableClassLoader.this.parent);
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
			return RemovableClassLoader.this.findClass(name);
		}
	}
	public ClassLoader parent;
	public Map<String,Part> classes;
	public RemovableClassLoader(ClassLoader parent)
	{
		this.parent=parent;
		this.classes=new ConcurrentHashMap<>();
	}
	public void addClass(String name,byte[] byteCode)
	{
		classes.put(name,new Part(name,byteCode));
	}
	public void removeClass(String name)
	{
		classes.remove(name);
	}
	public Class<?> findClass(String name) throws ClassNotFoundException
	{
		if(classes.containsKey(name))
			return classes.get(name).getContentClass();
		else
			throw new ClassNotFoundException(name);
	}
}
