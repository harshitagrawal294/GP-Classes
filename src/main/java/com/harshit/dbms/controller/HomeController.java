package com.harshit.dbms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.harshit.dbms.Utils.Pair;
import com.harshit.dbms.dao.AlumnusDAO;
import com.harshit.dbms.dao.ContactUsFormDAO;
import com.harshit.dbms.dao.CourseDAO;
import com.harshit.dbms.dao.FacultyApplicantDAO;
import com.harshit.dbms.dao.FacultyDAO;
import com.harshit.dbms.dao.SubjectDAO;
import com.harshit.dbms.model.Alumnus;
import com.harshit.dbms.model.ContactUsForm;
import com.harshit.dbms.model.Course;
import com.harshit.dbms.model.Faculty;
import com.harshit.dbms.model.FacultyApplicant;
import com.harshit.dbms.model.Student;
import com.harshit.dbms.model.Subject;
import com.harshit.dbms.service.EmailSenderService;


@Controller
@Transactional
public class HomeController {
	
	@Autowired
	FacultyApplicantDAO facultyApplicantDAO;
	@Autowired
	SubjectDAO subjectDAO;
	@Autowired
	EmailSenderService emailSenderService;
	@Autowired
	ContactUsFormDAO contactUsFormDAO;
	@Autowired
	CourseDAO courseDAO;
	@Autowired
	AlumnusDAO alumnusDAO;
	@Autowired
	FacultyDAO facultyDAO;
	
	@RequestMapping("/")
	public ModelAndView home() {
		
		ModelAndView model = new ModelAndView("home");
        return model;		
		
	}
	
	@GetMapping(value = "/send/application/")
    public ModelAndView addFacultyApplicantForm() {
		
        ModelAndView model = new ModelAndView("add_facultyapplicant");
        FacultyApplicant facultyApplicant = new FacultyApplicant();
        model.addObject("facultyApplicant", facultyApplicant);
        Pair<List<Subject>,List<Subject>> subjects= subjectDAO.allSubject();
    	List<Subject> allSubject = new ArrayList<Subject>();
        allSubject.addAll(subjects.getFirst());
        allSubject.addAll(subjects.getSecond());
        model.addObject("allSubject", allSubject);
        return model;
        
    }
	
	
	
	@PostMapping(value ="/send/application/")
    public ModelAndView addFacultyApplicant(@Valid @ModelAttribute("facultyApplicant") FacultyApplicant facultyApplicant, BindingResult bindingResult) {
		
		
		
		if (bindingResult.hasErrors()) {
	    	
	    	ModelAndView model = new ModelAndView("add_facultyapplicant");
	    	Pair<List<Subject>,List<Subject>> subjects= subjectDAO.allSubject();
	    	List<Subject> allSubject = new ArrayList<Subject>();
	        allSubject.addAll(subjects.getFirst());
	        allSubject.addAll(subjects.getSecond());
	        model.addObject("allSubject", allSubject);
	        return model;
	    }	
		
		facultyApplicant.setApplicationProcessed(false);
		facultyApplicant.setApplicationDate(new Date());
		facultyApplicant.setApplicationReviewStatus(false);
		
		 ////////////////////////Email-Verification/////////////////////
	    String token = UUID.randomUUID().toString();
	    facultyApplicant.setConfirmationToken(token);
	    SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(facultyApplicant.getApplicantEmailID());
        mailMessage.setSubject("Job Application!");
        mailMessage.setFrom("coaching.dbms@gmail.com");
        mailMessage.setText("To submit your application, please click here : "
        +"http://localhost:8080/submit-application?token="+token);
        ///////////////////////Email-Verification/////////////////////
		
		
		
		facultyApplicant=facultyApplicantDAO.save(facultyApplicant);        
        ModelAndView model = new ModelAndView("message");
        model.addObject("message", "Please click on the link sent to your email");		
        emailSenderService.sendEmail(mailMessage);
        
        return model;
    }
	
	 @RequestMapping(value="/submit-application", method= {RequestMethod.GET, RequestMethod.POST})
	 public ModelAndView processApplication(@RequestParam("token")String confirmationToken)
	 {
		 
		 FacultyApplicant facultyApplicant = facultyApplicantDAO.findByConfirmationToken(confirmationToken);
		 ModelAndView model=new ModelAndView("message");
		 
	     if(facultyApplicant != null)
	     {
	    	 if(facultyApplicant.isApplicationProcessed()) {
	    		 
	    		 model.addObject("message", "Application already submitted!");
	    		 
	    	 }else {
	    		 
	    		 facultyApplicant.setApplicationProcessed(true);
		    	 facultyApplicantDAO.processed(facultyApplicant);
		    	 model.addObject("message", "Your application has been submitted succesfully");		
	    		 
	    	 }    

	     }
	     else
	     {
	         model.addObject("message", "Broken Link");		
	     }

	     return model;
	 }
	 
	 @GetMapping(value = "/contactus/")
	 public ModelAndView addContactUsForm() {			
	    ModelAndView model = new ModelAndView("add_contactusform");
	    ContactUsForm contactUsForm = new ContactUsForm();
	    model.addObject("contactUsForm", contactUsForm);
	    return model;
	        
	 }
		
		
	 @PostMapping(value ="/contactus/")
	 public ModelAndView addContactUs(@Valid @ModelAttribute("contactUsForm") ContactUsForm contactUsForm, BindingResult bindingResult) {
		
		 if (bindingResult.hasErrors()) {
	     	
	     	ModelAndView model = new ModelAndView("add_contactusform");
	        return model;
		 }	
		
		 ModelAndView model = new ModelAndView("message");
		 contactUsFormDAO.save(contactUsForm);
		 model.addObject("message", "Your message has been sent successfully");
		
		
		 return model;
	 }
	 
	 @RequestMapping(value = {"/allcourse/","/student/allcourse/","/admin/allcourse/","/faculty/allcourse/","/staff/allcourse/"})
	 public ModelAndView ShowAllCourse() {
	 
	     ModelAndView model = new ModelAndView("all_course");
	     List<Course> allCourse= courseDAO.allCourse();
	     model.addObject("allCourse", allCourse);
	     
	     return model;
	 }
	 
	 @RequestMapping(value = {"/allalumnus/"})
	 public ModelAndView ShowAllAlumnus() {		
	     ModelAndView model = new ModelAndView("guest_alumnus");
	     List<Alumnus> allAlumnus= alumnusDAO.allAlumnus();
	     
	     model.addObject("allAlumnus", allAlumnus);
	     return model;
	 }
	 
	 @RequestMapping(value = "/allfaculty/")
	 public ModelAndView ShowAllFaculty() {
		
	     ModelAndView model = new ModelAndView("all_viewfaculty");
	     List<Faculty> allFaculty = facultyDAO.allFaculty();
	     
	     model.addObject("allFaculty", allFaculty);
	     return model;
	 }
	 
	 @RequestMapping(value = "/allfaq/")
	 public ModelAndView ShowAllFAQ() {
		
	     ModelAndView model = new ModelAndView("all_faq");
	     List<ContactUsForm> FAQ = contactUsFormDAO.allContactUsForm();
	     model.addObject("FAQ", FAQ);
	     
	     return model;
	 }
	 
	 @RequestMapping(value = "/privacy/")
	 public ModelAndView privacy() {
		
	     ModelAndView model = new ModelAndView("privacy");
     
	     return model;
	 }
	 
	 


}


