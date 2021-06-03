package com.yueejia;

import com.yueejia.data.CommentRepository;
import com.yueejia.data.PostRepository;
import com.yueejia.data.RoleRepository;
import com.yueejia.data.UserRepository;
import com.yueejia.model.BlogPost;
import com.yueejia.model.Comment;
import com.yueejia.model.Role;
import com.yueejia.model.User;
import com.yueejia.security.CustomOAuth2User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private RoleRepository roleRepository;
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
    @GetMapping("/logoutSuccessful")
    public String goLogout(){
        return "logoutSuccessful";
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
    public String createPost(@ModelAttribute BlogPost blogPost, Model model,Authentication auth){
        User currentUser = userRepository.findByUsername(auth.getName());
        Role ownerRole = roleRepository.findByRoleName("ROLE_OWNER");
        if(currentUser.getRole().contains(ownerRole)) {
            ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("America/Montreal"));
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            BlogPost newBlogPost = blogPost;
            User usr = userRepository.findByUsername(auth.getName());
            newBlogPost.setZonedDateTime(zdt);
            newBlogPost.setImg("abc");
            newBlogPost.setUser(usr);
            Iterable<Comment> cmt = commentRepository.findAll(); //cannot use postRepository get old blogpost and update part of old post and save it.
            for (Comment c : cmt) {                                  //seems must deal with ModelAttribute passed blogpost
                if (c.getBlogPost().getId() == newBlogPost.getId())
                    newBlogPost.getComment().add(c);
            }
            postRepository.save(newBlogPost);
            List<BlogPost> p = usr.getBlogPost();
            if (!p.contains(newBlogPost))
                p.add(newBlogPost);

            userRepository.save(usr);

            model.addAttribute("blogPost", new BlogPost());
        }else {
            model.addAttribute("errormessage","You have not be authorized to do this");
            return "error";
        }
        return "redirect:/listP";
    }


    @PostMapping("/postDetails/{id}/addComment")
    public String addComment(@PathVariable Long id, @ModelAttribute Comment comment123, Model model,Authentication auth){
        User currentUser = userRepository.findByUsername(auth.getName());
        Role ownerRole = roleRepository.findByRoleName("ROLE_OWNER");
        Role oauthRole = roleRepository.findByRoleName("ROLE_OAUTH");
        if(currentUser.getRole().contains(ownerRole)||currentUser.getRole().contains(oauthRole)) {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String ctnt = comment123.getCmtContent();
            Comment cmt = new Comment(ctnt);
//        Comment cmt = comment123;
            User usr = userRepository.findByUsername(auth.getName());
            BlogPost bp = postRepository.findById(id);
            ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("America/Montreal"));

            cmt.setUser(usr);
            cmt.setBlogPost(bp);
            cmt.setZonedDateTime(zdt);
            commentRepository.save(cmt);

            usr.getComment().add(cmt);
            userRepository.save(usr);

            bp.getComment().add(cmt);
            postRepository.save(bp);
        }else {
            model.addAttribute("errormessage","You have not be authorized to do this");
            return "error";
        }
        return "redirect:/postDetails/"+id;
    }

    @GetMapping("/owner/goEdit/{id}")
    public String edit(@PathVariable long id,Model model){
        BlogPost bp = postRepository.findById(id);
        model.addAttribute("blogPost", bp);
        return "owner/addPost";
    }
    @GetMapping("/owner/delete/{id}")
    public String delete(@PathVariable long id,Model model,Authentication auth){
        User currentUser = userRepository.findByUsername(auth.getName());
        Role ownerRole = roleRepository.findByRoleName("ROLE_OWNER");
        if(currentUser.getRole().contains(ownerRole)){
        BlogPost bp = postRepository.findById(id);
        List<Comment> cmts = commentRepository.findByBlogPost(bp);
        for(Comment c: cmts){
            c.getUser().getComment().remove(c);
            bp.getComment().remove(c);
            commentRepository.delete(c);
        }
        bp.getUser().getBlogPost().remove(bp);
        postRepository.delete(bp);
        }else {
            model.addAttribute("errormessage","You have not be authorized to do this");
            return "error";
        }
//        postRepository.deleteById(id); this not working 	No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call; nested exception is javax.persistence.TransactionRequiredException: No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call
        return "redirect:/owner/editing"; // no space between redirect: and /
    }
//    @PostMapping("/owner/postImgUpload")
//    public String upload(@RequestParam("file") MultipartFile file, ModelMap modelMap){
//        modelMap.addAttribute("file", file);
//
//        return "redirect: /owner/createPost";
//    }
}