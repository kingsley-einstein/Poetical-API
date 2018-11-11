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

import com.poetical.api.exceptions.InvalidMimeTypeException;
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
        ProfilePic profilePic = picRepo.findByOwner(userRepo.findById(id).get());

        if (profilePic == null) {
            profilePic = new ProfilePic(pic.getBytes(), pic.getContentType());
            profilePic.setOwner(userRepo.findById(id).get());

        if (profilePic.getMimeType().contains("image")) {
            picRepo.save(profilePic);

             return "Picture successfully uploaded";
        }
        else {
            throw new InvalidMimeTypeException(String.format("Expected an image file but found %s", pic.getContentType()));
        }
        } 
        else {
            profilePic.setData(pic.getBytes());
            profilePic.setMimeType(pic.getContentType());

            if (profilePic.getMimeType().contains("image")) {
                picRepo.save(profilePic);

                return "Picture successfully uploaded";
            }

            else {
                throw new InvalidMimeTypeException(String.format("Expected an image file but found %s", pic.getContentType())); 
            }
        }
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