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
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "party")
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long id;

    @Column(name = "winner")
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long ganador;

    @Column(name = "date", nullable = false)
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private LocalDateTime comienzo_partida;

    @Column(name = "max_player", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Integer max_players;

    @Column(name = "tipe_game", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String tipe_game;

    @Column(name = "state", nullable = false)//puede ser open, started o finished
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String status;

    @JsonIgnore
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

    public static Party createParty(User user,String tipe_game){
        Party party = new Party();
        party.setComienzo_partida(LocalDateTime.now());
        party.setStatus("open");
        party.setTipe_game(tipe_game);
        party.addUsers(user);
        if (tipe_game == "aviones"){
            party.setMax_players(8);
        }else{
            party.setMax_players(2);
        }
        return party;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Party party)) return false;
        return getId().equals(party.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}