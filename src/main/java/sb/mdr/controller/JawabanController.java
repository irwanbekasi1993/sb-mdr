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
import sb.mdr.model.Jawaban;
import sb.mdr.model.MataKuliah;
import sb.mdr.model.Semester;
import sb.mdr.model.Soal;
import sb.mdr.service.impl.DosenService;
import sb.mdr.service.impl.JawabanService;
import sb.mdr.service.impl.MataKuliahService;
import sb.mdr.service.impl.SemesterService;
import sb.mdr.service.impl.SoalService;

@RestController
@RequestMapping(value = "sb-mdr/test/v1/jawaban")
public class JawabanController {

	@Autowired
	private JawabanService jawabanService;

	@RequestMapping(value = "/insert/jawaban", method = RequestMethod.POST)
	public String insertDataJawaban(@RequestBody Jawaban jawaban) {
		String flagInsert = jawabanService.insertJawaban(jawaban);
		return flagInsert;
	}

	@RequestMapping(value = "/all/jawaban/{limit}", method = RequestMethod.GET)
	public List<Jawaban> getAllSoal(@PathVariable("limit") int limit) {
		List<Jawaban> listJawaban = jawabanService.getAllJawaban(limit);
		return listJawaban;
	}
	
	@RequestMapping(value = "/jawaban/{kodeJawaban}", method = RequestMethod.GET)
	public Jawaban getJawaban(@PathVariable("kodeJawaban") String kodeJawaban) {
		Jawaban jawaban = jawabanService.getJawaban(kodeJawaban);
		return jawaban;
	}
	
	@RequestMapping(value = "/update/jawaban/{kodeJawaban}", method = RequestMethod.PUT)
	public String updateDataJawaban(@RequestBody Jawaban jawaban, @PathVariable("kodeJawaban") String kodeJawaban) {
		String flagUpdate = jawabanService.updateJawaban(jawaban,kodeJawaban);
		return flagUpdate;
	}
	
	@RequestMapping(value = "/delete/jawaban/{kodeJawaban}", method = RequestMethod.DELETE)
	public String deleteDataJawaban(@PathVariable("kodejawaban") String kodeJawaban) {
		String flagUpdate = jawabanService.deleteJawaban(kodeJawaban);
		return flagUpdate;
	}

}
