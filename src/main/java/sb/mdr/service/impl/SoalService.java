package sb.mdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sb.mdr.model.Dosen;
import sb.mdr.model.MataKuliah;
import sb.mdr.model.Semester;
import sb.mdr.model.Soal;
import sb.mdr.repository.DosenRepository;
import sb.mdr.repository.MataKuliahRepository;
import sb.mdr.repository.SemesterRepository;
import sb.mdr.repository.SoalRepository;

@Service
public class SoalService {

	@Autowired
	private SoalRepository soalRepository;

	private Soal soal;

	public String insertSoal(Soal localSoal) {
		int increment = 0;
		int flagInsert = 0;
		String result=null;
		String cekSoal = soalRepository.getLastKodeSoal();
		if(cekSoal==null) {
			increment += 1;
			localSoal.setKodeSoal("DSN" + increment);

		}else {
			increment=soalRepository.hitungSoal()+1;
			localSoal.setKodeSoal("DSN" + increment);
		}
		try {
			flagInsert = soalRepository.insertDataSoal(localSoal.getKodeSoal(), localSoal.getJenisSoal(),
					localSoal.getWaktuPengerjaan(),localSoal.getWaktuPengumpulan(),localSoal.getIsiSoal(),localSoal.getNilai());

			if(flagInsert==1) {
				result="data semester berhasil dimasukkan dengan kode soal: "+cekSoal;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public List<Soal> getAllSoal(int limit) {
		List<Soal> listSoal= new ArrayList<>();
		try {
			listSoal = soalRepository.selectAllSoal(limit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listSoal;
	}
	
	public Soal getSoal(String kodeSoal) {
		Soal soal = new Soal();
		try {
			soal= soalRepository.getSoalByKodeSoal(kodeSoal);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return soal;
	}

	public String deleteSoal(String kodeSoal) {
		int deleteFlag = 0;
		String result=null;
		try {
			deleteFlag = soalRepository.deleteDataSoal(kodeSoal);
			if(deleteFlag==1) {
				
				result="data soal dengan kode soal: "+kodeSoal+" telah dihapus";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateSoal(Soal soal,String kodeSoal) {
		int updateFlag = 0;
		String result=null;
		try {
			updateFlag = soalRepository.updateDataSoal(soal.getJenisSoal(),soal.getWaktuPengerjaan(),
					soal.getWaktuPengumpulan(),soal.getIsiSoal(),soal.getNilai(),kodeSoal);
			if(updateFlag==1) {
				result="data soal telah diperbaharui dengan kode soal: "+kodeSoal;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public SoalRepository getSoalRepository() {
		return soalRepository;
	}

	public void setSoalRepository(SoalRepository soalRepository) {
		this.soalRepository = soalRepository;
	}

	public Soal getSoal() {
		return soal;
	}

	public void setSoal(Soal soal) {
		this.soal = soal;
	}





}
