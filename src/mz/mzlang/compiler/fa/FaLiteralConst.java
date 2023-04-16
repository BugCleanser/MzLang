package mz.mzlang.compiler.fa;

public class FaLiteralConst extends FaElement
{
	public Object value;
	public FaLiteralConst(CodeReader reader,Object value)
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
