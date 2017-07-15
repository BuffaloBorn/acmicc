package com.epm.acmi.struts.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Query;

import com.cc.acmi.common.User;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.FormType;
import com.cc.framework.ui.ImageMap;
import com.cc.framework.ui.OrientationType;
import com.cc.framework.ui.control.FormButtonContainer;
import com.cc.framework.ui.control.FormButtonElement;
import com.cc.framework.ui.control.FormControl;
import com.cc.framework.ui.control.FormGroupElement;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.model.ClientEvent;
import com.cc.framework.ui.model.FormDesignModel;
import com.cc.framework.ui.model.imp.FormDesignModelImp;
import com.cc.framework.ui.model.imp.ImageModelImp;
import com.cc.framework.util.ListHelp;
import com.epm.acmi.bd.IUAProcessBD;
import com.epm.acmi.bean.AcmiWorkItemBean;
import com.epm.acmi.cm.et.rules.EPMRules;
import com.epm.acmi.cm.et.rules.EPMRulesImpl;
import com.epm.acmi.datamodel.WorkListList;
import com.epm.acmi.hbm.BaseHibernateDAO;
import com.epm.acmi.hbm.IuaappDocProcesses;
import com.epm.acmi.hbm.IuaappDocProcessesDAO;
import com.epm.acmi.hbm.IuainterviewDispositionResultCode;
import com.epm.acmi.hbm.IuainterviewDispositionResultCodeDAO;
import com.epm.acmi.hbm.IuainterviewRequest;
import com.epm.acmi.hbm.IuainterviewRequestDAO;
import com.epm.acmi.hbm.IuatelephonicInterview;
import com.epm.acmi.hbm.IuatelephonicInterviewDAO;
import com.epm.acmi.hbm.IuaunableToCompleteReasonCode;
import com.epm.acmi.hbm.IuaunableToCompleteReasonCodeDAO;
import com.epm.acmi.hbm.Iuauser;
import com.epm.acmi.hbm.IuauserDAO;
import com.epm.acmi.pdf.WorkSheet;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.NBWSDetailsForm;
import com.epm.acmi.struts.form.dsp.StellentClient;
import com.epm.acmi.util.ACMICache;
import com.epm.acmi.util.Connect;
import com.epm.acmi.util.EPMHelper;
import com.epm.acmi.util.LDAPUser;
import com.epm.acmi.util.MiscellaneousUtils;
import com.fujitsu.iflow.model.workflow.ProcessInstance;
import com.fujitsu.iflow.model.workflow.WFAdminSession;
import com.fujitsu.iflow.model.workflow.WFObjectFactory;
import com.fujitsu.iflow.model.workflow.WFSession;
import com.fujitsu.iflow.model.workflow.WorkItem;

/**
 * Action class for NBWS Details Page
 * 
 * @author Rajeev Chachra
 */

public class NBWSDetailsAction extends MainTabPageBaseAction {
	private static final String MINUS_ONE = "-1";
	private static final String DIVIDER_OR = "|";
	private static final String SPACE = " ";
	private static final String NULL_STRING = "";
	private static final int MAX_UPDATE_ATTEMPTS = 5;
	private static final long INITIAL_WAITING_TIME = 100;
	
	private static Logger log = Logger.getLogger(NBWSDetailsAction.class);
	private static Map workingCtxMap =  Collections.synchronizedMap( new HashMap());
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static SimpleDateFormat interviewDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
	private static SimpleDateFormat interviewDateFormatNoSpace = new SimpleDateFormat("MM/dd/yyyy hh:mmaa");
	
	boolean returnvalue = false;
	//Hibernate DAO's are thread safe, so we can make these static.
	private static IuainterviewDispositionResultCodeDAO interviewDispositionDAO = null;
	private static IuaunableToCompleteReasonCodeDAO unableToCompleteReasonDAO = null;
	private static IuainterviewRequestDAO interviewRequestDAO = null;
	private static IuatelephonicInterviewDAO phoneInterviewDAO = null;	
	private static IuaappDocProcessesDAO processDAO = null;
	private static IuauserDAO userDAO = null;
	
	private static final Object lock = new Object();
	private static Map interviewArrowCodeMap = null;
	
	public NBWSDetailsAction() {
		super();
	}

	/**
	 * Return tab page Id
	 */
	public String getTabPageId() {
		return TABPAGE1;
	}

	/**
	 * @see com.cc.framework.adapter.struts.FrameworkAction#doExecute(com.cc.framework.adapter.struts.ActionContext)
	 */

	/**
	 * This method is called by Controller Servlet. This method is specific to
	 * Common Controls API
	 * 
	 * @param ctx
	 *            ActionContext
	 * @throws java.lang.Exception
	 */
	public void doExecute(ActionContext ctx) {

		try {
			log.debug("Begin doExecute");
			FormControl control = new FormControl();
			control.setDesignModel(getDesignModel(ctx.request()));
			ctx.session().setAttribute("ActivityActions", control);

		} catch (Exception ex) {
			ctx.addGlobalError("error.mainError", NULL_STRING);

		}

		log.debug("End doExecute");
		ctx.forwardToAction("main/secondarytabsetBrowse");

	}

	/**
	 * This Method creates Activity Action Buttons
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @throws Exception
	 */
	protected static FormDesignModel getDesignModel(
			javax.servlet.http.HttpServletRequest request) throws Exception {

		log.debug("Begin FormDesignModel");

		ImageMap imageMap = new ImageMap();
		imageMap.addImage("warning", new ImageModelImp(
				"fw/def/image/severity/imgWarning13x13.gif", 13, 13));
		imageMap.addImage("info", new ImageModelImp(
				"fw/def/image/severity/imgInfo13x13.gif", 13, 13));
		imageMap.addImage("lnkExternal", new ImageModelImp(
				"fw/def/image/severity/lnkExternal.gif", 13, 13));

		FormDesignModel form = new FormDesignModelImp();
		form.setFormId("ActivityActions");
		form.setFormType(FormType.EDIT);
		// form.setCaption("form.caption.edit");
		form.setWidth("850");
		form.setCaption("Activity Actions");

		form.setShowFrame(false);
		form.setImageMap(imageMap);
		form.setLocaleName("false");

		FormGroupElement row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		row.setJoin(true);

		FormButtonContainer buttonsection = new FormButtonContainer();
		// buttonsection.setDefaultButtonId("btnSave");
		Object obj = request.getSession().getAttribute(
				com.epm.acmi.struts.Constants.workItemId);

		long workIdlong = -1;
		if (obj != null) {
			String workIdString = (String) obj;
			workIdlong = Long.parseLong(workIdString);
		}

		if (workIdlong != -1) {
			EPMHelper epmHelper = new EPMHelper();
			String choices[] = epmHelper.getChoices(workIdlong, request);

			int count = 0;

			if (choices != null) {
				for (int i = 0; i < choices.length; i++) {
					FormButtonElement button = new FormButtonElement();
					button.setFilter(true);
					button.setFormElement(false);
					button.setId("btnActivityAction");
					button.setName("btnActivityAction");
					button.setText(choices[i]);
					button.setLocaleName("false");
					button.setTooltip(choices[i]);

					// button.setAction(choices[i]);
					String theButtonClicked = "theButtonClicked('" + choices[i]
							+ "');";
					button.setHandler(ClientEvent.ONCLICK, theButtonClicked);
					buttonsection.addFormElement(button);
					count++;
					if (count % 5 == 0) {
						row.addFormElement(buttonsection);
						form.addFormElement(row);
						row = new FormGroupElement();
						row.setOrientation(OrientationType.HORIZONTAL);
						row.setJoin(true);
						buttonsection = new FormButtonContainer();
					}
				}
			}
		}

		row.addFormElement(buttonsection);
		form.addFormElement(row);

		log.debug("End FormDesignModel");

		return form;
	}

