/*
 *  Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.saml.context;

import org.wso2.carbon.identity.gateway.api.context.GatewayMessageContext;
import org.wso2.carbon.identity.saml.request.SAMLIDPInitRequest;
import org.wso2.carbon.identity.saml.request.SAMLRequest;
import org.wso2.carbon.identity.saml.model.SAMLResponseHandlerConfig;
import org.wso2.carbon.identity.saml.model.SAMLValidatorConfig;

import java.io.Serializable;
import java.util.Map;

public class SAMLMessageContext<T1 extends Serializable, T2 extends Serializable> extends GatewayMessageContext {

    private static final long serialVersionUID = 104634801939285909L;
    /**
     * The unmarshelled SAML Request
     */
    private String destination;
    private String id;
    private String assertionConsumerUrl;
    private boolean isPassive;

    /**
     * Should be set in validateAuthnRequest
     */
    private boolean isValid;
    private String issuer;
    /**
     * Subject should be validated before set.
     * Validation is done in the request validation.
     */
    private String subject;
    private int attributeConsumingServiceIndex;

    private SAMLValidatorConfig samlValidatorConfig;
    private SAMLResponseHandlerConfig responseHandlerConfig;

    public SAMLMessageContext(SAMLRequest request, Map<T1, T2> parameters) {
        super(request, parameters);
    }

    public String getAssertionConsumerURL() {
        if (!isIdpInitSSO()) {
            return this.assertionConsumerUrl;
        } else {
            return getSamlValidatorConfig().getDefaultAssertionConsumerUrl();
        }
    }

    public int getAttributeConsumingServiceIndex() {
        return attributeConsumingServiceIndex;
    }

    public void setAttributeConsumingServiceIndex(int attributeConsumingServiceIndex) {
        this.attributeConsumingServiceIndex = attributeConsumingServiceIndex;
    }

    public String getDestination() {
        if (!isIdpInitSSO()) {
            return this.destination;
        } else if (isIdpInitSSO()) {
            return ((SAMLIDPInitRequest) this.getIdentityRequest()).getAcs();
        }
        return null;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getId() {
        if (!isIdpInitSSO()) {
            return this.id;
        }
        return null;
    }

    //    public String getRpSessionId() {
    //        return this.request.getParameter(MultitenantConstants.SSO_AUTH_SESSION_ID);
    //    }
    //
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public SAMLRequest getIdentityRequest() {
        return (SAMLRequest) identityRequest;
    }

    public String getIssuer() {
        if (issuer.contains("@")) {
            String[] splitIssuer = issuer.split("@");
            return splitIssuer[0];
        }
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getIssuerWithDomain() {
        return this.issuer;
    }

    public String getRelayState() {
        return this.getIdentityRequest().getRelayState();
    }

    public SAMLResponseHandlerConfig getResponseHandlerConfig() {
        return responseHandlerConfig;
    }

    public void setResponseHandlerConfig(SAMLResponseHandlerConfig responseHandlerConfig) {
        this.responseHandlerConfig = responseHandlerConfig;
    }

    public SAMLValidatorConfig getSamlValidatorConfig() {
        return samlValidatorConfig;
    }

    public void setSamlValidatorConfig(SAMLValidatorConfig samlValidatorConfig) {
        this.samlValidatorConfig = samlValidatorConfig;
    }

    //TODO
   /* public AuthenticatedUser getUser() {
        return this.getAuthenticationResult().getSubject();
    }
*/
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isIdpInitSSO() {
        return this.getIdentityRequest() instanceof SAMLIDPInitRequest;
    }

    public boolean isPassive() {
        return this.isPassive;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public void setAssertionConsumerUrl(String assertionConsumerUrl) {
        this.assertionConsumerUrl = assertionConsumerUrl;
    }

    public void setIsPassive(boolean isPassive) {
        this.isPassive = isPassive;
    }

    /**
     * @return AuthenticationResult saved in the messageContext
     * while authenticating in the framework.
     */
    // TODO
    //    public AuthenticationResult getAuthenticationResult() {
    //        if (this.getParameter(SAMLSSOConstants.AUTHENTICATION_RESULT) != null) {
    //            return (AuthenticationResult) this.getParameter(SAMLSSOConstants.AUTHENTICATION_RESULT);
    //        }
    //        return new AuthenticationResult();
    //    }
}