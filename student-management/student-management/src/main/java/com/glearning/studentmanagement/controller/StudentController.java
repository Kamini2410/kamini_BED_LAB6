package com.glearning.studentmanagement.controller;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glearning.studentmanagement.entity.Student;
import com.glearning.studentmanagement.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class StudentController {
private StudentService studentService;
	
	public StudentController(StudentService theStudentService) {
		studentService = theStudentService;
	}
	
	// add mapping for "/list"

	@GetMapping("/list")
	public String listStudents(Model theModel) {
		
		// get tickets from database
		List<Student> theStudents = studentService.getAllStudents();
		
		// add to the spring model
		theModel.addAttribute("students", theStudents);
		
		return "students";
	}
	

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Student theStudent = new Student();
		
		theModel.addAttribute("students", theStudent);
		
		return "create-student";
	}

	@PostMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("id") long theId,
									Model theModel) {
		
		// get the ticket from the service
		Student theStudent = studentService.getStudentById(theId);
		
		// set ticket as a model attribute to pre-populate the form
		theModel.addAttribute("students", theStudent);
		
		// send over to our form
		return "edit-student";			
	}
	
	
	@PostMapping("/save")
	public String saveTicket(@ModelAttribute("students") Student theStudent) {
		
		// save the ticket
		studentService.saveStudent(theStudent);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/list";
	}
	
	
	@PostMapping("/delete")
	public String delete(@RequestParam("id") long theId) {
		
		// delete the ticket
		studentService.deleteStudentById(theId);
		
		// redirect to /list
		return "redirect:/list";
		
	}
}


/*
@Controller
public class StudentController {
	@Autowired
	private StudentService studentService;

	public StudentController(StudentService theStudentService) {
		studentService = theStudentService;
	}
	
	
	// handler method to handle list students and return mode and view
	@GetMapping("/students")
	public String listStudents(Model model) {
		List<Student> theStudents = studentService.getAllStudents();
		
		model.addAttribute("students", theStudents);
		
		return "students";
			
	}
	
	@GetMapping("/students/new")
	public String createStudentForm(Model model) {
		
		// create student object to hold student form data
		Student student = new Student();
		model.addAttribute("student", student);
		return "create_student";
		
	}
	
	@PostMapping("/students")
	public String saveStudent(@ModelAttribute("student") Student student) {
		studentService.saveStudent(student);
		return "redirect:/students";
	}
	
	@GetMapping("/students/edit/{id}")
	public String editStudentForm(@PathVariable Long id, Model model) {
		model.addAttribute("student", studentService.getStudentById(id));
		return "edit_student";
	}

	@PostMapping("/students/{id}")
	public String updateStudent(@PathVariable Long id,
			@ModelAttribute("student") Student student,
			Model model) {
		
		// get student from database by id
		Student existingStudent = studentService.getStudentById(id);
		existingStudent.setId(id);
		existingStudent.setFirstName(student.getFirstName());
		existingStudent.setLastName(student.getLastName());
		existingStudent.setCourse(student.getCourse());
		existingStudent.setCountry(student.getCountry());
		
		
		// save updated student object
		studentService.updateStudent(existingStudent);
		return "redirect:/students";		
	}
	
	// handler method to handle delete student request
	
	@GetMapping("/students/{id}")
	public String deleteStudent(@PathVariable Long id) {
		studentService.deleteStudentById(id);
		return "redirect:/students";
	}	

}*/
