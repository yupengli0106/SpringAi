package com.demo.springai.Controller;

import com.demo.springai.utils.FileUtil;
import jakarta.annotation.Resource;
import org.springframework.ai.openai.OpenAiAudioSpeechClient;
import org.springframework.ai.openai.OpenAiAudioTranscriptionClient;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

/**
 * @Author: Yupeng Li
 * @Date: 10/5/2024 17:19
 * @Description:
 */
@RestController
public class AiTranscriptionController {

    @Resource
    private OpenAiAudioTranscriptionClient openAiAudioTranscriptionClient;

    @Resource
    private OpenAiAudioSpeechClient openAiAudioSpeechClient;


    /**
     * Transcribe audio to text
     * @param audioUrl audio URL from user
     * @return transcript text from audio
     */
    @RequestMapping("/transcription")
    public Object transcription(@RequestParam("url") String audioUrl) {
        if (Objects.isNull(audioUrl)) {
            return "Please provide audio URL";
        }
        // 获取音频作为资源，然后传给OpenAiAudioTranscriptionClient 进行转换
        // 这里是获取的本地音频文件(FileSystemResource)，后期可以改成从网络获取或者class path获取
        org.springframework.core.io.Resource audioFile = new FileSystemResource(audioUrl);

        // 获取音频转换后的文本
        String transcriptText = openAiAudioTranscriptionClient.call(audioFile);
        return transcriptText;
    }


    /**
     * Convert text to speech
     * @param message message from user
     * @return text to speech successfully
     * @throws IOException if I/O error occurs
     * 文本转换成MP3格式的音频保存在audio文件夹下
     */
    @RequestMapping("/speech")
    public Object speech(@RequestParam("msg") String message) throws IOException {
        if (Objects.isNull(message)) {
            return "Please provide message";
        }

        // 文字转语音，返回的是字节数组
        byte[] audio = openAiAudioSpeechClient.call(message);

        // 通过工具类把字节数组转成音频文件，保存到audio文件夹下
        try {
            FileUtil.writeBytesToMp3File("audio", audio);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "Text to speech successfully!";
    }
}
