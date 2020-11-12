package com.harshit.dbms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import com.harshit.dbms.dao.EnrollDAO;
import com.harshit.dbms.dao.FacultyApplicantDAO;
import com.harshit.dbms.dao.FacultyDAO;
import com.harshit.dbms.dao.StaffDAO;
import com.harshit.dbms.dao.StudentDAO;
import com.harshit.dbms.dao.SubjectDAO;
import com.harshit.dbms.dao.TestDAO;
import com.harshit.dbms.dao.TestSeriesDAO;
import com.harshit.dbms.dao.TransactionDAO;
import com.harshit.dbms.dao.UserDAO;
import com.harshit.dbms.model.Alumnus;
import com.harshit.dbms.model.AttemptTest;
import com.harshit.dbms.model.Batch;
import com.harshit.dbms.model.ContactUsForm;
import com.harshit.dbms.model.Course;
import com.harshit.dbms.model.Enroll;
import com.harshit.dbms.model.Exam;
import com.harshit.dbms.model.Student;
import com.harshit.dbms.model.Subject;
import com.harshit.dbms.model.Faculty;
import com.harshit.dbms.model.FacultyApplicant;
import com.harshit.dbms.model.Staff;
import com.harshit.dbms.model.Student;
import com.harshit.dbms.model.TeachesBatch;
import com.harshit.dbms.model.Test;
import com.harshit.dbms.model.TestSeries;
import com.harshit.dbms.model.Transaction;
import com.harshit.dbms.service.SecurityService;

@Controller
@Transactional
public class StaffController {
	
	@Autowired
	SecurityService securityService;
	@Autowired
	StaffDAO staffDAO;
	@Autowired
	TestSeriesDAO testSeriesDAO;
	@Autowired
	TestDAO testDAO;
	@Autowired
	BatchDAO batchDAO;
	@Autowired
	CourseDAO courseDAO;
	@Autowired
	FacultyDAO facultyDAO;
	@Autowired
	StudentDAO studentDAO;
	@Autowired
	EnrollDAO enrollDAO;
	@Autowired
	ContactUsFormDAO contactUsFormDAO;
	@Autowired
	TransactionDAO transactionDAO;
	@Autowired
	FacultyApplicantDAO facultyApplicantDAO;
	@Autowired
	EmployeeDAO employeeDAO;
	@Autowired
	UserDAO userDAO;
	
	
	
	
	@RequestMapping("/staff/")
	public String staff() {		
		return "staff";
	}
	
	@RequestMapping({"/staff/details/","/admin/staff/{username}/"})
	public ModelAndView staffDetails(Authentication authentication,@PathVariable(value = "username", required = false) String username) {	
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		if(username == null) username=((UserDetails) authentication.getPrincipal()).getUsername();
		
		Staff staff=staffDAO.findByUsername(username);

		ModelAndView model=new ModelAndView("staff_details");
		model.addObject("role",role);
		
		model.addObject("staff",staff);
		
		return model;
		
	}

	@GetMapping({"/staff/update/","/admin/staff/update/{username}/"})
	public ModelAndView updateStaff(Authentication authentication,@PathVariable(value = "username", required = false) String username) {	
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		if(username == null) username=((UserDetails) authentication.getPrincipal()).getUsername();
		Staff staff=staffDAO.findByUsername(username);
		
		if(staff==null) {
			ModelAndView model=new ModelAndView("message");
			model.addObject("message","incorrect username");
			return model;
		}
		
		ModelAndView model=new ModelAndView("register_staff");

		model.addObject("staff",staff);
		model.addObject("role",role);
				
		return model;
		
	}
	
