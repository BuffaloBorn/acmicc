package com.epm.acmi.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class StellentUpdateAudit {    		
	private static final String GFID = "GFID = ";
	private static final String RECID = "RECID = ";
	
	public static final int AUDIT_CREATE = 0;
	public static final int AUDIT_UPDATE = 1;
	public static final int AUDIT_DELETE  = 2;

	public static final String AUDIT_CREATE_STRING = "Create";
	public static final String AUDIT_UPDATE_STRING = "Update";
	public static final String AUDIT_DELETE_STRING  = "Delete";
	
	private static final String NEW_BIZ_MAIN = "NewBizMain";
	
	private static boolean enabled = false;

	private static final String INSERT_STATEMENT = "INSERT INTO IUAAUDIT (TableName, UserId, PrimaryKeys, NewValues, ModKind, AuditTimeStamp) VALUES (?,?,?,?,?,GetDate())";

	private static final Logger logger = Logger.getLogger(StellentUpdateAudit.class);    
	
	public static void auditStellentUpdate(final String userId, final String gfid, final String recordId, final int updateKind)
	{
		if (enabled)
			StartauditStellentUpdate(userId, gfid, recordId, updateKind);
	}
	
	public static void StartauditStellentUpdate(final String userId, final String gfid, final String recordId, final int updateKind)
	{
		Thread auditThread = new Thread(new Runnable() {
		
			public void run()
			{
				//Wait time in milliseconds...
				long waitTime = 25;
				
				for (int i = 0; i < 5; i++)
				{
					boolean auditSucceeded = false;
					
					try
					{
						auditSucceeded = doAuditStellentUpdate(userId, gfid, recordId, updateKind);
					} catch (SQLException e)
					{
						auditSucceeded = false;
					}
					
					if (auditSucceeded)
						return;
					else
					{
						try
						{
							//Wait progressively longer, up to three times...
							Thread.sleep(waitTime);
							waitTime = 2*waitTime;
						} catch(InterruptedException ie)
						{
							//do nothing... just continue...
						}
					}
				}			
			}
		});
		
		auditThread.start();
	}
	
	public static boolean doAuditStellentUpdate(String userId, String gfid, String recordId, int updateKind) throws SQLException
	{
		logger.debug("auditing for Stellent with userid=" + userId + ", recordId=" + recordId + ", and gfid=" + gfid);
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try
		{
			conn = Connect.getConnection();
			
			stmt = conn.prepareStatement(INSERT_STATEMENT);
			stmt.setString(1, NEW_BIZ_MAIN);
			stmt.setString(2, userId);
			stmt.setString(3, RECID + recordId);
			stmt.setString(4, GFID + gfid);
			stmt.setString(5, getUpdateKindString(updateKind));		
			int updateCount = stmt.executeUpdate();
			return (updateCount > 0);
		} catch (SQLException e)
		{
			logger.error(e.getClass().getName() + " exception thrown while trying to audit for Stellent");
			return false;
		} finally
		{
			try
			{
				if (stmt != null)
					stmt.close();
			} catch (SQLException e2)
			{
				//do nothing here...
			}
			
			try
			{
				if (conn != null)
					conn.close();
			} catch (SQLException e3)
			{
				//do nothing here...
			}
		}
	}

	private static String getUpdateKindString(int updateKind) {
		switch (updateKind)
		{
		case AUDIT_CREATE: return AUDIT_CREATE_STRING;
		case AUDIT_UPDATE: return AUDIT_UPDATE_STRING;
		case AUDIT_DELETE: return AUDIT_DELETE_STRING;
		default: return AUDIT_UPDATE_STRING;
		}
	}
	
	public static void main(String[] args)
	{
		auditStellentUpdate("d446", "E007008", "1234567", AUDIT_UPDATE);
	}
}
