package mz.mzlang.compiler.fa;

public class FaOperator extends FaElement
{
	public char operator;
	public boolean first,last;
	public FaOperator(CodeReader reader,char operator)
	{
		super(reader);
		this.operator=operator;
		this.first=true;
		this.last=true;
	}
	
	@Override
	public String toString()
	{
		return "op: "+operator;
	}
}
