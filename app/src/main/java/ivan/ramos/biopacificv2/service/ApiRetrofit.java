package ivan.ramos.biopacificv2.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {
    private static Retrofit retrofit;
    private static String BASE_URL = "http://10.0.2.2:5000/";

    public static Services getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(Services.class);
    }
}
