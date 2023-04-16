package mz.mzlang.compiler.fa;

public class FaIdentifier extends FaElement
{
	public String identifier;
	public FaIdentifier(CodeReader reader,String identifier)
	{
		super(reader);
		this.identifier=identifier;
	}
	
	@Override
	public String toString()
	{
		return "id: "+identifier;
	}
}
