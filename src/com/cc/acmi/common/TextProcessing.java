package com.cc.acmi.common;

import java.util.StringTokenizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.text.BreakIterator;
import java.util.Locale;

import com.cc.acmi.common.StringW;


public class TextProcessing {

	private static Logger log = Logger.getLogger(TextProcessing.class);
	
	public static final String EMPTY = "";
	
	public static String backspace(String  s, int repeat) {
       
		StringBuffer buffer = new StringBuffer(repeat * s.length());
		for (int i = 0; i < repeat; i++)
			buffer.append(s);

		return buffer.toString();
    }
	
	
	public static String formatSSN(String mfDate)
	{
		StringBuffer sb = new StringBuffer();
		
		if (mfDate.length() != 9 )
		{
			return mfDate;
		}
		
		
		String three = mfDate.substring(0, 3);
		String two = mfDate.substring(3, 5);
		String four = mfDate.substring(5,9);

		
		return sb.append(three).append("-").append(two).append("-").append(four).toString();
	}
	
	public static String SSSSSSSSSFormat(String Ssn)
	{
		
		String removeDots =  StringUtils.replace(Ssn, "-", "");
		
		if (removeDots.length() != 9 )
		{
			return "0";
		} 
		

		return  removeDots;		
		
	}
	
	public static String formatRiderText(String[] outArray )
	{
		String item =null;
		StringBuffer TextArea = new StringBuffer();
		int SpaceSize = 50;
		
		
		for (int i = 0; i < outArray.length; i++)
		{
			item = outArray[i];
			
			//int padding = 0;
			
			StringBuffer itemTxt = new StringBuffer(item);
			
			/*if(item.length() != SpaceSize)
			{
				padding =  SpaceSize - item.length(); 
				itemTxt.append(backspace(" ", padding));
			}*/
			
			if(item.length() == 0) 
			{
				TextArea.append(backspace(" ", SpaceSize)).append(System.getProperty("line.separator"));
			}
			else
			{
				TextArea.append(itemTxt).append(System.getProperty("line.separator"));
			}	
			
		}
		return rtrim(TextArea.toString());
		
	}
	
	public static String formatTextNew(String[] outArray, int SpaceSize )
	{
		String item =null;
		StringBuffer TextArea = new StringBuffer();
		int itemLength = 0;
		int Stringpadding; 
		
		for (int i = 0; i < outArray.length; i++)
		{
			item = outArray[i];
			itemLength = item.length();
			Stringpadding = 0;
			
			if(itemLength == 0) 
			{
				TextArea.append(backspace(" ", SpaceSize));
			}
			else
			{
				if (itemLength < SpaceSize)
				{
					Stringpadding = SpaceSize - itemLength;
					TextArea.append(" ").append(item).append(backspace(" ", Stringpadding-1));
				}	
				
				if (itemLength == SpaceSize)
					TextArea.append(" ").append(item);
			}	
			
		}
		return rtrim(TextArea.toString());
		
	}
	
	
	public static String formatText(String[] outArray, int SpaceSize )
	{
		String item =null;
		StringBuffer TextArea = new StringBuffer();
		
		//int NewSpaceSize = SpaceSize - 1;
		//int padding = 0;
		
		
		for (int i = 0; i < outArray.length; i++)
		{
			item = outArray[i];
			
			StringBuffer itemTxt = new StringBuffer(item);
			
			/*if(item.length() != SpaceSize)
			{
				padding =  SpaceSize - item.length(); 
				itemTxt.append(backspace(" ", padding));
			}*/
			
			
			for(int b=0; b<itemTxt.length(); b++)
			{
				if(itemTxt.charAt(b)=='ß')
				{
					itemTxt.replace(b, b+1, " ");
				}
				else
				{	break;
					
				}
			}
			
			if(itemTxt.toString().trim().length() == 0) 
			{
				TextArea.append(System.getProperty("line.separator"));
			}
			else
			{
				//padding = NewSpaceSize - item.length(); 
				//TextArea.append(item).append(backspace(" ", padding));
				TextArea.append(itemTxt.toString()).append(System.getProperty("line.separator")); 
			}	
			
		}
		return rtrim(TextArea.toString());
	
	}
	
