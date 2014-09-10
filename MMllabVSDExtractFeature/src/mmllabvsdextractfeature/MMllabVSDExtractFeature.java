/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mmllabvsdextractfeature;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author tiendv
 */
public class MMllabVSDExtractFeature {
    
    Utility utility = new Utility();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        MMllabVSDExtractFeature hello = new MMllabVSDExtractFeature();
       // ArrayList<FileStructer> test=  hello.readMetadata ("C://test.lst");
        ArrayList<String> paraUnzip = new ArrayList<>();
        paraUnzip.add("/home/tiendv/Downloads/tiendv.tar");
        paraUnzip.add("/home/tiendv"); 
        //hello.execSH("/home/tiendv/NetBeansProjects/trunk/MMllabVSDExtractFeature/src/mmllabvsdextractfeature/unzip.sh",paraUnzip);
        ProcessBuilder pb = new ProcessBuilder("/home/tiendv/NetBeansProjects/trunk/MMllabVSDExtractFeature/src/mmllabvsdextractfeature/unzip.sh","/home/tiendv/Downloads/tiendv.tar","/home/tiendv");
        Process p = pb.start();   
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        while ((line = reader.readLine()) != null)
        {
           System.out.println(line);
        }
    }
    void unZipFolder (String filenname, String outputDir){
        
    }
   
    
    /**
     * 
     * @param metadataFile: duong dan den 1 file metadata
     * @return : tra ve doi tuong chua ten folder va 
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public ArrayList<FileStructer> readMetadata (String metadataFile) throws FileNotFoundException, IOException {
            ArrayList<FileStructer> result = new ArrayList<>();
                try (FileInputStream inputStream = new FileInputStream(metadataFile)) {
                Scanner scanner = new Scanner(inputStream);
                ArrayList<String> line = new ArrayList<>();
                while(scanner.hasNextLine()){
                 line.add(scanner.nextLine());
               // System.out.println(scanner.nextLine());
                }
                for(int i=0; i<line.size(); i++){
                    FileStructer temp = new FileStructer();
                    String[] parts =  utility.SplitUsingTokenizer(line.get(i), "#$#");
                    temp.filmNameFrame = parts[0];
                    temp.folderName = parts[2];
                    result.add(temp);
                }
           }
           System.out.println(result.size());
        return result;
    }
    
    public void execPHP(String scriptName, String param) throws IOException {
        
      String line;
      StringBuilder output = new StringBuilder();
      Process p = Runtime.getRuntime().exec("php " + scriptName + " " + param);

  }
    public  String[] SplitUsingTokenizer(String subject, String delimiters) {
        StringTokenizer strTkn = new StringTokenizer(subject, delimiters);
        ArrayList<String> arrLis = new ArrayList<String>(subject.length());
        while(strTkn.hasMoreTokens())
           arrLis.add(strTkn.nextToken());

        return arrLis.toArray(new String[0]);
    }
    /**
     *  excute script file in Linux from script source and parameter
     * @param scriptName: source Script
     * @param listparam : list parameter
     * @throws IOException 
     */
     void execSH(String scriptName, ArrayList<String> listparam) throws IOException {
        String fullScript = scriptName + " ";
        for(int i=0; i<listparam.size();i++){
            fullScript+= " " + listparam.get(i);
        }
        ProcessBuilder pb = new ProcessBuilder(fullScript);
        Process p = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        while ((line = reader.readLine()) != null)
        {
           System.out.println(line);
        }
     }

}
