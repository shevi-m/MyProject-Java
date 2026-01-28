package org.example.demo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

//שולחת לבנאי את שם המשתמש הסיסמא וההרשאות-אצלנו מייל
public class CustomUserDetails extends User {
    private long id;
    public CustomUserDetails(long id,String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.id=id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
