/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaio;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author HIEU
 */
public class ImportSinhVien {

    public static void main(String args[]) throws IOException {
        Map<String, SinhVien> DsSinhVien = new HashMap<String, SinhVien>();
        Map<String, Map> SinhVienLop = new HashMap<String, Map>();
        Map<String, LopHoc> DsLopHoc = new HashMap<String, LopHoc>();
        List<SinhVien> lstSVImport = new ArrayList<SinhVien>();

        List<String> lstValue;
        lstValue = DocFileCSV("LopHoc.csv");
        for (int i = 0; i < lstValue.size(); i += 2) {
            LopHoc lh = new LopHoc();
            lh.MaLop = lstValue.get(i);
            lh.TenLop = lstValue.get(i + 1);
            DsLopHoc.put(lh.MaLop, lh);
        }
        Set<String> set = DsLopHoc.keySet();
        System.out.printf("Chọn lớp học\n");
        for (String key : set) {
            System.out.printf("%s %s\n", DsLopHoc.get(key).MaLop, DsLopHoc.get(key).TenLop);
        }
//        byte data[] = new byte[10];
//        System.in.read(data);
        Scanner scanner = new Scanner(System.in);
        String _maLop;
        _maLop = scanner.next();
        LopHoc lopChon = DsLopHoc.get(_maLop);
        if (lopChon != null) {
            System.out.printf("Bạn đã chọn Lớp %s\n", lopChon.TenLop);
            System.out.printf("Nhập file Import\n");
            String FileImportName;
            FileImportName = scanner.next();
            lstValue = DocFileCSV("SinhVien.csv");
            for (int i = 5; i < lstValue.size(); i += 5) {
                SinhVien sv = new SinhVien();
                sv.MaLop = lstValue.get(i);
                sv.MSSV = lstValue.get(i + 1);
                sv.HoTen = lstValue.get(i + 2);
                sv.GoiTinh = lstValue.get(i + 3);
                sv.CMND = lstValue.get(i + 4);
                if (sv.MaLop.equals(lopChon.MaLop)) {
                    DsSinhVien.put(sv.MSSV, sv);
                }
            }

            lstValue = DocFileCSV(FileImportName);
            for (int i = 5; i < lstValue.size(); i += 5) {
                SinhVien sv = new SinhVien();
                sv.MaLop = lstValue.get(i);
                sv.MSSV = lstValue.get(i + 1);
                sv.HoTen = lstValue.get(i + 2);
                sv.GoiTinh = lstValue.get(i + 3);
                sv.CMND = lstValue.get(i + 4);
                lstSVImport.add(sv);
            }
            for (int i = 0; i < lstSVImport.size(); i++) {
                SinhVien sv = DsSinhVien.get(lstSVImport.get(i).MSSV);
                if (sv != null) {
                    System.out.printf("Sinh vien: %s đã tồn tại \n", sv.MSSV);
                } else {
                    SinhVien svi = lstSVImport.get(i);
                    svi.MaLop = lopChon.MaLop;
                    DsSinhVien.put(svi.MSSV, svi);
                    System.out.printf("Đã thêm sinh viên\n MSSV:%s, Họ tên:%s \n", svi.MSSV, svi.HoTen);
                }
            }
            SaveSinhVien(DsSinhVien);
        } else {
            System.out.printf("Mã Lớp không hợp lệ\n");
            return;
        }
    }

    public static class SinhVien {

        public String MaLop;
        public String MSSV;
        public String HoTen;
        public String GoiTinh;
        public String CMND;
    }

    public static class LopHoc {

        public String MaLop;
        public String TenLop;
    }

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
//        FileWriter fw = new FileWriter("d.txt");
//        fw.write('a');
//        fw.close();
//        return true;
        try {
            File fOut = new File("a1.txt");
            PrintWriter out = new PrintWriter(fOut);

//            out.write("...123");
            out.println("STT,MSSV,Họ tên,Giới tính,CMND");
            Set<String> set = DsSinhVien.keySet();
            for (String key : set) {
                SinhVien sv = DsSinhVien.get(key);
                out.printf("%s,%s,%s,%s,%s\n", sv.MaLop, sv.MSSV,sv.HoTen,sv.GoiTinh,sv.CMND);
            }
//            double d = 1.23;
//            out.println(d);
//            out.printf("%d %c\n", 49, 49);

            out.close();
            return true;
        } catch (IOException ioe) {
            return false;
        }
    }
}
