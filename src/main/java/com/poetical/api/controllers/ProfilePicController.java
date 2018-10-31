package com.poetical.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.poetical.api.exceptions.NotFoundException;
import com.poetical.api.models.ProfilePic;
import com.poetical.api.repositories.ProfilePicRepository;
import com.poetical.api.repositories.UserRepository;

import java.io.IOException;

@RestController
@RequestMapping(value = "/pictures")
public class ProfilePicController {
    
    @Autowired
    private ProfilePicRepository picRepo;

    @Autowired
    private UserRepository userRepo;

    @PostMapping(value = "/new_picture")
    @ResponseBody
    public String uploadPic(@RequestParam("user_id") Long id, @RequestParam("picture") MultipartFile pic) throws IOException {
        ProfilePic profilepic = new ProfilePic(pic.getBytes(), pic.getContentType());
        profilepic.setOwner(userRepo.findById(id).get());

        picRepo.save(profilepic);

        return "Picture successfully uploaded";
    }

    @PutMapping(value = "/update")
    @ResponseBody
    public String editPic(@RequestParam("user_id") Long id, @RequestParam("newfile") MultipartFile file) throws IOException {
        ProfilePic pic = picRepo.findByOwner(userRepo.findById(id).get());

        pic.setData(file.getBytes());
        pic.setMimeType(file.getContentType());

        picRepo.save(pic);

        return "Picture updated successfully";
    } 

    @DeleteMapping(value = "/delete")
    @ResponseBody
    public String deletePic(@RequestParam("pic_id") Long id) {
        ProfilePic pic = picRepo
                         .findById(id)
                         .orElseThrow(() -> new NotFoundException("Profile pic not found"));

        picRepo.delete(pic);

        return "Picture successfully deleted";
    }

    


}