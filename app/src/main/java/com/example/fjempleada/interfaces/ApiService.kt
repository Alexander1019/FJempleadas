package com.example.fjempleada.interfaces

import com.google.gson.JsonArray
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("Service.asmx/Login")
    fun login(@Field("email") email: String, @Field("pass") pass: String, @Field("perfil") perfil: Int = 2): Call<JsonArray>

    @FormUrlEncoded
    @POST("Service.asmx/AddUsuario")
    fun AddUsuario(
        @Field("email") email: String, @Field("pass") pass: String, @Field("idperfil") idperfil: Int ,
        @Field("nombre") nombre: String, @Field("apellido") apellido: String,
        @Field("telefono") telefono: String, @Field("sexo") sexo: String, @Field("curp") curp: String,
        @Field("fechanacimiento") fechanacimiento: String, @Field("fotoperfil") fotoperfil: String, @Field(
            "longi"
        ) longi: String, @Field("lat") lat: String,
        @Field("idinteres") idinteres: Int
    ): Call<String>
    @FormUrlEncoded
    @POST("Service.asmx/App_Addpublicacion")
    fun Addpublicacion(@Field("idcategorias") idcate:Int, @Field("idusuario") idusuario:Int,
                       @Field("descripcion")descri:String,
                       @Field("fecha") fecha:String, @Field("tarifa") tarifa:Int, @Field("extra") extra:String,
                       @Field("estatus") estatus:Int, @Field("dispo") dispo:String, @Field("longi") log:String, @Field("lat") lat:String,
                       @Field("titulo") titulo:String,@Field("radio")radio:String): Call<String>

    @FormUrlEncoded
    @POST("Service.asmx/App_GeTPublicacion")
    fun GetPublicacion(@Field("id") id: String): Call<JsonArray>

    @FormUrlEncoded
    @POST("Service.asmx/App_GetPersona")
    fun GetPersona(@Field("id") id: Int): Call<JsonArray>

    @FormUrlEncoded
    @POST("Service.asmx/App_GeTPublicacionPorUser")
    fun getPublicacion(@Field("id") idUsuario:Int ): Call<JsonArray>
    @FormUrlEncoded
    @POST("Service.asmx/App_Addcalificacion")
    fun Addcalificacion(
        @Field("idusuario") idusuario: Int, @Field("puntuacion") puntuacion: Double, @Field(
            "comentario"
        ) comentario: String
    ): Call<String>

    @FormUrlEncoded
    @POST("Service.asmx/App_Addinsidencia")
    fun Addinsidencia(
        @Field("mensaje") mensaje: String, @Field("idusuario") idusuario: Int,
        @Field("idusreportado") idusreportado: Int, @Field("estatus") estatus: Int,
        @Field("tipo") tipo: Int, @Field("fecha") fecha: String
    ): Call<String>

    @POST("Service.asmx/GetCategorias")
    fun getCategorias(): Call<JsonArray>

    @FormUrlEncoded
    @POST("Service.asmx/App_GetSolicitudes")
    fun getSolicitud(@Field("idpublic") idpubli:Int):Call<JsonArray>

    @Multipart
    @POST("Service.asmx/App_UploadFile")
    fun loadImage(
        @Part file: MultipartBody.Part
    ): Call<String>

    @FormUrlEncoded
    @POST("Service.asmx/App_uploadfotoperfil")
    fun Updatefotoperfil(
        @Field("img") img: String, @Field("id") id: Int
    ): Call<String>
    @FormUrlEncoded
    @POST("Service.asmx/validarEmail")
    fun validarEmail(
        @Field("email") email: String
    ): Call<String>
    @Multipart
    @POST("Service.asmx/UploadFileDocumento")
    fun loadDocumentos(
        @Part file: MultipartBody.Part
    ): Call<String>
    @FormUrlEncoded
    @POST("Service.asmx/uploadDocumentos")
    fun updateDocumento(
        @Field("documento") documento:String,@Field("id") id:Int):Call<String>

    @FormUrlEncoded
    @POST("Service.asmx/App_upEstatusSolicitud")
    fun updateEstatus(@Field("idsoli") idsoli:Int,@Field("estatus") estatus:Int):Call<String>







}