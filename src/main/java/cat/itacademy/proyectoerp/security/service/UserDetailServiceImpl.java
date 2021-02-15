package cat.itacademy.proyectoerp.security.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.UserType;
import cat.itacademy.proyectoerp.repository.IUserRepository;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	IUserRepository userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//final org.springframework.security.core.userdetails.User UserSecurity;
		
		Set<GrantedAuthority> authoritiesUser = new HashSet<GrantedAuthority>(UserType.values().length);
               //  new ArrayList<GrantedAuthority>();
		
		//final User user = userDao.findByUsername(username);
				
		if (userDao.withUsername(username) == null)
			 throw new UsernameNotFoundException(username);
		
		//for (String role : user.getUserType())
		authoritiesUser.add(new SimpleGrantedAuthority("ROLE_" + userDao.withUsername(username).getUserType().toString()));

		
		UserDetails userDetails = User.withUsername(userDao.withUsername(username).getUsername()).password(userDao.withUsername(username).getPassword()).authorities(authoritiesUser).build();
		
		return userDetails;
	}


}
