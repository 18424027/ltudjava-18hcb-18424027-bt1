/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaio;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HIEU
 */
public class test {

    public static void main(String args[]) throws IOException {
        Map<String, SinhVien> lopHoc = new HashMap<String, SinhVien>();
        List<String> lstValue = new ArrayList<String>();
        FileReader fr = new FileReader("csv.csv");
        String value="";
        int ind = 1;
        while (true) {
            int i = fr.read();
            if (i == -1) {
//                System.out.printf("%d %s\n",ind,value);
                lstValue.add(value);
                break;
            }
            char ch = (char) i;
            if((char)i == ',')
            {
                lstValue.add(value);
//                System.out.printf("%d %s\n",ind,value);
                value="";
                ind++;
            }
            else if((char)i == '\n'){
                lstValue.add(value);
//                System.out.printf("%d %s\n",ind,value);
                value="";
                ind=1;
            }
            else{
                value+=(char) i;
            }
//            System.out.print(ch);
        }
        fr.close();
        
        for(int i=0;i<lstValue.size();i+=5){
//            System.out.printf("MSSV %s\n",lstValue.get(i+1));
//            System.out.printf("MSSV %s\n",lstValue.get(i+1));
//            System.out.printf("Họ tên %s\n",lstValue.get(i+2));
//            System.out.printf("Giới tính %s\n",lstValue.get(i+3));
//            System.out.printf("CMND %s\n",lstValue.get(i+4));
                SinhVien sv = new SinhVien();
                sv.MSSV = lstValue.get(i+1);
                sv.HoTen = lstValue.get(i+2);
                lopHoc.put(sv.MSSV, sv);
        }
        System.out.printf("%s\n",lopHoc.get("1742001").HoTen);
    }
    public static class SinhVien{
        public String MSSV; 
        public String HoTen;
    }
}

