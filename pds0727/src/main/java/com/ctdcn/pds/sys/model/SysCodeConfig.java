package com.ctdcn.pds.sys.model;

public class SysCodeConfig {
	
	private Integer id;
	private String typecode;//类型编码
	private Integer bm; // 字典编码
	private String mc;// 名称
	private String bz; // 备注
	private String value;//字典值
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	public Integer getBm() {
		return bm;
	}

	public void setBm(Integer bm) {
		this.bm = bm;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "SysCodeConfig [id=" + id + ", typecode=" + typecode + ", bm="
				+ bm + ", mc=" + mc + ", bz=" + bz + ", value=" + value + "]";
	}

}
