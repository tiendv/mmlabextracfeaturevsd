/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mmllabvsdextractfeature;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

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
     * RKF_1: film ID
     * shot_1: shot and id shot
     * Frame_1: frame and id frame
     */

public class FrameStructer {
    static Utility utility = new Utility();
    String folder;
    String shotID;
    String frameID;
    String filmID;
    
    public ArrayList<FrameStructer> getListFileInZipFolder (String pathZipFolder) {
        ArrayList<FrameStructer> result = new ArrayList<>();
        File[] listAllFileInFolder = utility.listAllFileInFolder(pathZipFolder);
        for (int i=0; i<listAllFileInFolder.length; i++){
            FrameStructer temp = new FrameStructer();
            String[] parts =  utility.SplitUsingTokenizerWithLine(listAllFileInFolder[i].getName(), ".");
            temp.folder = parts[0];
            temp.shotID = parts[1];
            temp.filmID =  parts[2];
            temp.frameID = parts[3];
            result.add(temp);
        }
        return result;  
    }

    /*Comparator for sorting the list by id frame*/
    public static Comparator<FrameStructer> frameIDNo = new Comparator<FrameStructer>() {

	public int compare(FrameStructer s1, FrameStructer s2) {
                
	   int rollno1 = Integer.parseInt(utility.SplitUsingTokenizer(s1.shotID, "_")[1]);
	   int rollno2 = Integer.parseInt(utility.SplitUsingTokenizer(s2.shotID, "_")[1]);

	   /*For ascending order*/
	   return rollno1-rollno2;
	   /*For descending order*/
	   //rollno2-rollno1;
        }
   
    };
        public String toFullName() {
        return folder + "." + shotID + "." + filmID +"."+frameID;
    }
    
}
