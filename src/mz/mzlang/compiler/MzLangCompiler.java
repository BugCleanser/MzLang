package mz.mzlang.compiler;

import mz.mzlang.*;

public class MzLangCompiler
{
	public static void main(String[] args)
	{
		MzLangClassLoader.instance=new MzLangClassLoader(ClassLoader.getSystemClassLoader());
	}
	
	public static MzLangClass compile(String code)
	{
		return null;
	}
}
