package es.eukariotas.apiservice.controller;

import es.eukariotas.apiservice.persistence.entity.Turn;
import es.eukariotas.apiservice.persistence.repository.TurnoRepository;
import es.eukariotas.apiservice.service.TurnoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turno")
public class TurnoController{

    private final TurnoService turnoService;
    @Autowired
    private HttpServletRequest request;
    private final TurnoRepository turnoRepository;

    public TurnoController(TurnoService turnoService,
                           TurnoRepository turnoRepository) {
        this.turnoService = turnoService;
        this.turnoRepository = turnoRepository;
    }

    @GetMapping
    public List<Turn> getTurnos() {
        return turnoService.getAllTurnos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turn> getTurnoById(Long id) {
        Turn turno = null;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        HttpHeaders headers = new HttpHeaders();
        try {
            //partidaService.verifyHeader(request);
            turno = turnoService.getTurnoById(id);
            status = HttpStatus.OK;

        } catch (Exception e) {
            //lanzar respuesta con error
        }
        return new ResponseEntity<>(turno, headers, status);
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> saveTurno(@RequestBody Turn turno){
        Boolean created = false;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        HttpHeaders headers = new HttpHeaders();
        String user = request.getHeader("user");
        try {
            //partidaService.verifyHeader(request);
            turnoService.saveTurno(turno, user);
            created = true;
            status = HttpStatus.OK;
        } catch (Exception e) {
            //lanzar respuesta con error
        }
        return new ResponseEntity<>(created, headers, status);
    }
}
