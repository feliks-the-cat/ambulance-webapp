package com.ambulance.core.web.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.ambulance.core.service.AuthenticationService;

@Named
@Scope("session")
public final class LoginBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String login;
	private String password;

	@Inject
	private AuthenticationService authenticationService;

	public String login() {
		boolean success = authenticationService.login(login, password);
		if (success){
			String LoggedUserRole = getLoggedUserRole();
			String redirect_page = null;
			
			if (LoggedUserRole.equals("ROLE_ADMIN"))
				redirect_page = "/admin/users.xhtml?faces-redirect=true";
			
			if (LoggedUserRole.equals("ROLE_DOCTOR"))
				redirect_page = "/doctor/patients.xhtml?faces-redirect=true";
			
			if (LoggedUserRole.equals("ROLE_LABORATORIAN"))
				redirect_page = "/laboratorian/analysis.xhtml?faces-redirect=true";
			return redirect_page;
		}
		else{
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Имя пользователя или пароль введены неверно"));			
			return "login.xhtml";
		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	private String getLoggedUserRole(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(user.getAuthorities());
		GrantedAuthority authority = authorities.get(0);
		return authority.getAuthority();
	}
}