package com.yueejia;

import com.yueejia.data.CommentRepository;
import com.yueejia.data.PostRepository;
import com.yueejia.data.UserRepository;
import com.yueejia.model.BlogPost;
import com.yueejia.model.Comment;
import com.yueejia.model.User;
import com.yueejia.security.CustomOAuth2User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BlogController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

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
        model.addAttribute("comments",bp.getComment());
        model.addAttribute("comment123",new Comment());
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
    @GetMapping("/owner/editing")
    public String goEditing(Model model){
        List<BlogPost> posts =postRepository.findAll();
        model.addAttribute("blogPosts",posts);
        List<Integer> commentCounts = new ArrayList<>();
        for(BlogPost ep:posts){
            commentCounts.add(ep.getComment().size());
        }
        model.addAttribute("commentCounts",commentCounts);
        return "owner/editing";
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
        User usr = userRepository.findByUsername(auth.getName());
        newBlogPost.setZonedDateTime(ZonedDateTime.now(ZoneId.of( "America/Montreal" )) );
        newBlogPost.setImg("abc");
        newBlogPost.setUser(usr);
        postRepository.save(newBlogPost);
        usr.getBlogPost().add(newBlogPost);
        userRepository.save(usr);
        model.addAttribute("blogPost", new BlogPost());
        return "redirect:/listP";
    }


    @PostMapping("/postDetails/{id}/addComment")
    public String addComment(@PathVariable Long id, @ModelAttribute Comment comment123, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String ctnt = comment123.getCmtContent();
        Comment cmt = new Comment(ctnt);
//        Comment cmt = comment123;
        User usr = userRepository.findByUsername(auth.getName());
        BlogPost bp = postRepository.findById(id);
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of( "America/Montreal" ));

        cmt.setUser(usr);
        cmt.setBlogPost(bp);
        cmt.setZonedDateTime(zdt);
        commentRepository.save(cmt);

        usr.getComment().add(cmt);
        userRepository.save(usr);

        bp.getComment().add(cmt);
        postRepository.save(bp);

        return "redirect:/postDetails/"+id;
    }
}