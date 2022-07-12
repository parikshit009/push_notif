package com.example.dremio.demo;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;

public class PostQuery {


    public static int getCount(String pat) throws InterruptedException, IOException {
        Gson gson = new Gson();
        ApiCalls makeRequest = new ApiCalls();

        DremioQuery countQuery = new DremioQuery("select count(*) from MX_Re_engage.mx_hot_sale_os_data");
        String CountQueryBody = gson.toJson(countQuery);

        String countQueryResponse = makeRequest.sendPOST("https://dremio-mx-cam-pre-prod.prod.us.walmart.net:9047/api/v3/sql", CountQueryBody, pat);
        QueryReaponse countJobId = gson.fromJson(countQueryResponse, QueryReaponse.class);
        System.out.println(countJobId.id);

        String CountJobStatusResponse= makeRequest.sendGet( "https://dremio-mx-cam-pre-prod.prod.us.walmart.net:9047/api/v3/job/" + countJobId.id,pat);
        JobStatus CountJobStatus = gson.fromJson(CountJobStatusResponse, JobStatus.class);
        System.out.println(CountJobStatus.jobState);


        int maxRetry = 60;
        int totalRecords = 0;
        while (maxRetry > 0) {
            if (Objects.equals(CountJobStatus.jobState, "COMPLETED")) {
                System.out.println(CountJobStatus.jobState);

                String result_1 = makeRequest.sendGet("https://dremio-mx-cam-pre-prod.prod.us.walmart.net:9047/apiv2/job/" + countJobId.id + "/data?offset=0&limit=1", pat);
                DremioResponseArray count = gson.fromJson(result_1, DremioResponseArray.class);
                totalRecords = 0;



                for (DremioResponse num : count.rows) {
                    //temp4.add(num.row[0].v);
                    totalRecords = (Integer.parseInt(num.row[0].v));
                }
                break;
            }
            else if (Objects.equals(CountJobStatus.jobState, "FAILED"))
            {
                System.out.println(CountJobStatus.jobState);
                break;
            }

            else {
                Thread.sleep(1000);
                CountJobStatus = gson.fromJson(CountJobStatusResponse, JobStatus.class);

                maxRetry--;
            }

        }

        return totalRecords;
    }

    public static String getEmails(String deviceType,String pat) throws IOException {
        Gson gson = new Gson();
        ApiCalls makeRequest = new ApiCalls();
        DremioQuery queryEmails = new DremioQuery("select * from MX_Re_engage.mx_hot_sale_os_data where os_type =" + deviceType);
        String dremioQueryBody = gson.toJson(queryEmails);
        String dremio_response_1 = makeRequest.sendPOST("https://dremio-mx-cam-pre-prod.prod.us.walmart.net:9047/api/v3/sql", dremioQueryBody, pat);
        return dremio_response_1;

    }
}
