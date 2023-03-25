package mz.mzlang.compiler.parser;

public class MzLangParsedOperator extends MzLangParsed
{
	public char operator;
	public MzLangParsedOperator(CodeReader reader,char operator)
	{
		super(reader);
		this.operator=operator;
	}
	
	@Override
	public String toString()
	{
		return "op: "+operator;
	}
}
