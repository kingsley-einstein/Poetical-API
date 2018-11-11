package com.poetical.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import com.poetical.api.models.Poem;
import com.poetical.api.models.Like;
import com.poetical.api.repositories.PoemRepository;
import com.poetical.api.repositories.LikeRepository;
import com.poetical.api.repositories.UserRepository;
import com.poetical.api.exceptions.NotFoundException;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/poems")
public class PoemController {

    @Autowired
    private PoemRepository poemRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private LikeRepository likeRepo;

    @GetMapping(value = "/all/{page}")
    @ResponseBody
    public Page<Poem> getPoems(@PathVariable("page") Integer page) {
        return poemRepo.findAll(PageRequest.of(page, 12));
    }

    @PostMapping(value = "/create")
    @ResponseBody
    public Poem createPoem(@RequestParam("username") String username, @RequestBody Map<String, String> body) {
        Poem poem = new Poem(body.get("title"), body.get("content"), userRepo.findByUsername(username), new Date());

        return poemRepo.save(poem);
    }

    @PutMapping(value = "/update")
    @ResponseBody
    public String update(@RequestParam("id") Long id, @RequestBody Map<String, String> body) {
        Poem poem = poemRepo
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException("Poem with given id not found"));

        poem.setTitle(body.get("title"));
        poem.setContent(body.get("content"));
        poemRepo.save(poem);

        return "Poem updated";
    }

    @PostMapping(value = "/search")
    @ResponseBody
    public Poem searchPoem(@RequestBody Map<String, String> body) {
        Poem poem = poemRepo
                    .findByTitle(body.get("title"))
                    .orElseThrow(() -> new NotFoundException(String.format("Poem with title %s not found", body.get("title"))));

        return poem;
    }

    @DeleteMapping(value = "/delete")
    public void deletePoem(@RequestParam("poem_id") Long id) {
        Poem poem = poemRepo
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException("Poem with given id not found"));

        poemRepo.delete(poem);
    }

    @PostMapping(value = "/find")
    @ResponseBody
    public Page<Poem> findAllByAuthor(@RequestParam("author") String author, @RequestParam("page") Integer page) {
        Page<Poem> poems = poemRepo.findByAuthor(userRepo.findByUsername(author), PageRequest.of(page, 15));

        return poems;
    }

    @GetMapping(value = "/{id}/like")
    public void likePoem(@PathVariable("id") Long id, @RequestParam("user_id") Long user_id) {
        Like like = new Like(userRepo.findById(user_id).get(), poemRepo.findById(id).get());

        likeRepo.save(like);
    }
}