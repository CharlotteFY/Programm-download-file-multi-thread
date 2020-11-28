//import java.net.URL;;

/**
 *
 * @author Behzad
 */
public class DownloadManager {
    public static void main(String[] args) {
        int amountthread = 10;
        String url = "http://localhost/Silky%20Smooth%20Thai%20Milk%20Tea%20Cr%c3%a8me%20Caramel%20-%20%e3%83%97%e3%83%aa%e3%83%b3%20-%20Flan.mp4";
        url= "http://192.168.137.1:56982/ubuntu-20.04.1-desktop-amd64.iso";
        Downloader dm = new Downloader(amountthread,url);
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        dm.build(fileName);
    }
}