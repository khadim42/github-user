package com.khadim.githubuser.github;


import com.khadim.githubuser.github.dto.GithubRepository;
import com.khadim.githubuser.github.dto.Status;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

@Service
public class GitHubService implements APIConfiguration {

    @Value("${accessToken}")
    private String accessToken;

    private String ACCESS_TOKEN = "token " + accessToken;

    private Logger logger = LoggerFactory.getLogger(GitHubService.class);

    private RepositoryInterface service = null;

    public GitHubService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(RepositoryInterface.class);
    }

    public List<GithubRepository> getRepositoriesByUser(String userName) throws IOException {
        Call<List<GithubRepository>> reposCall =
                service.listReposByUser("token " + accessToken, API_VERSION_SPEC, userName);

        Response<List<GithubRepository>> reposResponse = null;
        List<GithubRepository> repos = null;
        reposResponse = reposCall.execute();

        if (reposResponse.isSuccessful()) {
            repos = reposResponse.body();
            if (repos != null) {
                logger.info("web request to Github was successful");
            } else
                logger.info("Zero repositories found");
        } else {
            ResponseBody errorResponse = reposResponse.errorBody();
            if (errorResponse != null) {
                logger.warn(errorResponse.string());
                logger.warn("AcessToken from @Value: {}", accessToken);
                logger.warn("ACCESS_TOKEN from @Value: {}", ACCESS_TOKEN);
            }
        }

        return repos;
    }
    public List<GithubRepository> getRepositories() throws IOException {
        Call<List<GithubRepository>> reposCall = service.listRepos("token " + accessToken, API_VERSION_SPEC);

        Response<List<GithubRepository>> reposResponse = null;
        List<GithubRepository> repos = null;
        reposResponse = reposCall.execute();

        if (reposResponse.isSuccessful()) {
            repos = reposResponse.body();
            if (repos != null) {
                logger.info("web request to Github was successful");
            } else
                logger.info("Zero repositories found");
        } else {
            ResponseBody errorResponse = reposResponse.errorBody();
            if (errorResponse != null) {
                logger.warn(errorResponse.string());
                logger.warn("AcessToken from @Value: {}", accessToken);
                logger.warn("ACCESS_TOKEN from @Value: {}", ACCESS_TOKEN);
            }
        }

        return repos;
    }


    public GithubRepository createRepository(GithubRepository repo) {
        Call<GithubRepository> newRepoCall = service.createRepo(repo, ACCESS_TOKEN, API_VERSION_SPEC, JSON_CONTENT_TYPE);

        Response<GithubRepository> newRepoResponse = null;
        GithubRepository newRepo = null;
        try {
            newRepoResponse = newRepoCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (newRepoResponse.isSuccessful()) {
            newRepo = newRepoResponse.body();
            if (newRepo != null) {
                logger.info("web request to Github was successfull");
            } else
                logger.info("Zero repositories found");
        } else {
            ResponseBody errorReponse = newRepoResponse.errorBody();
            if (errorReponse != null) {
                try {
                    logger.warn(errorReponse.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return newRepo;
    }

    public Status deleteRepository(String owner, String repoName) {
        Call<Status> deleteRepoCall = service.deleteRepo(ACCESS_TOKEN, API_VERSION_SPEC, repoName, owner);

        Response<Status> deleteStatusResponse = null;
        Status deleteStatus = null;
        try {
            deleteStatusResponse = deleteRepoCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (deleteStatusResponse.isSuccessful()) {
            int statusCode = deleteStatusResponse.code();
            if (statusCode == 204) {
                deleteStatus = new Status("204 No Content");
                logger.info("Delete web request to Github was successfull");
            } else
                logger.info("Delete Request Failed");
        } else {
            ResponseBody errorReponse = deleteStatusResponse.errorBody();
            if (errorReponse != null) {
                try {
                    logger.warn(errorReponse.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return deleteStatus;
    }

}
