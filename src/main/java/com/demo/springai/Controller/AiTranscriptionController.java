package com.demo.springai.Controller;

import jakarta.annotation.Resource;
import org.springframework.ai.openai.OpenAiAudioTranscriptionClient;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
