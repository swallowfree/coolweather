package com.coolweather.app.model;

public class County {
	
	private int cityId;
	
	private int Id;
	
	private String countyName;
	
	private String countyCode;
	
	public int getCityId(){
		return cityId;
	}
	public void setCityId(int cityId){
		this.cityId=cityId;
	}
	
	public int getId(){
		return Id;
	}
	public void setId(int Id){
		this.Id=Id;
	}
	
	public String getCountyName(){
		return countyName;
	}
	public void setCountyName(String countyName){
		this.countyName=countyName;
	}
	
	public String getCountyCode(){
		return countyCode;
	}
	public void setCountyCode(String countyCode){
		this.countyCode=countyCode;
	}

}
