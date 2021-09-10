package com.ewbalasuriya.blogpost.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "category")
public class CategoryEntity {
	
	@Id
	@Column(name = "pid", nullable = false)
	private String pid;

	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "create_date", columnDefinition = "TIMESTAMP")
	private Date createDate;
	
	@JsonIgnore
	@OneToMany(mappedBy = "categoryEntity", targetEntity = BlogPostEntity.class)
	private List<BlogPostEntity> blogPostEntities;

}
