package cat.itacademy.proyectoerp.security.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.UserType;
import cat.itacademy.proyectoerp.repository.IUserRepository;
import cat.itacademy.proyectoerp.security.CustomLoginFailureHandler;
import cat.itacademy.proyectoerp.security.CustomLoginSuccessHandler;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	IUserRepository userDao;

	@Autowired
	LoginAttemptsService loginAttemptsService;
	
	@Autowired
    private CustomLoginFailureHandler loginFailureHandler;
     
    @Autowired
    private CustomLoginSuccessHandler loginSuccessHandler;
	
//	private User user;
	
//	public boolean isAccountNonLocked() {
//        return user.isAccountNonLocked();
//    }
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//final org.springframework.security.core.userdetails.User UserSecurity;
		Set<GrantedAuthority> authoritiesUser = new HashSet<GrantedAuthority>(UserType.values().length);
               //  new ArrayList<GrantedAuthority>();
		
		//final User user = userDao.findByUsername(username);

		if (null == userDao.findByUsername(username)) {
			throw new UsernameNotFoundException("Username not found");
		}
		
		

		//for (String role : user.getUserType())
		authoritiesUser.add(new SimpleGrantedAuthority("ROLE_" + userDao.findByUsername(username).getUserType().toString()));

		
		
		UserDetails userDetails = User.withUsername(userDao.findByUsername(username).getUsername()).password(userDao.findByUsername(username).getPassword()).authorities(authoritiesUser).build();
		System.out.println(userDetails.toString());
		if (userDetails == null) {
			loginFailureHandler.onAuthenticationFailure(username);
			return null;
		} else {
			return userDetails;			
		}
		
	
		//		
//		cat.itacademy.proyectoerp.domain.User user = userDao.findByUsername(username);
//		
//		loginAttemptsService.increaseFailedAttempts(user);
		
	}
}