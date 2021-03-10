package sb.mdr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sb.mdr.model.Dosen;
import sb.mdr.model.MataKuliah;
import sb.mdr.model.Semester;
import sb.mdr.service.impl.DosenService;
import sb.mdr.service.impl.MataKuliahService;
import sb.mdr.service.impl.SemesterService;

@RestController
@RequestMapping(value = "sb-mdr/test/v1/semester")
public class SemesterController {

	@Autowired
	private SemesterService semesterService;

	@RequestMapping(value = "/insert/semester", method = RequestMethod.POST)
	public String insertDataSemester(@RequestBody Semester semester) {
		String flagInsert = semesterService.insertSemester(semester);
		return flagInsert;
	}

	@RequestMapping(value = "/all/semester/{limit}", method = RequestMethod.GET)
	public List<Semester> getAllSemester(@PathVariable("limit") int limit) {
		List<Semester> listSemester = semesterService.getAllSemester(limit);
		return listSemester;
	}
	
	@RequestMapping(value = "/semester/{kodeSemester}", method = RequestMethod.GET)
	public Semester getSemester(@PathVariable("kodeSemester") String kodeSemester) {
		Semester semester = semesterService.getSemester(kodeSemester);
		return semester;
	}
	
	@RequestMapping(value = "/update/semester/{kodeSemester}", method = RequestMethod.PUT)
	public String updateDataSemester(@RequestBody Semester semester, @PathVariable("kodeSemester") String kodeSemester) {
		String flagUpdate = semesterService.updateSemester(semester,kodeSemester);
		return flagUpdate;
	}
	
	@RequestMapping(value = "/delete/semester/{kodeSemester}", method = RequestMethod.DELETE)
	public String deleteDataSemester(@PathVariable("kodeSemester") String kodeSemester) {
		String flagUpdate = semesterService.deleteSemester(kodeSemester);
		return flagUpdate;
	}

}
