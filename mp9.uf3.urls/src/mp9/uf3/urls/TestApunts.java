package mp9.uf3.urls;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestApunts {

    private static void testA(URL url, String tag) {
        InputStream in;
        BufferedReader reader;
        String line;

        try {
            in = url.openStream();
            reader = new BufferedReader(new InputStreamReader(in));
            while ((line = reader.readLine()) != null) {
                if (line.contains(tag)) {
                    System.out.println(line);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TestApunts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void testB(String respuesta1, String respuesta2) throws IOException {
        URL url = new URL("https://docs.google.com/forms/d/e/1FAIpQLSdV5QvhChK0fBpAMo5pN7sIvktqwHGu1vdoWJFvBguCeMvYUw/formResponse");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
        out.write("entry.835030737=" + respuesta1 + "&entry.1616686619=" + respuesta2);
        out.close();
        InputStream in = con.getInputStream();
    }

    public static void main(String[] args) throws IOException {
        try {
            TestApunts.testA(new URL("https://elpuig.xeill.net"), "img");
            TestApunts.testB("Pau4", "No");

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
