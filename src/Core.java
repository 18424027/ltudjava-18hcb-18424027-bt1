import java.io.*;
import java.util.*;

public class Core {
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
}
