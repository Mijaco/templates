package com.demo.controler.rest;


import com.demo.bean.CodigoPromocion;
import com.demo.bean.Saldo;
import com.demo.bean.Usuario;
import com.demo.bean.UsuarioRequest;
import com.demo.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "hackaton", description = "Api Hackaton Grupo 04")
@RequestMapping("/hackaton")
public class TeamControllerRest {

    @Autowired
    private DemoService service;

    @ApiOperation(value = "Busca un cliente", notes = "Return something" )
    @RequestMapping(value = "/usuario", method = RequestMethod.GET)
    public Usuario obtenerUsuario(HttpServletRequest servletRequest,
                                     @RequestParam String dni) {

        Usuario user = new Usuario();
        user.setApellidos("Aymara");
        user.setCodigo_promocion("YOUWIN001");
        user.setNombre("Mijail");
        user.setDni("47698614");
        user.setNumero("953247579");
        user.setRegistrado("true");
        return user;
    }

    @ApiOperation(value = "Buscar el saldo de un cliente", notes = "Return something" )
    @RequestMapping(value = "/saldo", method = RequestMethod.GET)
    public Saldo saldo(HttpServletRequest servletRequest,
                       @RequestParam String dni) {


        Saldo saldo  = new Saldo();
        saldo.setDni("47698614");
        saldo.setMonto(12342342);

        return saldo;

    }




        @ApiOperation(
                value = "Servicio para agregar un cliente y responder su código de promoción.",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                response = String.class

        )
    @RequestMapping(value = "/usuario/agregar", method = RequestMethod.POST)
    public String promocion(HttpServletRequest servlet, @RequestBody UsuarioRequest userRequest) {

        return service.agregarUsuario(userRequest);

    }

    @ApiOperation(value = "Validar un codigo promoción")
    @RequestMapping(value = "/codigo-promocion", method = RequestMethod.GET)
    public boolean validarCodigo(HttpServletRequest servletRequest,
                                     @RequestParam String codigo) {

        return service.validarCodigoPromocion(codigo);

    }


}
