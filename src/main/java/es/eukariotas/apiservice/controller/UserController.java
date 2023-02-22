package es.eukariotas.apiservice.controller;

import es.eukariotas.apiservice.exceptions.CustomExceptions;
import es.eukariotas.apiservice.persistence.entity.Token;
import es.eukariotas.apiservice.persistence.entity.User;
import es.eukariotas.apiservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.event.ListDataEvent;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController{

    private final UserService userService;
    @Autowired
    private HttpServletRequest request;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    /**
     * Loguea un usuario
     * @param user nombre de usuario
     * @param password contraseña
     * @return 200 si se ha logueado correctamente, 400 si no se ha podido loguear
     */
    @GetMapping("/login/{userName}/{password}")
    public ResponseEntity<User> login(@PathVariable(value = "userName") String user, @PathVariable("password") String password){
        System.out.println("usuario: " + user + " password: " + password);
        try {
            User userData = userService.login(user, password);
            if (userData != null) {
                return new ResponseEntity<>(userData, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (CustomExceptions e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/verify/{userName}/{password}")
    public ResponseEntity<String> verify(@PathVariable(value = "userName") String user, @PathVariable("password") String password){
        System.out.printf("cocaina");
            if (userService.verifyUser(user, password)) {
                return new ResponseEntity<>("cocaina", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
            }
    }

    /**
     * Verifica si el token es correcto o si ha expirado
     * @return 200 si es correcto, 401 si no es correcto
     */
    @GetMapping("/verify")
    public ResponseEntity<String> verify(){
        try {
            String token = request.getHeader("token");
            String userName = request.getHeader("userName");
            if (userService.verifyTokenByUser(userName, token)) {
                return new ResponseEntity<>("", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
            }
        } catch (CustomExceptions e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(){
        try {
            String token = request.getHeader("token");
            String userName = request.getHeader("userName");
            if (userService.verifyTokenByUser(userName, token)) {
                userService.logout(userName, token);
                return new ResponseEntity<>("", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
            }
        } catch (CustomExceptions e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/register/{password}/{email}")
    public ResponseEntity<String> register(@RequestBody String userName, @PathVariable("password") String password, @PathVariable("email") String email){
        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setPassword(password);
        newUser.setUserEmail(email);

        try {
            userService.registerUser(newUser);
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (CustomExceptions e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
