package es.eukariotas.apiservice.service;

import es.eukariotas.apiservice.exceptions.CustomExceptions;
import es.eukariotas.apiservice.persistence.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que se utilizara para metodos comunes de todos los servicios
 */
public class GenericService {


    /**
     * Metodo que verifica los datos del header
     * @param request reques del header
     * @return true en caso de que los datos esten correctos, si no false
     */
    public void verifyHeader(HttpServletRequest request) throws CustomExceptions {

        try{
            Map<String,String> header = headers(request);
            if (header == null){
                System.out.println("El header esta vacio");
            }else {
               verifyToken(header.get("user"),header.get("token"));
            }
        }catch (Exception e){
            throw new CustomExceptions();
        }

    }
    private void verifyToken(String userName, String token){

    }
    private Map<String,String> headers(HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            headers.put(key, value);
        }
        return headers;
    }
}