	public static String formatTextStdEventMemo(String[] outArray, int SpaceSize )
	{
		String item =null;
		StringBuffer TextArea = new StringBuffer();
		
		for (int i = 0; i < outArray.length; i++)
		{
			item = outArray[i];
			
			StringBuffer itemTxt = new StringBuffer(item);
			
			for(int b=0; b<itemTxt.length(); b++)
			{
				if(itemTxt.charAt(b)=='ß')
				{
					itemTxt.replace(b, b+1, " ");
				}
				else
				{	break;
					
				}
			}
			
			if(itemTxt.toString().trim().length() == 0)  
			{
				TextArea.append(backspace(" ", SpaceSize)).append(System.getProperty("line.separator"));
			}
			else
			{
				TextArea.append(itemTxt.toString()).append(System.getProperty("line.separator")); 
				//TextArea.append(" ").append(item);
			}	
			
		}
		return rtrim(TextArea.toString());
		
	}
	
	public static String MMDDYYYYFormat(String mfDate)
	{
	
		StringBuffer sb = new StringBuffer();
		
		if (mfDate.trim().length() == 0 )
		{
			sb.append("");
		}
		
		if (mfDate.length() != 10 )
		{
			sb.append("");
		} 
		
		String[] recipientList  = Pattern.compile("/").split(mfDate);
		
		if(recipientList.length == 3) 
		{
			
			String year = recipientList[2];
			String month = recipientList[0];
			String day = recipientList[1];
			
			sb.append(month).append(day).append(year).toString();

			
		}	
		
		return sb.toString();
	}
	
	public static String YYYYMMDDFormat(String mfDate)
	{
	
		if (mfDate.length() != 10 )
		{
			return "0";
		} 
		
		StringBuffer sb = new StringBuffer();
		String[] recipientList  = Pattern.compile("/").split(mfDate);
		
		if(recipientList.length == 3) 
		{
			
			String year = recipientList[2];
			String month = recipientList[0];
			String day = recipientList[1];
			
			return sb.append(year).append(month).append(day).toString();

			
		}	
		return  "0";
		
	}
	
	public static String formatMainFrameMessage(String message)
	{
		String REGEX = "[0-9]*:*";
		
		String REPLACE = "";
		
		 Pattern p = Pattern.compile(REGEX);
		 Matcher m = p.matcher(message); // get a matcher object
		 StringBuffer sb = new StringBuffer();
		  while (m.find()) {
		      m.appendReplacement(sb, REPLACE);
		    }
		    m.appendTail(sb);
		  
		return sb.toString();
	}
	
	
	public static String dateFormat(String mfDate)
	{
	
		StringBuffer sb = new StringBuffer();
		
		if(mfDate.equalsIgnoreCase("0"))
		{
			return "";
		}	
		
		if (mfDate.length() != 8 )
		{
			return mfDate;
		}
		
		
		String year = mfDate.substring(0, 4);
		String month = mfDate.substring(4, 6);
		String day = mfDate.substring(6,8);

		
		return sb.append(month).append("/").append(day).append("/").append(year).toString();
		
	}
	
	public static String plainDateFormat(String mfDate)
	{
	
		StringBuffer sb = new StringBuffer();
		
		if (mfDate.length() != 8 )
		{
			return mfDate;
		}
		
		String month = mfDate.substring(0, 2);
		String day = mfDate.substring(2,4);
		String year = mfDate.substring(4, 8);
		
		
		
		return sb.append(month).append("/").append(day).append("/").append(year).toString();
		
	}

	public static String recipientFormat(String recipient)
	{
		StringBuffer sb = new StringBuffer();
		String[] recipientList  = Pattern.compile(" ").split(recipient);
		
		for (int i = 0; i < recipientList.length; i++ )
		{
			sb.append(recipientList[i].toString()).append("/");
		}
		
		
		return sb.deleteCharAt(sb.length()-1).toString();
	}
	
	public class recipientDiaryFormat{
		private String recipient_id="";
		private String recipient_name="";
		
		
		public recipientDiaryFormat()
		{
			super();
		}
		
		public recipientDiaryFormat(String recipient)
		{
			StringBuffer sb = new StringBuffer();
			String[] recipientList  = Pattern.compile(" ").split(recipient);
			
			if (recipientList.length == 4)
			{
				this.recipient_id = recipientList[0];
				
				for (int i = 1; i < recipientList.length; i++ )
				{
					sb.append(recipientList[i].toString()).append("/");
				}
				
				this.recipient_name =  sb.deleteCharAt(sb.length()-1).toString();
			}
		}

