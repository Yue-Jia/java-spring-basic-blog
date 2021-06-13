package com.yueejia.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private OAuth2User oAuth2User;
    public CustomOAuth2User(OAuth2User oAuth2User){
        this.oAuth2User=oAuth2User;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("name");
    }

    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }

    public String getGivenName(){
        return oAuth2User.getAttribute("given_name");
    }
    public String getFamilyName(){
        return oAuth2User.getAttribute("family_name");
    }

    public String getImageUrl(){
        return oAuth2User.getAttribute("picture");
    }
    public String getLoginTime(){
        return oAuth2User.getAttribute("iat");
    }
    public String getUsername(){
        return oAuth2User.getAttribute("sub");
    }

    public String getFacebookUsername(){
        return oAuth2User.getAttribute("name");
    }
    public String getFacebookId(){
        return oAuth2User.getAttribute("id");
    }
    public String getFacebookImageUrl() {
        if(oAuth2User.getAttributes().containsKey("picture")) {
            Map<String, Object> pictureObj = (Map<String, Object>) oAuth2User.getAttributes().get("picture");
            if(pictureObj.containsKey("data")) {
                Map<String, Object>  dataObj = (Map<String, Object>) pictureObj.get("data");
                if(dataObj.containsKey("url")) {
                    return  dataObj.get("url").toString();
                }
            }
        }else{
            String url = "/images/pic1.png";
            return url;
        }
        return null;
    }

}
