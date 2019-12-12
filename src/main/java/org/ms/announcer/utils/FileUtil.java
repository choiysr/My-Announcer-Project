package org.ms.announcer.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;

/**
 * FileUtil
 */
public final class FileUtil {

    // 나중에 CP 썸네일이미지 설정시 타입확인해서 저장해주게 수정할것
    public static String audioSave(String audioName, byte[] audio) {

        UUID uuid = UUID.randomUUID();
        String filePath = "C:\\AudioStorage";

        // audioName이 tmp로 시작하는 것 -> 최종 등록 이전 음성들
        // audioName이 tmp로 시작하면 tmp폴더에 저장시킨다.->하루에 한번씩 삭제할 수 있도록.
        if (audioName.startsWith("tmp")) {
            filePath += "\\tmp";
        } else {
            filePath += makePath();
        }

        String saveName = uuid.toString().replace("-", "") + "_" + audioName + ".wav";

        try {
            FileOutputStream fos = new FileOutputStream(new File(filePath, saveName));
            FileCopyUtils.copy(audio, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filePath + "\\" + saveName;
    }

    public static String makePath(){

        DateTimeFormatter dateForm = DateTimeFormatter.ofPattern("yy\\MM\\dd");
        LocalDate currDate = LocalDate.now();
        String filePath = "\\"+dateForm.format(currDate);
        File targetDir = new File("C:\\AudioStorage"+filePath);

        if(!targetDir.exists()){
            targetDir.mkdirs();
        }
        return filePath;
    }


}