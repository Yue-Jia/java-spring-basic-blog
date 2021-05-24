package com.yueejia.security;

import com.yueejia.data.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //   userdetail is using data base to store the securityconfig class which config method store user info
        //find the user by the username
        com.yueejia.model.User user = userRepo.findByUsername(username);
        //if the user dont exist then stop
        if(user==null) {
            System.out.println("User "+username+" not found");
            throw new UsernameNotFoundException("User "+username+" not found");
        }
        //change the list of the user's roles into a list of GrantedAuthorities
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        for(com.yueejia.model.Role role: user.getRole()) {
            grantList.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        //using the info above create a userDetails for spring to verify with the user logging info
        UserDetails userDetails = (UserDetails)new User(user.getUsername(),user.getPassword(),grantList);
        return userDetails;
    }
}
