package sb.mdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
*/
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import sb.mdr.model.Dosen;
import sb.mdr.model.Mahasiswa;
import sb.mdr.model.redis.RedisMahasiswa;
import sb.mdr.repository.DosenRepository;
import sb.mdr.repository.MahasiswaRepository;
import sb.mdr.repository.redis.RedisMahasiswaRepository;

@Service
public class MahasiswaService {

	@Autowired
	private MahasiswaRepository mahasiswaRepository;

	private Mahasiswa mahasiswa;
	
	private RedisMahasiswa redisMahasiswa;
	
	@Autowired
	private RedisMahasiswaRepository redisMahasiswaRepo;
	
/*	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
*/	
	private ObjectMapper objectMapper;

	public String insertMahasiswa(Mahasiswa localMahasiswa) {
		int increment = 0;
		int flagInsert = 0;
		String result=null;
		String cekMahasiswa = mahasiswaRepository.getLastNim();
		if(cekMahasiswa==null) {
			increment += 1;
			localMahasiswa.setNim("MHS" + increment);

		}else {
			increment=mahasiswaRepository.hitungMahasiswa()+1;
			localMahasiswa.setNim("MHS" + increment);
		}
		try {
			flagInsert = mahasiswaRepository.insertDataMahasiswa(localMahasiswa.getNim(), localMahasiswa.getNamaMahasiswa(),
					localMahasiswa.getJenisKelaminMahasiswa(), 
					localMahasiswa.getAlamatMahasiswa(), localMahasiswa.getEmail(), localMahasiswa.getNohp(),
					localMahasiswa.getStatusMahasiswa());

			if(flagInsert==1) {
				
				redisMahasiswa.setNim(localMahasiswa.getNim());
				redisMahasiswa.setNamaMahasiswa(localMahasiswa.getNamaMahasiswa());
				redisMahasiswa.setJenisKelaminMahasiswa(localMahasiswa.getJenisKelaminMahasiswa());
				redisMahasiswa.setAlamatMahasiswa(localMahasiswa.getAlamatMahasiswa());
				redisMahasiswa.setEmail(localMahasiswa.getEmail());
				redisMahasiswa.setNohp(localMahasiswa.getNohp());
				redisMahasiswa.setStatusMahasiswa(localMahasiswa.getStatusMahasiswa());
				redisMahasiswaRepo.save(redisMahasiswa);
				
				byte[]bytes = objectMapper.writeValueAsBytes(localMahasiswa);
				String str = new String(bytes);
				/*kafkaTemplate.send("sbmdr",str);*/
				System.err.println("sending message: "+str);
				
				result="data mahasiswa berhasil dimasukkan dengan nim: "+cekMahasiswa;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	

	public List<Mahasiswa> getAllMahasiswa(int limit) {
		List<Mahasiswa> listMahasiswa = new ArrayList<>();
		try {
			listMahasiswa = mahasiswaRepository.selectAllMahasiswa(limit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listMahasiswa;
	}
	
	public Mahasiswa getMahasiswa(String nim) {
		Mahasiswa mahasiswa = new Mahasiswa();
		try {
			mahasiswa= mahasiswaRepository.getMahasiswaByNim(nim);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return mahasiswa;
	}

	public String deleteMahasiswa(String nim) {
		int deleteFlag = 0;
		String result=null;
		try {
			deleteFlag = mahasiswaRepository.deleteDataMahasiswa(nim);
			if(deleteFlag==1) {
				
				result="data mahasiswa dengan nim: "+nim+" telah dihapus";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateMahasiswa(Mahasiswa mahasiswa,String nim) {
		int updateFlag = 0;
		String result=null;
		try {
			updateFlag = mahasiswaRepository.updateDataMahasiswa(mahasiswa.getNamaMahasiswa(),mahasiswa.getJenisKelaminMahasiswa(),
					mahasiswa.getAlamatMahasiswa(),mahasiswa.getNohp(), mahasiswa.getEmail(),mahasiswa.getStatusMahasiswa(), nim);
			if(updateFlag==1) {
				
				mahasiswa.setNim(nim);
				redisMahasiswa.setNim(mahasiswa.getNim());
				redisMahasiswa.setNamaMahasiswa(mahasiswa.getNamaMahasiswa());
				redisMahasiswa.setJenisKelaminMahasiswa(mahasiswa.getJenisKelaminMahasiswa());
				redisMahasiswa.setAlamatMahasiswa(mahasiswa.getAlamatMahasiswa());
				redisMahasiswa.setEmail(mahasiswa.getEmail());
				redisMahasiswa.setNohp(mahasiswa.getNohp());
				redisMahasiswa.setStatusMahasiswa(mahasiswa.getStatusMahasiswa());
				redisMahasiswaRepo.save(redisMahasiswa);
				
				byte[]bytes = objectMapper.writeValueAsBytes(mahasiswa);
				String str = new String(bytes);
				/*kafkaTemplate.send("sbmdr",str);*/
				
				System.err.println("sending message: "+str);
				
				
				result="data mahasiswa telah diperbaharui dengan nim: "+nim;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public MahasiswaRepository getMahasiswaRepository() {
		return mahasiswaRepository;
	}

	public void setMahasiswaRepository(MahasiswaRepository mahasiswaRepository) {
		this.mahasiswaRepository = mahasiswaRepository;
	}

	public Mahasiswa getMahasiswa() {
		return mahasiswa;
	}

	public void setMahasiswa(Mahasiswa mahasiswa) {
		this.mahasiswa = mahasiswa;
	}



}
