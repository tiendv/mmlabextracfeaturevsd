/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mmllabvsdextractfeature;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;

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
     * @param extractfeatureSHFile
     * @param gpuID
     * @param cudapath
     * @param imageForExtract
     * @param resultFeatureExtraction
     * @throws IOException 
     */
     void excuteFeatureExtractionAnImage (String extractfeatureSHFile,String gpuID, String cudapath, String imageForExtract, String resultFeatureExtraction) throws IOException{
           //ProcessBuilder pb = new ProcessBuilder("/home/tiendv/NetBeansProjects/trunk/MMllabVSDExtractFeature/src/mmllabvsdextractfeature/unzip.sh","/home/tiendv/Downloads/tiendv.tar","/home/tiendv");
           ProcessBuilder pb = new ProcessBuilder(extractfeatureSHFile,gpuID,cudapath,imageForExtract,resultFeatureExtraction);
           Process process = pb.start();   
           BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
           String line = null;
           while ((line = reader.readLine()) != null)
           {
              System.out.println(line);
           }
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
       /**
        * 
        * @param imageName
        * @param saveDir
        * @param targetWidth
        * @param targetHeight
        * @throws IOException 
        */
        void resizeImage (String imageName, String saveDir, int targetWidth, int targetHeight ) throws IOException{
        BufferedImage img = ImageIO.read(new File(imageName));
        BufferedImage scaledImg = Scalr.resize(img, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight);
        File destFile = new File(saveDir+imageName+".jpg");
        ImageIO.write(scaledImg, "jpg", destFile);
        System.out.println("Done resizing");
    }
       /**
        * 
        * @param pathFolderResize
        * @param pathFolderSave
        * @param targetWidth
        * @param targetHeight
        * @throws IOException 
        */
       void resizeWholeFolder (String pathFolderResize, String pathFolderSave, int targetWidth, int targetHeight) throws IOException{
         File[] listAllFileInFolder = this.listAllFileInFolder(pathFolderResize);
         for(int i=0; i<listAllFileInFolder.length; i++) {
            String pathImage = pathFolderResize+listAllFileInFolder[i].getName()+"jpg";
            BufferedImage img = ImageIO.read(new File(pathImage));
            BufferedImage scaledImg = Scalr.resize(img, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight);
            File destFile = new File(pathFolderSave+listAllFileInFolder[i].getName()+".jpg");
            ImageIO.write(scaledImg, "jpg", destFile);
         }
          System.out.println("Done resizing");
       }
    
}
