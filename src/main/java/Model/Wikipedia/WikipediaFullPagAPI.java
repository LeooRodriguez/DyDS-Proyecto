package Model.Wikipedia;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikipediaFullPagAPI {

    @GET("api.php?format=json&action=query&prop=extracts")
    Call<String> getExtractByPageID(@Query("pageids") String term);
}