	@PostMapping({"/staff/update/","/admin/staff/update/{username}/"})
	public ModelAndView submitForm(Authentication authentication,@Valid @ModelAttribute("staff") Staff staff, BindingResult bindingResult, @PathVariable(value = "username", required = false) String username) {
	 	
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		ModelAndView model = new ModelAndView();

	    if (bindingResult.hasErrors()) {	    	
	    	model.setViewName( "register_staff");
	    	model.addObject("role",role);
	    	return model;	    	
	    }
	
	    if(username == null) {
	    	username=((UserDetails) authentication.getPrincipal()).getUsername();
	    	model.setViewName("redirect:/staff/details/");
	    }else {
	    	model.setViewName("redirect:/"+role+"/staff/"+username+"/");
	    }
	    
	    
	    staff.getEmployee().setEmployeeID(staffDAO.findByUsername(username).getEmployee().getEmployeeID());
	    staff.getEmployee().getUser().setUsername(username);
	    userDAO.update(staff.getEmployee().getUser());
	    employeeDAO.update(staff.getEmployee());
    	staffDAO.update(staff);
	    
    	return model;
		   		
	}
	
	
	@GetMapping(value = {"/staff/add/test/","/admin/add/test/"})
    public ModelAndView addTestForm() {
		
        ModelAndView model = new ModelAndView("add_test");
        Test test = new Test();
        model.addObject("test", test);
        List<TestSeries> allTestSeries = testSeriesDAO.allTestSeries();
        model.addObject("allTestSeries", allTestSeries);
        
        return model;
    }
	
	@PostMapping(value = {"/staff/add/test/","/admin/add/test/"})
    public ModelAndView addTest(Authentication authentication, @Valid @ModelAttribute("test") Test test, BindingResult bindingResult) {
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		
		if (bindingResult.hasErrors()) {
	    	
	    	ModelAndView model = new ModelAndView("add_test");
	    	List<TestSeries> allTestSeries = testSeriesDAO.allTestSeries();
	        model.addObject("allTestSeries", allTestSeries);
	        return model;
	    }	
		test.setTestNumber(testDAO.getNextTestNumber(test.getTestSeries().getTestSeriesID()));
		testDAO.save(test);
        return new ModelAndView ("redirect:/"+role+"/alltestseries/"+test.getTestSeries().getTestSeriesID()+"/");
    }
	
	@GetMapping(value = {"/staff/update/test/{testSeriesID}/{testNumber}/","/admin/update/test/{testSeriesID}/{testNumber}/"})
    public ModelAndView updateTestForm(@PathVariable(value="testNumber") int testNumber,@PathVariable(value="testSeriesID") int testSeriesID) {
        ModelAndView model = new ModelAndView("add_test");
        Test test = testDAO.getByTestNumber(testNumber,testSeriesID);
        model.addObject("test", test);
        List<TestSeries> allTestSeries = testSeriesDAO.allTestSeries();
        model.addObject("allTestSeries", allTestSeries);
        
        return model;
    }
	
	@PostMapping(value = {"/staff/update/test/{testSeriesID}/{testNumber}/","/admin/update/test/{testSeriesID}/{testNumber}/"})
    public ModelAndView updateTest(Authentication authentication, @PathVariable(value="testNumber") int testNumber,@PathVariable(value="testSeriesID") int testSeriesID,@Valid @ModelAttribute("test") Test test, BindingResult bindingResult) {
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		if (bindingResult.hasErrors()) {
	    	ModelAndView model = new ModelAndView("add_test");
	    	List<TestSeries> allTestSeries = testSeriesDAO.allTestSeries();
	        model.addObject("allTestSeries", allTestSeries);
	        return model;
	    }
		int newTestSeriesID= test.getTestSeries().getTestSeriesID();
		
		if(newTestSeriesID!=testSeriesID) {
			int newTestNumber=testDAO.getNextTestNumber(newTestSeriesID);
			test.setTestNumber(newTestNumber);
		}
		
		testDAO.update(test,testNumber,testSeriesID);
        return new ModelAndView("redirect:/"+role+"/alltestseries/"+newTestSeriesID+"/"+test.getTestNumber()+"/");
    }
	
	@GetMapping(value = {"/staff/delete/test/{testSeriesID}/{testNumber}/","/admin/delete/test/{testSeriesID}/{testNumber}/"})
    public ModelAndView deleteTest(Authentication authentication, @PathVariable(value="testNumber") int testNumber,@PathVariable(value="testSeriesID") int testSeriesID) {
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
        ModelAndView model = new ModelAndView("redirect:/"+role+"/alltestseries/"+testSeriesID+"/");
        testDAO.delete(testNumber,testSeriesID);
        return model;
    }
	
	
	@GetMapping(value = {"/staff/add/batch/","/admin/add/batch/"})
    public ModelAndView addBatchForm() {
		
        ModelAndView model = new ModelAndView("add_batch");
        Batch batch = new Batch();
        List<Course> allCourse= courseDAO.allCourse();
        model.addObject("allCourse", allCourse);
        model.addObject("batch", batch);
        return model;
    }
	
