/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mmllabvsdextractfeature;

import Jama.Matrix;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;
import org.ejml.data.DenseMatrix64F;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;

/**
 *
 * @author tiendv
 */
public class MMllabVSDExtractFeature {
    
    static String UNTARSHFILE ="/home/mmlab/NetBeansProjects/trunk/MMllabVSDExtractFeature/src/mmllabvsdextractfeature/untarfolder.sh";
    static String CREADFOLDER ="/home/mmlab/NetBeansProjects/trunk/MMllabVSDExtractFeature/src/mmllabvsdextractfeature/createfolder.sh";
    static String DELETEFOLDER ="/home/mmlab/NetBeansProjects/trunk/MMllabVSDExtractFeature/src/mmllabvsdextractfeature/deletewholefolder.sh";
    static String EXTRACTFEATURESHFILE ="/home/mmlab/NetBeansProjects/trunk/MMllabVSDExtractFeature/src/mmllabvsdextractfeature/extracfeature.sh";
    static String EXTRACTFEATUREANDDELETESOURCEIMAGESHFILE ="/home/mmlab/NetBeansProjects/trunk/MMllabVSDExtractFeature/src/mmllabvsdextractfeature/extracfeatureanddeletesoureimage.sh";
    static String CUDAOVERFEATPATH ="/home/mmlab/overfeat/bin/linux_64/cuda";
    static String DELETEFILE = "/home/mmlab/NetBeansProjects/trunk/MMllabVSDExtractFeature/src/mmllabvsdextractfeature/deletewholefolder.sh";
    static int resizeWidth = 800;
    static int resizeHeight = 600;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        MMllabVSDExtractFeature hello = new MMllabVSDExtractFeature();
        FileStructer metdata = new FileStructer();
        //Utility utilityClassIni = new Utility();
        FrameStructer frameStructer = new FrameStructer();

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
        String dir="/media/data1";
        String metadataFileDir = "/media/data1/metdata/devel2011-new.lst";
        ArrayList<FileStructer> listFileFromMetadata = metdata.readMetadata(metadataFileDir);
        
//        String test = utilityClass.readTextFile("C:\\Users\\tiendv\\Downloads\\VSD_devel2011_1.shot_1.RKF_1.Frame_1.txt");
//        ArrayList <String> testFeatureSave = utilityClass.SplitUsingTokenizerWithLineArrayList(test, " ");
//        utilityClass.writeToFile("C:\\Users\\tiendv\\Downloads\\VSD_devel2011_1.shot_1.txt", testFeatureSave);
//        System.out.println(test);
        
        for (int i =0; i< listFileFromMetadata.size();i++){
            
            Utility utilityClass = new Utility();
            //Un zip file
            String folderName = listFileFromMetadata.get(i).getWholeFolderName();  
            String nameZipFile = dir+"/"+folderName+"/"+listFileFromMetadata.get(i).getNameZipFileName()+".tar";
            nameZipFile = nameZipFile.replaceAll("\\s","");
            String outPutFolder = dir+"/"+folderName+"/"+listFileFromMetadata.get(i).getNameZipFileName();
            outPutFolder = outPutFolder.replaceAll("\\s","");
            utilityClass.unTarFolder(UNTARSHFILE, nameZipFile,outPutFolder+"_");
            
            // Resize all image in folder has been unzip
            utilityClass.createFolder(CREADFOLDER, outPutFolder);
            utilityClass.resizeWholeFolder(outPutFolder+"_", outPutFolder, resizeWidth, resizeHeight);
            utilityClass.deleteWholeFolder(DELETEFOLDER, outPutFolder+"_");
            // Read all file from Folder has been unzip
            
            ArrayList <FrameStructer> allFrameInZipFolder = new ArrayList<>();
            allFrameInZipFolder = frameStructer.getListFileInZipFolder(outPutFolder);
            System.out.println(allFrameInZipFolder.size());         
            // sort with shot ID
            Collections.sort(allFrameInZipFolder,FrameStructer.frameIDNo);
            
            // Loop with 1 shot
            int indexFrame=0;
            for(int n=0;n<allFrameInZipFolder.size()/25;n++)
            {
                // Process with 1 
                    ArrayList <String> nameAllFrameOneShot = new ArrayList<>();
                    String shotAndFolderName = new String();
                    for(;indexFrame<Math.min((n+1)*25,allFrameInZipFolder.size());indexFrame++){
                        String imageForExtract = outPutFolder+"/"+allFrameInZipFolder.get(indexFrame).toFullName()+".jpg";
                        shotAndFolderName=allFrameInZipFolder.get(indexFrame).shotName();
                        String resultNameAfterExtract = outPutFolder+"/"+allFrameInZipFolder.get(indexFrame).toFullName()+".txt";
                        nameAllFrameOneShot.add(resultNameAfterExtract);
                        // extract and Delete image file --> ouput image'feature
                        utilityClass.excuteFeatureExtractionAnImage(EXTRACTFEATUREANDDELETESOURCEIMAGESHFILE, "0", CUDAOVERFEATPATH,imageForExtract ,resultNameAfterExtract);
                    }
                    // Pooling 
                    
//                    DenseMatrix64F resultMaTrixFeatureOneShot = utilityClass.buidEJMLMatrixFeatureOneShot(nameAllFrameOneShot);
//                    utilityClass.savePoolingFeatureOneShotWithEJMLFromMatrix(resultMaTrixFeatureOneShot,outPutFolder,shotAndFolderName);
                    
                      utilityClass.buidPoolingFileWithOutMatrixFeatureOneShot(nameAllFrameOneShot,outPutFolder,shotAndFolderName);
                    // Save A file 

                    for (int frameCount=0;frameCount<nameAllFrameOneShot.size();frameCount++)
                    {  // Delete feature extract file
                       utilityClass.deletefile(DELETEFILE, nameAllFrameOneShot.get(frameCount));
                    }
                    // Release one shot data
                    nameAllFrameOneShot.clear();
                    System.out.print("The end of one's shot"+"\n"+n);  
            }
            // Delete zip file
            utilityClass.deletefile(DELETEFILE,nameZipFile);
            
            // Zip Whole Folder
            
            /**
             * Extract 1 shot
             * Resize 25 frame with the same shotid --> delete orgra.
             * Extract 25 frame --> feature file --->delete 
             * 
             */
             
            
        }
          
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
    
}
