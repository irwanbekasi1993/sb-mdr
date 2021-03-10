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
import sb.mdr.model.Jurusan;
import sb.mdr.model.MataKuliah;
import sb.mdr.service.impl.DosenService;
import sb.mdr.service.impl.JurusanService;
import sb.mdr.service.impl.MataKuliahService;

@RestController
@RequestMapping(value = "sb-mdr/test/v1/jurusan")
public class JurusanController {

	@Autowired
	private JurusanService jurusanService;

	@RequestMapping(value = "/insert/jurusan", method = RequestMethod.POST)
	public String insertDataJurusan(@RequestBody Jurusan jurusan) {
		String flagInsert = jurusanService.insertJurusan(jurusan);
		return flagInsert;
	}

	@RequestMapping(value = "/all/jurusan/{limit}", method = RequestMethod.GET)
	public List<Jurusan> getAllMatKul(@PathVariable("limit") int limit) {
		List<Jurusan> listJurusan = jurusanService.getAllJurusan(limit);
		return listJurusan;
	}
	
	@RequestMapping(value = "/jurusan/{kodeJurusan}", method = RequestMethod.GET)
	public Jurusan getJurusan(@PathVariable("kodeJurusan") String kodeJurusan) {
		Jurusan jurusan = jurusanService.getJurusan(kodeJurusan);
		return jurusan;
	}
	
	@RequestMapping(value = "/update/jurusan/{kodeJurusan}", method = RequestMethod.PUT)
	public String updateDataJurusan(@RequestBody Jurusan jurusan, @PathVariable("kodeJurusan") String kodeJurusan) {
		String flagUpdate = jurusanService.updateJurusan(jurusan,kodeJurusan);
		return flagUpdate;
	}
	
	@RequestMapping(value = "/delete/jurusan/{kodeJurusan}", method = RequestMethod.DELETE)
	public String deleteDataJurusan(@PathVariable("kodeJurusan") String kodeJurusan) {
		String flagUpdate = jurusanService.deleteJurusan(kodeJurusan);
		return flagUpdate;
	}

}
