package com.yueejia;

import com.yueejia.data.PostRepository;
import com.yueejia.data.UserRepository;
import com.yueejia.model.BlogPost;
import com.yueejia.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Controller
public class BlogController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public BlogController(PostRepository pr){
        postRepository = pr;
    }
    @GetMapping("/")
    public String listPosts(ModelMap model){
        List<BlogPost> lp = postRepository.findAll();
        model.put("posts",lp);
        return "client/home";
    }
    @GetMapping("/listP")
    public String goListP(Model model){
        List<BlogPost> posts =postRepository.findAll();
        model.addAttribute("blogPosts",posts);
        return "client/listPost";
    }

    @GetMapping("/postDetails/{id}")
    public String goPostDetails(@PathVariable Long id, Model model){
        BlogPost bp= postRepository.findById(id);
        model.addAttribute("blogPost",bp);
        return "client/post-details";
    }
    @GetMapping("/aboutMe")
    public String goAboutMe(){
        return "client/aboutMe";
    }
    @GetMapping("/myWork")
    public String goMyWork(){
        return "client/myWork";
    }



    @GetMapping("/owner/ownerLogin")
    public String goOwnerLogin(){
        return "owner/ownerLogin";
    }
    @GetMapping("/blogPost/{id}")
    public String postDetails(@PathVariable Long id, ModelMap modelMap){
        BlogPost blogPost = postRepository.findById(id);
        modelMap.put("blogPost", blogPost);
        return "client/post-details";
    }
    @GetMapping("/owner/goAddPost")
    public String goAddPost(Model model){
        model.addAttribute("blogPost", new BlogPost());
        return "owner/addPost";
    }
    @PostMapping("/owner/createPost")
    public String createPost(@ModelAttribute BlogPost blogPost, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BlogPost newBlogPost = blogPost;
        newBlogPost.setZonedDateTime(ZonedDateTime.now(ZoneId.of( "America/Montreal" )) );
        newBlogPost.setImg("abc");
        newBlogPost.setUser(userRepository.findByUsername(auth.getName()));
        postRepository.save(newBlogPost);
        model.addAttribute("blogPost", new BlogPost());
        return "redirect:/listP";
    }
}
