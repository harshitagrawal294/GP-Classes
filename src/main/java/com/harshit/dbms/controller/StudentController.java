package com.harshit.dbms.controller;

import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.harshit.dbms.Utils.HostName;
import com.harshit.dbms.Utils.Pair;
import com.harshit.dbms.dao.BatchDAO;
import com.harshit.dbms.dao.CourseDAO;
import com.harshit.dbms.dao.EnrollDAO;
import com.harshit.dbms.dao.ExamDAO;
import com.harshit.dbms.dao.FacultyDAO;
import com.harshit.dbms.dao.FeedbackDAO;
import com.harshit.dbms.dao.StudentDAO;
import com.harshit.dbms.dao.SubjectDAO;
import com.harshit.dbms.dao.TestDAO;
import com.harshit.dbms.dao.TestSeriesDAO;
import com.harshit.dbms.dao.TransactionDAO;
import com.harshit.dbms.dao.UserDAO;
import com.harshit.dbms.dao.WorkDAO;
import com.harshit.dbms.model.AttemptTest;
import com.harshit.dbms.model.Batch;
import com.harshit.dbms.model.Course;
import com.harshit.dbms.model.Enroll;
import com.harshit.dbms.model.Exam;
import com.harshit.dbms.model.Faculty;
import com.harshit.dbms.model.Feedback;
import com.harshit.dbms.model.Student;
import com.harshit.dbms.model.Subject;
import com.harshit.dbms.model.Test;
import com.harshit.dbms.model.TestSeries;
import com.harshit.dbms.model.Transaction;
import com.harshit.dbms.model.User;
import com.harshit.dbms.model.Work;
import com.harshit.dbms.service.SecurityService;
import com.instamojo.wrapper.api.ApiContext;
import com.instamojo.wrapper.api.Instamojo;
//import com.sun.mail.iap.ConnectionException;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderResponse;

@Controller
@Transactional
public class StudentController {
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private StudentDAO studentDAO;
	
	@Autowired
	private ExamDAO examDAO;
	
	@Autowired
	private SubjectDAO subjectDAO;
	
	@Autowired
	private CourseDAO courseDAO;
	
	@Autowired
	private BatchDAO batchDAO;
	
	@Autowired
	private TestSeriesDAO testSeriesDAO;
	
	@Autowired
	private TestDAO testDAO;
	
	@Autowired
	private WorkDAO workDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private EnrollDAO enrollDAO;
	
	@Autowired
	private FacultyDAO facultyDAO;
	
	@Autowired
	private FeedbackDAO feedbackDAO;
	
	@Autowired
	private TransactionDAO transactionDAO;
	
	Instamojo api;
	
	private static final Logger LOGGER = Logger.getLogger(StudentController.class.getName());
	
	@RequestMapping("/student/")
	public ModelAndView student(Principal principal) {	
		
		String username=principal.getName();
		Student student=studentDAO.findByUsername(username);
		
		ModelAndView model=new ModelAndView("student");
		model.addObject("student",student);

		return model;
		
	}
	
	@RequestMapping({"/student/details/","/admin/student/{username}/","/staff/student/{username}/","/faculty/student/{username}/"})
	public ModelAndView studentDetails(Authentication authentication,@PathVariable(value = "username", required = false) String username) {	
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		if(username == null) username=((UserDetails) authentication.getPrincipal()).getUsername();
		Student student=studentDAO.findByUsername(username);
		List<Work> allWork= workDAO.findByStudentID(student.getStudentID());
        
		ModelAndView model=new ModelAndView("student_details");
		model.addObject("role",role);
		
		model.addObject("student",student);
		model.addObject("allWork", allWork);
		
		return model;
		
	}
	
	@GetMapping({"/student/update/","/admin/student/update/{username}/"})
	public ModelAndView updateStudent(Authentication authentication,@PathVariable(value = "username", required = false) String username) {	
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		if(username == null) username=((UserDetails) authentication.getPrincipal()).getUsername();
		Student student=studentDAO.findByUsername(username);	
		
		if(student==null) {
			ModelAndView model=new ModelAndView("message");
			model.addObject("message","incorrect username");
			return model;
		}
		
		ModelAndView model=new ModelAndView("register_student");
		model.addObject("student",student);
		model.addObject("role",role);
		return model;
		
	}
	
