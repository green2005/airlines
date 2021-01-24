package by.epamtraining.airlines.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

@Entity
@Table(name = "User_Table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    private boolean accountActivated = false;

    @NotNull
    @Column(unique = true)
    private String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserCredentials> credentialsSet = new HashSet<>();

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

    public void addCredentials(UserCredentials credentials){
        credentialsSet.add(credentials);
    }

    public void setCredentialsSet(Set<UserCredentials> credentialsSet) {
        this.credentialsSet = credentialsSet;
    }

    public Set<UserCredentials> getCredentialsSet() {
        return credentialsSet;
    }
}
