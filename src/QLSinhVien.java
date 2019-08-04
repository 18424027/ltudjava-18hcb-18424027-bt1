import java.io.*;
import java.util.*;

public class QLSinhVien {
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		System.out.printf("Chức năng\n");
		System.out.printf("1. Xem danh sách sinh viên\n");
		System.out.printf("2. Xem danh sách lớp\n");
		System.out.printf("3. Thêm sinh viên\n");
		System.out.printf("4. Import danh sách sinh viên\n");
		System.out.printf("5. Thêm sinh viên\n");
		System.out.printf("6. Thêm thời khóa biểu\n");
		System.out.printf("7. Hủy môn cho sinh viên\n");
		System.out.printf("8. Thêm môn cho sinh viên\n");
		System.out.printf("0. Exit\n");
		String ChucNang;
		while (true) {
			System.out.printf("Nhập chức năng\n");
			ChucNang = br.readLine();
			if (Integer.parseInt(ChucNang) == 1) {
				XemDSSinhVien();
			} else if (Integer.parseInt(ChucNang) == 2) {
				DSLop();
			} else if (Integer.parseInt(ChucNang) == 3) {
				DSMonHoc();
			}
			else if (Integer.parseInt(ChucNang) == 4) {
				ImportSinhVien();
			} else if (Integer.parseInt(ChucNang) == 5) {
				ThemSinhVien();
			} else if (Integer.parseInt(ChucNang) == 6) {
				ImportThoiKhoaBieu();
			} else if (Integer.parseInt(ChucNang) == 7) {
				HuyMonSV();
			} else if (Integer.parseInt(ChucNang) == 8) {
				ThemMonSV();
			} else if (Integer.parseInt(ChucNang) == 0) {
				break;
			} else {

			}
		}
	}

	public static void XemDSSinhVien() throws IOException {
		List<String> lstValue;
		Map<String, SinhVien> DsSinhVien = new HashMap<String, SinhVien>();
		Map<String, LopHoc> DsLopHoc = new HashMap<String, LopHoc>();

		// doc DS sinh vien
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
		// doc DS lop hoc
		lstValue = DocFileCSV("LopHoc.csv");
		for (int i = 0; i < lstValue.size(); i += 2) {
			LopHoc lh = new LopHoc();
			lh.MaLop = lstValue.get(i);
			lh.TenLop = lstValue.get(i + 1).replace('\r', ' ');
			DsLopHoc.put(lh.MaLop, lh);
		}

		System.out.printf("Lớp, MSSV, Họ tên, Giới tính, CMND\n");
		Set<String> set = DsSinhVien.keySet();
		for (String key : set) {
			SinhVien sv = DsSinhVien.get(key);
			LopHoc lh = DsLopHoc.get(sv.MaLop);
			System.out.printf("%s,%s,%s,%s,%s\n", lh.TenLop, sv.MSSV, sv.HoTen, sv.GoiTinh, sv.CMND);
		}
	}

	public static void DSLop() throws IOException {
		List<String> lstValue;
		Map<String, LopHoc> DsLopHoc = new HashMap<String, LopHoc>();
		// doc DS lop hoc
		lstValue = DocFileCSV("LopHoc.csv");
		for (int i = 0; i < lstValue.size(); i += 2) {
			LopHoc lh = new LopHoc();
			lh.MaLop = lstValue.get(i);
			lh.TenLop = lstValue.get(i + 1).replace('\r', ' ');
			DsLopHoc.put(lh.MaLop, lh);
		}

		System.out.printf("Mã lớp, tên lớp\n");
		Set<String> set = DsLopHoc.keySet();
		for (String key : set) {
			LopHoc lh = DsLopHoc.get(key);
			System.out.printf("%s,%s,\n", lh.MaLop, lh.TenLop);
		}
	}

	public static void DSMonHoc() throws IOException {
		List<String> lstValue;
		Map<String, MonHoc> DsMonHoc = new HashMap<String, MonHoc>();
		// doc DS Mon Hoc
		lstValue = DocFileCSV("MonHoc.csv");
		for (int i = 0; i < lstValue.size(); i += 2) {
			MonHoc mh = new MonHoc();
			mh.MaMonHoc = lstValue.get(i);
			mh.TenMonHoc = lstValue.get(i + 1).replace('\r', ' ');
			DsMonHoc.put(mh.MaMonHoc, mh);
		}

		System.out.printf("Mã môn học, Tên môn học\n");
		Set<String> set = DsMonHoc.keySet();
		for (String key : set) {
			MonHoc mh = DsMonHoc.get(key);
			System.out.printf("%s,%s\n", mh.MaMonHoc, mh.TenMonHoc);
		}
	}

	public static void ImportSinhVien() throws IOException {
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
//                if (sv.MaLop.equals(lopChon.MaLop)) {
				DsSinhVien.put(sv.MSSV, sv);
//                }
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

	public static void ThemSinhVien() throws IOException {
		List<String> lstValue;
		Map<String, SinhVien> DsSinhVien = new HashMap<String, SinhVien>();
		Map<String, LopHoc> DsLopHoc = new HashMap<String, LopHoc>();

		// doc DS sinh vien
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
		// doc DS lop hoc
		lstValue = DocFileCSV("LopHoc.csv");
		for (int i = 0; i < lstValue.size(); i += 2) {
			LopHoc lh = new LopHoc();
			lh.MaLop = lstValue.get(i);
			lh.TenLop = lstValue.get(i + 1);
			DsLopHoc.put(lh.MaLop, lh);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		Scanner scanner = new Scanner(new InputStreamReader(System.in, "UTF-8"));
		String _maLop, _mssv, _hoTen, _goiTinh, _cmnd;
		System.out.printf("Nhập mã lớp\n");
		_maLop = br.readLine();
		System.out.printf("Nhập mã số sinh viên\n");
		_mssv = br.readLine();
		System.out.printf("Nhập họ tên\n");
		_hoTen = br.readLine();
		System.out.printf("Nhập giới tính\n");
		_goiTinh = scanner.nextLine();
		System.out.printf("Nhập CMND\n");
		_cmnd = br.readLine();
		// validate
		LopHoc lh = DsLopHoc.get(_maLop);
		if (lh == null) {
			System.out.printf("Mã lớp không hợp lệ\n");
			return;
		}
		SinhVien svvld = DsSinhVien.get(_mssv);
		if (svvld != null) {
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

	public static void ImportThoiKhoaBieu() throws IOException {
		Map<String, SinhVien> DsSinhVien = new HashMap<String, SinhVien>();
		Map<String, Map> SinhVienLop = new HashMap<String, Map>();
		Map<String, LopHoc> DsLopHoc = new HashMap<String, LopHoc>();
		Map<String, MH_SV> DsMH_SV = new HashMap<String, MH_SV>();
		List<MH_SV> lstSVImport = new ArrayList<MH_SV>();

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
			lstValue = DocFileCSV("MonHoc_SinhVien.csv");
			for (int i = 0; i < lstValue.size(); i += 6) {
				MH_SV mhsv = new MH_SV();
				mhsv.MaMonHoc = lstValue.get(i);
				mhsv.MSSV = lstValue.get(i + 1);
				mhsv.DiemGK = Float.parseFloat(lstValue.get(i + 2));
				mhsv.DiemCK = Float.parseFloat(lstValue.get(i + 3));
				mhsv.DiemKhac = Float.parseFloat(lstValue.get(i + 4));
				mhsv.DiemTong = Float.parseFloat(lstValue.get(i + 4));
				DsMH_SV.put(mhsv.MaMonHoc + mhsv.MSSV, mhsv);
			}

			lstValue = DocFileCSV(FileImportName);
			Set<String> setkeySV = DsSinhVien.keySet();
			for (String keySV : setkeySV) {
				SinhVien sv = DsSinhVien.get(keySV);
				for (int i = 2; i < lstValue.size(); i += 2) {
					MH_SV mhsv = new MH_SV();
					mhsv.MaMonHoc = lstValue.get(i);
					mhsv.MSSV = sv.MSSV;
					mhsv.DiemGK = 0;
					mhsv.DiemCK = 0;
					mhsv.DiemKhac = 0;
					mhsv.DiemTong = 0;
					lstSVImport.add(mhsv);
				}
			}
			for (int i = 0; i < lstSVImport.size(); i++) {
				MH_SV mhsv = DsMH_SV.get(lstSVImport.get(i).MaMonHoc + lstSVImport.get(i).MSSV);
				if (mhsv != null) {
//					System.out.printf("Sinh vien: %s đã tồn tại \n", mhsv.MSSV);
				} else {
					MH_SV mhsvi = lstSVImport.get(i);
					DsMH_SV.put(mhsvi.MaMonHoc + mhsvi.MSSV, mhsvi);
//					System.out.printf("Đã thêm sinh viên\n MSSV:%s, Họ tên:%s \n", mhsvi.MSSV, mhsvi.MaMonHoc);
				}
			}
			if(SaveMH_SV(DsMH_SV)) {
				System.out.printf("Thêm thời khóa biểu thành cônh\n");
			}
		} else {
			System.out.printf("Mã Lớp không hợp lệ\n");
			return;
		}
	}
	public static void HuyMonSV() throws IOException{
		Map<String, MH_SV> DsMH_SV = new HashMap<String, MH_SV>();
		List<String> lstValue;
		lstValue = DocFileCSV("MonHoc_SinhVien.csv");
		for (int i = 0; i < lstValue.size(); i += 6) {
			MH_SV mhsv = new MH_SV();
			mhsv.MaMonHoc = lstValue.get(i);
			mhsv.MSSV = lstValue.get(i + 1);
			mhsv.DiemGK = Float.parseFloat(lstValue.get(i + 2));
			mhsv.DiemCK = Float.parseFloat(lstValue.get(i + 3));
			mhsv.DiemKhac = Float.parseFloat(lstValue.get(i + 4));
			mhsv.DiemTong = Float.parseFloat(lstValue.get(i + 4));
			DsMH_SV.put(mhsv.MaMonHoc + mhsv.MSSV, mhsv);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		String _mamon, _mssv;
		System.out.printf("Nhập MSSV\n");
		_mssv = br.readLine();
		System.out.printf("Nhập mã môn học\n");
		_mamon = br.readLine();
		
		String k = _mamon+_mssv;
		MH_SV mhsv = DsMH_SV.get(k);
		if(mhsv == null) {
			System.out.printf("Sinh viên %s không học môn %s\n", _mssv, _mamon);
		}
		else {
			DsMH_SV.remove(_mamon+_mssv);
			if(SaveMH_SV(DsMH_SV)) {
				System.out.printf("Hủy môn cho sinh viên thành công\n");
			}
		}
	}
	public static void ThemMonSV() throws IOException{
		Map<String, MH_SV> DsMH_SV = new HashMap<String, MH_SV>();
		List<String> lstValue;
		lstValue = DocFileCSV("MonHoc_SinhVien.csv");
		for (int i = 0; i < lstValue.size(); i += 6) {
			MH_SV mhsv = new MH_SV();
			mhsv.MaMonHoc = lstValue.get(i);
			mhsv.MSSV = lstValue.get(i + 1);
			mhsv.DiemGK = Float.parseFloat(lstValue.get(i + 2));
			mhsv.DiemCK = Float.parseFloat(lstValue.get(i + 3));
			mhsv.DiemKhac = Float.parseFloat(lstValue.get(i + 4));
			mhsv.DiemTong = Float.parseFloat(lstValue.get(i + 4));
			DsMH_SV.put(mhsv.MaMonHoc + mhsv.MSSV, mhsv);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		String _mamon, _mssv;
		System.out.printf("Nhập MSSV\n");
		_mssv = br.readLine();
		System.out.printf("Nhập mã môn học\n");
		_mamon = br.readLine();
		
		String k = _mamon+_mssv;
		MH_SV mhsv = DsMH_SV.get(k);
		if(mhsv != null) {
			System.out.printf("Sinh viên %s đã học môn %s\n", _mssv, _mamon);
		}
		else {
			MH_SV mhsvi = new MH_SV();
			mhsvi.MaMonHoc = _mamon;
			mhsvi.MSSV = _mssv;
			mhsvi.DiemGK = 0;
			mhsvi.DiemCK = 0;
			mhsvi.DiemKhac = 0;
			mhsvi.DiemTong = 0;
			DsMH_SV.put(mhsvi.MaMonHoc + mhsvi.MSSV, mhsvi);
			if(SaveMH_SV(DsMH_SV)) {
				System.out.printf("Thêm môn cho sinh viên thành công\n");
			}
		}
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
		try {
			File fOut = new File("SinhVien.csv");
			PrintWriter out = new PrintWriter(fOut);
			out.println("STT,MSSV,Họ tên,Giới tính,CMND");
			List<SinhVien> dssv = new ArrayList<SinhVien>();
			Set<String> set = DsSinhVien.keySet();
			for (String key : set) {
				SinhVien sv = DsSinhVien.get(key);
//	              out.printf("%s,%s,%s,%s,%s\n", sv.MaLop, sv.MSSV,sv.HoTen,sv.GoiTinh,sv.CMND);
				dssv.add(sv);
			}

			for (int i = 0; i < dssv.size(); i++) {
				SinhVien sv = dssv.get(i);
				if (i == dssv.size() - 1) {
					out.printf("%s,%s,%s,%s,%s", sv.MaLop, sv.MSSV, sv.HoTen, sv.GoiTinh, sv.CMND);
				} else {
					out.printf("%s,%s,%s,%s,%s\n", sv.MaLop, sv.MSSV, sv.HoTen, sv.GoiTinh, sv.CMND);
				}
			}

			out.close();
			return true;
		} catch (IOException ioe) {
			return false;
		}
	}
	public static boolean SaveMH_SV(Map<String, MH_SV> DsMH_SV) throws IOException {
		try {
			File fOut = new File("MonHoc_SinhVien.csv");
			PrintWriter out = new PrintWriter(fOut);
			List<MH_SV> dsmhsv = new ArrayList<MH_SV>();
			Set<String> set = DsMH_SV.keySet();
			for (String key : set) {
				MH_SV mhsv = DsMH_SV.get(key);
				dsmhsv.add(mhsv);
			}

			for (int i = 0; i < dsmhsv.size(); i++) {
				MH_SV mhsv = dsmhsv.get(i);
				if (i == dsmhsv.size() - 1) {
					out.printf("%s,%s,%s,%s,%s,%s", mhsv.MaMonHoc, mhsv.MSSV,mhsv.DiemGK,mhsv.DiemCK,mhsv.DiemKhac,mhsv.DiemTong);
				} else {
					out.printf("%s,%s,%s,%s,%s,%s\n", mhsv.MaMonHoc, mhsv.MSSV,mhsv.DiemGK,mhsv.DiemCK,mhsv.DiemKhac,mhsv.DiemTong);
				}
			}

			out.close();
			return true;
		} catch (IOException ioe) {
			return false;
		}
	}
}
