package com.coolweather.app.model;

public class City {
	private int provinceId;
	
	private int Id;
	
	private String cityName;
	
	private String cityCode;
	
	public int getProvinceId(){
		return provinceId;
	}
	public void setProvinceId(int provinceId){
		this.provinceId=provinceId;
	}
	
	public int getId(){
		return Id;
	}
	public void setId(int Id){
		this.Id=Id;
	}
	
	public String getCityName(){
		return cityName;
	}
	public void setCityName(String cityName){
		this.cityName=cityName;
	}
	
	public String getCityCode(){
		return cityCode;
	}
	public void setCityCode(String cityCode){
		this.cityCode=cityCode;
	}
	

}
