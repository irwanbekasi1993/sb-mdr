package sbmdr.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import sbmdr.model.Dosen;
import sbmdr.model.redis.RedisDosen;
import sbmdr.repository.DosenRepository;
import sbmdr.repository.RoleRepository;
import sbmdr.repository.UserRepository;
import sbmdr.repository.redis.RedisDosenRepository;

@Service
public class DosenService {

	@Autowired
	private DosenRepository dosenRepository;

	@Autowired
	private RedisDosenRepository redisDosenRepo;

	private RedisDosen redisDosen = new RedisDosen();


	// @Autowired
	// private KafkaTemplate<String, String> kafkaTemplate;

	// private KafkaConsumer<String, Dosen> kafkaConsumer;
	
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private MataKuliahService mataKuliahService;

	@Autowired
	private JurusanService jurusanService;


	// @Autowired
	// private UserRepository userRepository;

	// @Autowired
	// private RoleRepository roleRepository;

	// public String insertDosen(Dosen localDosen) {
	// 	int increment = 0;
	// 	int flagInsert = 0;
	// 	String result = null;
	// 	String cekDosen = dosenRepository.getLastKodeDosen();
	// 	if (cekDosen == null) {
	// 		increment += 1;
	// 		localDosen.setKodeDosen("DSN" + increment);

	// 	} else {
	// 		increment = dosenRepository.hitungDosen() + 1;
	// 		localDosen.setKodeDosen("DSN" + increment);
	// 	}
	// 	try {
	// 		if(roleRepository.findByName("ROLE_DOSEN").isPresent()){
	// 		flagInsert = dosenRepository.insertDataDosen(localDosen.getKodeDosen(), localDosen.getNamaDosen(),
	// 				localDosen.getEmail());

	// 		redisDosen.setKodeDosen(localDosen.getKodeDosen());
	// 		redisDosen.setNamaDosen(localDosen.getNamaDosen());
	// 		redisDosen.setEmail(localDosen.getEmail());
	// 		redisDosenRepo.save(redisDosen);

	// 		if (flagInsert == 1) {
				
	// 			byte[]bytes = mapper.writeValueAsBytes(dosen);
	// 			String str= new String(bytes);
				
	// 			System.err.println("sending message: "+str);
				
	// 			// kafkaTemplate.send("sbmdr", str);
	// 			//kafkaConsumer.subscribe(Collections.singletonList("sbmdr"));
				
	// 			result = "data dosen berhasil dimasukkan dengan kode dosen: " + cekDosen;
	// 		}
	// 	}
	// 	} catch (Exception e) {
	// 		// TODO: handle exception
	// 		e.printStackTrace();
	// 	}
	// 	return result;
	// }

	public List<Dosen> getAllDosen(int limit) {
		List<Dosen> listDosen = new ArrayList<>();
		try {
			listDosen = dosenRepository.selectAllDosen(limit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listDosen;
	}

	public Dosen getDosen(String nip) {
		Dosen dosen = new Dosen();
		try {
			dosen = dosenRepository.getDosenByNip(nip);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dosen;
	}

	public String deleteDosen(String nip) {
		String result = null;
		try {
			Dosen cek = getDosen(nip);
			if(cek!=null){
			dosenRepository.deleteDataDosen(nip);
				redisDosen.setNip(nip);
				redisDosenRepo.deleteById(redisDosen.getNip());
				result = "data dosen dengan kode dosen: " + nip + " telah dihapus";
			}else{
				result="data dosen not found";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateDosen(Dosen dosen, String nip) {
		String result = null;
		try {
			Dosen cek = getDosen(nip);
			if(cek!=null){
				dosenRepository.updateDataDosen(dosen.getNamaDosen(), dosen.getAlamatDosen(),
					dosen.getJenisKelaminDosen(), dosen.getStatusDosen(), dosen.getNohp(), dosen.getEmail(), nip);
					
				dosen.setNip(nip);
				redisDosen.setNip(dosen.getNip());
				redisDosen.setNamaDosen(dosen.getNamaDosen());
				redisDosen.setJenisKelaminDosen(dosen.getJenisKelaminDosen());
				redisDosen.setAlamatDosen(dosen.getAlamatDosen());
				redisDosen.setStatusDosen(dosen.getStatusDosen());
				redisDosen.setNohp(dosen.getNohp());
				redisDosen.setEmail(dosen.getEmail());
				redisDosenRepo.save(redisDosen);
				
				byte[]bytes = mapper.writeValueAsBytes(dosen);
				String str= new String(bytes);
				
				System.err.println("sending message: "+str);
				
				// kafkaTemplate.send("sbmdr", str);
				//kafkaConsumer.subscribe(Collections.singletonList("sbmdr"));
				result = "data dosen telah diperbaharui dengan kode dosen: " + nip;
				
			}else{
				result="data dosen not found";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	

	public DosenRepository getDosenRepository() {
		return dosenRepository;
	}

	public void setDosenRepository(DosenRepository dosenRepository) {
		this.dosenRepository = dosenRepository;
	}


}
