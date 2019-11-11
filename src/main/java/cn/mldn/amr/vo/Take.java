package cn.mldn.amr.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Take implements Serializable {
	private Integer tkid ;
	private Integer geid ;
	private Integer beid ;
	private Res res ;
	private Date gdate ;
	private Date rdate ;
	private String note ;
	private Integer amount ;
	private Integer status ;
	public Integer getTkid() {
		return tkid;
	}
	public void setTkid(Integer tkid) {
		this.tkid = tkid;
	}
	public Integer getGeid() {
		return geid;
	}
	public void setGeid(Integer geid) {
		this.geid = geid;
	}
	public Integer getBeid() {
		return beid;
	}
	public void setBeid(Integer beid) {
		this.beid = beid;
	}
	public Res getRes() {
		return res;
	}
	public void setRes(Res res) {
		this.res = res;
	}
	public Date getGdate() {
		return gdate;
	}
	public void setGdate(Date gdate) {
		this.gdate = gdate;
	}
	public Date getRdate() {
		return rdate;
	}
	public void setRdate(Date rdate) {
		this.rdate = rdate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
