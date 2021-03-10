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
import sb.mdr.model.Soal;
import sb.mdr.service.impl.DosenService;
import sb.mdr.service.impl.MataKuliahService;
import sb.mdr.service.impl.SemesterService;
import sb.mdr.service.impl.SoalService;

@RestController
@RequestMapping(value = "sb-mdr/test/v1/soal")
public class SoalController {

	@Autowired
	private SoalService soalService;

	@RequestMapping(value = "/insert/soal", method = RequestMethod.POST)
	public String insertDataSoal(@RequestBody Soal soal) {
		String flagInsert = soalService.insertSoal(soal);
		return flagInsert;
	}

	@RequestMapping(value = "/all/soal/{limit}", method = RequestMethod.GET)
	public List<Soal> getAllSoal(@PathVariable("limit") int limit) {
		List<Soal> listSoal = soalService.getAllSoal(limit);
		return listSoal;
	}
	
	@RequestMapping(value = "/soal/{kodeSoal}", method = RequestMethod.GET)
	public Soal getSoal(@PathVariable("kodeSoal") String kodeSoal) {
		Soal soal = soalService.getSoal(kodeSoal);
		return soal;
	}
	
	@RequestMapping(value = "/update/soal/{kodeSoal}", method = RequestMethod.PUT)
	public String updateDataSoal(@RequestBody Soal soal, @PathVariable("kodeSoal") String kodeSoal) {
		String flagUpdate = soalService.updateSoal(soal,kodeSoal);
		return flagUpdate;
	}
	
	@RequestMapping(value = "/delete/soal/{kodeSoal}", method = RequestMethod.DELETE)
	public String deleteDataSoal(@PathVariable("kodeSoal") String kodeSoal) {
		String flagUpdate = soalService.deleteSoal(kodeSoal);
		return flagUpdate;
	}

}
