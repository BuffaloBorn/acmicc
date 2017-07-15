package com.epm.acmi.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Driver;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.xml.sax.InputSource;

import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.DocumentMetaDataForm;
import com.epm.acmi.struts.form.dsp.StellentClient;
import com.epm.acmi.util.Connect;
import com.epm.acmi.util.LocalProperties;
import com.epm.acmi.util.StellentUpdateAudit;

/**
 * WorkSheet class creates New Business Work Sheet PDF
 * @author Rajeev Chachra
 *
 */
public class WorkSheet {

	private static final String NBWS_XSL_DIR = "NBWS_XSL_DIR";
	private static final String PDF_OUTPUT_DIR = "PDF_OUTPUT_DIR";
	private static final Logger log = Logger.getLogger(WorkSheet.class);

	/**
	 * Create NBWS PDF
	 * @param strGfid
	 * @param policyNumber
	 * @param absPath
	 * @param AAID
	 * @throws Exception
	 */
	public void makePDF(final String userId, final String strGfid, final String policyNumber, final String absPath, final String AAID) throws Exception {
		Thread makePDFThread = new Thread(new Runnable() {
			public void run()
			{
				try
				{
					doMakePDF(userId, strGfid, policyNumber, absPath, AAID);
				} catch (Exception e)
				{
					log.error(e.getClass().getName() + " caught while trying to generate PDF", e);
				}
			}
		});
		
		makePDFThread.start();
	}
		
