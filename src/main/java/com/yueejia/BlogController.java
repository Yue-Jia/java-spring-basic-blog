package com.yueejia;

import com.yueejia.data.PostRepository;
import com.yueejia.data.UserRepository;
import com.yueejia.model.BlogPost;
import com.yueejia.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String goListP(){
        return "client/listPost";
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
        BlogPost newBlogPost = blogPost;
        newBlogPost.setZonedDateTime(ZonedDateTime.now(ZoneId.of( "America/Montreal" )) );
        newBlogPost.setImg("abc");
        newBlogPost.setUser(userRepository.findById(Long.valueOf(1)));
        postRepository.save(newBlogPost);
        model.addAttribute("blogPost", new BlogPost());
        return "client/listPost";
    }
}
