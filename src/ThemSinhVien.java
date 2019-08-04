import java.io.*;
import java.util.*;


public class ThemSinhVien {
    public static void main(String args[]) throws IOException {
        List<String> lstValue;
        Map<String, SinhVien> DsSinhVien = new HashMap<String, SinhVien>();
        Map<String, LopHoc> DsLopHoc = new HashMap<String, LopHoc>();

        //doc DS sinh vien
        lstValue = DocFileCSV("SinhVien.csv");
        for (int i = 5; i < lstValue.size(); i += 5) {
            SinhVien sv = new SinhVien();
            sv.MaLop = lstValue.get(i);
            sv.MSSV = lstValue.get(i + 1);
            sv.HoTen = lstValue.get(i + 2);
            sv.GoiTinh = lstValue.get(i + 3);
            sv.CMND = lstValue.get(i + 4);
            DsSinhVien.put(sv.MSSV, sv);
        }
        //doc DS lop hoc
        lstValue = DocFileCSV("LopHoc.csv");
        for (int i = 0; i < lstValue.size(); i += 2) {
            LopHoc lh = new LopHoc();
            lh.MaLop = lstValue.get(i);
            lh.TenLop = lstValue.get(i + 1);
            DsLopHoc.put(lh.MaLop, lh);
        }
        BufferedReader br = new BufferedReader(
                                new InputStreamReader(System.in, "UTF-8"));
        Scanner scanner = new Scanner(new InputStreamReader(System.in, "UTF-8"));
        String _maLop, _mssv, _hoTen, _goiTinh, _cmnd;
        System.out.printf("Nhập mã lớp\n");
        _maLop = scanner.nextLine();
        System.out.printf("Nhập mã số sinh viên\n");
        _mssv = br.readLine();
        System.out.printf("Nhập họ tên\n");
        _hoTen = br.readLine();
        System.out.printf("Nhập giới tính\n");
        _goiTinh = scanner.nextLine();
        System.out.printf("Nhập CMND\n");
        _cmnd = br.readLine();
        //validate
        LopHoc lh = DsLopHoc.get(_maLop);
        if(lh == null){
            System.out.printf("Mã lớp không hợp lệ\n");
            return;
        }
        SinhVien svvld = DsSinhVien.get(_mssv);
        if(svvld != null){
            System.out.printf("Mã sinh viên đã tồn tại\n");
            return;
        }
        SinhVien sv = new SinhVien();
        sv.MaLop = _maLop;
        sv.MSSV = _mssv;
        sv.HoTen = _hoTen;
        sv.GoiTinh = _goiTinh;
        sv.CMND = _cmnd;
        DsSinhVien.put(sv.MSSV, sv);
        SaveSinhVien(DsSinhVien);
    }

//    public static class SinhVien {
//
//        public String MaLop;
//        public String MSSV;
//        public String HoTen;
//        public String GoiTinh;
//        public String CMND;
//    }
//
//    public static class LopHoc {
//
//        public String MaLop;
//        public String TenLop;
//    }

    public static List<String> DocFileCSV(String FileName) throws IOException {
        List<String> lstValue = new ArrayList<String>();
        FileReader fr = new FileReader(FileName);
        String value = "";
        int ind = 1;
        while (true) {
            int i = fr.read();
            if (i == -1) {
                lstValue.add(value);
                break;
            }
            char ch = (char) i;
            if ((char) i == ',') {
                lstValue.add(value);
                value = "";
                ind++;
            } else if ((char) i == '\n') {
                lstValue.add(value);
                value = "";
                ind = 1;
            } else {
                value += (char) i;
            }
        }
        fr.close();
        return lstValue;
    }

    public static boolean SaveSinhVien(Map<String, SinhVien> DsSinhVien) throws IOException {
        try {
            File fOut = new File("a1.txt");
            PrintWriter out = new PrintWriter(fOut);

            out.println("STT,MSSV,Họ tên,Giới tính,CMND");
            Set<String> set = DsSinhVien.keySet();
            for (String key : set) {
                SinhVien sv = DsSinhVien.get(key);
                out.printf("%s,%s,%s,%s,%s\n", sv.MaLop, sv.MSSV, sv.HoTen, sv.GoiTinh, sv.CMND);
            }

            out.close();
            return true;
        } catch (IOException ioe) {
            return false;
        }
    }
}
