package sb.mdr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sb.mdr.model.Jadwal;
import sb.mdr.model.pojo.JadwalDto;
import sb.mdr.service.impl.JadwalService;

@RestController
@RequestMapping(value = "sb-mdr/test/v1/jadwal")
public class JadwalController {

	@Autowired
	private JadwalService jadwalService;
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertDataDosen(@RequestBody JadwalDto jadwalDto) {
		String flagInsert = jadwalService.insertJadwal(jadwalDto);
		return flagInsert;
	}

	
	@RequestMapping(value = "/all/{limit}", method = RequestMethod.GET)
	public List<Jadwal> getAllJadwal(@PathVariable("limit") int limit) {
		List<Jadwal> listJadwal = jadwalService.getAllJadwal(limit);
		return listJadwal;
	}
	
	@RequestMapping(value = "/{kodeJadwal}", method = RequestMethod.GET)
	public Jadwal getJadwal(@PathVariable("kodeJadwal") String kodeJadwal) {
		Jadwal jadwal = jadwalService.getJadwal(kodeJadwal);
		return jadwal;
	}
	
	@RequestMapping(value = "/update/{kodeJadwal}", method = RequestMethod.PUT)
	public String updateDataJadwal(@RequestBody JadwalDto jadwalDto, @PathVariable("kodeJadwal") String kodeJadwal) {
		String flagUpdate = jadwalService.updateJadwal(jadwalDto,kodeJadwal);
		return flagUpdate;
	}
	
	@RequestMapping(value = "/delete/{kodeJadwal}", method = RequestMethod.DELETE)
	public String deleteDataJadwal(@PathVariable("kodeJadwal") String kodeJadwal) {
		String flagUpdate = jadwalService.deleteJadwal(kodeJadwal);
		return flagUpdate;
	}

}
