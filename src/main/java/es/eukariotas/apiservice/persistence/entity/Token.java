package es.eukariotas.apiservice.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "token")
public class Token {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "token", unique = true, length = 200)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String token;

    @Column(name = "caducity")
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private LocalDateTime caducity;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Create a new token for the user
     * @param user user to associate the token
     * @return created token
     */
    public static Token createToken(User user) {
        Token token = new Token();
        token.setId(randomId());
        token.setToken(UUID.randomUUID().toString());
        token.setCaducity(LocalDateTime.now().plusDays(7));
        token.setUser(user);
        return token;
    }

    public Boolean isExpired() {
        return caducity.isBefore(LocalDateTime.now());
    }

    private static Long randomId() {
        return (long) (Math.random() * 1000000000);
    }


}
