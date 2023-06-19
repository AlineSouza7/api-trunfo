package br.com.trunfoAPI.controller;

import br.com.trunfoAPI.model.dto.CardDTO;
import br.com.trunfoAPI.model.entity.Card;
import br.com.trunfoAPI.model.entity.User;
import br.com.trunfoAPI.service.CardService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/card")
@CrossOrigin
public class CardController implements ImplementarController<Card, CardDTO> {

    @Autowired
    private CardService cardService;

    @Value("${chaveacesso}")
    private String chaveacesso;

    @Value("${chavesecreta}")
    private String chavesecreta;

    @Value("${bucketname}")
    private String bucketname;

    @Override
    @PostMapping
    public ResponseEntity<Card> create(@RequestBody CardDTO dto) {
        return ResponseEntity.ok(cardService.create(dto));
    }

    @PostMapping("/uploadImage/{id}")
    public ResponseEntity<PutObjectRequest> post(@RequestBody MultipartFile image,
                                                 @PathVariable Long id) throws IOException {

        try {
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(chaveacesso, chavesecreta);
            AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();

            String keyName = UUID.randomUUID().toString();

            if(!image.isEmpty()) {
                PutObjectRequest imageObject = new PutObjectRequest(bucketname, keyName, image.getInputStream(), null);
                amazonS3Client.putObject(imageObject);
            }

            Card card = cardService.listOne(id);
            card.setLinkPicture(keyName);

            cardService.save(card);
        } catch (AmazonS3Exception e) {
            System.exit(0);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/image/{keyName}")
    public ResponseEntity<URL> list(@PathVariable String keyName) {
        URL url = null;
        try{
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(chaveacesso, chavesecreta);
            AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();

            if(amazonS3Client.doesBucketExist(bucketname)){
                url = amazonS3Client.generatePresignedUrl(bucketname, keyName,
                        DateTime.now().plusDays(1).toDate());
            }

        } catch (AmazonS3Exception e) {
            System.exit(0);
        }
        return ResponseEntity.ok(url);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Card>> listAll() {
        return ResponseEntity.ok(cardService.listAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Card> listOne(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.listOne(id));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Card> update(@RequestBody @Valid CardDTO dto,@PathVariable Long id) {
        return ResponseEntity.ok(cardService.update(dto,id));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.delete(id));
    }
}
