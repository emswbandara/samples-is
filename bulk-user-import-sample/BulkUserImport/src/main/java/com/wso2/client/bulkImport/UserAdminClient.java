/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.wso2.client.bulkImport;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.wso2.carbon.user.mgt.stub.UserAdminStub;
import org.wso2.carbon.user.mgt.stub.UserAdminUserAdminException;

import javax.activation.DataHandler;
import java.rmi.RemoteException;

class UserAdminClient {
    private static final String SERVICE_NAME = "UserAdmin";
    private UserAdminStub UserAdminStub;

    /**
     * This constructor authenticates the user.
     * @param backEndUrl URL of the product.
     * @param sessionCookie Session Cookie generated by the LoginAdminServiceClient authenticate() method.
     * @throws AxisFault .
     */
    UserAdminClient(String backEndUrl, String sessionCookie) throws AxisFault {
        String endPoint = backEndUrl + "/services/" + SERVICE_NAME;
        UserAdminStub = new UserAdminStub(endPoint);

        //Authenticate Your stub from sessionCookie
        ServiceClient serviceClient;
        Options option;

        serviceClient = UserAdminStub._getServiceClient();
        option = serviceClient.getOptions();
        option.setManageSession(true);
        option.setProperty(HTTPConstants.COOKIE_STRING, sessionCookie);
    }


    /**
     * @param userStoreName If you keep it blank, the PRIMARY user store will be used.
     * @param defaultPassword Default password for the new users
     * @param file the .csv file with the users. IMPORTANT: The first line of the file is ALWAYS removed.
     * @param fileName The file name =)
     * @throws UserAdminUserAdminException .
     * @throws RemoteException .
     */
    void BulkImportUsers(String userStoreName, String defaultPassword, DataHandler file, String fileName) throws UserAdminUserAdminException, RemoteException {

        UserAdminStub.bulkImportUsers(userStoreName,fileName,file,defaultPassword);
    }
}


