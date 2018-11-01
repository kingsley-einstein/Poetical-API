package com.poetical.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;

import com.poetical.api.models.Message;
import com.poetical.api.models.MessageText;
import com.poetical.api.repositories.MessageRepository;
import com.poetical.api.repositories.MessageTextRepository;
import com.poetical.api.repositories.UserRepository;

import java.util.Map;

@RestController
@RequestMapping(value = "/messages")
public class MessageController {
    
    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private MessageTextRepository messageTextRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping(value = "/all/{page}")
    @ResponseBody
    public Page<Message> getAllMessagesByAuthor(@PathVariable("page") Integer page, @RequestParam("author") Long id) {
        Page<Message> messages = messageRepo.findByAuthor(userRepo.findById(id).get(), PageRequest.of(page, 25));

        return messages;
    }

    @PostMapping(value = "/create")
    @ResponseBody
    public Message createMessage(@RequestParam("author") String author_username, @RequestParam("recipient") String recipient_username, @RequestBody Map<String, String> body) {
        Message message = new Message(false, userRepo.findByUsername(author_username), userRepo.findByUsername(recipient_username));
        Message createdMessage = messageRepo.save(message);
        MessageText text = new MessageText(body.get("text"), createdMessage, userRepo.findByUsername(author_username));
        messageTextRepo.save(text);
        
        return createdMessage;
    }

    @PostMapping(value = "/send_text")
    @ResponseBody
    public Page<MessageText> sendAText(@RequestParam("author") String author_username, @RequestParam("message") Long message_id, @RequestParam("page") Integer page, @RequestBody Map<String, String> body) {
        MessageText messageText = new MessageText(body.get("text"), messageRepo.findById(message_id).get(), userRepo.findByUsername(author_username));
        messageTextRepo.save(messageText);

        return messageTextRepo.findByMessage(messageRepo.findById(message_id).get(), PageRequest.of(page, 10));
    }

    @DeleteMapping(value = "/delete")
    @ResponseBody
    public Page<Message> deleteMessages(@RequestParam("message_id") Long id, @RequestParam("id") Long author_id, @RequestParam("page") Integer page) {
        messageRepo.delete(messageRepo.findById(id).get());

        return messageRepo.findByAuthor(userRepo.findById(author_id).get(), PageRequest.of(page, 25));
    }
}