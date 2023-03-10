package com.glucoma.creater;

public enum ActionEnum {
	INPUT,
	SELECT,
	SELECT_SEARCH,
	RADIO,
	CHECKBOX,
	SWITCH, 
	
	CLICK, UPLOAD_DOCUMENT;
	
	public static ActionEnum getActionEnum(String actionEnum) {
		if(actionEnum == null || actionEnum.isEmpty()) 
			return null;
		
		for(ActionEnum e : ActionEnum.values()) {
			if(e.toString().toLowerCase().equals(actionEnum.toLowerCase())) 
				return e;
		}
		return null;
	}
}
