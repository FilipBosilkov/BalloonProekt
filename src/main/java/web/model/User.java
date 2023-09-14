package web.model;

import lombok.Data;
import web.model.enumeration.Role;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Table(name = "balloon_shop_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @Transient
    private boolean isAccountNonExpired = true;
    @Transient
    private boolean isAccountNonLocked = true;
    @Transient
    private boolean isCredentialsNonExpired = true;
    @Transient
    private boolean isEnabled = true;

    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Convert(converter = UserFullnameConverter.class)
    private UserFullname userFullname;
    private String password;

    @Transient
    DateTimeFormatterRegistrar dateTimeRegistrar = new DateTimeFormatterRegistrar();

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    public User(String username,String password, String name, String surname, LocalDate dateOfBirth, Role role) {
        this.username = username;
        this.userFullname = new UserFullname(name, surname);
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
    }

    public User() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
