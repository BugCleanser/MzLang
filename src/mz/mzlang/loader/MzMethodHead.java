package mz.mzlang.loader;

import java.util.*;

public class MzMethodHead
{
	public String name;
	public MzInterface returnType;
	public List<MzInterface> parametersTypes;
	public MzMethodHead(String name,MzInterface returnType,List<MzInterface> parametersTypes)
	{
		this.name=name;
		this.returnType=returnType;
		this.parametersTypes=parametersTypes;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(name,parametersTypes.size());
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof MzMethodHead))
			return false;
		MzMethodHead that=(MzMethodHead)o;
		return Objects.equals(name,that.name)&&parametersTypes.size()==that.parametersTypes.size()&&Objects.equals(returnType,that.returnType)&&Objects.equals(parametersTypes,that.parametersTypes);
	}
}
