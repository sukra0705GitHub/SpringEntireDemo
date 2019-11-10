package Utils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public final class Util {
    public static final String linSeparator = System.getProperty("line.separator");
    /**
     *
     * @param queryString
     * @param parameterName
     * @return
     */
    public static String getParameterValueFromQuery(String queryString, String parameterName){
        String pattern = "&?" + parameterName + "=(.*)?&|&?" + parameterName + "=(.*)?";
        Matcher dirPathMatcher = Pattern.compile(pattern).matcher(queryString);
        if (dirPathMatcher.find()){
            return dirPathMatcher.group(1) == null?dirPathMatcher.group(2):dirPathMatcher.group(1);
        } else {
            return "";
        }
    }

    /**
     *
     * @param pattern
     * @param originalString
     * @return
     */
    public static String regexMatchedValue(String pattern, String originalString){
        Matcher dirPathMatcher = Pattern.compile(pattern).matcher(originalString);
        if (dirPathMatcher.find()){
            return dirPathMatcher.group();
        } else {
            return "";
        }
    }

    /**
     *
     * @param pattern
     * @param originalString
     * @param getGroupValue
     * @return
     */
    public static String regexMatchedValue(String pattern, String originalString, Integer getGroupValue){
        Matcher dirPathMatcher = Pattern.compile(pattern).matcher(originalString);
        if (dirPathMatcher.find()){
            return getGroupValue < 0?dirPathMatcher.group():dirPathMatcher.group(getGroupValue);
        } else {
            return "";
        }
    }

    /**
     *
     * @param fileName
     * @param nameLike
     * @return
     */
    public static boolean isMatch(String fileName, String nameLike){
        if (fileName == null || fileName.isEmpty() || nameLike == null){
            return false;
        }

        if (nameLike == null || nameLike.isEmpty() || fileName.toLowerCase().contains(nameLike.toLowerCase())){
            return true;
        } else if (!nameLike.matches("^[A-Z][a-z]+$|^[a-z]+$|^[A-Z]+$")) {
            String newLikeName = nameLike.replaceAll("[^A-Za-z]+", " ").replaceAll("([A-Z]+?[A-Z][a-z]|[A-Z])", " $1");
            String[] likeNameArray = newLikeName.trim().split("\\s+");
            boolean hasMatchedProcess = false;
            for (int index = 0; index < likeNameArray.length; index++){
                String separateNameLike = likeNameArray[index];
                if(separateNameLike.length() > 4){
                    if (!fileName.toLowerCase().contains(separateNameLike.toLowerCase())){
                        return false;
                    }
                    hasMatchedProcess = true;
                }
            }

            return hasMatchedProcess;
        }

        return false;
    }

    /**
     *
     * @param csvPathName
     * @return
     */
    public static Map<String, Boolean> getIgnoreCsvContent(String csvPathName){
        File file = new File(csvPathName);
        if (!file.exists() || !file.isFile() || !file.canRead()){
            return null;
        } else {
            Map<String, Boolean> csvContentMap = new HashMap<>();
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String lineInfo = "";
                int azureStructureIndex = -1, fromSvnPathIndex = -1, checkStatusIndex = -1;
                String[] columnNameArray = null;
                while((lineInfo = bufferedReader.readLine()) != null){
                    if (columnNameArray == null){
                        columnNameArray = lineInfo.split(",");
                        for (int index = 0; index < columnNameArray.length; index++){
                            String Value = columnNameArray[index];
                            if (Value.replaceAll("\\s+","").equalsIgnoreCase("AzureStructure")){
                                azureStructureIndex = index;
                            } else if (Value.replaceAll("\\s+","").equalsIgnoreCase("FromSvnPath")){
                                fromSvnPathIndex = index;
                            } else if (Value.replaceAll("\\s+","").equalsIgnoreCase("CheckStatus")){
                                checkStatusIndex = index;
                            }
                        }
                    } else {
                        String[] cellArray = lineInfo.split(",");
                        if(checkStatusIndex > -1 && cellArray[checkStatusIndex].equalsIgnoreCase("Ignore")){
                            String fromSvnPathValue = "";
                            if (fromSvnPathIndex > -1){
                                fromSvnPathValue = cellArray[fromSvnPathIndex];
                            }
                            if (azureStructureIndex > -1 && Util.isExist(cellArray[azureStructureIndex])){
                                csvContentMap.put(fromSvnPathValue + "_" + cellArray[azureStructureIndex], true);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return csvContentMap;
        }
    }

    /**
     *
     * @param csvPathName
     * @return
     */
    public static List<Map> getCsvContent(String csvPathName){
        File file = new File(csvPathName);
        if (!file.exists() || !file.isFile() || !file.canRead()){
            return null;
        } else {
            List<Map> csvContentList = new ArrayList<>();
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String lineInfo = "";
                String[] columnNameArray = null;
                while((lineInfo = bufferedReader.readLine()) != null){
                    if (columnNameArray == null){
                        columnNameArray = lineInfo.split(",");
                    } else {
                        String[] cellArray = lineInfo.split(",");
                        Map lineInfoMap = new HashMap();
                        for (int index = 0; index < cellArray.length; index++){
                            lineInfoMap.put(columnNameArray[index], cellArray[index]);
                        }
                        csvContentList.add(lineInfoMap);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return csvContentList;
        }
    }

    /**
     * whether the pathName is a file.
     * @param pathName
     * @return
     */
    public static boolean isFileAndExist(String pathName){
        File file = new File(pathName);
        if (file.exists() && file.isFile()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * whether the directory and subdirectory of the path has the file which named fileName.
     * @param path
     * @param fileName
     * @return
     */
    public static boolean isFileAndExist(String path, String fileName){
        File file = new File(path + "\\" + fileName);
        if (file.exists() && file.isFile()){
            return true;
        }else{
            String[] fileArray = new File(path).list();
            if (fileArray == null || fileArray.length < 1){
                return false;
            }

            for (int index = 0; index < fileArray.length; index++){
                //File name or directory name.
                String fileOrDirName = fileArray[index];
                String fileOrDirPathName = path + "\\" + fileOrDirName;
                File fileDir = new File(fileOrDirPathName);
                if (fileDir.isDirectory()) {
                    boolean checkResult = isFileAndExist(fileOrDirPathName, fileName);
                    if (checkResult == true){
                        return true;
                    }
                }
            }

            return false;
        }
    }

    /**
     * whether the directory and subdirectory of the path has the file which named fileName. Return the matched file path or empty.
     * @param path
     * @param fileName
     * @return
     */
    public static String checkFileAndExist(String path, String fileName){
        File file = new File(path + "\\" + fileName);
        if (file.exists() && file.isFile()){
            return file.getAbsolutePath();
        }else{
            String[] fileArray = new File(path).list();
            if (fileArray == null || fileArray.length < 1){
                return "";
            }

            for (int index = 0; index < fileArray.length; index++){
                //File name or directory name.
                String fileOrDirName = fileArray[index];
                String fileOrDirPathName = path + "\\" + fileOrDirName;
                File fileDir = new File(fileOrDirPathName);
                if (fileDir.isDirectory()) {
                    String filePath = checkFileAndExist(fileOrDirPathName, fileName);
                    if (Util.isExist(filePath)){
                        return filePath;
                    }
                }
            }

            return "";
        }
    }

    /**
     *
     * @param path
     * @param fileName
     * @return
     */
    public static String checkFileByPath(String path, String fileName){
        File file = new File(path + "\\" + fileName);
        if (file.exists() && file.isFile()){
            return file.getAbsolutePath();
        }
        return "";
    }

    /**
     *
     * @param path
     * @return
     */
    public static String trimPath(String path){
        if (path == null || path.isEmpty()){
            return "";
        }
        return path.replaceAll("(\\s*)(\\\\)(\\s*)", "$2");
    }

    /**
     *
     * @param parameter
     * @return
     */
    public static boolean isExist(String parameter){
        return (parameter == null || parameter.isEmpty())?false:true;
    }

    public static void exeCmd(String cmdString) throws IOException{
        if (!Util.isExist(cmdString)){
            return ;
        }
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(cmdString);
        System.out.println("AvailableProcessors:" + runtime.availableProcessors());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String processInfo;
        while ((processInfo = bufferedReader.readLine()) != null){
            System.out.println(processInfo);
        }
        if (process != null){
            process.destroy();
        }
    }

    /**
     *
     * @param timeMillis1
     * @param timeMillis2
     * @return
     */
    public static Map<String, String> showDiffTimeMillisInfo(long timeMillis1, long timeMillis2){
        if (timeMillis1 < 0 || timeMillis2 < 0){
            return null;
        }
        long diffTimeMillis = Math.abs(timeMillis2 - timeMillis1);
        long totalSeconds = diffTimeMillis/1000;
        int day = (int)(totalSeconds/3600/24);
        int hour = (int)(totalSeconds/3600%24);
        int minute = (int)(totalSeconds/60%60);
        int second = (int)(totalSeconds%60);
        int millisecond = (int)(diffTimeMillis%1000);
        String combineInfo = day + " Days  " + hour + " Hours  " + minute + " Minutes  " + second + " Seconds  " + millisecond + " Milliseconds";

        Map<String, String> diffInfoMap = new HashMap<>();
        diffInfoMap.put("Day", day + "");
        diffInfoMap.put("Hour", hour + "");
        diffInfoMap.put("Minute", minute + "");
        diffInfoMap.put("Second", second + "");
        diffInfoMap.put("Millisecond", millisecond + "");
        diffInfoMap.put("show", combineInfo);


        return diffInfoMap;
    }

    /**
     *
     * @param zipFilePathName
     * @param uncompressDestinationPath
     */
    public static void unZip2(String zipFilePathName, String uncompressDestinationPath){
        try {
            FileInputStream fileInputStream = new FileInputStream(zipFilePathName);
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
            ZipFile zipFile = new ZipFile(zipFilePathName, java.nio.charset.Charset.forName("gbk"));

            ZipEntry zipEntry = null;
            FileManager fileManager = new FileManager();
            while((zipEntry = zipInputStream.getNextEntry()) != null){
                InputStream inputStream = zipFile.getInputStream(zipEntry);
                int bufferSize = 2048, count = 0;
                byte[] zipEntryBytes = new byte[bufferSize];
                String pathName = (Util.isExist(uncompressDestinationPath)?
                        uncompressDestinationPath.replaceAll("(.*)\\\\$", "$1"):
                        zipFilePathName.replaceAll("(.*\\\\).*", "$1")) +
                        zipEntry.getName().replaceAll("/","\\\\");
                System.out.println(pathName);
                File newFile = fileManager.getOrCreateFile(pathName);
                FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                while((count = inputStream.read(zipEntryBytes, 0, bufferSize)) != -1){
                    fileOutputStream.write(zipEntryBytes, 0, count);
                }
                fileOutputStream.flush();
                if (fileOutputStream != null){
                    fileOutputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param zipFilePathName
     * @param uncompressDestinationPath
     */
    public static void unZip(String zipFilePathName, String uncompressDestinationPath){
        try {
            //FileInputStream fileInputStream = new FileInputStream(zipFilePathName);
            //ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);

            ZipFile zipFile = new ZipFile(zipFilePathName, java.nio.charset.Charset.forName("gbk"));
            Enumeration enumeration = zipFile.entries();

            ZipEntry zipEntry = null;
            FileManager fileManager = new FileManager();
            while(enumeration.hasMoreElements()){
                zipEntry = (ZipEntry)enumeration.nextElement();

                InputStream inputStream = zipFile.getInputStream(zipEntry);
                int bufferSize = 2048,count = 0;
                byte[] zipEntryBytes = new byte[bufferSize];
                String pathName = (Util.isExist(uncompressDestinationPath)?
                        uncompressDestinationPath.replaceAll("(.*)\\\\$", "$1"):
                        zipFilePathName.replaceAll("(.*\\\\).*", "$1")) +
                        zipEntry.getName().replaceAll("/","\\\\");
                System.out.println(pathName);
                File newFile = fileManager.getOrCreateFile(pathName);
                FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                while ((count = inputStream.read(zipEntryBytes, 0, bufferSize)) != -1){
                    fileOutputStream.write(zipEntryBytes, 0, count);
                }
                fileOutputStream.flush();


                if (inputStream != null){
                    inputStream.close();
                }
                if (fileOutputStream != null){
                    fileOutputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
