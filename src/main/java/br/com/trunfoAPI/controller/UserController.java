package br.com.trunfoAPI.controller;

import br.com.trunfoAPI.model.dto.UserDTO;
import br.com.trunfoAPI.model.entity.User;
import br.com.trunfoAPI.model.enums.TypeUser;
import br.com.trunfoAPI.service.UserService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@CrossOrigin
public class UserController implements ImplementarController<User, UserDTO> {

    @Autowired
    private UserService userService;

    @Value("${chaveacesso}")
    private String chaveacesso;

    @Value("${chavesecreta}")
    private String chavesecreta;

    @Value("${bucketname}")
    private String bucketname;

    @PostMapping("/uploadImage/{id}")
    public ResponseEntity<PutObjectRequest> post(@RequestParam MultipartFile image,
                                                 @PathVariable Long id) throws IOException {

        try {
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(chaveacesso, chavesecreta);
            AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();

            String keyName = UUID.randomUUID().toString();

            PutObjectRequest imageObject = new PutObjectRequest(bucketname, keyName, image.getInputStream(), null);
            amazonS3Client.putObject(imageObject);

            User user = userService.listOne(id);
            user.setLinkProfile(new File(keyName));

            userService.save(user);
        } catch (AmazonS3Exception e) {
            System.exit(0);
        }
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid UserDTO dto) {
        return ResponseEntity.ok(userService.create(dto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<User>> listAll() {
        return ResponseEntity.ok(userService.listAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<User> listOne(@PathVariable Long id) {
        return ResponseEntity.ok(userService.listOne(id));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody @Valid UserDTO dto, @PathVariable Long id) {
        return ResponseEntity.ok(userService.update(dto, id));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userService.delete(id));
    }

    @GetMapping("/types")
    public ResponseEntity<TypeUser[]> typesUser() {
        return ResponseEntity.ok(userService.typesUser());
    }
}
