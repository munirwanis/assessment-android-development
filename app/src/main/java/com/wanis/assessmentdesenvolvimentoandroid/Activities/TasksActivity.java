package com.wanis.assessmentdesenvolvimentoandroid.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wanis.assessmentdesenvolvimentoandroid.Entities.Task;
import com.wanis.assessmentdesenvolvimentoandroid.Entities.TasksResponse;
import com.wanis.assessmentdesenvolvimentoandroid.R;
import com.wanis.assessmentdesenvolvimentoandroid.Services.ITaskService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TasksActivity extends AppCompatActivity {

    ListView listagem;
    ArrayAdapter<Task> adapter = null;

    private static final String TAG = TasksActivity.class.getSimpleName();
    public static final String BASE_URL = "http://infnet.educacao.ws/dadosAtividades.php/";
    private static Retrofit retrofit = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        listagem = (ListView) findViewById(R.id.lvListagem);

        connectApiData();


    }


    private void connectApiData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        ITaskService tarefaInterface = retrofit.create(ITaskService.class);
        Call<TasksResponse> call = tarefaInterface.getTasks();

        call.enqueue(new Callback<TasksResponse>() {
            @Override
            public void onResponse(Call<TasksResponse> call, Response<TasksResponse> response) {
                List<Task> tasks = response.body().getTasks();
                //Log.d("123", "Retorno" + tasks.size());
                adapter = new ArrayAdapter<Task>(getApplicationContext(), android.R.layout.simple_list_item_1, tasks);
                listagem.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<TasksResponse> call, Throwable t) {

            }
        });
    }
}
