package sbmdr.payload.request;

public class JadwalRequest {
private String nip;
private String kodeJurusan;
private String kodeKelas;
private String kodeMatkul;
private String nim;


public int getSemester() {
    return semester;
}
public void setSemester(int semester) {
    this.semester = semester;
}
public String getKodeSemester() {
    return kodeSemester;
}
public void setKodeSemester(String kodeSemester) {
    this.kodeSemester = kodeSemester;
}
private int semester;
private String kodeSemester;
public JadwalRequest() {
}
public JadwalRequest(String nip, String kodeJurusan, String kodeKelas, String kodeMatkul,int semester, String kodeSemester) {
    this.nip = nip;
    this.kodeJurusan = kodeJurusan;
    this.kodeKelas = kodeKelas;
    this.kodeMatkul = kodeMatkul;
    this.semester=semester;
    this.kodeSemester=kodeSemester;
}

public String getNip() {
    return nip;
}
public void setNip(String nip) {
    this.nip = nip;
}
public String getKodeJurusan() {
    return kodeJurusan;
}
public void setKodeJurusan(String kodeJurusan) {
    this.kodeJurusan = kodeJurusan;
}
public String getKodeKelas() {
    return kodeKelas;
}
public void setKodeKelas(String kodeKelas) {
    this.kodeKelas = kodeKelas;
}
public String getKodeMatkul() {
    return kodeMatkul;
}
public void setKodeMatkul(String kodeMatkul) {
    this.kodeMatkul = kodeMatkul;
}
public String getNim() {
    return nim;
}
public void setNim(String nim) {
    this.nim = nim;
}


}
