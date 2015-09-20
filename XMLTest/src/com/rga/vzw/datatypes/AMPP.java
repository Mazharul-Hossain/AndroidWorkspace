package com.rga.vzw.datatypes;

public class AMPP {

	/**
	 * @return the APPID
	 */
	public String getAPPID() {
		return APPID;
	}

	/**
	 * @return the NM
	 */
	public String getNM() {
		return NM;
	}

	/**
	 * @return the SUBP
	 */
	public String getVPPID() {
		return VPPID;
	}

	/**
	 * @return the SUBP
	 */
	public String getSUBP() {
		return SUBP;
	}

	/**
	 * @return the LEGAL_CATCD
	 */
	public String getLEGAL_CATCD() {
		return LEGAL_CATCD;
	}

	/**
	 * @return the APID
	 */
	public String getAPID() {
		return APID;
	}

	/**
	 * @param APPID
	 *            the APPID to set
	 */
	public void setAPPID(String APPID) {
		this.APPID = APPID;
	}

	/**
	 * @param NM
	 *            the NM to set
	 */
	public void setNM(String NM) {
		this.NM = NM;
	}

	/**
	 * @param SUBP
	 *            the SUBP to set
	 */
	public void setSUBP(String SUBP) {
		this.SUBP = SUBP;
	}

	/**
	 * @param APID
	 *            the APID to set
	 */
	public void setAPID(String APID) {
		this.APID = APID;
	}

	/**
	 * @param LEGAL_CATCD
	 *            the LEGAL_CATCD to set
	 */
	public void setLEGAL_CATCD(String LEGAL_CATCD) {
		this.LEGAL_CATCD = LEGAL_CATCD;
	}

	/**
	 * @param VPPID
	 *            the VPPID to set
	 */
	public void setVPPID(String VPPID) {
		this.VPPID = VPPID;
	}

	private String APPID;
	private String NM;
	private String VPPID;
	private String APID;
	private String LEGAL_CATCD;
	private String SUBP;
}