package pe.edu.cibertec.rueditas_frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.rueditas_frontend.dto.VehiculoRequestDTO;
import pe.edu.cibertec.rueditas_frontend.dto.VehiculoResponseDTO;
import pe.edu.cibertec.rueditas_frontend.viewmodel.VehiculoModel;

@Controller
@RequestMapping("/vehiculo")
public class VehiculoController {

    @Autowired  //Inyección de dependencia de Spring Boot para consumir servicios RESTful
    RestTemplate restTemplate;


    @GetMapping("/inicio")
    public String buscar(Model model) {
        VehiculoModel vehiculomodel = new VehiculoModel("00","00","");
        model.addAttribute("vehiculomodel", vehiculomodel);
        return "inicio";
    }

    @PostMapping("/buscarVehiculo")
    public String autenticar(@RequestParam("placa")String placa, Model model) {
        if(placa == null || placa.trim().length() == 0 || !placa.matches("^[A-Za-z]{3}-[0-9]{4}$")){
        VehiculoModel vehiculomodel = new VehiculoModel("01","Error: Debe ingresar una placa correcta","");
        model.addAttribute("vehiculomodel", vehiculomodel);
        return "inicio";
        }

        try {
            //Se invoca API de Busqueda de vehiculo

            String endpoint = "http://localhost:8081/datosvehiculo/search";
            VehiculoRequestDTO vehiculoRequestDTO = new VehiculoRequestDTO(placa);
            VehiculoResponseDTO vehiculoResponseDTO = restTemplate.postForObject(endpoint,vehiculoRequestDTO,VehiculoResponseDTO.class);

            //Se valida la respuesta
            if(vehiculoResponseDTO.codigo().equals("00")){
                VehiculoModel vehiculomodel= new VehiculoModel("00","", vehiculoRequestDTO.placa());
                model.addAttribute("vehiculomodel", vehiculomodel);
                model.addAttribute("vehiculoResponseDTO",vehiculoResponseDTO);
                return "principal";
            }else{
                VehiculoModel vehiculomodel = new VehiculoModel("02","No se encontró un vehículo para la placa ingresada","");
                model.addAttribute("vehiculomodel", vehiculomodel);
                System.out.println("Request DTO enviado: " + vehiculoRequestDTO.placa());
                return "inicio";
            }
        }catch (Exception e){
            VehiculoModel vehiculomodel = new VehiculoModel("99","Error: Ocurrió un problema en la búsqueda","");
            model.addAttribute("vehiculomodel", vehiculomodel);
            return "inicio";
        }


    }




}
