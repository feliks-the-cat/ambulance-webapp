package com.ambulance.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ambulance.core.domain.Doctor;
import com.ambulance.core.service.DoctorService;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Inject
	private DoctorService doctorService;
	
	private Collection<GrantedAuthority> adminAuthorities;
	private Collection<GrantedAuthority> doctorAuthorities;
	private Collection<GrantedAuthority> laboratorianAuthorities;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = loadUserFromDatabase(username);
		if (user == null) {
			throw new UsernameNotFoundException("UserAccount for name \""
					+ username + "\" not found.");
		}
		return user;
	}

	@PostConstruct
	public void init() {
		adminAuthorities = new ArrayList<GrantedAuthority>();
		adminAuthorities.add(new SimpleGrantedAuthority ("ROLE_ADMIN"));
		
		doctorAuthorities = new ArrayList<GrantedAuthority>();
		doctorAuthorities.add(new SimpleGrantedAuthority ("ROLE_DOCTOR"));
		
		laboratorianAuthorities = new ArrayList<GrantedAuthority>();
		laboratorianAuthorities.add(new SimpleGrantedAuthority ("ROLE_LABORATORIAN"));
	}

	public User loadUserFromDatabase(String username){
		Collection<GrantedAuthority> authorities = null;
		
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		Doctor doctor = doctorService.getDoctorByLogin(username);
		
		String role = doctor.getRole();
		if (role.equals("ROLE_ADMIN"))
			authorities = adminAuthorities;
		if (role.equals("ROLE_DOCTOR"))
			authorities = doctorAuthorities;
		if (role.equals("ROLE_LABORATORIAN"))
			authorities = laboratorianAuthorities;
			
		User user = null;
		if (doctor != null)
			user =  new User(doctor.getLogin(), doctor.getPassword(), enabled, accountNonExpired,
					credentialsNonExpired, accountNonLocked, authorities);
		return user;
	}
}