package com.example.interfazloginregister.network

import com.example.interfazloginregister.model.Direccion
import com.example.interfazloginregister.model.LoginResponse
import com.example.interfazloginregister.model.LoginUsuarioDTO
import com.example.interfazloginregister.model.TareaDTO
import com.example.interfazloginregister.model.Usuario
import com.example.interfazloginregister.model.UsuarioDTO
import com.example.interfazloginregister.model.UsuarioRegisterDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    // ----- USUARIOS -----
    @POST("/usuarios/register")
    suspend fun registerUser(
        @Body usuarioRegisterDTO: UsuarioRegisterDTO
    ): UsuarioDTO

    @POST("/usuarios/login")
    suspend fun loginUser(
        @Body loginDTO: LoginUsuarioDTO
    ): LoginResponse

    @GET("/usuarios/{username}")
    suspend fun getUsuarioByUsername(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Usuario

    @GET("/usuarios")
    suspend fun getAllUsuarios(
        @Header("Authorization") token: String
    ): List<Usuario>

    @PUT("/usuarios/{username}")
    suspend fun updateUsuario(
        @Header("Authorization") token: String,
        @Path("username") username: String,
        @Body usuario: Usuario
    ): Usuario

    @DELETE("/usuarios/{username}")
    suspend fun deleteUsuario(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Usuario

    @GET("/tareas")
    suspend fun getAllTareas(
        @Header("Authorization") token: String
    ): List<TareaDTO>

    @GET("/tareas/{codigo}")
    suspend fun getTareaByCodigo(
        @Header("Authorization") token: String,
        @Path("codigo") codigo: String
    ): TareaDTO

    @POST("/tareas")
    suspend fun createTarea(
        @Header("Authorization") token: String,
        @Body tarea: TareaDTO
    ): TareaDTO

    @PUT("/tareas/{codigo}")
    suspend fun updateTarea(
        @Header("Authorization") token: String,
        @Path("codigo") codigo: String,
        @Body tarea: TareaDTO
    ): TareaDTO

    @PUT("/tareas/{codigo}/completar")
    suspend fun completarTarea(
        @Header("Authorization") token: String,
        @Path("codigo") codigo: String
    ): TareaDTO

    @DELETE("/tareas/{codigo}")
    suspend fun deleteTarea(
        @Header("Authorization") token: String,
        @Path("codigo") codigo: String
    ): TareaDTO

    // ----- DIRECCIONES -----
    @GET("/direcciones")
    suspend fun getAllDirecciones(
        @Header("Authorization") token: String
    ): List<Direccion>

    @GET("/direcciones/{codigo}")
    suspend fun getDireccionByCodigo(
        @Header("Authorization") token: String,
        @Path("codigo") codigo: String
    ): Direccion

    @GET("/direcciones/usuario/{username}")
    suspend fun getDireccionByUsuario(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Direccion

    @POST("/direcciones")
    suspend fun createDireccion(
        @Header("Authorization") token: String,
        @Body direccion: Direccion
    ): Direccion

    @PUT("/direcciones/{codigo}")
    suspend fun updateDireccion(
        @Header("Authorization") token: String,
        @Path("codigo") codigo: String,
        @Body direccion: Direccion
    ): Direccion

    @DELETE("/direcciones/{codigo}")
    suspend fun deleteDireccion(
        @Header("Authorization") token: String,
        @Path("codigo") codigo: String
    ): Direccion
}






