package mz.mzlang.compiler.la;

import mz.mzlang.compiler.fa.*;

import java.util.*;

public class FaElementsReader
{
	public String codeName;
	public List<FaElement> value;
	public int index;
	public FaElementsReader(String codeName,List<FaElement> value)
	{
		this.codeName=codeName;
		this.value=value;
		this.index=0;
	}
	
	public boolean hasNext()
	{
		return index<value.size();
	}
	public FaElement getNext(int i)
	{
		return value.get(index+i);
	}
	public FaElement read()
	{
		return value.get(index++);
	}
	
	public void back()
	{
		index--;
	}
}
