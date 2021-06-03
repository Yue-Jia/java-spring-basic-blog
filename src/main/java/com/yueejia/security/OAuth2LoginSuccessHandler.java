package com.yueejia.security;

import com.yueejia.data.RoleRepository;
import com.yueejia.data.UserRepository;
import com.yueejia.model.AuthenticationProvider;
import com.yueejia.model.Role;
import com.yueejia.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository ;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{
        CustomOAuth2User oAuth2User = new CustomOAuth2User((OAuth2User) authentication.getPrincipal());
        String email = oAuth2User.getEmail();
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByRoleName("ROLE_OAUTH");
        if(user == null){
            User usr = new User();
            ZonedDateTime zdt = ZonedDateTime.now();
            usr.setAuth_provider(AuthenticationProvider.GOOGLE);
            usr.setAvatar(oAuth2User.getImageUrl());
            usr.setEmail(email);
            usr.getRole().add(role);
            usr.setName(oAuth2User.getName());
            usr.setUsername(oAuth2User.getUsername());
            usr.setLastLogin(zdt);
            userRepository.save(usr);
        }else{
            //update existing customer
            ZonedDateTime zdt = ZonedDateTime.now();
            user.setAuth_provider(AuthenticationProvider.GOOGLE);
            user.setAvatar(oAuth2User.getImageUrl());
            user.setEmail(email);
            if(!user.getRole().contains(role)){
                user.getRole().add(role);
            }
            user.setName(oAuth2User.getName());
            user.setUsername(oAuth2User.getUsername());
            user.setLastLogin(zdt);
            userRepository.save(user);
        }
        super.onAuthenticationSuccess(request,response,authentication);
    }
}
