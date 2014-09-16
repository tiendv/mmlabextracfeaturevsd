/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mmllabvsdextractfeature;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author tiendv
 */
   /**
     * Example devel2011-new/VSD_devel2011_1_001/VSD_devel2011_1.shot_1.RKF_1.Frame_1
     * 
     * VSD_devel2011_1_001: zipFileName
     * devel2011-new: wholeFolderName content all zipFile
     * VSD_devel2011_1: folder
     * shot_1: shot and id shot
     * Frame_1: frame and id frame
     */

public class FrameStructer {
    Utility utility = new Utility();
    String folder;
    String shotID;
    String frameID;
    
    public ArrayList<FrameStructer> getListFileInZipFolder (String pathZipFolder) {
        ArrayList<FrameStructer> result = new ArrayList<>();
        File[] listAllFileInFolder = utility.listAllFileInFolder(pathZipFolder);
        for (int i=0; i<listAllFileInFolder.length; i++){
            FrameStructer temp = new FrameStructer();
            String[] parts =  utility.SplitUsingTokenizer(listAllFileInFolder[i].getName(), ".");
            temp.folder = parts[0];
            temp.shotID = parts[1];
            temp.frameID = parts[1]; 
        }
        return result;
        
    }
}
