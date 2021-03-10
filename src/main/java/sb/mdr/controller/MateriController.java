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
import sb.mdr.model.Materi;
import sb.mdr.model.Semester;
import sb.mdr.model.Soal;
import sb.mdr.service.impl.DosenService;
import sb.mdr.service.impl.MataKuliahService;
import sb.mdr.service.impl.MateriService;
import sb.mdr.service.impl.SemesterService;
import sb.mdr.service.impl.SoalService;

@RestController
@RequestMapping(value = "sb-mdr/test/v1/materi")
public class MateriController {

	@Autowired
	private MateriService materiService;

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertDataMateri(@RequestBody Materi materi) {
		String flagInsert = materiService.insertMateri(materi);
		return flagInsert;
	}

	@RequestMapping(value = "/all/{limit}", method = RequestMethod.GET)
	public List<Materi> getAllMateri(@PathVariable("limit") int limit) {
		List<Materi> listMateri = materiService.getAllMateri(limit);
		return listMateri;
	}
	
	@RequestMapping(value = "/{kodeMateri}", method = RequestMethod.GET)
	public Materi getMateri(@PathVariable("kodeMateri") String kodeMateri) {
		Materi materi = materiService.getMateri(kodeMateri);
		return materi;
	}
	
	@RequestMapping(value = "/update/{kodeMateri}", method = RequestMethod.PUT)
	public String updateDataMateri(@RequestBody Materi materi, @PathVariable("kodeMateri") String kodeMateri) {
		String flagUpdate = materiService.updateMateri(materi,kodeMateri);
		return flagUpdate;
	}
	
	@RequestMapping(value = "/delete/{kodeMateri}", method = RequestMethod.DELETE)
	public String deleteDataMateri(@PathVariable("kodeMateri") String kodeMateri) {
		String flagUpdate = materiService.deleteMateri(kodeMateri);
		return flagUpdate;
	}

}
