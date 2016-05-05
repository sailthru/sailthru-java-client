package com.sailthru.client;

import com.sailthru.client.exceptions.ApiException;
import com.sailthru.client.handler.response.JsonResponse;
import com.sailthru.client.params.Purchase;
import com.sailthru.client.params.PurchaseItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class PurchaseExample {

    public static void main(String[] args) {

        String apiKey = "****";
        String apiSecret = "***";
        SailthruClient client = new SailthruClient(apiKey, apiSecret);
        try {
            Purchase purchase = new Purchase();
            PurchaseItem firstpurchaseitem = new PurchaseItem(1, "example purchase item", 1999, "example id", "http://www.sailthru.com/example/purchase/url");
            List<PurchaseItem> items = new ArrayList<PurchaseItem>();
            items.add(firstpurchaseitem);
            purchase.setItems(items);

            Map<String, Object> adjustmentItem = new HashMap<String, Object>();
            adjustmentItem.put("title", "bar");
            adjustmentItem.put("price", 1000);
            ArrayList adjustments = new ArrayList();
            adjustments.add(adjustmentItem);
            purchase.setAdjustments(adjustments);

            Map<String, Object> TenderItem = new HashMap<String, Object>();
            TenderItem.put("title", "bar");
            TenderItem.put("price", 1000);
            ArrayList tenders = new ArrayList();
            tenders.add(TenderItem);
            purchase.setTenders(tenders);

            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("name", "Prajwal Tuladhar");
            Map<String, String> addressVars = new HashMap<String, String>();
            addressVars.put("state", "NY");
            addressVars.put("city", "Jackson Heights");
            addressVars.put("zip", "11372");
            vars.put("address", addressVars);

            purchase.setPurchaseLevelVars(vars);

            purchase.setSendTemplate("test");

            purchase.setEmail("dyu@sailthru.com");


            JsonResponse response = client.purchase(purchase);
            if (response.isOK()) {
                System.out.println(response.getResponse());
            } else {
                System.out.println(response.getResponse().get("error").toString());
            }

            // optionally get the rate limit information for the corresponding API endpoint/method
            LastRateLimitInfo lastRateLimitInfo = client.getLastRateLimitInfo(ApiAction.purchase, AbstractSailthruClient.HttpRequestMethod.POST);
            if (lastRateLimitInfo != null) {
                // examine rate limit information
                int limit = lastRateLimitInfo.getLimit();
                int remaining = lastRateLimitInfo.getRemaining();
                Date reset = lastRateLimitInfo.getReset();
                // ...
            }
        } catch (ApiException e) {
            // handle exception
        } catch (IOException e) {
            // handle exception
        }
    }
}