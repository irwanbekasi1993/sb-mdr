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
import sb.mdr.service.impl.DosenService;

@RestController
@RequestMapping(value = "sb-mdr/test/v1/dosen")
public class DosenController {

	@Autowired
	private DosenService dosenService;

	@RequestMapping(value = "/insert/dosen", method = RequestMethod.POST)
	public String insertDataDosen(@RequestBody Dosen dosen) {
		String flagInsert = dosenService.insertDosen(dosen);
		return flagInsert;
	}

	@RequestMapping(value = "/all/dosen/{limit}", method = RequestMethod.GET)
	public List<Dosen> getAllDosen(@PathVariable("limit") int limit) {
		List<Dosen> listDosen = dosenService.getAllDosen(limit);
		return listDosen;
	}
	
	@RequestMapping(value = "/dosen/{kodeDosen}", method = RequestMethod.GET)
	public Dosen getDosen(@PathVariable("kodeDosen") String kodeDosen) {
		Dosen dosen = dosenService.getDosen(kodeDosen);
		return dosen;
	}
	
	@RequestMapping(value = "/update/dosen/{kodeDosen}", method = RequestMethod.PUT)
	public String updateDataDosen(@RequestBody Dosen dosen, @PathVariable("kodeDosen") String kodeDosen) {
		String flagUpdate = dosenService.updateDosen(dosen,kodeDosen);
		return flagUpdate;
	}
	
	@RequestMapping(value = "/delete/dosen/{kodeDosen}", method = RequestMethod.DELETE)
	public String deleteDataDosen(@PathVariable("kodeDosen") String kodeDosen) {
		String flagUpdate = dosenService.deleteDosen(kodeDosen);
		return flagUpdate;
	}

}
