package sbmdr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sbmdr.model.Dosen;
import sbmdr.repository.redis.RedisDosenRepository;
import sbmdr.service.impl.DosenService;

@RestController
@RequestMapping(value = "/sbmdr/test/v1/dosen")
public class DosenController {

	@Autowired
	private DosenService dosenService;
	
	// @PreAuthorize("hasRole('DOSEN')")
	// @RequestMapping(value = "/insert", method = RequestMethod.POST)
	// public String insertDataDosen(@RequestBody Dosen dosen) {
	// 	String flagInsert = dosenService.insertDosen(dosen);
	// 	return flagInsert;
	// }

	@RequestMapping(value = "/all/{limit}", method = RequestMethod.GET)
	public List<Dosen> getAllDosen(@PathVariable("limit") int limit) {
		List<Dosen> listDosen = dosenService.getAllDosen(limit);
		return listDosen;
	}
	
	@RequestMapping(value = "/{kodeDosen}", method = RequestMethod.GET)
	public Dosen getDosen(@PathVariable("kodeDosen") String kodeDosen) {
		Dosen dosen = dosenService.getDosen(kodeDosen);
		return dosen;
	}
	
	@PreAuthorize("hasRole('DOSEN')")
	@RequestMapping(value = "/update/{kodeDosen}", method = RequestMethod.PUT)
	public String updateDataDosen(@RequestBody Dosen dosen, @PathVariable("kodeDosen") String kodeDosen) {
		String flagUpdate = dosenService.updateDosen(dosen,kodeDosen);
		return flagUpdate;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/delete/{kodeDosen}", method = RequestMethod.DELETE)
	public String deleteDataDosen(@PathVariable("kodeDosen") String kodeDosen) {
		String flagUpdate = dosenService.deleteDosen(kodeDosen);
		return flagUpdate;
	}

}
