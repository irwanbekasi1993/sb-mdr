package sbmdr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sbmdr.model.Dosen;
import sbmdr.model.MataKuliah;
import sbmdr.model.Semester;
import sbmdr.service.impl.DosenService;
import sbmdr.service.impl.MataKuliahService;
import sbmdr.service.impl.SemesterService;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping(value = "/sbmdr/test/v1/semester")
public class SemesterController {

	@Autowired
	private SemesterService semesterService;

	private String result;

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertDataSemester(@RequestBody Semester semester) {
		result = semesterService.insertSemester(semester);
		return result;
	}

	@RequestMapping(value = "/all/{limit}", method = RequestMethod.GET)
	public List<Semester> getAllSemester(@PathVariable("limit") int limit) {
		List<Semester> listSemester = semesterService.getAllSemester(limit);
		return listSemester;
	}
	
	
	@RequestMapping(value = "/{kodeSemester}", method = RequestMethod.GET)
	public Semester getSemester( @PathVariable("kodeSemester") String kodeSemester) {
		Semester getSemester = semesterService.getSemester( kodeSemester);
		return getSemester;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/update/{kodeSemester}", method = RequestMethod.PUT)
	public String updateDataSemester(@RequestBody Semester semester, @PathVariable("kodeSemester") String kodeSemester) {
		result = semesterService.updateSemester(semester,kodeSemester);
		return result;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/delete/{kodeSemester}", method = RequestMethod.DELETE)
	public String deleteDataSemester(@PathVariable("kodeSemester") String kodeSemester) {
		result = semesterService.deleteSemester(kodeSemester);
		return result;
	}

}
