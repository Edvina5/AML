package Util;

import java.math.BigDecimal;

public class Customer {
	
	private int cid;
	private int sf;
	private int rf;
	BigDecimal sa = new BigDecimal("0");
	BigDecimal ra = new BigDecimal("0");
	private String date;
	
	
	public int getCID(){
		return cid;
	}
	
	public void setCID(int cid){
		this.cid = cid;
	}
	
	public int getSF(){
		return sf;
	}
	
	public void setSF(int sf){
		this.sf = sf;
	}
	
	public int getRF(){
		return rf;
	}
	
	public void setRF(int rf){
		this.rf = rf;
	}
	
	public BigDecimal getSA(){
		return sa;
	}
	
	public void setSA(BigDecimal sa){
		this.sa = sa;
	}
	
	public BigDecimal getRA(){
		return ra;
	}
	
	public void setRA(BigDecimal ra){
		this.ra = ra;
	}
	
	public String getDate(){
		return date;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	
}
