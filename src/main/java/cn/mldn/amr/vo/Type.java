package cn.mldn.amr.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Type implements Serializable {
	private Integer tid ;
	private String title ;
	private List<Subtype> subtypes ;
	private List<Details> allDetails ;
	public void setAllDetails(List<Details> allDetails) {
		this.allDetails = allDetails;
	}
	public List<Details> getAllDetails() {
		return allDetails;
	}
	public void setSubtypes(List<Subtype> subtypes) {
		this.subtypes = subtypes;
	}
	public List<Subtype> getSubtypes() {
		return subtypes;
	}
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
