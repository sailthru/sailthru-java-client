package com.sailthru.client;

import com.sailthru.client.handler.response.JsonResponse;
import com.sailthru.client.params.Adjustment;
import com.sailthru.client.params.Blast;
import com.sailthru.client.params.Purchase;
import com.sailthru.client.params.PurchaseItem;
import com.sailthru.client.params.Tender;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com> <praj@sailthru.com>
 */
public class SailthruUtilTest extends TestCase {

    public void testMd5() {
        String plainText1 = "hello_world";
        String hash1 = SailthruUtil.md5(plainText1);
        String expectedHash1 = "99b1ff8f11781541f7f89f9bd41c4a17";
        assertEquals(hash1, expectedHash1);
    }

    public void testArrayListToCSV() {
        java.util.List<String> list1 = new ArrayList<String>();
        list1.add("windows");
        list1.add("linux");
        list1.add("BSD");
        String expectedList1 = "windows,linux,BSD";
        assertEquals(expectedList1, SailthruUtil.arrayListToCSV(list1));

        java.util.List<String> list2 = new ArrayList<String>();
        list2.add("one_item");
        String expectedList2 = "one_item";
        assertEquals(expectedList2, SailthruUtil.arrayListToCSV(list2));
    }

    public void testPostBlast() throws Exception {

        SailthruClient client = new SailthruClient("a33a837d795265fe65504922cf32701f", "464f033eb58fc5c92de0a72949710915", "http://api.sailthru-sb.com");

        Blast blast = new Blast();
        blast.setName("George AB Test");
        blast.setList("40 Users No Teams");
        blast.setFromName("George");
        blast.setFromEmail("gliao@sailthru.com");
        blast.setScheduleTime("+10 hours");
        blast.setSubject("My Subject");
        blast.setContentText("Content Text");
        blast.setContentHtml("Content HTML");

        JsonResponse response = client.apiPost(blast);

        System.out.println(response);
        System.out.println("Blast ID: " + response.getResponse().get("blast_id"));
    }

    public void testCreateAbTestWithWinnerMetrics() throws Exception {

        SailthruClient client = new SailthruClient("a33a837d795265fe65504922cf32701f", "464f033eb58fc5c92de0a72949710915", "http://api.sailthru-sb.com");

        Blast blast = new Blast();
//        blast.setScheduleTime("+10 hours");
//        blast.setAbTest("1");
//        blast.setTestPercent(25);
//        blast.setName("George AB Test [Final]");
        blast.setAbtestWinnerMetric("clicks");
        blast.setBlastId(25);

        JsonResponse response = client.apiPost(blast);

        System.out.println(response);
        System.out.println("Error Message: " + response.getResponse().get("errormsg"));
        System.out.println("Blast ID: " + response.getResponse().get("blast_id"));
    }

    public void testCreateAbTest() throws Exception {

        SailthruClient client = new SailthruClient("a33a837d795265fe65504922cf32701f", "464f033eb58fc5c92de0a72949710915", "http://api.sailthru-sb.com");

        Integer blastId = 25;

        Blast blast = new Blast();
        blast.setScheduleTime("now");
        blast.setAbTest("1");
        blast.setTestPercent(25);
        blast.setName("George AB Test [B]");
        blast.setBlastId(blastId);

        JsonResponse response = client.apiPost(blast);

        System.out.println(response);
        System.out.println("Error Message: " + response.getResponse().get("errormsg"));
        System.out.println("Blast ID: " + response.getResponse().get("blast_id"));

        blast = new Blast();
        blast.setScheduleTime("now");
        blast.setAbTest("1");
        blast.setTestPercent(25);
        blast.setName("George AB Test [B]");
        blast.setBlastId(blastId);

        response = client.apiPost(blast);

        System.out.println(response);
        System.out.println("Error Message: " + response.getResponse().get("errormsg"));
        System.out.println("Blast ID: " + response.getResponse().get("blast_id"));
    }

