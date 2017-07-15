package com.epm.acmi.struts.form;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.cc.acmi.common.User;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.ui.control.ControlActionContext;
import com.epm.acmi.bd.IUAProcessBD;
import com.epm.acmi.hbm.BaseHibernateDAO;
import com.epm.acmi.hbm.IuaappDocProcesses;
import com.epm.acmi.hbm.IuaappDocProcessesDAO;
import com.epm.acmi.hbm.IuainterviewRequest;
import com.epm.acmi.hbm.IuatelephonicInterview;
import com.epm.acmi.hbm.IuatelephonicInterviewDAO;
import com.epm.acmi.hbm.IuaunableToCompleteReasonCode;
import com.epm.acmi.hbm.Iuauser;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.util.ACMICache;
import com.epm.acmi.util.Connect;
import com.epm.acmi.util.EPMHelper;
import com.epm.acmi.util.IUAUtils;
import com.epm.acmi.util.MiscellaneousUtils;
import com.fujitsu.iflow.model.workflow.DataItem;
import com.fujitsu.iflow.model.workflow.ProcessInstance;
import com.fujitsu.iflow.model.workflow.WFAdminSession;
import com.fujitsu.iflow.model.workflow.WFObjectFactory;
import com.fujitsu.iflow.model.workflow.WFSession;
import com.fujitsu.iflow.model.workflow.WorkItem;

/**
 * Form Loader helper class
 * @author Rajeev Chacra
 *
 */
/**
 * @author d446
 *
 */
public class FormLoaderHelper {
	private static final String SCHEDULED_FOLLOW_UP_STRING = "The scheduled follow-up for this interview is: ";
	private static final String ONE = "1";
	private static final String DIVIDER_OR = "|";
	private static final String NURSE_CONSULTANT = "NC";
	private static final String SHOW = "show";
	private static final String NULL_STRING = "";
	private static final String SPACE = " ";
	private static final String COMMA = ",";
	private static final String DESCRIPTION = "Description";
	private static final String PRODUCT_SQL = " select Description from IUAProducts prod WITH (NOLOCK)  where prod.ProductCode = ? ";
	private static final String APPLICATION_BY_GFID = " select * from IUAApplication app WITH (NOLOCK)  where app.GFID = ? ";
	private static Logger log = Logger.getLogger(FormLoaderHelper.class);

	//Hibernate DAO's are thread-safe, so we can use static instances.
	private static IuatelephonicInterviewDAO phoneInterviewDAO = null;	
	private static IuaappDocProcessesDAO processDAO = null;
	private static final Object lock = new Object();
	private static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private static final DateFormat timeFormat = new SimpleDateFormat("hh:mm aa");

	public FormLoaderHelper() {
	}

