sailthru-java-client
====================

A simple client library to remotely access the `Sailthru REST API` as per [http://docs.sailthru.com/api](http://docs.sailthru.com/api)

By default, it will make request in `JSON` format.

Javadocs: http://sailthru.github.com/sailthru-java-client/

Examples
--------

    String apiKey = "****";
    String apiSecret = "****";
    SailthruClient client = new SailthruClient(apiKey, apiSecret);

    //get email
    try {
        JsonResponse response = client.getEmail("praj@infynyxx.com");
        if (response.isOK()) {
            // do something here
        } else {
            // this will probably not happen because all API related errors are handled by ApiException class
        }
    } catch (ApiException e) {
        // handle API exceptions here
    } catch (IOException e) {
        // handle some unexpected, mostly non-API related exceptions here
    }
