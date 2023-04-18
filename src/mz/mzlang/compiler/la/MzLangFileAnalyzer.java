package mz.mzlang.compiler.la;

import mz.mzlang.compiler.*;
import mz.mzlang.compiler.fa.*;

import java.util.*;

public class MzLangFileAnalyzer
{
	public static MzLangFileAnalyzer instance=new MzLangFileAnalyzer();
	
	public SrcMzLangFile analyze(FaElementsReader fa)
	{
		String packet="";
		Set<String> usings=new HashSet<>();
		while(fa.hasNext())
		{
			FaElement i=fa.read();
			if(!(i instanceof FaIdentifier))
				throw new MzLangCompilerError(i,"Format Error");
			switch(((FaIdentifier)i).identifier)
			{
				case "package":
				{
					int j=1;
					for(;(!(fa.get(j) instanceof FaOperator)) || ((FaOperator)fa.get(j)).operator!=';';j++)
					{
						if(fa.get(j) instanceof FaIdentifier)
							packet+=((FaIdentifier)fa.get(j)).identifier;
						else
							packet+=((FaOperator)fa.get(j)).operator;
					}
					packet+='.';
					fa=fa.subList(j+1,fa.size());
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
					fa=fa.subList(1,fa.size());
					if(!(fa.get(0) instanceof FaIdentifier))
						throw new MzLangCompilerError(fa.get(0),"Format Error");
					boolean abs=false;
					if(((FaIdentifier)fa.get(0)).identifier.equals("abstract"))
					{
						abs=true;
						fa=fa.subList(1,fa.size());
					}
					if(!(fa.get(0) instanceof FaIdentifier))
						throw new MzLangCompilerError(fa.get(0),"Format Error");
					SrcMzLangClass ans=new SrcMzLangClass(packet+((FaIdentifier)fa.get(0)).identifier);
					fa=fa.subList(1,fa.size());
					
					return ans;
				case "placeholder":
					break;
				default:
					throw new MzLangCompilerError(fa.get(0),"Format Error");
			}
		}
		throw new MzLangCompilerError(fa,"Format Error");
	}
}
