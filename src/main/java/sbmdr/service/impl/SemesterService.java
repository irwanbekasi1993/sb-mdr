package sbmdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import sbmdr.model.Dosen;
import sbmdr.model.MataKuliah;
import sbmdr.model.Semester;
import sbmdr.model.redis.RedisSemester;
import sbmdr.repository.DosenRepository;
import sbmdr.repository.MataKuliahRepository;
import sbmdr.repository.SemesterRepository;
import sbmdr.repository.redis.RedisSemesterRepository;

@Service
public class SemesterService {

	@Autowired
	private SemesterRepository semesterRepository;

	private Semester semester;
	
	private RedisSemester redisSemester;
	
	@Autowired
	private RedisSemesterRepository redisSemesterRepo;
	
	// @Autowired
	// private KafkaTemplate<String,String> kafkaTemplate;
	
	private ObjectMapper objectMapper;

	public String insertSemester(Semester localSemester) {
		String result=null;
		
		try {
			Semester cek = getSemester(localSemester.getKodeSemester());
			if(cek==null){
				semesterRepository.insertDataSemester(localSemester.getKodeSemester(), localSemester.getPeriodeMulai(),localSemester.getPeriodeSelesai());

				redisSemester = new RedisSemester();	
				redisSemester.setKodeSemester(localSemester.getKodeSemester());
				redisSemester.setPeriodeMulai(localSemester.getPeriodeMulai());
				redisSemester.setPeriodeSelesai(localSemester.getPeriodeSelesai());
				redisSemesterRepo.save(redisSemester);
				
				objectMapper = new ObjectMapper();
				byte[]bytes=objectMapper.writeValueAsBytes(localSemester);
				String str = new String(bytes);
				// kafkaTemplate.send("sbmdr",str);
				System.err.println("sending message: "+str);
				
				result="data semester berhasil dimasukkan dengan kode semester: "+localSemester.getKodeSemester();
			
			}else{
				result="semester not found";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public List<Semester> getAllSemester(int limit) {
		List<Semester> listSemester = new ArrayList<>();
		try {
			listSemester = semesterRepository.selectAllSemester(limit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listSemester;
	}
	
	public Semester getSemester(String kodeSemester) {
		Semester cekSemester = new Semester();
		try {
			cekSemester= semesterRepository.getSemesterByKodeSemester(kodeSemester);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return cekSemester;
	}

	public String deleteSemester(String kodeSemester) {
		String result=null;
		try {
			Semester cekSMS = getSemester(kodeSemester);
			if(cekSMS!=null){
				 semesterRepository.deleteDataSemester(kodeSemester);
				
				result="data semester dengan kode semester: "+kodeSemester+" telah dihapus";
			
			}else{
				result="data semester not found";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateSemester(Semester inputSemester,String kodeSemester) {
		
		String result=null;
		try {
			Semester cekSMS = getSemester(kodeSemester);
			if(cekSMS!=null){
				 semesterRepository.updateDataSemester(inputSemester.getPeriodeMulai(),inputSemester.getPeriodeSelesai(),kodeSemester);
				
				redisSemester = new RedisSemester();
				semester.setKodeSemester(kodeSemester);
				redisSemester.setKodeSemester(semester.getKodeSemester());
				redisSemester.setPeriodeMulai(semester.getPeriodeMulai());
				redisSemester.setPeriodeSelesai(semester.getPeriodeSelesai());
				redisSemesterRepo.save(redisSemester);
				
				objectMapper = new ObjectMapper();
				byte[]bytes=objectMapper.writeValueAsBytes(semester);
				String str = new String(bytes);
				// kafkaTemplate.send("sbmdr",str);
				System.err.println("sending message: "+str);
				
				result="data semester telah diperbaharui dengan kode semester: "+kodeSemester;
			
			}else{
				result="data semester not found";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	

	public SemesterRepository getSemesterRepository() {
		return semesterRepository;
	}

	public void setSemesterRepository(SemesterRepository semesterRepository) {
		this.semesterRepository = semesterRepository;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}





}
