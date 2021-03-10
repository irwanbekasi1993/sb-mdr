package sb.mdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sb.mdr.model.Dosen;
import sb.mdr.model.Kelas;
import sb.mdr.model.MataKuliah;
import sb.mdr.repository.DosenRepository;
import sb.mdr.repository.KelasRepository;
import sb.mdr.repository.MataKuliahRepository;

@Service
public class KelasService {

	@Autowired
	private KelasRepository kelasRepository;

	private Kelas kelas;

	public String insertKelas(Kelas localKelas) {
		int increment = 0;
		int flagInsert = 0;
		String result=null;
		String cekKelas = kelasRepository.getLastKodeKelas();
		if(cekKelas==null) {
			increment += 1;
			localKelas.setKodeKelas("DSN" + increment);

		}else {
			increment=kelasRepository.hitungKelas()+1;
			localKelas.setKodeKelas("DSN" + increment);
		}
		try {
			flagInsert = kelasRepository.insertDataKelas(localKelas.getKodeKelas(), localKelas.getNamaKelas(),
					localKelas.getKapasitasKelas(),localKelas.getJenisKelas());

			if(flagInsert==1) {
				result="data kelas berhasil dimasukkan dengan kode kelas: "+cekKelas;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public List<Kelas> getAllKelas(int limit) {
		List<Kelas> listKelas = new ArrayList<>();
		try {
			listKelas = kelasRepository.selectAllKelas(limit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listKelas;
	}
	
	public Kelas getKelas(String kodeMatKul) {
		Kelas kelas = new Kelas();
		try {
			kelas= kelasRepository.getKelasByKodeKelas(kodeMatKul);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return kelas;
	}

	public String deleteKelas(String kodeKelas) {
		int deleteFlag = 0;
		String result=null;
		try {
			deleteFlag = kelasRepository.deleteDataKelas(kodeKelas);
			if(deleteFlag==1) {
				
				result="data kelas dengan kode kelas: "+kodeKelas+" telah dihapus";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateKelas(Kelas kelas,String kodeKelas) {
		int updateFlag = 0;
		String result=null;
		try {
			updateFlag = kelasRepository.updateDataKelas(kelas.getNamaKelas(),kelas.getKapasitasKelas(), 
					kelas.getJenisKelas(),kodeKelas);
			if(updateFlag==1) {
				result="data kelas telah diperbaharui dengan kode kelas: "+kodeKelas;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public KelasRepository getKelasRepository() {
		return kelasRepository;
	}

	public void setKelasRepository(KelasRepository kelasRepository) {
		this.kelasRepository = kelasRepository;
	}

	public Kelas getKelas() {
		return kelas;
	}

	public void setKelas(Kelas kelas) {
		this.kelas = kelas;
	}




}
