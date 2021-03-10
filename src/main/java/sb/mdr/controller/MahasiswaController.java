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
import sb.mdr.model.Mahasiswa;
import sb.mdr.service.impl.DosenService;
import sb.mdr.service.impl.MahasiswaService;

@RestController
@RequestMapping(value = "sb-mdr/test/v1/mahasiswa")
public class MahasiswaController {

	@Autowired
	private MahasiswaService mahasiswaService;

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertDataDosen(@RequestBody Mahasiswa mahasiswa) {
		String flagInsert = mahasiswaService.insertMahasiswa(mahasiswa);
		return flagInsert;
	}

	@RequestMapping(value = "/all/{limit}", method = RequestMethod.GET)
	public List<Mahasiswa> getAllDosen(@PathVariable("limit") int limit) {
		List<Mahasiswa> listMahasiswa = mahasiswaService.getAllMahasiswa(limit);
		return listMahasiswa;
	}
	
	@RequestMapping(value = "/{nim}", method = RequestMethod.GET)
	public Mahasiswa getMahasiswa(@PathVariable("nim") String nim) {
		Mahasiswa mahasiswa = mahasiswaService.getMahasiswa(nim);
		return mahasiswa;
	}
	
	@RequestMapping(value = "/update/{nim}", method = RequestMethod.PUT)
	public String updateDataMahasiswa(@RequestBody Mahasiswa mahasiswa, @PathVariable("nim") String nim) {
		String flagUpdate = mahasiswaService.updateMahasiswa(mahasiswa,nim);
		return flagUpdate;
	}
	
	@RequestMapping(value = "/delete/{nim}", method = RequestMethod.DELETE)
	public String deleteDataMahasiswa(@PathVariable("nim") String nim) {
		String flagUpdate = mahasiswaService.deleteMahasiswa(nim);
		return flagUpdate;
	}

}