	@PostMapping(value = {"/staff/add/batch/","/admin/add/batch/"})
    public ModelAndView addBatch(Authentication authentication,@Valid @ModelAttribute("batch") Batch batch, BindingResult bindingResult) {
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();

		
		if (bindingResult.hasErrors()) {
	    	
	    	ModelAndView model = new ModelAndView("add_batch");
	    	List<Course> allCourse= courseDAO.allCourse();
	        model.addObject("allCourse", allCourse);
	        return model;
	    }	
		int courseID=batch.getCourse().getCourseID();
		int session=batch.getSession();
		int batchID=batchDAO.getNextBatchID(courseID,session);
		batch.setBatchID(batchID);
		batchDAO.save(batch);
        return new ModelAndView ("redirect:/"+role+"/add/batch/faculty/"+batchID+"/"+courseID+"/"+session+"/");
    }
	
	@GetMapping(value = {"/staff/update/batch/{batchID}/{courseID}/{session}/","/admin/update/batch/{batchID}/{courseID}/{session}/"})
    public ModelAndView updateBatchForm(@PathVariable(value = "batchID") int batchID,@PathVariable(value = "courseID") int courseID,@PathVariable(value = "session") int session) {
        
		
		ModelAndView model = new ModelAndView("add_batch");
        Batch batch = batchDAO.getByBatchID(batchID,session,courseID);
        List<Faculty> facultyList= facultyDAO.getFacultyForBatch(batchID,session,courseID);
		batch.setFaculties(facultyList);
        
        model.addObject("batch", batch);
        List<Course> allCourse = courseDAO.allCourse();
        model.addObject("allCourse", allCourse);
        
        return model;
        
    }
	
	@PostMapping(value = {"/staff/update/batch/{batchID}/{courseID}/{oldsession}/","/admin/update/batch/{batchID}/{courseID}/{oldsession}/"})
    public ModelAndView updateBatch(Authentication authentication, @PathVariable(value = "batchID") int batchID,@PathVariable(value = "courseID") int courseID,@PathVariable(value = "oldsession") int oldsession,@Valid @ModelAttribute("batch") Batch batch, BindingResult bindingResult) {
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		
		if (bindingResult.hasErrors()) {
	    	ModelAndView model = new ModelAndView("add_batch");
	    	List<Course> allCourse = courseDAO.allCourse();
	        model.addObject("allCourse", allCourse);
	        return model;
	    }
		
		int newCourseID= batch.getCourse().getCourseID();
		int newSession= batch.getSession();
		int newBatchID= batch.getBatchID();
		
		if(newCourseID!=courseID || newSession!=oldsession) {
			newBatchID=batchDAO.getNextBatchID(newCourseID,newSession);
			batch.setBatchID(newBatchID);
		}
		
		
		
		
		
		
		batchDAO.update(batch,batchID,courseID,oldsession);
        return new ModelAndView("redirect:/"+role+"/add/batch/faculty/"+newBatchID+"/"+newCourseID+"/"+newSession+"/");
    }
	
	@GetMapping(value = {"/staff/delete/batch/{batchID}/{courseID}/{session}/","/admin/delete/batch/{batchID}/{courseID}/{session}/"})
    public ModelAndView deleteBatch(Authentication authentication, @PathVariable(value = "batchID") int batchID,@PathVariable(value = "courseID") int courseID,@PathVariable(value = "session") int session) {
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
        ModelAndView model = new ModelAndView("redirect:/"+role+"/allbatch/");
        batchDAO.delete(batchID,courseID,session);
        return model;
        
    }
	
