package mp9.uf3.urls;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;



public class TestApunts {

    private static void printContent(URL url){
        InputStream in;
        char[] cbuf = new char[512];
        int caractersLlegits;


        try {
            in = url.openStream();
            InputStreamReader inr = new InputStreamReader(in);
            while((caractersLlegits=inr.read(cbuf))!=-1){
                String str = String.copyValueOf(cbuf, 0, caractersLlegits);
                System.out.print(str);
            }
            System.out.println();
        } catch (IOException ex) {
            Logger.getLogger(TestApunts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            TestApunts.printContent(new URL("https://elpuig.xeill.net"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