		public String getRecipient_id() {
			return recipient_id;
		}

		public void setRecipient_id(String recipient_id) {
			this.recipient_id = recipient_id;
		}

		public String getRecipient_name() {
			return recipient_name;
		}

		public void setRecipient_name(String recipient_name) {
			this.recipient_name = recipient_name;
		}
		
	}
	
	public class reponseZipCode{
		private String five="";
		private String four="";
		
		public reponseZipCode()
		{
			super();
		}
		
		
		public reponseZipCode(String zipCode)
		{
			
			String removeDots = StringUtils.replace(zipCode, "-", "");
			String removeFirstCurls = StringUtils.replace(removeDots, "(", "");
			String removeLastCurls =  StringUtils.replace(removeFirstCurls,")", "");
			
			if (removeLastCurls.length() == 9) 
			{
				this.five = removeLastCurls.substring(0, 5);
				this.four = removeLastCurls.substring(5, 9);
			}
			
			if (removeLastCurls.length() == 0) 
			{
				this.five = "";
				this.four = "";
			}
			
			if (removeLastCurls.length() == 5) 
			{
				this.five = removeLastCurls.substring(0, 5);
				this.four = "";
			}

		}


		public String getFive() {
			return five;
		}


		public void setFive(String five) {
			this.five = five;
		}


		public String getFour() {
			return four;
		}


		public void setFour(String four) {
			this.four = four;
		}
		
	}
	
	
	
	public class reponsePhone{
		
		private String area = "";
		private String exchange= "";
		private String number="";
		
		public reponsePhone()
		{
			super();
		}
		
		
		public reponsePhone(String fullnumber)
		{
			String REGEX = "[(]*[)]*[-]*[ ]*";

			String REPLACE = "";
			
			 Pattern p = Pattern.compile(REGEX);
			 Matcher m = p.matcher(fullnumber); // get a matcher object
			 StringBuffer sb = new StringBuffer();
			  while (m.find()) {
			      m.appendReplacement(sb, REPLACE);
			    }
			    m.appendTail(sb);
			
			
			if (sb.length() == 10)
			{
				this.area = sb.substring(0, 3);
				this.exchange = sb.substring(3, 6);
				this.number = sb.substring(6, 10);
				
			}
			
		}


		public String getArea() {
			return area;
		}


		public void setArea(String area) {
			this.area = area;
		}


		public String getExchange() {
			return exchange;
		}


		public void setExchange(String exchange) {
			this.exchange = exchange;
		}


		public String getNumber() {
			return number;
		}


		public void setNumber(String number) {
			this.number = number;
		}
		
	}
	
	public class reponseDate{
		
		private String respn_yyyy;
		private String respn_mm;
		private String respn_dd;
		
		
		public reponseDate() {
			super();
		}


		public reponseDate(String respn) 
		{
			if (respn.toString().length() == 8 )
			{
				this.respn_yyyy = respn.substring(0, 4);
				this.respn_mm = respn.substring(4, 6);
				this.respn_dd = respn.substring(6,8);
				
			}	
		}
		
		
		public String getRespn_dd() {
			return respn_dd;
		}
		public void setRespn_dd(String respn_dd) {
			this.respn_dd = respn_dd;
		}
		public String getRespn_mm() {
			return respn_mm;
		}
		public void setRespn_mm(String respn_mm) {
			this.respn_mm = respn_mm;
		}
		public String getRespn_yyyy() {
			return respn_yyyy;
		}
		public void setRespn_yyyy(String respn_yyyy) {
			this.respn_yyyy = respn_yyyy;
		}
		
		
	}  
	
