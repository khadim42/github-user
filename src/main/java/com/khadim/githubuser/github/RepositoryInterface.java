package com.khadim.githubuser.github;

import com.khadim.githubuser.github.dto.GithubRepository;
import com.khadim.githubuser.github.dto.Status;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface RepositoryInterface {

    @GET("user/repos")
    Call<List<GithubRepository>> listRepos(@Header("Authorization") String accessToken,
                                           @Header("Accept") String apiVersionSpec);

    @GET("users/{owner}/repos")
    Call<List<GithubRepository>> listReposByUser(@Header("Authorization") String accessToken,
                                           @Header("Accept") String apiVersionSpec, @Path("owner") String owner);

    @DELETE("repos/{owner}/{repo}")
    Call<Status> deleteRepo(@Header("Authorization") String accessToken, @Header("Accept") String apiVersionSpec,
                            @Path("repo") String repo, @Path("owner") String owner);

    @POST("user/repos")
    Call<GithubRepository> createRepo(@Body GithubRepository repo, @Header("Authorization") String accessToken,
                                      @Header("Accept") String apiVersionSpec,
                                      @Header("Content-Type") String contentType);
}
