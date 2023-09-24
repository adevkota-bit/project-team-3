package Employee_Management_System.credential;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CredentialService {

    @Autowired
    private CredentialRepository credentialRepository;

    public Credential createAccount(Credential credential) {
        // Hash the password and save the credential
        //String hashedPassword = password;
        String password = credential.getPasswordHash();
        credential.setPasswordHash(password);
        return credentialRepository.save(credential);
    }

    public Credential login(String username, String password) {
        Optional<Credential> optionalCredential = credentialRepository.findByUsername(username);

        if(optionalCredential.isPresent()) {
            Credential credential = optionalCredential.get();
            if(password.equals(credential.getPasswordHash())) {
                return credential;
            }
        }
        return null;
    }

//    // READ
//    public Credential getCredential(String username) {
//        return credentialRepository.findById(username)
//                .orElseThrow(() -> new ResourceNotFoundException("Credential", "username", username));
//    }
//
//    // UPDATE
//    public Credential updateCredential(String username, Credential credentialDetails) {
//        Credential credential = getCredential(username); // or use findById(username).orElseThrow(...)
//        // Set fields that need to be updated
//        credential.setPasswordHash(credentialDetails.getPasswordHash());
//        return credentialRepository.save(credential);
//    }
//
//    // DELETE
//    public void deleteCredential(String username) {
//        Credential credential = getCredential(username); // or use findById(username).orElseThrow(...)
//        credentialRepository.delete(credential);
//    }
//
//    // READ ALL
//    public List<Credential> getAllCredentials() {
//        return credentialRepository.findAll();
//    }
//
//    public Credential saveCredential(Credential credential) {
//        return credentialRepository.save(credential);
//    }
//
//    public Optional<Credential> findCredentialById(String id) {
//        return credentialRepository.findById(id);
//    }
//
//    public void deleteCredential(Credential credential) {
//        credentialRepository.delete(credential);
//    }
}

