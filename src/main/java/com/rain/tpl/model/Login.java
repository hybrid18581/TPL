package com.rain.tpl.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import org.hibernate.validator.constraints.Length;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tpl_user_tbl")
public class Login {

	public Login() {

	}

	public Login(String fullName, String password, String emailId, String mobileNo, Date dob, String clubSupported,
			String country, String host) {

		this.fullName = fullName;
		this.password = password;

		this.emailId = emailId;
		this.mobileNo = mobileNo;
		this.dob = dob;
		this.clubSupported = clubSupported;
		this.country = country;
		this.host = host;
	}

	@Id
	// for custom id generation
	@GenericGenerator(name = "owner_id", strategy = "com.rain.tpl.idgenerator.OwnerIdGenerator")
	@GeneratedValue(generator = "owner_id")
	@Column(name = "tpl_owner_id")
	private String id;

	@Column(name = "tpl_full_name")
	@NotBlank(message = "is required")
	private String fullName;

	@Column(name = "tpl_user_password")
	@NotBlank(message = "is required")
	private String password;

	@Transient
	@NotBlank(message = "is required")
	private String confirmPassword;

	@Column(name = "tpl_user_emailid")
	@Email(message = "Invalid Email")
	@NotBlank(message = "is required")
	private String emailId;

	@Column(name = "tpl_user_mobileno")
	@Length(min = 10, message = "Mobile can not be less than 10 digits")

	private String mobileNo;

	@Column(name = "tpl_user_dob")
	@NotNull(message = "is required")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dob;

	@Column(name = "tpl_user_suportclub")
	@NotBlank(message = "is required")
	private String clubSupported;

	@Column(name = "tpl_user_country")
	@NotBlank(message = "is required")
	private String country;

	@Column(name = "tpl_sys_host")
	private String host = "";
	
	@Column(name = "tpl_user_activeyn")
	private String activeYn;

	public String getActiveYn() {
		return activeYn;
	}

	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}

	public String getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getClubSupported() {
		return clubSupported;
	}

	public void setClubSupported(String clubSupported) {
		this.clubSupported = clubSupported;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public String toString() {
		return "Login [fullName=" + fullName + ", password=" + password + ", teamName=" + ", emailId=" + emailId
				+ ", mobileNo=" + mobileNo + ", dob=" + dob + ", clubSupported=" + clubSupported + ", country="
				+ country + ", host=" + host + "]";
	}

}
