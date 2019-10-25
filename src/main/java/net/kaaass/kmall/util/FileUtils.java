package net.kaaass.kmall.util;

import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

    public static String readAll(String filename) throws IOException {
        FileReader fr = new FileReader(filename);
        int ch;
        StringBuilder stringBuilder = new StringBuilder();
        while ((ch = fr.read()) != -1) {
            stringBuilder.append((char) ch);
        }
        fr.close();
        return stringBuilder.toString();
    }
}
