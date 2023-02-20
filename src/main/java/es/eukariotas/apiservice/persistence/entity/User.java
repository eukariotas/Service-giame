package es.eukariotas.apiservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Column(name = "user",nullable = false, unique = true, length = 45)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String userName;

    @Column(name = "email", nullable = false, unique = true, length = 45)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String userEmail;

    @Column(name = "password",nullable = false, unique = true, length = 45)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String password;

    @Column(name = "image", length = 100)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String userImage;

    @Column(name = "description", length = 240)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String userDescription;

    @Column(name = "country", length = 45)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String userCountry;


    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_has_party",
            joinColumns = @JoinColumn(name = "party_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Collection<Party> parties = new ArrayList<>();

    @Column(name = "last_login")
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private LocalDateTime lastLogin;

    @JsonIgnore
    @OneToOne(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private Token token;

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Turn> turns = new ArrayList<>();
    @JsonIgnore
    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private Stadistics stadistics;

    public void addParty(Party party) {
        parties.add(party);
    }

    public void addTurn(Turn turn) {
        turns.add(turn);
    }



}
