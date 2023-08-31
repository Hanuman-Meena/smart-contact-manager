package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	       
	       @Autowired
	       private UserRepository userRepository;
            
	       @GetMapping("/home")
	       public String home(Model model)
	       {
	    	   model.addAttribute("title","Home- Smart Contact Manager");
	    	   
	    	   return "home";
	       }
	       
	       @GetMapping("/")
	       public String homePage(Model model)
	       {
	    	   model.addAttribute("title","Home- Smart Contact Manager");
	    	   
	    	   return "home";
	       }
	        
	       @GetMapping("/about")
	       public String about(Model model)
	       {
	    	   model.addAttribute("title","About- Smart Contact Manager");
	    	   
	    	   return "about";
	       }
	       
	       @GetMapping("/signup")
	       public String signUp(Model model, HttpSession session)
	       {
	    	   model.addAttribute("title","Register- Smart Contact Manager");
	    	   model.addAttribute("user", new User());
	    	   model.addAttribute("session", session);
	    	   
	    	   return "signup";
	       }
	       
	       //This handler is for registering user
           @PostMapping("/do_signup")
	       public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1 , @RequestParam(value = "agreement",defaultValue = "false")
	       boolean agreement, Model model, HttpSession session)
	       {
        	  try {
        		  if(!agreement)
           	   {
           		   System.out.println("you have not agreed the terms and conditions");
           		   throw new Exception("you have not agreed the terms and conditions");
           	   }
        		  
        		  if(result1.hasErrors())
        		  {   
        			  System.out.println("ERROR"+result1.toString());
        			  model.addAttribute("user",user);
        			  return "signup";
        		  }
           	   
           	   user.setRole("USER_ROLE");
           	   user.setEnabled(true);
           	   
           	   System.out.println("USER"+' '+ user);
	    	   System.out.println("Agreement"+ ' '+ agreement);
	    	   
           	   User result = this.userRepository.save(user);
           	   model.addAttribute("user",new User());
           
           	   
           	session.setAttribute("message", new Message("Successfully registered!!", "alert-success"));
    	   
   	    	 return "signup";     
   	    	  
			} catch (Exception e) {
				
				e.printStackTrace();
				model.addAttribute("user",user);
				session.setAttribute("message", new Message("Somethig went wrong!!" + e.getMessage(), "alert-danger"));
				return "signup";
			}       	
        	  
	       }
	       
}
