package service;

import model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CredentialRepository;

import java.util.List;
import java.util.Optional;
import Exception.ResourceNotFoundException;


@Service
public class CredentialService {

    @Autowired
    private CredentialRepository credentialRepository;

    // CREATE
    public Credential createCredential(Credential credential) {
        return credentialRepository.save(credential);
    }

    // READ
    public Credential getCredential(String username) {
        return credentialRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("Credential", "username", username));
    }

    // UPDATE
    public Credential updateCredential(String username, Credential credentialDetails) {
        Credential credential = getCredential(username); // or use findById(username).orElseThrow(...)
        // Set fields that need to be updated
        credential.setPasswordHash(credentialDetails.getPasswordHash());
        // ...
        return credentialRepository.save(credential);
    }

    // DELETE
    public void deleteCredential(String username) {
        Credential credential = getCredential(username); // or use findById(username).orElseThrow(...)
        credentialRepository.delete(credential);
    }

    // READ ALL
    public List<Credential> getAllCredentials() {
        return credentialRepository.findAll();
    }

    public Credential saveCredential(Credential credential) {
        return credentialRepository.save(credential);
    }

    public Optional<Credential> findCredentialById(String id) {
        return credentialRepository.findById(id);
    }

    public void deleteCredential(Credential credential) {
        credentialRepository.delete(credential);
    }
}

