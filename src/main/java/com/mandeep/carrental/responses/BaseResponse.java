package com.mandeep.carrental.responses;

import com.mandeep.carrental.utils.Constants;

/**
 *  Base response for all response classes
 * @author mandeep
 *
 */
public class BaseResponse {
	
	boolean isSuccess;
	String message;
	int code;
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public void createDefaultSucces(String message) {
		this.isSuccess=true;
		this.message=message;
		this.code= Constants.ResponseCode.DEFAULT_SUCCESS;

	}

	
	public void createDefaultError(String message) {
		this.isSuccess=false;
		this.message=message;
		this.code= Constants.ResponseCode.DEFAULT_SUCCESS;

	}
	
	public void createDefaultError(String message, int errorCode) {
		this.isSuccess=false;
		this.message=message;
		this.code= errorCode;

	}
	
	

}
