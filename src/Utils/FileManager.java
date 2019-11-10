package Utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class FileManager {
    private static String path = "C:/";
    private static String filenameTemp;
    public Map<String, String> tempMap = new HashMap<String, String>();

    /**
     *
     *
     * @throws IOException
     */
    public static boolean creatTxtFile(String name) throws IOException {
        boolean flag = false;
        filenameTemp = path + name + ".txt";
        File filename = new File(filenameTemp);
        if (!filename.exists()) {
            filename.createNewFile();
            flag = true;
        }
        return flag;
    }

    /**
     *
     *
     * @param newStr
     * @throws IOException
     */
    public static boolean writeTxtFile(String newStr) throws IOException {
        boolean flag = false;
        String filein = newStr + "\r\n";
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            File file = new File(filenameTemp);
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
                // System.getProperty("line.separator")
                buf = buf.append(System.getProperty("line.separator"));
            }
            buf.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (IOException e1) {
            throw e1;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return flag;
    }

    /**
     *
     * @param remoteFileUrl
     * @param storeFilePathName
     * @throws Exception
     */
    public void downLoadFileFromUrl (String remoteFileUrl, String storeFilePathName) throws Exception{
        URL remoteUrl = new URL(remoteFileUrl);
        HttpURLConnection fileUrlConn = (HttpURLConnection)remoteUrl.openConnection();
        fileUrlConn.setConnectTimeout(6000);
        fileUrlConn.setReadTimeout(6000);
        int responseCode = fileUrlConn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK){
            throw new Exception("Read file failure!");
        }

        DataInputStream remoteFileInputStream = new DataInputStream(fileUrlConn.getInputStream());

        FileOutputStream fileOutputStream = new FileOutputStream(storeFilePathName);

        DataOutputStream storeFileOutStream = new DataOutputStream(fileOutputStream);

        byte[] remoteFileByte = new byte[remoteFileInputStream.available()];
        int count = 0;
        while ((count = remoteFileInputStream.read(remoteFileByte)) > 0){
            storeFileOutStream.write(remoteFileByte, 0, count);
        }
        if (remoteFileInputStream != null){
            remoteFileInputStream.close();
        }
        if (storeFileOutStream != null){
            storeFileOutStream.close();
        }
    }

    public File getOrCreateFile(String pathName){
        try{
            if (!Util.isExist(pathName) || !pathName.matches(".+\\\\.+")){
                return null;
            }

            File file = new File(pathName);
            if (!file.exists()){
                String dirPath = Util.regexMatchedValue("(.*)\\\\.*", pathName, 1);
                File dir = new File(dirPath);
                if (!dir.exists()){
                    dir.mkdirs();
                }
                file.createNewFile();
                System.out.println("Create New Log File.");
            }else if (!file.isFile()) {
                System.out.println("Path Name is not a file path.");
                return null;
            }

            return file;
        }catch(IOException e){
            String writeInfo = "java.io.IOException:" + e.getMessage();
            System.out.println(writeInfo);
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param writeInfo
     * @param pathName
     */
    public void writeInfoToFile(String writeInfo, String pathName, Boolean isAppend){
        try{
            File file = getOrCreateFile(pathName);
            if (file == null){
                System.out.println("Can't find the file with path:" + pathName);
                return ;
            }
            FileWriter fileWriter = new FileWriter(file, isAppend);
            fileWriter.write(writeInfo);
            System.out.println("Log info:" + writeInfo);
            if (fileWriter != null){
                fileWriter.close();
            }
        }catch(IOException e){
            writeInfo = "java.io.IOException:" + e.getStackTrace().toString();
            System.out.println(writeInfo);
            e.printStackTrace();
        }
    }

    /**
     *
     * @param writeInfo
     * @param pathName
     */
    public void writeInfoToCsv(String writeInfo, String pathName, Boolean isAppend){
        try{
            File file = new File(pathName);
            boolean isNewFile = false;
            if (!file.exists()){
                System.out.println("Create New Log File.");
                file.createNewFile();
                isNewFile = true;
            }else if (!file.isFile()) {
                System.out.println("Path Name is not a file path.");
                return ;
            }
            FileWriter fileWriter = new FileWriter(file, isAppend);
            if (isNewFile){
                fileWriter.write("AzureStructure,FromSvnPath,CheckStatus" + System.getProperty("line.separator"));
            }
            fileWriter.write(writeInfo);
            System.out.println("Log info:" + writeInfo);
            if (fileWriter != null){
                fileWriter.close();
            }
        }catch(IOException e){
            writeInfo = "java.io.IOException:" + e.getStackTrace().toString();
            System.out.println(writeInfo);
            e.printStackTrace();
        }
    }

    /**
     *
     * @param writeInfo
     * @param pathName
     * @param isAppend
     * @param headLineInfo
     */
    public void writeInfoToCsv(String writeInfo, String pathName, Boolean isAppend, String headLineInfo){
        try{
            File file = new File(pathName);
            boolean isNewFile = false;
            if (!file.exists()){
                String dirPath = Util.regexMatchedValue("(.*)\\\\.*", pathName, 1);
                File dir = new File(dirPath);
                if (!dir.exists()){
                    dir.mkdirs();
                }
                file.createNewFile();
                isNewFile = true;
                System.out.println("Create New Log File.");
            }else if (!file.isFile()) {
                System.out.println("Path Name is not a file path.");
                return ;
            }

            FileWriter fileWriter = new FileWriter(file, isAppend);
            if (isNewFile){
                fileWriter.write(headLineInfo + System.getProperty("line.separator"));
            }
            fileWriter.write(writeInfo);
            System.out.println("Log info:" + writeInfo);
            if (fileWriter != null){
                fileWriter.close();
            }
        }catch(IOException e){
            writeInfo = "java.io.IOException:" + e.getStackTrace().toString();
            System.out.println(writeInfo);
            e.printStackTrace();
        }
    }

    /**
     *
     * @param writeInfo
     * @param file
     */
    public void writeInfoToFile(String writeInfo, File file){
        try{
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(writeInfo);

            /*FileInputStream fileInputStream = new FileInputStream(file);
            byte[] fileByteArray = new byte[fileInputStream.available()];
            fileInputStream.read(fileByteArray);*/
            if (fileWriter != null){
                fileWriter.close();
            }
            /*if (fileInputStream != null){
                fileInputStream.close();
            }*/
        }catch(IOException e){
            writeInfo = "java.io.IOException:" + e.getStackTrace().toString();
            System.out.println(writeInfo);
            e.printStackTrace();
        }
    }

    /**
     *
     * @param pathName
     * @return
     * @throws IOException
     */
    public File createFile(String pathName) throws IOException{
        File file = new File(pathName);
        file.createNewFile();
        return file;
    }

    /**
     *
     * @param moveFromPath
     * @param moveToDirPath
     * @param nameLike
     * @throws IOException
     */
    public void moveFile(String moveFromPath, String moveToDirPath, String nameLike, String logFilePathName){
        try{
            File file = new File(moveFromPath);
            System.out.println("fromAbsolutePath:" + file.getAbsolutePath());
            if (file.exists() && file.isDirectory()){
                String[] fileArray = file.list();
                for (int index = 0; index < fileArray.length; index++){
                    String fileOrDirName = fileArray[index];//File name or directory name.
                    String fileOrDirPathName = file.getAbsolutePath() + "\\" + fileOrDirName;
                    File fileDir = new File(fileOrDirPathName);
                    boolean isMatch = Util.isMatch(fileOrDirName, nameLike);
                    if (fileDir.isFile() && isMatch){
                        String renamePathName = moveToDirPath + "\\" + fileOrDirName;
                        File folder = new File(moveToDirPath);
                        if (!folder.exists()){
                            folder.mkdirs();
                        }
                        System.out.println("renamePathName:" + renamePathName);

                        new File(fileOrDirPathName).renameTo(new File(renamePathName));
                        String moveInfo = "Move " + fileOrDirPathName + "   To  " + renamePathName + System.getProperty("line.separator");
                        writeInfoToFile(moveInfo, logFilePathName, true);
                    } else if (fileDir.isDirectory()) {
                        String newMoveTo = moveToDirPath + "\\" + fileOrDirName;
                        String newNameLike = isMatch ? "" : nameLike;
                        moveFile(fileOrDirPathName, newMoveTo, newNameLike, logFilePathName);
                    }
                }
            } else if (file.exists() && file.isFile()){
                String renamePathName = moveToDirPath + "\\" + file.getName();
                File folder = new File(moveToDirPath);
                if (!folder.exists()){
                    folder.mkdirs();
                }
                System.out.println("renamePathName:" + renamePathName);
                file.renameTo(new File(renamePathName));
                String moveInfo = "Move " + moveFromPath + "  To  " + renamePathName + System.getProperty("line.separator");
                writeInfoToFile(moveInfo, logFilePathName, true);
            }
        }catch (Exception e){
            System.out.println(e.getStackTrace().toString());
            writeInfoToFile(e.getStackTrace().toString(), logFilePathName, true);
        }
    }

    /**
     *
     * @param moveFromPath
     * @param moveToDirPath
     * @param nameLikeRegexPattern
     * @throws IOException
     */
    public void moveSpecTypeFile(String moveFromPath, String moveToDirPath, Map<String, String> fileTypeMap,
                                 String nameLikeRegexPattern, String logFilePathName){
        try{
            File file = new File(moveFromPath);
            System.out.println("fromAbsolutePath:" + file.getAbsolutePath());
            if (file.exists() && file.isDirectory()){
                String[] fileArray = file.list();
                for (int index = 0; index < fileArray.length; index++){
                    String fileOrDirName = fileArray[index];//File name or directory name.
                    String fromPath = file.getAbsolutePath();
                    String fileOrDirPathName = fromPath + "\\" + fileOrDirName;
                    File fileDir = new File(fileOrDirPathName);
                    boolean isMatch = !Util.isExist(nameLikeRegexPattern) || fileOrDirName.matches(nameLikeRegexPattern);
                    if (fileDir.isFile() && isMatch){
                        String fileType = Util.regexMatchedValue(".*\\.(.*)", fileOrDirName, 1);
                        if (Util.isExist(fileType) && (fileTypeMap == null || fileTypeMap.containsKey(fileType))){
                            String renamePathName = moveToDirPath + "\\" + fileOrDirName;
                            File folder = new File(moveToDirPath);
                            if (!folder.exists()){
                                folder.mkdirs();
                            }
                            System.out.println("renamePathName:" + renamePathName);

                            new File(fileOrDirPathName).renameTo(new File(renamePathName));
                            String writInfo = fromPath + "," + moveToDirPath + "," + fileOrDirName + System.getProperty("line.separator");
                            String headlineInfo = "From,To,FileName";
                            writeInfoToCsv(writInfo,logFilePathName,true, headlineInfo);
                        }
                    } else if (fileDir.isDirectory()) {
                        String newMoveTo = moveToDirPath + "\\" + fileOrDirName;
                        moveSpecTypeFile(fileOrDirPathName, newMoveTo, fileTypeMap, nameLikeRegexPattern, logFilePathName);
                    }
                }
            } else if (file.exists() && file.isFile()){
                boolean isMatch = !Util.isExist(nameLikeRegexPattern) || file.getName().matches(nameLikeRegexPattern);
                String renamePathName = moveToDirPath + "\\" + file.getName();
                String fileType = Util.regexMatchedValue(".*\\.(.*)", renamePathName, 1);
                if (Util.isExist(fileType) && (fileTypeMap == null || fileTypeMap.containsKey(fileType)) && isMatch){
                    File folder = new File(moveToDirPath);
                    if (!folder.exists()){
                        folder.mkdirs();
                    }
                    System.out.println("renamePathName:" + renamePathName);
                    file.renameTo(new File(renamePathName));
                    String writInfo = file.getAbsolutePath() + "," + moveToDirPath + "," + file.getName() + System.getProperty("line.separator");
                    String headlineInfo = "From,To,FileName";
                    writeInfoToCsv(writInfo,logFilePathName,true, headlineInfo);
                }
            }
        }catch (Exception e){
            System.out.println(e.getStackTrace().toString());
            writeInfoToCsv(e.getMessage(),logFilePathName,true);
        }
    }

    /**
     *
     * @param moveFromPathName
     * @param moveToDirPath
     * @param logFilePathName
     */
    public void moveFile(String moveFromPathName, String moveToDirPath, String logFilePathName){
        try{
            File file = new File(moveFromPathName);
            if (file.exists() && file.isFile()){
                String renamePathName = moveToDirPath + "\\" + file.getName();
                File folder = new File(moveToDirPath);
                if (!folder.exists()){
                    folder.mkdirs();
                }
                file.renameTo(new File(renamePathName));
                String moveFromDirPath = Util.regexMatchedValue("(.*)\\\\.*", moveFromPathName, 1);
                String moveInfo = file.getName() + "," + moveFromDirPath + "," + moveToDirPath + System.getProperty("line.separator");
                writeInfoToCsv(moveInfo, logFilePathName, true, "FileName,MoveFromPath,ToDirPath");
            }
        }catch (Exception e){
            System.out.println(e.getStackTrace().toString());
            writeInfoToFile(e.getStackTrace().toString(), logFilePathName, true);
        }
    }

    /**
     *
     * @param filePathName
     * @param logFilePathName
     */
    public void moveFilesByCsv(String filePathName, String logFilePathName){
        if (!Util.isExist(filePathName)){
            return ;
        }
        File file = new File(filePathName);
        if (file.exists() && file.isFile() && file.canRead()){
            List<Map> csvContentList = Util.getCsvContent(filePathName);
            ListIterator<Map> listIterator = csvContentList.listIterator();
            while (listIterator.hasNext()){
                Map<String, String> linMap = listIterator.next();
                //String azureStructure = linMap.get("AzureStructure");
                String fromSvnPathName = linMap.get("FromSvnPath");
                String moveToPath = linMap.get("MoveToPath");
                String firstLevel = linMap.get("First Level");
                String secondLevel = linMap.get("Second Level");
                String thirdLevel = linMap.get("Third Level");
                String moveToSubDir = firstLevel + "\\" + secondLevel + "\\" + thirdLevel;

                moveFile(fromSvnPathName, moveToPath + "\\" + moveToSubDir, logFilePathName);
            }
        }
    }

    /**
     *
     * @param filePathName
     * @param logFilePathName
     */
    public void recoverySvnFilesByCsv(String filePathName, String logFilePathName){
        if (!Util.isExist(filePathName)){
            return ;
        }
        File file = new File(filePathName);
        if (file.exists() && file.isFile() && file.canRead()){
            List<Map> csvContentList = Util.getCsvContent(filePathName);
            ListIterator<Map> listIterator = csvContentList.listIterator();
            while (listIterator.hasNext()){
                Map<String, String> linMap = listIterator.next();
                //String azureStructure = linMap.get("AzureStructure");
                String fromSvnPathName = linMap.get("FromSvnPath");
                String moveFromDir = linMap.get("MoveToPath");
                String firstLevel = linMap.get("First Level");
                String secondLevel = linMap.get("Second Level");
                String thirdLevel = linMap.get("Third Level");
                String pattern = "(.*)\\\\(.*)";
                String moveToDir = Util.regexMatchedValue(pattern, fromSvnPathName, 1);
                String fileName = Util.regexMatchedValue(pattern, fromSvnPathName, 2);
                String moveFromSubDir = firstLevel + "\\" + secondLevel + "\\" + thirdLevel;
                String moveFromPathName = moveFromDir + "\\" + moveFromSubDir + "\\" + fileName;

                moveFile(moveFromPathName, moveToDir, logFilePathName);
            }
        }
    }

    /**
     *
     * @param fromPath
     * @param nameLike
     * @param logFilePathName
     */
    public void listFiles(String fromPath, String nameLike, String logFilePathName){
        try{
            File file = new File(fromPath);
            //System.out.println("fromFileToPath():" + file.toPath());
            System.out.println("fromAbsolutePath:" + file.getAbsolutePath());
            if (file.exists() && file.isDirectory()){
                String[] fileArray = file.list();
                for (int index = 0; index < fileArray.length; index++){
                    String fileOrDirName = fileArray[index];//File name or directory name.
                    //System.out.println(index + "_fileInfo:"  + fileOrDirName);
                    String fileOrDirPathName = file.getAbsolutePath() + "\\" + fileOrDirName;
                    File fileDir = new File(fileOrDirPathName);
                    boolean isMatch = Util.isMatch(fileOrDirName, nameLike);
                    if (fileDir.isFile() && isMatch){
                        writeInfoToFile(fileOrDirPathName + System.getProperty("line.separator"), logFilePathName, true);
                    } else if (fileDir.isDirectory()) {
                        String newNameLike = isMatch ? "" : nameLike;
                        listFiles(fileOrDirPathName, newNameLike, logFilePathName);
                    }
                }
            } else if (file.exists() && file.isFile()){
                writeInfoToFile(fromPath + System.getProperty("line.separator"), logFilePathName, true);
            }
        }catch (Exception e){
            System.out.println(e.getStackTrace().toString());
            writeInfoToFile(e.getStackTrace().toString() + System.getProperty("line.separator"), logFilePathName, true);
        }
    }

    /**
     *
     * @param fromPath
     * @param logFilePathName  Store the directory level info
     * @param dirStructure  First Directory Level
     */
    public void listDirStructure(String fromPath, String logFilePathName, String dirStructure){
        try{
            File file = new File(fromPath);
            System.out.println("fromAbsolutePath:" + file.getAbsolutePath());
            if (file.exists() && file.isDirectory()){
                dirStructure += ",";
                boolean hasDirectory = false;
                String[] fileArray = file.list();
                if (fileArray != null && fileArray.length > 0){
                    for (int index = 0; index < fileArray.length; index++){
                        String fileOrDirName = fileArray[index];//File name or directory name.
                        String fileOrDirPathName = file.getAbsolutePath() + "\\" + fileOrDirName;
                        File fileDir = new File(fileOrDirPathName);
                        if (fileDir.isDirectory()) {
                            hasDirectory = true;
                            String newDirStructure = dirStructure + fileOrDirName;
                            listDirStructure(fileOrDirPathName, logFilePathName, newDirStructure);
                        }
                    }
                }
                if (!hasDirectory || fileArray == null || fileArray.length < 1){
                    String lineInfo = Util.isExist(dirStructure)?dirStructure.replaceAll(",$", ""):"";
                    writeInfoToFile(lineInfo + System.getProperty("line.separator"), logFilePathName, true);
                }
            }
        }catch (Exception e){
            System.out.println(e.getStackTrace().toString());
            writeInfoToFile(e.getMessage() + System.getProperty("line.separator"), logFilePathName, true);
        }
    }

    /**
     * Search files named match namelike rule in fromPath but not find in compareDirPath.
     * @param fromPath
     * @param compareDirPath
     * @param nameLike
     * @param logFilePathName
     */
    public void listNotMatchedFiles(String fromPath, String compareDirPath, String nameLike, String logFilePathName,Map<String, Boolean> ignoreContentMap){
        try{
            File file = new File(Util.trimPath(fromPath));
            //System.out.println("fromFileToPath():" + file.toPath());
            System.out.println("fromPath:" + file.getAbsolutePath() + "_compareDirPath:" + compareDirPath);
            if (file.exists() && file.isDirectory()){
                String[] fileArray = file.list();
                for (int index = 0; index < fileArray.length; index++){
                    String fileOrDirName = fileArray[index];//File name or directory name.
                    String fileOrDirPathName = file.getAbsolutePath() + "\\" + fileOrDirName;
                    File fileDir = new File(fileOrDirPathName);
                    boolean isMatch = Util.isMatch(fileOrDirName, nameLike);
                    if (fileDir.isFile() && isMatch){
                        if (!Util.isFileAndExist(Util.trimPath(compareDirPath), fileOrDirName)){
                            String azureLevelStructure = logFilePathName.replaceAll(".*?\\\\|\\..*", "");
                            if (ignoreContentMap == null || !ignoreContentMap.containsKey(fileOrDirPathName + "_" + azureLevelStructure)){
                                String writeInfo = azureLevelStructure + "," + fileOrDirPathName + System.getProperty("line.separator");
                                String headLineInfo = "AzureStructure,FromSvnPath,CheckStatus";
                                writeInfoToCsv(writeInfo, logFilePathName, true, headLineInfo);
                            }
                        }
                    } else if (fileDir.isDirectory()) {
                        String newNameLike = isMatch ? "" : nameLike;
                        listNotMatchedFiles(Util.trimPath(fileOrDirPathName), Util.trimPath(compareDirPath), newNameLike, logFilePathName, ignoreContentMap);
                    }
                }
            } else if (file.exists() && file.isFile()){
                if (Util.isMatch(file.getName(), nameLike) && !Util.isFileAndExist(Util.trimPath(compareDirPath), file.getName())){
                    String azureLevelStructure = logFilePathName.replaceAll(".*?\\\\|\\..*", "");
                    if (ignoreContentMap == null || !ignoreContentMap.containsKey(fromPath + "_" + azureLevelStructure)){
                        String headLineInfo = "AzureStructure,FromSvnPath,CheckStatus";
                        String writeInfo = azureLevelStructure + "," + fromPath + System.getProperty("line.separator");
                        writeInfoToCsv(writeInfo, logFilePathName, true, headLineInfo);
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e.getStackTrace().toString());
            writeInfoToCsv(e.getStackTrace().toString() + System.getProperty("line.separator"), logFilePathName, true);
        }
    }

    /**
     * If the file from compareWithDirPath not be find in corresponding fromPath or subdirectory, it will be record in nonMatchLogFile file.
     * If the file from compareWithDirPath be found in corresponding fromPath or subdirectory, it will be record in matchedLogFile file.
     *
     * @param fromPath
     * @param compareWithDirPath
     * @param nonMatchLogFile
     * @param matchedLogFile
     */
    public void listFilesMappingByPathName(String fromPath, String compareWithDirPath, String nonMatchLogFile, String matchedLogFile){
        try{
            File file = new File(Util.trimPath(compareWithDirPath));
            //System.out.println("compareWithDirPath:" + file.toPath());
            System.out.println("compareWithDirPath:" + file.getAbsolutePath() + "_fromPath:" + fromPath);
            if (file.exists() && file.isDirectory()){
                String[] fileArray = file.list();
                for (int index = 0; index < fileArray.length; index++){
                    String fileOrDirName = fileArray[index];//File name or directory name.
                    String fileOrDirPathName = file.getAbsolutePath() + "\\" + fileOrDirName;
                    File fileDir = new File(fileOrDirPathName);
                    if (fileDir.isFile()){
                        String matchedFilePath = Util.checkFileAndExist(Util.trimPath(fromPath), fileOrDirName);
                        String writeInfo = fileOrDirName
                                + "," + compareWithDirPath.replaceAll("^C:\\\\Workday\\\\GoogleDrive\\\\","")
                                + "," + fromPath.replaceAll("^C:\\\\Workday\\\\Azure Devops Repos Files\\\\", "")
                                + "," + System.getProperty("line.separator");
                        String headLineInfo = "FileName,GoogleDriveDirectory,AzureStructureDirectory,CheckStatus";
                        if (!Util.isExist(matchedFilePath)){
                            writeInfoToCsv(writeInfo, nonMatchLogFile, true, headLineInfo);
                        }else if (Util.isExist(matchedLogFile)){
                            writeInfoToCsv(writeInfo, matchedLogFile, true, headLineInfo);
                        }
                    } else if (fileDir.isDirectory()) {
                        //fileOrDirPathName corresponding Azure Directory
                        String azureDirectory = fromPath + "\\" + fileOrDirName;//fromPath
                        listFilesMappingByPathName(Util.trimPath(azureDirectory), Util.trimPath(fileOrDirPathName), nonMatchLogFile, matchedLogFile);
                    }
                }
            } else if (file.exists() && file.isFile()){
                String matchedFilePath = Util.checkFileAndExist(Util.trimPath(fromPath), file.getName());
                String writeInfo = file.getName()
                        + "," + compareWithDirPath.replaceAll("^C:\\\\Workday\\\\GoogleDrive\\\\","")
                        + "," + fromPath.replaceAll("^C:\\\\Workday\\\\Azure Devops Repos Files\\\\", "")
                        + "," + System.getProperty("line.separator");
                String headLineInfo = "FileName,GoogleDriveDirectory,AzureStructureDirectory,CheckStatus";
                if (!Util.isExist(matchedFilePath)){
                    writeInfoToCsv(writeInfo, nonMatchLogFile, true, headLineInfo);
                }else if (Util.isExist(matchedLogFile)){
                    writeInfoToCsv(writeInfo, matchedLogFile, true, headLineInfo);
                }
            }
        }catch (Exception e){
            System.out.println(e.getStackTrace().toString());
            writeInfoToCsv(e.getMessage() + System.getProperty("line.separator"), nonMatchLogFile, true);
        }
    }

    /**
     * If the file in compareWithDirPath not be found in corresponding fromPath, it will be record in log file.
     * @param fromPath
     * @param compareWithDirPath
     * @param nonMatchLogFile
     */
    public void listNotMatchedFilesByPathName(String fromPath, String compareWithDirPath, String nonMatchLogFile){
        try{
            File file = new File(Util.trimPath(compareWithDirPath));
            //System.out.println("compareWithDirPath:" + file.toPath());
            System.out.println("compareWithDirPath:" + file.getAbsolutePath() + "_fromPath:" + fromPath);
            if (!this.tempMap.containsKey("fromPath")){
                this.tempMap.put("fromPath", fromPath);
                this.tempMap.put("compareWithPath", compareWithDirPath);
            }
            if (file.exists() && file.isDirectory()){
                String[] fileArray = file.list();
                for (int index = 0; index < fileArray.length; index++){
                    String fileOrDirName = fileArray[index];//File name or directory name.
                    String fileOrDirPathName = file.getAbsolutePath() + "\\" + fileOrDirName;
                    File fileDir = new File(fileOrDirPathName);
                    if (fileDir.isFile()){
                        String baseFromPath = this.tempMap.get("fromPath");
                        String baseCompareWithPath = this.tempMap.get("compareWithPath");
                        String matchedFilePath = Util.checkFileByPath(Util.trimPath(fromPath), fileOrDirName);
                        String writeInfo = fileOrDirName
                                + "," + compareWithDirPath.replace(baseCompareWithPath,"")
                                + "," + fromPath.replace(baseFromPath, "")
                                + "," + System.getProperty("line.separator");

                        String headLineInfo = "FileName,FilePath(BasePath:" + baseCompareWithPath + "),CompareWithPath(BasePath:" + baseFromPath + ")";
                        if (!Util.isExist(matchedFilePath)){
                            writeInfoToCsv(writeInfo, nonMatchLogFile, true, headLineInfo);
                        }/*else if (Util.isExist(matchedLogFile)){
                            writeInfoToCsv(writeInfo, matchedLogFile, true, headLineInfo);
                        }*/
                    } else if (fileDir.isDirectory()) {
                        //fileOrDirPathName corresponding Azure Directory
                        String azureDirectory = fromPath + "\\" + fileOrDirName;//fromPath
                        listNotMatchedFilesByPathName(Util.trimPath(azureDirectory), Util.trimPath(fileOrDirPathName), nonMatchLogFile);
                    }
                }
            } else if (file.exists() && file.isFile()){
                String baseFromPath = this.tempMap.get("fromPath");
                String baseCompareWithPath = this.tempMap.get("compareWithPath");
                String matchedFilePath = Util.checkFileByPath(Util.trimPath(fromPath), file.getName());
                String writeInfo = file.getName()
                        + "," + compareWithDirPath.replaceAll(baseCompareWithPath,"")
                        + "," + fromPath.replaceAll(baseFromPath, "")
                        + "," + System.getProperty("line.separator");
                String headLineInfo = "FileName,FilePath(BasePath:" + baseCompareWithPath + "),CompareWithPath(BasePath:" + baseFromPath + ")";
                if (!Util.isExist(matchedFilePath)){
                    writeInfoToCsv(writeInfo, nonMatchLogFile, true, headLineInfo);
                }/*else if (Util.isExist(matchedLogFile)){
                    writeInfoToCsv(writeInfo, matchedLogFile, true, headLineInfo);
                }*/
            }
        }catch (Exception e){
            System.out.println(e.getStackTrace().toString());
            writeInfoToCsv(e.getMessage() + System.getProperty("line.separator"), nonMatchLogFile, true);
        }
    }

    /**
     *
     * @param dirPath
     * @param addFilePathName
     * @param ignoreFilePathName
     */
    public void inputAddContentToCSV(String dirPath, String addFilePathName, String ignoreFilePathName,String combineFilePathName){
        if (!Util.isExist(dirPath)){
            return ;
        }
        File file = new File(dirPath);
        String[] fileArray = file.list();
        for (int index = 0; index < fileArray.length; index++){
            String fileName = fileArray[index];
            List<Map> csvContentList = Util.getCsvContent(dirPath + "\\" + fileName);
            ListIterator<Map> listIterator = csvContentList.listIterator();
            while (listIterator.hasNext()){
                Map<String, String> linMap = listIterator.next();
                String azureStructure = linMap.get("AzureStructure");
                String fromSvnPath = linMap.get("FromSvnPath");
                String checkStatus = linMap.get("CheckStatus");
                String firstLevel = Util.regexMatchedValue("(.*?)__", azureStructure, 1);
                String secondLevel = Util.regexMatchedValue("__(.*?)__", azureStructure, 1);
                String thirdLevel = Util.regexMatchedValue(".*__(.*)", azureStructure, 1);
                String newCsvLineInfo = azureStructure + "," + fromSvnPath + "," + firstLevel + ","
                        + secondLevel + "," + thirdLevel + "," + checkStatus + System.getProperty("line.separator");
                String csvHeadLineInfo = "AzureStructure,FromSvnPath,First Level,Second Level,Third Level,Check Status";
                if (Util.isExist(checkStatus) && checkStatus.toLowerCase().equals("add")){
                    writeInfoToCsv(newCsvLineInfo,addFilePathName, true, csvHeadLineInfo);
                } else if (Util.isExist(checkStatus) && checkStatus.toLowerCase().equals("ignore")){
                    writeInfoToCsv(newCsvLineInfo,ignoreFilePathName, true, csvHeadLineInfo);
                }

                writeInfoToCsv(newCsvLineInfo,combineFilePathName, true, csvHeadLineInfo);
            }
        }
    }
}

