
package com.webapp.wooriga.mybatis.auth.service;

import com.google.gson.*;
import com.webapp.wooriga.mybatis.vo.KakaoProfile;
import com.webapp.wooriga.mybatis.vo.RetKakaoAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class KakaoService {

    private final RestTemplate restTemplate;
    private final Environment env;
    private final Gson gson;
    private JsonParser parser = new JsonParser();

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${social.kakao.redirect}")
    private String kakaoRedirect;

    public KakaoProfile getKakaoProfile(String accessToken) throws Exception {
        // Set header : Content-type: application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        try {
            // Request profile
            ResponseEntity<String> response = restTemplate.postForEntity(env.getProperty("social.kakao.url.profile"), request, String.class);
            if (response.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(response.getBody(), KakaoProfile.class);
        } catch (Exception e) {
            throw new Exception();
        }
        throw new Exception();
    }

    public void logout(String accessToken) throws Exception {
        // Set header : Content-type: application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        try {
            // Request profile
            ResponseEntity<String> response = restTemplate.postForEntity(env.getProperty("social.kakao.url.logout"), request, String.class);
            if (response.getStatusCode() == HttpStatus.OK)
                return;
        } catch (Exception e) {
            throw new Exception();
        }
        throw new Exception();
    }

    public HashMap<String, Object> getUserInfo (String access_Token) {

        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            JsonObject profile = kakao_account.getAsJsonObject().get("profile").getAsJsonObject();

            long id = element.getAsJsonObject().get("id").getAsLong();
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            String image = profile.getAsJsonObject().get("thumbnail_image_url").getAsString();
            String birth = kakao_account.getAsJsonObject().get("birthday").getAsString();

            System.out.println(id + ", " + nickname + ", " + email + ", " + image + ", " + birth);

            userInfo.put("id", id);
            userInfo.put("nickname", nickname);
            userInfo.put("email", email);
            userInfo.put("profile", image);
            userInfo.put("birth", birth);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return userInfo;
    }

    public RetKakaoAuth getKakaoTokenInfo(String code) {
        // Set header : Content-type: application/x-www-form-urlencoded

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // Set parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", baseUrl + kakaoRedirect);
        params.add("code", code);
        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(env.getProperty("social.kakao.url.token"), request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
//            JsonElement element = parser.parse(response.getBody());
//            System.out.println(element.getAsJsonObject().get("access_token").getAsString());
//
//            retKakaoAuth.setAccess_token(element.getAsJsonObject().get("access_token").getAsString());
//            retKakaoAuth.setToken_type(element.getAsJsonObject().get("token_type").getAsString());
//            retKakaoAuth.setRefresh_token(element.getAsJsonObject().get("refresh_token").getAsString());
//            retKakaoAuth.setExpires_in(element.getAsJsonObject().get("expires_in").getAsLong());
//            retKakaoAuth.setScope(element.getAsJsonObject().get("scope").getAsString());

//            return retKakaoAuth;
            return gson.fromJson(response.getBody(), RetKakaoAuth.class);
        }
        return null;
    }

    public String getAccessToken (String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=").append(kakaoClientId);
            sb.append("&redirect_uri=").append(baseUrl).append(kakaoRedirect);
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            //    결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return access_Token;
    }
}