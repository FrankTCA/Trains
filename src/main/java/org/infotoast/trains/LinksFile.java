package org.infotoast.trains;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class LinksFile {
    public static final String saveFile = "minecarts.mclnk";

    public static void save(@NotNull MinecartLinks db) {
        String str = db.toString();
        try {
            compressString(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MinecartLinks load() throws InvalidSaveFileException {
        if (!(new File(saveFile).exists())) {
            return new MinecartLinks();
        }
        String str = null;
        try {
            str = decompress();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (str != null) {
                MinecartLinks lnks = MinecartLinks.getFromUncompressedStringDB(str);
                return lnks;
            } else {
                throw new InvalidSaveFileException();
            }
        }
    }

    private static void compressString(String str) throws IOException{

        BufferedWriter writer = null;

        try{
            File file =  new File(saveFile);
            GZIPOutputStream zip = new GZIPOutputStream(new FileOutputStream(file));

            writer = new BufferedWriter(new OutputStreamWriter(zip, "UTF-8"));

            writer.append(str);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            if(writer != null){
                writer.close();
            }
        }
    }

    private static String decompress() throws IOException {
        BufferedReader reader = null;
        String contents = null;
        try {
            File file = new File(saveFile);
            GZIPInputStream zip = new GZIPInputStream(new FileInputStream(file));

            reader = new BufferedReader(new InputStreamReader(zip, "UTF-8"));
            contents = reader.readLine();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return contents;
    }
}
