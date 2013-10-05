package mrq.plugin.minecraft.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class L {
    private static final L l = new L();
    public static final L getInstance() {
        return l;
    }
    
    private File file = null;
    private BufferedWriter bw = null;
    
    private L() {
        file = new File(TimeStamp.getTimeStamp() + ".log");
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void og(Object... o) {
        if (bw != null) {
            try {
                StringBuilder sb = new StringBuilder();
                for (int i=0; i<o.length; i++) {
                    sb.append(" " + o[i]);
                }
                
                bw.write(TimeStamp.getTimeStamp(false) + sb.toString());
                bw.newLine();
                bw.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public void close() {
        if (bw != null) {
            try {
                bw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}