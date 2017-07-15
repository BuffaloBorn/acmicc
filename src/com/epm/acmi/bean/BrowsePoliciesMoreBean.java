package com.epm.acmi.bean;

import java.math.BigDecimal;

public class BrowsePoliciesMoreBean {

	  private java.math.BigDecimal start_policy;
	  private java.lang.String more_ind;
	
	  public BrowsePoliciesMoreBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BrowsePoliciesMoreBean(BigDecimal strat_policy, String more_ind) {
		super();
		this.start_policy = strat_policy;
		this.more_ind = more_ind;
	}

	public java.math.BigDecimal getStart_policy() {
		return start_policy;
	}

	public void setStart_policy(java.math.BigDecimal strat_policy) {
		this.start_policy = strat_policy;
	}

	public java.lang.String getMore_ind() {
		return more_ind;
	}

	public void setMore_ind(java.lang.String more_ind) {
		this.more_ind = more_ind;
	}
	
}
