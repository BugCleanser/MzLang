package mz.mzlang.compiler.parser;

public class CodeReader implements Cloneable
{
	public String code;
	public int index;
	public CodeReader(String code)
	{
		this.code=code;
		index=0;
	}
	
	public String getCode()
	{
		return code;
	}
	public int getIndex()
	{
		return index;
	}
	void back()
	{
		index--;
	}
	char read() throws StringIndexOutOfBoundsException
	{
		return code.charAt(index++);
	}
	public byte readHex()
	{
		char t=Character.toLowerCase(read());
		if(t>='0'&&t<='9')
			return (byte)(t-'0');
		else if(t>='a'&&t<='f')
			return (byte)(t-'a'+10);
		back();
		throw error("Wrong hex");
	}
	
	public MzLangParseError error(String msg)
	{
		return new MzLangParseError(msg);
	}
	
	@Override
	public CodeReader clone()
	{
		try
		{
			return (CodeReader)super.clone();
		}
		catch(CloneNotSupportedException e)
		{
			throw new AssertionError();
		}
	}
}
