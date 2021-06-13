package com.yueejia.security;

import com.yueejia.data.RoleRepository;
import com.yueejia.data.UserRepository;
import com.yueejia.model.AuthenticationProvider;
import com.yueejia.model.Role;
import com.yueejia.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.ZonedDateTime;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository ;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{

        CustomOAuth2User oAuth2User = new CustomOAuth2User((OAuth2User) authentication.getPrincipal());
        String email = oAuth2User.getEmail();
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByRoleName("ROLE_OAUTH");
        String previousUrl = (String) request.getServletContext().getAttribute("previouseUrl");
        if(user == null){
            User usr = new User();
            ZonedDateTime zdt = ZonedDateTime.now();

            if(oAuth2User.getUsername()!=null&&oAuth2User.getImageUrl()!=null){
                usr.setAvatar(oAuth2User.getImageUrl());
                usr.setUsername(oAuth2User.getUsername());
                usr.setAuth_provider(AuthenticationProvider.GOOGLE);
            }else{
                usr.setAvatar(oAuth2User.getFacebookImageUrl());
                usr.setUsername(oAuth2User.getFacebookUsername());
                usr.setAuth_provider(AuthenticationProvider.FACEBOOK);
            }

            usr.setEmail(email);
            usr.getRole().add(role);
            usr.setName(oAuth2User.getName());

            usr.setLastLogin(zdt);
            userRepository.save(usr);
            redirectStrategy.sendRedirect(request,response,previousUrl);
        }else{
            //update existing customer
            ZonedDateTime zdt = ZonedDateTime.now();
            user.setEmail(email);
            if(!user.getRole().contains(role)){
                user.getRole().add(role);
            }
            user.setName(oAuth2User.getName());
            if(oAuth2User.getUsername()!=null&&oAuth2User.getImageUrl()!=null){
                user.setAvatar(oAuth2User.getImageUrl());
                user.setUsername(oAuth2User.getUsername());
                user.setAuth_provider(AuthenticationProvider.GOOGLE);
            }else{
                user.setAvatar(oAuth2User.getFacebookImageUrl());
                user.setUsername(oAuth2User.getFacebookUsername());
                user.setAuth_provider(AuthenticationProvider.FACEBOOK);
            }
            user.setLastLogin(zdt);
            userRepository.save(user);
            redirectStrategy.sendRedirect(request,response,previousUrl);
        }
        super.onAuthenticationSuccess(request,response,authentication);
    }
}
