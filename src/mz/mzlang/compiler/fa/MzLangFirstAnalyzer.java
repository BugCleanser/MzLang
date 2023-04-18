package mz.mzlang.compiler.fa;

import mz.mzlang.compiler.*;

import java.util.*;

public class MzLangFirstAnalyzer
{
	public static MzLangFirstAnalyzer instance=new MzLangFirstAnalyzer();
	
	public Set<Character> operators;
	public Map<Character,Character> leftBracket;
	{
		operators=new HashSet<>();
		operators.add('@');
		operators.add('#');
		operators.add('$');
		operators.add('+');
		operators.add('-');
		operators.add('*');
		operators.add('/');
		operators.add('%');
		operators.add(',');
		operators.add('.');
		operators.add(':');
		operators.add(';');
		operators.add('?');
		operators.add('=');
		operators.add('|');
		operators.add('^');
		operators.add('&');
		operators.add('~');
		operators.add('!');
		operators.add('<');
		operators.add('>');
		operators.add('(');
		operators.add('[');
		operators.add('{');
		leftBracket=new HashMap<>();
		leftBracket.put(')','(');
		leftBracket.put(']','[');
		leftBracket.put('}','{');
	}
	
	public String readCodeString(CodeReader reader,char t)
	{
		StringBuilder ans=new StringBuilder();
		for(;;)
		{
			char sign=reader.read();
			if(sign==t)
				break;
			else if(sign=='\\')
			{
				sign=reader.read();
				switch(sign)
				{
					case '\\':
					case '\'':
					case '\"':
						break;
					case '0':
						sign='\0';
						break;
					case 'r':
						sign='\r';
						break;
					case 'n':
						sign='\n';
						break;
					case 't':
						sign='\t';
						break;
					case 'f':
						sign='\f';
						break;
					case 'b':
						sign='\b';
						break;
					case 'u':
						sign=0;
						for(int i=0;i<4;i++)
						{
							sign<<=4;
							sign+=reader.readHex();
						}
						break;
					default:
						reader.back();
						throw new MzLangCompilerError(reader,"Wrong escape");
				}
			}
			ans.append(sign);
		}
		return ans.toString();
	}
	
	public List<FaElement> analyze(String codeName,String code)
	{
		CodeReader reader=new CodeReader(codeName,code);
		Stack<FaElement> s=new Stack<>();
		for(;;)
		{
			char sign;
			boolean whitespace=false;
			try
			{
				while(Character.isWhitespace(sign=reader.read()))
					whitespace=true;
			}
			catch(StringIndexOutOfBoundsException e)
			{
				break;
			}
			switch(sign)
			{
				case '\'':
					String c=readCodeString(reader,'\'');
					if(c.length()!=1)
						throw new MzLangCompilerError(reader,"Wrong character length");
					s.add(new FaLiteralConst(reader,c.charAt(0)));
					break;
				case '\"':
					s.add(new FaLiteralConst(reader,readCodeString(reader,'\"')));
					break;
				default:
					if(sign>='0'&&sign<='9')
					{
						StringBuilder sb=new StringBuilder();
						sb.append('-');
						sb.append(sign);
						boolean hex=false;
						if(sign=='0')
						{
							try
							{
								sign=Character.toLowerCase(reader.read());
							}
							catch(StringIndexOutOfBoundsException e)
							{
								sign=' ';
							}
							if(sign=='x')
							{
								hex=true;
								sb.append('x');
							}
							else
								reader.back();
						}
						for(;;)
						{
							try
							{
								sign=Character.toLowerCase(reader.read());
							}
							catch(StringIndexOutOfBoundsException e)
							{
								sign=' ';
							}
							if(sign>='0'&&sign<='9' || hex&&sign>='a'&&sign<='f' || (!hex)&&sign=='.')
								sb.append(sign);
							else
							{
								switch(sign)
								{
									case 'f':
										s.push(new FaLiteralConst(reader,-Float.parseFloat(sb.toString())));
										break;
									case 'd':
										s.push(new FaLiteralConst(reader,-Double.parseDouble(sb.toString())));
										break;
									case 'l':
										s.push(new FaLiteralConst(reader,-Long.decode(sb.toString())));
										break;
									default:
										reader.back();
										if(sb.toString().contains("."))
											s.push(new FaLiteralConst(reader,-Double.parseDouble(sb.toString())));
										else
											s.push(new FaLiteralConst(reader,-Integer.decode(sb.toString())));
								}
								break;
							}
						}
					}
					else if(operators.contains(sign))
					{
						s.push(new FaOperator(reader,sign));
						if((!whitespace)&&s.size()>0&&s.lastElement() instanceof FaOperator)
						{
							((FaOperator)s.get(s.size()-2)).last=false;
							((FaOperator)s.lastElement()).first=false;
						}
					}
					else if(leftBracket.containsKey(sign))
					{
						FaComponent a=new FaComponent(reader,leftBracket.get(sign));
						while((!(s.lastElement() instanceof FaOperator))||((FaOperator)s.lastElement()).operator!=a.leftBracket)
							a.push(s.pop());
						s.pop();
						s.push(a);
					}
					else
					{
						StringBuilder sb=new StringBuilder();
						sb.append(sign);
						for(;;)
						{
							try
							{
								sign=reader.read();
							}
							catch(StringIndexOutOfBoundsException e)
							{
								sign=' ';
							}
							if(Character.isWhitespace(sign)||operators.contains(sign))
							{
								reader.back();
								switch(sb.toString())
								{
									case "true":
										s.push(new FaLiteralConst(reader,true));
										break;
									case "false":
										s.push(new FaLiteralConst(reader,false));
										break;
									default:
										s.push(new FaIdentifier(reader,sb.toString()));
										break;
								}
								break;
							}
							else
								sb.append(sign);
						}
					}
			}
		}
		List<FaElement> ans=new ArrayList<>();
		while(!s.isEmpty())
			ans.add(s.pop());
		return ans;
	}
}
