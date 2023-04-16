package mz.mzlang.compiler.fa;

import java.util.*;

public class FaComponent extends FaElement
{
	public char leftBracket;
	public List<FaElement> elements;
	public FaComponent(CodeReader codeReader,char leftBracket)
	{
		super(codeReader);
		this.leftBracket=leftBracket;
		elements=new ArrayList<>();
	}
	public void push(FaElement element)
	{
		elements.add(element);
	}
}