	public void doMakePDF(String userId, String strGfid, String policyNumber, String absPath, String AAID) throws Exception {

		log.debug(" Begin makePDF");
		String NULL_STRING = "";
		DocumentMetaDataForm dmf = new DocumentMetaDataForm();
		Element root = new Element("Root");
		Document doc = new Document(root);
		Connection conn = null;
		Statement stmt = null;

		try {
			
			conn = Connect.getConnection();
			stmt = conn.createStatement();

			String input = strGfid;

			String strRootData = "select KeyAppFirstName,KeyAppLastName,PolicyNo,State,AgentName, EFT,IASListBill,PlanId,Network,Deductible,CoInsurance,CoPay,ScreenBy,convert(varchar(10),ScreenDate,101),PharmaRequestedBy,NoResultsFound, UWInitial,  gfid, AgentNumber from IUAApplication where GFID = ?";

			PreparedStatement psmtAppTable = conn.prepareStatement(strRootData);
			psmtAppTable.setString(1, input);
			ResultSet rsRootData = psmtAppTable.executeQuery();

			while (rsRootData.next()) {

				Element keyAppFirstName = new Element("KeyAppFirstName");
				keyAppFirstName.setText(rsRootData.getString(1));
				root.addContent(keyAppFirstName);
				dmf.setKeyAppFirstName(rsRootData.getString(1));

				Element keyAppLastName = new Element("KeyAppLastName");
				keyAppLastName.setText(rsRootData.getString(2));
				root.addContent(keyAppLastName);
				dmf.setKeyAppLastName(rsRootData.getString(2));
				
				// USR 8399-1 CHANGES 
				if (rsRootData.getString(3).equals("0")) {
					Element gfid = new Element("gfid");
					gfid.setText(rsRootData.getString(18));
					root.addContent(gfid);
					dmf.setGfid(rsRootData.getString(18));
					dmf.setPolicyNumber("0");
				} else {
					Element policyNo = new Element("PolicyNo");
					policyNo.setText(rsRootData.getString(3));
					root.addContent(policyNo);
					dmf.setPolicyNumber(rsRootData.getString(3));
				}	
				// ENDS USR 8399-1 CHANGES 
				Element state = new Element("State");
				state.setText(rsRootData.getString(4));
				root.addContent(state);
				dmf.setState(rsRootData.getString(4));

				Element agentName = new Element("AgentName");
				agentName.setText(rsRootData.getString(5));
				root.addContent(agentName);
				dmf.setAgentName(rsRootData.getString(5));

			
				Element eFT = new Element("EFT");
				eFT.setText(rsRootData.getString(6));
				root.addContent(eFT);
				dmf.setAgentNumber(rsRootData.getString(6));

				Element listBill = new Element("ListBill");
				listBill.setText(rsRootData.getString(7));
				root.addContent(listBill);
				dmf.setLbid(rsRootData.getString(7));

				Element plan = new Element("Plan");
				plan.setText(rsRootData.getString(8));
				root.addContent(plan);

				Element network = new Element("Network");
				network.setText(rsRootData.getString(9));
				root.addContent(network);

				Element deductible = new Element("Deductible");
				deductible.setText(rsRootData.getString(10));				
				root.addContent(deductible);

				Element coInsurance = new Element("CoInsurance");
				coInsurance.setText(rsRootData.getString(11));
				root.addContent(coInsurance);

				Element coPay = new Element("CoPay");							
				coPay.setText(rsRootData.getString(12));
				root.addContent(coPay);

				Element screenBy = new Element("ScreenBy");
				screenBy.setText(rsRootData.getString(13));
				root.addContent(screenBy);

				Element screenDate = new Element("ScreenDate");
				screenDate.setText(rsRootData.getString(14));
				root.addContent(screenDate);

				Element pharmaReqBy = new Element("PharmaRequestedBy");
				pharmaReqBy.setText(rsRootData.getString(15));
				root.addContent(pharmaReqBy);

				Element noResultFound = new Element("NoResultsFound");
				noResultFound.setText(rsRootData.getString(16));
				root.addContent(noResultFound);

				Element uwInitial = new Element("UWInitial");
				uwInitial.setText(rsRootData.getString(17));
				root.addContent(uwInitial);
				
										
				dmf.setGfid(rsRootData.getString(18));
								
				// Agent number was missing had to add it
				Element agentNum = new Element("AgentNumber");
				agentNum.setText(rsRootData.getString(19));
				root.addContent(agentNum);
				dmf.setAgentNumber(rsRootData.getString(19));
				
			}

			String strPolicyData = "select PreviousPolicyNo,Status,convert(varchar(10),PaidToDate,101),convert(varchar(10),EffectiveDate,101),InsuredName from IUAPrevPolicy where GFID = ?";

			PreparedStatement psmtPolicyData = conn.prepareStatement(strPolicyData);
			psmtPolicyData.setString(1, input);

			ResultSet rsPolicyData = psmtPolicyData.executeQuery();
			Element policies = new Element("PrevPolicies");
			root.addContent(policies);

			while (rsPolicyData.next()) {
				Element policy = new Element("Policy");

				Element no = new Element("No");
				no.setText(rsPolicyData.getString(1));
				policy.addContent(no);

				Element status = new Element("Status");
				status.setText((rsPolicyData.getString(2)).trim());
				policy.addContent(status);

				Element pTD = new Element("PTD");
				pTD.setText(rsPolicyData.getString(3));
				policy.addContent(pTD);

				Element eFFDate = new Element("EFFDate");
				eFFDate.setText(rsPolicyData.getString(4));
				policy.addContent(eFFDate);

				Element insuredName = new Element("InsuredName");
				insuredName.setText(rsPolicyData.getString(5));
				policy.addContent(insuredName);

				policies.addContent(policy);

			}

			String strMemoNote = "select a.MemoCode,b.MemoName,a.Note from IUAAppMemos a,IUAMemoCodes b where a.GFID = ? and a.MemoCode=b.MemoCode and a.Note NOT LIKE 'ischecked'";

			PreparedStatement psmtMemoNote = conn.prepareStatement(strMemoNote);
			psmtMemoNote.setString(1, input);

			ResultSet rsMemoNote = psmtMemoNote.executeQuery();
			Element notes = new Element("Notes");
			root.addContent(notes);

			while (rsMemoNote.next()) {

				Element note = null;
				if ((rsMemoNote.getString(1).equalsIgnoreCase(Constants.shared_ADEC))
						|| (rsMemoNote.getString(1).equalsIgnoreCase(Constants.shared_DNP))
						|| (rsMemoNote.getString(1).equalsIgnoreCase(Constants.shared_RCA))
						|| (rsMemoNote.getString(1).equalsIgnoreCase(Constants.shared_RPR))
						|| (rsMemoNote.getString(1).equalsIgnoreCase(Constants.shared_SIOT))) {
					note = new Element("SharedNote");
				} else {
					note = new Element("MemoAndAmendNote");
				}

				Element memoCode = new Element("MemoCode");
				memoCode.setText((rsMemoNote.getString(1)).trim());
				note.addContent(memoCode);

				Element memoNote = new Element("MemoNote");
				memoNote.setText(rsMemoNote.getString(2));
				note.addContent(memoNote);

				Element noteDesc = new Element("NoteDesc");
				noteDesc.setText(rsMemoNote.getString(3));
				note.addContent(noteDesc);

				notes.addContent(note);
			}

			String strMedRecRequest = "select FirstName, LastName, PhysicianName, Type, RequestDate from IUAMedRecRequest where GFID = ? ";

			PreparedStatement psmtMedRecRequest = conn.prepareStatement(strMedRecRequest);
			psmtMedRecRequest.setString(1, input);

			ResultSet rsMedRecRequest = psmtMedRecRequest.executeQuery();
			Element medRecRequest = new Element("MedRecRequest");
			root.addContent(medRecRequest);

			while (rsMedRecRequest.next()) {

				Element keySpouseDep = null;

				if ((rsMedRecRequest.getString("Type").trim().equalsIgnoreCase("S"))) {
					keySpouseDep = new Element("Spouse");
				} if ((rsMedRecRequest.getString("Type").trim().equalsIgnoreCase("K"))) {
					keySpouseDep = new Element("Key");
				} else {
					keySpouseDep = new Element("Dependent");
				}

				Element keyName = new Element("KeyName");
				String firstname = rsMedRecRequest.getString("FirstName");
				String lastname = rsMedRecRequest.getString("LastName");
				String fullname ="";
				if (null != firstname && null != lastname)
				{
					fullname = firstname.trim() + " " + lastname.trim();
				} else if (null == firstname && null != lastname)
				{
					fullname = lastname.trim();
				} else if (null == firstname && null != lastname)
				{
					fullname = firstname.trim() ;	
				}
				keyName.setText(fullname);
				keySpouseDep.addContent(keyName);

				Element doctor = new Element("Doctor");
				doctor.setText(rsMedRecRequest.getString("PhysicianName"));
				keySpouseDep.addContent(doctor);

				Element medRecDate = new Element("RequestDate");
				String format = "MM/dd/yyyy";
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
				Timestamp dt = rsMedRecRequest.getTimestamp("RequestDate");
				if (null != dt) {
					medRecDate.setText(sdf.format(dt));
					keySpouseDep.addContent(medRecDate);
				}
				medRecRequest.addContent(keySpouseDep);
			}
			rsMedRecRequest.close();
		} catch (SQLException se) {
			log.error("Error occurred during PDF Generation, GFID =" + strGfid);
			log.error("Exception " + se.getClass().getName() + " caught with message: " + se.getMessage());
			se.printStackTrace();
		} finally {
			Connect.closeSTMT(stmt);
			Connect.closeConnection(conn);
		}

		try {

			String path = absPath;
			String xslFilePathName = path + LocalProperties.getProperty(NBWS_XSL_DIR);
			String pdfOutputDir = LocalProperties.getProperty(PDF_OUTPUT_DIR);
			String FileName = NULL_STRING;
			String fName = NULL_STRING;
			
			if (policyNumber.equals("0")) {

				FileName = pdfOutputDir + strGfid + "_NBWS.pdf";
				fName = strGfid + "_NBWS.pdf";

			} else {

				FileName = pdfOutputDir + policyNumber + "_NBWS.pdf";
				fName = policyNumber + "_NBWS.pdf";

			}

			FileOutputStream outpdffile = new FileOutputStream(new File(FileName));
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(xslFilePathName));
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			XMLOutputter xmloutputter = new XMLOutputter();
			
			
			xmloutputter.output(doc, outStream);
			xmloutputter.output(doc, System.out);

			ByteArrayInputStream inStream = new ByteArrayInputStream(outStream.toByteArray());
			Source src = new StreamSource(inStream);
			transformer.transform(src, new StreamResult(out));
			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			InputSource source = new InputSource(in);
			Driver driver = new Driver(source, outpdffile);

			driver.setRenderer(Driver.RENDER_PDF);
			driver.run();

			
			outStream.close();
			inStream.close();
			in.close();
			outpdffile.close();
			
			
			log.debug("PDF sucessfully created");
			log.debug(" file path is " + FileName);

			log.debug("loading document into stellent");
			dmf.setDocCode("WRK");
			dmf.setDocType("New Business Worksheet");	
			if (policyNumber.equals("0")) 
				dmf.setDocDescription("New Business WorkSheet " + dmf.getGfid());
			else 
				dmf.setDocDescription("New Business WorkSheet " + dmf.getPolicyNumber());
			StellentClient sc = new StellentClient();
			String recId = sc.createDocument(dmf, fName);
			StellentUpdateAudit.auditStellentUpdate(userId, dmf.getGfid(), recId, StellentUpdateAudit.AUDIT_CREATE);
			log.debug("done loading document into stellent");

		} catch (Exception e) {
			log.error("Error occurred during PDF Generation, GFID =" + strGfid);
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage());
			e.printStackTrace();
		}

		log.debug(" End makePDF");

	}

}
