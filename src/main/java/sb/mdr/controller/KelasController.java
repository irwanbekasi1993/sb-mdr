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
import sb.mdr.model.Kelas;
import sb.mdr.service.impl.DosenService;
import sb.mdr.service.impl.KelasService;

@RestController
@RequestMapping(value = "sb-mdr/test/v1/kelas")
public class KelasController {

	@Autowired
	private KelasService kelasService;

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
	
	@RequestMapping(value = "/update/{kodeKelas}", method = RequestMethod.PUT)
	public String updateDataKelas(@RequestBody Kelas kelas, @PathVariable("kodeKelas") String kodeKelas) {
		String flagUpdate = kelasService.updateKelas(kelas,kodeKelas);
		return flagUpdate;
	}
	
	@RequestMapping(value = "/delete/{kodeKelas}", method = RequestMethod.DELETE)
	public String deleteDataKelas(@PathVariable("kodeKelas") String kodeKelas) {
		String flagUpdate = kelasService.deleteKelas(kodeKelas);
		return flagUpdate;
	}

}
