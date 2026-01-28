package org.example.demo.Controller;

import jakarta.validation.Valid;
import org.example.demo.Model.Users;
import org.example.demo.Service.ImageUtils;
import org.example.demo.Service.RoleRepository;
import org.example.demo.Service.UsersMapper;
import org.example.demo.Service.UsersRepository;
import org.example.demo.dto.UsersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.example.demo.security.CustomUserDetails;
import org.example.demo.security.jwt.JwtUtils;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private UsersRepository usersRepository;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private UsersMapper userMapper;
    private JwtUtils jwtUtils;

    @Autowired
    public UsersController(UsersRepository usersRepository, UsersMapper userMapper, RoleRepository roleRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.usersRepository = usersRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    //V
    @GetMapping("/getUser/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UsersDTO> getUser(@PathVariable long id) throws IOException {
        Users user = usersRepository.findById(id).orElse(null);
        if (user != null)
            return new ResponseEntity<>(userMapper.userToDTO(user), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //?
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UsersDTO>> getAllUsers() {
        try {
            List<Users> users = usersRepository.findAll();
            return new ResponseEntity<>(userMapper.usersToDTO(users), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //V
//    @PostMapping("/uploadUser")
    @PostMapping("/signUp")
    public ResponseEntity<Users> uploadUser(@RequestPart(value = "image", required = false) MultipartFile file,@Valid @RequestPart("user") Users user) throws IOException {
        try {
            if (usersRepository.existsUsersByEmail(user.getEmail())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            //הצפנה
            String pass = user.getPassword();
            user.setPassword(new BCryptPasswordEncoder(8).encode(pass));
            user.getRoles().add(roleRepository.findById((long) 1).get());
            if (file!=null){
                ImageUtils.uploadImage(file);
                user.setImagePath(file.getOriginalFilename());
            }
            Users u = usersRepository.save(user);

       return new ResponseEntity<>(u , HttpStatus.CREATED);

        } catch (IOException e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //הוספתי פונקצית התחברות
//    @PostMapping("/signIn")
//    public ResponseEntity signIn(@RequestBody Users user) {
//        try {
//            if (usersRepository.existsUsersByEMail(user.geteMail())) {
//                Users u = usersRepository.getUsersByEMail(user.geteMail());
//                if (u.getPassword().equals(user.getPassword())) {
//                    return new ResponseEntity<>(u.getId(), HttpStatus.OK);
//                }
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @PostMapping("/signIn")
//    //<?> האם בככונה לא היה פה:
//    public ResponseEntity<?> signIn(@Valid @RequestBody Users user) {
//        try {
//            Authentication authentication = authenticationManager
//                    .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
//            //שומר את האימות
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            //userDetails מכניס את הפרטים ל
//            //CustomUserDetails מסוג
//            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//            //מכניס את הפרטים לעוגיה
//            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
//
//            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//                    .body(userDetails.getUsername());
//
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

    @PostMapping("/signIn")
    //<?> האם בככונה לא היה פה:
    public ResponseEntity<Users> signIn(@Valid @RequestBody Users user) {
        try {
            Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();

            if (existingAuth != null && existingAuth.isAuthenticated() &&
                    !(existingAuth.getPrincipal() instanceof String && existingAuth.getPrincipal().equals("anonymousUser"))) {

                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            //שומר את האימות
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //userDetails מכניס את הפרטים ל
            //CustomUserDetails מסוג
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            //מכניס את הפרטים לעוגיה
            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

            // userDetails.getUsername() מחזיר את האימייל המאושר
            String userEmail = userDetails.getUsername();

            // שליפת המשתמש המלא לפי האימייל
            Users authenticatedUser = usersRepository.getUsersByEmail(userEmail);

            // בדיקה לשגיאה: אם המשתמש נמצא ב-DB
            if (authenticatedUser == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // שלב 4: החזרת התגובה המוצלחת (משתמש בגוף, JWT בכותרת)
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(authenticatedUser);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    @PostMapping("/signOut")
    public ResponseEntity<?> signOut() {
        try {
            ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body("you've been signed out!");

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}