	@GetMapping(value = {"/staff/add/batch/faculty/{batchID}/{courseID}/{session}/","/admin/add/batch/faculty/{batchID}/{courseID}/{session}/"})
    public ModelAndView addBatchFacultyForm(Authentication authentication, @PathVariable(value = "batchID") int batchID,@PathVariable(value = "courseID") int courseID,@PathVariable(value = "session") int session) {
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		Batch batch = batchDAO.getByBatchID(batchID,session,courseID);
		
		if(batch==null) {
			return new ModelAndView("redirect/"+role+"/add/batch/");
		}
		
		List<Faculty> facultyList= facultyDAO.getFacultyForBatch(batchID,session,courseID);
		batch.setFaculties(facultyList);
		
        ModelAndView model = new ModelAndView("add_batch_faculty");
        TeachesBatch teachesBatch = new TeachesBatch();
        
        teachesBatch.setBatch(batch);
               
        model.addObject("teachesBatch", teachesBatch);
        
        List<Faculty> allFaculty = facultyDAO.allFaculty();
        List<Faculty> otherFaculty = new ArrayList<Faculty>();
        
        List<Integer> facultyTeachingBatchID  = facultyList.stream()
                .map(f ->  f.getFacultyID())
                .collect(Collectors.toList());
        
        
        for(Faculty f: allFaculty) {
        	if(!facultyTeachingBatchID.contains(f.getFacultyID())) {
        		otherFaculty.add(f);
        	}
        }
        
        model.addObject("otherFaculty",otherFaculty);
        model.addObject("facultyTeachingBatch",facultyList); 
        model.addObject("role",role); 
        
        return model;
    }
	
	@PostMapping(value = {"/staff/add/batch/faculty/{batchID}/{courseID}/{session}/","/admin/add/batch/faculty/{batchID}/{courseID}/{session}/"})
    public ModelAndView addBatchFaculty(Authentication authentication, @Valid @ModelAttribute("teachesBatch") TeachesBatch teachesBatch,BindingResult bindingResult,@PathVariable(value = "batchID") int batchID,@PathVariable(value = "courseID") int courseID,@PathVariable(value = "session") int session) {
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		batchDAO.saveTeachesBatch(batchID,courseID,session,teachesBatch.getFaculty().getFacultyID());
        return new ModelAndView ("redirect:/"+role+"/add/batch/faculty/"+batchID+"/"+courseID+"/"+session+"/");
        
    }
	
	@GetMapping(value = {"/staff/add/batch/faculty/{batchID}/{courseID}/{session}/{facultyID}/","/admin/add/batch/faculty/{batchID}/{courseID}/{session}/{facultyID}/"})
    public ModelAndView deleteBatchFaculty(Authentication authentication, @PathVariable(value = "facultyID") int facultyID, @PathVariable(value = "batchID") int batchID,@PathVariable(value = "courseID") int courseID, @PathVariable(value = "session") int session) {
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		batchDAO.deleteTeachesBatch(batchID,courseID,session,facultyID);
		return new ModelAndView ("redirect:/"+role+"/add/batch/faculty/"+batchID+"/"+courseID+"/"+session+"/");
		
	}
	
	
	@GetMapping(value = {"/staff/add/testattempt/{testSeriesID}/{testNumber}/","/admin/add/testattempt/{testSeriesID}/{testNumber}/"})
    public ModelAndView addTestAttemptForm(Authentication authentication, @PathVariable(value = "testSeriesID") int testSeriesID,@PathVariable(value = "testNumber") int testNumber) {
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();

        ModelAndView model = new ModelAndView("add_testattempt");
        
        Test test= testDAO.getByTestNumber(testNumber,testSeriesID);
        
        if(test == null) {
        	return new ModelAndView("redirect:/"+role+"/add/test/");
        }
        
        
        
        AttemptTest attemptTest = new AttemptTest();
        attemptTest.setTest(test);
        List<Student> allStudent= studentDAO.studentCanTakeTest(testNumber,testSeriesID);
        List<AttemptTest> attemptsAdded= testDAO.getStudentTakingTest(testNumber,testSeriesID);
        
        model.addObject("allStudent", allStudent);
        model.addObject("attemptTest", attemptTest);
        model.addObject("attemptsAdded", attemptsAdded);
        model.addObject("role",role);
        
        return model;
    }
	
	@PostMapping(value = {"/staff/add/testattempt/{testSeriesID}/{testNumber}/","/admin/add/testattempt/{testSeriesID}/{testNumber}/"})
    public ModelAndView addTestAttempt(Authentication authentication,@Valid @ModelAttribute("attemptTest") AttemptTest attemptTest,BindingResult bindingResult,@PathVariable(value = "testSeriesID") int testSeriesID,@PathVariable(value = "testNumber") int testNumber){
		
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		attemptTest.setTest(new Test(testNumber,new TestSeries(testSeriesID)));
		testDAO.saveTestAttempt(attemptTest);	
        return new ModelAndView ("redirect:/"+role+"/add/testattempt/"+testSeriesID+"/"+testNumber+"/");
        
    }
	
