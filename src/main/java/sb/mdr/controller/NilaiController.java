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
import sb.mdr.model.Nilai;
import sb.mdr.model.Semester;
import sb.mdr.service.impl.DosenService;
import sb.mdr.service.impl.MataKuliahService;
import sb.mdr.service.impl.NilaiService;
import sb.mdr.service.impl.SemesterService;

@RestController
@RequestMapping(value = "sb-mdr/test/v1/nilai")
public class NilaiController {

	@Autowired
	private NilaiService nilaiService;

	@RequestMapping(value = "/insert/nilai", method = RequestMethod.POST)
	public String insertDataNilai(@RequestBody Nilai nilai) {
		String flagInsert = nilaiService.insertNilai(nilai);
		return flagInsert;
	}

	@RequestMapping(value = "/all/nilai/{limit}", method = RequestMethod.GET)
	public List<Nilai> getAllNilai(@PathVariable("limit") int limit) {
		List<Nilai> listNilai = nilaiService.getAllNilai(limit);
		return listNilai;
	}
	
	@RequestMapping(value = "/nilai/{kodeNilai}", method = RequestMethod.GET)
	public Nilai getNilai(@PathVariable("kodeNilai") String kodeNilai) {
		Nilai nilai = nilaiService.getNilai(kodeNilai);
		return nilai;
	}
	
	@RequestMapping(value = "/update/nilai/{kodeNilai}", method = RequestMethod.PUT)
	public String updateDataNilai(@RequestBody Nilai nilai, @PathVariable("kodeNilai") String kodeNilai) {
		String flagUpdate = nilaiService.updateNilai(nilai,kodeNilai);
		return flagUpdate;
	}
	
	@RequestMapping(value = "/delete/nilai/{kodeNilai}", method = RequestMethod.DELETE)
	public String deleteDataNilai(@PathVariable("kodeNilai") String kodeNilai) {
		String flagUpdate = nilaiService.deleteNilai(kodeNilai);
		return flagUpdate;
	}

}
