package com.location;

public class UserForm {
	private String userId;
	private String userName;
	private String password;
	private String mobileNumber;
	private String radius;
	private String latitude;
	private String longitude;
	private String dateOfBirth;
	private String securtyAnswer;
	private String securityQuestionId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getRadius() {
		return radius;
	}
	public void setRadius(String radius) {
		this.radius = radius;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getSecurtyAnswer() {
		return securtyAnswer;
	}
	public void setSecurtyAnswer(String securtyAnswer) {
		this.securtyAnswer = securtyAnswer;
	}
	public String getSecurityQuestionId() {
		return securityQuestionId;
	}
	public void setSecurityQuestionId(String securityQuestionId) {
		this.securityQuestionId = securityQuestionId;
	}
	public boolean isValidUserId() {
		if(userId.equals("")&&userId.length()==0)
		return false;
		return true;
	}
	public boolean isValidUserName() {
		if(userName.equals("")&&userName.length()>10)
		return false;
		return true;
	}
	public boolean isValidPassword() {
		if(password.equals("")||password.length()<6)
		return false;
		return true;
	}
	public boolean isValidDob() {
		if(dateOfBirth.equals("")&&dateOfBirth.length()==0)
		return false;
		return true;
	}
	public boolean isValidMobileNumber() {
		if(mobileNumber.equals("")&&mobileNumber.length()<10)
		return false;
		return true;
	}
	public boolean isValidLatitude() {
		if(latitude.equals("")&&latitude.length()==0)
		return false;
		return true;
	}
	public boolean isValidLongitude() {
		if(longitude.equals("")&&longitude.length()==0)
		return false;
		return true;
	}
	public boolean isValidSecurtyAnswer() {
		if(securtyAnswer.equals("")&&securtyAnswer.length()==0)
		return false;
		return true;
	}
	public boolean isValidSecurityQuestion() {
		if(securityQuestionId.equals("")&&securityQuestionId.length()==0)
		return false;
		return true;
	}
	public boolean isValidRadius() {
		if(radius.equals("")&&radius.length()==0)
		return false;
		return true;
	}
	
}
