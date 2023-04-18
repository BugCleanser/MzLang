package mz.mzlang.compiler;

import mz.mzlang.compiler.fa.*;
import mz.mzlang.compiler.la.*;

public class MzLangCompilerError extends Error
{
	public String codeName;
	int lineNum;
	public MzLangCompilerError(String codeName,int lineNum,String msg)
	{
		super(msg);
		this.codeName=codeName;
		this.lineNum=lineNum;
	}
	public MzLangCompilerError(CodeReader reader,String msg)
	{
		this(reader.codeName,reader.lineNum,msg);
	}
	public MzLangCompilerError(FaElement fa,String msg)
	{
		this(fa.codeName,fa.lineNum,msg);
	}
	public MzLangCompilerError(FaElementsReader fa,String msg)
	{
		this(fa.codeName,0,msg);
	}
	
	@Override
	public String toString()
	{
		return "mz.mzlang.compiler.MzLangCompilerError: "+codeName+":"+lineNum+"\n"+getMessage();
	}
}
