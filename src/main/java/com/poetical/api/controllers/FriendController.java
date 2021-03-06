package com.poetical.api.controllers;

//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;

import com.poetical.api.models.FriendRequest;
import com.poetical.api.models.User;
import com.poetical.api.models.Friend;
import com.poetical.api.repositories.FriendRequestRepository;
import com.poetical.api.repositories.UserRepository;
import com.poetical.api.repositories.FriendRepository;

@RestController
@RequestMapping(value = "/friends")
public class FriendController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private FriendRequestRepository fReqRepo;

    @Autowired
    private FriendRepository fRepo;

    @GetMapping(value = "/send")
    public void sendRequest(@RequestParam("recepient_id") Long recepient_id, @RequestParam("sender_id") Long sender_id) {
        FriendRequest request = new FriendRequest(userRepo.findById(recepient_id).get(), userRepo.findById(sender_id).get());
        fReqRepo.save(request);
    }

    @GetMapping(value = "/accept")
    public void acceptRequest(@RequestParam("received_id") Long sender_id, @RequestParam("receiver_id") Long recepient_id, @RequestParam("request_id") Long request_id) {
        User user = userRepo.findById(recepient_id).get();
        user.addFriend(userRepo.findById(sender_id).get());
        userRepo.save(user);

        fReqRepo.delete(fReqRepo.findById(request_id).get());
    }

    @DeleteMapping(value = "/reject")
    public void rejectRequest(@RequestParam("request_id") Long id) {
        fReqRepo.deleteById(id);
    }

    @GetMapping(value = "/frequests/{page}")
    @ResponseBody
    public Page<FriendRequest> getAllRequestsByRecepient(@PathVariable("page") Integer page, @RequestParam("user_id") Long id) {
        Page<FriendRequest> requests = fReqRepo.findByRecepient(userRepo.findById(id).get(), PageRequest.of(page, 25));

        return requests;
    }

    @GetMapping(value = "/all_friends/{page}")
    @ResponseBody
    public Page<Friend> getAllFriendsByUser(@PathVariable("page") Integer page, @RequestParam("user_id") Long id) {
        Page<Friend> friends = fRepo.findByUser(userRepo.findById(id).get(), PageRequest.of(page, 20));

        return friends;
    }

}