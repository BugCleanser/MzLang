package mz.mzlang.compiler.parser;

public class MzLangParsedLiteralConst extends MzLangParsed
{
	public Object value;
	public MzLangParsedLiteralConst(CodeReader reader,Object value)
	{
		super(reader);
		this.value=value;
	}
	
	@Override
	public String toString()
	{
		return "lc: "+value;
	}
}
