package com.ewbalasuriya.blogpost.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blogpost")
public class BlogPostEntity {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "header")
	private String header;
	
	@Column(name = "description", columnDefinition = "LONGTEXT")
	private String description;
	
	@Column(name = "pstatus")
	private String postStatus;
	
	@Column(name = "status", columnDefinition = "TINYINT(1)")
	private Integer status;
	
	@Column(name = "create_date", columnDefinition = "TIMESTAMP")
	private Date createDate;
	
	@Column(name = "approve_date", columnDefinition = "TIMESTAMP")
	private Date approveDate;
	
	@ManyToOne(cascade =CascadeType.ALL)
	@JoinColumn(name = "approved_user_id")
	private AdminUserEntity adminUserEntity;
	
	@ManyToOne(cascade =CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;
	
	@ManyToOne(cascade =CascadeType.ALL)
	@JoinColumn(name = "category_id")
	private CategoryEntity categoryEntity;

}
