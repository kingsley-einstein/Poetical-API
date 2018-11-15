package com.poetical.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.poetical.api.models.Audio;
import com.poetical.api.repositories.AudioRepository;
import com.poetical.api.repositories.UserRepository;
import com.poetical.api.exceptions.InvalidMimeTypeException;

import java.util.Map;
import java.util.Date;
import java.io.IOException;

@RestController
@RequestMapping(value = "/audios")
public class AudioController {
    
    @Autowired
    private AudioRepository audioRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping(value = "/all/{page}")
    @ResponseBody
    public Page<Audio> getAllAudios(@PathVariable("page") Integer page) {
        Page<Audio> audios = audioRepo.findAll(PageRequest.of(page, 12));

        return audios;
    }

    @GetMapping(value = "personal/all/{page}")
    @ResponseBody
    public Page<Audio> getAllAudiosByUser(@PathVariable("page") Integer page, @RequestParam("user_id") Long id) {
        Page<Audio> audios = audioRepo.findAllByAuthor(userRepo.findById(id).get(), PageRequest.of(page, 12));

        return audios;
    }

    @PostMapping(value = "/create")
    @ResponseBody
    public Audio createAudio(@RequestParam("file") MultipartFile file, @RequestParam("user_id") Long id) throws IOException {
        Audio audio = new Audio(new Date(), file.getBytes(), file.getContentType(), userRepo.findById(id).get());

        if (audio.getMimeType().contains("audio")) {
            return audioRepo.save(audio);
        }
        else {
            throw new InvalidMimeTypeException(String.format("Invalid file content type. Required audio/* but found %s", file.getContentType()));
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteAudio(@PathVariable("id") Long id) {
        audioRepo.deleteById(id);
    }


}