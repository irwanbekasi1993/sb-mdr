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
import sbmdr.model.MataKuliah;
import sbmdr.service.impl.DosenService;
import sbmdr.service.impl.MataKuliahService;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping(value = "/sbmdr/test/v1/matkul")
public class MataKuliahController {

	@Autowired
	private MataKuliahService matKulService;

	private String result;

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertDataMatKul(@RequestBody MataKuliah matkul) {
		result = matKulService.insertMatKul(matkul);
		return result;
	}

	@RequestMapping(value = "/all/{limit}", method = RequestMethod.GET)
	public List<MataKuliah> getAllMatKul(@PathVariable("limit") int limit) {
		List<MataKuliah> listMatKul = matKulService.getAllMatKul(limit);
		return listMatKul;
	}
	
	@RequestMapping(value = "/{kodeMatKul}", method = RequestMethod.GET)
	public MataKuliah getMatKul(@PathVariable("kodeMatKul") String kodeMatKul) {
		MataKuliah matkul = matKulService.getMatKul(kodeMatKul);
		return matkul;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/update/{kodeMatKul}", method = RequestMethod.PUT)
	public String updateDataMatKul(@RequestBody MataKuliah matkul, @PathVariable("kodeMatKul") String kodeMatKul) {
		result = matKulService.updateMatKul(matkul,kodeMatKul);
		return result;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/delete/{kodeMatKul}", method = RequestMethod.DELETE)
	public String deleteDataMatKul(@PathVariable("kodeMatKul") String kodeMatKul) {
		result = matKulService.deleteMatKul(kodeMatKul);
		return result;
	}

}
