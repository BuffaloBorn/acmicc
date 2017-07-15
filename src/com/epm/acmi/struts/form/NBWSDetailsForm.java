package com.epm.acmi.struts.form;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.cc.framework.adapter.struts.FWActionForm;
import com.cc.framework.ui.model.OptionListDataModel;

/**
 * NBWSDetailsForm used in details page
 * @author Rajeev Chara
 *
 */
public class NBWSDetailsForm extends FWActionForm {

	private static final long serialVersionUID = 1L;

	private String pastActivities;

	private String pastActions;

	protected String taskName;

	protected long taskId;

	// actual
	private String keyApplicantSuffix;

	private String keyApplicantFirstName;

	private String keyApplicantLastName;

	private String keyApplicantMI;

	private String eft;

	private String agentName;	

	private String agentNumber;

	private String policyNumber;

	private String listBill;

	private Date receivedDate;

	private String receivedDateString;

	private String uwInitail;

	private String shortTermPolicy;

	private Date screenDate;

	private String screenDateString;

	private String screenBy;

	private String state;

	private String status;

	private String productName;

	private String fundedHSA = "N";

	private boolean fundedHSAboolean;

	private String planId;

	private String network;

	private String deductible;

	private String coInsurance;

	private String coPay;

	private String pharmaProfile;

	private String noResultsFound;

	private String reasonCode;

	private String returnCode;

	// private Date lastUpdatedDateTime;
	private Timestamp lastUpdatedDateTime;

	private String lastUpdatedDateTimeString;
	
	private String interviewScheduledDateTimeString;

	private String buttonSelected;

	// private String

	// UDA Properties for Addl Info - Specify Additional Info
	private boolean memoAttachReqd;

	private boolean memoNoAttachReqd;

	private boolean paraMedReqd;

	private boolean dmvReqd;

	private boolean medRecReqd;

	private boolean medConsultReqd;

	private boolean declineIndividual;

	private boolean manuallyStarted;

	private String memoAttachedYesNo;

	private boolean recordIncomingMEMO;

	private boolean recordIncomingEXAM;

	private boolean recordIncomingMEDREC;

	private boolean recordIncomingQUOTE;
	//
	private String numberOfDaysRefund;

	private String rewrite;

	// Whether to show the Specify Additional Info or not
	private boolean showAdditionalInfoUDAs;

	//

	// HashMaps
	private TreeMap statesMap;

	private TreeMap statusMap;

	private TreeMap productsMap;

	private TreeMap otsReasonMap;

	private TreeMap returnReasonMap;

	private TreeMap numberOfDaysRefundMap;

	private TreeMap unableToCompleteReasonMap;
	

	//

	// OLD STUFF FROM WORKLIST
	private Date ApplicationDate;

	private String processName;

	private Date createdDate;

	private Date dueDate;

	private String assignee;

	private String createdDateString;

	private String electronicApp;//usr7930
	
	//Telephonic Interview
	private String initialInterviewNurseConsultant;
	
	private String intervieweeName;
	
	private String followUpDateString;
	
	private String followUpTimeString;
	
	private Date followUpDateTime;
	
	private String unableToCompleteReason;
	
	private boolean phoneInterviewRequired;
	
	private OptionListDataModel nurseConsultantList = null;
	//Telephonic Interview
	
	public NBWSDetailsForm() {
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest request) {
		super.reset(actionMapping, request);
		setMemoAttachReqd(false);
		setMemoNoAttachReqd(false);
		setParaMedReqd(false);
		setDmvReqd(false);
		setMedConsultReqd(false);
		setMedRecReqd(false);
		setFundedHSAboolean(false);
		setPhoneInterviewRequired(false);
	}

	/*
	 * Usr 7930
	 */
	public String getElectronicApp() {
		return electronicApp;
	}

	public void setElectronicApp(String electronicApp) {
		this.electronicApp = electronicApp;
	}

	//End USR 7930
	public String getPastActions() {
		return pastActions;
	}

	public void setPastActions(String pastActions) {
		this.pastActions = pastActions;
	}

