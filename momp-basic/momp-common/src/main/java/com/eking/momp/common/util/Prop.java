package com.eking.momp.common.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Prop {
	
	private String column;
	
	private Object value;
	
	private String text;
	
	public static Prop of(String column, Object value) {
		Prop property = new Prop();
		property.setColumn(column);
		property.setValue(value);
		return property;
	}

	public static Prop of(String column, Object value, String text) {
		Prop property = new Prop();
		property.setColumn(column);
		property.setValue(value);
		property.setText(text);
		return property;
	}
}
	
