package mz.mzlang.compiler.fa;

public class FaElement
{
	public String codeName;
	public int lineNum;
	public FaElement(CodeReader codeReader)
	{
		this.codeName=codeReader.getCodeName();
		this.lineNum=codeReader.getLineNum();
	}
}
