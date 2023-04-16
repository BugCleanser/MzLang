package mz.mzlang.compiler.fa;

public class CodeReader implements Cloneable
{
	public String codeName;
	public String code;
	public int index;
	public int lineNum;
	public CodeReader(String codeName,String code)
	{
		this.codeName=codeName;
		this.code=code;
		index=0;
		lineNum=0;
	}
	
	public String getCodeName()
	{
		return codeName;
	}
	public String getCode()
	{
		return code;
	}
	public int getIndex()
	{
		return index;
	}
	public int getLineNum()
	{
		return lineNum;
	}
	void back()
	{
		if(code.charAt(--index)=='\n')
			lineNum--;
	}
	char read() throws StringIndexOutOfBoundsException
	{
		if(code.charAt(index)=='\n')
			lineNum++;
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
	
	public FaError error(String msg)
	{
		return new FaError(msg);
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
