package com.harshit.dbms.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.harshit.dbms.Utils.Pair;
import com.harshit.dbms.dao.AlumnusDAO;
import com.harshit.dbms.dao.BatchDAO;
import com.harshit.dbms.dao.ContactUsFormDAO;
import com.harshit.dbms.dao.CourseDAO;
import com.harshit.dbms.dao.EmployeeDAO;
import com.harshit.dbms.dao.ExamDAO;
import com.harshit.dbms.dao.FacultyDAO;
import com.harshit.dbms.dao.FeedbackDAO;
import com.harshit.dbms.dao.PaymentAndLeavesDAO;
import com.harshit.dbms.dao.StaffDAO;
import com.harshit.dbms.dao.StudentDAO;
import com.harshit.dbms.dao.SubjectDAO;
import com.harshit.dbms.dao.TestDAO;
import com.harshit.dbms.dao.TestSeriesDAO;
import com.harshit.dbms.dao.UserDAO;
import com.harshit.dbms.model.Alumnus;
import com.harshit.dbms.model.Batch;
import com.harshit.dbms.model.Course;
import com.harshit.dbms.model.Faculty;
import com.harshit.dbms.model.Student;
import com.harshit.dbms.model.Subject;
import com.harshit.dbms.model.Test;
import com.harshit.dbms.model.TestSeries;
import com.harshit.dbms.model.Work;
import com.harshit.dbms.service.EmailSenderService;
import com.harshit.dbms.service.SecurityService;


@Controller
@Transactional
public class FacultyController {
	
	@Autowired
	private FacultyDAO facultyDAO;
	@Autowired
	private StaffDAO staffDAO;
	@Autowired
	private SubjectDAO subjectDAO;
	@Autowired
	private ExamDAO examDAO;
	@Autowired
	private CourseDAO courseDAO;
	@Autowired
	private TestSeriesDAO testSeriesDAO;
	@Autowired
	private TestDAO testDAO;
	@Autowired
	private StudentDAO studentDAO;	
	@Autowired
	EmailSenderService emailSenderService;
	@Autowired
	private EmployeeDAO employeeDAO;
	@Autowired
	private PaymentAndLeavesDAO paymentDAO;
	@Autowired
	private ContactUsFormDAO contactUsFormDAO;
	@Autowired
	private FeedbackDAO feedbackDAO;
	@Autowired
	private AlumnusDAO alumnusDAO;
	@Autowired
	private BatchDAO batchDAO;
	@Autowired
	private UserDAO userDAO;
	
	
	
	@RequestMapping("/faculty/")
	public String faculty() {		
		return "faculty";
	}
	
	@RequestMapping({"/faculty/details/","/admin/faculty/{username}/"})
	public ModelAndView facultyDetails(Authentication authentication,@PathVariable(value = "username", required = false) String username) {	
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		if(username == null) username=((UserDetails) authentication.getPrincipal()).getUsername();
		
		Faculty faculty=facultyDAO.findByUsername(username);
		
		if(faculty.getSubject()!=null) {
			faculty.setSubject(subjectDAO.getBySubjectID(faculty.getSubject().getSubjectID()));
		}

		ModelAndView model=new ModelAndView("faculty_details");
		model.addObject("role",role);
		
		model.addObject("faculty",faculty);
		
		return model;
		
	}
	

	@GetMapping({"/faculty/update/","/admin/faculty/update/{username}/"})
	public ModelAndView updateFaculty(Authentication authentication,@PathVariable(value = "username", required = false) String username) {	
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		 
		if(username == null) username=((UserDetails) authentication.getPrincipal()).getUsername();
		Faculty faculty=facultyDAO.findByUsername(username);
		
		if(faculty==null) {
			ModelAndView model=new ModelAndView("message");
			model.addObject("message","incorrect username");
			return model;
		}
		
		ModelAndView model=new ModelAndView("register_faculty");
		
		Pair<List<Subject>,List<Subject>> subjects= subjectDAO.allSubject(); 
        List<Subject> allSubject = new ArrayList<Subject>();
        allSubject.addAll(subjects.getFirst());
        allSubject.addAll(subjects.getSecond());
        
		model.addObject("allSubject",allSubject);
		model.addObject("faculty",faculty);
		model.addObject("role",role);
				
		return model;
		
	}
	
