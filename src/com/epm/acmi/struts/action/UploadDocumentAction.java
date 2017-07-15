package com.epm.acmi.struts.action;

/**
 * $Header: /usr/local/cvsroot/acmiroot/acmicc/src/com/epm/acmi/struts/action/UploadDocumentAction.java,v 1.7 2005/12/16
 * 00:35:29 jhombal Exp $ $Revision: 1.19 $ $Date: 2006/05/15 11:07:31 $
 */

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.Optika.message.SavedSearchPrompt;
import com.cc.acmi.common.User;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.AlignmentType;
import com.cc.framework.ui.FormType;
import com.cc.framework.ui.ImageMap;
import com.cc.framework.ui.OrientationType;
import com.cc.framework.ui.control.FormButtonContainer;
import com.cc.framework.ui.control.FormButtonElement;
import com.cc.framework.ui.control.FormControl;
import com.cc.framework.ui.control.FormControlElement;
import com.cc.framework.ui.control.FormGroupElement;
import com.cc.framework.ui.control.SelectControl;
import com.cc.framework.ui.control.TextControl;
import com.cc.framework.ui.model.ClientEvent;
import com.cc.framework.ui.model.FormDesignModel;
import com.cc.framework.ui.model.FormLabelDesignModel;
import com.cc.framework.ui.model.ListDataModel;
import com.cc.framework.ui.model.OptionListDesignModel;
import com.cc.framework.ui.model.SelectDesignModel;
import com.cc.framework.ui.model.TextDesignModel;
import com.cc.framework.ui.model.imp.FormDesignModelImp;
import com.cc.framework.ui.model.imp.FormLabelDesignModelImp;
import com.cc.framework.ui.model.imp.ImageModelImp;
import com.cc.framework.ui.model.imp.OptionListDesignModelImp;
import com.cc.framework.ui.model.imp.SelectDesignModelImp;
import com.cc.framework.ui.model.imp.TextDesignModelImp;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.DocumentMetaDataForm;
import com.epm.acmi.struts.form.dsp.SimpleList;
import com.epm.acmi.struts.form.dsp.SimpleType;
import com.epm.acmi.struts.form.dsp.StellentClient;
import com.epm.acmi.util.ACMICache;
import com.epm.acmi.util.LocalProperties;
import com.epm.acmi.util.StellentUpdateAudit;

/**
 * This examples shows how to create the designmodel for a form server side.
 * 
 * @author Jay Hombal
 * @version $Revision: 1.19 $
 */
public class UploadDocumentAction extends CCAction {

	private static final String PDF_OUTPUT_DIR = "PDF_OUTPUT_DIR";

	// specify a width for the first/second label column
	String width1Colum = Integer.toString(160);

	String script = "this.blur();";

	String style = "background-color:#E9E2E1";

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	private static Logger log = Logger.getLogger(UploadDocumentAction.class);

	/**
	 * Constructor
	 */
	public UploadDocumentAction() {
		super();
	}
	
	

	/**
	 * @see com.cc.framework.adapter.struts.FrameworkAction#doExecute(com.cc.framework.adapter.struts.ActionContext)
	 */
	
	/**
	 * This method is called by Controller Servlet. This method is specific to Common Controls API
	 * 
	 * @param ctx
	 *            ActionContext
	 * @throws java.lang.Exception
	 */
	public void doExecute(ActionContext ctx) throws Exception {
		log.debug("Begin doExecute");
		long t = System.currentTimeMillis();
		log.debug("Start - " + t);
		DocumentMetaDataForm dmf = (DocumentMetaDataForm) ctx.form();
		String docCode = (String) ctx.request().getAttribute("docType");
		if (docCode == null) {
			docCode = dmf.getDocCode();
		}
		if (docCode != null) {

			SavedSearchPrompt ssps[] = null;
			String searchName = Constants.allDocsSearchName;
			FormControl control = new FormControl();
			ssps = ACMICache.getSavedSearchPrompts(searchName);
			control.setDesignModel(getDesignModel(ssps, docCode, dmf));
			ctx.session().setAttribute("uploadFormControl", control);

		}
		log.debug("Time taken " + (System.currentTimeMillis() - t));
		// forward to the jsp page
		log.debug("End  doExecute");
		ctx.forwardToInput();
	}

