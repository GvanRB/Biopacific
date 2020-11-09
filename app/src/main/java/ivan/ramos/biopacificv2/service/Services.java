package ivan.ramos.biopacificv2.service;

import java.util.List;

import ivan.ramos.biopacificv2.models.Agregarmascota;
import ivan.ramos.biopacificv2.models.Clientes;
import ivan.ramos.biopacificv2.models.Dataclientemenu;
import ivan.ramos.biopacificv2.models.ListadoMascotasPorCliente;
import ivan.ramos.biopacificv2.models.Perfiles;
import ivan.ramos.biopacificv2.models.ServiciosXmascotas;
import ivan.ramos.biopacificv2.models.Solicitarservicio;
import ivan.ramos.biopacificv2.models.Usuarios;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Services {
    @POST("api/usuario/iniciarsesion")
    Call<Boolean> postvalidariniciosesion(@Body Usuarios usuario);

    @POST("api/cliente")
    Call<Clientes> postRegistrar(@Body Clientes clientes);

    @POST("api/cliente/usuariocliente")
    Call<Dataclientemenu> postNomVet(@Body Usuarios usuarios);

    @GET("api/usuario/validarcorreo/{correo}")
    Call<Boolean> getValidarCorreo(@Path("correo")String correo);

    @POST("api/mascota/listadomascotasporcliente")
    Call<List<ListadoMascotasPorCliente>> postListMascXCli(@Body Usuarios usuarios);

   @POST("api/mascota")
    Call<Agregarmascota> postAddMascota(@Body Agregarmascota agregarmascota);
    @GET("api/perfil")
    Call<List<Perfiles>> getListPerfiles();

    @POST("api/detalleorden")
    Call<Solicitarservicio> postRegistrarServicio(@Body Solicitarservicio solicitarservicio);

    @GET("api/detalleorden/serviciosmascota/{idmascota}")
    Call<List<ServiciosXmascotas>> getlistadoserviciosmasc(@Path("idmascota") String idmascota);
}
