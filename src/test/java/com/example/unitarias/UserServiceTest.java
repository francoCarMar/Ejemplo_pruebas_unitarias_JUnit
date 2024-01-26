package com.example.unitarias;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.unitarias.controllers.UserRestController;
import com.example.unitarias.models.User;
import com.example.unitarias.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class UserServiceTest {

    // La anotación @InjectMocks se utiliza para inyectar los objetos simulados
    // (mocks)
    // creados con la anotación @Mock en el objeto que se está probando.
    // En este caso, se inyecta el mock de UserService en UserRestController.
    @InjectMocks
    private UserRestController userRestController;

    // La anotación @Mock se utiliza para crear un objeto simulado (mock) de
    // UserService.
    // Este objeto simulado se puede programar para devolver valores específicos o
    // lanzar
    // excepciones cuando se llaman a sus métodos.
    @Mock
    private UserService userService;

    // MockMvc se utiliza para simular solicitudes HTTP para probar los
    // controladores de Spring MVC.
    // Permite probar el controlador de manera aislada, sin necesidad de iniciar
    // toda la aplicación.
    private MockMvc mockMvc;

    // ObjectMapper se utiliza para convertir objetos en JSON y viceversa.
    // Es útil para probar los controladores de Spring MVC, ya que las solicitudes y
    // respuestas HTTP
    // suelen estar en formato JSON.
    private ObjectMapper objectMapper;

    // La anotación @BeforeEach se utiliza para indicar que el método que la lleva
    // debe ejecutarse
    // antes de cada prueba. En este caso, el método setup() inicializa mockMvc y
    // objectMapper.
    @BeforeEach
    public void setup() {
        // Inicializa mockMvc para probar UserRestController de manera aislada
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();

        // Inicializa objectMapper
        objectMapper = new ObjectMapper();
    }

    // Prueba unitaria para el método createUser
    @Test
    public void testCreateUser() throws Exception {
        // Crea un nuevo usuario
        User user = new User();

        // Configura la expectativa para que cuando se llame a userService.createUser
        // con cualquier objeto User
        // , se devuelva el usuario
        when(userService.createUser(any(User.class))).thenReturn(user);

        // Simula una solicitud POST a "/users", pasa el usuario como contenido de la
        // solicitud y verifica que el estado HTTP en la respuesta es OK
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    // Prueba unitaria para el método getUsers
    @Test
    public void testGetUsers() throws Exception {
        // Crea dos nuevos usuarios
        User user1 = new User();
        User user2 = new User();

        // Configura la expectativa para que cuando se llame a userService.getUsers, se
        // devuelva una lista con los dos usuarios
        when(userService.getUsers()).thenReturn(Arrays.asList(user1, user2));

        // Simula una solicitud GET a "/users" y verifica que el estado HTTP en la
        // respuesta es OK
        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // Prueba unitaria para el método getUserById
    @Test
    public void testGetUserById() throws Exception {
        // Crea un nuevo usuario y establece su ID a 1
        User user = new User();
        user.setId(1);

        // Configura la expectativa para que cuando se llame a userService.getUserById
        // con cualquier entero como argumento, se devuelva el usuario
        when(userService.getUserById(anyInt())).thenReturn(Optional.of(user));

        // Simula una solicitud GET a "/users/1", y verifica que el estado HTTP en la
        // respuesta es OK
        mockMvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // Prueba unitaria para el método updateUser
    @Test
    public void testUpdateUser() throws Exception {
        // Crea un nuevo usuario y establece su ID a 1
        User user = new User();
        user.setId(1);

        // Configura la expectativa para que cuando se llame a userService.updateUser
        // con cualquier objeto User y cualquier entero como argumentos, se devuelva el
        // usuario
        when(userService.updateUser(any(User.class), anyInt())).thenReturn(Optional.of(user));

        // Simula una solicitud PUT a "/users/1" con el usuario como contenido de la
        // solicitud, y verifica que el estado HTTP en la respuesta es OK
        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    // Prueba unitaria para el método deleteUserById
    @Test
    public void testDeleteUserById() throws Exception {
        // Configura la expectativa para que cuando se llame a
        // userService.deleteUserById con cualquier entero como argumento, se devuelva
        // true
        when(userService.deleteUserById(anyInt())).thenReturn(true);

        // Simula una solicitud DELETE a "/users/1", y verifica que el estado HTTP en la
        // respuesta es OK
        mockMvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}