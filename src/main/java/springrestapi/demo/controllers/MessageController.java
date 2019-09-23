package springrestapi.demo.controllers;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springrestapi.demo.models.Message;
import springrestapi.demo.repo.MessageRepo;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageRepo messageRepo;

    @GetMapping()
    public List<Message> getMessages(){
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    public Optional<Message> getOne(@PathVariable Long id){
        return messageRepo.findById(id);
    }

    @PostMapping
    public  Message createNewMessage(@RequestBody Message message){
        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message update(@PathVariable("id")  Message fromDB , @RequestBody Message message){
        System.out.println(fromDB);
        System.out.println(message);
        BeanUtils.copyProperties(message,fromDB,"id");
        return messageRepo.save(fromDB);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        messageRepo.deleteById(id);
    }
}
