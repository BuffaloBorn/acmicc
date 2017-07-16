package com.epm.acmi.struts;
/**
 * Application level Constants variable declarations 
 */
public interface Constants {

	public static final String prompts = "PROMPTS";
    public static final String docTypes = "DOCTYPES";
    public static final String stellentProerties = "STELLENT";
    public static final String savedSearchResult = "SSRS";
    public static final String users = "ADUSERS";
    public static final String loggedUser = "USER";
    public static final String constantDate = "1/1/1900"; 
    public static final String docMetaData = "DOCMETADATA";
    public static final String appdata ="appdata";
    //public static final String searchNamePrefix = "epmSearch";
    public static final String gfid = "GFID";
    //public static final String documentUploadURL ="http://10.1.210.28:49950/acmicc/upload/";
    public static final String expGFID ="HEP";
    public static final String noGFID ="NOGFID";
    public static final String delGFID ="DEL";
    public static final String recId ="RECID";
    public static final String lucId ="LUCID";
    public static final String reassignkey="reassignKey";
    public static final String workItemId="wid";
    public static final String allDocsSearchName= "epm_AllDocs_AllFields";
    public static final String applicantLevelDocs = "MRI MED EXM LAB HIV QST PHY MVR";
    public static final String checkEFTDocs = "CHK EFT";
    public static final String checkLBSLBI = "LBS LBI";
    public static final String expPolicyNum="999999999999999";
    public static final String NEWBIZMain = "NEWBIZMain";
    public static final String SuspendActivityName = "Suspend";
    public static final String SuspendActivityUser = "Suspender";
    
    // Details PAGE

    public static final String taskName= "taskName";
    public static final String Rewrite = "Rewrite";
    public static final String keyAppFN = "keyAppFN";
	public static final String keyApplicantSuffix= "KeyAppSuffix";
   	public static final String keyApplicantFirstName= "KeyAppFirstName";
	public static final String keyApplicantLastName= "KeyAppLastName";
	public static final String keyApplicantMI= "KeyAppMI";
	public static final String keyApplicantDOB ="KeyAppDOB";
	public static final String keyApplicantSSN ="KeyAppSSN";
	public static final String applicantSuffix= "AppSuffix";
   	public static final String applicantFirstName= "AppFirstName";
	public static final String applicantLastName= "AppLastName";
	public static final String applicantMI= "AppMI";
	public static final String applicantDOB = "AppDOB";
	public static final String eft= "EFT";
	public static final String cfp = "CFP";
	public static final String checkMaker =  "CheckMaker";
	public static final String lbid = "LBID";
	public static final String employer = "Employer";
	public static final String epmReviewed = "epmReviewed";
	public static final String docDescription = "DocDescription";
	public static final String docType = "DocType";
	public static final String docCode = "DocCode";
	public static final String agentName= "AgentName";
	public static final String agentNumber= "AgentNumber";
	public static final String policyNumber= "PolicyNo";
	public static final String policyNumberUDA = "PolicyNumber";
	public static final String listBill= "IASListBill";
	public static final String receivedDateString= "ReceivedDate";
	public static final String uwInitail= "UWInitial";
	//public static final String networkId= "Network";
	public static final String shortTermPolicy= "ShortTermAppNo";
	public static final String screenDateString= "ScreenDate";
	public static final String screenBy= "ScreenBy";
	public static final String state= "State";
	public static final String status= "Status";
	public static final String productName= "Product";
	public static final String fundedHSA= "FundedHSA";
	public static final String planId= "PlanId";
	public static final String network= "Network";
	public static final String deductible= "Deductible";
	public static final String coInsurance= "CoInsurance";
	public static final String coPay= "CoPay";
	public static final String pharmaProfile= "PharmaRequestedBy";
	public static final String noResultsFound= "NoResultsFound";
	public static final String reasonCode= "AOTSReasonCode";
	public static final String returnCode= "ReturnReasonCode";
	public static final String lastUpdatedDateTime="LastUpdatedDateTime";
	public static final String lastUpdatedUserId="LastUpdatedUserId";


	// DB Columns IUAActivites
	public static final String AcceptDate="AcceptDate";
	public static final String ActiveDate="ActiveDate";
	public static final String PID="PID";
	public static final String ActivityName="ActivityName";
	public static final String EmployeeID="EmployeeID";
	public static final String CompleteDate="CompleteDate";

	//public static final String autoDecline= "AutoDecline";
	public static final String memoAttachReqd = "memoAttachReqd";
	public static final String memoNoAttachReqd = "memoNoAttachReqd";
	public static final String paraMedReqd = "paraMedReqd";
	public static final String dmvReqd = "dmvReqd";
	public static final String medRecReqd = "medRecReqd";
	public static final String medConsultReqd = "medConsultReqd";
	//public static final String numberOfDaysRefund="__atmr_Refund_Timer_time";
	public static final String numberOfDaysRefund="refundTimerDays";
	public static final String declineIndividual = "declineIndividual";
	public static final String manuallyStarted = "manuallyStarted";
	public static final String openProcessesExist = "openProcessesExist";
	public static final String memoAttachedYesNo = "memoAttachedYesNo";
	public static final String Waiting_on_addl_paramed_exam ="Waiting_on_addl_paramed_exam";
	public static final String Waiting_on_addl_memo = "Waiting_on_addl_memo";
	public static final String Waiting_on_addl_med_rec = "Waiting_on_addl_med_rec";
	public static final String Waiting_on_addl_quote = "Waiting_on_addl_quote";

