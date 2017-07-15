package com.epm.acmi.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.cc.framework.adapter.struts.FWActionForm;

/**
 * Memos and Amendment Form
 * @author Rajeev Chachra
 *
 */
public class MemosAmendForm extends FWActionForm
{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public MemosAmendForm()
    {
    }


    public void reset(ActionMapping actionMapping, HttpServletRequest request)
    {
        checkboxMDI = false;
        checkboxSIAD = false;    
        checkboxANCH = false;
        checkboxAOA = false;
        checkboxGAL = false;
        checkboxMMI = false;
        checkboxMOI = false;
        checkboxSIA = false;
        checkboxSIAG = false;
        checkboxMAOT = false;
    }

    protected boolean checkboxMDI;
    protected boolean checkboxSIAD;    
    protected boolean checkboxANCH;
    protected boolean checkboxAOA;
    protected boolean checkboxGAL;
    protected boolean checkboxMMI;
    protected boolean checkboxMOI;
    protected boolean checkboxSIA;
    protected boolean checkboxSIAG;
    protected boolean checkboxMAOT;
    
    protected String  mdi;
    protected String  siad;    
    protected String anch;
    protected String aoa;
    protected String gal;
    protected String mmi;
    protected String moi;
    protected String sia;
    protected String siag;
    protected String maot;
    
    
    
	public boolean isCheckboxMAOT() {
		return checkboxMAOT;
	}


	public void setCheckboxMAOT(boolean checkboxMAOT) {
		this.checkboxMAOT = checkboxMAOT;
	}


	public String getMaot() {
		return maot;
	}


	public void setMaot(String maot) {
		this.maot = maot;
		
		if (!maot.trim().equals("")) {
			this.setCheckboxMAOT(true);		
		} 
		else
		{
			this.setCheckboxMAOT(false);
		}
		//		Fix to issue #67 cmontero 03/28/2006		
		if (maot.trim().equals("isChecked")) {
			this.maot="";
		}
		//		End of Fix
	}


	public String getSiad() {
		return siad;
	}
	public void setSiad(String Siad) {
		this.siad = Siad;
		if (!Siad.trim().equals("")) {
			this.setCheckboxSIAD(true);		
		} 
		else
		{
			this.setCheckboxSIAD(false);
		}
		//		Fix to issue #67 cmontero 03/28/2006		
		if (siad.trim().equals("isChecked")) {
			this.siad="";
		}
		//		End of Fix
	}
    
	public String getMdi() {
		return mdi;
	}
	public void setMdi(String Mdi) {
		this.mdi = Mdi;
		if (!Mdi.trim().equals("")) {
			this.setCheckboxMDI(true);		
		} 
//		else
//		{
//			this.setCheckboxMDI(false);
//		}
		//		Fix to issue #67 cmontero 03/28/2006		
		if (mdi.trim().equals("isChecked")) {
			this.mdi="";
		}
		//		End of Fix
	}


	public String getAnch() {
		return anch;
	}


	public void setAnch(String anch) {
		this.anch = anch;
		if (!anch.trim().equals("")) {
			this.setCheckboxANCH(true);		
		} 
//		else
//		{
//			this.setCheckboxANCH(false);
//		}
		//		Fix to issue #67 cmontero 03/28/2006		
		if (anch.trim().equals("isChecked")) {
			this.anch="";
		}
		//		End of Fix
	}


	public String getAoa() {
		return aoa;
	}


	public void setAoa(String aoa) {
		this.aoa = aoa;
		if (!aoa.trim().equals("")) {
			this.setCheckboxAOA(true);		
		} 
//		else
//		{
//			this.setCheckboxAOA(false);
//		}
		//		Fix to issue #67 cmontero 03/28/2006		
		if (aoa.trim().equals("isChecked")) {
			this.aoa="";
		}
		//		End of Fix
	}


	public String getGal() {
		return gal;
	}


