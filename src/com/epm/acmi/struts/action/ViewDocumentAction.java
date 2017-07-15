package com.epm.acmi.struts.action;

import org.apache.log4j.Logger;

import com.cc.acmi.common.Forwards;
import com.cc.acmi.common.Messages;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.http.HttpUtil;
import com.cc.framework.ui.control.SimpleListControl;
import com.epm.acmi.struts.form.dsp.DisplayDocument;
import com.epm.acmi.struts.form.dsp.DisplayDocumentList;

/**
 * View Document Action - View Document from stellent
 * 
 * @author Jay Hombal
 */
public class ViewDocumentAction extends CCAction {

	private static Logger log = Logger.getLogger(ViewDocumentAction.class);

	/**
	 * Constructor for UserPrintAction.
	 */
	public ViewDocumentAction() {
		super();
	}

	
	/**
	 * This method is called by Controller Servlet. This method is specific to Common Controls API
	 * 
	 * @param ctx
	 *            ActionContext
	 * @throws java.lang.Exception
	 */
	public void doExecute(ActionContext ctx) throws Exception {

		// Get the userId from the Request.
		// The name of the request parameter is set up in
		// the struts-sample-list.xml

		log.debug("Begin doExecute");

		String LUCID = ctx.request().getParameter("LUCID");

		try {
			if (LUCID != null) {
				SimpleListControl slc = (SimpleListControl) ctx.session().getAttribute("expdoclist");
				DisplayDocumentList ddl = (DisplayDocumentList) slc.getDataModel();

				int size = ddl.size();
				for (int i = 0; i < size; i++) {
					String lucId = ddl.getUniqueKey(i);
					if (lucId.equals(LUCID)) {
						StringBuffer sb = new StringBuffer();
						DisplayDocument dd = (DisplayDocument) ddl.getElementAt(i);
						sb.append("http://dev7000/ibpmweb/default.asp?ToolName=AWVWR&Viewer=Optika");
						sb.append("&LUCID=" + LUCID);
						sb.append("&MIMETYPE=image/tiff");
						sb.append("&SSPROVIDERID=" + dd.getSSPROVIDERID());
						sb.append("&TABLENAME=" + dd.getTABLENAME());
						sb.append("&ROWIDENTIFIER=" + dd.getROWIDENTIFIER());
						sb.append("&INDEXPROVIDER=" + dd.getINDEXPROVIDER());
						sb.append("&EOF=1");

						String encodedURL = HttpUtil.urlEncode(sb.toString());
						ctx.response().sendRedirect(encodedURL);
					}
				}

			}

		} catch (Throwable t) {
			// log.error(t);
			ctx.addGlobalError(Messages.ERROR_INIT_FORMBEAN, t.getClass().getName() + " " + t.getMessage());
			ctx.forwardByName(Forwards.BACK);
			throw new Exception(t);
		}

		log.debug("End doExecute");

	}
}