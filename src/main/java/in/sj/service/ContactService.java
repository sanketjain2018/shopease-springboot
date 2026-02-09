package in.sj.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import in.sj.entity.ContactMessage;
import in.sj.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactMessageRepository repository;

    public void saveMessage(String name, String email, String subject, String message) {

        ContactMessage msg = new ContactMessage();
        msg.setName(name);
        msg.setEmail(email);
        msg.setSubject(subject);
        msg.setMessage(message);
        msg.setCreatedAt(LocalDateTime.now());

        repository.save(msg);
    }
}
