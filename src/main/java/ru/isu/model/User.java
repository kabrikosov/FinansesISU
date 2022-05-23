package ru.isu.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String name;

    private Integer monthlyIncome;

    private Integer cashAvailable;

    private String login;

    @Transient
    private String pass;

    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    public void resetPasswor(){
        this.password = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(this.pass);
    }

    public void setPassword(String pass){
        this.pass = pass;
        this.password = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(pass);
    }

    public User(String name, Integer monthlyIncome, Integer cashAvailable, String login, String pass) {
        this.name = name;
        this.monthlyIncome = monthlyIncome;
        this.cashAvailable = cashAvailable;
        this.login = login;
        setPassword(pass);
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