	/**
	 * Creates the DesignModel for our form
	 * 
	 * @return The designmodel for the form
	 * @param ssps
	 * @param docTypeCode
	 * @param dmf
	 * @return
	 */
	private FormDesignModel getDesignModel(SavedSearchPrompt ssps[], String docTypeCode, DocumentMetaDataForm dmf) {

		ImageMap imageMap = new ImageMap();
		imageMap.addImage("warning", new ImageModelImp("fw/def/image/severity/imgWarning13x13.gif", 13, 13));
		imageMap.addImage("info", new ImageModelImp("fw/def/image/severity/imgInfo13x13.gif", 13, 13));
		imageMap.addImage("lnkExternal", new ImageModelImp("fw/def/image/severity/lnkExternal.gif", 13, 13));
		boolean isotherEditable = false;
		boolean isapplicantEditable = false;
		if (Constants.applicantLevelDocs.indexOf(docTypeCode) > 0) {
			isapplicantEditable = true;
			isotherEditable= false;
		} else
		{
			isotherEditable= true;
		}
		FormDesignModel form = new FormDesignModelImp();
		form.setFormId("frmEdit");

		form.setFormType(FormType.EDIT);
		form.setCaption("form.doc.metadata.edit");
		form.setWidth("900");
		form.setAlignment(AlignmentType.LEFT);
		form.setShowFrame(true);
		form.setImageMap(imageMap);

		FormGroupElement row = null;
		FormGroupElement section = new FormGroupElement();
		section.setTitle("form.doc.applicantion");
		section.setShowFrame(false);

		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		FormControlElement element = createSelectElement("form.doc.Product", "product", 30, 30, 2, true, ACMICache.getProductNameMap()
				, "key", "value", false);
		row.addFormElement(element);
		element = createSelectElement("form.doc.State", "state", 30, 30, 1, true, ACMICache.getStatesMap()
				, "key", "value", false);
		row.addFormElement(element);
		
		//createElement(row, "form.doc.State", "state", 2, 2, 1, true, true);
		//createElement(row, "form.doc.Product", "product", 8, 8, 1, true, true);
		section.addFormElement(row);

		
		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		createElement(row, "form.doc.PolicyNumber", "policyNumber", 15, 15, 1, true, false);
		createElement(row, "form.doc.AgentName", "agentName", 25, 25, 1, true, false);
		createElement(row, "form.doc.AgentNumber", "agentNumber", 5, 5, 1, true, false);

		section.addFormElement(row);

		//add row
		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		createElement(row, "form.doc.Employer", "employer", 25, 25, 1, isotherEditable, false);
		createElement(row, "form.doc.LBID", "lbid", 15, 15, 1, false, false);
		section.addFormElement(row);

		form.addFormElement(section);

		section = new FormGroupElement();
		section.setTitle("form.doc.key.applicant");
		section.setShowFrame(false);
		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		createElement(row, "form.doc.KeyAppLastName", "keyAppLastName", 20, 20, 1, true, false);
		createElement(row, "form.doc.KeyAppFirstName", "keyAppFirstName", 20, 20, 1, true, false);
		createElement(row, "form.doc.KeyAppMiddle", "keyAppMiddle", 1, 1, 1, true, false);
		createElement(row, "form.doc.KeyAppSuffix", "keyAppSuffix", 4, 4, 1, true, false);

		section.addFormElement(row);
		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		createElement(row, "form.doc.KeyAppSSN", "keyAppSSN", 9, 9, 1, true, false);
		createElement(row, "form.doc.KeyAppDOB", "keyAppDOB", 10, 10, 1, true, false);
		section.addFormElement(row);
		form.addFormElement(section);

		if (Constants.applicantLevelDocs.indexOf(docTypeCode) > 0) {
			isapplicantEditable = true;
		}
		section = new FormGroupElement();
		section.setTitle("form.doc.applicant");
		section.setShowFrame(false);

		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);

