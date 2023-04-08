package mz.mzlang;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

public class MzLangJvmInterface implements MzLangInterface
{
	public Class<?> jvmClass;
	public MzLangJvmInterface(Class<?> jvmClass)
	{
		this.jvmClass=jvmClass;
	}
	
	@Override
	public Set<MzMethodHead> getMethods()
	{
		Set<MzMethodHead> ans=new HashSet<>();
		for(Method m:jvmClass.getMethods())
			ans.add(new MzMethodHead(m.getName(),new MzLangJvmInterface(m.getReturnType()),Arrays.stream(m.getParameterTypes()).map(MzLangJvmInterface::new).collect(Collectors.toList())));
		return ans;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof MzLangJvmInterface)
			return this.jvmClass==((MzLangJvmInterface)o).jvmClass;
		return false;
	}
}
