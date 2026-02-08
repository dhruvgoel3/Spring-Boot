package com.example.spring_security;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SecurityController {
    @PreAuthorize("/hasRole('ADMIN' , 'USER')")
    @GetMapping
    public String healthCheck() {
        return "Health is OK";
    }

    @GetMapping("/admin/hello")
    public String sayAdminHello() {
        return "admin is OK";
    }

    @GetMapping("/user/hello")
    public String sayUserHello() {
        return "User is OK";
    }
}
