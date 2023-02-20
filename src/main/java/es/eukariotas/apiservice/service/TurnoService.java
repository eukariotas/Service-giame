package es.eukariotas.apiservice.service;

import es.eukariotas.apiservice.persistence.entity.Party;
import es.eukariotas.apiservice.persistence.entity.Turn;
import es.eukariotas.apiservice.persistence.entity.User;
import es.eukariotas.apiservice.persistence.repository.PartidaRepository;
import es.eukariotas.apiservice.persistence.repository.TurnoRepository;
import es.eukariotas.apiservice.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService {
    private final TurnoRepository turnoRepository;
    private final PartidaRepository partidaRepository;
    private final UserRepository userRepository;

    public TurnoService(TurnoRepository turnoRepository, PartidaRepository partidaRepository, UserRepository userRepository) {
        this.turnoRepository = turnoRepository;
        this.partidaRepository = partidaRepository;
        this.userRepository = userRepository;
    }

    public void saveTurno(Turn turn) {
        try {
            Party party = turn.getParty();
            party.addTurn(turn);
            User user = turn.getUser();
            user.addTurn(turn);
            partidaRepository.save(party);
            userRepository.save(user);
            turnoRepository.save(turn);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteTurno(Turn turn) {
        turnoRepository.delete(turn);
    }

    public Turn getTurnoById(Long id) {
        return turnoRepository.findById(id).orElse(null);
    }

    public List<Turn> getAllTurnos() {
        return turnoRepository.findAll();
    }
}
