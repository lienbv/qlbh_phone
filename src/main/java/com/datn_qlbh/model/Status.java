package com.datn_qlbh.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Status implements Serializable{
	private String status="";
	private String message="";
	public static String Success="1";
	public static String Fail="0";
	
}
