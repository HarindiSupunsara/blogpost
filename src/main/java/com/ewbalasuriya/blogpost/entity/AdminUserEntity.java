package com.ewbalasuriya.blogpost.entity;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin_user")
public class AdminUserEntity {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "status", columnDefinition = "TINYINT(1)")
	private Integer status;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "fname")
	private String firstName;
	
	@Column(name = "lname")
	private String lastName;
	
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "create_date", columnDefinition = "TIMESTAMP")
	private Date createDate;
	
	@Column(name = "is_superadm")
	private Boolean superadmin;
	
	@JsonIgnore
	@OneToMany(mappedBy = "adminUserEntity", targetEntity = BlogPostEntity.class)
	private List<BlogPostEntity> blogPostEntities;

}
