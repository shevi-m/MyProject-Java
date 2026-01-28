package org.example.demo.Controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.example.demo.Model.Category;
import org.example.demo.Model.Thought;
import org.example.demo.Model.Users;
import org.example.demo.Service.*;
import org.example.demo.dto.ChatRequest;
import org.example.demo.dto.ThoughtDTO;
import org.example.demo.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/thoughts")
//האם צריך את זה?
//@CrossOrigin
public class ThoughtController {
    private final CategoryRepository categoryRepository;
    ThoughtRepository thoughtRepository;
    UsersRepository usersRepository;
    ThoughtMapper thoughtMapper;
    private static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\images\\";
    private AIChatService aiChatService;

    @Autowired
    public ThoughtController(ThoughtRepository thoughtRepository, ThoughtMapper thoughtMapper, AIChatService aiChatService, CategoryRepository categoryRepository,UsersRepository userRepository) {
        this.thoughtRepository = thoughtRepository;
        this.thoughtMapper = thoughtMapper;
        this.aiChatService = aiChatService;
        this.categoryRepository = categoryRepository;
        this.usersRepository=userRepository;
    }

    //V
    @GetMapping("/getThought/{id}")
    public ResponseEntity<ThoughtDTO> getThought(@PathVariable long id) throws IOException {
        Thought thought = thoughtRepository.findById(id).orElse(null);
        if (thought != null)
            return new ResponseEntity<>(thoughtMapper.thoughtToDTO(thought), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //
    @GetMapping("/getThoughtsByUserId/{id}")
    public ResponseEntity<List<ThoughtDTO>> getThoughtsByUserId(@PathVariable long id) throws IOException {
        List<Thought> thoughts = thoughtRepository.getThoughtsByUser_Id(id);
        if (thoughts != null)
            return new ResponseEntity<>(thoughtMapper.thoughtsToDTO(thoughts), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //V
    @GetMapping("/getAllThoughts")
    public ResponseEntity<List<ThoughtDTO>> getAllThoughts() {
        try {
            List<Thought> thoughts = thoughtRepository.findAll();
            return new ResponseEntity<>(thoughtMapper.thoughtsToDTO(thoughts), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //V
//    @PreAuthorize("hasRole('ADMIN') or #thought.user.id == authentication.principal.id")
    @PostMapping("/uploadThought")
    public ResponseEntity<Thought> uploadThoughtWithPicture(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestPart("image") MultipartFile file, @Valid @RequestPart("thought") Thought thought) {
        Long authenticatedUserId = userDetails.getId();
        Long claimedUserId = thought.getUser().getId();
        if (claimedUserId == null || !claimedUserId.equals(authenticatedUserId)) {
            // החסימה: מחזירים שגיאת 403 (Forbidden) או 401 (Unauthorized)
            // שגיאת 403 היא מתאימה יותר כאן: המשתמש מחובר, אבל אין לו הרשאה לפעולה זו.
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        try {
            ImageUtils.uploadImage(file);
            thought.setImagePath(file.getOriginalFilename());
            Thought t = thoughtRepository.save(thought);
            return new ResponseEntity<>(t, HttpStatus.CREATED);
        } catch (IOException e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @PostMapping("/uploadThought")
//    public ResponseEntity<Thought> uploadThoughtWithPicture(
//            @RequestPart("image") MultipartFile file,
//            @Valid @RequestPart("thought") ThoughtDTO thoughtDTO) {
//        try {
//            Category category=categoryRepository.findById(thoughtDTO.getCategoryDTO().getId())
//            ImageUtils.uploadImage(file);
//            thought.setImagePath(file.getOriginalFilename());
//            Thought t = thoughtRepository.save(thought);
//            return new ResponseEntity<>(t, HttpStatus.CREATED);
//        } catch (IOException e) {
//            System.out.println(e);
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    //    @PreAuthorize("hasRole('ADMIN') or @thoughtOwnershipService.isOwner(#id, authentication.principal.id)")
    @DeleteMapping("deleteThought/{id}")
    public ResponseEntity<?> deleteThought(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable long id) {
        try {

            Thought thought = thoughtRepository.findById(id).orElse(null);
            if (thought != null) {
                Long authenticatedUserId = userDetails.getId();
                Users user=this.usersRepository.findById(authenticatedUserId).orElse(null);
                Long claimedUserId = thought.getUser().getId();
                if (!claimedUserId.equals(authenticatedUserId)) {
                    // החסימה: מחזירים שגיאת 403 (Forbidden) או 401 (Unauthorized)
                    // שגיאת 403 היא מתאימה יותר כאן: המשתמש מחובר, אבל אין לו הרשאה לפעולה זו.
                    return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
                }
                this.thoughtRepository.delete(thought);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/chat")
//    public Flux<String> getResponse(@RequestBody ChatRequest chatRequest) {

    public String getResponse(@RequestBody ChatRequest chatRequest) {
        return aiChatService.getResponse2(chatRequest.message(), chatRequest.conversationId());
    }
}
