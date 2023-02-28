package es.eukariotas.apiservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "turn")
public class Turn {
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long id;

    @Column(name = "info", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String informacion;

    @Column(name = "num_turn", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Integer num_turn;

    @Column(name = "end")
    @JdbcTypeCode(SqlTypes.TINYINT)
    private Boolean end;

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "next_turn_id")
    private Turn next_turn;
}