		createElement(row, "form.doc.AppLastName", "appLastName", 20, 20, 1, isapplicantEditable, false);
		createElement(row, "form.doc.AppFirstName", "appFirstName", 20, 20, 1, isapplicantEditable, false);
		createElement(row, "form.doc.AppMiddle", "appMiddle", 1, 1, 1, isapplicantEditable, false);
		createElement(row, "form.doc.AppSuffix", "appSuffix", 4, 4, 1, isapplicantEditable, false);

		section.addFormElement(row);

		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		createElement(row, "form.doc.AppDOB", "appDOB", 10, 10, 1, isapplicantEditable, false);
		section.addFormElement(row);
		form.addFormElement(section);

		section = new FormGroupElement();
		section.setTitle("form.doc.details");
		section.setShowFrame(false);

		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		ListDataModel ldl = getSimpleListDataModel();

		createSelectElement(row, "form.doc.epmReviewed", "epmReviewed", 1, 3, 1, true, ldl, "key", "value", false);
		createSelectElement(row, "form.doc.CFP", "cfp", 1, 3, 1, true, ldl, "key", "value", false);
		createElement(row, "form.doc.CheckMaker", "checkMaker", 20, 20, 1, isotherEditable, false);
		section.addFormElement(row);
		form.addFormElement(section);

		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);

		createElement(row, "form.doc.DocDescription", "docDescription", 35, 35, 2, true, false);
		section.addFormElement(row);

		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);

		section.addFormElement(row);

		// ------------------------------
		// Add a button section
		// ------------------------------
		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		row.setJoin(true);

		FormButtonContainer buttonsection = new FormButtonContainer();
		buttonsection.setColSpan(4);
		buttonsection.setShowFrame(true);
		buttonsection.setAlignment(AlignmentType.BASELINE);
		buttonsection.setDefaultButtonId("btnSave");

		FormButtonElement button2 = new FormButtonElement();
		button2 = new FormButtonElement();
		button2.setId("btnUpload");
		button2.setName("btnUpload");
		button2.setFilter(false);
		button2.setLocaleName("false");
		button2.setText("Upload");
		button2.setTooltip("Upload Document to Stellent");
		button2.setHandler(ClientEvent.ONCLICK, "return validateInput();");
		buttonsection.addFormElement(button2);

		row.addFormElement(buttonsection);

		FormButtonElement button = new FormButtonElement();
		button.setFilter(false);
		button.setId("btnCancel");
		button.setName("btnCancel");
		button.setText("button.title.cancel");
		button.setLocaleName("true");
		button.setTooltip("button.title.cancel");
		buttonsection.addFormElement(button);
		form.addFormElement(row);
		return form;
	}

	private void createElement(FormGroupElement row, String labelName, String property, int size, int width, int colspan,
			boolean isEditable, boolean required) {

		FormControlElement element = null;
		TextDesignModel text = null;
		FormLabelDesignModel label = null;

		label = new FormLabelDesignModelImp();
		label.setNowrap(new Boolean(true));
		label.setText(labelName);
		// label.setWidth(width1Colum);
		label.setLocaleName("true");

		element = new FormControlElement();
		element.setLabel(label);
		element.setRequired(required);

		text = new TextDesignModelImp();
		text.setProperty(property);
		text.setMaxLength(width);
		text.setSize(size);
		text.setTooltip("form.tooltip");
		text.setTooltip(property);
		text.setHandler(ClientEvent.ONCHANGE, "dataChanged();");

		if (!isEditable) {
			text.setDisabled(true);
			text.setStyle(style);
		}

		element.setColSpan(colspan);
		element.setControl(new TextControl(text));
		row.addFormElement(element);

	}

