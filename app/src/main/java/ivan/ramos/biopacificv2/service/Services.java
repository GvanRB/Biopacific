package ivan.ramos.biopacificv2.service;

import java.util.List;

import ivan.ramos.biopacificv2.models.Mascotas;
import ivan.ramos.biopacificv2.models.PerfilExamenes;
import ivan.ramos.biopacificv2.models.Veterinarias;
import ivan.ramos.biopacificv2.models.IniciarSesion;
import ivan.ramos.biopacificv2.models.Distritos;
import ivan.ramos.biopacificv2.models.Especie;
import ivan.ramos.biopacificv2.models.ListadoMascotasPorVet;
import ivan.ramos.biopacificv2.models.Medicos;
import ivan.ramos.biopacificv2.models.Perfiles;
import ivan.ramos.biopacificv2.models.Raza;
import ivan.ramos.biopacificv2.models.ServiciosXmascotas;
import ivan.ramos.biopacificv2.models.Solicitarservicio;
import ivan.ramos.biopacificv2.models.Usuarios;
import ivan.ramos.biopacificv2.models.pruebaDNI;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface    Services {
    @POST("api/usuario/iniciarsesion")
    Call<IniciarSesion> postvalidariniciosesion(@Body Usuarios usuario);

    @POST("api/veterinaria")
    Call<Veterinarias> postRegistrar(@Body Veterinarias veterinarias);

    @POST("api/veterinaria/usuarioveterinaria")
    Call<IniciarSesion> postNomVet(@Body Usuarios usuarios);

    @GET("api/usuario/validarcorreo/{correo}")
    Call<Boolean> getValidarCorreo(@Path("correo")String correo);

    @GET("api/Mascota/ListadoMascotasPorVeterinaria/{idVeterinaria}")
    Call<List<ListadoMascotasPorVet>> postListMascXCli(@Path("idVeterinaria") String idVeterinaria);

    @GET("api/Medico/MedicosPorVeterinaria/{idVeterinaria}")
    Call<List<Medicos>> postListMediXVet(@Path("idVeterinaria") String idVeterinaria);

    @GET("/api/PerfilExamen/ExamenesPorPerfil/{idPerfil}")
    Call<List<PerfilExamenes>> getListPerfilExamenes(@Path("idPerfil") String idPerfil);

   @POST("api/mascota")
    Call<Mascotas> postAddMascota(@Body Mascotas mascotas);
    @POST("api/medico")
    Call<Medicos> postAddMedico(@Body Medicos medicos);

    @GET("api/perfil")
    Call<List<Perfiles>> getListPerfiles();

    @GET("api/distrito")
    Call<List<Distritos>> getListDistritos();

    @GET("api/especie")
    Call<List<Especie>> getListEspecies();
    @GET("api/raza")
    Call<List<Raza>> getListRazas();

    @POST("api/orden")
    Call<Solicitarservicio> postRegistrarServicio(@Body Solicitarservicio solicitarservicio);

    @GET("api/orden/ordenespormascota/{idmascota}")
    Call<List<ServiciosXmascotas>> getListadoServiPorMasc(@Path("idmascota") String idmascota);

    @GET("api/orden/ordenespormedico/{idmedico}")
    Call<List<ServiciosXmascotas>> getListadoServiPorMedic(@Path("idmedico") String idmedico);

    @GET("api/mascota/idonombre/{idmascota}")
    Call<Mascotas> getIdMascotaPorVet(@Path("idmascota") String idmascota);

    @GET("api/Medico/{id}")
    Call<Medicos> getIdMedicoPorVet(@Path("id") String id);
    @GET("api/Veterinaria/{id}")
    Call<Veterinarias> getIdVeterinaria(@Path("id") String id);

    @PUT("api/Medico/{id}")
    Call<Medicos> editarMedicoPorVet(@Body Medicos medicos, @Path("id") String id);

    @PUT("api/Mascota/{id}")
    Call<Mascotas> editarMedicoPorVet(@Body Mascotas mascotas, @Path("id") String id);

    @PUT("api/Veterinaria/{id}")
    Call<Veterinarias> editarMedicoPorVet(@Body Veterinarias veterinarias, @Path("id") String id);

    @DELETE("api/Medico/{id}")
    Call<Medicos> deleteMedico(@Path("id") String id);

    @DELETE("api/Mascota/{id}")
    Call<Mascotas> deleteMascota(@Path("id") String id);

    @GET("api/v1/dni/{datoDni}?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImdoZW5yYWxkQGdtYWlsLmNvbSJ9.RihbA7g2NVbcR4MEdWRrDyaCR92ICeJ3Wm0JP34oWbM")
    Call<pruebaDNI>datosDNI(@Path("datoDni") String datoDni);

    @GET("/api/Orden/OrdenPdf/{idOrden}")
    Call<ServiciosXmascotas> getPdfView(@Path("idOrden")String idOrden);
}
