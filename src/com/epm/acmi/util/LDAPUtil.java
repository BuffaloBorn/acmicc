package com.epm.acmi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.log4j.Logger;

/**
 * This class used build User cache that is used to display full names and roles in EPM UI and user attributes are used
 * in authentication. Actual autentication is done with Windows Domain Via EPM WFSession
 * 
 * @author Jay Hombal
 */

public class LDAPUtil {

	private static Logger log = Logger.getLogger(LDAPUtil.class);


	private static final String providerURL;


	private static final String contextFactory;


	private static final String username;


	// private static final String loginName;

	private static final String searchBase;


	private static final String empSearchBase;


	private static final String globalsearchBase;
	
	private static String password ="";

	public LDAPUtil() {
	}


	/**
	 * Returns DirContext to Active Directory
	 * 
	 * @return DirContext
	 */
	public static DirContext getContext() {
		DirContext ctx = null;
		Hashtable env = new Hashtable();
		env.put("java.naming.factory.initial", contextFactory);
		env.put("java.naming.security.authentication", "simple");
		env.put("java.naming.security.principal", username);
		env.put("java.naming.security.credentials", password);
		env.put("java.naming.provider.url", providerURL);
		log.debug("getting Directory Context");
		try {
			ctx = new InitialDirContext(env);
		} catch (Exception e) {
			try {
				//A second try if first time was unsuccessful...
				ctx = new InitialDirContext(env);
			} catch (Exception ex) {
				log.error("Failed to get LDAP directory context", ex);
			}
		}
		return ctx;
	}


	/**
	 * Authenticate with Active Directory
	 * 
	 * @param loginName
	 * @param ppassword
	 * @return boolean
	 */
	public static boolean authenticate(String loginName, String ppassword) {
		boolean userIsOK = false;
		log.debug("Begin authenticate()");
		String keyDn = getCn(loginName);
		if (keyDn != null) {
			Hashtable env = new Hashtable(11);
			env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
			env.put("java.naming.provider.url", "");
			env.put("java.naming.security.authentication", "simple");
			env.put("java.naming.security.principal", keyDn);
			env.put("java.naming.security.credentials", ppassword);
			log.debug("authenticate with >" + keyDn + "<");
			try {
				new InitialDirContext(env);
				userIsOK = true;
			} catch (NamingException e) {
				log.debug("authenticate failed with " + e);
			}
		}
		log.debug("End authenticate()");
		return userIsOK;
	}


	/**
	 * Retrive common name for a given login name
	 * 
	 * @param loginName
	 * @return String
	 */
	public static String getCn(String loginName) {
		String keyDn = null;
		log.debug("Begin getCn()");
		DirContext ctx = null;
		
		try {
			ctx = getContext();
			SearchControls ctls = new SearchControls();
			ctls.setSearchScope(2);
			String searchStr = "(" + loginName + "=" + loginName + ")";
			NamingEnumeration answer = ctx.search("", searchStr, ctls);
			
			if (answer.hasMore()) {
				SearchResult sr = (SearchResult) answer.next();
				keyDn = sr.getName() + "," + ctx.getNameInNamespace();
				log.debug("entry found for LDAP-search >" + searchStr + "<: dn= >" + keyDn + "<!");
				answer.close();
			} else {
				log.debug("no entry found for LDAP-search >" + searchStr + "<!");
			}
		} catch (NamingException e) {
			log.debug("get common name (CN) for >" + loginName + "< failed with " + e);
		} finally
		{
			closeContext(ctx);
		}
		
		log.debug("End getCn()");
		return keyDn;
	}

	public static void closeContext(DirContext ctx)
	{
		if (ctx != null)
		{
			try
			{
				ctx.close();
			} catch (NamingException ne)
			{
				log.debug("Exception trying to close directory context");
			}
		}
	}

	/**
	 * Returns true if LDAP server is running
	 * 
	 * @param loginName
	 * @return boolean
	 */
	public static boolean isOnLdapServer(String loginName) {
		return getCn(loginName) != null;
	}


