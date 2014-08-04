package com.youymi.youymiframework.data;

import java.io.Serializable;
import java.util.Map;

public class TreeNode implements Serializable {
	
	/**
	 * 树实体
	 * 
	 */
	private static final long serialVersionUID = 3184748802331949483L;
	private String id;
	private String parentId;
	private String name;
	private String extraData;
	
	private Map<String, Object> extraMapData;
	
	
	public TreeNode() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}

	public Map<String, Object> getExtraMapData() {
		return extraMapData;
	}

	public void setExtraMapData(Map<String, Object> extraMapData) {
		this.extraMapData = extraMapData;
	}
			

}