	public static void storeTextDataV(String str, int StartText, int EndStart,String[] TEXT, int LastPosition, int FirstPosition, int MaxLength )
	{
		
		int StrLength = 0;
		StringBuffer loadLine = new StringBuffer();
		StrLength = str.length();
		int o = StartText;
		String hold = "";
		
		
		
		if(StrLength == 0)
		{
			TEXT[o] = "";
			return;
		}
		
		int end = LastPosition;
		int begin = 0;
		
		while(end < StrLength)
		{

			if ( o > EndStart) 
			{
				return;
			}
			
			hold = str.substring(begin, end);
			loadLine.append(substringBeforeLast(hold, " "));
			TEXT[o] = loadLine.toString();
			loadLine = new StringBuffer();
			loadLine.append(substringAfterLast(hold, " "));
			
			begin = begin + LastPosition;
			//begin = begin - loadLine.length();
			end = begin + LastPosition;
			o++;
		}
		
		loadLine.append(str.substring(begin, StrLength));
		TEXT[o] = loadLine.toString();
		
	}
	
	public static String substringBeforeLast(String str, String separator) {
        if (isEmpty(str) || isEmpty(separator)) {
            return str;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

  

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    
    public static String substringAfterLast(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (isEmpty(separator)) {
            return EMPTY;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1 || pos == (str.length() - separator.length())) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    public static void storeTextData(String str, int StartText, int EndStart,String[] TEXT, int LastPosition, int FirstPosition, int MaxLength )
    {
    	  	
    	String wrapwordText = null;
		
    	str = str.replaceAll("\r\n", "\n");
    	
    	wrapwordText = StringW.wordWrap(str, LastPosition, "\n", " ");
    	
    	String[] items = wrapwordText.split("\n");
    	   
    	for (int i = 0; i < items.length; i++) 
    	{

			StringBuffer itemTxt = new StringBuffer(items[i]);
			
			for(int b=0; b<itemTxt.length(); b++)
			{
				if(itemTxt.charAt(b)==' ')
				{
					itemTxt.replace(b, b+1, "ß");
				}
				else
				{	break;
					
				}
			}
    		
    		if (i <= EndStart)
    			TEXT[i] = itemTxt.toString();
    	}
    	
    }
    
    public static void storeTextDataLetter(String str, int StartText, int EndStart,String[] TEXT, int LastPosition, int FirstPosition, int MaxLength )
    {
    	  	
    	String wrapwordText = null;
    	int o = StartText; 
    	str = str.replaceAll("\r\n", "\n");
    	
    	wrapwordText = StringW.wordWrap(str, LastPosition, "\n", " ");
    	
    	String[] items = wrapwordText.split("\n");
    	    
    	for (int i = 0; i < items.length; i++) 
    	{
    		
    		StringBuffer itemTxt = new StringBuffer(items[i]);
			
			for(int b=0; b<itemTxt.length(); b++)
			{
				if(itemTxt.charAt(b)==' ')
				{
					itemTxt.replace(b, b+1, "ß");
				}
				else
				{	break;
					
				}
			}
    		
    		if (i < EndStart)
    		{
    			TEXT[o] = itemTxt.toString();
    			o++;	
    		}
    	}
    	
    }
    
    
    public static int acmiWrapWord(String[] TEXT, String TextStr, int wrapWith)
    {
    	
    	int BPos = 0; 
    	int EPos = 0;
    	int Pos =0;
    	String TempStr = "";
    	String CRLF = "\r\n";
    	char CR = '\r';
    	int ArrayCounter = 0;
    	boolean SkipCRLF = false;
    	
    	while(BPos < TextStr.length())
    	{
    		SkipCRLF = false; //comment reset flag with each loop iteration
    		
    		
    		//comment - determine the end of search position for this loop iteration 
    		EPos = BPos + (wrapWith-1); 
    		if(EPos > (TextStr.length()-1))
    			EPos = (TextStr.length()-1);
    		
    		//comment - get the sub string to be parsed
    		TempStr = TextStr.substring(BPos, EPos-1);
    		
    		//comment - check for a CRLF combination
    		Pos = TempStr.indexOf(CRLF);
    		
    		if(Pos == -1)
    		{

    			//comment - if no CRLF was found then check to see if a word is being split at the end of TempStr
    			if(!Character.isWhitespace(TempStr.charAt(EPos-1))) 
    			{
    				//comment - check to see if the last character is a the end of a word or a sentence
    				if(!Character.isWhitespace(TextStr.charAt(EPos+1))) 
        			{
    					//comment - if the next character was a CRLF then save the string 
    					if(TextStr.charAt(EPos+1)== CR )
    					{
    						SkipCRLF = true; //comment - set flag so the CRLF can be skipped
    						Pos = EPos;		 //comment - set Pos to point at the character just before the CRLF
    					}	
    					else
    					{
    						//comment - find the beginning of the split word
    				    	while(EPos >= BPos )					
    				    	{
    				    		EPos = EPos - 1; //comment - go back one character
    				    		if(Character.isWhitespace(TextStr.charAt(EPos+1)))
    				    		{
    				    			Pos = EPos;
    				    			break;
    				    		}
    				    		
    				    	}	
    					}//End of If CRLF	
        			}
    			}	
    			else  //comment - if the last character was a blank space
    			{
    				Pos = EPos; //comment - set Pos equal to the end of the sub string so the whole substring will be put into the array
    				TEXT[ArrayCounter] = TempStr;
    			} //End of If Blank Space test
    		} 
    		else  //comment - a CRLF was found 
    		{
    				SkipCRLF = true; //comment - set flag so the CRLF can be skipped
    				Pos = Pos -1; //comment - set Pos to point at the character just before the CRLF
    		}//End of IF Pos test
    		
    	   
    		TEXT[ArrayCounter] = TempStr.substring(BPos, Pos);
    		
    		BPos = Pos + 1;
    		ArrayCounter++;
    		
    		if(SkipCRLF)
    		{
    			BPos = BPos + 2; //comment - skip the CRLF
    		}
    		
    		
    	}
    
    	return ArrayCounter;
    }
    
	public static void storeTextDataStdEventMemo(String str, int StartText, int EndStart,String[] TEXT, int LastPosition, int FirstPosition, int MaxLength )
	{
			char charAtI;
			int chType;
			int letterCount = 0; 
			int StrLength = 0;
			StringBuffer loadLine = new StringBuffer();
			int o = StartText; 
			
			boolean insertFlag = false;
			
			StrLength = str.length();
			
			if(StrLength == 0)
			{
				TEXT[o] = "";
				return;
			}
			
			for(int i = 0; i < StrLength; i++)
			{
				charAtI = str.charAt(i);
				
				chType = Character.getType(charAtI);
				
				insertFlag = true;
				
				if ( o > EndStart) 
				{
					return;
				}
				
				if(chType == Character.CONTROL)
	    		{
					TEXT[o] = loadLine.toString();
					letterCount = 0;
					insertFlag = false;
			    	loadLine = new StringBuffer("");
			    	o++;
			    	i++;
	    		}
				
				if(insertFlag)
				{
					if(letterCount < LastPosition)
					{
						if(loadLine.toString() == "")
						{
							loadLine = new StringBuffer();
							loadLine.append(charAtI);
						}
						else
						{
							loadLine.append(charAtI);	
						}
					   letterCount++;
					}
					else
					{
					   TEXT[o] = loadLine.toString();
					   loadLine = new StringBuffer();
		    		   o++;
		    		   letterCount = 0;
		    		   loadLine.append(charAtI);
		    		   letterCount++;
					 }
				}
			}	
			
			
			if(loadLine.length() <=  LastPosition)
			{
				TEXT[o] = loadLine.toString();
			}
	}
		
	public static void storeTextDataWorkWithWords(String str, int StartText, int EndStart,String[] TEXT, int LastPosition, int FirstPosition, int MaxLength )
	{
		Locale currentLocale = new Locale("en", "US");
		BreakIterator wordIterator = BreakIterator.getWordInstance(currentLocale);
		
		wordIterator.setText(str);
	    int start = wordIterator.first();
	    int end = wordIterator.next();
	    int o = FirstPosition;
	    int wordLength = 0;
	    boolean loadLineFlag;
	   
	    int endIndex = LastPosition;
	    int beginIndex = 0;
	    
	    
	    StringBuffer loadLine = new StringBuffer();

	    String word = str.substring(start, end);
	    
	    wordLength = word.length();
	    
	    if (str.length() <= LastPosition)
    	{
    		TEXT[o] = str;
    		end = BreakIterator.DONE;
    	}

		if (MaxLength == wordLength)
    	{
    		while(MaxLength != endIndex )
    		{		 	
    			TEXT[o] = str.substring(beginIndex, endIndex-1);
    			endIndex = endIndex + LastPosition;
    			beginIndex = beginIndex + LastPosition;
    			o++;
    		}
    		
    		end = BreakIterator.DONE;
    	}
	    
	    
	    while ((end != BreakIterator.DONE) && (o < EndStart) ) 
	    {
	    	loadLineFlag = true;
	    	
	    	word = str.substring(start, end);
	    	
	    	wordLength = word.length();

	    	if (loadLine.toString().length()  < LastPosition+1)
	    	{
				if(wordLength > 1)
		    	{
		    		int firstCh = Character.getType(word.charAt(0));
		    		int secondCh = Character.getType(word.charAt(1));
		    		
		    		if((firstCh == Character.SPACE_SEPARATOR) && (secondCh == Character.CONTROL))
		    		{
		    			TEXT[o] = loadLine.toString();
		    			loadLine = new StringBuffer();
		    			o++;
		    			loadLineFlag = false;
		    		}
		    		
		    		if((firstCh == Character.CONTROL) && (secondCh == Character.CONTROL))
		    		{
		    			TEXT[o] ="";
	    				loadLine = new StringBuffer();
	    				o++;
	    				loadLineFlag = false;
		    		}
		    		
		    	}
	 
	    		if (loadLineFlag)
	    		{
		    		int combineSize = loadLine.toString().length() + word.length();
		    		
		    		if (combineSize < LastPosition+1)
		    		{	
		    			loadLine.append(word);
		    		}
		    		else
		    		{
		    			TEXT[o] = loadLine.toString();
		    			loadLine = new StringBuffer();
		    			loadLine.append(word);
		    			o++;
		    		}

		    		loadLineFlag = false;
	    		}	
	    		
	    	}
	    	else
	    	{
	    		TEXT[o] = loadLine.substring(0, LastPosition-1 );
	    		o++;
	    		TEXT[o] = loadLine.substring(LastPosition,  loadLine.length()-1 );
	    		o++;
	    	}
	    	
	    	start = end;
	    	end = wordIterator.next();
	    }
	}
	
	
	public static void storeTextDataNotWorking(String str, int StartText, int EndStart,String[] TEXT, int LastPosition, int FirstPosition )
	{
	
		//form.getFreeTextArea(), StartText=1, EndStart=60, Text , LastPosition=75, FirstPosition=0
		
		Locale currentLocale = new Locale("en", "US");
		BreakIterator wordIterator = BreakIterator.getWordInstance(currentLocale);
		
		wordIterator.setText(str);
	    int start = wordIterator.first();
	    int end = wordIterator.next();
	    int o = FirstPosition;
	    
	    StringBuffer loadLine = new StringBuffer();
	    
	    while ((end != BreakIterator.DONE) && (o < EndStart) ) 
	    {
	    	String word = str.substring(start, end);
	    	
	    	if (word.trim().length() > LastPosition)
	    	{
	    		word = backspace(" ", 74);
	    	}
	    	
	    	if (loadLine.toString().length()  < LastPosition+1)
	    	{
	    		
	    	
	    		int combineSize = loadLine.toString().length() + word.length();
	    		
	    		if (combineSize < LastPosition+1)
	    		{	
	    			if (word.length() > 1)
	    			{
	    				log.debug("Character Type1: "+ Character.getType(word.charAt(0)) + " word: " + word.toString());
	    				log.debug("Character Type2: "+ Character.getType(word.charAt(1)) + " word: " + word.toString());
	    				
	    				/*if ((Character.getType(word.charAt(0)) == Character.SPACE_SEPARATOR) && (Character.getType(word.charAt(1)) == Character.CONTROL)) 
		    	    	{
		    				TEXT[o] ="";
		    				loadLine = new StringBuffer();
		    				o++;
		    	    	}*/
	    				
	    			}
	    			else
	    				loadLine.append(word);
	    		}
	    		else
	    		{
	    			TEXT[o] = loadLine.toString();
	    			//log.debug("loadLine: "+ loadLine.toString());
	    			loadLine = new StringBuffer();
	    			loadLine.append(word);
	    			o++;
	    		}
	    	}  	
	    	else
	    	{  			
	    		TEXT[o] = loadLine.toString();
	    		//log.debug("loadLine: "+ loadLine.toString());
	    		loadLine = new StringBuffer();
	    		
	    		if(word.length() < LastPosition+1)
	    		{
	    			loadLine.append(word);	    			
	    		}
	    		else
	    		{
	    			loadLine.append(word.substring(0, LastPosition-1));
	    			TEXT[o] = loadLine.toString();
	    			loadLine = new StringBuffer();
	    			loadLine.append(word.substring(LastPosition, word.length()-1));
	    		}	
	    		o++;
	    	}	
	    	
	    	start = end;
	    	end = wordIterator.next();
	    }

	}
	
	//static void formatLines(String target, int maxLength)
	public static void storeTextDataWithBreakIteratorLine(String str, int StartText, int EndStart,String[] TEXT, int LastPosition, int FirstPosition )
	{
	
		Locale currentLocale = new Locale("en", "US");
	    BreakIterator boundary = BreakIterator.getLineInstance(currentLocale);
	    boundary.setText(str);
	    int start = boundary.first();
	    int end = boundary.next();
	    int lineLength = 0;
	    int o = StartText;

	    StringBuffer loadLine = new StringBuffer();
	    
	    while (end != BreakIterator.DONE) {
	      String word = str.substring(start, end);
	      lineLength = lineLength + word.length();
	      if (lineLength >= LastPosition) {
	        //System.out.println();
	    	TEXT[o] = loadLine.toString();
	    	loadLine = new StringBuffer();
	    	o++;
	        lineLength = word.length();
	      }
	      //System.out.print(word);
	      loadLine.append(word);
	      start = end;
	      end = boundary.next();
	    }
	    
	    if (loadLine.toString().length() == lineLength ) 
	    {
	    	TEXT[o] = loadLine.toString();	
	    }
	    
	  }
	
	public static void storeTextDataOld(String str, int StartText, int EndStart,String[] TEXT, int LastPosition, int FirstPosition )
	{
				
			String g=""; 
			String w =""; 
			int tokenCount;
			int firstchar = FirstPosition; 
			int lastchar = LastPosition;
			
			int totallength = str.length();
			
			if( totallength < lastchar)
			{
				
				TEXT[StartText] = str;
				return;
			}	
			
			// char[] space = new char[] {' '};
		      
			for(int o=StartText; o < EndStart; o++)
			{
				g = str.substring(firstchar, lastchar);
				
				StringTokenizer st = new StringTokenizer(g);
				 tokenCount = st.countTokens();
				if (tokenCount==1)
				{
					TEXT[o] = g;
				}
				else
				{
					String u = g.substring(g.length()-1, g.length());
				
					if (u.length() != 0) 
					{					
						for (int i=g.length( ); i >0 ; i--)
						{
							w = g.substring(i-1,i );
							if (w.trim().length() == 0) 
							{
								break;
							}
							else
							{
								lastchar = lastchar - 1;
							}
							
						}
						g = str.substring(firstchar, lastchar);
						TEXT[o] = g;
					}
					else
					{
						TEXT[o] = g;
					}
					
					firstchar = lastchar;
					
					totallength = totallength - g.length();
					
					lastchar = lastchar + LastPosition;
					
					
					if (totallength < LastPosition)
					{
						o++;
						TEXT[o] = str.substring(firstchar, firstchar+totallength);
						break;
						
					}
				}
			}
		}
	
	
	public static String wordwrap(String str, int LineWidth)
	{
		//var LineWidth = 74;
		int SpaceLeft = LineWidth;
		String SpaceWidth = " "; 
		StringBuffer Text = new StringBuffer(); 
			
	
		String formcontent[] = str.split(" ");
		
		for (int i = 0; i < formcontent.length; i++)
		{

			String word = formcontent[i];
		
			if(word.length() > SpaceLeft)
			{
				Text.append(" ").append("~").append(word);
				SpaceLeft = LineWidth - word.length();
					
			}
			else
			{
				SpaceLeft = SpaceLeft - (word.length() + SpaceWidth.length());
				if(i == 0 )
				{
					Text.append(word);
				}	
				else
				{
					Text.append(" ").append(word);
				}
			}
			 
		
		}
		
	    return Text.toString();
		
	}
	
	 public static String ltrim(String str)
	 {
		 
		    int len = str.length();
		 	int i = 0;
		 	char[] val = str.toCharArray();
		 
		 	while(  i < len  && val[ i ] <= ' '  ) i++;
		 
		         return i != 0 ? str.substring( i ): str;
		 
	  }
	 
	 
	 public static String rtrim(String source) {
	        return source.replaceAll("\\s+$", "");
	    }


}
