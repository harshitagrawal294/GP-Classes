package com.harshit.dbms.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.harshit.dbms.Utils.HostName;
import com.harshit.dbms.dao.StudentDAO;
import com.harshit.dbms.dao.UserDAO;
import com.harshit.dbms.model.FacultyApplicant;
import com.harshit.dbms.model.Staff;
import com.harshit.dbms.model.Student;
import com.harshit.dbms.model.User;
import com.harshit.dbms.service.EmailSenderService;
import com.harshit.dbms.service.SecurityService;

@Controller
@Transactional
public class LoginController {
	
	 @Autowired
	 private SecurityService securityService;
	 @Autowired
	 private StudentDAO studentDAO;
	 @Autowired
	 EmailSenderService emailSenderService;
	 @Autowired
	 private UserDAO userDAO;
	
	 @GetMapping("/register/student/")
	 public String showForm(Model model,HttpServletRequest request) {
		 new SecurityContextLogoutHandler().logout(request, null, null);
	 	 Student student = new Student();		 
	 	 model.addAttribute("student", student);
	 	 model.addAttribute("role", "none");
	     return "register_student";
	 }

	 @PostMapping("/register/student/")
	 public String submitForm(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult, Model model) {
	 	
	    if (bindingResult.hasErrors()) {
	    	
	    	model.addAttribute("role", "none");
	        return "register_student";
	    }
	    
	    User user= student.getUser();
	    user.setRole("student");
	    
	    ////////////////////////Email-Verification/////////////////////
	    String token = UUID.randomUUID().toString();
	    user.setToken(token);
	    SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmailID());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("coaching.dbms@gmail.com");
        mailMessage.setText("Your account has been registered on GPCoaching. To confirm your account, please click here : "
                +HostName.getHost()+"confirm-account?token="+token);
        
        user.setActive(false);
        ///////////////////////Email-Verification/////////////////////
        
        String password=user.getPassword();
	 	String username=user.getUsername();
	 	student.setUser(user);
	 	userDAO.save(user);
	    student=studentDAO.save(student);	
	    emailSenderService.sendEmail(mailMessage);
	    securityService.autoLogin(username,password);
	    
        
	    
		return "redirect:/welcome";
	}
	 
	 @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
	 public ModelAndView confirmAccount(@RequestParam("token")String token,HttpServletRequest request)
	 {
		 
		 User user = userDAO.findByConfirmationToken(token);
		 new SecurityContextLogoutHandler().logout(request, null, null);
		 
	     if(user != null)
	     {
	    	 ModelAndView model=new ModelAndView("confirm_account");
	    	 user.setActive(true);
	    	 
	    	 userDAO.update(user);
	    	 return model;
	     }
	     else
	     {	
	    	 ModelAndView model=new ModelAndView("message");
	         model.addObject("message", "Broken Link");		
	         return model;
	     }

	    
	 }
	
	@GetMapping("/welcome")
	public ModelAndView welcome(Principal principal) {
		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		User user = userDAO.findByUsername(principal.getName());
		
		ModelAndView model=new ModelAndView();
		if(!user.isActive()) {
			model.addObject("message", "Please verify your Email ID first");
			model.setViewName("message");
		}else if(user.getRole().equals("student")) {
			model.setViewName("redirect:/student/");
		}else if(user.getRole().equals("faculty")) {
			model.setViewName("redirect:/faculty/");
		}else if(user.getRole().equals("staff")){
			model.setViewName("redirect:/staff/");
		}else if(user.getRole().equals("admin")) {
			model.setViewName("redirect:/admin/");
		}else {
			model.setViewName("redirect:/403");
		}
		
		return model;
	}

	
	@GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
	
	@GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
	
	@GetMapping(value={"/student/password/","/faculty/password/","/staff/password/","/admin/password/","/admin/password/{username}/"})
	public ModelAndView changePasswordForm(Authentication authentication,@PathVariable(value = "username", required = false) String username) {	
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		if(username == null) username=((UserDetails) authentication.getPrincipal()).getUsername();
		User user = userDAO.findByUsername(username);
		
		if(user==null) {
			ModelAndView model=new ModelAndView("message");
			model.addObject("message","incorrect username");
			return model;
		}
		
		ModelAndView model=new ModelAndView("change_password");

		model.addObject("user",user);
		model.addObject("role",role);
						
		return model;
		
	}
	
	@PostMapping(value={"/student/password/","/faculty/password/","/staff/password/","/admin/password/","/admin/password/{username}/"})
	public ModelAndView changePassword(Authentication authentication,@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @PathVariable(value = "username", required = false) String username) {
	 	
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		ModelAndView model = new ModelAndView();
		
	    if (bindingResult.getFieldError("password") != null) {	    
	    	
	    	model.setViewName("change_password");
	    	model.addObject("role",role);
	    	return model;	    	
	    }
	
	    if(username == null) {username=((UserDetails) authentication.getPrincipal()).getUsername();}
	    
	    User oldUser = userDAO.findByUsername(username);
		
		if(user==null) {
			model.setViewName("message");
			model.addObject("message","incorrect username");
			return model;
		}
		
		boolean isAdmin;
				
		if(role.equals("admin")) {
			isAdmin=true;
			user.setOldPassword("dummy");
		}else {
			isAdmin=false;
		}
		
		boolean success=userDAO.updatePassword(username,oldUser.getPassword(),user.getOldPassword(),user.getPassword(),isAdmin);	
		
		if(!success) {
			model.setViewName( "change_password");
	    	model.addObject("role",role);
	    	model.addObject("error","Incorrect Password");
	    	return model;
		}else {
			model.setViewName("message");
			model.addObject("message","Password has been changed successfully");
			return model;
		}
		
		   		
	}
	
}