	/**
	 * This Method is called if any of the Activity buttons are pressed on the
	 * HTML-Page. It calls Save - real logic is in Save method
	 * 
	 * @param ctx
	 *            FormActionContext
	 * @throws Exception
	 */
	public void activityAction_onClick(FormActionContext ctx) throws Exception {

		log.debug(" Begin activityAction_onClick ");

		NBWSDetailsForm nbwsDetailsForm = (NBWSDetailsForm) ctx.form();
		String buttonSelected = nbwsDetailsForm.getButtonSelected();

		// Fix for the issue noted during reassign on EnterApplication Activity
		if (null != buttonSelected) {
			if (buttonSelected.equals("reassign")) {
				reassign_onClick(ctx);
			} else {
				save_onClick(ctx);

				if (returnvalue) {
					log.debug("forward to details page");
					ctx
							.forwardToAction("main/iuauser?ctrl=maintabset&action=TabClick&param=tab1");
					returnvalue = false;
				} else
				{
					//Goes to the work list page...
					// make this thread slow down by 1.5 seconds- EPM need to finish its work
					Thread.sleep(1500);
				}
			}
		}
		log.debug("End activityAction_onClick");
	}

	/**
	 * Update EPM Process UDAs - return true only when EPM update failed
	 * 
	 * @param workIdlong
	 * @param nbwsDetailsForm
	 * @param ctx
	 * @param user
	 * @param gfid
	 */
	private boolean updateUDAs(long workIdlong,
			NBWSDetailsForm nbwsDetailsForm, FormActionContext ctx, User user,
			String gfid) {
		boolean canMoveFromActivity = true;
		EPMHelper epmh = null;
		WFSession wfSession = null;
		boolean isEPMupdateSuccessful = false;
		// precondition check
		if (workIdlong == -1) {
			log.debug("workId is not valid " + workIdlong);

		}

		try {
			log.debug("Begin UpdateUDAs()");
			if (null == user) {
				log.debug("User object is null");
			} else {
				log.debug("User " + user);

			}
			log.debug("User Id : " + user.getUserId());
			log.debug("Password is not null");
			epmh = new EPMHelper();
			synchronized (ctx.session()) {
				ctx.session().setAttribute("showAdditionalInfoUDAsSession",
						null);
			}
			log.debug("Logging into EPM");
			wfSession = epmh
					.connectAsUser(user.getUserId(), user.getPassword());

			log.debug("EPM Login is successful");
			ProcessInstance processIns = null;
			Properties p = new Properties();
			WorkItem wi = WFObjectFactory.getWorkItem(workIdlong, wfSession);
			log
					.info("User=" + user.getUserId() + "	Activity="
							+ wi.getName() + "	Activity Action="
							+ nbwsDetailsForm.getButtonSelected());
			processIns = wi.getProcessInstance();

			EPMHelper epmHelper = new EPMHelper();
			String choices[] = epmHelper.getChoices(workIdlong, ctx.request());

			EPMRules epmRules = new EPMRulesImpl();

			if (choices != null) {

				for (int i = 0; i < choices.length; i++) {

					// Update UDAs when choice is equal to Add debug - value as
					// per contant variable
					if (choices[i].trim().equalsIgnoreCase(Constants.addlInfo)) {
						ctx.session().setAttribute(
								"showAdditionalInfoUDAsSession",
								"notNullString");
						com.fujitsu.iflow.model.workflow.DataItem[] di = processIns
								.getDataItems();
						for (int j = 0; j < di.length; j++) {
							String udaName = di[j].getName();

							if (nbwsDetailsForm.getMemoAttachedYesNo() != null) {

								// #Issue 101 - When memo with attachement is
								// selected do not check for pending memo
								// processes
								if (udaName
										.equalsIgnoreCase(Constants.memoAttachReqd)) {
									if (nbwsDetailsForm.getMemoAttachedYesNo()
											.equalsIgnoreCase("Yes")) {
										p.put(udaName, "1");
										log
												.info("User="
														+ user.getUserId()
														+ "	Memo with attachement was ordered");
									} else {
										p.put(udaName, MINUS_ONE);
									}

								}
								String memoDocProcPId = epmRules
										.isEPMProcessSuspended(gfid,
												Constants.epmCode_memo);

								if (memoDocProcPId.equals(MINUS_ONE)) {

									if (udaName
											.equalsIgnoreCase(Constants.memoNoAttachReqd)) {
										if (nbwsDetailsForm
												.getMemoAttachedYesNo()
												.equalsIgnoreCase("No")) {
											p.put(udaName, "1");
											log
													.info("User="
															+ user.getUserId()
															+ "	Memo with No attachement was ordered");
										} else {
											p.put(udaName, MINUS_ONE);
										}

									}
								}
							}
							if (udaName.equalsIgnoreCase(Constants.paraMedReqd)) {
								String paramedDocProcPId = epmRules
										.isEPMProcessSuspended(gfid,
												Constants.epmCode_exam);

								if (paramedDocProcPId.equals(MINUS_ONE)) {

									if (nbwsDetailsForm.isParaMedReqd()) {
										p.put(udaName, "1");
										log.info("User=" + user.getUserId()
												+ "	Paramed Exam was ordered");
									} else {
										p.put(udaName, MINUS_ONE);
									}
								}
							}
							if (udaName.equalsIgnoreCase(Constants.dmvReqd)) {
								String dmvDocProcPId = epmRules
										.isEPMProcessSuspended(gfid,
												Constants.epmCode_dmv);
								if (dmvDocProcPId.equals(MINUS_ONE)) {

									if (nbwsDetailsForm.isDmvReqd()) {
										p.put(udaName, "1");
										log.info("User=" + user.getUserId()
												+ "	DMV record ordered");
									} else {
										p.put(udaName, MINUS_ONE);
									}
								}
							}
							if (udaName.equalsIgnoreCase(Constants.medRecReqd)) {
								String medRecDocProcPId = epmRules
										.isEPMProcessSuspended(gfid,
												Constants.epmCode_mrreq);
								if (medRecDocProcPId.equals(MINUS_ONE)) {

									if (nbwsDetailsForm.isMedRecReqd()) {
										p.put(udaName, "1");
										log.info("User=" + user.getUserId()
												+ "	Med Rec was ordered");
									} else {
										p.put(udaName, MINUS_ONE);
									}
								}
							}
							if (udaName
									.equalsIgnoreCase(Constants.medConsultReqd)) {
								if (nbwsDetailsForm.isMedConsultReqd()) {
									p.put(udaName, "1");
									log.info("User=" + user.getUserId()
											+ "	Dr Consult was ordered");
								} else {
									p.put(udaName, MINUS_ONE);
								}
							}
							//Need to start new interview processes. Actual work is done in the backend.
							if (udaName.equalsIgnoreCase(Constants.PHONE_INTERVIEW_REQD)) {
								if (nbwsDetailsForm.isPhoneInterviewRequired()) {
									p.put(udaName, "1");
									log.info("User=" + user.getUserId()
											+ "	Phone Interviews were ordered.");
								} else {
									p.put(udaName, MINUS_ONE);
								}
							}
							if (udaName
									.equalsIgnoreCase(Constants.declineIndividual)) {
								String declineDocProcPId = epmRules
										.isEPMProcessSuspended(gfid,
												Constants.epmCode_decl);
								if (declineDocProcPId.equals(MINUS_ONE)) {

									if (nbwsDetailsForm.isDeclineIndividual()) {
										p.put(udaName, "1");
										log
												.info("User="
														+ user.getUserId()
														+ "	Decline Individual selected");
									} else {
										p.put(udaName, MINUS_ONE);
									}
								}
							}
							if (udaName
									.equalsIgnoreCase(Constants.policyNumberUDA)) {
								p.put(udaName, nbwsDetailsForm
										.getPolicyNumber());
							}
							if (di[j].getName().equalsIgnoreCase(
									Constants.manuallyStarted)) {
								if (di[j].getValue().equals("1")) {
									synchronized (ctx.session()) {
										ctx.session().setAttribute(
												"showManuallyStartedSession",
												"show");
									}
								}
							}

						}

					} else {
						com.fujitsu.iflow.model.workflow.DataItem[] di = processIns
								.getDataItems();
						for (int j = 0; j < di.length; j++) {
							if (di[j].getName().equalsIgnoreCase(
									Constants.policyNumberUDA)) {
								p.put(di[j].getName(), nbwsDetailsForm
										.getPolicyNumber());
							}
							if (di[j].getName().equalsIgnoreCase(
									Constants.manuallyStarted)) {
								if (di[j].getValue().equals("1")) {
									synchronized (ctx.session()) {
										ctx.session().setAttribute(
												"showManuallyStartedSession",
												"show");
									}
								}
							}
							// Three UDAs - if proc Name contains Memo / Exam /
							// Medrec and work Item Name contains
							// Record Incoming
							if (wi
									.getName()
									.matches(
											("(?i).*"
													+ Constants.recordIncomingDocPrefix + ".*"))) {

								// start First UDA for Record Incoming - Memo
								if (wi.getProcessInstance().getName().matches(
										"(?i).*" + Constants.epmCode_memo
												+ ".*")) {
									if (di[j].getName().equalsIgnoreCase(
											Constants.Waiting_on_addl_memo)) {
										// String memoDocProcPId =
										// epmRules.isProcessSuspended(gfid,
										// Constants.memoDocProc);
										String memoDocProcPId = epmRules
												.isEPMProcessSuspended(gfid,
														Constants.epmCode_memo);
										if (memoDocProcPId.equals(MINUS_ONE)) {
											if (nbwsDetailsForm
													.isRecordIncomingMEMO()) {
												log
														.info(" Record Incoming found Memo UDA will set to 1 ");
												p.put(di[j].getName(), "1");
											} else {
												log
														.info(" Record Incoming found Memo UDA will set to -1 ");
												p.put(di[j].getName(), MINUS_ONE);
											}
										}
									}
								}// End First UDA
								// start Second UDA for Record Incoming - MEDREC
								if (wi.getProcessInstance().getName().matches(
										"(?i).*" + Constants.epmCode_medRec
												+ ".*")) {
									if (di[j].getName().equalsIgnoreCase(
											Constants.Waiting_on_addl_med_rec)) {
										// String medrecDocProcPId =
										// epmRules.isProcessSuspended(gfid,
										// Constants.medRecDocProcPId);
										String medrecDocProcPId = epmRules
												.isEPMProcessSuspended(
														gfid,
														Constants.epmCode_medRec);
										if (medrecDocProcPId.equals(MINUS_ONE)) {
											if (nbwsDetailsForm
													.isRecordIncomingMEDREC()) {
												p.put(di[j].getName(), "1");
											} else {
												p.put(di[j].getName(), MINUS_ONE);
											}
										}
									}
								}// End Second UDA
								

							} // end if loop for
							
							if (wi
									.getName()
									.matches(
											("(?i).*"
													+ Constants.reviewIncomingDocPrefix + ".*"))) {						

								// start First UDA for Review Incoming - EXAM
								if (wi.getProcessInstance().getName().matches(
										"(?i).*" + Constants.epmCode_exam
												+ ".*")) {
									if (di[j]
											.getName()
											.equalsIgnoreCase(
													Constants.Waiting_on_addl_paramed_exam)) {
										// String examDocProcPId =
										// epmRules.isProcessSuspended(gfid,
										// Constants.examDocProcId);
										String examDocProcPId = epmRules
												.isEPMProcessSuspended(gfid,
														Constants.epmCode_exam);
										if (examDocProcPId.equals(MINUS_ONE)) {
											if (nbwsDetailsForm
													.isRecordIncomingEXAM()) {
												p.put(di[j].getName(), "1");
											} else {
												p.put(di[j].getName(), MINUS_ONE);
											}
										}
									}
								}// End First UDA

								// start Second UDA for Review Incoming - QUOTE
								if (wi.getProcessInstance().getName().matches(
										"(?i).*" + Constants.epmCode_quote
												+ ".*")) {
									if (di[j].getName().equalsIgnoreCase(
											Constants.Waiting_on_addl_quote)) {
										// String medrecDocProcPId =
										// epmRules.isProcessSuspended(gfid,
										// Constants.medRecDocProcPId);
										String medrecDocProcPId = epmRules
												.isEPMProcessSuspended(gfid,
														Constants.epmCode_quote);
										if (medrecDocProcPId.equals(MINUS_ONE)) {
											if (nbwsDetailsForm
													.isRecordIncomingQUOTE()) {
												p.put(di[j].getName(), "1");
											} else {
												p.put(di[j].getName(), MINUS_ONE);
											}
										}
									}
								}// End Second UDA
							}
							
							// Constants.recordIncomingDocPrefix

						} // end for loop for di.length
					}

				}

				/*
				 * PDF Generation Code
				 */
				// Execute choice of work Item.
				if (!nbwsDetailsForm.getButtonSelected().equals(NULL_STRING)
						&& !nbwsDetailsForm.getButtonSelected().equals("save")) {

					// for (int i = 0; i < choices.length; i++) {
					if (nbwsDetailsForm.getButtonSelected().equals("Verified")
							|| nbwsDetailsForm.getButtonSelected().equals(
									"Decline")
							|| nbwsDetailsForm.getButtonSelected().equals(
									"Incomplete-Final")
							|| nbwsDetailsForm.getButtonSelected().equals(
									"Withdraw")) {
						String AAID = null;
						
						try
						{
							AAID = StellentClient.login();
							WorkSheet workSheet = new WorkSheet();
							workSheet.makePDF(user.getUserId(), gfid, nbwsDetailsForm
									.getPolicyNumber(), ctx.session()
									.getServletContext().getRealPath(NULL_STRING), AAID);
							synchronized (ctx.session()) {
								ctx.session().setAttribute("NBWSDetailsForm",
										nbwsDetailsForm);
							}
						} catch (Exception e) {
							log.debug("Exception " + e.getClass().getName() + " with message: " + e.getMessage() + " thrown while attemping to use Stellent");
						} finally
						{
							if (AAID != null)
								StellentClient.logout(AAID);
						}
						// break;
					}

					// }
				}
			}

			// Funded HSA
			if (nbwsDetailsForm.getFundedHSA().equalsIgnoreCase("Y")) {
				ctx.session().setAttribute("showFundedHSASession", "show");
			} else {
				ctx.session().setAttribute("showFundedHSASession", null);
			}
			if (wi.getName().trim().equalsIgnoreCase(Constants.processDIW)) {
				processIns = wi.getProcessInstance();
				p = new Properties();
				com.fujitsu.iflow.model.workflow.DataItem[] di = processIns
						.getDataItems();
				for (int j = 0; j < di.length; j++) {
					if (di[j].getName().equalsIgnoreCase(
							Constants.numberOfDaysRefund)) {
						p.put(di[j].getName(), nbwsDetailsForm
								.getNumberOfDaysRefund());
					}

				}
			}

			//Make updates for Interview if this is a nurse consultant activity			
			if (MiscellaneousUtils.isInterviewPlanName(wi.getPlanName()) && MiscellaneousUtils.isNurseConsultantActivity(wi.getName()))
			{
				try
				{
					updateForInterview(ctx, nbwsDetailsForm, wi, p);
				} catch (Exception e)
				{
					canMoveFromActivity = false;
				}
			}
					
			try {
				int waittime = 2;
				while (processIns.isLockedForEdit()) {
					log.debug(" Process instance Id :" + processIns.getId());
					log.debug(" Process Instance is locked wating 1");
					if (waittime == 900) {
						log
								.debug("Process Instance was locked for more than 90 seconds, UDA update failed");
						ctx.addGlobalError("error.proc.inst.locked");
						isEPMupdateSuccessful = false;
						return isEPMupdateSuccessful;

					}
					Thread.sleep(100);
					waittime = waittime + 1;
				}
				log.debug(" Process instance Id :" + processIns.getId());
				long t3 = System.currentTimeMillis();
				processIns.startEdit();
				processIns.setDataItemValues(p);
				processIns.commitEdit();
				long t4 = System.currentTimeMillis();
				log.debug("Time taken for updating uda " + (t4 - t3)
						+ " milliseconds");

			} catch (Exception ex) {
				processIns.cancelEdit();
				log.debug("UDA Update failed as process instance was locked. GFID = " + gfid);
				ex.printStackTrace();
			}

			// If an Activity Action is clicked - then delegate to appropriate
			// method
			if (!nbwsDetailsForm.getButtonSelected().equals(NULL_STRING)) {
				if (wi != null) {
					if (wi.getName().trim().equalsIgnoreCase(
							Constants.screenApplication)
							|| wi.getName().trim().equalsIgnoreCase(
									Constants.enterApplication)) {
						StellentClient stellentClient = new StellentClient();

						// User object stored in session will have EPM and
						// Stellent sessionIds. This is the correct way to
						// retrieve the AAID and session

						String AAID = null;
						
						try
						{
							AAID = StellentClient.login();
							log.debug("Begin update all documents");
							stellentClient.updateAllDocuments(
									user.getUserId(),
									AAID, 
									gfid,
									nbwsDetailsForm.getPolicyNumber(),
									nbwsDetailsForm.getKeyApplicantFirstName(),
									nbwsDetailsForm.getKeyApplicantLastName(),
									nbwsDetailsForm.getKeyApplicantMI(),
									nbwsDetailsForm.getKeyApplicantSuffix(),
									nbwsDetailsForm.getAgentName(),
									nbwsDetailsForm.getAgentNumber(),
									nbwsDetailsForm.getListBill(),
									nbwsDetailsForm.getProductName(),
									nbwsDetailsForm.getState());

						} catch (Exception e) {
							log.debug("Exception " + e.getClass().getName() + " with message: " + e.getMessage() + " thrown while attemping to use Stellent");
						} finally
						{
							if (AAID != null)
								StellentClient.logout(AAID);
						}
						log.debug("End update all documents");
					}
				}

			} else
			{
				
			}

			// Execute choice of work Item.
			if (canMoveFromActivity && !nbwsDetailsForm.getButtonSelected().equals(NULL_STRING)
					&& !nbwsDetailsForm.getButtonSelected().equals("save")) {
				long t = System.currentTimeMillis();
				int mcwaittime = 2;
				while (processIns.isLockedForEdit()) {
					log.debug(" Process is locked for edit, waiting 3 seconds");
					log.debug(" Process instance Id :" + processIns.getId());
					log.debug(" Work Item Id :" + wi.getId());
					if (mcwaittime == 900) {
						log
								.debug("Process Instance was locked for more than 90 seconds, UDA update failed");
						ctx.addGlobalError("error.proc.inst.locked");
						isEPMupdateSuccessful = false;
						return isEPMupdateSuccessful;
					}
					
					Thread.sleep(100);
					mcwaittime = mcwaittime + 1;
				}
				wi.makeChoice(nbwsDetailsForm.getButtonSelected());
				long t1 = System.currentTimeMillis();
				log.info(" Make Choice " + (t1 - t));
				// logout from stellent when activity choice is taken
				StellentClient.logoutByUserNonRedundant(user);
				// clear the current workitem from session as activity is
				// completed
				HttpSession session = ctx.session();
				session.setAttribute(com.epm.acmi.struts.Constants.workItemId,
						null);
			}

			isEPMupdateSuccessful = canMoveFromActivity;
			log.debug("End updateUDAs");

		} catch (Exception ex) {
			log.error("Update UDA Failed , GFID = " + gfid, ex);
		} finally
		{
			try
			{
				if ((epmh != null) && (wfSession != null))
					epmh.disconnect(wfSession);
			} catch (Exception e)
			{
				log.error("Exception while trying to disconnect from EPM Server", e);
			}
		}
		return isEPMupdateSuccessful;
	}

