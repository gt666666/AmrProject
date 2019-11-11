package cn.mldn.amr.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Subtype implements Serializable {
	private Integer stid ;
	private Type type ;
	private String title ;
	private List<Details> allDetails ;
	public void setAllDetails(List<Details> allDetails) {
		this.allDetails = allDetails;
	}
	public List<Details> getAllDetails() {
		return allDetails;
	}
	public Integer getStid() {
		return stid;
	}
	public void setStid(Integer stid) {
		this.stid = stid;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
