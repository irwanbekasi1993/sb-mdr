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
import sbmdr.model.Soal;
import sbmdr.service.impl.DosenService;
import sbmdr.service.impl.MataKuliahService;
import sbmdr.service.impl.SemesterService;
import sbmdr.service.impl.SoalService;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping(value = "/sbmdr/test/v1/soal")
public class SoalController {

	@Autowired
	private SoalService soalService;

	@PreAuthorize("hasRole('DOSEN')")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertDataSoal(@RequestBody Soal soal) {
		String flagInsert = soalService.insertSoal(soal);
		return flagInsert;
	}

	@RequestMapping(value = "/all/{limit}", method = RequestMethod.GET)
	public List<Soal> getAllSoal(@PathVariable("limit") int limit) {
		List<Soal> listSoal = soalService.getAllSoal(limit);
		return listSoal;
	}
	
	@RequestMapping(value = "/{kodeSoal}", method = RequestMethod.GET)
	public Soal getSoal(@PathVariable("kodeSoal") String kodeSoal) {
		Soal soal = soalService.getSoal(kodeSoal);
		return soal;
	}
	
	@PreAuthorize("hasRole('DOSEN')")
	@RequestMapping(value = "/update/{kodeSoal}", method = RequestMethod.PUT)
	public String updateDataSoal(@RequestBody Soal soal, @PathVariable("kodeSoal") String kodeSoal) {
		String flagUpdate = soalService.updateSoal(soal,kodeSoal);
		return flagUpdate;
	}
	
	@PreAuthorize("hasRole('DOSEN')")
	@RequestMapping(value = "/delete/{kodeSoal}", method = RequestMethod.DELETE)
	public String deleteDataSoal(@PathVariable("kodeSoal") String kodeSoal) {
		String flagUpdate = soalService.deleteSoal(kodeSoal);
		return flagUpdate;
	}

}
