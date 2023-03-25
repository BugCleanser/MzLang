package mz.mzlang.compiler.parser;

import java.io.*;
import java.util.*;

public class MzLangParser
{
	public static MzLangParser instance=new MzLangParser();
	
	public Set<Character> operators;
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
		operators.add(')');
		operators.add('[');
		operators.add(']');
		operators.add('{');
		operators.add('}');
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
						throw reader.error("Wrong escape");
				}
			}
			ans.append(sign);
		}
		return ans.toString();
	}
	
	public List<MzLangParsed> parse(String code)
	{
		CodeReader reader=new CodeReader(code);
		List<MzLangParsed> ans=new ArrayList<>();
		for(;;)
		{
			char sign;
			CodeReader readerMark=reader.clone();
			try
			{
				while(Character.isWhitespace(sign=reader.read()));
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
						throw readerMark.error("Wrong character length");
					ans.add(new MzLangParsedLiteralConst(readerMark,c.charAt(0)));
					break;
				case '\"':
					ans.add(new MzLangParsedLiteralConst(readerMark,readCodeString(reader,'\"')));
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
							if(Character.toLowerCase(reader.read())=='x')
							{
								hex=true;
								sb.append('x');
							}
							else
								reader.back();
						}
						for(;;)
						{
							sign=Character.toLowerCase(reader.read());
							if(sign>='0'&&sign<='9' || hex&&sign>='a'&&sign<='f' || (!hex)&&sign=='.')
								sb.append(sign);
							else
							{
								switch(sign)
								{
									case 'f':
										ans.add(new MzLangParsedLiteralConst(readerMark,-Float.parseFloat(sb.toString())));
										break;
									case 'd':
										ans.add(new MzLangParsedLiteralConst(readerMark,-Double.parseDouble(sb.toString())));
										break;
									case 'l':
										ans.add(new MzLangParsedLiteralConst(readerMark,-Long.decode(sb.toString())));
										break;
									default:
										reader.back();
										if(sb.toString().contains("."))
											ans.add(new MzLangParsedLiteralConst(readerMark,-Double.parseDouble(sb.toString())));
										else
											ans.add(new MzLangParsedLiteralConst(readerMark,-Integer.decode(sb.toString())));
								}
								break;
							}
						}
					}
					else if(operators.contains(sign))
						ans.add(new MzLangParsedOperator(readerMark,sign));
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
										ans.add(new MzLangParsedLiteralConst(readerMark,true));
										break;
									case "false":
										ans.add(new MzLangParsedLiteralConst(readerMark,false));
										break;
									default:
										ans.add(new MzLangParsedIdentifier(readerMark,sb.toString()));
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
		return ans;
	}
}
