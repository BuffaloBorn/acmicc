package com.cc.acmi;

import java.util.Date;

import com.cc.framework.common.Singleton;
import com.cc.framework.version.SystemType;
import com.cc.framework.version.VersionInfo;

/**
 * This class holds informations about the version of the IU&A application.
 * 
 * @author Jay Hombal
 * @version $Revision: 1.6 $
 */
public final class Version implements VersionInfo, Singleton {

	/** product name */
	private String productName = "IU&A Process SystemAmerican Community Insurance";

	/** extension to the product name */
	private String productNameExtension = "released";

	/** Major version */
	private int productMajorVersion = 1;

	/** Minor version */
	private int productMinorVersion = 2;

	/** Build number */
	private String productBuildNumber = "075";

	/** Build date */
	private Date productBuildDate = new java.util.GregorianCalendar(2004, 1, 14).getTime(); // Year,

	/** Vendor */
	private String productVendor = "SoftwareAG USA";

	/** Vendor site */
	private String productVendorSite = "www.softwareagusa.com";

	/** product site */
	private String productSite = "www.softwareagusa.com";

	/**
	 * The single instance of this class
	 */
	private static Version instance = null;

	
	// ------------------------------------------------
	// methods
	// ------------------------------------------------

	/**
	 * Default Constructor.
	 */
	private Version() {
		super();
	}

	/**
	 * Returns the single instance of the ComponentVersion class
	 * 
	 * @return ComponentVersion
	 */
	public static synchronized Version instance() {
		if (instance == null) {
			instance = new Version();
		}

		return instance;
	}

	public String getProductBuild() {
		return productBuildNumber;
	}

	public Date getProductBuildDate() {
		return productBuildDate;
	}

	public String getProductBuildNumber() {
		return productBuildNumber;
	}

	public int getProductMajorVersion() {
		return productMajorVersion;
	}

		public int getProductMinorVersion() {
		return productMinorVersion;
	}

	public String getProductName() {
		return productName;
	}

		public String getProductNameExtension() {
		return productNameExtension;
	}

		public String getProductVendor() {
		return productVendor;
	}

	public String getProductVendorSite() {
		return productVendorSite;
	}

	public String getProductSite() {
		return productSite;
	}
	public String getProductVersion() {
		return "v" + productMajorVersion + "." + productMinorVersion + "." + productBuildNumber;
	}

	public static String getComponentName() {
		return instance().getProductName();
	}

		public static String getComponentVesion() {
		return instance().getProductVersion();
	}

	public SystemType getSystemType() {
		return SystemType.SYSTYPE_PRODUCTION;
	}

}