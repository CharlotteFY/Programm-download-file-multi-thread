import java.io.BufferedInputStream;
import java.io.File;
//import java.io.*; 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
import java.net.URL;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.nio.channels.*;
import java.util.concurrent.TimeUnit;

public class Downloader {
    private ArrayList<Thread> threads;
    long size = 0;
    String name;

    int amountThread;
    int buffSize = 5000;

    public Downloader(int amountThread, String url) {
        this.name = url;
        threads = new ArrayList<Thread>();
        this.amountThread = amountThread;
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            size = conn.getContentLengthLong();
        } catch (IOException e) {
            return;
        } finally {
            conn.disconnect();
        }
        long partFile = (size / amountThread);
        long remaining = (size % amountThread);
        for (int i = 0; i < amountThread; ++i) {
            long start = (i == 0 ? 0 : partFile * i),
                    end = (i + 1 >= amountThread ? (partFile * (i + 1)) + remaining : partFile * (i + 1));
            threads.add(new Downloader_thread("tmp " + i, url, end - 1, start));
            /*
             * if (amountThread == i) { long start = (partFile * (i - 1)); long end =
             * ((partFile * i)); threads.add(new Downloader_thread("tmp " + i, url, end,
             * start)); } else { long start = (partFile * (i - 1)); long end = ((partFile *
             * i)); threads.add(new Downloader_thread("tmp " + i, url, end, start)); }
             */threads.get(i).start();
        }
    }

    public void build(String filnalname) {
        System.out.print("NNN");
        new Thread(new Runnable(){

            @Override
            public void run() {
                // TODO Auto-generated method stub
                 checkFile(filnalname);
            }
        }).start();
    }

    public void checkFile(String filnalname) {
        for (int i = 0; i < amountThread; ++i) {
            if (((Downloader_thread) threads.get(i)).downloadFinished() != true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    build(filnalname);
                    return ;
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }
        }
        File[] files = new File[amountThread];
        for (int j = 0; j < amountThread; ++j) {
            files[j] = new File(((Downloader_thread) threads.get(j)).getname());
            System.out.println(j+files[j].getAbsolutePath());
        }
        try {
            buildFiles(files, filnalname);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void buildFiles(File[] files, String name) throws IOException {
        //System.out.println(files.length+name);
        FileOutputStream fileOutputStream = new FileOutputStream(name);
        byte dataBuffer[] = new byte[this.buffSize];
        int bytesRead;
        try{
            for (File f : files) {
                //f.delete();
            FileInputStream  in = new FileInputStream(f);
              if(f.exists()==false) return;
            BufferedInputStream Buffer = new BufferedInputStream(in);
            while ((bytesRead = Buffer.read(dataBuffer, 0, this.buffSize)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            Buffer.close();
            f.deleteOnExit();
            }
            } catch(Exception e) {
               e.printStackTrace();
            }
    }
    public boolean getdownloadFinished(){
        return false;
    }
}
