package cat.itacademy.proyectoerp.security.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import cat.itacademy.proyectoerp.dao.UserDao;
import cat.itacademy.proyectoerp.domain.UserType;
import org.springframework.security.core.userdetails.User;

public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//final org.springframework.security.core.userdetails.User UserSecurity;
		
		Set<GrantedAuthority> authoritiesUser = new HashSet<GrantedAuthority>(UserType.values().length);
               //  new ArrayList<GrantedAuthority>();
		
		//final User user = userDao.findByUsername(username);
				
		if (userDao.findByUsername(username) == null)
			 throw new UsernameNotFoundException(username);
		
		//for (String role : user.getUserType())
		authoritiesUser.add(new SimpleGrantedAuthority("ROLE_" + userDao.findByUsername(username).getUserType().toString()));

		
		UserDetails userDetails = User.withUsername(userDao.findByUsername(username).getUsername()).password(userDao.findByUsername(username).getPassword()).authorities(authoritiesUser).build();
		
		return userDetails;
	}

}
