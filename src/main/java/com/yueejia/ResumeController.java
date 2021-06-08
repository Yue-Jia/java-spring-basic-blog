package com.yueejia;

import com.yueejia.data.*;
import com.yueejia.model.*;
import com.yueejia.tools.ImageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ResumeController {
    @Autowired
    private MyWorkRepository myWorkRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private ResumeRepository resumeRepository;
    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("/owner/goEditResume")
    public String goEditResume(Model model){
        Resume r = resumeRepository.findFirstByOrderByIdAsc();
        model.addAttribute("education",new Education());
        model.addAttribute("skill",new Skill());
        if(r ==null)
            model.addAttribute("resume",new Resume());
        else
            model.addAttribute("resume", r);
        model.addAttribute("experience", new Experience());
        return "owner/editResume";
    }

    @GetMapping("/aboutMe")
    public String goAboutMe(Model model){
        Role role = roleRepository.findByRoleName("ROLE_OWNER");
        Resume resume = resumeRepository.findFirstByOrderByIdAsc();
        model.addAttribute("myWorks",myWorkRepository.findAll());
        model.addAttribute("ownerme",userRepository.findByRoleContaining(role));
        if(resume!=null)
            model.addAttribute("resume",resume);
        else
            model.addAttribute("resume", new Resume());
        model.addAttribute("experiences", experienceRepository.findAll());
        model.addAttribute("skills",skillRepository.findAll());
        model.addAttribute("educations",educationRepository.findAll());
        return "client/aboutMe";
    }

    @PostMapping("/owner/addEducation")
    public String addEducation(@ModelAttribute Education education){
        educationRepository.save(education);
        return "redirect:/owner/goEditResume";
    }
    @PostMapping("/owner/addSkill")
    public String addSkill(@ModelAttribute Skill skill){
        skillRepository.save(skill);
        return "redirect:/owner/goEditResume";
    }
    @PostMapping("/owner/addResume")
    public String addResume(@ModelAttribute Resume resume,Model model){
        resumeRepository.save(resume);
        return "redirect:/owner/goEditResume";
    }
    @PostMapping("/owner/addExperience")
    public String addExperience(@ModelAttribute Experience experience){
        experienceRepository.save(experience);
        return "redirect:/owner/goEditResume";
    }
    @GetMapping("/owner/deleteExperience/{id}")
    public String deleteExperience(@PathVariable Integer id, Model model, Authentication auth){
        User currentUser = userRepository.findByUsername(auth.getName());
        Role ownerRole = roleRepository.findByRoleName("ROLE_OWNER");
        if(currentUser.getRole().contains(ownerRole)){
            Experience exp = experienceRepository.findById(id).get();
            experienceRepository.delete(exp);
        }else {
            model.addAttribute("errormessage","You have not be authorized to do this");
            return "error";
        }
//        postRepository.deleteById(id); this not working 	No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call; nested exception is javax.persistence.TransactionRequiredException: No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call
        return "redirect:/aboutMe"; // no space between redirect: and /
    }
    @GetMapping("/owner/deleteSkill/{id}")
    public String deleteSkill(@PathVariable Integer id, Model model, Authentication auth){
        User currentUser = userRepository.findByUsername(auth.getName());
        Role ownerRole = roleRepository.findByRoleName("ROLE_OWNER");
        if(currentUser.getRole().contains(ownerRole)){
            Skill skill = skillRepository.findById(id).get();
            skillRepository.delete(skill);
        }else {
            model.addAttribute("errormessage","You have not be authorized to do this");
            return "error";
        }
//        postRepository.deleteById(id); this not working 	No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call; nested exception is javax.persistence.TransactionRequiredException: No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call
        return "redirect:/aboutMe"; // no space between redirect: and /
    }
    @GetMapping("/owner/deleteEducation/{id}")
    public String deleteEducation(@PathVariable Integer id, Model model, Authentication auth){
        User currentUser = userRepository.findByUsername(auth.getName());
        Role ownerRole = roleRepository.findByRoleName("ROLE_OWNER");
        if(currentUser.getRole().contains(ownerRole)){
            Education education = educationRepository.findById(id).get();
            educationRepository.delete(education);
        }else {
            model.addAttribute("errormessage","You have not be authorized to do this");
            return "error";
        }
//        postRepository.deleteById(id); this not working 	No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call; nested exception is javax.persistence.TransactionRequiredException: No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call
        return "redirect:/aboutMe"; // no space between redirect: and /
    }
}