	@PostMapping({"/faculty/update/","/admin/faculty/update/{username}/"})
	public ModelAndView submitForm(Authentication authentication,@Valid @ModelAttribute("faculty") Faculty faculty, BindingResult bindingResult, @PathVariable(value = "username", required = false) String username) {
	 	
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		ModelAndView model = new ModelAndView();
		
		
		 
	    if (bindingResult.hasErrors()) {
	    	
	    	Pair<List<Subject>,List<Subject>> subjects= subjectDAO.allSubject(); 
	        List<Subject> allSubject = new ArrayList<Subject>();
	        allSubject.addAll(subjects.getFirst());
	        allSubject.addAll(subjects.getSecond());
			model.addObject("allSubject",allSubject);
	    	model.setViewName( "register_faculty");
	    	model.addObject("role",role);
	    	return model;
	    	
	    }	   
    	
    	if(username == null) {
	    	username=((UserDetails) authentication.getPrincipal()).getUsername();
	    	model.setViewName("redirect:/faculty/details/");
	    }else {
	    	model.setViewName("redirect:/"+role+"/faculty/"+username+"/");
	    }
    	
    	faculty.getEmployee().setEmployeeID(facultyDAO.findByUsername(username).getEmployee().getEmployeeID());
 	    faculty.getEmployee().getUser().setUsername(username);
 	    userDAO.update(faculty.getEmployee().getUser());
 	    employeeDAO.update(faculty.getEmployee());
     	facultyDAO.update(faculty);
	    
    	return model;
		   		
	}
	
	@RequestMapping(value = {"/admin/alltestseries/","/faculty/alltestseries/","/staff/alltestseries/"})
    public ModelAndView ShowAllTestSeries(Authentication authentication) {	
		
        ModelAndView model = new ModelAndView("all_testSeries");
        List<TestSeries> allTestSeries= testSeriesDAO.allTestSeries();
         
        model.addObject("allTestSeries", allTestSeries);
        String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
        model.addObject("role",role);
        
        return model;
    }
	
	@RequestMapping(value = {"/staff/alltest/","/admin/alltest/","/faculty/alltest/"})
    public ModelAndView ShowAllTest(Authentication authentication) {		
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
        ModelAndView model = new ModelAndView("all_test");
        List<Test> allTest= testDAO.allTest();
         
        model.addObject("allTest", allTest);
        model.addObject("role", role);
        return model;
        
    }
	
	@RequestMapping(value = {"/staff/allbatch/","/admin/allbatch/","/faculty/allbatch/"})
    public ModelAndView ShowAllBatch(Authentication authentication) {
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
        ModelAndView model = new ModelAndView("all_batch");
        List<Batch> allBatch= batchDAO.allBatch();
         
        model.addObject("allBatch", allBatch);
        model.addObject("role", role);
        return model;
    }
	
	@RequestMapping(value = {"/admin/allstudent/","/faculty/allstudent/","/staff/allstudent/"})
    public ModelAndView ShowAllStudent() {		
        ModelAndView model = new ModelAndView("all_student");
        List<Student> allStudent = studentDAO.allStudent();
        model.addObject("allStudent", allStudent);
        return model;
    }
	
	@RequestMapping(value = {"/admin/allsubject/","/faculty/allsubject/","/staff/allsubject/"})
    public ModelAndView ShowAllSubject() {		
        ModelAndView model = new ModelAndView("all_subject");
        Pair<List<Subject>,List<Subject>> allSubject= subjectDAO.allSubject();
        model.addObject("allSubject", allSubject);
        return model;
    }
	
	@RequestMapping(value = {"/admin/allalumnus/","/staff/allalumnus/","/faculty/allalumnus/"})
	public ModelAndView ShowAllAlumnus() {		
	    ModelAndView model = new ModelAndView("all_alumnus");
	    List<Alumnus> allAlumnus= alumnusDAO.allAlumnus();
	     
	    model.addObject("allAlumnus", allAlumnus);
	    return model;
	}
		

}
