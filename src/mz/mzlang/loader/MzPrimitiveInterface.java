package mz.mzlang.loader;

import java.util.*;

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
	public static Map<String,MzPrimitiveInterface> values;
	static
	{
		values=new HashMap<>();
		values.put("Bool",BOOL);
		values.put("Char",CHAR);
		values.put("Byte",BYTE);
		values.put("Short",SHORT);
		values.put("Int",INT);
		values.put("Long",LONG);
		values.put("Float",FLOAT);
		values.put("Double",DOUBLE);
	}
	
	@Override
	public boolean equals(Object o)
	{
		return o==this;
	}
}
