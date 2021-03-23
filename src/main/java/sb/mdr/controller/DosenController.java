package sb.mdr.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
/*import org.springframework.kafka.core.KafkaTemplate;*/
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sb.mdr.model.Dosen;
import sb.mdr.repository.redis.RedisDosenRepository;
import sb.mdr.service.impl.DosenService;
import sb.mdr.service.impl.SessionService;
import sb.mdr.util.JwtTokenUtil;

@RestController
@RequestMapping(value = "sb-mdr/test/v1/dosen")
public class DosenController {

	@Autowired
	private DosenService dosenService;

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertDataDosen( @RequestBody Dosen dosen) {
		String flagInsert = null;
		flagInsert = dosenService.insertDosen(dosen);
		return flagInsert;
	}

	@RequestMapping(value = "/all/{limit}", method = RequestMethod.GET)
	public List<Dosen> getAllDosen(@PathVariable("limit") int limit) {
		List<Dosen> listDosen = new ArrayList<>();
		listDosen = dosenService.getAllDosen(limit);

		return listDosen;
	}

	@RequestMapping(value = "/{kodeDosen}", method = RequestMethod.GET)
	public Dosen getDosen(@PathVariable("kodeDosen") String kodeDosen) {
		Dosen dosen = dosenService.getDosen(kodeDosen);
		return dosen;
	}

	@RequestMapping(value = "/update/{kodeDosen}", method = RequestMethod.PUT)
	public String updateDataDosen(@RequestBody Dosen dosen, @PathVariable("kodeDosen") String kodeDosen) {
		String flagUpdate = dosenService.updateDosen(dosen, kodeDosen);
		return flagUpdate;
	}

	@RequestMapping(value = "/delete/{kodeDosen}", method = RequestMethod.DELETE)
	public String deleteDataDosen(@PathVariable("kodeDosen") String kodeDosen) {
		String flagUpdate = dosenService.deleteDosen(kodeDosen);
		return flagUpdate;
	}

}