	@PostMapping({"/student/update/","/admin/student/update/{username}/"})
	public String submitForm(Model model,Authentication authentication,@Valid @ModelAttribute("student") Student student, BindingResult bindingResult, @PathVariable(value = "username", required = false) String username) {
	 	
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();

		
		
	    if (bindingResult.hasErrors()) {
	    	
	    	model.addAttribute("role",role);
	        return "register_student";
	    }
	
	    if(username == null) {
	    	username=((UserDetails) authentication.getPrincipal()).getUsername();
	    	student.getUser().setUsername(username);
	    	userDAO.update(student.getUser());
	    	studentDAO.update(student);
	    	return "redirect:/student/details/";
	    }else {
	    	student.getUser().setUsername(username);
	    	userDAO.update(student.getUser());
	    	studentDAO.update(student);
	    	return "redirect:/admin/student/"+username+"/";
	    }
	    	   		
	}
	
	@RequestMapping(value = {"/admin/allexam/","/student/allexam/","/staff/allexam/","/faculty/allexam/"})
    public ModelAndView ShowAllExam(Authentication authentication) {
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
        ModelAndView model = new ModelAndView("all_exam");
        List<Exam> allExam = examDAO.allExam();
        model.addObject("allExam", allExam);
        model.addObject("role", role);
        return model;
        
    }
	
	
	@RequestMapping(value = {"/admin/allexam/{examID}/","/student/allexam/{examID}/","/staff/allexam/{examID}/","/faculty/allexam/{examID}/"})
    public ModelAndView ShowExam(@PathVariable(value = "examID") int examID, Authentication authentication) {		
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();		
        ModelAndView model = new ModelAndView("exam");
        Exam exam = examDAO.getByExamID(examID);
        List<Subject> subjectList = subjectDAO.getByExamID(examID);
		exam.setSubjects(subjectList);
		List<Course> courseList = courseDAO.getByExamID(examID);
		exam.setCourses(courseList);

        model.addObject("exam", exam);
        model.addObject("role", role);
        return model;
        
    }
	
	
	@RequestMapping(value = {"/admin/allcourse/{courseID}/","/student/allcourse/{courseID}/","/staff/allcourse/{courseID}/","/faculty/allcourse/{courseID}/"})
    public ModelAndView ShowCourse(@PathVariable(value = "courseID") int courseID, Authentication authentication) {		
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();		
        ModelAndView model = new ModelAndView("course");
        
        Boolean isBatchOpen = true;
        
        Course course = courseDAO.getByCourseID(courseID,isBatchOpen);
        List<Batch>  batchList= batchDAO.getOpenByCourseID(courseID);		
        List<TestSeries> testSeriesList = testSeriesDAO.getByCourseID(courseID);
        course.setBatches(batchList);
        course.setTestSeries(testSeriesList);
        
        model.addObject("course", course);
        model.addObject("role", role);
        
        return model;
        
    }
	
	@RequestMapping(value = 
		{	"/student/allbatch/{courseID}/{session}/{batchID}/",
			"/admin/allbatch/{courseID}/{session}/{batchID}/",
			"/faculty/allbatch/{courseID}/{session}/{batchID}/",
			"/staff/allbatch/{courseID}/{session}/{batchID}/"
	})
    public ModelAndView ShowCourse(Authentication authentication, @PathVariable(value = "courseID") int courseID, @PathVariable(value = "session") int session, @PathVariable(value = "batchID") int batchID) {		
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();	
		
		ModelAndView model = new ModelAndView("batch");
		model.addObject("role", role);
        Batch batch = batchDAO.getByBatchID(batchID,session,courseID);
        model.addObject("batch", batch);
        List<Faculty> facultyList= facultyDAO.getFacultyForBatch(batchID,session,courseID);
		batch.setFaculties(facultyList);
        
        boolean isOpen = (batchDAO.isOpen(batchID,session,courseID))&&((session== (Calendar.getInstance()).get(Calendar.YEAR)));
        
        List<Student> allStudent = studentDAO.getByBatch(courseID,session,batchID);
        model.addObject("allStudent", allStudent);
        model.addObject("open", isOpen);
        
        return model;
        
    }
	
	
	@RequestMapping(value = "/student/enroll/{courseID}/{session}/{batchID}/paid")
	public ModelAndView success(@RequestParam("transaction_id")String transaction_id,@PathVariable(value = "courseID") int courseID, @PathVariable(value = "session") int session, @PathVariable(value = "batchID") int batchID, Principal principal)
	{	
		Batch batch = batchDAO.getByBatchID(batchID,session,courseID);
		String username= principal.getName();
		int studentID=studentDAO.findByUsername(username).getStudentID();
		boolean isOpen = batchDAO.isOpen(batchID,session,courseID);
		boolean isStudentEnrolled = enrollDAO.isEnrolledInCourse(studentID,session,courseID);
		
		
		ModelAndView model = new ModelAndView();
		if(batch==null) {
			model.setViewName("message");
	        model.addObject("message", "No such batch");
	        return model;
		}else if((!isOpen) || (session!= (Calendar.getInstance()).get(Calendar.YEAR))) {
			model.setViewName("message");
	        model.addObject("message", "Batch Not open currently");
	        return model;
		}else if(isStudentEnrolled) {
			model.setViewName("message");
	        model.addObject("message", "You are already enrolled in this course");
	        return model;
		}

		try {
			
		    PaymentOrder paymentOrder = api.getPaymentOrderByTransactionId(transaction_id);
		    
		    
		    Transaction transaction=transactionDAO.updateVerified(transaction_id);
		    Pair<Enroll,Transaction> sucessEnroll=enrollDAO.save(transaction,studentID,batchID,session,courseID);
	    	model.setViewName("enroll_success");
	    	model.addObject("sucessEnroll", sucessEnroll);
	    	
	    	return model;
		    

		} catch (HTTPException e) {
		    
		    
		    

		} catch (ConnectionException e) {
		    
		}
		return new ModelAndView("/error");
		
	}
	
