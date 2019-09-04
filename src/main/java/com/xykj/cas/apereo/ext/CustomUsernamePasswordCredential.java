package com.xykj.cas.apereo.ext;

import org.apereo.cas.authentication.RememberMeUsernamePasswordCredential;

/**
 * 自定义Credential
 * @author stc
 *
 */
public class CustomUsernamePasswordCredential extends RememberMeUsernamePasswordCredential {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 机构ID
	 */
	private Long orgId;
	/**
	 * 科室ID
	 */
	private Long depId;
	/**
	 * 图片验证码
	 */
	private String verificationCode;
	
	/**
	 * 登录跳转系统ID ds_system_address表配置
	 */
	private String systemId;
	
	/**
	 * 登录类型(区分医生、护士、管理人员、患者.具体值待重新定义)
	 */
	private Integer loginType;
	
	/**
	 * 随机串
	 */
	private String nonceStr;
	
	/**
	 * 加密串
	 */
	private String sign;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getDepId() {
		return depId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((depId == null) ? 0 : depId.hashCode());
		result = prime * result + ((loginType == null) ? 0 : loginType.hashCode());
		result = prime * result + ((nonceStr == null) ? 0 : nonceStr.hashCode());
		result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
		result = prime * result + ((sign == null) ? 0 : sign.hashCode());
		result = prime * result + ((systemId == null) ? 0 : systemId.hashCode());
		result = prime * result + ((verificationCode == null) ? 0 : verificationCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomUsernamePasswordCredential other = (CustomUsernamePasswordCredential) obj;
		if (depId == null) {
			if (other.depId != null)
				return false;
		} else if (!depId.equals(other.depId))
			return false;
		if (loginType == null) {
			if (other.loginType != null)
				return false;
		} else if (!loginType.equals(other.loginType))
			return false;
		if (nonceStr == null) {
			if (other.nonceStr != null)
				return false;
		} else if (!nonceStr.equals(other.nonceStr))
			return false;
		if (orgId == null) {
			if (other.orgId != null)
				return false;
		} else if (!orgId.equals(other.orgId))
			return false;
		if (sign == null) {
			if (other.sign != null)
				return false;
		} else if (!sign.equals(other.sign))
			return false;
		if (systemId == null) {
			if (other.systemId != null)
				return false;
		} else if (!systemId.equals(other.systemId))
			return false;
		if (verificationCode == null) {
			if (other.verificationCode != null)
				return false;
		} else if (!verificationCode.equals(other.verificationCode))
			return false;
		return true;
	}

	
}
