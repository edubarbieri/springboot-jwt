package br.com.edubarbieri.sbjwt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "SC_ROLE")
public class Role {

	@Id
	@Column(name="ROLE_ID", nullable = false)
	private String id;
	@Column(name="DESCRIPTION", nullable = false)
	private String description;
}
