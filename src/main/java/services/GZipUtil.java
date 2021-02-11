package services;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtil {

    /**
     *
     * @param data
     * @return  compressed base 64 encoded string
     * @throws IOException
     */
    public static String compressData(String data) throws IOException {
        try(
                ByteArrayOutputStream obj=new ByteArrayOutputStream();
                GZIPOutputStream gzip = new GZIPOutputStream(obj);
        ){
            gzip.write(data.getBytes("UTF-8"));
            gzip.flush();
            gzip.close();
            byte[] inStr = obj.toByteArray();
            return Base64.getEncoder().encodeToString(inStr);
        }catch (IOException io){
            throw new IOException("Error while compressing process data");
        }
    }

    /**
     *
     * @param compressedData
     * @return decompressed original String
     * @throws IOException if input is not in Gzip format
     */
    public static String decompressData(String compressedData) throws IOException {
        try(

                GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(compressedData.getBytes("UTF-8"))));
                BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
        ){
//            String outStr = "";
//            String line;
//            while ((line=bf.readLine())!=null) {
//                outStr += line;
//            }
//            return outStr;
            byte[] bytes = IOUtils.toByteArray(gis);
            return new String(bytes, "UTF-8");
        }catch (IOException io){
            throw new IOException("Error while extracting process data");
        }
    }
}
