package mz.mzlang;

import java.util.*;

public class MzMethodHead
{
	public String name;
	public MzLangInterface returnType;
	public List<MzLangInterface> parametersTypes;
	public MzMethodHead(String name,MzLangInterface returnType,List<MzLangInterface> parametersTypes)
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
		if(!(Objects.equals(name,that.name)&&parametersTypes.size()==that.parametersTypes.size()))
			return false;
		StackOverflowError e=null;
		try
		{
			if(!Objects.equals(returnType,that.returnType))
				return false;
		}
		catch(StackOverflowError error)
		{
			e=error;
		}
		for(int i=0;i<parametersTypes.size();i++)
		{
			try
			{
				if(!Objects.equals(parametersTypes.get(i),that.parametersTypes.get(i)))
					return false;
			}
			catch(StackOverflowError error)
			{
				e=error;
			}
		}
		if(e!=null)
			throw e;
		return true;
	}
}
