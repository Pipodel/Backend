package com.portfolio.Delaporte.Interface;

import com.portfolio.Delaporte.Entity.Persona;
import java.util.List;


public interface IPersonaService {
    //Traer lista de Persona
    public List<Persona> getPersona();
    
    //Guardar un objeto de tipo Persona
    public void savePersona(Persona persona);
    
    //Eliminar un objeto pero buscado por ID
    public void deletePersona(Long id);
    
    //Buscar una persona por ID
    public Persona findPersona(Long id);
    
}
