package com.harshit.dbms.controller;

import java.util.ArrayList;
//import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.harshit.dbms.Utils.HostName;
import com.harshit.dbms.Utils.Pair;
import com.harshit.dbms.dao.FacultyDAO;
import com.harshit.dbms.dao.FeedbackDAO;
import com.harshit.dbms.dao.PaymentAndLeavesDAO;
import com.harshit.dbms.dao.StaffDAO;
import com.harshit.dbms.dao.StudentDAO;
import com.harshit.dbms.dao.SubjectDAO;
import com.harshit.dbms.dao.TestDAO;
import com.harshit.dbms.dao.AlumnusDAO;
import com.harshit.dbms.dao.BatchDAO;
import com.harshit.dbms.dao.ContactUsFormDAO;
import com.harshit.dbms.dao.CourseDAO;
import com.harshit.dbms.dao.EmployeeDAO;
import com.harshit.dbms.dao.ExamDAO;
import com.harshit.dbms.dao.TestSeriesDAO;
import com.harshit.dbms.dao.UserDAO;
import com.harshit.dbms.model.Alumnus;
import com.harshit.dbms.model.Batch;
import com.harshit.dbms.model.ContactUsForm;
import com.harshit.dbms.model.Course;
import com.harshit.dbms.model.Employee;
import com.harshit.dbms.model.Exam;
import com.harshit.dbms.model.Faculty;
import com.harshit.dbms.model.Feedback;
import com.harshit.dbms.model.PaymentAndLeaves;
import com.harshit.dbms.model.Staff;
import com.harshit.dbms.model.Student;
import com.harshit.dbms.model.Subject;
import com.harshit.dbms.model.Test;
import com.harshit.dbms.model.TestSeries;
import com.harshit.dbms.model.User;
import com.harshit.dbms.service.EmailSenderService;
import com.harshit.dbms.service.SecurityService;
import org.springframework.transaction.annotation.Transactional;

@Controller
@Transactional
public class AdminController {
	
	@Autowired
	private UserDAO userDAO;
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
	private StudentDAO studentDAO;	
	@Autowired
	EmailSenderService emailSenderService;
	@Autowired
	private EmployeeDAO employeeDAO;
	@Autowired
	private PaymentAndLeavesDAO paymentDAO;
	@Autowired
	private BatchDAO batchDAO;
	@Autowired
	private FeedbackDAO feedbackDAO;
	@Autowired
	private TestDAO testDAO;

	@Autowired
	private AlumnusDAO alumnusDAO;
	
	@RequestMapping("/admin/")
	public String adminHome() {
		return "admin";
	}
	
	@RequestMapping({"/admin/details/"})
	public ModelAndView adminDetails() {	
		
		
		User user=userDAO.findByUsername("admin");

		ModelAndView model=new ModelAndView("admin_details");		
		model.addObject("user",user);
		
		return model;
		
	}
	
	@GetMapping("/admin/register/faculty/")
	public String registerFacultyForm(Model model) {
		Faculty faculty = new Faculty();
		
		Pair<List<Subject>,List<Subject>> subjects= subjectDAO.allSubject(); 
        List<Subject> allSubject = new ArrayList<Subject>();
        allSubject.addAll(subjects.getFirst());
        allSubject.addAll(subjects.getSecond());
        
		
		model.addAttribute("faculty", faculty);
		model.addAttribute("allSubject",allSubject);
		model.addAttribute("role","admin");
		
	    return "register_faculty";
	}
	
	@PostMapping("/admin/register/faculty/")
	public String registerFaculty(@Valid @ModelAttribute("faculty") Faculty faculty,BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
	    	Pair<List<Subject>,List<Subject>> subjects= subjectDAO.allSubject(); 
	        List<Subject> allSubject = new ArrayList<Subject>();
	        allSubject.addAll(subjects.getFirst());
	        allSubject.addAll(subjects.getSecond());
			
			model.addAttribute("faculty", faculty);
			model.addAttribute("allSubject",allSubject);
			model.addAttribute("role","admin");
	    	
	        return "register_faculty";
	    }
		
