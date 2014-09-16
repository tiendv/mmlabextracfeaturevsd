/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mmllabvsdextractfeature;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author tiendv
 */
public class Utility {
    
    
    public  String[] SplitUsingTokenizer(String subject, String delimiters) {
    StringTokenizer strTkn = new StringTokenizer(subject, delimiters);
    ArrayList<String> arrLis = new ArrayList<String>(subject.length());
    while(strTkn.hasMoreTokens())
       arrLis.add(strTkn.nextToken());

    return arrLis.toArray(new String[0]);
 }
     /**
     * 
     * @param unTarScriptShFile
     * @param folderForUnTar
     * @param dirtoSave
     * @return
     * @throws IOException 
     */
    Boolean unTarFolder (String unTarScriptShFile, String folderForUnTar, String dirtoSave) throws IOException{
           //ProcessBuilder pb = new ProcessBuilder("/home/tiendv/NetBeansProjects/trunk/MMllabVSDExtractFeature/src/mmllabvsdextractfeature/unzip.sh","/home/tiendv/Downloads/tiendv.tar","/home/tiendv");
           Boolean result =false;
           ProcessBuilder pb = new ProcessBuilder(unTarScriptShFile,folderForUnTar,dirtoSave);
           Process process = pb.start();   
           BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
           String line = null;
           while ((line = reader.readLine()) != null)
           {
              System.out.println(line);
           }
             return result;

       }
    /**
     * 
     * @param pathFolder
     * @return list file in Folder
     */
       File[] listAllFileInFolder (String pathFolder) {
           File folder = new File(pathFolder);
           File[] listOfFiles = folder.listFiles();
                for (int i = 0; i < listOfFiles.length; i++) {
                  if (listOfFiles[i].isFile()) {
                    System.out.println("File " + listOfFiles[i].getName());
                  } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory " + listOfFiles[i].getName());
                  }
                }
        return listOfFiles;
       }
    
}
