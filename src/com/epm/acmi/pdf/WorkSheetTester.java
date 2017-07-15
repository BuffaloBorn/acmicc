package com.epm.acmi.pdf;

public class WorkSheetTester {
	public static void main (String [] args) {
		try {
			WorkSheet workSheet = new WorkSheet();
			
			workSheet.makePDF("d446", "B06001173", "2131322" , "c://pdf.xsl", "AAID");
		} catch (Exception e) {e.printStackTrace();}
	}
}
