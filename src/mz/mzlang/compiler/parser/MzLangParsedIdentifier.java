package mz.mzlang.compiler.parser;

public class MzLangParsedIdentifier extends MzLangParsed
{
	public String identifier;
	public MzLangParsedIdentifier(CodeReader reader,String identifier)
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
