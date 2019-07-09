package com.demo.service.impl;

import com.demo.bean.Campana;
import com.demo.bean.Consultant;
import com.demo.bean.UsuarioRequest;
import com.demo.model.Cliente;
import com.demo.model.Promocion;
import com.demo.repository.BookCategoryRepository;
import com.demo.repository.PromocionRepository;
import com.demo.repository.UsuarioRepository;
import com.demo.service.DemoService;
import com.demo.util.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @Autowired
    private PromocionRepository promocionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

     public String agregarUsuario(UsuarioRequest usuario) {
         String codigoPromocion = Utiles.getSaltString();

         String year = dateOfCampana();

         String code  = codigoPromocion.concat(year);

         try {
             Cliente cliente = new Cliente(usuario,code);
             usuarioRepository.save(cliente);

         } catch (Exception e){
             e.printStackTrace();
             return "ERROR";
         }

         return code;
     }



    public boolean validarCodigoPromocion(String codigo) {

        try {

            List<Cliente> lista = usuarioRepository.findAll();
            for (Cliente c:  lista) {
                if(codigo.equals(c.getCodigo_promocion())){
                    return true;
                }
            }



            return false;


        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private String dateOfCampana(){

        RestTemplate restTemplate = new RestTemplate();

        try {


        Map< String, String > params = new HashMap< String, String >();
        params.put("country", "PE");
        Campana result = restTemplate.getForObject("https://oapi-qa.somosbelcorp.com:8083/campaigns/{country}", Campana.class, params);

        String year = result.getEnd_date().substring(1,4);
        return year;
        }catch (Exception e){
            return "";
        }

    }


    private boolean isConsultant(){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();



        Map< String, String > params = new HashMap< String, String >();
        params.put("country", "PE");
        Object[] result = restTemplate.getForObject("https://oapi-qa.somosbelcorp.com:8082/consultants/{country}", Object[].class, params);


        System.out.println("result: " + result);

        return true;
    }



}