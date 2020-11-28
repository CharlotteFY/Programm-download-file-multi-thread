
import java.io.IOException;
//import java.net.MalformedURLException;
import java.net.URL;
//import java.nio.channels.Channels;
//import java.nio.channels.ReadableByteChannel;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.*;
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.URLConnection;
import java.io.BufferedInputStream;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.concurrent.TimeUnit;
public class Downloader_thread extends Thread {
    private String name, url;
    private long size;
    private long start;
    private int buffSize;
    private boolean downloadFinished;

    public Downloader_thread(String name,String url,long end,long start) {
        this.size = end;
        this.start = start;
        this.name = name;
        this.url = url;
        this.buffSize = 5000;
    }
    @Override
    public void run() {
        try {
        HttpURLConnection urlc = (HttpURLConnection) (new URL(this.url).openConnection());
        urlc.setRequestMethod("GET");
        urlc.setRequestProperty("User-Agent", "http_download");
        urlc.setRequestProperty("Range", "bytes=" + this.start + '-' + this.size);
        BufferedInputStream bufferedInputStream = null;
        FileOutputStream fileOutputStream = null;   
        bufferedInputStream = new BufferedInputStream(urlc.getInputStream());

            fileOutputStream = new FileOutputStream(name);
            byte dataBuffer[] = new byte[buffSize];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(dataBuffer, 0, buffSize)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead); 
                sizeRead += bytesRead;  
           }
            this.downloadFinished = true;
            bufferedInputStream.close();
            fileOutputStream.close();
        } catch(IOException e){
            System.out.print("Download_fail");
        }
        }public long sizeRead = 0;

        public long getsizeRead() {
            return sizeRead;
        } 
        public boolean downloadFinished() {
            return downloadFinished;
        }
        public String getname(){
            return name;
        }
}
