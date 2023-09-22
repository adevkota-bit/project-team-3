package controller;

import model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CredentialService;

import java.util.List;

@RestController
@RequestMapping("/credentials")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    // CREATE
    @PostMapping
    public Credential createCredential(@RequestBody Credential credential) {
        return credentialService.createCredential(credential);
    }

    // READ
    @GetMapping("/{username}")
    public Credential getCredential(@PathVariable String username) {
        return credentialService.getCredential(username);
    }

    // UPDATE
    @PutMapping("/{username}")
    public Credential updateCredential(@PathVariable String username, @RequestBody Credential credentialDetails) {
        return credentialService.updateCredential(username, credentialDetails);
    }

    // DELETE
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteCredential(@PathVariable String username) {
        credentialService.deleteCredential(username);
        return ResponseEntity.ok().build();
    }

    // READ ALL
    @GetMapping
    public List<Credential> getAllCredentials() {
        return credentialService.getAllCredentials();
    }
}


