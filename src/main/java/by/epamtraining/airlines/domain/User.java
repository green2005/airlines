package by.epamtraining.airlines.domain;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

@Entity
@Table(name = "User_Table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Min(value = 3)
    private String name;

    private boolean accountActivated = false;

    @Min(value = 5)
    @Column(unique = true)
    private String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    Set<UserCredentials> credentials = new HashSet<>();

    public String getCRC() {
        return Long.toString(crc32(String.format("%s;%s", getEmail(), getName())));
    }

    private static long crc32(String input) {
        byte[] bytes = input.getBytes();
        Checksum checksum = new CRC32();
        checksum.update(bytes, 0, bytes.length);
        return checksum.getValue();
    }

    private UserRole userRole;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public boolean isAccountActivated() {
        return accountActivated;
    }

    public void setAccountActivated(boolean accountActivated) {
        this.accountActivated = accountActivated;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCredentials(Set<UserCredentials> credentials) {
        this.credentials = credentials;
    }

    public Set<UserCredentials> getCredentials() {
        return credentials;
    }
}
