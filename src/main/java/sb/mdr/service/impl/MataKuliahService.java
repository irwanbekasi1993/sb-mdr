package sb.mdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sb.mdr.model.Dosen;
import sb.mdr.model.MataKuliah;
import sb.mdr.repository.DosenRepository;
import sb.mdr.repository.MataKuliahRepository;

@Service
public class MataKuliahService {

	@Autowired
	private MataKuliahRepository matKulRepository;

	private MataKuliah matkul;

	public String insertMatKul(MataKuliah localMatKul) {
		int increment = 0;
		int flagInsert = 0;
		String result=null;
		String cekMatKul = matKulRepository.getLastKodeMatKul();
		if(cekMatKul==null) {
			increment += 1;
			localMatKul.setKodeMatkul("DSN" + increment);

		}else {
			increment=matKulRepository.hitungMatKul()+1;
			localMatKul.setKodeMatkul("DSN" + increment);
		}
		try {
			flagInsert = matKulRepository.insertDataMatKul(localMatKul.getKodeMatkul(), localMatKul.getNamaMatkul(),
					localMatKul.getSks());

			if(flagInsert==1) {
				result="data matakuliah berhasil dimasukkan dengan kode matakuliah: "+cekMatKul;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public List<MataKuliah> getAllMatKul(int limit) {
		List<MataKuliah> listMatKul = new ArrayList<>();
		try {
			listMatKul = matKulRepository.selectAllMatKul(limit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listMatKul;
	}
	
	public MataKuliah getMatKul(String kodeMatKul) {
		MataKuliah matKul = new MataKuliah();
		try {
			matKul= matKulRepository.getMatkulByKodeMatKul(kodeMatKul);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return matKul;
	}

	public String deleteMatKul(String kodeMatKul) {
		int deleteFlag = 0;
		String result=null;
		try {
			deleteFlag = matKulRepository.deleteDataMatKul(kodeMatKul);
			if(deleteFlag==1) {
				
				result="data mata kuliah dengan kode mata kuliah: "+kodeMatKul+" telah dihapus";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateMatKul(MataKuliah matKul,String kodeMatKul) {
		int updateFlag = 0;
		String result=null;
		try {
			updateFlag = matKulRepository.updateDataMatKul(matKul.getNamaMatkul(),matKul.getSks(), kodeMatKul);
			if(updateFlag==1) {
				result="data mata kuliah telah diperbaharui dengan kode mata kuliah: "+kodeMatKul;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public MataKuliahRepository getMatKulRepository() {
		return matKulRepository;
	}

	public void setMatKulRepository(MataKuliahRepository matKulRepository) {
		this.matKulRepository = matKulRepository;
	}

	public MataKuliah getMatkul() {
		return matkul;
	}

	public void setMatkul(MataKuliah matkul) {
		this.matkul = matkul;
	}



}
