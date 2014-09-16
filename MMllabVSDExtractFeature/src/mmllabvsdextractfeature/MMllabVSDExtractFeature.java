/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mmllabvsdextractfeature;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;

/**
 *
 * @author tiendv
 */
public class MMllabVSDExtractFeature {
    
    static String UNTARSHFILE ="home/mmlab/NetBeansProjects/trunk/MMllabVSDExtractFeature/src/mmllabvsdextractfeature/untarfolder.sh";
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        MMllabVSDExtractFeature hello = new MMllabVSDExtractFeature();
        FileStructer metdata = new FileStructer();
        Utility utilityClass = new Utility();

        /*
            Flow :
        
        1. Read  metdata file
        2. Untar folder:
                -1 Shot content 25 frame
                -1 folder 50 shot
        Process with 1 shot:
                - Resize All image 1 shot --> delete orginal.
                - Extract feature 25 frame
                - Pooling Max and aveg.
                - Delete Image, feature
                - zip file Feature 
        3. Delete File.
                
        */
        
        // Load metadata File
        String dir="";
        String metadataFileDir = "";
        ArrayList<FileStructer> listFileFromMetadata = metdata.readMetadata(metadataFileDir);
        
        // process with all Folder in metdata file
        
        for (int i =0; i< listFileFromMetadata.size();i++){
            //Load with 1 zip file
            String nameZipFile = dir+"/"+listFileFromMetadata.get(i).getNameZipFileName()+".tar";
            String folderName = listFileFromMetadata.get(i).getNameZipFileName();  
            // Unzip tarFolder
            utilityClass.unTarFolder(UNTARSHFILE, nameZipFile, dir);
            
        }
        
    }
    
    Void resizeImage (String imageName, String saveDir, int targetWidth, int targetHeight ) throws IOException{
        
        BufferedImage img = ImageIO.read(new File(imageName));
        BufferedImage scaledImg = Scalr.resize(img, Mode.AUTOMATIC, targetWidth, targetHeight);
        File destFile = new File(saveDir+imageName+".jpg");
        ImageIO.write(scaledImg, "jpg", destFile);
        System.out.println("Done resizing");
        return null;
    }
   
    /**
     * 
     * @param tarScriptShFile
     * @param folderForTar
     * @param dirtoSave
     * @return
     * @throws IOException 
     */
       Boolean zipFolder (String tarScriptShFile, String folderForZip, String dirtoSave) throws IOException{
           //ProcessBuilder pb = new ProcessBuilder("/home/tiendv/NetBeansProjects/trunk/MMllabVSDExtractFeature/src/mmllabvsdextractfeature/unzip.sh","/home/tiendv/Downloads/tiendv.tar","/home/tiendv");
           Boolean result =false;
           ProcessBuilder pb = new ProcessBuilder(tarScriptShFile,folderForZip,dirtoSave);
           Process process = pb.start();   
           BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
           String line = null;
           while ((line = reader.readLine()) != null)
           {
              System.out.println(line);
           }
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
}
