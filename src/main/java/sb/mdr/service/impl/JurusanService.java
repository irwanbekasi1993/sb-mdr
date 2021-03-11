package sb.mdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import sb.mdr.model.Dosen;
import sb.mdr.model.Jurusan;
import sb.mdr.model.MataKuliah;
import sb.mdr.model.redis.RedisJurusan;
import sb.mdr.repository.DosenRepository;
import sb.mdr.repository.JurusanRepository;
import sb.mdr.repository.MataKuliahRepository;
import sb.mdr.repository.redis.RedisJurusanRepository;

@Service
public class JurusanService {

	@Autowired
	private JurusanRepository jurusanRepository;

	private Jurusan jurusan;
	
	private RedisJurusan redisJurusan;
	
	@Autowired
	private RedisJurusanRepository redisJurusanRepo;
	
	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
	
	private ObjectMapper mapper;

	public String insertJurusan(Jurusan localJurusan) {
		int increment = 0;
		int flagInsert = 0;
		String result=null;
		String cekJurusan = jurusanRepository.getLastKodeJurusan();
		if(cekJurusan==null) {
			increment += 1;
			localJurusan.setKodeJurusan("FAK" + increment);

		}else {
			increment=jurusanRepository.hitungJurusan()+1;
			localJurusan.setKodeJurusan("FAK" + increment);
		}
		try {

			if(flagInsert==1) {
				flagInsert = jurusanRepository.insertDataJurusan(localJurusan.getKodeJurusan(), localJurusan.getNamaJurusan());
				
				redisJurusan.setKodeJurusan(localJurusan.getKodeJurusan());
				redisJurusan.setNamaJurusan(localJurusan.getNamaJurusan());
				redisJurusanRepo.save(redisJurusan);
				
				byte[] bytes = mapper.writeValueAsBytes(localJurusan);
				String str = new String(bytes);
				
				kafkaTemplate.send("sbmdr",str);
				receiveMessage("sbmdr");
				result="data jurusan berhasil dimasukkan dengan kode jurusan: "+cekJurusan;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	@KafkaListener(topics="sbmdr",groupId="sbmdr")
	public void receiveMessage(String data) {
		System.err.println("receive message: "+data);
	}

	public List<Jurusan> getAllJurusan(int limit) {
		List<Jurusan> listJurusan = new ArrayList<>();
		try {
			listJurusan = jurusanRepository.selectAllJurusan(limit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listJurusan;
	}
	
	public Jurusan getJurusan(String kodeJurusan) {
		Jurusan jurusan = new Jurusan();
		try {
			jurusan= jurusanRepository.getJurusanByKodeJurusan(kodeJurusan);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jurusan;
	}

	public String deleteJurusan(String kodeJurusan) {
		int deleteFlag = 0;
		String result=null;
		try {
			deleteFlag = jurusanRepository.deleteDataJurusan(kodeJurusan);
			if(deleteFlag==1) {
				
				result="data jurusan dengan kode jurusan: "+kodeJurusan+" telah dihapus";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateJurusan(Jurusan jurusan,String kodeJurusan) {
		int updateFlag = 0;
		String result=null;
		try {
			updateFlag = jurusanRepository.updateDataJurusan(jurusan.getNamaJurusan(),kodeJurusan);
			if(updateFlag==1) {
				
				jurusan.setKodeJurusan(kodeJurusan);
				
				redisJurusan.setKodeJurusan(jurusan.getKodeJurusan());
				redisJurusan.setNamaJurusan(jurusan.getNamaJurusan());
				redisJurusanRepo.save(redisJurusan);
				
				byte[]bytes = mapper.writeValueAsBytes(jurusan);
				String str = new String(bytes);
				kafkaTemplate.send("sbmdr",str);
				
				System.err.println("sending message: "+str);
				
				receiveMessage("sbmdr");
				
				result="data jurusan telah diperbaharui dengan kode jurusan: "+kodeJurusan;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}



}