	/**
	 * Returns a Map of Active Directory groups
	 * 
	 * @return HashMap
	 */
	public HashMap getGroups() {
		log.debug("Begin getGroups()");

		HashMap groups = new HashMap();
		DirContext ctx = null;
		
		try {
			ctx = getContext();
			SearchControls searchCtls = new SearchControls();
			String returnedAtts[] = { "cn", "sAMAccountName" };
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(2);
			String searchFilter = "(&(objectClass=group)(CN=*))";
			int totalResults = 0;
			for (NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls); answer.hasMoreElements();) {
				SearchResult sr = (SearchResult) answer.next();
				totalResults++;
				Attributes attrs = sr.getAttributes();
				if (attrs != null) {
					try {
						Attribute sAMA = attrs.get("sAMAccountName");
						String value = sAMA.get().toString();
						if (value.indexOf("Stellent") < 0 && value.indexOf("Iflow") < 0 && value.indexOf("TWFAdmin") < 0
								&& value.indexOf("IBPM Administrator") < 0) {
							String cn = attrs.get("cn").get().toString();
							log.debug(" common name: " + cn);
							log.debug(" sAMAccountName: " + sAMA);
							groups.put(cn, value);
						}
					} catch (NullPointerException e) {
						log.error("Errors listing attributes: " + e);
					}
				}
			}

			log.debug("Total results: " + totalResults);
		} catch (NamingException e) {
			log.error("Problem searching directory: " + e);
		} finally
		{
			closeContext(ctx);
		}
		
		log.debug("End getGroups()");

