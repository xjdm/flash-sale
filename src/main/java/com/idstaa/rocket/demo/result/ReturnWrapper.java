package com.idstaa.rocket.demo.result;

import java.util.ArrayList;
import java.util.List;

public class ReturnWrapper<T> {
	private List<T> result = new ArrayList<T>();

	public ReturnWrapper(List<T> result) {
		this.result = result;
	}
	public ReturnWrapper(T result) {
		if (result != null) {
			this.result.add(result);
		}
	}
	public ReturnWrapper() {
	}
	public AppResultList<T> success() {
		AppResultList<T> appResult = new AppResultList<T>();
		appResult.setCode(Constant.ACCESS_CODE.SUCCESS.getCode());
		appResult.setErrors(null);
		appResult.setMessage(Constant.ACCESS_CODE.SUCCESS.getName());
		appResult.setResults(result);
		return appResult;
	}
	public AppResult<T> success(String message) {
		AppResult<T> appResult = new AppResult<T>();
		appResult.setCode(Constant.ACCESS_CODE.SUCCESS.getCode());
		appResult.setMessage(message);
		if(result.size()>0){
			appResult.setResults(result.get(0));
		}
		return appResult;
	}

	public AppResult<T> fail(String message) {
		AppResult<T> appResult = new AppResult<T>();
		appResult.setCode(Constant.ACCESS_CODE.FAILED.getCode());
		appResult.setMessage(message);
		return appResult;
	}

	public AppResult<T> denied(String message) {
		AppResult<T> appResult = new AppResult<T>();
		appResult.setCode(Constant.ACCESS_CODE.DENIED.getCode());
		appResult.setMessage(message);
		return appResult;
	}


	public AppResultList<T> errorList(String message) {
		AppResultList<T> appResult = new AppResultList<T>();
		appResult.setCode(Constant.ACCESS_CODE.ERROR.getCode());
		appResult.setErrors(null);
		appResult.setMessage(message);
		appResult.setResults(result);
		return appResult;
	}

	public AppResult<T> error(String message) {
		AppResult<T> appResult = new AppResult<T>();
		appResult.setCode(Constant.ACCESS_CODE.ERROR.getCode());
		appResult.setMessage(message);
		return appResult;
	}

	public AppResultList<T> failList(String message) {
		AppResultList<T> appResult = new AppResultList<T>();
		appResult.setCode(Constant.ACCESS_CODE.FAILED.getCode());
		appResult.setErrors(null);
		appResult.setMessage(message);
		appResult.setResults(result);
		return appResult;
	}

	public AppResultList<T> deniedList(String message) {
		AppResultList<T> appResult = new AppResultList<T>();
		appResult.setCode(Constant.ACCESS_CODE.DENIED.getCode());
		appResult.setErrors(null);
		appResult.setMessage(message);
		appResult.setResults(result);
		return appResult;
	}

	public AppResult<T> complete(T responseValue) {
		AppResult<T> appResult = new AppResult<T>();
		appResult.setCode(Constant.ACCESS_CODE.SUCCESS.getCode());
		appResult.setMessage(Constant.ACCESS_CODE.SUCCESS.getName());
		appResult.setResults(responseValue);
		return appResult;
	}
}