	@RequestMapping(value = "/student/enroll/{courseID}/{session}/{batchID}/")
	public ModelAndView enrollStudent(@PathVariable(value = "courseID") int courseID, @PathVariable(value = "session") int session, @PathVariable(value = "batchID") int batchID, Principal principal) {
		
		Batch batch = batchDAO.getByBatchID(batchID,session,courseID);
		String username= principal.getName();
		Student student = studentDAO.findByUsername(username);
		int studentID= student.getStudentID();
		
		boolean isOpen = batchDAO.isOpen(batchID,session,courseID);
		boolean isStudentEnrolled = enrollDAO.isEnrolledInCourse(studentID,session,courseID);
		
		ModelAndView model = new ModelAndView();
		if(batch==null) {
			model.setViewName("message");
	        model.addObject("message", "No such batch");
	        return model;
		}else if((!isOpen) || (session!= (Calendar.getInstance()).get(Calendar.YEAR))) {
			model.setViewName("message");
	        model.addObject("message", "Batch Not open currently");
	        return model;
		}else if(isStudentEnrolled) {
			model.setViewName("message");
	        model.addObject("message", "You are already enrolled in this course");
	        return model;
		}
		
		
		float fees = batch.getFees();	
	    
		String clientId = "test_23S7qHPCPl85W3iriMorlc15Unt4a8Akg40";
	    String clientSecret = "test_R78aFel2JNDEcsOlir6WnU3AAs2sOIsAxc9cQOnh0D6KJMtufzNvpl58oXqfmBpHDZcivBNHeVeo1wzebpnETXrQnOPb69Ao9AC6k6ImoFQZlRe49QNBuuOJzro";
	    
	 	ApiContext context = ApiContext.create(clientId, clientSecret, ApiContext.Mode.TEST);
	    api = new InstamojoImpl(context);

		PaymentOrder order = new PaymentOrder();
		order.setName(student.getUser().getFirstName());
		order.setEmail(student.getUser().getEmailID());
		order.setPhone("9204040100");
		order.setCurrency("INR");
		order.setAmount((double) batch.getFees());
		order.setDescription("Enrollment");
		
		order.setRedirectUrl(HostName.getHost()+"student/enroll/"+courseID+"/"+session+"/"+batchID+"/paid");
		order.setWebhookUrl("http://www.someurl.com/");
		String token= UUID.randomUUID().toString();
		order.setTransactionId(token);
		
		

		try {
		    PaymentOrderResponse paymentOrderResponse = api.createPaymentOrder(order);
		    
		    String paymentOrderId = paymentOrderResponse.getPaymentOrder().getId();		    
			
        	
        	
        	Transaction transaction = new Transaction();
    	    transaction.setAmount(fees);  
    	    transaction.setVerified(false);
    	    transaction.setTransactionDate(new Date());
   	     	transaction.setTransactionTime(new Date());
	    	transaction.setMode("online");
	    	transaction.setToken(token);
	    	transactionDAO.save(transaction);
        	
		    return new ModelAndView("redirect:"+paymentOrderResponse.getPaymentOptions().getPaymentUrl());
		    				

		} catch (HTTPException e) {
		    
		    
		    

		} catch (ConnectionException e) {
		    
		}
		
		return new ModelAndView("/error");
	}
	
	
	@RequestMapping(value = "/student/enrolled/course/")
    public ModelAndView allEnrolledCourse(Principal principal) {		
		
		String username= principal.getName();
		int studentID=studentDAO.findByUsername(username).getStudentID();
		
        ModelAndView model = new ModelAndView("enrolled_batches");
        List<Batch> enrolledBatch = batchDAO.getEnrolledBatches(studentID);
        model.addObject("enrolledBatch", enrolledBatch);
        
        return model;
        
    }
	