		User user=faculty.getEmployee().getUser();
		user.setRole("faculty");
		
		/////Email-Verification////////////////////////////////////
		String token = UUID.randomUUID().toString();
	    user.setToken(token);
	    SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmailID());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("coaching.dbms@gmail.com");
        mailMessage.setText("Your account has been registered on GPCoaching with username = "+user.getUsername()+" and temporary password = "+user.getPassword()+" To confirm your account, please click here : "
        +HostName.getHost()+"confirm-account?token="+token);
        
        user.setActive(false);
        /////Email-Verification////////////////////////////////////
        
        faculty.getEmployee().setUser(user);
        userDAO.save(user);
        faculty.setEmployee(employeeDAO.save(faculty.getEmployee()));
		facultyDAO.save(faculty);
		
		emailSenderService.sendEmail(mailMessage);

        return "redirect:/admin/allfaculty/";
	}
	
	@RequestMapping(value = "/admin/allfaculty/")
    public ModelAndView ShowAllFaculty() {
		
        ModelAndView model = new ModelAndView("all_faculty");
        List<Faculty> allFaculty = facultyDAO.allFaculty();
        model.addObject("allFaculty", allFaculty);
        return model;
    }
	
	@GetMapping("/admin/register/staff/")
	public String registerStaffForm(Model model) {
		Staff staff= new Staff();
		model.addAttribute("staff", staff);
		model.addAttribute("role","admin");
	    return "register_staff";
	}
	
	@PostMapping("/admin/register/staff/")
	public String registerStaff(@Valid @ModelAttribute("staff") Staff staff,BindingResult bindingResult, Model model) {
		

		if (bindingResult.hasErrors()) {
	    	 
	    	model.addAttribute("role","admin");
	        return "register_staff";
	    }
		
		User user=staff.getEmployee().getUser();
		user.setRole("staff");
		
		/////Email-Verification////////////////////////////////////
		String token = UUID.randomUUID().toString();
	    user.setToken(token);
	    SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmailID());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("coaching.dbms@gmail.com");
        mailMessage.setText("Your account has been registered on GPCoaching with username = "+user.getUsername()+" and temporary password = "+user.getPassword()+" To confirm your account, please click here : "
                +HostName.getHost()+"confirm-account?token="+token);
        
        user.setActive(false);
        /////Email-Verification////////////////////////////////////
        
        staff.getEmployee().setUser(user);
        userDAO.save(user);
        staff.setEmployee(employeeDAO.save(staff.getEmployee()));
		staffDAO.save(staff);
		
		emailSenderService.sendEmail(mailMessage);

        return "redirect:/admin/allstaff/";
	}
	
	@RequestMapping(value = "/admin/allstaff/")
    public ModelAndView ShowAllStaff() {
		
        ModelAndView model = new ModelAndView("all_staff");
        List<Staff> allStaff = staffDAO.allStaff();
         
        model.addObject("allStaff", allStaff);
        return model;
        
    }
	
	@RequestMapping(value = "/admin/allemployee/")
    public ModelAndView showAllEmployee() {
				
        ModelAndView model = new ModelAndView("all_employee");
        List<Employee> allEmployee = employeeDAO.allEmployee();
         
        model.addObject("allEmployee", allEmployee);
        return model;
        
    }
		
	@GetMapping(value = {"/admin/payment/faculty/{facultyID}/","/faculty/payment/"})
    public ModelAndView payrollFaculty(Authentication authentication, @PathVariable(value = "facultyID", required=false) Integer facultyID) {
		
		Faculty faculty;	
		
		if(facultyID==null) {
			String username=((UserDetails) authentication.getPrincipal()).getUsername();
			faculty = facultyDAO.findByUsername(username);
		}else {
			faculty = facultyDAO.findByFacultyID(facultyID);
		}
		int employeeID=faculty.getEmployee().getEmployeeID();
		
        ModelAndView model = new ModelAndView("all_payment_faculty");
        Employee employee= employeeDAO.findByEmployeeID(employeeID);
        model.addObject("faculty", faculty);
        List<PaymentAndLeaves> allPayment= paymentDAO.findByEmployeeID(employeeID);
        model.addObject("allPayment", allPayment);
        
         
         
        
        return model;
        
    }
	
	@GetMapping(value = "/admin/payment/faculty/")
    public ModelAndView payFacultyForm() {
		
        ModelAndView model = new ModelAndView("add_payment_faculty");
        List<Faculty> allFaculty = facultyDAO.allFaculty();
        model.addObject("allFaculty", allFaculty);
        PaymentAndLeaves payment= new PaymentAndLeaves();
        model.addObject("payment", payment);
       
        return model;
        
    }
	
	@PostMapping(value = "/admin/payment/faculty/")
    public ModelAndView payFaculty(@Valid @ModelAttribute("payment") PaymentAndLeaves payment, BindingResult bindingResult) {
		
		  
		
		if (bindingResult.hasErrors()) {
			
			ModelAndView model = new ModelAndView("add_payment_faculty");
	        List<Faculty> allFaculty = facultyDAO.allFaculty();
	        model.addObject("allFaculty", allFaculty);
	        return model;
	    }
        paymentDAO.save(payment);
        return new ModelAndView("redirect:/admin/allfaculty/");
    }
	
	
	@GetMapping(value = "/admin/update/payment/faculty/{id}/")
    public ModelAndView updateFacultyPaymentForm(@PathVariable(value="id") int id) {
		
        ModelAndView model = new ModelAndView("add_payment_faculty");
        PaymentAndLeaves payment = paymentDAO.getByID(id);
        List<Faculty> allFaculty = facultyDAO.allFaculty();
        model.addObject("payment", payment);
        model.addObject("allFaculty", allFaculty);
        return model;
        
    }
	
	@PostMapping(value = "/admin/update/payment/faculty/{id}/")
    public ModelAndView updateFacultyPayment(@Valid @ModelAttribute("payment") PaymentAndLeaves payment,@PathVariable(value="id") int id, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
	    	ModelAndView model = new ModelAndView("add_payment_faculty");
	    	List<Faculty> allFaculty = facultyDAO.allFaculty();
	    	model.addObject("allFaculty", allFaculty);
	        return model;
	    }
		
		paymentDAO.update(payment);
		
		int employeeID = payment.getEmployee().getEmployeeID();
		Employee employee= employeeDAO.findByEmployeeID(employeeID);
		Faculty faculty = facultyDAO.findByUsername(employee.getUser().getUsername());
        return new ModelAndView("redirect:/admin/payment/faculty/"+faculty.getFacultyID()+"/");
        
    }
	
	@GetMapping(value = "/admin/delete/payment/faculty/{id}/")
    public ModelAndView deleteFacultyPayment(@PathVariable(value="id") int id) {
		
		PaymentAndLeaves payment = paymentDAO.getByID(id);
		int employeeID = payment.getEmployee().getEmployeeID();
		Employee employee= employeeDAO.findByEmployeeID(employeeID);
		Faculty faculty = facultyDAO.findByUsername(employee.getUser().getUsername());
		paymentDAO.delete(id);
		
        return new ModelAndView("redirect:/admin/payment/faculty/"+faculty.getFacultyID()+"/");

    }
	
	@GetMapping(value = {"/admin/payment/staff/{staffID}/","/staff/payment/"})
    public ModelAndView payrollStaff(Authentication authentication, @PathVariable(value = "staffID", required=false) Integer staffID) {
		
		
		Staff staff;	
		
		if(staffID==null) {
			String username=((UserDetails) authentication.getPrincipal()).getUsername();
			staff = staffDAO.findByUsername(username);
		}else {
			staff = staffDAO.findByStaffID(staffID);
		}

		int employeeID=staff.getEmployee().getEmployeeID();
		
        ModelAndView model = new ModelAndView("all_payment_staff");
        Employee employee= employeeDAO.findByEmployeeID(employeeID);
        model.addObject("staff", staff);
        List<PaymentAndLeaves> allPayment= paymentDAO.findByEmployeeID(employeeID);
        model.addObject("allPayment", allPayment);
        
         
         
        
        return model;
        
    }
	
	@GetMapping(value = "/admin/payment/staff/")
    public ModelAndView payStaffForm() {
		
        ModelAndView model = new ModelAndView("add_payment_staff");
        List<Staff> allStaff = staffDAO.allStaff();
        model.addObject("allStaff", allStaff);
        PaymentAndLeaves payment= new PaymentAndLeaves();
        model.addObject("payment", payment);
       
        return model;
        
    }
	
	@PostMapping(value = "/admin/payment/staff/")
    public ModelAndView payStaff(@Valid @ModelAttribute("payment") PaymentAndLeaves payment, BindingResult bindingResult) {
		
		  
		
		if (bindingResult.hasErrors()) {
			
			ModelAndView model = new ModelAndView("add_payment_staff");
	        List<Staff> allStaff = staffDAO.allStaff();
	        model.addObject("allStaff", allStaff);
	        return model;
	    }
        paymentDAO.save(payment);
        return new ModelAndView("redirect:/admin/allstaff/");
    }
	
	
	@GetMapping(value = "/admin/update/payment/staff/{id}/")
    public ModelAndView updateStaffPaymentForm(@PathVariable(value="id") int id) {
        ModelAndView model = new ModelAndView("add_payment_staff");
        PaymentAndLeaves payment = paymentDAO.getByID(id);
        List<Staff> allStaff = staffDAO.allStaff();
        model.addObject("payment", payment);
        model.addObject("allStaff", allStaff);
        return model;
    }
	
	@PostMapping(value = "/admin/update/payment/staff/{id}/")
    public ModelAndView updateStaffPayment(@Valid @ModelAttribute("payment") PaymentAndLeaves payment,@PathVariable(value="id") int id, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
	    	ModelAndView model = new ModelAndView("add_payment_staff");
	    	List<Staff> allStaff = staffDAO.allStaff();
	    	model.addObject("allStaff", allStaff);
	        return model;
	    }
		
		paymentDAO.update(payment);
		
		int employeeID = payment.getEmployee().getEmployeeID();
		Employee employee= employeeDAO.findByEmployeeID(employeeID);
		Staff staff = staffDAO.findByUsername(employee.getUser().getUsername());
        return new ModelAndView("redirect:/admin/payment/staff/"+staff.getStaffID()+"/");
        
    }
	
	@GetMapping(value = "/admin/delete/payment/staff/{id}/")
    public ModelAndView deleteStaffPayment(@PathVariable(value="id") int id) {
		
		PaymentAndLeaves payment = paymentDAO.getByID(id);
		int employeeID = payment.getEmployee().getEmployeeID();
		Employee employee= employeeDAO.findByEmployeeID(employeeID);
		Staff staff = staffDAO.findByUsername(employee.getUser().getUsername());
		paymentDAO.delete(id);
		
        return new ModelAndView("redirect:/admin/payment/staff/"+staff.getStaffID()+"/");

		
    }
	
	

	@GetMapping(value = "/admin/add/subject/")
    public ModelAndView addSubjectForm() {
        ModelAndView model = new ModelAndView("add_subject");
        Subject subject = new Subject();
        model.addObject("subject", subject);
        List<Faculty> allFaculty = facultyDAO.allFaculty();
        model.addObject("allFaculty", allFaculty);
        return model;
    }
	
	@PostMapping(value ="/admin/add/subject/")
    public ModelAndView addSubject(@Valid @ModelAttribute("subject") Subject subject, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
	    	ModelAndView model = new ModelAndView("add_subject");
	    	List<Faculty> allFaculty = facultyDAO.allFaculty();
	    	model.addObject("allFaculty", allFaculty);
	        return model;
	    }
		if(subject.getHead().getFacultyID()==0) {
			subject.setHead(null);
		}
		subjectDAO.save(subject);
        return new ModelAndView("redirect:/admin/allsubject/");
    }
	
	
	@GetMapping(value = "/admin/update/subject/{subjectID}/")
    public ModelAndView updateSubjectForm(@PathVariable(value="subjectID") int subjectID) {
        ModelAndView model = new ModelAndView("add_subject");
        Subject subject = subjectDAO.getBySubjectID(subjectID);
        if(subject.getHead()!=null) subject.setHead(facultyDAO.findByFacultyID(subject.getHead().getFacultyID()));
        model.addObject("subject", subject);
        List<Faculty> allFaculty = facultyDAO.allFaculty();
        model.addObject("allFaculty", allFaculty);
        return model;
    }
	
	@PostMapping(value = "/admin/update/subject/{subjectID}/")
    public ModelAndView updateSubject(@PathVariable(value="subjectID") int subjectID,@Valid @ModelAttribute("subject") Subject subject, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
	    	ModelAndView model = new ModelAndView("add_subject");
	    	List<Faculty> allFaculty = facultyDAO.allFaculty();
	    	model.addObject("allFaculty", allFaculty);
	        return model;
	    }
		if(subject.getHead().getFacultyID()==0) {
			subject.setHead(null);
		}
		subjectDAO.update(subject);
        return new ModelAndView("redirect:/admin/allsubject/");
    }
	
	@GetMapping(value = "/admin/delete/subject/{subjectID}/")
    public ModelAndView deleteSubject(@PathVariable(value="subjectID") int subjectID) {
        ModelAndView model = new ModelAndView("redirect:/admin/allsubject/");
        subjectDAO.delete(subjectID);
        return model;
    }
	
	@GetMapping(value = "/admin/add/exam/")
    public ModelAndView addExamForm() {
        ModelAndView model = new ModelAndView("add_exam");
        Exam exam = new Exam();
        model.addObject("exam", exam);
        Pair<List<Subject>,List<Subject>> subjects= subjectDAO.allSubject();
        
        List<Subject> allSubject = new ArrayList<Subject>();
        allSubject.addAll(subjects.getFirst());
        allSubject.addAll(subjects.getSecond());
        
        model.addObject("allSubject", allSubject);
        return model;
    }
	
	
	@PostMapping(value ="/admin/add/exam/")
    public ModelAndView addExam(@Valid @ModelAttribute("exam") Exam exam, BindingResult bindingResult,HttpServletRequest request) {
		
		BindingResult newBindingResult = new BeanPropertyBindingResult(exam, "exam");
		List<FieldError> errorsToKeep = bindingResult.getFieldErrors().stream()
                .filter(fer -> !fer.getField().equals("subjects"))
                .collect(Collectors.toList());
		for (FieldError fieldError : errorsToKeep) {
			newBindingResult.addError(fieldError);
        }
		
		String error="";
		
		if (request.getParameterValues("subjects")==null) {
			newBindingResult.rejectValue("subjects","choose at least one subject");	
			error="Please choose at least one subject";
		}	
	
		
		if (newBindingResult.hasErrors()) {
			
	    	 
	    	ModelAndView model = new ModelAndView("add_exam");
	    	Pair<List<Subject>,List<Subject>> subjects= subjectDAO.allSubject();
	    	List<Subject> allSubject = new ArrayList<Subject>();
	        allSubject.addAll(subjects.getFirst());
	        allSubject.addAll(subjects.getSecond());
	        model.addObject("allSubject", allSubject);
	        model.addObject("error", error);
	        return model;
	        
	    }	
		
		List<Subject> subjectList= new ArrayList<Subject>();
		
		for(String subjectIDStr:request.getParameterValues("subjects")) {
			int subjectID=Integer.parseInt(subjectIDStr);
			Subject subject= new Subject();
			subject.setSubjectID(subjectID);
			subjectList.add(subject);
			 
		}
		
		exam.setSubjects(subjectList);
		
		
		examDAO.save(exam);
        return new ModelAndView("redirect:/admin/allexam/");
    }
	
	@GetMapping(value = "/admin/update/exam/{examID}/")
    public ModelAndView updateExamForm(@PathVariable(value="examID") int examID) {
		
        ModelAndView model = new ModelAndView("add_exam");
        Exam exam = examDAO.getByExamID(examID);
        List<Subject> subjectList = subjectDAO.getByExamID(examID);
		exam.setSubjects(subjectList);
		List<Course> courseList = courseDAO.getByExamID(examID);
		exam.setCourses(courseList);
		
        model.addObject("exam", exam);
        Pair<List<Subject>,List<Subject>> subjects= subjectDAO.allSubject();
        
        List<Subject> allSubject = new ArrayList<Subject>();
        allSubject.addAll(subjects.getFirst());
        allSubject.addAll(subjects.getSecond());
        model.addObject("allSubject", allSubject);
        
        return model;
    }
	
	@PostMapping(value ="/admin/update/exam/{examID}/")
    public ModelAndView updateExam(@PathVariable(value="examID") int examID,@Valid @ModelAttribute("exam") Exam exam, BindingResult bindingResult,HttpServletRequest request) {
		
		BindingResult newBindingResult = new BeanPropertyBindingResult(exam, "exam");
		List<FieldError> errorsToKeep = bindingResult.getFieldErrors().stream()
                .filter(fer -> !fer.getField().equals("subjects"))
                .collect(Collectors.toList());
		for (FieldError fieldError : errorsToKeep) {
			newBindingResult.addError(fieldError);
        }
		
		String error="";
		
		if (request.getParameterValues("subjects")==null) {
			 
			error="Please choose at least one subject";
			newBindingResult.rejectValue("subjects","choose at least one subject");	
		}	
	
		
		if (newBindingResult.hasErrors()) {
			
	    	 
	    	ModelAndView model = new ModelAndView("add_exam");
	    	Pair<List<Subject>,List<Subject>> subjects= subjectDAO.allSubject();
	    	List<Subject> allSubject = new ArrayList<Subject>();
	        allSubject.addAll(subjects.getFirst());
	        allSubject.addAll(subjects.getSecond());
	        model.addObject("allSubject", allSubject);
	        model.addObject("error", error);
	        return model;
	        
	    }	
		
		List<Subject> subjectList= new ArrayList<Subject>();
		
		for(String subjectIDStr:request.getParameterValues("subjects")) {
			int subjectID=Integer.parseInt(subjectIDStr);
			Subject subject= new Subject();
			subject.setSubjectID(subjectID);
			subjectList.add(subject);
			 
		}

		exam.setSubjects(subjectList);		
		examDAO.update(exam);
        return new ModelAndView("redirect:/admin/allexam/"+examID+"/");
    }
	
	@GetMapping(value = "/admin/delete/exam/{examID}/")
    public ModelAndView deleteExam(@PathVariable(value="examID") int examID) {
        ModelAndView model = new ModelAndView("redirect:/admin/allexam/");
        examDAO.delete(examID);
        return model;
    }
	
	
	
	@GetMapping(value = "/admin/add/course/")
    public ModelAndView addCourseForm() {
        ModelAndView model = new ModelAndView("add_course");
        Course course = new Course();
        model.addObject("course", course);
        List<Exam> allExam = examDAO.allExam();
        model.addObject("allExam", allExam);
        return model;
    }
	
	@PostMapping(value ="/admin/add/course/")
    public ModelAndView addCourse(@Valid @ModelAttribute("course") Course course, BindingResult bindingResult) {
		
		 
		if (bindingResult.hasErrors()) {
			ModelAndView model = new ModelAndView("add_course");
	    	List<Exam> allExam = examDAO.allExam();
	    	model.addObject("allExam", allExam);
	        return model;
	    }		
		courseDAO.save(course);
		return new ModelAndView("redirect:/admin/allcourse/");
    }
	
	@GetMapping(value = "/admin/update/course/{courseID}/")
    public ModelAndView updateCourseForm(@PathVariable(value="courseID") int courseID) {
        ModelAndView model = new ModelAndView("add_course");
        Course course = courseDAO.getByCourseID(courseID,false);
        
        List<Batch> batchList = batchDAO.getByCourseID(courseID);
		
        List<TestSeries> testSeriesList = testSeriesDAO.getByCourseID(courseID);
        
        course.setBatches(batchList);
        course.setTestSeries(testSeriesList);
        
        model.addObject("course", course);
        List<Exam> allExam = examDAO.allExam();
        model.addObject("allExam", allExam);
         
        return model;
    }
	
	@PostMapping(value = "/admin/update/course/{courseID}/")
    public ModelAndView updateCourse(@PathVariable(value="courseID") int courseID,@Valid @ModelAttribute("course") Course course, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
	    	ModelAndView model = new ModelAndView("add_course");
	    	List<Exam> allExam = examDAO.allExam();
	    	model.addObject("allExam", allExam);
	        return model;
	    }
		courseDAO.update(course);
        return new ModelAndView("redirect:/admin/allcourse/"+courseID+"/");
    }
	
	@GetMapping(value = "/admin/delete/course/{courseID}/")
    public ModelAndView deleteCourse(@PathVariable(value="courseID") int courseID) {
        ModelAndView model = new ModelAndView("redirect:/admin/allcourse/");
        courseDAO.delete(courseID);
        return model;
    }
	
	
	
	@GetMapping(value = "/admin/add/testseries/")
    public ModelAndView addTestSeriesForm() {
        ModelAndView model = new ModelAndView("add_testSeries");
        TestSeries testSeries = new TestSeries();
        model.addObject("testSeries", testSeries);
        List<Course> allCourse = courseDAO.allCourse();
        model.addObject("allCourse", allCourse);
        return model;
    }
	
	@PostMapping(value ="/admin/add/testseries/")
    public ModelAndView addTestSeries(@Valid @ModelAttribute("testSeries") TestSeries testSeries, BindingResult bindingResult) {
		
		 
		if (bindingResult.hasErrors()) {
			ModelAndView model = new ModelAndView("add_testSeries");
	    	List<Course> allCourse = courseDAO.allCourse();
	    	model.addObject("allCourse", allCourse);
	        return model;
	    }	
		testSeriesDAO.save(testSeries);
		return new ModelAndView("redirect:/admin/alltestseries/");
    }
	
	@GetMapping(value = "/admin/update/testseries/{testSeriesID}/")
    public ModelAndView updateTestSeriesForm(@PathVariable(value="testSeriesID") int testSeriesID) {
        ModelAndView model = new ModelAndView("add_testSeries");
        TestSeries testSeries = testSeriesDAO.getByTestSeriesID(testSeriesID);
        List<Test> testList = testDAO.getByTestSeriesID(testSeriesID);
		testSeries.setTestList(testList);
        model.addObject("testSeries", testSeries);
        List<Course> allCourse = courseDAO.allCourse();
        model.addObject("allCourse", allCourse);
         
        return model;
    }
	
	@PostMapping(value = "/admin/update/testseries/{testSeriesID}/")
    public ModelAndView updateTestSeries(@PathVariable(value="testSeriesID") int testSeriesID,@Valid @ModelAttribute("testSeries") TestSeries testSeries, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
	    	ModelAndView model = new ModelAndView("add_testSeries");
	    	List<Course> allCourse = courseDAO.allCourse();
	    	model.addObject("allCourse", allCourse);
	        return model;
	    }
		testSeriesDAO.update(testSeries);
        return new ModelAndView("redirect:/admin/alltestseries/"+testSeriesID+"/");
    }
	
	@GetMapping(value = "/admin/delete/testseries/{testSeriesID}/")
    public ModelAndView deleteTestSeries(@PathVariable(value="testSeriesID") int testSeriesID) {
        ModelAndView model = new ModelAndView("redirect:/admin/alltestseries/");
        testSeriesDAO.delete(testSeriesID);
        return model;
    }
	
	@RequestMapping(value = "/admin/allfeedback/")
    public ModelAndView ShowAllfeedback() {		
        ModelAndView model = new ModelAndView("all_feedback");
        List<Feedback> allFeedback = feedbackDAO.allFeedback();
        model.addObject("allFeedback", allFeedback);
        return model;
    }
	
	@RequestMapping(value = "/admin/allfeedback/delete/{feedbackID}/")
    public ModelAndView deleteFeedback(Authentication authentication,@PathVariable(value="feedbackID") int feedbackID) {		
    	
		feedbackDAO.delete(feedbackID);
        ModelAndView model = new ModelAndView("redirect:/admin/allfeedback/");
        return model;
        
	}
	

	@GetMapping(value = "/admin/add/alumnus/")
    public ModelAndView addAlumnusForm() {
        ModelAndView model = new ModelAndView("add_alumnus");
        Alumnus alumnus = new Alumnus();
        model.addObject("alumnus", alumnus);
        List<Student> allStudent = studentDAO.allStudentNotAlumnus();
        model.addObject("allStudent", allStudent);
        return model;
    }
	
	@PostMapping(value ="/admin/add/alumnus/")
    public ModelAndView addAlumnus(@Valid @ModelAttribute("alumnus") Alumnus alumnus, BindingResult bindingResult) {
		
		 
		if (bindingResult.hasErrors()) {
	    	 
	    	ModelAndView model = new ModelAndView("add_alumnus");
	        List<Student> allStudent = studentDAO.allStudentNotAlumnus();
	        model.addObject("allStudent", allStudent);
	        return model;
	    }		
		alumnusDAO.save(alumnus);
        return new ModelAndView("redirect:/admin/allalumnus/");
    }
	
	@GetMapping(value = "/admin/update/alumnus/{alumnusID}/")
    public ModelAndView updateAlumnusForm(@PathVariable(value="alumnusID") int alumnusID) {
        ModelAndView model = new ModelAndView("add_alumnus");
        Alumnus alumnus = alumnusDAO.getByAlumnusID(alumnusID);
        model.addObject("alumnus", alumnus);
        List<Student> allStudent = studentDAO.allStudentNotAlumnus();
        allStudent.add(alumnus.getStudent());
        model.addObject("allStudent", allStudent);
         
        return model;
    }
	
	@PostMapping(value = "/admin/update/alumnus/{alumnusID}/")
    public ModelAndView updateAlumnus(@PathVariable(value="alumnusID") int alumnusID,@Valid @ModelAttribute("alumnus") Alumnus alumnus, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {			
			return new ModelAndView("redirect:/admin/update/alumnus/"+alumnusID);
	    }
		
		alumnusDAO.update(alumnus);
        return new ModelAndView("redirect:/admin/allalumnus/");
    }
	
	@GetMapping(value = "/admin/delete/alumnus/{alumnusID}/")
    public ModelAndView deleteAlumnus(@PathVariable(value="alumnusID") int alumnusID) {
        ModelAndView model = new ModelAndView("redirect:/admin/allalumnus/");
        alumnusDAO.delete(alumnusID);
        return model;
    }
	
	
	
	@GetMapping(value = "/admin/delete/student/{studentID}/")
    public ModelAndView deleteStudent(@PathVariable(value="studentID") int studentID) {
        ModelAndView model = new ModelAndView("redirect:/admin/allstudent/");
        Student student=studentDAO.findByStudentID(studentID);
        userDAO.delete(student.getUser().getUsername());
        return model;
    }
	
	@GetMapping(value = "/admin/delete/faculty/{facultyID}/")
    public ModelAndView deleteFaculty(@PathVariable(value="facultyID") int facultyID) {
        ModelAndView model = new ModelAndView("redirect:/admin/allfaculty/");
        Faculty faculty=facultyDAO.findByFacultyID(facultyID);
        userDAO.delete(faculty.getEmployee().getUser().getUsername());
        return model;
    }
	
	@GetMapping(value = "/admin/delete/staff/{staffID}/")
    public ModelAndView deleteStaff(@PathVariable(value="staffID") int staffID) {
        ModelAndView model = new ModelAndView("redirect:/admin/allstaff/");
        Staff staff=staffDAO.findByStaffID(staffID);
        userDAO.delete(staff.getEmployee().getUser().getUsername());
        return model;
    }
	
}
