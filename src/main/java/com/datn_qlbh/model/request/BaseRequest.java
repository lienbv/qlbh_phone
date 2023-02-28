package com.datn_qlbh.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseRequest {
	@ApiModelProperty(name = "language", dataType = "String", value = "language", example = "vi", required = true)
    private String language = "";

	@Override
	public String toString() {
		return "BaseRequest [language=" + language + ", getLanguage()=" + getLanguage() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
