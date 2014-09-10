/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mmllabvsdextractfeature;

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
    
}
