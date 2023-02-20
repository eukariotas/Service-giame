package es.eukariotas.apiservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "party")
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long id;

    @Column(name = "winner")
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long ganador;

    @Column(name = "date", nullable = false)
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private LocalDateTime comienzo_partida;

    @Column(name = "max_players", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Integer max_players;

    @Column(name = "tipe_game", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String tipe_game;

    @Column(name = "status", nullable = false)//puede ser open, started o finished
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String status;

    @ManyToMany(mappedBy = "parties")
    private Collection<User> users = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "party", orphanRemoval = true)
    private List<Turn> turns = new ArrayList<>();

    public void addUsers(User user) {
        users.add(user);
    }

    public void addTurn(Turn turn) {
        turns.add(turn);
    }



}