	// UDA s
	public static final String addlInfo = "addl Info";

	// Task Names
	public static final String screenApplication = "Screen Application";
	public static final String enterApplication = "Enter Application";
	public static final String processDIW = "Process D/I/W Letter";
	public static final String initiateRXQ = "Initiate RX Query";
	public static final String reviewApp = "Review Application";
	public static final String MAKE_UNDERWRITING_DECISION = "Make Underwriting Decision";

	public static final String declineApplication = "";
	//public static final String memoDocProc ="MEMO";
	//public static final String paramedDocProc ="PM";
	//public static final String dmvDocProc = "DMV";
	//public static final String medRecDocProcPId = "MEDREC";
	//public static final String declineDocProcPId = "DECL";
	public static final String recordIncomingDocPrefix = "Record Incoming";
	public static final String reviewIncomingDocPrefix = "Review Incoming";
	//public static final String examDocProcId = "EXAM";
	public static final String epmCode_exam = "EXAM";
	public static final String epmCode_dmv = "DMV";
	public static final String epmCode_memo = "MEMO";
	public static final String epmCode_medRec = "MRREC";
	public static final String epmCode_quote = "QUOT";
	public static final String epmCode_decl = "DECL";
	public static final String epmCode_mrreq = "MRREQ";
	public static final String stellentCode_exam = "EXM";
	public static final String stellentCode_memo = "MEMO";
	public static final String stellentCode_medRec = "MED";
	public static final String stellentCode_dmv = "MVR";
	public static final String stellentCode_decl = "N/A";

	// Shared Info and Memos and Amendments
	public static final String	shared_ADEC = "ADEC ";
	public static final String	memosAmend_ANCH = "ANCH ";
	public static final String	memosAmend_AOA  = "AOA  ";
	public static final String	shared_DNP  = "DNP  ";
	public static final String	memosAmend_GAL  = "GAL  ";
	public static final String	memosAmend_MAOT = "MAOT ";
	public static final String	memosAmend_MDI  = "MDI  ";
	public static final String	memosAmend_MMI  = "MMI  ";
	public static final String	memosAmend_MOI  = "MOI  ";
	public static final String	shared_RCA  = "RCA  ";
	public static final String	shared_RPR  = "RPR  ";
	public static final String	memosAmend_SIA  = "SIA  ";
	public static final String	memosAmend_SIAD = "SIAD ";
	public static final String	memosAmend_SIAG = "SIAG ";
	public static final String	shared_SIOT = "SIOT ";


	public static final String KEY_APPLICANT = "Key Applicant";
	public static final String DEPENDANT_APPLICANT="Dependent";
	public static final String SPOUCE_APPLICANT="Spouse";
	
	public static final String KEY_APPLICANT_CODE = "K";
	public static final String DEPENDANT_APPLICANT_CODE="D";
	public static final String SPOUCE_APPLICANT_CODE="S";
	
	//JSP displaying session variable names
	public static final String SHOW_NURSE_CONSULTANT = "showNurseConsultant";
	public static final String SHOW_INTERVIEWEE_NAME = "showIntervieweeName";
	public static final String SHOW_UNABLE_TO_COMPLETE_REASON = "showUnableToCompleteReason";
	public static final String SHOW_AND_ENABLE_FOLLOW_UP_DATE_AND_TIME = "showAndEnableFollowUpDateAndTime";
	public static final String SHOW_SCHEDULED_DATE_TIME = "showScheduledDateTime";
	
	//JSP enabling session variable names
	public static final String ENABLE_NURSE_CONSULTANT = "enableNurseConsultant";
	public static final String ENABLE_UNABLE_TO_COMPLETE_REASON = "enableUnableToCompleteReason";

	//EPM Telephonic Interview Process Names
	public static final String EPM_CODE_INITIAL_INTERVIEW = "ACMI_Initial_Interview";
	public static final String EPM_CODE_FOLLOW_UP_INTERVIEW = "ACMI_FollowUp_Interview";
	public static final String EPM_CODE_INTERVIEW_COMMON = "ACMI_Interview_Common";
	public static final String EPM_CODE_DOCUMENT_DECISION = "ACMI_Document_Decision";
	

	//DB Telephonic Interview Process Names
	public static final String DB_CODE_INITIAL_INTERVIEW = "ININT";
	public static final String DB_CODE_FOLLOW_UP_INTERVIEW = "FUINT";
	public static final String DB_CODE_INTERVIEW_COMMON = "INTCM";
	public static final String DB_CODE_DOCUMENT_DECISION = "DOCDE";
	public static final String DB_CODE_ACMI_QUALITY_ASSURANCE = "QUAL";
	public static final String DB_CODE_ACMI_MANUAL_ACTIVATE = "MA";

