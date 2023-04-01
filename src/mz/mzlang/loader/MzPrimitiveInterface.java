package mz.mzlang.loader;

public class MzPrimitiveInterface extends MzInterface
{
	public static MzPrimitiveInterface BOOL=new MzPrimitiveInterface();
	public static MzPrimitiveInterface CHAR=new MzPrimitiveInterface();
	public static MzPrimitiveInterface BYTE=new MzPrimitiveInterface();
	public static MzPrimitiveInterface SHORT=new MzPrimitiveInterface();
	public static MzPrimitiveInterface INT=new MzPrimitiveInterface();
	public static MzPrimitiveInterface LONG=new MzPrimitiveInterface();
	public static MzPrimitiveInterface FLOAT=new MzPrimitiveInterface();
	public static MzPrimitiveInterface DOUBLE=new MzPrimitiveInterface();
	
	@Override
	public boolean equals(Object o)
	{
		return o==this;
	}
}
