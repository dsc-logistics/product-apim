/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied. See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 *  WSO2 API Manager - Publisher API
 *  This specifies a **RESTful API** for WSO2 **API Manager** - Publisher.  Please see [full swagger definition](https://raw.githubusercontent.com/wso2/carbon-apimgt/v6.0.4/components/apimgt/org.wso2.carbon.apimgt.rest.api.publisher/src/main/resources/publisher-api.yaml) of the API which is written using [swagger 2.0](http://swagger.io/) specification.
 *
 *  OpenAPI spec version: 0.10.0
 *  Contact: architecture@wso2.com
 *
 *  NOTE: This class is auto generated by the swagger code generator program.
 *  https://github.com/swagger-api/swagger-codegen.git
 *  Do not edit the class manually.
 *
 */

package org.wso2.carbon.apimgt.rest.integration.tests.api.publisher;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.wso2.carbon.apimgt.rest.integration.tests.publisher.api.DocumentCollectionApi;
import org.wso2.carbon.apimgt.rest.integration.tests.publisher.ApiException;
import org.wso2.carbon.apimgt.rest.integration.tests.publisher.model.Document;
import org.wso2.carbon.apimgt.rest.integration.tests.publisher.model.APIList;
import org.wso2.carbon.apimgt.rest.integration.tests.publisher.api.APICollectionApi;
import org.wso2.carbon.apimgt.rest.integration.tests.publisher.api.APIIndividualApi;
import org.wso2.carbon.apimgt.rest.integration.tests.publisher.model.DocumentList;

/**
 * API tests for DocumentCollectionApi
 */
public class DocumentCollectionApiIT {

    private final DocumentCollectionApi api = new DocumentCollectionApi();
    private final APICollectionApi apiSetup = new APICollectionApi();
    private final APIIndividualApi apiIndividualApi = new APIIndividualApi();
    TestUtils testUtils = new TestUtils();

    @Test
    public void apisApiIdDocumentsGetTest() throws ApiException {
        String apiId = testUtils.createApi("API-141", "1.0.0", "API-141");
        Document document1 = new Document();
        String ifUnmodifiedSince = null;
        String ifMatch = null;
        document1.setName("Help1");
        document1.setType(Document.TypeEnum.SWAGGER_DOC);
        document1.setSourceType(Document.SourceTypeEnum.FILE);
        document1.setVisibility(Document.VisibilityEnum.PRIVATE);
        api.apisApiIdDocumentsPost(apiId, document1, ifMatch, ifUnmodifiedSince);

        Document document2 = new Document();
        document2.setName("Help2");
        document2.setType(Document.TypeEnum.PUBLIC_FORUM);
        document2.setSourceType(Document.SourceTypeEnum.INLINE);
        document2.setVisibility(Document.VisibilityEnum.OWNER_ONLY);
        api.apisApiIdDocumentsPost(apiId, document2, ifMatch, ifUnmodifiedSince);

        Integer limit = 10;
        Integer offset = 0;
        String ifNoneMatch = null;
        DocumentList response = api.apisApiIdDocumentsGet(apiId, limit, offset, ifNoneMatch);

        int count = response.getCount();
        Assert.assertEquals(count, 2, "document count mismatch");
        if (response.getList().get(0).getName().equals("Help1")) {
            Assert.assertEquals(response.getList().get(0).getName(), "Help1", "Document name mismatch");
            Assert.assertEquals(response.getList().get(0).getType().toString(), "SWAGGER_DOC", "");
            Assert.assertEquals(response.getList().get(0).getVisibility().toString(), "PRIVATE", "");
            Assert.assertEquals(response.getList().get(0).getSourceType().toString(), "FILE", "");

            Assert.assertEquals(response.getList().get(1).getName(), "Help2", "Document name mismatch");
            Assert.assertEquals(response.getList().get(1).getType().toString(), "PUBLIC_FORUM", "");
            Assert.assertEquals(response.getList().get(1).getVisibility().toString(), "OWNER_ONLY", "");
            Assert.assertEquals(response.getList().get(1).getSourceType().toString(), "INLINE", "");
        } else {
            Assert.assertEquals(response.getList().get(0).getName(), "Help2", "Document name mismatch");
            Assert.assertEquals(response.getList().get(0).getType().toString(), "PUBLIC_FORUM", "");
            Assert.assertEquals(response.getList().get(0).getVisibility().toString(), "OWNER_ONLY", "");
            Assert.assertEquals(response.getList().get(0).getSourceType().toString(), "INLINE", "");

            Assert.assertEquals(response.getList().get(1).getName(), "Help1", "Document name mismatch");
            Assert.assertEquals(response.getList().get(1).getType().toString(), "SWAGGER_DOC", "");
            Assert.assertEquals(response.getList().get(1).getVisibility().toString(), "PRIVATE", "");
            Assert.assertEquals(response.getList().get(1).getSourceType().toString(), "FILE", "");
        }
        testUtils.deleteApi();

    }

    @Test
    public void apisApiIdDocumentsGetTest_NF() throws ApiException {
        try {
            String apiId = "invalidId";
            Integer limit = 10;
            Integer offset = 0;
            String ifNoneMatch = null;
            DocumentList response = api.apisApiIdDocumentsGet(apiId, limit, offset, ifNoneMatch);
        } catch (ApiException ae) {
            int responseCode = ae.getCode();
            JsonParser parser = new JsonParser();
            JsonObject responseBody = (JsonObject) parser.parse(ae.getResponseBody());
            String errorMsg = responseBody.get("message").getAsString();

            Assert.assertEquals(responseCode, 404, "Response code mismatch");
            Assert.assertEquals(errorMsg, "API not found", "Response message mismatch");
        }
    }

    @Test
    public void apisApiIdDocumentsPostTest() throws ApiException {
        String apiId = testUtils.createApi("API-140", "1.0.0", "API-140");
        String ifUnmodifiedSince = null;
        String ifMatch = null;
        Document body = new Document();
        body.setName("Help");
        body.setType(Document.TypeEnum.HOWTO);
        body.setSourceType(Document.SourceTypeEnum.INLINE);
        body.setVisibility(Document.VisibilityEnum.API_LEVEL);
        Document response = api.apisApiIdDocumentsPost(apiId, body, ifMatch, ifUnmodifiedSince);

        Assert.assertEquals(response.getSourceType().toString(), "INLINE", "source type mismatch");
        Assert.assertEquals(response.getType().toString(), "HOWTO", "type mismatch");
        Assert.assertEquals(response.getVisibility().toString(), "API_LEVEL", "Visibility mismatch");
        Assert.assertEquals(response.getName(), "Help", "Document name mismatch");
        testUtils.deleteApi();
    }

    /*FAILS
    * Please refer
    * https://github.com/wso2/product-apim/issues/1615
    * Therefore making the test disabled.
    */
    @Test(enabled = false)
    public void apisApiIdDocumentsPostTest_NF() throws ApiException {
        try {
            String apiId = testUtils.createApi("API-143", "1.0.0", "API-143");
            Document body = new Document();
            body.setName("Help");
            body.setVisibility(Document.VisibilityEnum.API_LEVEL);
            // body.setType(Document.TypeEnum.HOWTO);
            body.setSourceType(Document.SourceTypeEnum.INLINE);
            String ifUnmodifiedSince = null;
            String ifMatch = null;
            api.apisApiIdDocumentsPost(apiId, body, ifMatch, ifUnmodifiedSince);
        } catch (ApiException ae) {
            int responseCode = ae.getCode();
            JsonParser parser = new JsonParser();
            JsonObject responseBody = (JsonObject) parser.parse(ae.getResponseBody());
            String errorMsg = responseBody.get("message").getAsString();
            System.out.println(ae.getResponseBody());

            Assert.assertEquals(responseCode, 400, "Response code mismatch");
            Assert.assertEquals(errorMsg, "Bad Request", "Response message mismatch");
        }

    }

    @AfterClass
    public void afterClass() throws ApiException {
        APIList response = apiSetup.apisGet(10, 0, null, null);
        for (int i = 0; i < response.getCount(); i++) {
            apiIndividualApi.apisApiIdDelete(response.getList().get(i).getId(), null, null);
        }
    }
}

