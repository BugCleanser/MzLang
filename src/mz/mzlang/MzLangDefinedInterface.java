package mz.mzlang;

import java.util.*;

public class MzLangDefinedInterface implements MzLangInterface
{
	public HashSet<MzMethodHead> methods;
	
	@Override
	public Set<MzMethodHead> getMethods()
	{
		return methods;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o.getClass()!=MzLangDefinedInterface.class)
			return false;
		return Objects.equals(this.methods,((MzLangDefinedInterface)o).methods);
	}
}