	@GetMapping(value = {"/staff/add/testattempt/{testSeriesID}/{testNumber}/{studentID}/","/admin/add/testattempt/{testSeriesID}/{testNumber}/{studentID}/"})
    public ModelAndView deleteTestAttempt(Authentication authentication,@PathVariable(value = "studentID") int studentID, @PathVariable(value = "testSeriesID") int testSeriesID,@PathVariable(value = "testNumber") int testNumber){
    	
		String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
		testDAO.deleteTestAttempt(testSeriesID,testNumber,studentID);	
        return new ModelAndView ("redirect:/"+role+"/add/testattempt/"+testSeriesID+"/"+testNumber+"/");
        
    }
	
	
	@RequestMapping(value = {"/staff/allfacultyapplicant/", "/admin/allfacultyapplicant/"})
    public ModelAndView ShowAllFacultyApplicant(Authentication authentication) {		
        ModelAndView model = new ModelAndView("all_applicant");
        List<FacultyApplicant> allApplicant= facultyApplicantDAO.allFacultyApplicant();
        
        String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
        model.addObject("role",role);
        model.addObject("allApplicant", allApplicant);
        return model;
    }
	
	@RequestMapping(value = { "/staff/allfacultyapplicant/delete/{applicationID}/","/admin/allfacultyapplicant/delete/{applicationID}/"})
    public ModelAndView deleteFacultyApplicantForm(Authentication authentication,@PathVariable(value="applicationID") int applicationID) {		
    	
		facultyApplicantDAO.delete(applicationID);
        String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
        ModelAndView model = new ModelAndView("redirect:/"+role+"/allfacultyapplicant/");
        return model;
        
	}
	
	@RequestMapping(value = { "/staff/allfacultyapplicant/review/{applicationID}/","/admin/allfacultyapplicant/review/{applicationID}/"})
    public ModelAndView processFacultyApplicantForm(Authentication authentication,@PathVariable(value="applicationID") int applicationID) {		
    	
		facultyApplicantDAO.reviewed(applicationID);
        String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
        ModelAndView model = new ModelAndView("redirect:/"+role+"/allfacultyapplicant/");
        return model;
        
	}
	
	@RequestMapping(value = { "/staff/allcontactusform/","/admin/allcontactusform/"})
    public ModelAndView ShowAllContactUsForm(Authentication authentication) {		
    	
        ModelAndView model = new ModelAndView("all_contactusform");
        List<ContactUsForm> allContactUsForm = contactUsFormDAO.allContactUsForm();
        String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
        model.addObject("role",role);
        model.addObject("allContactUsForm", allContactUsForm);
        return model;
        
    }
	
	 @GetMapping(value = { "/staff/allcontactusform/update/{queryID}/","/admin/allcontactusform/update/{queryID}/"})
	 public ModelAndView replyContactUsForm(@PathVariable(value="queryID") int queryID) {			
	    ModelAndView model = new ModelAndView("add_reply");
	    ContactUsForm contactUsForm = contactUsFormDAO.getByQueryID(queryID);
	    model.addObject("contactUsForm", contactUsForm);
	    return model;
	 }
	 
	 @PostMapping(value = { "/staff/allcontactusform/update/{queryID}/","/admin/allcontactusform/update/{queryID}/"})
	 public ModelAndView replyContactUs(@PathVariable(value="queryID") int queryID,Authentication authentication,@Valid @ModelAttribute("contactUsForm") ContactUsForm contactUsForm, BindingResult bindingResult) {
		
		 if (bindingResult.hasErrors()) {
	     	
	     	ModelAndView model = new ModelAndView("add_reply");
	        return model;
		 }	
		 
	     String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();

		 ModelAndView model = new ModelAndView("redirect:/"+role+"/allcontactusform/");
		 contactUsFormDAO.updateReply(queryID,contactUsForm.getQuery(),contactUsForm.getReplyGiven());		
		
		 return model;
	 }
	 
	 
	 
	
	@RequestMapping(value = { "/staff/allcontactusform/show/{queryID}/","/admin/allcontactusform/show/{queryID}/"})
    public ModelAndView showContactUsForm(Authentication authentication,@PathVariable(value="queryID") int queryID) {		
    	
		contactUsFormDAO.show(queryID);
        String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
        ModelAndView model = new ModelAndView("redirect:/"+role+"/allcontactusform/");
        return model;
        
	}
	
