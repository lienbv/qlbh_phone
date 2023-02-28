package com.datn_qlbh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Component
@ToString
@Data
@Entity
@Table(name = "user")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	@Column(name = "USERNAME")
	private String username;
	@Column(name = "FULLNAME")
	private String fullname;
	@Column(name = "PASSWORD")
	private String password;
	@Column(name = "CCCD")
	private String cccd;
	@Column(name = "ADDRESS")
	private String address;
	@Column(name = "NUMBER_PHONE")
	private String numberPhone;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "CREATED_DATE")
	private Date created;
	@Column(name = "STATUS")
	private int status;
}
