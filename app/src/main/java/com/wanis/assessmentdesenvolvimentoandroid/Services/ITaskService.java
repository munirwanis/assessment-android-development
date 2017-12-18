package com.wanis.assessmentdesenvolvimentoandroid.Services;

import com.wanis.assessmentdesenvolvimentoandroid.Entities.TasksResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by munirwanis on 17/12/17.
 */

public interface ITaskService {
    @GET("./")
    Call<TasksResponse> getTasks();
}
