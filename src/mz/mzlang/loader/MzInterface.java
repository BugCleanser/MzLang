package mz.mzlang.loader;

import java.util.*;

public class MzInterface extends HashSet<MzMethodHead>
{
	@Override
	public boolean equals(Object o)
	{
		if(o.getClass()!=MzInterface.class)
			return false;
		return super.equals(o);
	}
}
