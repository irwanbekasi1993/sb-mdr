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
import sbmdr.model.Kelas;
import sbmdr.service.impl.DosenService;
import sbmdr.service.impl.KelasService;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping(value = "/sbmdr/test/v1/kelas")
public class KelasController {

	@Autowired
	private KelasService kelasService;

				@Value("${sbmdr.key}")
private String key;

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertDataDosen(@RequestBody Kelas kelas) {
		String flagInsert = kelasService.insertKelas(kelas);
		return flagInsert;
	}

	@RequestMapping(value = "/all/{limit}", method = RequestMethod.GET)
	public List<Kelas> getAllKelas(@PathVariable("limit") int limit) {
		List<Kelas> listKelas = kelasService.getAllKelas(limit);
		return listKelas;
	}
	
	@RequestMapping(value = "/{kodeKelas}", method = RequestMethod.GET)
	public Kelas getKelas(@PathVariable("kodeKelas") String kodeKelas) {
		Kelas kelas = kelasService.getKelas(kodeKelas);
		return kelas;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/update/{kodeKelas}", method = RequestMethod.PUT)
	public String updateDataKelas(@RequestBody Kelas kelas, @PathVariable("namaKelas") String namaKelas) {
		String flagUpdate = kelasService.updateKelas(kelas,namaKelas);
		return flagUpdate;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/delete/{kodeKelas}", method = RequestMethod.DELETE)
	public String deleteDataKelas(@PathVariable("kodeKelas") String kodeKelas) {
		String flagUpdate = kelasService.deleteKelas(kodeKelas);
		return flagUpdate;
	}

}
