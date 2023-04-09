package com.zcy.querytest.service;

import com.alibaba.fastjson.JSONObject;
import com.zcy.querytest.dao.RequestItem;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Random;

@Service
public class QueryService {

    @Value("${config.numStock}")
    private int numStock;

    @Value("${config.url}")
    private String requestURL;

    @Value("${config.numThread}")
    private int numThread;

    public void query(String code, int index, int count) throws Exception {
        RequestItem requestItem = new RequestItem();
        requestItem.setCode(code);
        requestItem.setDate_time(covertTime(System.currentTimeMillis()));
        requestItem.setLimit_by_coordinate(limitByCoord(index, count));
        requestItem.setStats_type(3);

        HttpPost httpPost = new HttpPost(requestURL);

        httpPost.addHeader("Content-Type", "application/json");


        StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(requestItem), "utf-8");

        stringEntity.setContentEncoding("UTF-8");
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);


        CloseableHttpClient httpClient = HttpClients.createDefault();

        CloseableHttpResponse result = httpClient.execute(httpPost);

//        int statusCode = result.getStatusLine().getStatusCode();

//        // 响应结果
//        if (statusCode == HttpStatus.SC_OK) {
//        ResponseItem response = JSON.parseObject(EntityUtils.toString(result.getEntity(), "utf-8"), ResponseItem.class);

        System.out.println(EntityUtils.toString(result.getEntity(), "utf-8"));
    }

    public void queryThread() {
        for(int i = 0; i < numThread; i++){
            ThreadPoolService.fixedExecutor.execute(()->{
                try {
                    query(randStkCode(), 0, 10);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
    private JSONObject limitByCoord(int index, int count){
        JSONObject object = new JSONObject();
        object.put("index", index);
        object.put("count", count);
        return object;
    }

    private JSONObject limitByTime(String begin, String end){
        JSONObject object = new JSONObject();
        object.put("begin", begin);
        object.put("end", end);
        return object;
    }

    private String covertTime(long time){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return dateFormat.format(time);
    }

    private String randStkCode(){
        Random rand = new Random(System.nanoTime());
        return "sh" + String.format("%06d", rand.nextInt(numStock));
    }
}
