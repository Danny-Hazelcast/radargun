package org.cachebench.utils;

import java.io.File;

/**
 *
 * @author Mircea.Markus@jboss.com
 */
public class Utils {

   public static String getDurationString(long duration) {
      long secs = duration / 1000;
      String result = " ";
      long mins = secs / 60;
      if (mins > 0) {
         result += mins + " mins ";
      }
      result += (secs % 60) + " secs ";
      return result.trim();
   }


   public static String fileName2Config(String fileName) {
      int index = fileName.indexOf('.');
      if (index > 0) {
         fileName = fileName.substring(0, index);
         index = fileName.indexOf(File.separator);
         if (index > 0) {
            fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
         }
      }
      return fileName;
   }
}