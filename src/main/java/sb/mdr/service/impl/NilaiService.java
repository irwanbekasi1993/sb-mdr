package sb.mdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sb.mdr.model.Dosen;
import sb.mdr.model.MataKuliah;
import sb.mdr.model.Nilai;
import sb.mdr.model.Semester;
import sb.mdr.repository.DosenRepository;
import sb.mdr.repository.MataKuliahRepository;
import sb.mdr.repository.NilaiRepository;
import sb.mdr.repository.SemesterRepository;

@Service
public class NilaiService {

	@Autowired
	private NilaiRepository nilaiRepository;

	private Nilai nilai;

	public String insertNilai(Nilai localNilai) {
		int increment = 0;
		int flagInsert = 0;
		String result=null;
		String cekNilai = nilaiRepository.getLastKodeNilai();
		if(cekNilai==null) {
			increment += 1;
			localNilai.setKodeNilai("DSN" + increment);

		}else {
			increment=nilaiRepository.hitungNilai()+1;
			localNilai.setKodeNilai("DSN" + increment);
		}
		try {
			flagInsert = nilaiRepository.insertDataNilai(localNilai.getKodeNilai(), localNilai.getJenisNilai(),
					localNilai.getPoin());

			if(flagInsert==1) {
				result="data semester berhasil dimasukkan dengan kode nilai: "+cekNilai;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public List<Nilai> getAllNilai(int limit) {
		List<Nilai> listNilai = new ArrayList<>();
		try {
			listNilai = nilaiRepository.selectAllNilai(limit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listNilai;
	}
	
	public Nilai getNilai(String kodeNilai) {
		Nilai nilai = new Nilai();
		try {
			nilai= nilaiRepository.getNilaiByKodeNilai(kodeNilai);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return nilai;
	}

	public String deleteNilai(String kodeNilai) {
		int deleteFlag = 0;
		String result=null;
		try {
			deleteFlag = nilaiRepository.deleteDataNilai(kodeNilai);
			if(deleteFlag==1) {
				
				result="data nilai dengan kode nilai: "+kodeNilai+" telah dihapus";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateNilai(Nilai nilai,String kodeNilai) {
		int updateFlag = 0;
		String result=null;
		try {
			updateFlag = nilaiRepository.updateDataNilai(nilai.getJenisNilai(),nilai.getPoin(),kodeNilai);
			if(updateFlag==1) {
				result="data nilai telah diperbaharui dengan kode nilai: "+kodeNilai;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public NilaiRepository getNilaiRepository() {
		return nilaiRepository;
	}

	public void setNilaiRepository(NilaiRepository nilaiRepository) {
		this.nilaiRepository = nilaiRepository;
	}

	public Nilai getNilai() {
		return nilai;
	}

	public void setNilai(Nilai nilai) {
		this.nilai = nilai;
	}

	



}