	public String getPastActivities() {
		return pastActivities;
	}

	public void setPastActivities(String pastActivities) {
		this.pastActivities = pastActivities;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentNumber() {
		return agentNumber;
	}

	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}

	public Date getApplicationDate() {
		return ApplicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		ApplicationDate = applicationDate;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedDateString() {
		return createdDateString;
	}

	public void setCreatedDateString(String createdDateString) {
		this.createdDateString = createdDateString;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getEft() {
		return eft;
	}

	public void setEft(String eft) {
		this.eft = eft;
	}

	public String getFundedHSA() {
		if (fundedHSA == null)
			fundedHSA = "N";
		return fundedHSA;
	}

	public void setFundedHSA(String fundedHSA) {

		this.fundedHSA = fundedHSA;

		if (fundedHSA != null) {
			if (fundedHSA.equalsIgnoreCase("Y")) {
				this.setFundedHSAboolean(true);
			}
		} else {
			this.setFundedHSAboolean(false);
		}
	}

	public String getKeyApplicantFirstName() {
		return keyApplicantFirstName;
	}

	public void setKeyApplicantFirstName(String keyApplicantFirstName) {
		this.keyApplicantFirstName = keyApplicantFirstName;
	}

	public String getKeyApplicantLastName() {
		return keyApplicantLastName;
	}

	public void setKeyApplicantLastName(String keyApplicantLastName) {
		this.keyApplicantLastName = keyApplicantLastName;
	}

	public String getKeyApplicantMI() {
		return keyApplicantMI;
	}

	public void setKeyApplicantMI(String keyApplicantMI) {
		this.keyApplicantMI = keyApplicantMI;
	}

	public String getKeyApplicantSuffix() {
		return keyApplicantSuffix;
	}

	public void setKeyApplicantSuffix(String keyApplicantSuffix) {
		this.keyApplicantSuffix = keyApplicantSuffix;
	}

	public String getListBill() {
		return listBill;
	}

	public void setListBill(String listBill) {
		this.listBill = listBill;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getReceivedDateString() {
		return receivedDateString;
	}

	public void setReceivedDateString(String receivedDateString) {
		this.receivedDateString = receivedDateString;
	}

	public String getScreenBy() {
		return screenBy;
	}

	public void setScreenBy(String screenBy) {
		this.screenBy = screenBy;
	}

	public Date getScreenDate() {
		return screenDate;
	}

	public void setScreenDate(Date screenDate) {
		this.screenDate = screenDate;
	}

	public String getScreenDateString() {
		return screenDateString;
	}

	public void setScreenDateString(String screenDateString) {
		this.screenDateString = screenDateString;
	}

	public String getShortTermPolicy() {
		return shortTermPolicy;
	}

	public void setShortTermPolicy(String shortTermPolicy) {
		this.shortTermPolicy = shortTermPolicy;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getUwInitail() {
		return uwInitail;
	}

	public void setUwInitail(String uwInitail) {
		this.uwInitail = uwInitail;
	}

	public String getCoInsurance() {
		return coInsurance;
	}

	public void setCoInsurance(String coInsurance) {
		this.coInsurance = coInsurance;
	}

	public String getCoPay() {
		return coPay;
	}

	public void setCoPay(String coPay) {
		this.coPay = coPay;
	}

	public String getDeductible() {
		return deductible;
	}

	public void setDeductible(String deductible) {
		this.deductible = deductible;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getNoResultsFound() {
		return noResultsFound;
	}

	public void setNoResultsFound(String noResultsFound) {
		this.noResultsFound = noResultsFound;
	}

	public String getPharmaProfile() {
		return pharmaProfile;
	}

	public void setPharmaProfile(String pharmaProfile) {
		this.pharmaProfile = pharmaProfile;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public TreeMap getOtsReasonMap() {
		return otsReasonMap;
	}

	public void setOtsReasonMap(TreeMap otsReasonMap) {
		this.otsReasonMap = otsReasonMap;
	}

	public TreeMap getProductsMap() {
		return productsMap;
	}

	public void setProductsMap(TreeMap productsMap) {
		this.productsMap = productsMap;
	}

	public TreeMap getReturnReasonMap() {
		return returnReasonMap;
	}

	public void setReturnReasonMap(TreeMap returnReasonMap) {
		this.returnReasonMap = returnReasonMap;
	}

	public TreeMap getStatesMap() {
		return statesMap;
	}

	public void setStatesMap(TreeMap statesMap) {
		this.statesMap = statesMap;
	}

	public TreeMap getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(TreeMap statusMap) {
		this.statusMap = statusMap;
	}

	// public Date getLastUpdatedDateTime() {
	// return lastUpdatedDateTime;
	// }
	//
	// public void setLastUpdatedDateTime(Date lastUpdatedDateTime) {
	// lastUpdatedDateTime = lastUpdatedDateTime;
	// this.lastUpdatedDateTimeString = DateUtility.convertDateToDateString(lastUpdatedDateTime);
	// }

	public String getButtonSelected() {
		return buttonSelected;
	}

	public void setButtonSelected(String buttonSelected) {
		this.buttonSelected = buttonSelected;
	}

	public boolean isDmvReqd() {
		return dmvReqd;
	}

	public void setDmvReqd(boolean dmvReqd) {
		this.dmvReqd = dmvReqd;
	}

	public boolean isMedConsultReqd() {
		return medConsultReqd;
	}

	public void setMedConsultReqd(boolean medConsultReqd) {
		this.medConsultReqd = medConsultReqd;
	}

	public boolean isMedRecReqd() {
		return medRecReqd;
	}

	public void setMedRecReqd(boolean medRecReqd) {
		this.medRecReqd = medRecReqd;
	}

	public boolean isMemoAttachReqd() {
		return memoAttachReqd;
	}

	public void setMemoAttachReqd(boolean memoAttachReqd) {
		this.memoAttachReqd = memoAttachReqd;
	}

	public boolean isMemoNoAttachReqd() {
		return memoNoAttachReqd;
	}

	public void setMemoNoAttachReqd(boolean memoNoAttachReqd) {
		this.memoNoAttachReqd = memoNoAttachReqd;
	}

	public boolean isParaMedReqd() {
		return paraMedReqd;
	}

	public void setParaMedReqd(boolean paraMedReqd) {
		this.paraMedReqd = paraMedReqd;
	}

	public boolean isShowAdditionalInfoUDAs() {
		return showAdditionalInfoUDAs;
	}

	public void setShowAdditionalInfoUDAs(boolean showAdditionalInfoUDAs) {
		this.showAdditionalInfoUDAs = showAdditionalInfoUDAs;
	}

	public boolean isFundedHSAboolean() {
		return fundedHSAboolean;
	}

	public void setFundedHSAboolean(boolean fundedHSAboolean) {
		this.fundedHSAboolean = fundedHSAboolean;
	}

	public TreeMap getNumberOfDaysRefundMap() {
		return numberOfDaysRefundMap;
	}

	public void setNumberOfDaysRefundMap(TreeMap numberOfDaysRefundMap) {
		this.numberOfDaysRefundMap = numberOfDaysRefundMap;
	}

	public String getNumberOfDaysRefund() {
		return numberOfDaysRefund;
	}

	public void setNumberOfDaysRefund(String numberOfDaysRefund) {
		this.numberOfDaysRefund = numberOfDaysRefund;
	}

	public boolean isDeclineIndividual() {
		return declineIndividual;
	}

	public void setDeclineIndividual(boolean declineIndividual) {
		this.declineIndividual = declineIndividual;
	}

	public boolean isManuallyStarted() {
		return manuallyStarted;
	}

	public void setManuallyStarted(boolean manuallyStarted) {
		this.manuallyStarted = manuallyStarted;
	}

	public String getMemoAttachedYesNo() {
		return memoAttachedYesNo;
	}

	public void setMemoAttachedYesNo(String memoAttachedYesNo) {
		this.memoAttachedYesNo = memoAttachedYesNo;
	}

	public String getLastUpdatedDateTimeString() {
		return lastUpdatedDateTimeString;
	}

	public void setLastUpdatedDateTimeString(String lastUpdatedDateTimeString) {
		this.lastUpdatedDateTimeString = lastUpdatedDateTimeString;
	}

	public Timestamp getLastUpdatedDateTime() {
		return lastUpdatedDateTime;
	}

	public void setLastUpdatedDateTime(Timestamp lastUpdatedDateTime) {
		this.lastUpdatedDateTime = lastUpdatedDateTime;
		this.lastUpdatedDateTimeString = lastUpdatedDateTime.toString();
	}

	public boolean isRecordIncomingEXAM() {
		return recordIncomingEXAM;
	}

	public void setRecordIncomingEXAM(boolean recordIncomingEXAM) {
		this.recordIncomingEXAM = recordIncomingEXAM;
	}

	public boolean isRecordIncomingMEDREC() {
		return recordIncomingMEDREC;
	}

	public void setRecordIncomingMEDREC(boolean recordIncomingMEDREC) {
		this.recordIncomingMEDREC = recordIncomingMEDREC;
	}

	public boolean isRecordIncomingMEMO() {
		return recordIncomingMEMO;
	}

	public void setRecordIncomingMEMO(boolean recordIncomingMEMO) {
		this.recordIncomingMEMO = recordIncomingMEMO;
	}

	public boolean isRecordIncomingQUOTE() {
		return recordIncomingQUOTE;
	}

	public void setRecordIncomingQUOTE(boolean recordIncomingQUOTE) {
		this.recordIncomingQUOTE = recordIncomingQUOTE;
	}

	public String getRewrite() {
		return rewrite;
	}

	public void setRewrite(String rewrite) {
		this.rewrite = rewrite;
	}	
	
	public String getInitialInterviewNurseConsultant() {
		return initialInterviewNurseConsultant;
	}

	public void setInitialInterviewNurseConsultant(
			String initialInterviewNurseConsultant) {
		this.initialInterviewNurseConsultant = initialInterviewNurseConsultant;
	}

	public String getIntervieweeName() {
		return intervieweeName;
	}

	public void setIntervieweeName(String intervieweeName) {
		this.intervieweeName = intervieweeName;
	}

	public Date getFollowUpDateTime() {
		return followUpDateTime;
	}

	public void setFollowUpDateTime(Date followUpDateTime) {
		this.followUpDateTime = followUpDateTime;
	}

	public String getUnableToCompleteReason() {
		return unableToCompleteReason;
	}

	public void setUnableToCompleteReason(String unableToCompleteReason) {
		this.unableToCompleteReason = unableToCompleteReason;
	}

	public boolean isPhoneInterviewRequired() {
		return phoneInterviewRequired;
	}

	public void setPhoneInterviewRequired(boolean phoneInterviewRequired) {
		this.phoneInterviewRequired = phoneInterviewRequired;
	}

	public TreeMap getUnableToCompleteReasonMap() {
		return unableToCompleteReasonMap;
	}

	public void setUnableToCompleteReasonMap(TreeMap unableToCompleteReasonMap) {
		this.unableToCompleteReasonMap = unableToCompleteReasonMap;
	}

	public String getFollowUpDateString() {
		return followUpDateString;
	}

	public void setFollowUpDateString(String followUpDateString) {
		this.followUpDateString = followUpDateString;
	}

	public String getFollowUpTimeString() {
		return followUpTimeString;
	}

	public void setFollowUpTimeString(String followUpTimeString) {
		this.followUpTimeString = followUpTimeString;
	}

	public OptionListDataModel getNurseConsultantList() {
		return nurseConsultantList;
	}

	public void setNurseConsultantList(OptionListDataModel nurseConsultantList) {
		this.nurseConsultantList = nurseConsultantList;
	}

	public String getInterviewScheduledDateTimeString() {
		return interviewScheduledDateTimeString;
	}

	public void setInterviewScheduledDateTimeString(
			String interviewScheduledDateTimeString) {
		this.interviewScheduledDateTimeString = interviewScheduledDateTimeString;
	}
}