//	private void createCalendarElement(FormGroupElement row, String labelName, String property, int size, int width, int colspan,
//			boolean isEditable, boolean required) {
//
//		FormControlElement element = null;
//		FormLabelDesignModel label = null;
//
//		label = new FormLabelDesignModelImp();
//		label.setNowrap(new Boolean(true));
//		label.setText(labelName);
//		label.setLocaleName("true");
//		label.setWidth("40");
//		element = new FormControlElement();
//		element.setLabel(label);
//		element.setRequired(required);
//		CalendarDesignModel calendar = new CalendarDesignModelImp();
//		calendar.setHandler(ClientEvent.ONCHANGE, "dataChanged();");
//
//		calendar.setFormat("MM/dd/yyyy");
//		calendar.setFilter(true);
//		calendar.setMaxLength(10);
//		calendar.setSize(8);
//		calendar.setShowFormat("true");
//		calendar.setProperty(property);
//		calendar.setTooltip(property);
//
//		if (!isEditable) {
//			calendar.setDisabled(true);
//			calendar.setStyle(style);
//		}
//
//		calendar.setHandler(ClientEvent.ONCHANGE, "dataChanged();");
//
//		element.setControl(new CalendarControl(calendar));
//		element.setColSpan(colspan);
//		row.addFormElement(element);
//
//	}

	private void createSelectElement(FormGroupElement row, String labelName, String property, int size, int width, int colspan,
			boolean isEditable, ListDataModel ldm, String keyProperty, String valueProperty, boolean required) {

		FormControlElement element = null;
		FormLabelDesignModel label = null;
		SelectDesignModel select = null;
		label = new FormLabelDesignModelImp();
		label.setNowrap(new Boolean(true));
		label.setText(labelName);
		label.setLocaleName("true");
		label.setWidth("40");
		select = new SelectDesignModelImp();
		select.setMultiple(false);
		select.setProperty(property);
		select.setLocaleName("true");
		select.setHandler(ClientEvent.ONCHANGE, "dataChanged();");
		select.setTooltip(property);

		element = new FormControlElement();
		element.setLabel(label);
		element.setRequired(required);

		if (!isEditable) {
			select.setDisabled(false);
			select.setStyle(style);

		}
		SelectControl ctrl = new SelectControl(select);

		ctrl.setOptions(getOptionListDesignModel(keyProperty, valueProperty), ldm);
		element.setColSpan(colspan);
		element.setControl(ctrl);
		row.addFormElement(element);

	}

	private FormControlElement createSelectElement(String labelName, String property, int size, int width, int colspan,
			boolean isEditable, TreeMap ldm, String keyProperty, String valueProperty, boolean required) {

		FormControlElement element = null;
		FormLabelDesignModel label = null;
		SelectDesignModel select = null;
		label = new FormLabelDesignModelImp();
		label.setNowrap(new Boolean(true));
		label.setText(labelName);
		label.setLocaleName("true");

		select = new SelectDesignModelImp();
		select.setMultiple(false);
		select.setProperty(property);
		select.setLocaleName("true");
		select.setHandler(ClientEvent.ONCHANGE, "dataChanged();");
		select.setTooltip(property);

		element = new FormControlElement();
		element.setLabel(label);
		element.setRequired(required);

		if (!isEditable) {
			select.setDisabled(false);
			select.setStyle(style);

		}
		SelectControl ctrl = new SelectControl(select);

		ctrl.setOptions(getOptionListDesignModel(keyProperty, valueProperty), ldm);
		element.setColSpan(colspan);
		element.setControl(ctrl);
		return element;
	}
	