	@RequestMapping(value = "/student/enrolled/course/{courseID}/{session}/{batchID}/")
    public ModelAndView enrolledBatch(@PathVariable(value = "courseID") int courseID, @PathVariable(value = "session") int session, @PathVariable(value = "batchID") int batchID,Principal principal) {		
		
		String username= principal.getName();
		int studentID=studentDAO.findByUsername(username).getStudentID();
		
		int enrollmentNumber = enrollDAO.isEnrolledInBatch(studentID,session,courseID,batchID);
		
		if(enrollmentNumber==0) {
			ModelAndView model = new ModelAndView("message");
			model.addObject("message","You are not enrolled in this course/batch");
			return model;
		}
		
		Batch batch=batchDAO.getByBatchID(batchID,session,courseID);
		List<TestSeries> allTestSeries = testSeriesDAO.getByCourseID(courseID);
		
		List<Faculty> facultyList= facultyDAO.getFacultyForBatch(batchID,session,courseID);
		batch.setFaculties(facultyList);
		
        ModelAndView model = new ModelAndView("student_batch");
        model.addObject("batch", batch);
        model.addObject("enrollmentNumber", enrollmentNumber);
        model.addObject("allTestSeries", allTestSeries);
        
        return model;
        
    }
	
	@GetMapping(value = "/student/feedback/{facultyID}/")
    public ModelAndView feedbackForm(@PathVariable(value = "facultyID") int facultyID,Principal principal) {		
		
		String username= principal.getName();
		int studentID=studentDAO.findByUsername(username).getStudentID();
		boolean hasTaught = facultyDAO.hasTaughtStudent(facultyID,studentID);
		
		
		if(!hasTaught) {
			ModelAndView model = new ModelAndView("message");
			model.addObject("message","This faculty has not taught you any course");
			return model;
		}
		
		Faculty faculty = facultyDAO.findByFacultyID(facultyID);
		Feedback feedback=new Feedback();
		feedback.setFaculty(faculty);
		
        ModelAndView model = new ModelAndView("add_feedback");
        model.addObject("feedback", feedback);
        
        
        return model;
        
    }
	
	@PostMapping(value = "/student/feedback/{facultyID}/")
    public ModelAndView feedback(@Valid @ModelAttribute("feedback") Feedback feedback, BindingResult bindingResult,@PathVariable(value = "facultyID") int facultyID,Principal principal) {		
		
		String username= principal.getName();
		Student student = studentDAO.findByUsername(username);
		int studentID= student.getStudentID();
		
		boolean hasTaught = facultyDAO.hasTaughtStudent(facultyID,studentID);
		
		if(!hasTaught) {
			ModelAndView model = new ModelAndView("message");
			model.addObject("message","This faculty has not taught you any course");
			return model;
		}
		
		if(bindingResult.hasErrors()) {
			
	        ModelAndView model = new ModelAndView("add_feedback");
	        return model;
		}
		 
		Faculty faculty = facultyDAO.findByFacultyID(facultyID);
		feedback.setFaculty(faculty);
		feedback.setStudent(student);
		feedbackDAO.save(feedback);
		
        ModelAndView model = new ModelAndView("message");
        model.addObject("message", "feedback sent");
        
        return model;
        
    }
	
	@RequestMapping(value = {"/student/alltestseries/{testSeriesID}/","/staff/alltestseries/{testSeriesID}/","/faculty/alltestseries/{testSeriesID}/","/admin/alltestseries/{testSeriesID}/"})
    public ModelAndView allEnrolledTestSeries(@PathVariable(value = "testSeriesID") int testSeriesID,Principal principal) {		
		
		TestSeries testSeries=testSeriesDAO.getByTestSeriesID(testSeriesID);
		List<Test> testList = testDAO.getByTestSeriesID(testSeriesID);
		testSeries.setTestList(testList);
        ModelAndView model = new ModelAndView("testseries");
        model.addObject("testSeries", testSeries);
        return model;
        
    }
	
