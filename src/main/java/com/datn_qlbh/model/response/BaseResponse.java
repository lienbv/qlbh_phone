package com.datn_qlbh.model.response;

import com.datn_qlbh.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse implements Serializable{
	private Status status=new Status();
}
