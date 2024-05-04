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
import sbmdr.model.Mahasiswa;
import sbmdr.service.impl.DosenService;
import sbmdr.service.impl.MahasiswaService;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping(value = "/sbmdr/test/v1/mahasiswa")
public class MahasiswaController {

	@Autowired
	private MahasiswaService mahasiswaService;

	private String result;

	// @PreAuthorize("hasRole('MAHASISWA')")
	// @RequestMapping(value = "/insert", method = RequestMethod.POST)
	// public String insertDataDosen(@RequestBody Mahasiswa mahasiswa) {
	// 	String flagInsert = mahasiswaService.insertMahasiswa(mahasiswa);
	// 	return flagInsert;
	// }

	@RequestMapping(value = "/all/{limit}", method = RequestMethod.GET)
	public List<Mahasiswa> getAllDosen(@PathVariable("limit") int limit) {
		List<Mahasiswa> listMahasiswa = mahasiswaService.getAllMahasiswa(limit);
		return listMahasiswa;
	}
	
	@PreAuthorize("hasRole('MAHASISWA')")
	@RequestMapping(value = "/{nim}", method = RequestMethod.GET)
	public Mahasiswa getMahasiswa(@PathVariable("nim") String nim) {
		Mahasiswa mahasiswa = mahasiswaService.getMahasiswa(nim);
		return mahasiswa;
	}
	
	@PreAuthorize("hasRole('MAHASISWA')")
	@RequestMapping(value = "/update/{nim}", method = RequestMethod.PUT)
	public String updateDataMahasiswa(@RequestBody Mahasiswa mahasiswa, @PathVariable("nim") String nim) {
		result = mahasiswaService.updateMahasiswa(mahasiswa,nim);
		return result;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/delete/{nim}", method = RequestMethod.DELETE)
	public String deleteDataMahasiswa(@PathVariable("nim") String nim) {
		result = mahasiswaService.deleteMahasiswa(nim);
		return result;
	}

}