	//EPM Telephonic Interview Nurse Consultant Activity Names

	public static final String EPM_ACTIVITY_CONDUCT_INITIAL_INTERVIEW = "Conduct Initial Interview";
	public static final String EPM_ACTIVITY_CONDUCT_FOLLOW_UP_INTERVIEW = "Conduct Follow Up Interview";
	public static final String EPM_ACTIVITY_REVISE_INTERVIEW = "Revise Interview";

	public static final String EPM_ACTIVITY_FOLLOW_UP_REQD_ARROW = "Follow Up Reqd";
	public static final String EPM_ACTIVITY_INTERVIEW_COMPLETE_ARROW = "Interview Complete";
	public static final String EPM_ACTIVITY_UNABLE_TO_COMPLETE_ARROW = "Unable To Complete";
	
	//Interview Request Status Values
	public static final String INTERVIEW_REQUEST_STATUS_NEW = "New";
	public static final String INTERVIEW_REQUEST_STATUS_ORDERED = "Ordered";
	public static final String INTERVIEW_REQUEST_STATUS_FOLLOW_UP = "Follow Up";
	public static final String INTERVIEW_REQUEST_STATUS_ABORTED = "Aborted";
	public static final String INTERVIEW_REQUEST_STATUS_FINISHED = "Finished";
	
	public static final String SHOW_OTHER_PROC_EXISTS_SESSION = "showOTHERPROCEXISTSession";
	public static final String SHOW_RECORD_INCOMING_QUOTE_SESSION = "showRecordIncomingQUOTESession";
	public static final String SHOW_RECORD_INCOMING_MEDREC_SESSION = "showRecordIncomingMEDRECSession";
	public static final String SHOW_RECORD_INCOMING_EXAM_SESSION = "showRecordIncomingEXAMSession";
	public static final String SHOW_RECORD_INCOMING_MEMO_SESSION = "showRecordIncomingMEMOSession";
	public static final String SHOW_MANUALLY_STARTED_SESSION = "showManuallyStartedSession";
	public static final String SHOW_DECLINE_TEMPLATE_NUMBER_OF_DAYS = "showDeclineTemplateNumberOfDays";
	public static final String SHOW_ADDITIONAL_INFO_UDAS_SESSION = "showAdditionalInfoUDAsSession";
	public static final String IS_INTERVIEW_TAB_READ_ONLY = "isInterviewTabReadOnly";
	//UDA to tell EPM that we need to start interview processes
	public static final String PHONE_INTERVIEW_REQD = "phoneInterviewReqd";
	//UDA to tell EPM process which phone interview id we need to update with the process instance id
	//when we start the new follow up process
	public static final String FOLLOW_UP_INTERVIEW_ID = "followUpInterviewId";
	public static final String FOLLOW_UP_DATE_TIME_TIMER = "__atmr_Follow Up Date_time";
	public static final String FOLLOW_UP_DATE_TIME = "followUpDateTime";
	
	//Nurse Consultant Activity Choices
	public static final String INTERVIEW_ARROW_FOLLOW_UP_REQD = "Follow Up Reqd";
	public static final String INTERVIEW_ARROW_INTERVIEW_COMPLETE = "Interview Complete";
	public static final String INTERVIEW_ARROW_UNABLE_TO_COMPLETE = "Unable To Complete";
	
	//EPM Roles
	public static final String IUPS_UW_ROLE = "UW";
	
	//Disposition Codes
	public static final String COMPLETE_DISP_CODE = "01";
	public static final String UNABLE_TO_COMPLETE_DISP_CODE = "02";
	public static final String FOLLOW_UP_DISP_CODE = "03";
	
	public static final String NOTSELECTED = "Not Selected";
	public static final String NOT = "Not";
	public static final String SELECTED = "Selected";
	
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	//IAS variable
	public static final String IASpolicyNumber= "IASPolicyNo";
	public static final String IAStaskName= "IAStaskName";
	public static final String IASkeyInsured = "IASkeyInsured";
	public static final String IASkeyInsuredId = "IASkeyInsuredId";
	public static final String IASModify = "IASModify";
	public static final String IASDiaryModify = "IASDiaryModify";
	public static final String IASuser = "IASuser";
	
	//LDAP
	public static final String INITIAL_CONTEXT_FACTORY = "INITIAL_CONTEXT_FACTORY";
	public static final String PROVIDER_URL = "PROVIDER_URL";
	public static final String SECURITY_PRINCIPAL = "SECURITY_PRINCIPAL";
	public static final String SEARCH_BASE = "SEARCH_BASE";
	public static final String EPM_SEARCH_BASE = "EPM_SEARCH_BASE";
	public static final String GLOBAL_SEARCH_BASE = "GLOBAL_SEARCH_BASE";
}