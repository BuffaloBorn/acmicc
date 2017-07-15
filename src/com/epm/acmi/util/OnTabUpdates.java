package com.epm.acmi.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.cc.framework.adapter.struts.ActionContext;
import com.epm.acmi.hbm.BaseHibernateDAO;
import com.epm.acmi.hbm.Iuaapplication;
import com.epm.acmi.hbm.IuaapplicationDAO;
import com.epm.acmi.hbm.IuainterviewRequest;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.action.IUAUserAction;

/*
 *  Created new class for USR 8399-1 changes.
 *  on 02-14-2008 by Nagrathna Hiriyurkar.
 */

public class OnTabUpdates {

	private static Logger log = Logger.getLogger(IUAUserAction.class);
	
	public static ArrayList executeSelectSQL(ActionContext ctx, String SQLString) throws Exception{
	
		String gfid = (String) ctx.request().getSession().getAttribute(com.epm.acmi.struts.Constants.gfid);
		
		ResultSet rst = null;
		Connection conn = null;
		PreparedStatement psmt = null;
		
		String rowCount = null;
		ArrayList tempList = new ArrayList();

		try {
			conn = Connect.getConnection();

			psmt = conn.prepareStatement(SQLString);
			
			psmt.setString(1, gfid);

			rst = psmt.executeQuery();
			
			while (rst.next()) {
	            rowCount = rst.getString("rCount"); 
			}
			
			if(rowCount == null)
				tempList.add("0");
			else 
				tempList.add(rowCount);
			
		} catch (Exception e) {
			
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " ,GFID = " + gfid);
			e.printStackTrace();
			throw e;
			
		} finally {
			
			rst.close();
			psmt.close();
			conn.close();
			
		}
		