	/**
	 * Load Memos and Amendment Form
	 * @param ctx
	 * @throws Exception
	 */
	public void loadMemosAmendForm(ControlActionContext ctx) throws Exception {
		log.debug("Begin loadMemosAmendForm()");

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rst = null;
		MemosAmendForm memosForm = new MemosAmendForm();
		try {
			conn = Connect.getConnection();

			String wid = (String) ctx.request().getSession().getAttribute("wid");
			User user = (User) ctx.session().getAttribute(com.epm.acmi.struts.Constants.loggedUser);

			EPMHelper epmHelper = new EPMHelper();
			String gfid = epmHelper.getGFIDFromWorkId(wid, user);

			String sql = " select * from IUAAppMemos  WITH (NOLOCK) where GFID = ? ";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, gfid);

			rst  = psmt.executeQuery();
			while (rst.next()) {
				String appMemoCode = rst.getString("MemoCode");
				if (appMemoCode.equalsIgnoreCase("ANCH ")) {
					String note = rst.getString("Note");
					if (!note.trim().equals("")) {
						memosForm.setAnch(note);
						memosForm.setCheckboxANCH(true);
					} else {
						memosForm.setCheckboxANCH(false);
					}
				}

				if (appMemoCode.equalsIgnoreCase("AOA  ")) {
					String note = rst.getString("Note");
					if (!note.trim().equals("")) {
						memosForm.setAoa(note);
						memosForm.setCheckboxAOA(true);
					} else {
						memosForm.setCheckboxAOA(false);
					}
				}

				if (appMemoCode.equalsIgnoreCase("GAL  ")) {
					String note = rst.getString("Note");
					if (!note.trim().equals("")) {
						memosForm.setGal(note);
						memosForm.setCheckboxGAL(true);
					} else {
						memosForm.setCheckboxGAL(false);
					}
				}

				if (appMemoCode.equalsIgnoreCase("MDI  ")) {
					String note = rst.getString("Note");
					if (!note.trim().equals("")) {
						memosForm.setMdi(note);
						memosForm.setCheckboxMDI(true);
					} else {
						memosForm.setCheckboxMDI(false);
					}
				}

				if (appMemoCode.equalsIgnoreCase("MOI  ")) {
					String note = rst.getString("Note");
					if (!note.trim().equals("")) {
						memosForm.setMoi(note);
						memosForm.setCheckboxMOI(true);
					} else {
						memosForm.setCheckboxMOI(false);
					}
				}

				if (appMemoCode.equalsIgnoreCase("MMI  ")) {
					String note = rst.getString("Note");
					if (!note.trim().equals("")) {
						memosForm.setMmi(note);
						memosForm.setCheckboxMMI(true);
					} else {
						memosForm.setCheckboxMMI(false);
					}
				}

				if (appMemoCode.equalsIgnoreCase("SIA  ")) {
					String note = rst.getString("Note");
					if (!note.trim().equals("")) {
						memosForm.setSia(note);
						memosForm.setCheckboxSIA(true);
					} else {
						memosForm.setCheckboxSIA(false);
					}
				}

				if (appMemoCode.equalsIgnoreCase("SIAD ")) {
					String note = rst.getString("Note");
					if (!note.trim().equals("")) {
						memosForm.setSiad(note);
						memosForm.setCheckboxSIAD(true);
					} else {
						memosForm.setCheckboxSIAD(false);
					}
				}

				if (appMemoCode.equalsIgnoreCase("SIAG ")) {
					String note = rst.getString("Note");
					if (!note.trim().equals("")) {
						memosForm.setSiag(note);
						memosForm.setCheckboxSIAG(true);
					} else {
						memosForm.setCheckboxSIAG(false);
					}
				}

				if (appMemoCode.equalsIgnoreCase("MAOT ")) {
					String note = rst.getString("Note");
					if (!note.trim().equals("")) {
						memosForm.setMaot(note);
						memosForm.setCheckboxMAOT(true);
					} else {
						memosForm.setCheckboxMAOT(false);
					}
				}

			}
		} catch (Exception ex) {
			log.error("loadMemosAmendForm failed ", ex);
			log.error(ex.getMessage());
		} finally {
			Connect.closeResultSet(rst);
			Connect.closePSMT(psmt);
			Connect.closeConnection(conn);
		}
		ctx.session().setAttribute("MemosAmendForm", memosForm);
		log.debug("End loadMemosAmendForm()");
	}

	/**
	 * Load Shared Info Form
	 * @param ctx
	 * @throws Exception
	 */
	public void loadSharedInfoForm(ControlActionContext ctx) throws Exception {
		log.debug("Begin loadSharedInfoForm()");
		Connection conn = null;
		PreparedStatement psmt = null;
		SharedInfoForm sharedInfoForm = new SharedInfoForm();
		ResultSet rst = null;
		try {

			conn = Connect.getConnection();

			String wid = (String) ctx.request().getSession().getAttribute("wid");
			User user = (User) ctx.session().getAttribute(com.epm.acmi.struts.Constants.loggedUser);

			EPMHelper epmHelper = new EPMHelper();
			String gfid = epmHelper.getGFIDFromWorkId(wid, user);

			String sql = " select * from IUAAppMemos WITH (NOLOCK) where GFID = ? ";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, gfid);

			rst = psmt.executeQuery();
			while (rst.next()) {
				String appMemoCode = rst.getString("MemoCode");
				if (appMemoCode.equalsIgnoreCase("RCA  ")) {
					String note = rst.getString("Note");
					if (!note.trim().equals("")) {
						sharedInfoForm.setRca(note);
						sharedInfoForm.setCheckboxRCA(true);
					} else {
						sharedInfoForm.setCheckboxRCA(false);
					}
				}

				if (appMemoCode.equalsIgnoreCase("ADEC ")) {
					String note = rst.getString("Note");
					if (!note.trim().equals("")) {
						sharedInfoForm.setAdec(note);
						sharedInfoForm.setCheckboxADEC(true);
					} else {
						sharedInfoForm.setCheckboxADEC(false);
					}
				}

				if (appMemoCode.equalsIgnoreCase("RPR  ")) {
					String note = rst.getString("Note");
					if (!note.trim().equals("")) {
						sharedInfoForm.setRpr(note);
						sharedInfoForm.setCheckboxRPR(true);
					} else {
						sharedInfoForm.setCheckboxRPR(false);
					}
				}

				if (appMemoCode.equalsIgnoreCase("DNP  ")) {
					String note = rst.getString("Note");
					if (!note.trim().equals("")) {
						sharedInfoForm.setDnp(note);
						sharedInfoForm.setCheckboxDNP(true);
					} else {
						sharedInfoForm.setCheckboxDNP(false);
					}
				}

				if (appMemoCode.equalsIgnoreCase("SIOT ")) {
					String note = rst.getString("Note");
					if (!note.trim().equals("")) {
						sharedInfoForm.setSiot(note);
						sharedInfoForm.setCheckboxSIOT(true);
					} else {
						sharedInfoForm.setCheckboxSIOT(false);
					}
				}

			}

		} catch (Exception ex) {
			log.error("loadSharedInfoForm failed ", ex);
			log.error(ex.getMessage());
		} finally {
			Connect.closeResultSet(rst);
			Connect.closePSMT(psmt);
			Connect.closeConnection(conn);
		}
		ctx.session().setAttribute("SharedInfoForm", sharedInfoForm);
		log.debug("End loadSharedInfoForm()");
	}


	/**
	 * Load Case Notes Form
	 * @param ctx
	 * @throws Exception
	 */
	public void loadCaseNotesForm(ControlActionContext ctx) throws Exception {
		log.debug("Begin loadCaseNotesForm()");
		Connection conn = null;
		PreparedStatement psmt = null;
		CaseNotesForm caseNotesForm = new CaseNotesForm();
		ResultSet rst = null;
		try {
			conn = Connect.getConnection();
			String wid = (String) ctx.request().getSession().getAttribute("wid");
			User user = (User) ctx.session().getAttribute(com.epm.acmi.struts.Constants.loggedUser);

			EPMHelper epmHelper = new EPMHelper();
			String gfid = epmHelper.getGFIDFromWorkId(wid, user);

			String sql = " select * from IUACaseNotes WITH (NOLOCK) where GFID = ? ORDER BY CaseId";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, gfid);
			rst = psmt.executeQuery();
			StringBuffer previouNotes = new StringBuffer();

			String format = "M/d/yyyy hh:mm a";
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);

			while (rst.next()) {
				Timestamp toFormatTime = rst.getTimestamp("LastUpdatedDateTime");

				previouNotes.append(rst.getString("LastUpdatedUserId"));
				previouNotes.append(" : " + sdf.format(toFormatTime));
				previouNotes.append(" : Sub : " + rst.getString("subject"));
				previouNotes.append(" : - " + rst.getString("message"));
				previouNotes.append("\n\n");
			}
			caseNotesForm.setPreviousNotes(previouNotes.toString());
			ctx.session().setAttribute("CaseNotesForm", caseNotesForm);
		} catch (Exception ex) {
			log.error("loadCaseNotesForm failed ", ex);
			log.error(ex.getMessage());
		} finally {
			Connect.closeResultSet(rst);
			Connect.closePSMT(psmt);
			Connect.closeConnection(conn);
		}
		log.debug("End loadCaseNotesForm()");
	}


	/**
	 * Load UploadDocumentForm
	 * @param ctx
	 * @throws Exception
	 */
	public void loadUploadDocumentForm(ControlActionContext ctx) throws Exception {
		log.debug("Begin loadUploadDocumentForm()");
		String GFID = (String) ctx.session().getAttribute(Constants.gfid);
		String docCode = (String) ctx.session().getAttribute("docCode");
		if (docCode == null) {
			DocumentMetaDataForm dmf = new DocumentMetaDataForm();
			ctx.request().removeAttribute("docUpdateMetaDataForm");
			loadApplicationDetails(dmf,GFID);
			ctx.session().setAttribute(Constants.appdata, dmf);
		}
		if (docCode !=null){
			DocumentMetaDataForm dmf = null;
			Object obj = ctx.session().getAttribute(Constants.appdata);
			if (null != obj){
				dmf = (DocumentMetaDataForm )obj;
			} else
			{
				dmf = new DocumentMetaDataForm();
			}

			String docType = ACMICache.getDocType(docCode);

			dmf.setGfid(GFID);
			dmf.setDocCode(docCode);
			dmf.setDocType(docType);
			//
			ctx.request().setAttribute("docUpdateMetaDataForm", dmf);
		}
		log.debug("End loadUploadDocumentForm()");

	}

	/**
	 * Load Applicaiton Details from IUAApplication table in ACMI database
	 * @param dmf
	 * @param gfid
	 */
	private void loadApplicationDetails(DocumentMetaDataForm dmf, String gfid)
	{
		Connection conn = null;
		ResultSet rst = null;
		PreparedStatement psmt = null;
		log.debug(" loadApplicationDetails current gfid " + gfid);

		try {

			conn = Connect.getConnection();
			String sql = APPLICATION_BY_GFID;
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, gfid);
			rst = psmt.executeQuery();
			while (rst.next()) {

				dmf.setKeyAppSuffix(rst.getString(Constants.keyApplicantSuffix));
				dmf.setKeyAppFirstName(rst.getString(Constants.keyApplicantFirstName));
				dmf.setKeyAppLastName(rst.getString(Constants.keyApplicantLastName));
				dmf.setKeyAppMiddle(rst.getString(Constants.keyApplicantMI));
				dmf.setLbid(rst.getString("IASListBill"));
				dmf.setAgentName(rst.getString(Constants.agentName));
				dmf.setAgentNumber(rst.getString(Constants.agentNumber));
				String  plnum = rst.getString("PolicyNo");
				if (plnum != null) plnum= plnum.trim();
				dmf.setPolicyNumber(plnum);
				dmf.setState(rst.getString(Constants.state));
				dmf.setProduct(rst.getString("Product"));
			}
			rst.close();
		} catch (Exception ex) {
			log.error("loadNBWSDetailsForm failed ", ex);
		} finally {
			Connect.closePSMT(psmt);
			Connect.closeConnection(conn);
		}
	}

	/**
	 * loads NBWSDetails Form
	 * @param ctx
	 * @throws Exception
	 */
	public void loadNBWSDetailsForm(ActionContext ctx) throws Exception {
		log.debug("Begin loadNBWSDetailsForm()");

		NBWSDetailsForm nbwsDetailsForm = new NBWSDetailsForm();

		String gfid = (String) ctx.request().getSession().getAttribute(com.epm.acmi.struts.Constants.gfid);
		Connection conn = null;
		ResultSet rst = null;
		PreparedStatement psmt = null;
		String pastActivities = null;
		EPMHelper epmHelper = null;
		WFSession wfSession = null;
		
		log.debug(" loadNBWSDetailsForm current gfid " + gfid);

		try {
			conn = Connect.getConnection();
			String sql = APPLICATION_BY_GFID;
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, gfid);
			rst = psmt.executeQuery();
			//USR 7930
			while (rst.next()) {
				if(gfid.startsWith("E")||gfid.startsWith("e") ){
					log.debug("Electronics App="+gfid);
					nbwsDetailsForm.setElectronicApp("Electronic Application");
				}
			//USR 7930
				nbwsDetailsForm.setKeyApplicantSuffix(rst.getString(Constants.keyApplicantSuffix));
				nbwsDetailsForm.setKeyApplicantFirstName(rst.getString(Constants.keyApplicantFirstName));
				nbwsDetailsForm.setKeyApplicantLastName(rst.getString(Constants.keyApplicantLastName));
				nbwsDetailsForm.setKeyApplicantMI(rst.getString(Constants.keyApplicantMI));
				nbwsDetailsForm.setRewrite(rst.getString(Constants.Rewrite));
				// Setting key APP name in session. Displayed on Details page
				ctx.session().setAttribute(Constants.keyAppFN, rst.getString(Constants.keyApplicantFirstName) + " " + rst.getString(Constants.keyApplicantLastName));

				nbwsDetailsForm.setEft(rst.getString(Constants.eft));
				nbwsDetailsForm.setAgentName(rst.getString(Constants.agentName));
				nbwsDetailsForm.setAgentNumber(rst.getString(Constants.agentNumber));
				nbwsDetailsForm.setPolicyNumber(rst.getString(Constants.policyNumber).trim());

				// Setting value of Policy Number in session - used to display
				// on the New business work sheet Tab
				ctx.session().setAttribute(Constants.policyNumber, rst.getString(Constants.policyNumber));

				nbwsDetailsForm.setListBill(rst.getString(Constants.listBill));
				nbwsDetailsForm.setReceivedDateString(this.convertDateToString(rst.getDate(Constants.receivedDateString)));
				nbwsDetailsForm.setUwInitail(rst.getString(Constants.uwInitail));
				nbwsDetailsForm.setShortTermPolicy(rst.getString(Constants.shortTermPolicy));
				nbwsDetailsForm.setScreenDateString(this.convertDateToString(rst.getDate(Constants.screenDateString)));
				nbwsDetailsForm.setScreenBy(rst.getString(Constants.screenBy));
				nbwsDetailsForm.setState(rst.getString(Constants.state));
				nbwsDetailsForm.setStatus(rst.getString(Constants.status));
				nbwsDetailsForm.setProductName(rst.getString(Constants.productName));
				nbwsDetailsForm.setPharmaProfile(rst.getString(Constants.pharmaProfile));
				nbwsDetailsForm.setNoResultsFound(rst.getString(Constants.noResultsFound));
				nbwsDetailsForm.setReasonCode(rst.getString(Constants.reasonCode));
				nbwsDetailsForm.setReturnCode(rst.getString(Constants.returnCode));
				nbwsDetailsForm.setPlanId(rst.getString(Constants.planId));
				nbwsDetailsForm.setNetwork(rst.getString(Constants.network));
				nbwsDetailsForm.setDeductible(rst.getString(Constants.deductible));
				nbwsDetailsForm.setCoInsurance(rst.getString(Constants.coInsurance));
				nbwsDetailsForm.setCoPay(rst.getString(Constants.coPay));
				nbwsDetailsForm.setFundedHSA(rst.getString(Constants.fundedHSA));
				nbwsDetailsForm.setLastUpdatedDateTime(rst.getTimestamp(Constants.lastUpdatedDateTime));
				nbwsDetailsForm.setPastActions(rst.getString("PastActions"));
				pastActivities = rst.getString("PastActivities");
			}

			
			log.debug(" start executing ACMI Cache for drop downs");
			
			TreeMap temporaryProductNameMap = ACMICache.getProductMapForListBox(nbwsDetailsForm.getProductName());
						
			nbwsDetailsForm.setStatesMap(ACMICache.getStatesMap());
			nbwsDetailsForm.setStatusMap(ACMICache.getStatusMap());
			nbwsDetailsForm.setProductsMap(temporaryProductNameMap);
			nbwsDetailsForm.setOtsReasonMap(ACMICache.getOtsReasonMap());
			nbwsDetailsForm.setReturnReasonMap(ACMICache.getReturnReasonMap());
			nbwsDetailsForm.setUnableToCompleteReasonMap(ACMICache.getUnableToCompleteReasonMap());

			log.debug(" finished executing ACMI Cache for drop downs");

			if (nbwsDetailsForm.getFundedHSA().equalsIgnoreCase("Y")) {
				log.debug("form loader nbwsDetailsForm.getFundedHSA() y ");
				ctx.session().setAttribute("showFundedHSASession", SHOW);
				nbwsDetailsForm.setFundedHSAboolean(true);
			} else {
				ctx.session().setAttribute("showFundedHSASession", null);
				log.debug("form loader nbwsDetailsForm.getFundedHSA() n ");
				nbwsDetailsForm.setFundedHSAboolean(false);
			}

			// Get the user defined attributes
			String workIdString = (String) ctx.request().getSession().getAttribute(com.epm.acmi.struts.Constants.workItemId);
			long workIdlong = -1;
			if (workIdString != null) {
				workIdlong = Long.parseLong(workIdString);
			}
						
			User user = (User) ctx.session().getAttribute(com.epm.acmi.struts.Constants.loggedUser);
			
			epmHelper = new EPMHelper();
			wfSession = epmHelper.connectAsUser(user.getUserId(), user.getPassword());
			
			WorkItem wi = null;

			ctx.session().setAttribute(Constants.SHOW_ADDITIONAL_INFO_UDAS_SESSION, null);
			ctx.session().setAttribute(Constants.SHOW_DECLINE_TEMPLATE_NUMBER_OF_DAYS, null);
			ctx.session().setAttribute(Constants.SHOW_MANUALLY_STARTED_SESSION, null);
			ctx.session().setAttribute(Constants.SHOW_RECORD_INCOMING_MEMO_SESSION, null);
			ctx.session().setAttribute(Constants.SHOW_RECORD_INCOMING_EXAM_SESSION, null);
			ctx.session().setAttribute(Constants.SHOW_RECORD_INCOMING_MEDREC_SESSION, null);
			ctx.session().setAttribute(Constants.SHOW_RECORD_INCOMING_QUOTE_SESSION, null);
			ctx.session().setAttribute(Constants.SHOW_OTHER_PROC_EXISTS_SESSION, null);
			ctx.session().setAttribute(Constants.SHOW_NURSE_CONSULTANT, null);
			ctx.session().setAttribute(Constants.SHOW_INTERVIEWEE_NAME, null);
			ctx.session().setAttribute(Constants.SHOW_UNABLE_TO_COMPLETE_REASON, null);			
			ctx.session().setAttribute(Constants.SHOW_SCHEDULED_DATE_TIME, null);			
			ctx.session().setAttribute(Constants.ENABLE_NURSE_CONSULTANT, null);			
			ctx.session().setAttribute(Constants.ENABLE_UNABLE_TO_COMPLETE_REASON, null);				
			ctx.session().setAttribute(Constants.SHOW_AND_ENABLE_FOLLOW_UP_DATE_AND_TIME, null);

			if (workIdlong != -1) {

				wi = WFObjectFactory.getWorkItem(workIdlong, wfSession);
				log.debug(" the work item name is " + wi.getName());
				String processName = wi.getProcessInstance().getName();		
								
				if (MiscellaneousUtils.isInterviewPlanName(wi.getPlanName()))
					fillInterviewProperties(ctx, user, nbwsDetailsForm, wi);
				
				// Fix for issue #87 04/13/2006 cmontero
				if (processName.endsWith("DMV")) {
					ctx.session().setAttribute("showDRConsult", null);
				} else {
					ctx.session().setAttribute("showDRConsult", SHOW);
				}
				log.debug(" the work item - process name is  " + processName);
				// Setting value of task name in session - used to display on
				// the New business work sheet Tab
				ctx.session().setAttribute(Constants.taskName, wi.getName());
				nbwsDetailsForm.setTaskName(wi.getName());

				if (wi.getName().indexOf(Constants.enterApplication) >= 0 ||
						wi.getName().indexOf(Constants.screenApplication) >= 0 ||
						wi.getName().indexOf(Constants.initiateRXQ) >= 0 ||
						wi.getName().indexOf(Constants.reviewApp) >= 0)
				{

					if (pastActivities == null) {
						pastActivities = wi.getName();
					}
					else if (pastActivities.indexOf(wi.getName()) == -1 ) {
					// if it does not contain the current activity, add it

						pastActivities = pastActivities + ", " + wi.getName();
					}
				}
				nbwsDetailsForm.setPastActivities(pastActivities);


				String choices[] = epmHelper.getChoices(workIdlong, ctx.request());
				// String choices[] = getChoices(workIdlong, ctx.request());
				if (choices != null) {
					for (int i = 0; i < choices.length; i++) {
						if (choices[i].trim().equalsIgnoreCase(Constants.addlInfo)) {
							ctx.session().setAttribute(Constants.SHOW_ADDITIONAL_INFO_UDAS_SESSION, "notNullString");
							// }

							com.fujitsu.iflow.model.workflow.DataItem[] di = wi.getProcessInstance().getDataItems();
							for (int j = 0; j < di.length; j++) {
								if (di[j].getName().equalsIgnoreCase(Constants.memoAttachReqd)) {
									if (di[j].getValue().equals(ONE)) {
										nbwsDetailsForm.setMemoAttachReqd(true);
										nbwsDetailsForm.setMemoAttachedYesNo("Yes");
									} else {
										nbwsDetailsForm.setMemoAttachReqd(false);
										// nbwsDetailsForm.setMemoAttachedYesNo("No");
									}
								}
								if (di[j].getName().equalsIgnoreCase(Constants.memoNoAttachReqd)) {
									if (di[j].getValue().equals(ONE)) {
										nbwsDetailsForm.setMemoNoAttachReqd(true);
										nbwsDetailsForm.setMemoAttachedYesNo("No");
									} else {
										nbwsDetailsForm.setMemoNoAttachReqd(false);
									}
								}
								if (di[j].getName().equalsIgnoreCase(Constants.paraMedReqd)) {
									if (di[j].getValue().equals(ONE)) {
										nbwsDetailsForm.setParaMedReqd(true);
									} else {
										nbwsDetailsForm.setParaMedReqd(false);
									}
								}
								if (di[j].getName().equalsIgnoreCase(Constants.dmvReqd)) {
									if (di[j].getValue().equals(ONE)) {
										nbwsDetailsForm.setDmvReqd(true);
									} else {
										nbwsDetailsForm.setDmvReqd(false);
									}
								}
								if (di[j].getName().equalsIgnoreCase(Constants.medRecReqd)) {
									if (di[j].getValue().equals(ONE)) {
										nbwsDetailsForm.setMedRecReqd(true);
									} else {
										nbwsDetailsForm.setMedRecReqd(false);
									}
								}
								if (di[j].getName().equalsIgnoreCase(Constants.medConsultReqd)) {
									if (di[j].getValue().equals(ONE)) {
										nbwsDetailsForm.setMedConsultReqd(true);
									} else {
										nbwsDetailsForm.setMedConsultReqd(false);
									}
								}
								if (di[j].getName().equalsIgnoreCase(Constants.declineIndividual)) {
									if (di[j].getValue().equals(ONE)) {
										nbwsDetailsForm.setDeclineIndividual(true);
									} else {
										nbwsDetailsForm.setDeclineIndividual(false);
									}
								}
								// repeat the manually started as there might be manually started in Adl Info also
								if (di[j].getName().equalsIgnoreCase(Constants.manuallyStarted)) {
									if (di[j].getValue().equals(ONE)) {
										nbwsDetailsForm.setManuallyStarted(true);
										ctx.session().setAttribute(Constants.SHOW_MANUALLY_STARTED_SESSION, SHOW);
									}
								}
								if (di[j].getName().equalsIgnoreCase(Constants.openProcessesExist)) {
									if (di[j].getValue().equals(ONE)) {
										nbwsDetailsForm.setManuallyStarted(true);
										ctx.session().setAttribute(Constants.SHOW_OTHER_PROC_EXISTS_SESSION, SHOW);
									}
								}
								if (di[j].getName().equals(Constants.PHONE_INTERVIEW_REQD)) {
									String value = di[j].getValue();
									nbwsDetailsForm.setPhoneInterviewRequired(ONE.equals(value));					
								}
							}
						} else {
							// not belong to addl info - the manually started
							// UDA
							com.fujitsu.iflow.model.workflow.DataItem[] di = wi.getProcessInstance().getDataItems();
							for (int j = 0; j < di.length; j++) {
								if (di[j].getName().equalsIgnoreCase(Constants.manuallyStarted)) {
									if (di[j].getValue().equals(ONE)) {
										nbwsDetailsForm.setManuallyStarted(true);
										ctx.session().setAttribute(Constants.SHOW_MANUALLY_STARTED_SESSION, SHOW);
									}
								}

								if (di[j].getName().equalsIgnoreCase(Constants.openProcessesExist)) {
									if (di[j].getValue().equals(ONE)) {
										nbwsDetailsForm.setManuallyStarted(true);
										ctx.session().setAttribute(Constants.SHOW_OTHER_PROC_EXISTS_SESSION, SHOW);
									}
								}
								
								// Three UDAs - if proc Name contains Memo / Exam / Medrec and work Item Name contains
								// Record Incoming
								if (wi.getName().matches(("(?i).*" + Constants.recordIncomingDocPrefix + ".*"))) {
									//First UDA
									if (wi.getProcessInstance().getName().matches("(?i).*" + Constants.epmCode_memo + ".*")) {
										if (di[j].getName().equalsIgnoreCase(Constants.Waiting_on_addl_memo)) {
											ctx.session().setAttribute(Constants.SHOW_RECORD_INCOMING_MEMO_SESSION, SHOW);
											if (di[j].getValue().equals(ONE)) {
												nbwsDetailsForm.setRecordIncomingMEMO(true);
											} else {
												nbwsDetailsForm.setRecordIncomingMEMO(false);
											}

										}
									}// End First UDA
									
									// Second UDA									
									if (wi.getProcessInstance().getName().matches("(?i).*" + Constants.epmCode_medRec + ".*")) {
										if (di[j].getName().equalsIgnoreCase(Constants.Waiting_on_addl_med_rec)) {
											ctx.session().setAttribute(Constants.SHOW_RECORD_INCOMING_MEDREC_SESSION, SHOW);
											if (di[j].getValue().equals(ONE)) {
												nbwsDetailsForm.setRecordIncomingMEDREC(true);
											} else {
												nbwsDetailsForm.setRecordIncomingMEDREC(false);
											}

										}
									} // End Second UDA

								}
								// End 2 UDAs logic
								
								//USR 8399-1
								// Two UDAs - if proc Name contains Exam / Quote and work Item Name contains
								// Review Incoming
								if (wi.getName().matches(("(?i).*" + Constants.reviewIncomingDocPrefix + ".*"))) {
									// First UDA
									if (wi.getProcessInstance().getName().matches("(?i).*" + Constants.epmCode_exam + ".*")) {
										if (di[j].getName().equalsIgnoreCase(Constants.Waiting_on_addl_paramed_exam)) {
											ctx.session().setAttribute(Constants.SHOW_RECORD_INCOMING_EXAM_SESSION, SHOW);
											nbwsDetailsForm.setRecordIncomingEXAM(false);
										}
									} // End First UDA
									
									
									//Second UDA
									if (wi.getProcessInstance().getName().matches("(?i).*" + Constants.epmCode_quote + ".*")) {
										if (di[j].getName().equalsIgnoreCase(Constants.Waiting_on_addl_quote)) {
											ctx.session().setAttribute(Constants.SHOW_RECORD_INCOMING_QUOTE_SESSION, SHOW);
											if (di[j].getValue().equals(ONE)) {
												nbwsDetailsForm.setRecordIncomingMEDREC(true);
											} else {
												nbwsDetailsForm.setRecordIncomingMEDREC(false);
											}

										}
									}//End Second UDA
								}		
								
								//USR 8399-1
							}
						}
					}
				}
			}
						
			// end getting User Defined Attributes

			//Set visibility/editability of Phone Interview data in the Details page.
			if (MiscellaneousUtils.comesFromATelephonicInterviewProcess(wi, wfSession))
			{
				//Show process-level data for telephonic interviews
				ctx.session().setAttribute(Constants.SHOW_NURSE_CONSULTANT, SHOW);
				ctx.session().setAttribute(Constants.SHOW_INTERVIEWEE_NAME, SHOW);
				ctx.session().setAttribute(Constants.SHOW_UNABLE_TO_COMPLETE_REASON, SHOW);
				
				//Handle Editing/Not Editing of Data for above fields.
				if(shouldEnableNurseConsultant(wi))
					ctx.session().setAttribute(Constants.ENABLE_NURSE_CONSULTANT, SHOW);
																						
				if(shouldEnableUnableToCompleteReason(wi))
					ctx.session().setAttribute(Constants.ENABLE_UNABLE_TO_COMPLETE_REASON, SHOW);
				
				//Handle Follow Up Date and Time
				if(shouldEnableFollowUpDateAndTime(wi))
					ctx.session().setAttribute(Constants.SHOW_AND_ENABLE_FOLLOW_UP_DATE_AND_TIME, SHOW);
				
				if(shouldEnableScheduledDateAndTime(wi))
					ctx.session().setAttribute(Constants.SHOW_SCHEDULED_DATE_TIME, SHOW);
			}
			
			// Get the numberOfDaysRefund for ACMI Decline Template only if Work Item name is Process D/I/W Letter
			if (wi != null) {
				if (wi.getName().trim().equalsIgnoreCase(Constants.processDIW)) {
					ctx.session().setAttribute(Constants.SHOW_DECLINE_TEMPLATE_NUMBER_OF_DAYS, SHOW);
					com.fujitsu.iflow.model.workflow.DataItem[] di = wi.getProcessInstance().getDataItems();
					for (int j = 0; j < di.length; j++) {
						if (di[j].getName().equalsIgnoreCase(Constants.numberOfDaysRefund)) {
							nbwsDetailsForm.setNumberOfDaysRefund(di[j].getValue());
						}
					}
				}
				// }
			}
			// closing result Set

		} catch (Exception ex) {
			log.error("loadNBWSDetailsForm failed ", ex);
		} finally {
			Connect.closeResultSet(rst);
			Connect.closePSMT(psmt);
			Connect.closeConnection(conn);
			
			try
			{
				if ((epmHelper != null) && (wfSession != null))
					epmHelper.disconnect(wfSession);
			} catch (Exception e)
			{
				log.error("Exception while trying to disconnect from EPM Server", e);
			}
		}
		// end acmi decline
		log.debug(" before leaving formloader save - " + (String) ctx.session().getAttribute("showFundedHSASession"));
		log.debug(" End loadNBWSDetailsForm()");
		ctx.session().setAttribute("NBWSDetailsForm", nbwsDetailsForm);
	}

	
	private static Map getRoles(String roleStr) {
		Map roles = new HashMap();
		
		String roleListStr = roleStr.substring(1, roleStr.length() - 1);
		log.debug("substring: " + roleListStr);
		StringTokenizer tok = new StringTokenizer(roleListStr, COMMA);
		
		while (tok.hasMoreTokens())
		{
			String element = tok.nextToken().trim();
			roles.put(element, element);
		}
		
		log.debug("role map: " + roles);
		return roles;
	}

	/**
	 * Fill interview-related details form properties from data in
	 * follow up interview record, if any...
	 * 
	 * @param detailsForm
	 * @param pid
	 */
	private void fillInterviewProperties(ActionContext ctx, User user, NBWSDetailsForm detailsForm, WorkItem wi) throws Exception {
		ProcessInstance processIns = wi.getProcessInstance();
		

		WFAdminSession wfAdminSession = null;
        
        try {
        	EPMHelper epmHelper = new EPMHelper();
        	wfAdminSession = epmHelper.connectAsAdminFromPropertiesFile();
        	long topInterviewProcessId = MiscellaneousUtils.getTopInterviewProcessId(processIns.getId(), wfAdminSession);
			ProcessInstance topInterviewProcInstance = epmHelper.getProcessInstance(topInterviewProcessId, wfAdminSession);
			String topInterviewTemplateName = topInterviewProcInstance.getPlanName();
			
			DataItem[] di = processIns.getDataItems();
			
			detailsForm.setNurseConsultantList(IUAUtils.getUsersByRole(NURSE_CONSULTANT));
			IuaappDocProcessesDAO processDAO = getProcessDAO();
			IuaappDocProcesses process = processDAO.findById(new Long(processIns.getId()));
				
			if ((process == null) || (!MiscellaneousUtils.isInterviewProcessTypeDB(process.getTypeCode())))
			{
				log.debug("Process is not an interview process");
				return;
			}
			
			long thisInterviewProcessId = process.getPid().longValue();
			Iterator phoneInterviews = process.getIuatelephonicInterviews().iterator();
			
			IuatelephonicInterview phoneInterviewForProcess = null;
			
			if (phoneInterviews.hasNext())
			{
				//There is only one phone interview per interview process.
				phoneInterviewForProcess = (IuatelephonicInterview)phoneInterviews.next();
				IuaunableToCompleteReasonCode reasonCode = phoneInterviewForProcess.getIuaunableToCompleteReasonCode();
				
				if (reasonCode != null)
					detailsForm.setUnableToCompleteReason(reasonCode.getCode());
				else if (topInterviewProcessId != thisInterviewProcessId)
				{
					IuaappDocProcesses topInterviewProcess = processDAO.findById(new Long(topInterviewProcessId));
					Iterator topPhoneInterviews = topInterviewProcess.getIuatelephonicInterviews().iterator();
					
					if (topPhoneInterviews.hasNext())
					{
						IuatelephonicInterview topPhoneInterview = (IuatelephonicInterview)topPhoneInterviews.next();
						reasonCode = topPhoneInterview.getIuaunableToCompleteReasonCode();
						
						if (reasonCode != null)
							detailsForm.setUnableToCompleteReason(reasonCode.getCode());
					}
				}
				
				IuainterviewRequest interviewRequest = phoneInterviewForProcess.getIuainterviewRequest();
				Iuauser nurseConsultant = interviewRequest.getIuauserByInitialInterviewNc();
				
				if (nurseConsultant != null)
				{								
					detailsForm.setInitialInterviewNurseConsultant(nurseConsultant.getEmployeename());
				}
				else
				{
					if (Constants.EPM_ACTIVITY_CONDUCT_INITIAL_INTERVIEW.equals(wi.getName()))
					{
						StringBuffer employeeName = new StringBuffer();
						employeeName.append(user.getFirstName());
						employeeName.append(SPACE);
						employeeName.append(user.getLastName());	
						detailsForm.setInitialInterviewNurseConsultant(employeeName.toString());
					}
					else
						detailsForm.setInitialInterviewNurseConsultant(NULL_STRING);
				}
				
				detailsForm.setIntervieweeName(interviewRequest.getApplicantName());
				
				detailsForm.setFollowUpDateString(NULL_STRING);
				detailsForm.setFollowUpTimeString(NULL_STRING);

				if (Constants.EPM_CODE_FOLLOW_UP_INTERVIEW.equals(topInterviewTemplateName))
				{
					detailsForm.setInterviewScheduledDateTimeString(getInterviewScheduledDateTimeString(phoneInterviewForProcess.getScheduledDateTime()));
				}
				
				IuatelephonicInterview followUpInterview = IUAProcessBD.getExistingFollowUpInterview(interviewRequest);
				
				if (followUpInterview != null)
				{				
					//The rest of the interview data is for scheduling the next interview			
					Date followUpDateTime = followUpInterview.getScheduledDateTime();
					
					if (followUpDateTime != null)
					{
						detailsForm.setFollowUpDateString(dateFormat.format(followUpDateTime));
						detailsForm.setFollowUpTimeString(timeFormat.format(followUpDateTime));		
					} 
				} else {
					for (int i=0; i < di.length; i++) {
						DataItem item = di[i];
						if (item.getName().equals(Constants.FOLLOW_UP_DATE_TIME)) {
							String value = item.getValue();
							
							try {
								long longVal = Long.parseLong(value);
								
								if (longVal > -1) {
									Date followUpDateTime = new Date(longVal);
									detailsForm.setFollowUpDateString(dateFormat.format(followUpDateTime));
									detailsForm.setFollowUpTimeString(timeFormat.format(followUpDateTime));
								}
							} catch (NumberFormatException e) {
								//This is OK, the follow-up date might not be set yet. or was set to -1 in UDA.
								log.error("Error Parsing FollowUp DateTime received from UDA", e);
							}
						
						}
					}
				}				
			}
		} finally
		{
			BaseHibernateDAO.close();
			
			if (wfAdminSession != null)
				wfAdminSession.logOut();
		}
	}
	
	private String getInterviewScheduledDateTimeString(Date scheduledDateTime) {
		String dateString = dateFormat.format(scheduledDateTime);
		String timeString = timeFormat.format(scheduledDateTime);
		StringBuffer result = new StringBuffer();
		result.append(SCHEDULED_FOLLOW_UP_STRING);
		result.append(SPACE );
		result.append(dateString);
		result.append(SPACE);
		result.append(timeString);
		return result.toString();
	}

	protected static IuatelephonicInterviewDAO getPhoneInterviewDAO()
	{
		if (phoneInterviewDAO == null)
		{
			synchronized(lock)
			{
				if (phoneInterviewDAO == null)
				{
					phoneInterviewDAO = new IuatelephonicInterviewDAO();
				}
			}
		} 
		
		return phoneInterviewDAO;
	}

	protected static IuaappDocProcessesDAO getProcessDAO() {
		if (processDAO == null)
		{
			synchronized(lock)
			{
				if (processDAO == null)
				{
					processDAO = new IuaappDocProcessesDAO();
				}
			}
		} 
		
		return processDAO;
	}

	/**
	 * @param wi
	 * @return
	 */
	private boolean shouldEnableFollowUpDateAndTime(WorkItem wi) {
		return MiscellaneousUtils.isNurseConsultantActivity(wi.getName());
	}

	/**
	 * @param wi
	 * @return
	 */
	private boolean shouldEnableScheduledDateAndTime(WorkItem wi) {
		if (MiscellaneousUtils.isNurseConsultantActivity(wi.getName()))
		{
			WFAdminSession wfAdminSession = null;
			
			try
			{
				EPMHelper epmHelper = new EPMHelper();
				wfAdminSession = epmHelper.connectAsAdminFromPropertiesFile();
				long topInterviewProcessInstanceId = MiscellaneousUtils.getTopInterviewProcessId(wi.getProcessInstanceId(), wfAdminSession);
				
				return (topInterviewProcessInstanceId > -1);
			} catch (Exception e)
			{
				return false;
			} finally
			{
				if (wfAdminSession != null)
				{
					try
					{
						wfAdminSession.logOut();
					} catch (Exception e)
					{
						
					}
				}
			}
		} else
			return false;
	}

	/**
	 * @param wi
	 * @return
	 */
	private boolean shouldEnableUnableToCompleteReason(WorkItem wi) {
		return MiscellaneousUtils.isNurseConsultantActivity(wi.getName());
	}

	/**
	 * @param wi
	 * @return
	 */
	private boolean shouldEnableNurseConsultant(WorkItem wi) {
		return false;
		//return (wi.getName().indexOf(Constants.EPM_ACTIVITY_CONDUCT_INITIAL_INTERVIEW) > -1);
	}

	/**
	 * Return Activity Actions
	 * @return String
	 */
	public String getActivityActions() {
		log.debug("Begin getActivityActions()");
		String activityString = "";
		activityString = activityString + "<forms:buttonsection >";
		activityString = activityString
				+ "<forms:button  name=\"btnSave\"    text=\"button.title.save\"    title=\"button.title.save\" /> ";
		activityString = activityString
				+ "<forms:button  name=\"btnReassign\"    text=\"button.title.Reassign\"    title=\"button.title.Reassign\" /> ";
		activityString = activityString
				+ "<forms:button  name=\"btnDecline\"    text=\"button.title.Decline\"    title=\"button.title.Decline\" />";
		activityString = activityString + "	</forms:buttonsection>";
		log.debug(" activitu is " + activityString);
		log.debug("End getActivityActions()");
		return activityString;
	}


	/**
	 * Convert Date to String (local util method)
	 * @param date
	 * @return
	 */
	private String convertDateToString(Date date) {

		Calendar cal = Calendar.getInstance();
		String retVal;

		if (date == null)
			retVal = "";
		else {
			cal.setTime(date);
			retVal = "" + (cal.get(Calendar.MONTH) + 1);
			retVal += "/";
			retVal += cal.get(Calendar.DATE);
			retVal += "/";
			retVal += cal.get(Calendar.YEAR);
		}

		return retVal;
	}
}