	public void setGal(String gal) {
		this.gal = gal;
		if (!gal.trim().equals("")) {
			this.setCheckboxGAL(true);		
		} 
		else
		{
			this.setCheckboxGAL(false);
		}
		//		Fix to issue #67 cmontero 03/28/2006		
		if (gal.trim().equals("isChecked")) {
			this.gal="";
		}
		//		End of Fix
	}


	public String getMmi() {
		return mmi;
	}


	public void setMmi(String mmi) {
		this.mmi = mmi;
		if (!mmi.trim().equals("")) {
			this.setCheckboxMMI(true);		
		} 
//		else
//		{
//			this.setCheckboxMMI(false);
//		}
		//		Fix to issue #67 cmontero 03/28/2006		
		if (mmi.trim().equals("isChecked")) {
			this.mmi="";
		}
		//		End of Fix
	}


	public String getMoi() {
		return moi;
	}


	public void setMoi(String moi) {
		this.moi = moi;
		if (!moi.trim().equals("")) {
			this.setCheckboxMOI(true);		
		} 
//		else
//		{
//			this.setCheckboxMOI(false);
//		}
		//		Fix to issue #67 cmontero 03/28/2006		
		if (moi.trim().equals("isChecked")) {
			this.moi="";
		}
		//		End of Fix
	}


	public String getSia() {
		return sia;
	}


	public void setSia(String sia) {
		this.sia = sia;
		if (!sia.trim().equals("")) {
			this.setCheckboxSIA(true);		
		} 
//		else
//		{
//			this.setCheckboxSIA(false);
//		}
		//		Fix to issue #67 cmontero 03/28/2006		
		if (sia.trim().equals("isChecked")) {
			this.sia="";
		}
		//		End of Fix
	}


	public String getSiag() {
		return siag;
	}


	public void setSiag(String siag) {
		this.siag = siag;
		if (!siag.trim().equals("")) {
			this.setCheckboxSIAG(true);		
		} 
		else
		{
			this.setCheckboxSIAG(false);
		}
		//		Fix to issue #67 cmontero 03/28/2006		
		if (siag.trim().equals("isChecked")) {
			this.siag="";
		}
		//		End of Fix
	}


	public boolean isCheckboxANCH() {
		return checkboxANCH;
	}


	public void setCheckboxANCH(boolean checkboxANCH) {
		this.checkboxANCH = checkboxANCH;
	}


	public boolean isCheckboxAOA() {
		return checkboxAOA;
	}


	public void setCheckboxAOA(boolean checkboxAOA) {
		this.checkboxAOA = checkboxAOA;
	}


	public boolean isCheckboxGAL() {
		return checkboxGAL;
	}


	public void setCheckboxGAL(boolean checkboxGAL) {
		this.checkboxGAL = checkboxGAL;
	}


	public boolean isCheckboxMDI() {
		return checkboxMDI;
	}


	public void setCheckboxMDI(boolean checkboxMDI) {
		this.checkboxMDI = checkboxMDI;
	}


	public boolean isCheckboxMMI() {
		return checkboxMMI;
	}


	public void setCheckboxMMI(boolean checkboxMMI) {
		this.checkboxMMI = checkboxMMI;
	}


	public boolean isCheckboxMOI() {
		return checkboxMOI;
	}


	public void setCheckboxMOI(boolean checkboxMOI) {
		this.checkboxMOI = checkboxMOI;
	}


	public boolean isCheckboxSIA() {
		return checkboxSIA;
	}


	public void setCheckboxSIA(boolean checkboxSIA) {
		this.checkboxSIA = checkboxSIA;
	}


	public boolean isCheckboxSIAD() {
		return checkboxSIAD;
	}


	public void setCheckboxSIAD(boolean checkboxSIAD) {
		this.checkboxSIAD = checkboxSIAD;
	}


	public boolean isCheckboxSIAG() {
		return checkboxSIAG;
	}


	public void setCheckboxSIAG(boolean checkboxSIAG) {
		this.checkboxSIAG = checkboxSIAG;
	}



}