	@RequestMapping(value = { "/staff/allcontactusform/hide/{queryID}/","/admin/allcontactusform/hide/{queryID}/"})
    public ModelAndView hideContactUsForm(Authentication authentication,@PathVariable(value="queryID") int queryID) {		
    	
		contactUsFormDAO.hide(queryID);
        String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
        ModelAndView model = new ModelAndView("redirect:/"+role+"/allcontactusform/");
        return model;
        
	}
	
	@RequestMapping(value = { "/staff/allcontactusform/delete/{queryID}/","/admin/allcontactusform/delete/{queryID}/"})
    public ModelAndView deleteContactUsForm(Authentication authentication,@PathVariable(value="queryID") int queryID) {		
    	
		contactUsFormDAO.delete(queryID);
        String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
        ModelAndView model = new ModelAndView("redirect:/"+role+"/allcontactusform/");
        return model;
        
	}
	
	@RequestMapping(value = { "/staff/alltransaction/","/admin/alltransaction/"})
    public ModelAndView allTransaction(Authentication authentication) {		
    	
		List<Pair<Transaction,Enroll>> allTransactionEnroll=transactionDAO.getAllTransaction();
        ModelAndView model = new ModelAndView("all_transaction");
        model.addObject("allTransactionEnroll",allTransactionEnroll);
        
        return model;
        
	}
	
	
	@RequestMapping(value = { "/staff/alltransaction/delete/{transactionID}/","/admin/alltransaction/delete/{transactionID}/"})
    public ModelAndView deleteTransaction(Authentication authentication,@PathVariable(value="transactionID") int transactionID) {		
    	
		transactionDAO.delete(transactionID);
        String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
        ModelAndView model = new ModelAndView("redirect:/"+role+"/alltransaction/");
        return model;
        
	}
	
	 @GetMapping(value = { "/staff/allbatch/{courseID}/{session}/{batchID}/enroll/","/admin/allbatch/{courseID}/{session}/{batchID}/enroll/"})
	 public ModelAndView enrollStudentForm(@PathVariable(value = "courseID") int courseID, @PathVariable(value = "session") int session, @PathVariable(value = "batchID") int batchID) {		
		 
	    ModelAndView model = new ModelAndView("staff_enroll");
	    Enroll enroll = new Enroll();	    
	    List<Student> allStudent = studentDAO.allStudentNotInBatch(courseID,session,batchID);
	    Batch batch = batchDAO.getByBatchID(batchID,session,courseID);
	    
	    model.addObject("enroll", enroll);
	    model.addObject("allStudent", allStudent);
	    model.addObject("batch", batch);
	    
	    return model;
	 }
	 
	 @PostMapping(value = { "/staff/allbatch/{courseID}/{session}/{batchID}/enroll/","/admin/allbatch/{courseID}/{session}/{batchID}/enroll/"})
	 public ModelAndView enrollStudent(Authentication authentication,@Valid @ModelAttribute("enroll") Enroll enroll, BindingResult bindingResult,@PathVariable(value = "courseID") int courseID, @PathVariable(value = "session") int session, @PathVariable(value = "batchID") int batchID) {		
		 
	     String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
	     Batch batch = batchDAO.getByBatchID(batchID,session,courseID);
	     
	     Transaction transaction = new Transaction();
	     transaction.setVerified(true);
	     transaction.setTransactionDate(new Date());
	     transaction.setTransactionTime(new Date());
	     transaction.setMode("offline");
	     transaction.setAmount(batch.getFees());	
	     transaction=transactionDAO.save(transaction);
	     
	     Pair<Enroll,Transaction> sucessEnroll=enrollDAO.save(transaction,enroll.getStudentID(),batchID,session,courseID);
	     	     
		 ModelAndView model = new ModelAndView("redirect:/"+role+"/allbatch/"+courseID+"/"+session+"/"+batchID+"/");
		
		 return model;
	 }

}