	private boolean isNurseConsultantArrow(String arrowString) {
		return Constants.EPM_ACTIVITY_FOLLOW_UP_REQD_ARROW.equalsIgnoreCase(arrowString) || 
		Constants.EPM_ACTIVITY_INTERVIEW_COMPLETE_ARROW.equalsIgnoreCase(arrowString) || 
		Constants.EPM_ACTIVITY_UNABLE_TO_COMPLETE_ARROW.equalsIgnoreCase(arrowString);
	}

	private static IuaappDocProcessesDAO getProcessDAO() {
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

	private static IuatelephonicInterviewDAO getPhoneInterviewDAO() {
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

	private static IuainterviewRequestDAO getInterviewRequestDAO() {
		if (interviewRequestDAO == null)
		{
			synchronized(lock)
			{
				if (interviewRequestDAO == null)
				{
					interviewRequestDAO = new IuainterviewRequestDAO();
				}
			}
		} 
		
		return interviewRequestDAO;
	}

	private static IuaunableToCompleteReasonCodeDAO getUnableToCompleteReasonDAO() {
		if (unableToCompleteReasonDAO == null)
		{
			synchronized(lock)
			{
				if (unableToCompleteReasonDAO == null)
				{
					unableToCompleteReasonDAO = new IuaunableToCompleteReasonCodeDAO();
				}
			}
		} 
		
		return unableToCompleteReasonDAO;
	}

	private static IuainterviewDispositionResultCodeDAO getInterviewDispositionDAO() {
		if (interviewDispositionDAO == null)
		{
			synchronized(lock)
			{
				if (interviewDispositionDAO == null)
				{
					interviewDispositionDAO = new IuainterviewDispositionResultCodeDAO();
				}
			}
		} 
		
		return interviewDispositionDAO;
	}

	private static IuauserDAO getUserDAO() {
		if (userDAO == null)
		{
			synchronized(lock)
			{
				if (userDAO == null)
				{
					userDAO = new IuauserDAO();
				}
			}
		} 
		
		return userDAO;
	}

	private static Map getInterviewArrowCodeMap() {
		if (interviewArrowCodeMap == null)
		{
			synchronized(lock)
			{
				if (interviewArrowCodeMap == null)
				{
					interviewArrowCodeMap = new HashMap();
					interviewArrowCodeMap.put(Constants.EPM_ACTIVITY_INTERVIEW_COMPLETE_ARROW, Constants.COMPLETE_DISP_CODE);
					interviewArrowCodeMap.put(Constants.EPM_ACTIVITY_UNABLE_TO_COMPLETE_ARROW, Constants.UNABLE_TO_COMPLETE_DISP_CODE);
					interviewArrowCodeMap.put(Constants.EPM_ACTIVITY_FOLLOW_UP_REQD_ARROW, Constants.FOLLOW_UP_DISP_CODE);
				}
			}
		} 
		
		return interviewArrowCodeMap;
	}
	
	
	/**
	 * This method updates the interview table based on the choices taken by the nurse consultant.
	 * It updates the current interview with 
	 * @param detailsForm
	 * @param pid
	 * @param arrowString
	 * @return
	 * @throws ParseException
	 */
	private void updateForInterview(ActionContext ctx, NBWSDetailsForm detailsForm, WorkItem workItem, Properties p) throws Exception {		
		long followUpInterviewId = -1L;		
		String arrowString = null;
		ProcessInstance processIns = workItem.getProcessInstance();
		String buttonChoice = detailsForm.getButtonSelected().trim();
		
		if (isNurseConsultantArrow(buttonChoice))
			arrowString = buttonChoice;
		
		IuaappDocProcessesDAO processDAO = null;
		IuatelephonicInterviewDAO phoneInterviewDAO = null;
		boolean isInterviewNew = false;

		com.fujitsu.iflow.model.workflow.DataItem[] di = processIns.getDataItems();
				
		for (int j = 0; j < di.length; j++) {
			String udaName = di[j].getName();
			
			if (udaName.equalsIgnoreCase(Constants.FOLLOW_UP_DATE_TIME)) {
				
				try
				{
					Date followUpDateTime = getFollowUpDateTime(detailsForm);
					
					if (followUpDateTime != null)
					{
						String followUpTimestamp = Long.toString(followUpDateTime.getTime());
						p.put(di[j].getName(), followUpTimestamp);
					}
					else
						p.put(di[j].getName(), MINUS_ONE);
						
				} catch (ParseException pe)
				{
					log.debug("Parse exception trying to update follow-up interview date-time in EPM process");
				}
			}
		}
		
		try
		{
			processDAO = getProcessDAO();			
			IuaappDocProcesses process = MiscellaneousUtils.getProcessForInterview(ctx, processDAO, processIns);
						
			if (process == null)
			{
				log.debug("Either this is not an Interview process, or it is a the common interview subprocess");
				return;
			}
					
			IuatelephonicInterview thisProcessInterview = null;
			Iterator phoneInterviews = process.getIuatelephonicInterviews().iterator();
			boolean updateBothInterviews;
			phoneInterviewDAO = getPhoneInterviewDAO();
			IuatelephonicInterview topPhoneInterview = null;
			
			if (phoneInterviews.hasNext())
			{
				//There is only one phone interview per interview process.
				topPhoneInterview = (IuatelephonicInterview)phoneInterviews.next();
				thisProcessInterview = MiscellaneousUtils.getInterviewForPID(processIns.getId());
				
				if (thisProcessInterview != null && topPhoneInterview!= null)
					updateBothInterviews = !thisProcessInterview.getInterviewId().equals(topPhoneInterview.getInterviewId());
				else
					updateBothInterviews = false;
				
				//Update current interview with unable to complete reason and final disposition...
				IuaunableToCompleteReasonCodeDAO unableToCompleteReasonDAO = null;
				
				try
				{
					IuaunableToCompleteReasonCode unableToCompleteReason = null;
					
					if (Constants.INTERVIEW_ARROW_UNABLE_TO_COMPLETE.equals(arrowString))
					{
						unableToCompleteReasonDAO = getUnableToCompleteReasonDAO();
						String reasonCode = detailsForm.getUnableToCompleteReason();
						unableToCompleteReason = unableToCompleteReasonDAO.findById(reasonCode);
					}
					
					if (updateBothInterviews)
					{
						thisProcessInterview.setIuaunableToCompleteReasonCode(unableToCompleteReason);
						topPhoneInterview.setIuaunableToCompleteReasonCode(null);
					} else
						topPhoneInterview.setIuaunableToCompleteReasonCode(unableToCompleteReason);
				} finally
				{
					if (unableToCompleteReasonDAO != null)
						IuaunableToCompleteReasonCodeDAO.close();
				}

				if (!MiscellaneousUtils.isNullString(arrowString))
				{
					IuainterviewDispositionResultCodeDAO interviewDispositionDAO = null;
					
					try {
						interviewDispositionDAO = getInterviewDispositionDAO();
						String arrowCode = (String)getInterviewArrowCodeMap().get(arrowString);
						IuainterviewDispositionResultCode interviewDisposition = interviewDispositionDAO.findById(arrowCode);

						if (updateBothInterviews)
						{
							thisProcessInterview.setIuainterviewDispositionResultCode(interviewDisposition);
							topPhoneInterview.setIuainterviewDispositionResultCode(null);
						} else
							topPhoneInterview.setIuainterviewDispositionResultCode(interviewDisposition);
					} finally
					{
						if (interviewDispositionDAO != null)
							IuainterviewDispositionResultCodeDAO.close();
					}
				}
				
				if (topPhoneInterview != null)
					BaseHibernateDAO.getSession().update(topPhoneInterview);
				
				if (updateBothInterviews)
					BaseHibernateDAO.getSession().update(thisProcessInterview);
				
				BaseHibernateDAO.getSession().flush();
				
				//Update interview request with nurse consultant...
				IuainterviewRequest interviewRequest = topPhoneInterview.getIuainterviewRequest();
				Iuauser nurseConsultant = null;
				String nurseConsultantName = detailsForm.getInitialInterviewNurseConsultant();
				
				Iterator nurseConsultants = getUserDAO().findByEmployeename(nurseConsultantName).iterator();
				
				if (nurseConsultants.hasNext())
					nurseConsultant = (Iuauser)nurseConsultants.next(); 
				
				interviewRequest.setIuauserByInitialInterviewNc(nurseConsultant);
				
				BaseHibernateDAO.getSession().update(interviewRequest);
				BaseHibernateDAO.getSession().flush();						

				if (Constants.INTERVIEW_ARROW_FOLLOW_UP_REQD.equals(arrowString))
				{
					Date followUpDateTime = null;
					
					try
					{
						followUpDateTime = getFollowUpDateTime(detailsForm);
						
						if ((followUpDateTime != null) && (MiscellaneousUtils.isPastDate(followUpDateTime)))
						{
							ctx.addGlobalError("tInterview.error.followUpDate.past", detailsForm.getFollowUpDateString());
							throw new Exception("Follow Up Date is before today");
						}
					} catch (ParseException pe)
					{
						log.debug("Parse Exception trying to update follow-up interview date-time");
					}
					
					//Update follow up interview with scheduled date and time...
					IuatelephonicInterview followUpInterview = IUAProcessBD.getExistingFollowUpInterview(interviewRequest); 
						
					if (followUpInterview == null)
					{
						isInterviewNew = true;
						followUpInterview = new IuatelephonicInterview();
					}
											
					if (followUpDateTime != null)
						followUpInterview.setScheduledDateTime(followUpDateTime);
									
					if (followUpInterview.getIuainterviewRequest() == null)
						followUpInterview.setIuainterviewRequest(interviewRequest);
					
					if (isInterviewNew)
						BaseHibernateDAO.getSession().save(followUpInterview);
					else
					{
						BaseHibernateDAO.getSession().update(followUpInterview);
					}
					
					BaseHibernateDAO.getSession().flush();
					followUpInterviewId = followUpInterview.getInterviewId().longValue();		
				}
			}		
		} finally
		{
			BaseHibernateDAO.close();
		}
		
		//Store the follow up interview id in the process, if any...
		if (followUpInterviewId > -1)
		{
			for (int j = 0; j < di.length; j++) {
				String udaName = di[j].getName();
				
				//Pass the follow up interview id to the process, so that it can match it when the follow-up process starts.
				if (udaName.equalsIgnoreCase(Constants.FOLLOW_UP_INTERVIEW_ID)) {
					p.put(di[j].getName(), Long.toString(followUpInterviewId));
				}
			}
		}
	}

	/**
	 * Retrieves the follow-up date-time from the form's follow-up date and follow-up time strings
	 * 
	 * @param detailsForm
	 * @return
	 * @throws ParseException
	 */
	private Date getFollowUpDateTime(NBWSDetailsForm detailsForm) throws ParseException {
		if (MiscellaneousUtils.isNullString(detailsForm.getFollowUpDateString()) || MiscellaneousUtils.isNullString(detailsForm.getFollowUpTimeString()))
			return null;
		
		StringBuffer dateString = new StringBuffer();
		dateString.append(detailsForm.getFollowUpDateString());
		dateString.append(SPACE);
		dateString.append(detailsForm.getFollowUpTimeString());
		Date followUpDateTime = null;
		
		try
		{
			followUpDateTime = interviewDateFormat.parse(dateString.toString());
		} catch (ParseException pe)
		{
			followUpDateTime = interviewDateFormatNoSpace.parse(dateString.toString());
		}
		
		return followUpDateTime;
	}



	/**
	 * Returns true if the .
	 */
	private boolean QAProcessVerfiedCheck(FormActionContext ctx,
			Connection conn, String buttonSelected, String gfId) {
		boolean procExists = false;
		if (buttonSelected.equalsIgnoreCase("Verified")) {
			// check if active or suspended processes exist
			String sql = "Select count(*) as count from IUAAppDocProcesses where gfId = '"
					+ gfId
					+ "' and status not in ('DEAD', 'ABORT') and typeCode not in ('DECL', 'QUAL', 'MRCL', 'MRPP', 'MAIN', 'DOCDE', 'INTCM')";
			try {
				Statement stmt = conn.createStatement();
				ResultSet rst = stmt.executeQuery(sql);
				int procCount = 0;

				while (rst.next()) {
					procCount = rst.getInt("count");
				}

				if (procCount > 0) {
					ctx.addGlobalError("Details.OtherProcExistMsg");
					procExists = true;
				}
				rst.close();
				stmt.close();
			} catch (Exception ex) {
				log.error("Exception " + ex.getClass().getName() + " caught with message: GFID = " + gfId + ex.getMessage());
				ex.printStackTrace();
			}

		}
		return procExists;
	}

	/**
	 * This Method is called if the save button on the HTML-Page is pressed.
	 * 
	 * @param ctx
	 *            FormActionContext
	 * @throws Exception
	 */
	public void save_onClick(FormActionContext ctx) throws Exception {

		log.debug("Begin save_onClick");

		synchronized (ctx)
		{
			FormActionContext testCtx = (FormActionContext)workingCtxMap.get(ctx);
			
			//If context is being worked on, we clicked twice, don't process again...
			if (testCtx != null)
				return;
			
			//Mark the fact we are working on this context
			workingCtxMap.put(ctx, ctx);
		}
		
		
		try
		{
			// Save the user defined attributes
			String workIdString = (String) ctx.request().getSession()
					.getAttribute(com.epm.acmi.struts.Constants.workItemId);
	
			// This condition check is work around as some time tomcat may bee
			// loosing session attributes
			if (null == workIdString) {
	
				log
						.debug("WorkItem Id is not stored in session properly, current value is null");
				log
						.debug("Trying to retrive work item from session, WorkItem Id is not stored in session properly, current value is null");
				workIdString = (String) ctx.request().getAttribute(
						com.epm.acmi.struts.Constants.workItemId);
	
				if (null == workIdString) {
					log
							.debug("Trying to retrive work item from session, WorkItem Id is not stored in request also , current value is null");
					log
							.debug("user must retry the action ang go back to work list");
					ctx.addGlobalError("error.assignError2");
					log.debug("forward to work list");
					String fwdurl = "/worklist.do?ctrl=secondarymaintabset&action=TabClick&param=tab5";
					log.debug("fwdurl -> " + fwdurl);
					ctx.forwardByName(fwdurl);
	
				}
	
			}
	
			String gfid = (String) ctx.session().getAttribute((Constants.gfid));
			if (null == gfid) {
				log
						.debug("GFID Id is not stored in session properly, current value is null");
				log
						.debug("Trying to retrive work item from session, GFID Id is not stored in session properly, current value is null");
				workIdString = (String) ctx.request().getAttribute(
						com.epm.acmi.struts.Constants.workItemId);
	
				if (null == workIdString) {
					log
							.debug("Trying to retrive GFID from session, GFIG Id is not stored in request also , current value is null");
					log
							.debug("user must retry the action ang go back to work list");
					ctx.addGlobalError("error.assignError2");
					log.debug("forward to work list");
					String fwdurl = "/worklist.do?ctrl=secondarymaintabset&action=TabClick&param=tab5";
					log.debug("fwdurl -> " + fwdurl);
					ctx.forwardByName(fwdurl);
	
				}
	
			}
	
			String parsedScreenDate = null;
	
			NBWSDetailsForm nbwsDetailsForm = (NBWSDetailsForm) ctx.form();
			//Check that the numeric values are actually numbers.
			
			boolean error = false;
			try {
				String policyNumber = nbwsDetailsForm.getPolicyNumber();
				if (!MiscellaneousUtils.isNullString(policyNumber))
					Long.parseLong(policyNumber);
			} catch (NumberFormatException ne) {
				ctx.addGlobalError("Details.invalidPolicyNumber");
				log.warn("Policy Number Format Exception ", ne);
				error = true;
			}

			try {
				String agentNumber = nbwsDetailsForm.getAgentNumber();
				if (!MiscellaneousUtils.isNullString(agentNumber))
					Integer.parseInt(agentNumber);
			} catch (NumberFormatException ne) {
				ctx.addGlobalError("Details.invalidAgentNumber");
				log.warn("Agent Number Format Exception ", ne);
				error = true;
			}

			try {
				String shortTermPolicyNumber = nbwsDetailsForm.getShortTermPolicy();
				if (!MiscellaneousUtils.isNullString(shortTermPolicyNumber))
					Integer.parseInt(shortTermPolicyNumber);
			} catch (NumberFormatException ne) {
				ctx.addGlobalError("Details.invalidShortTermPolicyNumber");
				log.warn("Short Term Policy Number Format Exception ", ne);
				error = true;
			}

			try {
				String iASListBill = nbwsDetailsForm.getListBill();
				if (!MiscellaneousUtils.isNullString(iASListBill))
					Integer.parseInt(iASListBill);
			} catch (NumberFormatException ne) {
				ctx.addGlobalError("Details.invalidIASListBill");
				log.warn("IAS List Bill Format Exception ", ne);
				error = true;
			}
			
			String buttonSelected = nbwsDetailsForm.getButtonSelected();
	
			if (error) {
				if (buttonSelected.equalsIgnoreCase("save")) { //Stay on the details page.
					ctx.forwardToAction("main/iuauser?ctrl=maintabset&action=TabClick&param=tab1");
				}
				else {  //forward to WorkList
					return;
				}
			}
			
			Connection conn = null;
			PreparedStatement psmt = null;
			try {
	
				conn = Connect.getConnection();
				if (buttonSelected.equalsIgnoreCase("Verified")) {
					returnvalue = QAProcessVerfiedCheck(ctx, conn,
							buttonSelected, gfid);
	
					if (returnvalue) {
						// Do not continue return back to user and display a
						// message on the user screen
						return;
					}
				}
				User user = (User) ctx.session().getAttribute(
						com.epm.acmi.struts.Constants.loggedUser);
	
				// set the FundedHSA String based on the value from the boolean
				if (nbwsDetailsForm.isFundedHSAboolean()) {
					nbwsDetailsForm.setFundedHSA("Y");
				} else {
					nbwsDetailsForm.setFundedHSA("N");
				}
	
				StringBuffer updateSQL = new StringBuffer(
						" update IUAApplication set "
								+ Constants.keyApplicantSuffix + " = ? , ");
				updateSQL.append(Constants.keyApplicantFirstName + " = ? , ");
				updateSQL.append(Constants.keyApplicantLastName + " = ? , ");
				updateSQL.append(Constants.keyApplicantMI + " = ? , ");
				updateSQL.append(Constants.eft + " = ? , ");
				updateSQL.append(Constants.agentName + " = ? , ");
				updateSQL.append(Constants.agentNumber + " = ? , ");
				updateSQL.append(Constants.policyNumber + " = ? , ");
				updateSQL.append(Constants.listBill + " = ? , ");
				updateSQL.append(Constants.receivedDateString + " = ? , ");
				updateSQL.append(Constants.uwInitail + " = ? , ");
				updateSQL.append(Constants.network + " = ? , ");
				updateSQL.append(Constants.shortTermPolicy + " = ? , ");
				updateSQL.append(Constants.screenDateString + " = ? , ");
				updateSQL.append(Constants.state + " = ? , ");
				updateSQL.append(Constants.productName + " = ? , ");
				updateSQL.append(Constants.planId + " = ?, ");
				updateSQL.append(Constants.deductible + "= ? , ");
				updateSQL.append(Constants.coInsurance + " = ? , ");
				updateSQL.append(Constants.coPay + "= ? , ");
				updateSQL.append(Constants.pharmaProfile + " = ? , ");
				updateSQL.append(Constants.noResultsFound + " = ? , ");
				updateSQL.append(Constants.reasonCode + " = ? , ");
				updateSQL.append(Constants.returnCode + " = ? , ");
				updateSQL.append(Constants.lastUpdatedDateTime
						+ " = GetDate() , ");
				updateSQL.append(Constants.lastUpdatedUserId + " = ? , ");
				updateSQL.append(Constants.fundedHSA + " = ? , ");
				updateSQL.append(Constants.screenBy + " = ? , ");
				updateSQL.append(Constants.Rewrite + " = ?, ");
				updateSQL.append("PastActivities = ?, ");
				updateSQL.append("PastActions = ? ");
	
				updateSQL.append("where " + Constants.gfid + " = ? ");
	
				psmt = conn.prepareStatement(updateSQL.toString());
				psmt.setString(1, nbwsDetailsForm.getKeyApplicantSuffix());
				psmt.setString(2, nbwsDetailsForm.getKeyApplicantFirstName());
				psmt.setString(3, nbwsDetailsForm.getKeyApplicantLastName());
				psmt.setString(4, nbwsDetailsForm.getKeyApplicantMI());
				psmt.setString(5, nbwsDetailsForm.getEft());
	
				psmt.setString(6, nbwsDetailsForm.getAgentName());
				psmt.setString(7, nbwsDetailsForm.getAgentNumber());
				psmt.setString(8, nbwsDetailsForm.getPolicyNumber());
				psmt.setString(9, nbwsDetailsForm.getListBill());
				psmt.setString(10, nbwsDetailsForm.getReceivedDateString());
	
				psmt.setString(11, nbwsDetailsForm.getUwInitail());
				psmt.setString(12, nbwsDetailsForm.getNetwork());
				psmt.setString(13, nbwsDetailsForm.getShortTermPolicy());
				parsedScreenDate = nbwsDetailsForm.getScreenDateString();
	
				// Fix for issue #68 03/04/06 cmontero
				if (parsedScreenDate.trim().equals(NULL_STRING))
					parsedScreenDate = null;
	
				psmt.setString(14, parsedScreenDate);
				psmt.setString(15, nbwsDetailsForm.getState());
	
				psmt.setString(16, nbwsDetailsForm.getProductName());
				psmt.setString(17, nbwsDetailsForm.getPlanId());
				psmt.setString(18, nbwsDetailsForm.getDeductible());
				psmt.setString(19, nbwsDetailsForm.getCoInsurance());
				psmt.setString(20, nbwsDetailsForm.getCoPay());
				psmt.setString(21, nbwsDetailsForm.getPharmaProfile());
				psmt.setString(22, nbwsDetailsForm.getNoResultsFound());
				// Fix for issue #76 04/13/2006 cmontero
				String taskName = (String) ctx.session().getAttribute(
						Constants.taskName);
				// System.err.println("TASKNAME: '"+taskName+"'");
				if (taskName.equals("Make Underwriting Decision")) {
					if (!buttonSelected.equals("Apprv o/t Appld - Quote")
							&& !buttonSelected
									.equals("Apprv o/t Appld - No Quote")
							&& !buttonSelected.equals("save"))
						nbwsDetailsForm.setReasonCode(NULL_STRING);
				}
				//Begin User 8399-1 changes
				if (taskName.equals("Verify File for Policy Issue")) {
					    if((nbwsDetailsForm.getReasonCode().equals(NULL_STRING.trim())) || 
					    		(nbwsDetailsForm.getReasonCode().equalsIgnoreCase("Not Selected"))){
					    	nbwsDetailsForm.setReasonCode(NULL_STRING);
					    }else { 	
					    	nbwsDetailsForm.setReasonCode(nbwsDetailsForm.getReasonCode().trim());
					    }	
				}
				// End User 8399-1 changes.
				
				psmt.setString(23, nbwsDetailsForm.getReasonCode());
				if (taskName.equals("Resolve Screening Issue")
						|| taskName.equals("Resolve Initial Review Issue.")) {
					if (!buttonSelected.equals("Return App")
							&& !buttonSelected.equals("save"))
						nbwsDetailsForm.setReturnCode(NULL_STRING);
					
					/* USR 8399-1 begins changes
					 * Rathna Hiriyurkar 
					 * 01-21-2008 				  
					 */
					if ( buttonSelected.equals("Return App") 
							|| buttonSelected.equals("Unload App")) 
							 {
							String AAID = null;
							try
							{
								AAID = StellentClient.login();
								WorkSheet workSheet = new WorkSheet();
								workSheet.makePDF(user.getUserId(), gfid, nbwsDetailsForm
										.getPolicyNumber(), ctx.session()
										.getServletContext().getRealPath(NULL_STRING), AAID);
								synchronized (ctx.session()) {
									ctx.session().setAttribute("NBWSDetailsForm",
											nbwsDetailsForm);
								}
							} catch (Exception e) {
								log.debug("Exception " + e.getClass().getName() + " with message: " + e.getMessage() + " thrown while attemping to use Stellent");
							} finally
							{
								if (AAID != null)
									StellentClient.logout(AAID);
							} 
						// Ends USR 8399-1 changes;
					}	
				}
				psmt.setString(24, nbwsDetailsForm.getReturnCode());
				psmt.setString(25, user.getUserId());
				psmt.setString(26, nbwsDetailsForm.getFundedHSA());
				psmt.setString(27, nbwsDetailsForm.getScreenBy());
				psmt.setString(28, nbwsDetailsForm.getRewrite());
	
				// Added for UI required fields
				psmt.setString(29, nbwsDetailsForm.getPastActivities());
	
				if (nbwsDetailsForm.getTaskName().indexOf("Enter Application") >= 0) {
	
					// buttonSelected = the currently selected action
					if (buttonSelected.indexOf("To UW") >= 0
							|| buttonSelected.indexOf("RX Query") >= 0) {
						String pastActions = nbwsDetailsForm.getPastActions();
	
						if (pastActions == null) {
							nbwsDetailsForm.setPastActions(buttonSelected);
						} else if (pastActions.indexOf(buttonSelected) == -1) {
							nbwsDetailsForm.setPastActions(pastActions + ", "
									+ buttonSelected);
						}
					}
				}
				
				psmt.setString(30, nbwsDetailsForm.getPastActions());
				psmt.setString(31, gfid);
	
				psmt.executeUpdate();
	
				//
				long workIdlong = -1;
				if (workIdString != null) {
					workIdlong = Long.parseLong(workIdString);
				}
	
				// Funded HSA
				if (nbwsDetailsForm.getFundedHSA().equalsIgnoreCase("Y")) {
					ctx.session().setAttribute("showFundedHSASession", "show");
				} else {
					ctx.session().setAttribute("showFundedHSASession", null);
				}
	
				if (workIdlong != -1) {					
					// if UDA or make choice error occured then set the error
					// and forwad it details page.
					// error is set in the called method
					boolean isEPMUpdateSuccessful = updateUDAs(workIdlong,
							nbwsDetailsForm, ctx, user, gfid);
					if (!isEPMUpdateSuccessful) {
						returnvalue = true;
					}
				} else {
					log.debug("WorkItem Id =" + workIdlong);
					log.debug("WorkItem String =" + workIdString);
	
					log.debug("WorkItem id is not valid so retry the activity");
				}				
			} catch (Exception ex) {
				log.error(buttonSelected + " activity Failed ", ex);
				log.error("Exception: Save button action failed", ex);
				ctx.addGlobalError("error.mainError", NULL_STRING);
			} finally {
				Connect.closePSMT(psmt);
				Connect.closeConnection(conn);
			}
			log.debug("End save_onClick");
	
			log.debug("Selected Button = " + buttonSelected);
			if (buttonSelected.equalsIgnoreCase("save")
					|| buttonSelected.equalsIgnoreCase(NULL_STRING)) {
				log.debug("forward to details page");
				ctx
						.forwardToAction("main/iuauser?ctrl=maintabset&action=TabClick&param=tab1");
			} else {
				if (!returnvalue) {
					log.debug("forward to work list");
					String fwdurl = "/worklist.do?ctrl=secondarymaintabset&action=TabClick&param=tab5";
					log.debug("fwdurl -> " + fwdurl);
					ctx.forwardByName(fwdurl);
				} else {
					log.debug("forward to details page");
					ctx
							.forwardToAction("main/iuauser?ctrl=maintabset&action=TabClick&param=tab1");
				}
			}
		} finally
		{
			synchronized (ctx)
			{
				workingCtxMap.remove(ctx);
			}
		}

	}

	/**
	 * This Method is called if the Reassign button on the HTML-Page is pressed.
	 * 
	 * @param ctx
	 *            FormActionContext
	 * @throws Exception
	 */
	public void reassign_onClick(FormActionContext ctx) throws Exception {
		WFAdminSession wfAdminSession = null;
		EPMHelper epmHelper = null;		
		String wid = null;
		
		try {
			log.debug("Begin reassign_onClick");

			boolean isAssignToRole = false;
			boolean isAssignToUser = false;

			epmHelper = new EPMHelper();
			wfAdminSession = epmHelper.connectAsAdminFromPropertiesFile();
			String roleToAssignTo = null;
			String userToAssignTo = null;

			String reassignTo = (String) ctx.session().getAttribute(
					com.epm.acmi.struts.Constants.reassignkey);
			if (reassignTo != null) {
				String roleOrUser = reassignTo.substring(0, 4);
				if (roleOrUser.equalsIgnoreCase("user")) {
					isAssignToUser = true;
					userToAssignTo = reassignTo.replaceFirst("user", NULL_STRING);
				} else if (roleOrUser.equalsIgnoreCase("role")) {
					isAssignToRole = true;
					roleToAssignTo = reassignTo.replaceFirst("role", NULL_STRING);
				}

				if (reassignTo != null) {
					 wid = (String) ctx.session().getAttribute(
							Constants.workItemId);
					long currentId = new Long(wid).longValue();
					WorkItem currentWorkItem = WFObjectFactory.getWorkItem(
							currentId, wfAdminSession);
					
					SimpleListControl control = (SimpleListControl)ctx.session().getAttribute("WLList");
					WorkListList dataModel = (WorkListList)control.getDataModel();
					String key = Long.toString(currentWorkItem.getId());
					AcmiWorkItemBean acmiWorkItemBean = (AcmiWorkItemBean) ListHelp.getLineFromKey(dataModel, key);
					Date activeDate = acmiWorkItemBean.getCreatedDate();

					if (isAssignToRole) {
						if (currentWorkItem.getNodeInstance().getRole()
								.equalsIgnoreCase(roleToAssignTo)) {
							//
							LDAPUser userObj;
							ArrayList userRoleList;
							String userRole;
							String userName;
							HashMap usersDispHash = new HashMap();
							HashMap userList = ACMICache.getUsers();

							Object[] userArray = userList.values().toArray();
							for (int ii = 0; ii < userArray.length; ii++) {
								userObj = (LDAPUser) userArray[ii];
								userRoleList = userObj.getRoles();
								for (int j = 0; j < userRoleList.size(); j++) {
									userRole = (String) userRoleList.get(j);
									if (userRole
											.equalsIgnoreCase(roleToAssignTo)) {
										userName = userObj.getFirstName() + SPACE
												+ userObj.getLastName();
										usersDispHash.put(userObj.getUserId(),
												userName);
										break;
									}
								}
							}
							Iterator membersIterator = usersDispHash.keySet()
									.iterator();
							String[] usersOfGroup = new String[usersDispHash
									.keySet().size()];
							int count = 0;
							while (membersIterator.hasNext()) {
								usersOfGroup[count] = (String) membersIterator
										.next();
								count++;
							}
							currentWorkItem.reassignTo(usersOfGroup);
							// Update the IUAActivities Table - put null for
							// Employee Id, Accepted Date - since assigned to
							// role
							updateIUAActivitiesTable(currentWorkItem.getName(),
									currentWorkItem.getProcessInstance()
											.getId(), activeDate, null);
							//
						} else {

						}
					} else if (isAssignToUser) {
						String[] oneUser = new String[1];
						oneUser[0] = userToAssignTo;
						currentWorkItem.reassignTo(oneUser);
						updateIUAActivitiesTable(currentWorkItem.getName(),
								currentWorkItem.getProcessInstance().getId(),
								activeDate, userToAssignTo);
					}

				}
				ctx.session().setAttribute(
						com.epm.acmi.struts.Constants.reassignkey, null);
			}

		} catch (Exception ex) {
			ctx.addGlobalError("error.mainError", NULL_STRING);
			log.error("Exception: reassign button click failed. WorkItemId = " + wid, ex);
		} finally {
			try
			{
				if ((epmHelper != null) && (wfAdminSession != null))
					epmHelper.disconnect(wfAdminSession);
			} catch (Exception e)
			{
				log.error("Exception while trying to connect to EPM Server", e);
			}
		}

		log.debug("End reassign_onClick");
		ctx.forwardToAction("/worklist");

	}

	/**
	 * This Method is called if the Decline button on the HTML-Page is pressed.
	 * 
	 * @param ctx
	 *            FormActionContext
	 * @throws Exception
	 */
	public void decline_onClick(FormActionContext ctx) {
		EPMHelper epmh = null;
		WFSession wfSession = null;

		try {
			log.debug(" Begin decline_onClick ");
			User user = (User) ctx.session().getAttribute(
					com.epm.acmi.struts.Constants.loggedUser);
			String workIdString = (String) ctx.request().getSession()
					.getAttribute(com.epm.acmi.struts.Constants.workItemId);
			long workIdlong = -1;
			if (workIdString != null) {
				workIdlong = Long.parseLong(workIdString);
			}
			epmh = new EPMHelper();
			wfSession = epmh.connectAsUser(user.getUserId(), user.getPassword());
			WorkItem wi = WFObjectFactory.getWorkItem(workIdlong, wfSession);
			wi.decline();

			SimpleListControl control = (SimpleListControl)ctx.session().getAttribute("WLList");
			WorkListList dataModel = (WorkListList)control.getDataModel();
			String key = Long.toString(wi.getId());
			AcmiWorkItemBean acmiWorkItemBean = (AcmiWorkItemBean) ListHelp.getLineFromKey(dataModel, key);
			Date activeDate = acmiWorkItemBean.getCreatedDate();
			
			updateIUAActivitiesTable(wi.getName(), wi.getProcessInstance().getId(), activeDate, null);
		} catch (Exception ex) {
			ctx.addGlobalError("error.mainError", NULL_STRING);
			
			log.error("Exception " + ex.getClass().getName() + " caught with message:  "+ ex.getMessage());
			ex.printStackTrace();
		} finally
		{
			try
			{
				if ((epmh != null) && (wfSession != null))
					epmh.disconnect(wfSession);
			} catch (Exception e)
			{
				log.error("Exception while disconnecting from EPM Server", e);
			}
		}
		
		log.debug("End decline_onClick");
		ctx.forwardToAction("main/secondarytabsetBrowse");

	}

	/**
	 * Updates IUActivities table in ACMI database
	 * 
	 * @param workItemName
	 * @param processId
	 * @param employeeId
	 */
	private void updateIUAActivitiesTable(final String workItemName, final long processId, final Date activeDate, final String employeeId) 
	{
		log.debug("Begin updateIUAActivitiesTable");		
		
		Thread updateThread = new Thread (new Runnable() {
			long waitingTime = INITIAL_WAITING_TIME;
			
			public void run() {
				for (int i = 0; i < MAX_UPDATE_ATTEMPTS; i++)
				{
					try
					{
						int updateCount = doUpdateIUAActivitiesTable(workItemName, processId, activeDate, employeeId);
						//If successful, no need to re-try, so just break out of the loop.
						if (updateCount > 0)
						{
							log.debug("workItemName = '" + workItemName + "'; processId = " + processId + "; activeDate = " + dateFormat.format(activeDate) + "; employeeId = '" + employeeId + "'");
							log.debug("IUAActivities update affected " + updateCount + " records (after attempt # " + (i + 1) + ").");
							break;
						} else if (i == (MAX_UPDATE_ATTEMPTS - 1))
						{
							log.warn("workItemName = '" + workItemName + "'; processId = " + processId + "; activeDate = " + dateFormat.format(activeDate) + "; employeeId = '" + employeeId + "'");
							log.warn("IUAActivities update affected " + updateCount + " records (after attempt # " + (i + 1) + ").");
						}
					} catch (SQLException e) {
						if (i == (MAX_UPDATE_ATTEMPTS - 1))
						{
							log.error("There has been an SQLException thrown while updating IUAActivities table with Work Item Accepted user and date.", e);
							break;
						}
					}
					
					try
					{
						Thread.sleep(waitingTime);
						waitingTime = waitingTime*2;
					} catch (InterruptedException ie)
					{
						//do nothing...
					}				
				}		
			}
		});
		
		updateThread.start();
		log.debug("End updateIUAActivitiesTable");
	}
	
	private int doUpdateIUAActivitiesTable(String workItemName, long processId, Date activeDate, String employeeId) throws SQLException {		
		Connection conn = Connect.getConnection();
		PreparedStatement psmtUpdate = null;
		try {

			StringBuffer updateSQL = new StringBuffer(" UPDATE IUAActivities SET " + Constants.AcceptDate + " = ? , ");
			updateSQL.append(Constants.EmployeeID + " = ? ");
			updateSQL.append("WHERE " + Constants.PID + " = ? ");
			updateSQL.append(" AND " + Constants.ActivityName + " = ? ");
			updateSQL.append(" AND " + Constants.ActiveDate + " = ? ");
			updateSQL.append(" AND " + Constants.CompleteDate + " IS NULL ");

			psmtUpdate = conn.prepareStatement(updateSQL.toString());

			psmtUpdate.setNull(1, Types.DATE);
			
			if (employeeId != null)
				psmtUpdate.setString(2, employeeId);
			else
				psmtUpdate.setNull(2, Types.VARCHAR);
			
			psmtUpdate.setLong(3, processId);
			psmtUpdate.setString(4, workItemName);
			psmtUpdate.setTimestamp(5, new Timestamp(activeDate.getTime()));
			int updateCount = psmtUpdate.executeUpdate();
			
			if (updateCount == 0)
			{
				SQLWarning warning = psmtUpdate.getWarnings();
				
				if (warning != null)
					printWarnings(warning);
			}
			return updateCount;
		} finally {
			Connect.closePSMT(psmtUpdate);
			Connect.closeConnection(conn);
		}
	}

	private void printWarnings(SQLWarning warning) {
		StringBuffer buff = new StringBuffer();
		SQLWarning currentWarning = warning;
		
		while (currentWarning != null)
		{
			buff.append("Warning: " + currentWarning.getClass().getName() + "; message: " + currentWarning.getMessage() + "; error code: " + currentWarning.getErrorCode());
			
			currentWarning = currentWarning.getNextWarning();
			
			if (currentWarning != null)
				buff.append(System.getProperty("line.separator"));
		}
		
		log.warn(buff.toString());
	}

}