//	private FormControlElement createSelectElement(String labelName, String property, int size, int width, int colspan,
//			boolean isEditable, HashMap ldm, String keyProperty, String valueProperty, boolean required) {
//
//		FormControlElement element = null;
//		FormLabelDesignModel label = null;
//		SelectDesignModel select = null;
//		label = new FormLabelDesignModelImp();
//		label.setNowrap(new Boolean(true));
//		label.setText(labelName);
//		label.setLocaleName("true");
//
//		select = new SelectDesignModelImp();
//		select.setMultiple(false);
//		select.setProperty(property);
//		select.setLocaleName("true");
//		select.setHandler(ClientEvent.ONCHANGE, "dataChanged();");
//		select.setTooltip(property);
//
//		element = new FormControlElement();
//		element.setLabel(label);
//		element.setRequired(required);
//
//		if (!isEditable) {
//			select.setDisabled(false);
//			select.setStyle(style);
//
//		}
//		SelectControl ctrl = new SelectControl(select);
//	
//		ctrl.setOptions(getOptionListDesignModel(keyProperty, valueProperty), ldm);
//		element.setColSpan(colspan);
//		element.setControl(ctrl);
//		return element;
//	}
//	
	
	// private FormControlElement createSelectElement(String labelName, String property, int size, int width, int
	// colspan,
	// boolean isEditable, ListDataModel ldm, String keyProperty, String valueProperty, boolean required) {
	//
	// FormControlElement element = null;
	// FormLabelDesignModel label = null;
	// SelectDesignModel select = null;
	// label = new FormLabelDesignModelImp();
	// label.setNowrap(new Boolean(true));
	// label.setText(labelName);
	// label.setLocaleName("true");
	// label.setWidth("40");
	// select = new SelectDesignModelImp();
	// select.setMultiple(false);
	// select.setProperty(property);
	// select.setLocaleName("true");
	// select.setHandler(ClientEvent.ONCHANGE, "dataChanged();");
	// select.setTooltip(property);
	//
	// element = new FormControlElement();
	// element.setLabel(label);
	// element.setRequired(required);
	//
	// if (!isEditable) {
	// select.setDisabled(false);
	// select.setStyle(style);
	//
	// }
	// SelectControl ctrl = new SelectControl(select);
	//
	// ctrl.setOptions(getOptionListDesignModel(keyProperty, valueProperty), ldm);
	// element.setColSpan(colspan);
	// element.setControl(ctrl);
	// return element;
	// }

	/**
	 * Returns the Designmodel of the Optionlist for our Select element
	 * 
	 * @return OptionListDesignModel
	 */
	// private OptionListDesignModel getTreeOptionListDesignModel() {
	//
	// OptionListDesignModel optionList = new OptionListDesignModelImp();
	// optionList.setEmpty("<Please select>");
	// optionList.setFilter(true);
	// optionList.setKeyProperty("uniqueKey");
	// optionList.setLabelProperty("name");
	// optionList.setStyle("color: navy;");
	//
	// return optionList;
	// }
	private OptionListDesignModel getOptionListDesignModel(String labelKey, String propertyKey) {

		OptionListDesignModel optionList = new OptionListDesignModelImp();
		optionList.setFilter(true);
		optionList.setKeyProperty(labelKey);
		optionList.setLabelProperty(propertyKey);
		optionList.setStyle("color: navy;");

		return optionList;
	}

	// private OptionListDesignModel getListDesignModel() {
	//
	// OptionListDesignModel optionList = new OptionListDesignModelImp();
	// optionList.setFilter(true);
	// optionList.setKeyProperty("key");
	// optionList.setLabelProperty("title");
	// optionList.setStyle("color: navy;");
	// return optionList;
	// }

	protected ListDataModel getSimpleListDataModel() {

		SimpleType st[] = new SimpleType[2];

		st[0] = new SimpleType("N", "No");
		st[1] = new SimpleType("Y", "Yes");
		ListDataModel data = new SimpleList(st);
		return data;
	}

	/**
	 * Handles upload button event
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void upload_onClick(FormActionContext ctx) throws Exception {
		log.debug("Begin upload_onClick");
		String fileName = null;
		String contentType = null;
		FormFile myFile = null;
		//String AAID = null;
		try {
			log.debug("start upload " + System.currentTimeMillis());
			//User user = (User) ctx.session().getAttribute(Constants.loggedUser);
			//AAID = StellentClient.login(user);
			
			DocumentMetaDataForm dmf = (DocumentMetaDataForm) ctx.form();
			validateForm(dmf);
			
			// Process the FormFile
			myFile = dmf.getFile();
			contentType = myFile.getContentType();
			fileName = myFile.getFileName();
			int fileSize = myFile.getFileSize();
			log.debug("contentType: " + contentType);
			log.debug("File Name: " + fileName);
			log.debug("File Size: " + fileSize);

			if (myFile != null) {
				try {

					InputStream input = myFile.getInputStream();
					OutputStream output = new FileOutputStream(LocalProperties.getProperty(PDF_OUTPUT_DIR) + fileName);
					int bytesRead = 0;
					byte[] buffer = new byte[8192];
					while ((bytesRead = input.read(buffer, 0, 8192)) != -1) {
						output.write(buffer, 0, bytesRead);
					}
					output.close();

				} catch (Exception ex) {
					log.error("Exception " + ex.getClass().getName() + " caught with message: " + ex.getMessage() + "File Name: " + fileName);
					ex.printStackTrace();
				}
			}
			
			log.debug("Finished File upload to tomcat" + System.currentTimeMillis());
			log.debug("start upload to stellent" + System.currentTimeMillis());
			StellentClient sClient = new StellentClient();
			String GFID = (String) ctx.session().getAttribute(Constants.gfid);
			dmf.setGfid(GFID);
			String recId = sClient.createDocument(dmf);
			StellentUpdateAudit.auditStellentUpdate(((User)ctx.session().getAttribute(Constants.loggedUser)).getUserId(), GFID, recId, StellentUpdateAudit.AUDIT_CREATE);
			ctx.session().removeAttribute("docCode");
			ctx.session().removeAttribute("uploadFormControl");

			log.debug("Finished upload to stellent" + System.currentTimeMillis());
			ctx.forwardToAction("main/iuauser?ctrl=nestedtabset&action=TabClick&param=tab12");

			log.debug("End upload_onClick");
		} catch (Exception t) {
			log.error("File upload Failed");
			ctx.addGlobalError("error.li.fileupload", fileName);
			log.error("error.li.fileupload", new Exception(t));
			ctx.forwardToInput();

		} finally
		{
			//StellentClient.logout(AAID);
		}
	}

	/**
	 * Handles cancel button event
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void cancel_onClick(FormActionContext ctx) throws Exception {
		log.debug("Begin cancel_onClick"); 
		ctx.request().removeAttribute("docUpdateMetaDataForm");
		ctx.session().removeAttribute("docCode");
		ctx.session().removeAttribute(Constants.appdata);
		ctx.forwardToAction("main/iuauser?ctrl=nestedtabset&action=TabClick&param=tab13");
		log.debug("End cancel_onClick");
	}

	/**
	 * Handles reload button event
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void reload_onClick(FormActionContext ctx) throws Exception {
		log.debug("Begin reload_onClick");
		// no action
		DocumentMetaDataForm dmf = (DocumentMetaDataForm) ctx.form();

		String lucId = (String) ctx.session().getAttribute(Constants.lucId);
		String docType = dmf.getDocCode();
		ctx.session().setAttribute("docType", docType);

		if (lucId == null) {
			lucId = (String) ctx.session().getAttribute(Constants.lucId);
		}

		// forward to the jsp page
		ctx.forwardToAction("main/iuauser?ctrl=nestedtabset&action=TabClick&param=tab13");
		log.debug("End cancel_onClick");
	}

	/**
	 * Handles Go button event
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void go_onClick(FormActionContext ctx) throws Exception {
		log.debug("Begin go_onClick");
		DocumentMetaDataForm dmf = (DocumentMetaDataForm) ctx.form();
		String docCode = dmf.getDocCode();
		//String searchName = Constants.allDocsSearchName;
		//SavedSearchPrompt ssps[] = ACMICache.getSavedSearchPrompts(searchName);

//		if (docCode != null || !docCode.equals("")) {
//			FormControl control = new FormControl();
//			control.setDesignModel(getDesignModel(ssps, docCode, dmf));
//			ctx.session().setAttribute("uploadFormControl", control);
//		} else {
//			ctx.session().removeAttribute("uploadFormControl");
//		}
//		
		ctx.session().setAttribute("docCode", docCode);

		// forward to the jsp page
		ctx.forwardToAction("main/iuauser?ctrl=nestedtabset&action=TabClick&param=tab13");
		log.debug("End  go_onClick");
	}

	public boolean validateForm(DocumentMetaDataForm form) {
		
		StringBuffer agentName = new StringBuffer(form.getAgentName());
		
		if (agentName != null) {
			//For XML to work, we need to replace & with &amp;
			int index = 0, startPos = 0;
					
			while (index != -1) {
				index = agentName.indexOf("&", startPos);
				if (index != -1) {
					agentName = agentName.replace(index, index + 1, "&amp;");
					startPos = index + "&amp;".length();
				}			
			}	
			form.setAgentName(agentName.toString());
		}
		return true;
	}
}