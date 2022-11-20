package com.eauction.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "signin")
public class Signin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name  = "password")
	private String password;

	@Column(name  = "role")
	private String role;
	
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "signin_role",
//		joinColumns = { @JoinColumn(name = "signin_id") },
//		inverseJoinColumns = { @JoinColumn(name = "role_id") })
//	private List<Role> role;
}
