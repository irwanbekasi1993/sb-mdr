package sbmdr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sbmdr.model.Dosen;
import sbmdr.model.Jurusan;
import sbmdr.model.MataKuliah;
import sbmdr.service.impl.DosenService;
import sbmdr.service.impl.JurusanService;
import sbmdr.service.impl.MataKuliahService;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping(value = "/sbmdr/test/v1/jurusan")
public class JurusanController {

	@Autowired
	private JurusanService jurusanService;

				@Value("${sbmdr.key}")
private String key;

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertDataJurusan(@RequestBody Jurusan jurusan) {
		String flagInsert = jurusanService.insertJurusan(jurusan);
		return flagInsert;
	}

	@RequestMapping(value = "/all/{limit}", method = RequestMethod.GET)
	public List<Jurusan> getAllMatKul(@PathVariable("limit") int limit) {
		List<Jurusan> listJurusan = jurusanService.getAllJurusan(limit);
		return listJurusan;
	}
	
	@RequestMapping(value = "/{kodeJurusan}", method = RequestMethod.GET)
	public Jurusan getJurusan(@PathVariable("kodeJurusan") String kodeJurusan) {
		Jurusan jurusan = jurusanService.getJurusan(kodeJurusan);
		return jurusan;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/update/{kodeJurusan}", method = RequestMethod.PUT)
	public String updateDataJurusan(@RequestBody Jurusan jurusan, @PathVariable("kodeJurusan") String kodeJurusan) {
		String flagUpdate = jurusanService.updateJurusan(jurusan,kodeJurusan);
		return flagUpdate;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/delete/{kodeJurusan}", method = RequestMethod.DELETE)
	public String deleteDataJurusan(@PathVariable("kodeJurusan") String kodeJurusan) {
		String flagUpdate = jurusanService.deleteJurusan(kodeJurusan);
		return flagUpdate;
	}

}
