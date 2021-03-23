package sb.mdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;*/
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import sb.mdr.model.Dosen;
import sb.mdr.model.MataKuliah;
import sb.mdr.model.Semester;
import sb.mdr.model.redis.RedisSemester;
import sb.mdr.repository.DosenRepository;
import sb.mdr.repository.MataKuliahRepository;
import sb.mdr.repository.SemesterRepository;
import sb.mdr.repository.redis.RedisSemesterRepository;

@Service
public class SemesterService {

	@Autowired
	private SemesterRepository semesterRepository;

	private Semester semester;
	
	private RedisSemester redisSemester;
	
	@Autowired
	private RedisSemesterRepository redisSemesterRepo;
	
	/*@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;*/
	
	private ObjectMapper objectMapper;

	public String insertSemester(Semester localSemester) {
		int increment = 0;
		int flagInsert = 0;
		String result=null;
		String cekSemester = semesterRepository.getLastKodeSemester();
		if(cekSemester==null) {
			increment += 1;
			localSemester.setKodeSemester("SMS" + increment);

		}else {
			increment=semesterRepository.hitungSemester()+1;
			localSemester.setKodeSemester("SMS" + increment);
		}
		try {
			flagInsert = semesterRepository.insertDataSemester(localSemester.getKodeSemester(), localSemester.getPeriode(),
					localSemester.getWaktuMasuk(),localSemester.getWaktuKeluar(),localSemester.getGanjilGenap());

			if(flagInsert==1) {
				
				redisSemester.setKodeSemester(localSemester.getKodeSemester());
				redisSemester.setPeriode(localSemester.getPeriode());
				redisSemester.setWaktuMasuk(localSemester.getWaktuMasuk());
				redisSemester.setWaktuKeluar(localSemester.getWaktuKeluar());
				redisSemester.setGanjilGenap(localSemester.getGanjilGenap());
				redisSemesterRepo.save(redisSemester);
				
				byte[]bytes=objectMapper.writeValueAsBytes(localSemester);
				String str = new String(bytes);
				/*kafkaTemplate.send("sbmdr",str);*/
				System.err.println("sending message: "+str);
				
				result="data semester berhasil dimasukkan dengan kode semester: "+cekSemester;
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
		Semester semester = new Semester();
		try {
			semester= semesterRepository.getSemesterByKodeSemester(kodeSemester);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return semester;
	}

	public String deleteSemester(String kodeSemester) {
		int deleteFlag = 0;
		String result=null;
		try {
			deleteFlag = semesterRepository.deleteDataSemester(kodeSemester);
			if(deleteFlag==1) {
				
				result="data semester dengan kode semester: "+kodeSemester+" telah dihapus";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateSemester(Semester semester,String kodeSemester) {
		int updateFlag = 0;
		String result=null;
		try {
			updateFlag = semesterRepository.updateDataSemester(semester.getPeriode(),semester.getWaktuMasuk(),
					semester.getWaktuKeluar(),semester.getGanjilGenap(),kodeSemester);
			if(updateFlag==1) {
				
				semester.setKodeSemester(kodeSemester);
				redisSemester.setKodeSemester(semester.getKodeSemester());
				redisSemester.setPeriode(semester.getPeriode());
				redisSemester.setWaktuMasuk(semester.getWaktuMasuk());
				redisSemester.setWaktuKeluar(semester.getWaktuKeluar());
				redisSemester.setGanjilGenap(semester.getGanjilGenap());
				redisSemesterRepo.save(redisSemester);
				
				byte[]bytes=objectMapper.writeValueAsBytes(semester);
				String str = new String(bytes);
				/*kafkaTemplate.send("sbmdr",str);*/
				System.err.println("sending message: "+str);
				
				result="data semester telah diperbaharui dengan kode semester: "+kodeSemester;
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
