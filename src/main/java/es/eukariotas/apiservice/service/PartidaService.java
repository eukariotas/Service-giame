package es.eukariotas.apiservice.service;

import es.eukariotas.apiservice.exceptions.CustomExceptions;
import es.eukariotas.apiservice.persistence.entity.Party;
import es.eukariotas.apiservice.persistence.entity.Turn;
import es.eukariotas.apiservice.persistence.entity.User;
import es.eukariotas.apiservice.persistence.repository.GenericRepository;
import es.eukariotas.apiservice.persistence.repository.PartidaRepository;
import es.eukariotas.apiservice.persistence.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PartidaService extends GenericService{
    @Autowired
    private GenericRepository genericRepository;
    private final PartidaRepository partidaRepository;
    private final UserRepository userRepository;

    public PartidaService( PartidaRepository partidaRepository,
                           UserRepository userRepository){

        this.partidaRepository = partidaRepository;
        this.userRepository = userRepository;
    }

    public void savePartida(Party party) {
        partidaRepository.save(party);
    }

    public void deletePartida(Party party) {
        partidaRepository.delete(party);
    }

    public Party getPartidaById(Long id) {
        return partidaRepository.findById(id).orElse(null);
    }

    public List<Party> getAllPartidas() {
        return partidaRepository.findAll();
    }

    public void verifyHeader(HttpServletRequest request) throws CustomExceptions {
        Map<String,String> headers = headers(request);
        if (!genericRepository.verifyToken(headers.get("user"),headers.get("token"))){
            throw new CustomExceptions("token invalido");
        }
    }


    public Party createParty(HttpServletRequest request,String tipe) throws CustomExceptions {
        //verifyHeader(request);
        Map<String,String> headers = headers(request);
        String user_id = headers.get("user");
        User user = userRepository.findById(Long.parseLong(user_id)).orElse(null);
        if (user != null){
            Party party = Party.createParty(user,tipe);
            Party partySaved = partidaRepository.save(party);
            user.addParty(partySaved);

            return partySaved;
        }
        return null;
    }

    public List<Turn> getTurnos(Long id) {
        Party party = partidaRepository.findById(id).orElse(null);
        if (party != null){
            return party.getTurns();
        }
        return null;
    }

    public Party joinParty(Long id, HttpServletRequest request) {
        try {
        Party party = partidaRepository.findById(id).orElse(null);
        User user = userRepository.findById(Long.parseLong(headers(request).get("user"))).orElse(null);
        if (party != null && user != null){
            party.addUsers(user);
            party.setStatus("started");
            Party saved = partidaRepository.save(party);
            System.out.println("user: " + user.getId() + " se unio a la partida: " + party.getId());
            return saved;
        }else {
            throw new CustomExceptions("No se ha podido unir a la partida");
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Party getParty(Long id) {
        return partidaRepository.findById(id).orElse(null);
    }

    public List<Party> getOpenParties() {

       List<Party> parties = partidaRepository.findAll();
       return parties.stream().filter(party -> party.getStatus().equals("open")).peek(party -> System.out.println("Devolviendo partida: "+party.getId()+" juego: "+party.getTipe_game())).collect(Collectors.toList());
    }

    public Boolean finishParty(Long id) {
        Party party = partidaRepository.findById(id).orElse(null);
        if (party != null){
            party.setStatus("finished");
            partidaRepository.save(party);
            return true;
        }
        return false;
    }
}
