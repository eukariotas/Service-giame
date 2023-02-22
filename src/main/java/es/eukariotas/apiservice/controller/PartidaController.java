package es.eukariotas.apiservice.controller;

import es.eukariotas.apiservice.persistence.entity.Party;
import es.eukariotas.apiservice.persistence.entity.Turn;
import es.eukariotas.apiservice.service.PartidaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partida")
public class PartidaController{
    private final PartidaService partidaService;

    @Autowired
    private HttpServletRequest request;


    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;

    }

    @GetMapping
    public List<Party> getPartidas() {
        return partidaService.getAllPartidas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Party> getPartida(@PathVariable("id") Long id){
        Party party = null;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        HttpHeaders headers = new HttpHeaders();
        try {
            //partidaService.verifyHeader(request);
            party = partidaService.getParty(id);
            status = HttpStatus.OK;
        } catch (Exception e) {
            //lanzar respuesta con error
        }
        return new ResponseEntity<>(party, headers, status);
    }

    @GetMapping("/open")
    public ResponseEntity<List<Party>> getPartidasAbiertas() {
        List<Party> parties = null;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        HttpHeaders headers = new HttpHeaders();
        try {
            //partidaService.verifyHeader(request);
            parties = partidaService.getOpenParties();
            status = HttpStatus.OK;
        } catch (Exception e) {
            //lanzar respuesta con error
        }
        return new ResponseEntity<>(parties, headers, status);
    }

    @GetMapping("/create/{tipe}")
    public ResponseEntity<Party> crearPartida(@PathVariable("tipe") String tipe){
        Party created = null;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        HttpHeaders headers = new HttpHeaders();
        try {
            //partidaService.verifyHeader(request);
           created = partidaService.createParty(request, tipe);
            status = HttpStatus.OK;

        } catch (Exception e) {
           e.printStackTrace();
        }
        return new ResponseEntity<>(created, headers, status);
    }

    @GetMapping("/turnos/{id}")
    public ResponseEntity<List<Turn>> getTurnos(@PathVariable("id") Long id){
        List<Turn> turnos = null;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        HttpHeaders headers = new HttpHeaders();
        try {
            //TODO: partidaService.verifyHeader(request);
            turnos = partidaService.getTurnos(id);
            status = HttpStatus.OK;
        } catch (Exception e) {
            //TODO: lanzar respuesta con error
        }
        return new ResponseEntity<>(turnos, headers, status);
    }

    @GetMapping("/join/{id}")
    public ResponseEntity<Party> joinPartida(@PathVariable("id") Long id){
        Party joined = null;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        HttpHeaders headers = new HttpHeaders();
        try {
            //partidaService.verifyHeader(request);
            joined = partidaService.joinParty(id, request);
            status = HttpStatus.OK;

        } catch (Exception e) {
            //lanzar respuesta con error
        }
        return new ResponseEntity<>(joined, headers, status);
    }

    @GetMapping("/finish/{id}")
    public ResponseEntity<Boolean> finishPartida(@PathVariable("id") Long id){
        Boolean finished = false;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        HttpHeaders headers = new HttpHeaders();
        try {
            //partidaService.verifyHeader(request);
            finished = partidaService.finishParty(id);
            status = HttpStatus.OK;
            finished = true;
        } catch (Exception e) {
            //lanzar respuesta con error
        }
        return new ResponseEntity<>(finished, headers, status);
    }
}
