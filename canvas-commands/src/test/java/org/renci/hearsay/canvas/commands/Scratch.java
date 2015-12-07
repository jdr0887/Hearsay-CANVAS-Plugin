package org.renci.hearsay.canvas.commands;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;

public class Scratch {

    @Test
    public void scratch() {
        String geneIdLine = "&lt;Gene-track_geneid&gt;285033&lt;/Gene-track_geneid&gt;";
        Pattern p = Pattern.compile("<Gene-track_geneid>(\\d.+)</Gene-track_geneid>");
        String escapedGeneIdLine = StringEscapeUtils.unescapeHtml4(geneIdLine);
        Matcher m = p.matcher(escapedGeneIdLine);
        m.find();
        if (m.matches()) {
            String geneId = m.group(1);
            System.out.println(geneId);
        }
    }

    @Test
    public void sort() {
        List<Integer> starts = new ArrayList<Integer>();
        starts.addAll(Arrays.asList(1, 2, 3, 4, 5));
        starts.sort((a, b) -> a.compareTo(b));
        System.out.println(starts.get(0));

        List<Integer> stops = new ArrayList<Integer>();
        stops.addAll(Arrays.asList(1, 2, 3, 4, 5));
        stops.sort((a, b) -> b.compareTo(a));
        System.out.println(stops.get(0));
    }

    @Test
    public void parseGenomeRefValue() {

        List<String> genomeRefValues = Arrays.asList("BUILD.36.1", "BUILD.37.2");

        for (String genomeRefValue : genomeRefValues) {
            genomeRefValue = genomeRefValue.replace("BUILD.", "GRCh");

            if (!genomeRefValue.substring(genomeRefValue.indexOf('.'), genomeRefValue.length()).equals(".1")) {
                genomeRefValue = genomeRefValue.replace(".", ".p");
            }

            if (genomeRefValue.substring(genomeRefValue.indexOf('.'), genomeRefValue.length()).equals(".1")) {
                genomeRefValue = genomeRefValue.replace(".1", "");
            }

            System.out.println(genomeRefValue);
        }

    }

    @Test
    public void test() {
        try {
            String url = String.format("http://www.ncbi.nlm.nih.gov/gene/?term=%s&report=xml&format=text", "LOC285033");
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();
            System.out.printf("responsecode: %s%n", responseCode);

            List<String> lines = IOUtils.readLines(con.getInputStream());
            String geneIdLine = lines.get(6);
            Pattern p = Pattern.compile("<Gene-track_geneid>(\\d.+)</Gene-track_geneid>");
            String escapedGeneIdLine = StringEscapeUtils.unescapeHtml4(geneIdLine).trim();
            Matcher m = p.matcher(escapedGeneIdLine);
            m.find();
            if (m.matches()) {
                String geneId = m.group(1);
                System.out.println(geneId);
            }

            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
