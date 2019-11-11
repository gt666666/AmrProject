package cn.mldn.amr.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Res implements Serializable {
	private Integer rid ;
	private Type type ;
	private Date indate  ;
	private Subtype subtype ;
	private String title ;
	private Double price ;
	private String photo ;
	private Integer rflag ;
	private Integer amount ;
	
	public void setIndate(Date indate) {
		this.indate = indate;
	}
	public Date getIndate() {
		return indate;
	}
	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Subtype getSubtype() {
		return subtype;
	}
	public void setSubtype(Subtype subtype) {
		this.subtype = subtype;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Integer getRflag() {
		return rflag;
	}
	public void setRflag(Integer rflag) {
		this.rflag = rflag;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
