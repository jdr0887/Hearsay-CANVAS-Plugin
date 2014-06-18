package org.renci.hearsay.canvas;

import java.io.File;
import java.io.RandomAccessFile;

import org.junit.Test;

public class Scratch {

    @Test
    public void seekDetails() throws Exception {
        File f = new File("/home/jdr0887/tmp/canvas", "details.txt");
        RandomAccessFile raf = new RandomAccessFile(f, "r");
        raf.seek(121277646);
        System.out.println(raf.readLine());
        raf.seek(113131871);
        System.out.println(raf.readLine());
        raf.close();
    }
    
}
