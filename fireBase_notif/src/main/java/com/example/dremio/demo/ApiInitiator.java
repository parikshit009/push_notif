package com.example.dremio.demo;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ApiInitiator {
    public static void initiate(int totalRecords,int perSegment,String segmentName,String pat) throws IOException, InterruptedException {
        Gson gson = new Gson();
        ApiCalls makeRequest = new ApiCalls();

        for (int device_type = 1; device_type <= 2; device_type++) {
            String deviceType = "";
            if (device_type == 1)
                deviceType = "'androidsams'";
            else
                deviceType = "'iossams'";

            PostQuery emailPostQueryJobId= new PostQuery();

            String dremioResponse = emailPostQueryJobId.getEmails(deviceType,pat);
            QueryReaponse emailsJobId = gson.fromJson(dremioResponse, QueryReaponse.class);
            System.out.println(emailsJobId.id);

            String emailsJobStatusResponse = makeRequest.sendGet("https://dremio-mx-cam-pre-prod.prod.us.walmart.net:9047/api/v3/job/" + emailsJobId.id,pat);
            JobStatus emailsJobStatus = gson.fromJson(emailsJobStatusResponse, JobStatus.class);
            System.out.println(emailsJobStatus.jobState);


            int maxRetry = 60;
            while (maxRetry > 0) {
                if (Objects.equals(emailsJobStatus.jobState, "COMPLETED")) {
                    System.out.println(emailsJobStatus.jobState);

                    for (int i = 0, k = 1; i < totalRecords; i += perSegment, k++) {


                        String emailsResponse = makeRequest.sendGet("https://dremio-mx-cam-pre-prod.prod.us.walmart.net:9047/apiv2/job/"+ emailsJobId.id + "/data?offset=" + i + "&limit="+perSegment,pat);
                        DremioResponseArray emailsObject = gson.fromJson(emailsResponse, DremioResponseArray.class);
                        ArrayList<String> emails = new ArrayList<String>();

                        for (DremioResponse num : emailsObject.rows) {
                            emails.add(num.row[0].v);
                        }

                        if(emails.isEmpty())
                            break;

                        for (int j = 0; j < emails.size(); j += 500) {
                            ArrayList<String> temp2 = new ArrayList<String>();

                            for (int l = 0; l < 500 && ((l+j)<emails.size()); l++) {
                                temp2.add(emails.get(l + j));
                            }

                            if(temp2.isEmpty())
                                break;

                            SignatureGenerator sg = new SignatureGenerator();
                            String[] ans =sg.getToken("prod");
                            Signature ksi_token = new Signature(ans[1],ans[0]);

                            String dt;
                            if (device_type == 1)
                                dt = "1";
                            else
                                dt = "2";

                            ProdApiBody prodBodyData = new ProdApiBody(dt, temp2);

                            String prodBody = gson.toJson(prodBodyData);
                            String res = makeRequest.sendPOST_Prod("https://prod.sps.glb.us.walmart.net/profile/api/v1/bulk/token", ksi_token, prodBody);

                            //javaObject from json for google api
                            ProdApiResponseWrapperArray emailsTokenObject = gson.fromJson(res, ProdApiResponseWrapperArray.class);
                            ArrayList<String> emailsToken = new ArrayList<String>();


                            //array of tokens for Google api
                            for (ProdApiResponse num : emailsTokenObject.tokenDTOList)
                                emailsToken.add(num.token);

                            String tp ="";

                            if(device_type == 1)
                                tp = "Android";
                            else
                                tp = "iOS";

                            GApiBody gApiBodyData = new GApiBody("/topics/" + segmentName + "-" +tp+ "-Segment-" + k, emailsToken);
                            String gApiBody = gson.toJson(gApiBodyData);
                            System.out.println(gApiBodyData.to);
                            String finalResult = makeRequest.sendPOST("https://iid.googleapis.com//iid/v1:batchAdd", gApiBody,"key=AIzaSyByIg__eCPHzGIGbNuDmKNznBe_34RiWSQ");
                            System.out.println(finalResult);


                        }
                    }

                    break;
                }

                else if (Objects.equals(emailsJobStatus.jobState, "FAILED")) {
                    System.out.println(emailsJobStatus.jobState);
                    break;
                }

                else {
                    Thread.sleep(1000);
                    emailsJobStatus = gson.fromJson(emailsJobStatusResponse, JobStatus.class);

                    maxRetry--;
                }

            }

        }

        return ;
    }
}
