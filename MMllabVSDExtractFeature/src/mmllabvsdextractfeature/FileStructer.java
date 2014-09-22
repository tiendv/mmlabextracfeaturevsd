/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mmllabvsdextractfeature;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * File's struct
 * @author tiendv
 */
public class FileStructer {
    /**
     * Example devel2011-newlist.lst
     * 
     * VSD_devel2011_1_001 #$# VSD_devel2011_1_001 VSD_devel2011_1_001 #$# devel2011-new
     * 
     * VSD_devel2011_1_001: zipFileName
     * devel2011-new: wholeFolderName content all zipFile
     */
    Utility utility = new Utility();
    String nameZipFileName;
    String wholeFolderName;

    public FileStructer() {
    }

    public String getNameZipFileName() {
        return nameZipFileName;
    }

    public void setNameZipFileName(String nameZipFileName) {
        this.nameZipFileName = nameZipFileName;
    }

    public String getWholeFolderName() {
        return wholeFolderName;
    }

    public void setWholeFolderName(String wholeFolderName) {
        this.wholeFolderName = wholeFolderName;
    }
    
      /**
     * 
     * @param metadataFile: duong dan den 1 file metadata
     * // 
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
                    String[] parts =  utility.SplitUsingTokenizerWithLine(line.get(i), "#$#");
                    temp.nameZipFileName = parts[0];
                    temp.wholeFolderName = parts[2];
                    result.add(temp);
                }
           }
           System.out.println(result.size());
        return result;
    }
    
}
