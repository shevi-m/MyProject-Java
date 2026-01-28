package org.example.demo.Controller;

import org.example.demo.Model.Likes;
import org.example.demo.Model.Thought;
import org.example.demo.Model.Users;
import org.example.demo.Service.LikesRepository;
import org.example.demo.Service.ThoughtRepository;
import org.example.demo.Service.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/likes")
public class LikesController {
    private final UsersRepository usersRepository;
    private final ThoughtRepository thoughtRepository;
    LikesRepository likesRepository;

    @Autowired
    public LikesController(LikesRepository likesRepository, UsersRepository usersRepository, ThoughtRepository thoughtRepository) {
        this.usersRepository = usersRepository;
        this.thoughtRepository = thoughtRepository;
        this.likesRepository = likesRepository;
    }

    //V
    @GetMapping("/getLike/{id}")
    public ResponseEntity<Likes> get(@PathVariable long id) {
        Likes likes = likesRepository.findById(id).get();
        if (likes != null)
            return new ResponseEntity<>(likes, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //V
    @GetMapping("/getLikes")
    public ResponseEntity<List<Likes>> getAllLikes() {
        try {
            List<Likes> likes = likesRepository.findAll();
            return new ResponseEntity<>(likes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //V
//    @PostMapping("/addLikes")
//    public ResponseEntity<Likes> post(@RequestBody Likes  likes) {
//        try {
//            Likes  like = likesRepository.save( likes);
//            return new ResponseEntity<>( like, HttpStatus.CREATED);
//        } catch (Exception e) {
//            System.out.println(e);
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }



    @PostMapping("/addLikes")
    public ResponseEntity<Likes> post(@RequestBody Map<String, Long> requestData) {

        // אנו משתמשים ב-try-catch כדי ללכוד שגיאות כלליות (כמו בעיות DB),
        // אבל עדיף לטפל בשגיאות לוגיות (כמו 404) בצורה יותר ספציפית
        try {
            // 1. **בדיקת קיום לייק קודם**
            Long userId = requestData.get("userId");
            Long thoughtId = requestData.get("thoughtId");
            Optional<Likes> existingLike = likesRepository.findByUserIdAndThoughtId(userId, thoughtId);

            // **תיקון קריטי #1:** בדיקת Optional נכונה היא isPresent()
            if (existingLike.isPresent()) {
                // אם הלייק כבר קיים, מחזירים סטטוס 409 Conflict או 200 OK עם הלייק הקיים
                return new ResponseEntity<>(existingLike.get(), HttpStatus.CONFLICT);
            }

            // 2. **שליפת User ו-Thought (או זריקת שגיאה)**

            // **תיקון קריטי #2:** שימוש ב-orElseThrow() לשליפה בטוחה
            // אם ה-User או ה-Thought לא קיימים, תזרק שגיאת 404
            Users user = usersRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "המשתמש לא נמצא"));

            Thought thought = thoughtRepository.findById(thoughtId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "הפוסט לא נמצא"));

            // 3. **יצירה ושמירה של הלייק החדש**

            // יצירת אובייקט ה-Likes החדש
            Likes newLike = new Likes(user, thought);

            // **תיקון קריטי #3:** חובה לשמור את האובייקט ב-Repository
            Likes savedLike = likesRepository.save(newLike);

            // 4. החזרת התגובה עם סטטוס 201 Created
            return new ResponseEntity<>(savedLike, HttpStatus.CREATED);

        } catch (ResponseStatusException e) {
            // לכידת שגיאות 404/Conflict שזרקנו בעצמנו והחזרת התגובה המתאימה
            throw e;

        } catch (Exception e) {
            // לכידת שגיאות כלליות (כמו בעיות בבסיס הנתונים)
            System.err.println("שגיאה בהוספת לייק: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
