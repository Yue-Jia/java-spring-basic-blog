package com.yueejia;

import com.yueejia.data.RoleRepository;
import com.yueejia.data.UserRepository;
import com.yueejia.model.Role;
import com.yueejia.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class RController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @PostMapping(value="/owner/updateMuted/{id}")
    public String putUserMuted(@PathVariable Long id) {
        User user = userRepository.findById(id).get();
        Role ownerRole = roleRepository.findByRoleName("ROLE_OWNER");
        if(!user.getRole().contains(ownerRole)) {
        boolean en = user.isEnabled();
        user.setEnabled(!en);
        userRepository.save(user);
        return "User "+user.getName()+" is enabled: "+!en;
        }
        else{
            return "Blog owner cannot be muted.";
        }
    }

    @PostMapping(value="/saveServletContext")
    public String saveServletContext(HttpServletRequest request){
        request.getServletContext().setAttribute("previouseUrl",request.getHeader("Referer"));
        return "blogPost id saved in session";
    }
}
