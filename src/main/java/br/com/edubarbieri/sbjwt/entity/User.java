package br.com.edubarbieri.sbjwt.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "SC_USER")
public class User {
	@Id
	@SequenceGenerator(name="user_seq", sequenceName = "user_seq", initialValue = 10000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	@Column(name = "USER_ID")
	private Long id;
	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	@Column(name = "EMAIL", nullable = false)
	private String email;
	@Column(name = "LOGIN", nullable = false)
	private String login;
	@Column(name = "PASSWORD", nullable = false)
	@JsonIgnore
	private String password;
	@Column(name = "ENABLED", nullable = false)
	private Boolean enabled;
	
	
	@ManyToMany
	@JoinTable(name = "SC_USER_RULES", 
		joinColumns = {@JoinColumn(name="USER_ID")},
		inverseJoinColumns = {@JoinColumn(name="ROLE_ID")})
	private Set<Role> roles;
	
}
