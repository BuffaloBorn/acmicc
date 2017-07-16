package com.softwarag.extirex.webservice.seventcodes.client;

/*import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.softwarag.extirex.webservice.seventcodes.client.holders.MUEVNWSResponseMORE1Holder;
import com.softwarag.extirex.webservice.seventcodes.client.holders.MUEVNWSResponseMSG_INFOHolder;
import com.softwarag.extirex.webservice.seventcodes.client.holders.MUEVNWSResponseOUT_PARMSsHolder;*/

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class WSStdEventCodesCall {

	public static synchronized TreeMap fetch() //throws ServiceException, RemoteException
	{
		
		TreeMap map = new TreeMap();
		TreeMap localTMap = new TreeMap();
		
		map.put("ALC/DRUG", "ALCOHOL AND/OR DRUG");
		map.put("ALLG", "ALLERGIES");
		map.put("ANEM", "ANEMIA");
		map.put("APNEA", "SLEEP APNEA");
		map.put("ARTH", "ARTHRITIS");
		map.put("AST/ALLG", "ASTHMA AND ALLERGIES");
		map.put("ASTH", "ASTHMA");
		map.put("BACK", "BACK SPRAIN/STRAIN");
		map.put("BACK-01", "CHIROPRACTIC TREATMENT");
		map.put("BACK-02", "SCIATICA");
		map.put("BACK-03", "SCOLIOSIS");
		map.put("BP", "BLOOD PRESSURE");
		map.put("BRST", "BREAST IMPLANTS");
		map.put("BRST-01", "BREAST CYST");
		map.put("BRST-02", "FIBROCYSTIC BREAST DISEASE");
		map.put("BUILD", "HEIGHT & WEIGHT");
		map.put("BURS", "BURSITIS");
		map.put("CANCER", "CANCER (ENTER TYPE)");
		map.put("CARD DEF", "CARDIAC DEFECT");
		map.put("CAT", "CATARACT");
		map.put("CERVIX", "DYSPLASIA OR DISORDER");
		map.put("CHLSTRL", "CHOLESTEROL");
		map.put("CNDYLOMA", "GENITAL VERRUCAE,GENITAL WARTS,VENEREAL WARTS");
		map.put("CS", "CAESAREAN SECTION");
		map.put("CYST", "CYST-TEXT REQUIRED");
		map.put("DEAF", "DEAFNESS");
		map.put("DEFORM", "DEFORMITY");
		map.put("DIABETES", "DIABETES");
		map.put("DIV", "DIVERTICULITIS");
		map.put("DSL", "DISLOCATION-TEXT REQUIRED");
		map.put("DVLO", "DIVERTICULOSIS");
		map.put("EAR", "HEARING IMPAIRMENT");
		map.put("EAR-01", "OTITIS MEDIA");
		map.put("ENDO DIS", "ENDORCRINE DISORDER OR DISEASE");
		map.put("EYE", "ARTIFICIAL EYE");
		map.put("EYE-01", "BLINDNESS");
		map.put("EYE-02", "GLAUCOMA");
		map.put("FRAC", "FRACTURE-TEXT REQUIRED");
		map.put("GAS", "GASTRITIS");
		map.put("GERD", "ESOPHAGEAL REFLUX");
		map.put("GEST DM", "GESTATIONAL DIABETES");
		map.put("GOUT", "GOUT");
		map.put("HA", "HEADACHES");
		map.put("HIV/AIDS", "HIV AND/OR AIDS");
		map.put("HYDRO", "HYDROCEPHALUS");
		map.put("HYGL", "HYPOGLYCEMIA");
		map.put("IBS", "SPASTIC COLON/IRRITABLE BOWEL SYND");
		map.put("INT FIXA", "INTERNAL FIXATION-TEXT REQUIRED");
		map.put("JOINT", "JOINT-TEXT REQUIRED");
		map.put("KID", "KIDNEY STONES");
		map.put("MENTAL", "MENTAL DISORDERS");
		map.put("MVP", "MITRAL VALVE PROLAPSE");
		map.put("MVR", "DRIVING RECORD");
		map.put("MX", "TEXT IS REQUIRED");
		map.put("NEUROPA", "NEUROPATHY");
		map.put("OCCUPAT", "OCCUPATION-TEXT REQUIRED");
		map.put("OSTEOPEN", "OSTEOPENIA/OSTEOPOROSIS");
		map.put("PHL", "PHLEBITIS");
		map.put("PHL-01", "THROMBOPHLEBITIS");
		map.put("POLYPS", "POLYPS-TEXT REQUIRED");
		map.put("PREG", "PREGNANCY");
		map.put("PROS", "BENIGN PROSTATE HYPERTROPHY");
		map.put("PROS-01", "PROSTATITIS");
		map.put("RAYN", "RAYNAUD'S DISEASE");
		map.put("RAYN-01", "RAYNAUD'S PHENOMENON");
		map.put("RECTAL", "HEMORRHOIDS");
		map.put("RESP", "PLEURISY");
		map.put("SEIZURE", "INJURY,TREATMENT OR OPERATION RESULTING FROM SEIZU");
		map.put("SINUS", "SINUSITIS");
		map.put("SKIN", "ACNE");
		map.put("SKIN DIS", "SKIN DISORDER - TEXT REQUIRED");
		map.put("SKIN-01", "ECZEMA");
		map.put("SKIN-02", "PSORIASIS");
		map.put("STD", "SEXUALLY TRANSMITTED DISEASE");
		map.put("SURG PND", "SURGERY PENDING");
		map.put("SX DYSF", "SEXUAL DYSFUNCTION");
		map.put("TEND", "TENDONITIS");
		map.put("THY", "HYPERTHYROIDISM");
		map.put("TONSILS", "TONSIL/ADNOID");
		map.put("ULCER", "ULCER-TEXT REQUIRED");
		map.put("UT", "CYSTITIS");
		map.put("UT-02", "URETHRITIS");
		map.put("VERT", "VERTIGO");
		map.put("VIRUS", "VIRUS (ADD DESCRIPTION)");
		map.put("VV", "VARICOSE VEINS");

		
		
		/*IASLIBPort service = new SEventCodesLocator().getIASLIBPort();
		
		MUEVNWSResponseMORE1Holder more1 = new MUEVNWSResponseMORE1Holder();
		MUEVNWSResponseOUT_PARMSsHolder outparms = new MUEVNWSResponseOUT_PARMSsHolder();
		MUEVNWSResponseMSG_INFOHolder msginfo = new MUEVNWSResponseMSG_INFOHolder();
		MUEVNWSMORE more = new MUEVNWSMORE();
		
		
		more.setMORE_CODES(new String(""));
		more.setLAST_KEY(new String(""));
		
		service.MUEVNWS(more, msginfo, more1, outparms);
		
		MUEVNWSResponseOUT_PARMSsOUT_PARMS[] outArray =  outparms.value;
		
		for (int i = 0; i < outArray.length; i++)
		{
			MUEVNWSResponseOUT_PARMSsOUT_PARMS item = outArray[i];
			localTMap.put(item.getSTD_EVENT_ID(), item.getSTD_EVENT_ID()+ ": " + item.getDESCRIPTION());
			
		}*/
		
		 String key;
		 Set set= map.keySet (  ) ; 
	     Iterator iter = set.iterator (  ) ; 
	      
	     while ( iter.hasNext (  )  )  
	     {  
	    	
	    	  key = iter.next().toString();
	    	 localTMap.put(key, key+ ": " + map.get ( key  ));
	      }  


		return localTMap;
	}
	
}