	@RequestMapping(value = {"/staff/alltestseries/{testSeriesID}/{testNumber}/","/student/alltestseries/{testSeriesID}/{testNumber}/","/faculty/alltestseries/{testSeriesID}/{testNumber}/","/admin/alltestseries/{testSeriesID}/{testNumber}/"})
    public ModelAndView showTestAttempt(@PathVariable(value = "testSeriesID") int testSeriesID,@PathVariable(value = "testNumber") int testNumber) {	
		
		Test test= testDAO.getByTestNumber(testNumber,testSeriesID);
		
        if(test == null) {
        	ModelAndView model = new ModelAndView("message");
        	model.addObject("message","No such test");
        	return model;
        }
        
		List<AttemptTest> attemptTest= testDAO.getStudentTakingTest(testNumber,testSeriesID);
		
	    ModelAndView model = new ModelAndView("test");
	    
	    
	    model.addObject("attemptTest", attemptTest);
	    model.addObject("test", test);
	    return model;
    }
	
	@GetMapping(value = {"/student/report/","/admin/student/report/{username}/","/staff/student/report/{username}/","/faculty/student/report/{username}/"})
	public ModelAndView getReport(Authentication authentication, @PathVariable(value = "username", required=false) String username) {	
		
		if(username == null) username=((UserDetails) authentication.getPrincipal()).getUsername();
		Student student=studentDAO.findByUsername(username);		
		List<AttemptTest> attemptTest= testDAO.getAttemptsForStudent(student.getStudentID());
		
	    ModelAndView model = new ModelAndView("report");
	    model.addObject("attemptTest", attemptTest);
	    model.addObject("student",student);
	    return model;
    }
	
	
	
	
	@GetMapping(value = {"/student/add/work/","/admin/student/add/work/{username}/"})
    public ModelAndView addWorkForm(Authentication authentication, @PathVariable(value = "username", required=false) String username) {
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		if(username == null) username=((UserDetails) authentication.getPrincipal()).getUsername();
		Student student=studentDAO.findByUsername(username);	
		
		if(student==null) {
			ModelAndView model=new ModelAndView("message");
			model.addObject("message","incorrect username");
			return model;
		}
		
        ModelAndView model = new ModelAndView("add_work");
      
        Work work = new Work();
        work.setStudent(student);
        List<Work> allWork= workDAO.findByStudentID(student.getStudentID());

        model.addObject("allWork", allWork);
        model.addObject("work", work);
        model.addObject("role",role);
        
        return model;
    }
	
	@PostMapping(value ={"/student/add/work/","/admin/student/add/work/{username}/"})
    public ModelAndView addTestAttempt(@PathVariable(value = "username", required=false) String username, Authentication authentication,@Valid @ModelAttribute("work") Work work,BindingResult bindingResult){
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		if(username == null) username=((UserDetails) authentication.getPrincipal()).getUsername();
		Student student=studentDAO.findByUsername(username);	
		
		if(student==null) {
			ModelAndView model=new ModelAndView("message");
			model.addObject("message","incorrect username");
			return model;
		};
		
		work.setStudent(student);
		
		if(bindingResult.hasErrors()) {
			
			ModelAndView model = new ModelAndView("add_work");
			List<Work> allWork= workDAO.findByStudentID(student.getStudentID());
	        model.addObject("allWork", allWork);
	        model.addObject("role",role);
	        return model;       
			
		}
		
		work.setWorkID(workDAO.getNextWorkID(student.getStudentID()));
		
		workDAO.save(work);	

		if(role.equals("student")) {
		return new ModelAndView ("redirect:/student/add/work/");
		}else {
		return new ModelAndView("redirect:/admin/student/add/work/"+username+"/");
		}
        
    }
	
	@GetMapping(value = {"/student/add/work/{workID}/","/admin/student/add/work/{username}/{workID}/"})
    public ModelAndView deleteWork(Authentication authentication,@PathVariable(value = "workID") int workID, @PathVariable(value = "username",required=false) String username){
    	
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		if(username == null) username=((UserDetails) authentication.getPrincipal()).getUsername();
		Student student=studentDAO.findByUsername(username);	
		
		if(student==null) {
			ModelAndView model=new ModelAndView("message");
			model.addObject("message","incorrect username");
			return model;
		};
		
		workDAO.delete(workID,student.getStudentID());
		
		if(role.equals("student")) {
		return new ModelAndView ("redirect:/student/add/work/");
		}else {
		return new ModelAndView("redirect:/admin/student/add/work/"+username+"/");
		}
        
    }

}
