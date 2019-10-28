package cn.shizhe.community.provider;

import cn.shizhe.community.vo.GitHubAccessTokenVo;
import cn.shizhe.community.vo.GitHubUser;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {

    public String getAccessToken(GitHubAccessTokenVo gitHubAccessTokenVo){
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON.toJSONString(gitHubAccessTokenVo),mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string =  response.body().string();
            String[] split = string.split("&");
            String[] split1 = split[0].split("=");
            String accessToken = split1[1];
            return accessToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public GitHubUser getGitHubUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //ctrl+alt+v
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
            return  gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
