package sb.mdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sb.mdr.model.Dosen;
import sb.mdr.model.redis.RedisDosen;
import sb.mdr.repository.DosenRepository;
import sb.mdr.repository.redis.RedisDosenRepository;

@Service
public class DosenService {

	@Autowired
	private DosenRepository dosenRepository;

	@Autowired
	private RedisDosenRepository redisDosenRepo;

	private RedisDosen redisDosen = new RedisDosen();

	private Dosen dosen;

	public String insertDosen(Dosen localDosen) {
		int increment = 0;
		int flagInsert = 0;
		String result = null;
		String cekDosen = dosenRepository.getLastKodeDosen();
		if (cekDosen == null) {
			increment += 1;
			localDosen.setKodeDosen("DSN" + increment);

		} else {
			increment = dosenRepository.hitungDosen() + 1;
			localDosen.setKodeDosen("DSN" + increment);
		}
		try {
			flagInsert = dosenRepository.insertDataDosen(localDosen.getKodeDosen(), localDosen.getNamaDosen(),
					localDosen.getJenisKelaminDosen(), localDosen.getAlamatDosen(), localDosen.getStatusDosen(),
					localDosen.getNohp(), localDosen.getEmail());

			redisDosen.setKodeDosen(localDosen.getKodeDosen());
			redisDosen.setNamaDosen(localDosen.getNamaDosen());
			redisDosen.setJenisKelaminDosen(localDosen.getJenisKelaminDosen());
			redisDosen.setAlamatDosen(localDosen.getAlamatDosen());
			redisDosen.setStatusDosen(localDosen.getStatusDosen());
			redisDosen.setNohp(localDosen.getNohp());
			redisDosen.setEmail(localDosen.getEmail());
			redisDosenRepo.save(redisDosen);

			if (flagInsert == 1) {
				result = "data dosen berhasil dimasukkan dengan kode dosen: " + cekDosen;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

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

	public Dosen getDosen(String kodeDosen) {
		Dosen dosen = new Dosen();
		try {
			dosen = dosenRepository.getDosenByKodeDosen(kodeDosen);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dosen;
	}

	public String deleteDosen(String kodeDosen) {
		int deleteFlag = 0;
		String result = null;
		try {
			deleteFlag = dosenRepository.deleteDataDosen(kodeDosen);
			if (deleteFlag == 1) {

				result = "data dosen dengan kode dosen: " + kodeDosen + " telah dihapus";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateDosen(Dosen dosen, String kodeDosen) {
		int updateFlag = 0;
		String result = null;
		try {
			updateFlag = dosenRepository.updateDataDosen(dosen.getNamaDosen(), dosen.getAlamatDosen(),
					dosen.getJenisKelaminDosen(), dosen.getStatusDosen(), dosen.getNohp(), dosen.getEmail(), kodeDosen);
			if (updateFlag == 1) {
				result = "data dosen telah diperbaharui dengan kode dosen: " + kodeDosen;
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

	public Dosen getDosen() {
		return dosen;
	}

	public void setDosen(Dosen dosen) {
		this.dosen = dosen;
	}

}
