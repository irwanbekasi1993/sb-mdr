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
import sb.mdr.service.impl.DosenService;
import sb.mdr.service.impl.MataKuliahService;

@RestController
@RequestMapping(value = "sb-mdr/test/v1/matkul")
public class MataKuliahController {

	@Autowired
	private MataKuliahService matKulService;

	@RequestMapping(value = "/insert/matkul", method = RequestMethod.POST)
	public String insertDataMatKul(@RequestBody MataKuliah matkul) {
		String flagInsert = matKulService.insertMatKul(matkul);
		return flagInsert;
	}

	@RequestMapping(value = "/all/matkul/{limit}", method = RequestMethod.GET)
	public List<MataKuliah> getAllMatKul(@PathVariable("limit") int limit) {
		List<MataKuliah> listMatKul = matKulService.getAllMatKul(limit);
		return listMatKul;
	}
	
	@RequestMapping(value = "/matkul/{kodeMatKul}", method = RequestMethod.GET)
	public MataKuliah getMatKul(@PathVariable("kodeMatKul") String kodeMatKul) {
		MataKuliah matkul = matKulService.getMatKul(kodeMatKul);
		return matkul;
	}
	
	@RequestMapping(value = "/update/matkul/{kodeMatKul}", method = RequestMethod.PUT)
	public String updateDataMatKul(@RequestBody MataKuliah matkul, @PathVariable("kodeMatKul") String kodeMatKul) {
		String flagUpdate = matKulService.updateMatKul(matkul,kodeMatKul);
		return flagUpdate;
	}
	
	@RequestMapping(value = "/delete/matkul/{kodeMatKul}", method = RequestMethod.DELETE)
	public String deleteDataMatKul(@PathVariable("kodeMatKul") String kodeMatKul) {
		String flagUpdate = matKulService.deleteMatKul(kodeMatKul);
		return flagUpdate;
	}

}
