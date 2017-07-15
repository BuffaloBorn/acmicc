package com.cc.acmi.common;

public class HelpUtil {

	/*This class creates an unique for the help attribute on the page that have supply help   
	 *using the help.js to trigger the CC framework help system. The unique is is compose of 
	 *numerical value to select the right help page to open, policy number to pass to that page
	 *and abbreviation of the page who request the help. This abbreviation (example: FT = Free Text)
	 *is used in the Action class of the Help page to know where to return the result of the help page */
	
	public static String getEventPersonBrowsePolicyCertPersonHelp(String PolicyNo)
	{
		String help = null;
		help = "1" + "_" + PolicyNo + "_" + "EP";
		return help;
	}
	
	public static String getFreeTextPersonBrowsePolicyCertPersonHelp(String PolicyNo)
	{
		String help = null;
		help = "1" + "_" + PolicyNo + "_" + "FT";
		return help;
	}
	
	public static String getFreeTextPersonStdMemoHelp(String PolicyNo)
	{
		String help = null;
		help = "4" + "_" + PolicyNo + "_" + "FT";
		return help;
	}
	
	public static String getRiderMainPersonBrowsePolicyCertPersonHelp(String PolicyNo)
	{
		String help = null;
		help = "1" + "_" + PolicyNo + "_" + "RM";
		return help;
	}
	
	public static String getLetterPersonBrowsePolicyCertPersonHelp(String PolicyNo)
	{
		String help = null;
		help = "5" + "_" + PolicyNo + "_" + "LT";
		return help;
	}
	
	public static String getPolicyDiaryBrowsePolicyHelp(String PolicyNo)
	{
		String help = null;
		help = "3" + "_" + PolicyNo + "_" + "PD";
		return help;
	}
	
	public static String getEventStdMemoPersonBrowsePolicyCertPersonHelp(String PolicyNo)
	{
		String help = null;
		help = "6" + "_" + PolicyNo + "_" + "ESM";
		return help;
		
	}
	
	public static String getFreeTextMemoIdHelp(String PolicyNo)
	{
		String help = null;
		help = "7" + "_" + PolicyNo + "_" + "FT";
		return help;
		
	}
	
}