    public void testPurchaseApi() throws IOException {
        SailthruClient client = new SailthruClient("a33a837d795265fe65504922cf32701f", "464f033eb58fc5c92de0a72949710915", "http://localhost:8080/sailthru-api");
//        SailthruClient client = new SailthruClient("a33a837d795265fe65504922cf32701f", "464f033eb58fc5c92de0a72949710915", "http://api.sailthru-sb.com");
        Purchase purchase = new Purchase();

        List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();
        PurchaseItem purchaseItem1 = new PurchaseItem(1, "RACING JACKET®", 32000, "26186", "http://www.oakley.com/products/6950/26186");
        Map<String, Object> var1 = new HashMap<String, Object>();
        var1.put("Test1", 100);

        List<Adjustment> item1Adjustments = new ArrayList<Adjustment>();
        item1Adjustments.add(new Adjustment("Broken", -100));
        item1Adjustments.add(new Adjustment("Sale", -100));
        purchaseItem1.setAdjustments(item1Adjustments);

        purchaseItem1.setVars(var1);

        PurchaseItem purchaseItem2 = new PurchaseItem(1, "POLARIZED HALF JACKET® 2.0 XL", 38020, "26181", "http://www.oakley.com/products/6944/26181");
        Map<String, Object> var2 = new HashMap<String, Object>();
        var2.put("Test2", "Test2");
        purchaseItem2.setVars(var2);

        purchaseItems.add(purchaseItem1);
        purchaseItems.add(purchaseItem2);

        Map<String, Object> purchaseVars = new HashMap<String, Object>();
        purchaseVars.put("PurchaseVarTest1", var1);
        purchaseVars.put("PurchaseVarTest2", var2);

        purchase.setEmail("gliao@sailthru.com");
        purchase.setSendTemplate("George Oakley Test");
        purchase.setItems(purchaseItems);
        purchase.setVars(purchaseVars);

        List<Adjustment> adjustments = new ArrayList<Adjustment>();
        adjustments.add(new Adjustment("Loyalty", -1000));
        adjustments.add(new Adjustment("Charisma", -1000));
        purchase.setAdjustments(adjustments);

        List<Tender> tenders = new ArrayList<Tender>();
        tenders.add(new Tender("Cash", 10000));
        tenders.add(new Tender("Credit", 10000));
        purchase.setTenders(tenders);

        JsonResponse response = client.purchase(purchase);

        System.out.println(response.getResponse());

    }

    public void testGlobalVarsApi() throws IOException {
        SailthruClient client = new SailthruClient("a33a837d795265fe65504922cf32701f", "464f033eb58fc5c92de0a72949710915", "http://api.sailthru-sb.com");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", "George Test");
        JsonResponse response = client.apiGet(ApiAction.globalvars, params);

        System.out.println(response.toString());
    }

    public void testTemplate() throws Exception {
//        SailthruClient client = new SailthruClient("a33a837d795265fe65504922cf32701f", "464f033eb58fc5c92de0a72949710915", "http://api.sailthru-sb.com");
        SailthruClient client = new SailthruClient("a33a837d795265fe65504922cf32701f", "464f033eb58fc5c92de0a72949710915", "http://localhost:8080/sailthru-api");

//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("revision", "3");
//        params.put("from_email", "George Liao");
//        params.put("subject", "George Test");
//        params.put("blah", "blah Test");
//        params.put("format", "json");
//
//        JsonResponse response = client.apiPost(ApiAction.template, params);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("template", "George Oakley Test");
//        params.put("revision", "3");
        params.put("format", "json");

        JsonResponse response = client.apiGet(ApiAction.template, params);

        System.out.println(response.getResponse());
    }

    public void testMongo() throws Exception {
        String postData = "username=test&password=TEST&message="
                + java.net.URLEncoder.encode(readFileIntoString("/Users/georgekliao/dev/seamlessreceipts-utilities/slrmim/src/test/resources/testxml/full_return.xml"));

        URL url = new URL("http://localhost:8080/slrmim/http-rproreceipt");
        OutputStreamWriter out = null;
        BufferedReader in = null;

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Encoding", "utf-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(postData.getBytes().length));

            out = new OutputStreamWriter(connection.getOutputStream());
            out.write(postData);
            out.flush();

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }

        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
    }

    public static String readFileIntoString(String fileName) throws FileNotFoundException, IOException {
        String line = null;
        StringBuilder fileString = new StringBuilder();
        String ls = System.getProperty("line.separator");

        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        while ((line = reader.readLine()) != null) {
            fileString.append(line);
            fileString.append(ls);
        }

        return fileString.toString();
    }
}
