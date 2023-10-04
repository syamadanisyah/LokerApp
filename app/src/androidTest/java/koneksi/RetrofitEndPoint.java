package koneksi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

public class RetrofitEndPoint {

@POST(login.php)
    Call<UserResponse>login (
            @Field("email")String email
            @Field
)

}
