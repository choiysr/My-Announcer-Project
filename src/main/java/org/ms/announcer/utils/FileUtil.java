package org.ms.announcer.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;

/**
 * FileUtil
 */
public final class FileUtil {

    // ~6일까지 폴더경로 날짜별 설정.
    // 나중에 CP 썸네일이미지 설정시 타입확인해서 저장해주게 수정할것
    public static String audioSave(String audioName, byte[] audio) {

        UUID uuid = UUID.randomUUID();
        String filePath = "C:\\AudioStorage";

        // audioName이 tmp로 시작하는 것 -> 최종 등록 이전 음성들
        // audioName이 tmp로 시작하면 tmp폴더에 저장시킨다.->하루에 한번씩 삭제할 수 있도록.
        if (audioName.startsWith("tmp")) {
            filePath += "\\tmp";
        }

        String saveName = uuid.toString().replace("-", "") + "_" + audioName + ".wav";
        String audioNameWithPath = "";

        try {
            FileOutputStream fos = new FileOutputStream(new File(filePath, saveName));
            FileCopyUtils.copy(audio, fos);
            // 저장직후. 여기에 audioCombine 메서드 호출해주면 됨
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("확인=========================");
        System.out.println(audioNameWithPath);

        return filePath + "\\" + saveName;
    }

    /*
     * public static void audioCombine(String audioNameWithPath) {
     * 
     * // 기존 파일에 덮어쓰면 될까?
     * 
     * String soundEffect =
     * "C:\\AudioStorage\\soundEffect\\airport_announcement.wav";
     * 
     * 
     * try { AudioInputStream clip1 = AudioSystem.getAudioInputStream(new
     * File(soundEffect)); System.out.println("clip1완성");
     * System.out.println("오디오네임위드패스 확인!!! ");
     * System.out.println(audioNameWithPath);
     * 
     * Clip sound = AudioSystem.getClip();
     * sound.open(AudioSystem.getAudioInputStream(new File(audioNameWithPath)));
     * AudioInputStream clip2 = AudioSystem.getAudioInputStream(new
     * File(audioNameWithPath)); System.out.println("clip2완성"); AudioInputStream
     * appendFiles = new AudioInputStream(new SequenceInputStream(clip1, clip2),
     * clip2.getFormat(), clip1.getFrameLength() + clip2.getFrameLength());
     * System.out.println("이거뜨나 확인 - 시퀀스 시간 ");
     * 
     * 
     * File testFile = new File("C:\\AudioStorage\\soundEffect\\combinevoices.wav");
     * // AudioSystem.write(appendFiles, AudioFileFormat.Type.WAVE, new //
     * File("C:\\AudioStorage\\soundEffect\\combinevoices.wav"));
     * AudioSystem.write(appendFiles, AudioFileFormat.Type.WAVE,testFile);
     * 
     * 
     * 
     * } catch (Exception e) { e.printStackTrace(); }
     * 
     * try { FileInputStream clip1 = new FileInputStream(soundEffect);
     * FileInputStream clip2 = new FileInputStream(audioNameWithPath);
     * System.out.println(soundEffect); System.out.println(audioNameWithPath);
     * SequenceInputStream sis = new SequenceInputStream(clip1, clip2);
     * FileOutputStream fos = new
     * FileOutputStream("C:\\AudioStorage\\soundEffect\\teasdaazztAA.wav"); // 덮어쓰는
     * 부분 int data = sis.read(); while (data != -1) { // 너무 오래걸림
     * System.out.println(data); data = sis.read(); fos.write(data); } fos.close();
     * sis.close(); clip1.close(); clip2.close(); } catch (Exception e) {
     * e.printStackTrace(); }
     * 
     * }
     */

}