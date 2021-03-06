package security;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.security
 * %%
 * Copyright (C) 2013 Osgiliath corp
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
/**
 * Represent a User
 * @author Charlie
 *
 */
@SuppressWarnings("serial")
public class MUser implements UserDetails, Serializable {
	/**
	 * His Pseudo
	 */
	private String pseudo;
	/**
	 * His Email
	 */
	private String password;
	/**
	 * His rights
	 */
	private Collection<MAuthority> mAuthorities = new HashSet<MAuthority>();
	public String getPseudo() {
		return pseudo;
	}
	/**
	 * Returns a List of Authorities
	 */
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> ret = new HashSet<GrantedAuthority>();
		for (MAuthority authority : mAuthorities)
			ret.add(authority);
		return ret;
	}
	/**
	 * Spring rm management
	 */
	@Override
	public String getUsername() {
		return pseudo;
	}
	/**
	 * account expired
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	/**
	 * Account locked
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	/**
	 * Rights expired
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	/**
	 * Account enabled
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Collection<MAuthority> getmAuthorities() {
		return mAuthorities;
	}
	public void setmAuthorities(Collection<MAuthority> mAuthorities) {
		this.mAuthorities = mAuthorities;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
}
