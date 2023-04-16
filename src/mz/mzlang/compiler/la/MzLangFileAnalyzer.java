package mz.mzlang.compiler.la;

import mz.mzlang.compiler.fa.*;

import java.util.*;

public class MzLangFileAnalyzer
{
	public static MzLangFileAnalyzer instance=new MzLangFileAnalyzer();
	
	public SrcMzLangElement analyze(List<FaElement> fa)
	{
		String packet="";
		Set<String> usings=new HashSet<>();
		while(fa.size()>0)
		{
			switch(((FaIdentifier)fa.get(0)).identifier)
			{
				case "package":
				{
					int i=1;
					for(;(!(fa.get(i) instanceof FaOperator)) || ((FaOperator)fa.get(i)).operator!=';';i++)
					{
						if(fa.get(i) instanceof FaIdentifier)
							packet+=((FaIdentifier)fa.get(i)).identifier;
						else
							packet+=((FaOperator)fa.get(i)).operator;
					}
					packet+='.';
					fa=fa.subList(i+1,fa.size());
					break;
				}
				case "using":
				{
					String using="";
					int i=1;
					for(;(!(fa.get(i) instanceof FaOperator)) || ((FaOperator)fa.get(i)).operator!=';';i++)
					{
						if(fa.get(i) instanceof FaIdentifier)
							using+=((FaIdentifier)fa.get(i)).identifier;
						else
							using+=((FaOperator)fa.get(i)).operator;
					}
					using+='.';
					fa=fa.subList(i+1,fa.size());
					usings.add(using);
					break;
				}
				case "class":
					
					break;
				case "placeholder":
					break;
			}
		}
		return null;
	}
}
