package dungcts.backendapi.com.shoplaptop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dungcts.backendapi.com.shoplaptop.dto.ContactDTO;
import dungcts.backendapi.com.shoplaptop.entity.Contact;
import dungcts.backendapi.com.shoplaptop.responsitory.ContactRepository;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Transactional
    public ContactDTO createContact(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setName(contactDTO.getName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhone(contactDTO.getPhone());
        contact.setTitle(contactDTO.getTitle());
        contact.setContent(contactDTO.getContent());

        contact = contactRepository.save(contact);
        return new ContactDTO(contact.getCategoryId(), contact.getName(), contact.getEmail(), contact.getPhone(),
                contact.getTitle(), contact.getContent());
    }

    public ContactDTO getContactById(Long categoryId) {
        return contactRepository.findContactDTOById(categoryId);
    }

    @Transactional
    public ContactDTO updateContact(Long categoryId, ContactDTO contactDTO) {
        Optional<Contact> optionalContact = contactRepository.findById(categoryId);
        if (optionalContact.isPresent()) {
            Contact contact = optionalContact.get();
            contact.setName(contactDTO.getName());
            contact.setEmail(contactDTO.getEmail());
            contact.setPhone(contactDTO.getPhone());
            contact.setTitle(contactDTO.getTitle());
            contact.setContent(contactDTO.getContent());

            contact = contactRepository.save(contact);
            return new ContactDTO(contact.getCategoryId(), contact.getName(), contact.getEmail(), contact.getPhone(),
                    contact.getTitle(), contact.getContent());
        } else {
            throw new RuntimeException("Contact not found with id: " + categoryId);
        }
    }

    public List<ContactDTO> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();
        return contacts.stream()
                .map(contact -> new ContactDTO(contact.getCategoryId(), contact.getName(), contact.getEmail(),
                        contact.getPhone(), contact.getTitle(), contact.getContent()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteContact(Long categoryId) {
        contactRepository.deleteById(categoryId);
    }

    public List<ContactDTO> getContactsByName(String name) {
        return contactRepository.findContactDTOsByName(name);
    }
}