		return groups;
	}


	/**
	 * Returns all IUPS Specific EPM Groups from Active Directory.
	 * 
	 * @return ArrayList
	 */
	public ArrayList getAllEPMGroups() {
		ArrayList allEPMGroups = new ArrayList();
		DirContext ctx = null;
		
		try {

			// Create the initial directory context
			ctx = getContext();

			// Create the search controls
			SearchControls searchCtls = new SearchControls();

			// Specify the search scope
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			// specify the LDAP search filter
			String searchFilter = "(&(objectClass=group)(CN=IflowGroups))";

			// initialize counter to total the group members
			int totalResults = 0;

			// Specify the attributes to return
			String returnedAtts[] = { "member", "cn", "sAMAccountName", "distinguishedName" };
			searchCtls.setReturningAttributes(returnedAtts);

			// Search for objects using the filter
			NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls);
			
			// Loop through the search results
			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				Attributes attrs = sr.getAttributes();
				if (attrs != null) {

					Attribute sAMA = attrs.get("sAMAccountName");
					String sAMSs = sAMA.get().toString();

					try {
						for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
							Attribute attr = (Attribute) ae.next();
							for (NamingEnumeration e = attr.getAll(); e.hasMore(); totalResults++) {
								String next = e.next().toString();
								String groupName = getGroupAttributes(next, ctx);
								String NOT_USED_EPMGroups = LocalProperties.getProperty("NOTUSED_AD_GROUPS");
								if (NOT_USED_EPMGroups.indexOf(groupName) == -1) {
									allEPMGroups.add(groupName);
								}

							}

						}

					} catch (NamingException e) {
						System.err.println("Problem listing members: " + e);
					}

				}
			}
		}
		catch (NamingException e) {
			System.err.println("Problem searching directory: " + e);
		} finally
		{
			closeContext(ctx);
		}
		
		return allEPMGroups;
	}


	/**
	 * Retrive All Active Directory Groups
	 * 
	 * @return ArrayList
	 */
	public ArrayList getAllGroups() {
		return getAllGroups(null);
	}

		/**
		 * Retrive All Active Directory Groups
		 * 
		 * @return ArrayList
		 */
	public ArrayList getAllGroups(DirContext ctx) {
		log.debug("Begin getAllGroups()");
		ArrayList groups = new ArrayList();
		boolean closeContext = false;
		
		try {
			if (ctx == null) {
				ctx = getContext();
				closeContext = true;
			}
			
			SearchControls searchCtls = new SearchControls();
			String returnedAtts[] = { "cn", "sAMAccountName" };
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
			String searchFilter = "(&(objectClass=group)(CN=*))";
			int totalResults = 0;
			for (NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls); answer.hasMoreElements();) {
				SearchResult sr = (SearchResult) answer.next();
				totalResults++;
				Attributes attrs = sr.getAttributes();
				if (attrs != null) {
					try {

						Attribute sAMA = attrs.get("sAMAccountName");
						String value = sAMA.get().toString();
						String EPMGrorups = LocalProperties.getProperty("EPM_AD_GROUPS");

						if (EPMGrorups.indexOf(value) != -1) {
							String cn = attrs.get("cn").get().toString();
							log.debug("common name: " + cn);
							log.debug("Cached sAMAccountName: " + sAMA);
							groups.add(value);
						}

					} catch (NullPointerException e) {
						log.error("Errors listing attributes: " + e);
					}
				}
			}

			log.debug("Total results: " + totalResults);
		} catch (NamingException e) {
			log.error("Problem searching directory: " + e);
		} finally
		{
			if (closeContext)
				closeContext(ctx);
		}
		
		log.debug("End getAllGroups()");
		return groups;
	}


	/**
	 * Returns a list of member in a group
	 * 
	 * @param group_name
	 * @return List
	 */
	public List getMembersForGroup(String group_name) {
		return getMembersForGroup(group_name, null);
	}


	/**
	 * Returns a list of member in a group
	 * 
	 * @param group_name
	 * @param ctx
	 * @return List
	 */
	public List getMembersForEPMGroup(String group_name, DirContext ctx) {
		log.debug("Begin getMembersForGroup()");
		ArrayList list = new ArrayList();
		boolean closeContext = false;
		
		try {
			if (ctx == null) {
				ctx = getContext();
				closeContext = true;
			}
			
			SearchControls searchCtls = new SearchControls();
			searchCtls.setSearchScope(2);
			String searchFilter = "(&(objectClass=*)(CN=" + group_name + "))";
			int totalResults = 0;
			int Start = 0;
			int Step = 10;
			int Finish = 9;
			for (boolean Finished = false; !Finished;) {
				String returnedAtts[] = { "objectClass", "member", };
				searchCtls.setReturningAttributes(returnedAtts);
				for (NamingEnumeration answer = ctx.search(empSearchBase, searchFilter, searchCtls); answer.hasMoreElements();) {
					SearchResult sr = (SearchResult) answer.next();
					log.debug("GroupName:" + sr.getName());
					Attributes attrs = sr.getAttributes();
					if (attrs != null) {
						try {

							for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
								Attribute attr = (Attribute) ae.next();

								if (attr.getID().endsWith("*")) {
									Finished = true;
								}
								for (NamingEnumeration e = attr.getAll(); e.hasMore();) {
									String objclass = e.next().toString();
									String member = e.nextElement().toString();
									list.add(member);
									totalResults++;
								}

							}

						} catch (Exception e) {
							System.err.println("Problem printing attributes: " + e);
						}
						Start += Step;
						Finish += Step;
					}
				}

			}

			log.debug("Total members: " + totalResults);
		} catch (NamingException e) {
			log.error("Problem searching directory: " + e);
		} finally
		{
			if (closeContext)
			{
				closeContext(ctx);
			}
		}
		log.debug("End getMembersForGroup()");
		return list;
	}


	/**
	 * Returns a list of member in a group
	 * 
	 * @param group_name
	 * @param ctx
	 * @return List
	 */
	public List getMembersForGroup(String group_name, DirContext ctx) {
		log.debug("Begin getMembersForGroup()");
		ArrayList list = new ArrayList();
		boolean closeContext = false;
		
		try {
			if (ctx == null) {
				ctx = getContext();
				closeContext = true;
			}
			
			SearchControls searchCtls = new SearchControls();
			searchCtls.setSearchScope(2);
			String searchFilter = "(&(objectClass=group)(CN=" + group_name + "))";
			int totalResults = 0;
			int Start = 0;
			int Step = 10;
			int Finish = 9;
			for (boolean Finished = false; !Finished;) {
				String Range = Start + "-" + Finish;
				String returnedAtts[] = { "member;Range=" + Range };
				searchCtls.setReturningAttributes(returnedAtts);
				for (NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls); answer.hasMoreElements();) {
					SearchResult sr = (SearchResult) answer.next();
					log.debug("GroupName:" + sr.getName());
					Attributes attrs = sr.getAttributes();
					if (attrs != null) {
						try {
							for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
								Attribute attr = (Attribute) ae.next();
								if (attr.getID().endsWith("*")) {
									Finished = true;
								}
								for (NamingEnumeration e = attr.getAll(); e.hasMore();) {
									String member = e.next().toString();
									list.add(member);
									totalResults++;
								}

							}

						} catch (NamingException e) {
							System.err.println("Problem printing attributes: " + e);
						}
						Start += Step;
						Finish += Step;
					}
				}

			}

			log.debug("Total members: " + totalResults);
		} catch (NamingException e) {
			log.error("Problem searching directory: " + e);
		} finally
		{
			if (closeContext)
			{
				closeContext(ctx);
			}
		}
		log.debug("End getMembersForGroup()");
		return list;
	}


	/**
	 * Returns LDAPUser for a given Distinguished Name
	 * 
	 * @param dn
	 * @return LDAPUser
	 */
	public LDAPUser getUserAttributes(String dn) {
		return getUserAttributes(dn, null);
	}


	/**
	 * Return Group Attributes
	 * 
	 * @param dn
	 * @return String
	 */
	public String getGroupAttributes(String dn, DirContext ctx) {
		log.debug("Begin getGroupAttributes()");
		String cn = "";
		boolean closeContext = false;
		
		try {
			if (ctx == null)
			{
				ctx = getContext();
				closeContext = true;
			}
			
			SearchControls searchCtls = new SearchControls();
			String returnedAtts[] = { "sAMAccountName", "cn" };
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String searchFilter = "(&(objectClass=group)(distinguishedName=" + dn + "))";
			int totalResults = 0;
			for (NamingEnumeration answer = ctx.search(globalsearchBase, searchFilter, searchCtls); answer.hasMoreElements();) {
				SearchResult sr = (SearchResult) answer.next();
				totalResults++;
				Attributes attrs = sr.getAttributes();
				if (attrs != null) {
					try {
						cn = (attrs.get("sAMAccountName").get().toString());

					} catch (NullPointerException e) {
						log.error("Errors listing attributes: " + e);
					}
				}
			}
		} catch (NamingException e) {
			log.error("Problem searching directory: " + e);
		} finally
		{
			if (closeContext)
			{
				closeContext(ctx);
			}
		}
		
		log.debug("End getGroupAttributes()");
		return cn;

	}


	/**
	 * Returns LDAPUser for a given Distinguished Name
	 * 
	 * @param dn
	 * @param ctx
	 * @return LDAPUser
	 */
	public LDAPUser getUserAttributes(String dn, DirContext ctx) {
		LDAPUser user = null;
		//log.debug("Begin getUserAttributes()");
		boolean closeContext = false;
		
		try {
			if (ctx == null) {
				ctx = getContext();
				closeContext = true;
			}

			SearchControls searchCtls = new SearchControls();
			String returnedAtts[] = { "sAMAccountName", "sn", "givenName", "mail", "cn" };
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(2);
			String searchFilter = "(&(objectClass=user)(distinguishedName=" + dn + "))";
			int totalResults = 0;
			for (NamingEnumeration answer = ctx.search(globalsearchBase, searchFilter, searchCtls); answer.hasMoreElements();) {
				SearchResult sr = (SearchResult) answer.next();
				totalResults++;
				Attributes attrs = sr.getAttributes();
				if (attrs != null) {
					try {
						user = new LDAPUser();
						user.setUserId(attrs.get("sAMAccountName").get().toString());
						if (attrs.get("sn") != null)
							user.setLastName(attrs.get("sn").get().toString());
						if (attrs.get("givenName") != null)
							user.setFirstName(attrs.get("givenName").get().toString());
						if (attrs.get("mail") != null)
							user.setEmail(attrs.get("mail").get().toString());
						if (attrs.get("cn") != null)
							user.setCn(attrs.get("cn").get().toString());
						//log.info("user fullname= " + user.getFirstName() + " | " + user.getLastName() + " CN=" + user.getCn());
					} catch (NullPointerException e) {
						log.error("Errors listing attributes: " + e);
					}
				}
			}

		} catch (NamingException e) {
			log.error("Problem searching directory: " + e);
		} finally
		{
			if (closeContext)
			{
				closeContext(ctx);
			}
		}
		
		//log.debug("End getUserAttributes()");
		return user;
	}


	/**
	 * Returns users map from a group
	 * 
	 * @param group_name
	 * @return HashMap
	 */
	public HashMap getUsersFromGroup(String group_name) {
		return getUsersFromGroup(group_name, null);

	}


	/**
	 * Returns users map from a group
	 * 
	 * @param group_name
	 * @param ctx
	 * @return HashMap
	 */
	public HashMap getUsersFromGroup(String group_name, DirContext ctx) {

		log.debug("Begin  getUsersFromGroup(String group_name)");
		HashMap users = new HashMap();
		ArrayList members = (ArrayList) getMembersForGroup(group_name, ctx);
		for (int i = 0; i < members.size(); i++) {
			String member = (String) members.get(i);
			LDAPUser user = getUserAttributes(member, ctx);
			user.setRole(group_name);
			users.put(user.getUserId(), user);
		}
	
		closeContext(ctx);		
		log.debug("End  getUsersFromGroup(String group_name)");
		return users;
	}


	/**
	 * Returns users map from a list groups
	 * 
	 * @param group_names
	 * @return HashMap
	 */
	public HashMap getUsersFromGroup(List group_names) {
		return getUsersFromGroup(group_names, null);
	}
	
	public HashMap getUsersFromGroup(List group_names, DirContext ctx) {
		log.debug("Begin getUsersFromGroup(List group_names)");
		HashMap users = new HashMap();
		boolean closeContext = false;
	
		if (ctx == null) {
			ctx = getContext();
			closeContext = true;
		}
		
		for (int j = 0; j < group_names.size(); j++) {
			String group_name = (String) group_names.get(j);
			ArrayList members = (ArrayList) getMembersForGroup(group_name, ctx);
			for (int i = 0; i < members.size(); i++) {
				String member = (String) members.get(i);
				log.debug("member = " + member);
				if (member != null) {
					LDAPUser user = getUserAttributes(member, ctx);
					String userId = user.getUserId().toLowerCase();
					if (users.containsKey(userId)) {
						user = (LDAPUser) users.get(userId);
						user.setRole(group_name);
					} else {
						user.setRole(group_name);
						users.put(userId, user);
					}
				}

			}

		}
		
		if (closeContext)
		{
			closeContext(ctx);
		}
		
		// only for debug
		if (log.isDebugEnabled()) {
			Iterator itr = users.keySet().iterator();

			while (itr.hasNext()) {
				String key = (String) itr.next();
				LDAPUser loguser = (LDAPUser) users.get(key);
				log.debug(loguser);

			}
		}
		log.debug("End getUsersFromGroup(List group_names)");
		return users;
	}

	static {
		log = Logger.getLogger(com.epm.acmi.util.LDAPUtil.class);
		providerURL = LocalProperties.getProperty("PROVIDER_URL");
		contextFactory = LocalProperties.getProperty("INITIAL_CONTEXT_FACTORY");
		username = LocalProperties.getProperty("SECURITY_PRINCIPAL");
		password = PasswordEncryption.getDecryptedPassword(username);
		searchBase = LocalProperties.getProperty("SEARCH_BASE");
		empSearchBase = LocalProperties.getProperty("EPM_SEARCH_BASE");
		globalsearchBase = LocalProperties.getProperty("GLOBAL_SEARCH_BASE");
	}


	public static void main(String args[]) {
		String group = "MRT";
		LDAPUtil cache = new LDAPUtil();
		DirContext ctx = LDAPUtil.getContext();
		cache.getMembersForEPMGroup(group, ctx);
	}
}
