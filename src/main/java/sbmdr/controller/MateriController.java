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
import sbmdr.model.Materi;
import sbmdr.model.Semester;
import sbmdr.model.Soal;
import sbmdr.service.impl.DosenService;
import sbmdr.service.impl.MataKuliahService;
import sbmdr.service.impl.MateriService;
import sbmdr.service.impl.SemesterService;
import sbmdr.service.impl.SoalService;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping(value = "/sbmdr/test/v1/materi")
public class MateriController {

	@Autowired
	private MateriService materiService;

	@PreAuthorize("hasRole('DOSEN')")
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
	
	@PreAuthorize("hasRole('DOSEN')")
	@RequestMapping(value = "/{kodeMateri}", method = RequestMethod.GET)
	public Materi getMateri(@PathVariable("kodeMateri") String kodeMateri) {
		Materi materi = materiService.getMateri(kodeMateri);
		return materi;
	}
	
	@PreAuthorize("hasRole('DOSEN')")
	@RequestMapping(value = "/update/{kodeMateri}", method = RequestMethod.PUT)
	public String updateDataMateri(@RequestBody Materi materi, @PathVariable("kodeMateri") String kodeMateri) {
		String flagUpdate = materiService.updateMateri(materi,kodeMateri);
		return flagUpdate;
	}
	
	@PreAuthorize("hasRole('DOSEN')")
	@RequestMapping(value = "/delete/{kodeMateri}", method = RequestMethod.DELETE)
	public String deleteDataMateri(@PathVariable("kodeMateri") String kodeMateri) {
		String flagUpdate = materiService.deleteMateri(kodeMateri);
		return flagUpdate;
	}

}