		return tempList;

	}
	
	public static List getMemoCode(ActionContext ctx) throws Exception{
		
		String gfid = (String) ctx.request().getSession().getAttribute(com.epm.acmi.struts.Constants.gfid);
		
		String memoCodeSQL = " select MemoCode as memoCode from IUAAppMemos WITH (NOLOCK) where GFID = ? ";
		
		ResultSet rst = null;
		Connection conn = null;
		PreparedStatement psmt = null;
		
		String memoCode = null;
		List memoCodeList = null; 
	
		try {
			conn = Connect.getConnection();

			psmt = conn.prepareStatement(memoCodeSQL);
			
			psmt.setString(1, gfid);

			rst = psmt.executeQuery();
			
			memoCodeList = new ArrayList();
			
			while (rst.next()) {
	            memoCode = rst.getString("memoCode"); 
	            
	            memoCodeList.add(memoCode.trim());
			}
			
		} catch (Exception e) {
			
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " ,GFID = " + gfid);
			e.printStackTrace();
			throw e;
			
		} finally {
			
			rst.close();
			psmt.close();
			conn.close();
			
		}
		
		return memoCodeList;

	}

	
	
	public static void medRecTabDataStatus(ActionContext ctx) throws Exception{
		
		log.debug("OnTabUpdates: med recs tab data status");

		String SQLString = " select count(*) as rCount from IUAMedRecRequest WITH (NOLOCK) where GFID = ? ";
		
		ArrayList tabDataList = executeSelectSQL(ctx, SQLString);
		
		log.debug("size of Med record list: " + tabDataList.size());
		
		ctx.session().setAttribute("medRecsDataStatus", tabDataList);
	}
		
	public static void prevPoliciesTabDataStatus(ActionContext ctx) throws Exception{
		
		log.debug("OnTabUpdates: prev policies tab data status");
		
		String SQLString = " select count(*) as rCount from IUAPrevPolicy WITH (NOLOCK) where GFID = ? ";
		
		ArrayList tabDataList = executeSelectSQL(ctx, SQLString);
		
		log.debug("size of previous Policies List: " + tabDataList.size());
		
		ctx.session().setAttribute("prevPolicieDataStatus", tabDataList);
	}
	
	public static void CaseNotesTabDataStatus(ActionContext ctx) throws Exception{
		
		log.debug("OnTabUpdates: Case Notes Tab Data Status()");
		
		String SQLString = " select count(*) as rCount from IUACaseNotes WITH (NOLOCK) where GFID = ? ";
		
		ArrayList tabDataList = executeSelectSQL(ctx, SQLString);
		
		log.debug("size of case notes List: " + tabDataList.size());
		
		ctx.session().setAttribute("caseNotesDataStatus", tabDataList);

	} 
	
	public static void MemosAmendTabDataStatus(ActionContext ctx) throws Exception{
		
		log.debug("OnTabUpdates: MemosAmend Tab Data Status()");
		
		String SQLString = " select count(*) as rCount from IUAAppMemos WITH (NOLOCK) where GFID = ? ";
		
		ArrayList tabDataList = new ArrayList();
		
		ArrayList memoCodeList = (ArrayList)getMemoCode(ctx);
	
		if (memoCodeList.size() == 0)
			
			tabDataList.add("0");
		
		else {
			
			log.debug("MemoCode in Memo : " + memoCodeList.size()) ;
			
			if ( memoCodeList.contains("MAOT") || memoCodeList.contains("SIAG") || memoCodeList.contains("SIAD") ||
				 memoCodeList.contains("SIA") || memoCodeList.contains("MMI") || memoCodeList.contains("MOI")||memoCodeList.contains("MDI") 
				 || memoCodeList.contains("GAL")|| memoCodeList.contains("AOA") || memoCodeList.contains("ANCH")){
				
				tabDataList = executeSelectSQL(ctx, SQLString);
				
			}else { 
				tabDataList.add("0");
			}
		}
	
		log.debug("size of MemosAmend List: " + tabDataList.size());
		
		ctx.session().setAttribute("memosAmendDataStatus", tabDataList);
		
	}
	
	public static void SharedInfoTabDataStatus(ActionContext ctx) throws Exception{
		
		log.debug("OnTabUpdates: Shared Info Tab Data Status()");
		
		String SQLString = " select count(*) as rCount from IUAAppMemos WITH (NOLOCK) where GFID = ? ";
		
		ArrayList tabDataList = new ArrayList();
		
		ArrayList memoCodeList = (ArrayList)getMemoCode(ctx);
		
		if (memoCodeList.size() == 0)
			
			tabDataList.add("0");
		
		else {
			
			log.debug("MemoCode in shared : " + memoCodeList.size()) ;
			
			if ( memoCodeList.contains("RCA") || memoCodeList.contains("ADEC") || memoCodeList.contains("RPR") ||
				 memoCodeList.contains("DNP") || memoCodeList.contains("SIOT") )
			
				tabDataList = executeSelectSQL(ctx, SQLString);
			
			else 
				
				tabDataList.add("0");
				
		}
		
		log.debug("size of shared info List: " + tabDataList.size());
			
		ctx.session().setAttribute("sharedInfoDataStatus", tabDataList);
	
	}
	
	public static void TelephoneInterviewStatus(ActionContext ctx) {		
		try
		{
			String gfid = (String) ctx.request().getSession().getAttribute(com.epm.acmi.struts.Constants.gfid);
			
			IuaapplicationDAO appDAO = new IuaapplicationDAO();
			Iuaapplication app = appDAO.findById(gfid);
			appDAO.getSession().flush();
			
			int size = app.getIuainterviewRequests().size();
			ctx.session().setAttribute("telephoneInterviewStatus", new Integer(size));
			
			int newInterviewCount = 0;
			Iterator requests = app.getIuainterviewRequests().iterator();
			
			while (requests.hasNext())
			{
				IuainterviewRequest request = (IuainterviewRequest)requests.next();
				if (Constants.INTERVIEW_REQUEST_STATUS_NEW.equals(request.getInterviewRequestStatus()))
				{
					newInterviewCount++;
				}
			}
			 
			ctx.session().setAttribute("numNewInterviewRequests", new Integer(newInterviewCount));			
			
		} finally {
			BaseHibernateDAO.close();
		}
	}
	
}
