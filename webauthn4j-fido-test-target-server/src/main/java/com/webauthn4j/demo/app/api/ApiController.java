/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webauthn4j.demo.app.api;

import com.webauthn4j.converter.util.ObjectConverter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApiController {

    @GET
    @Path("hello")
    public String login() {
        ObjectConverter objectConverter = new ObjectConverter();
        return "hello";
    }

//    @GET
//    @Path("attestation/options")
//    public ServerPublicKeyCredentialCreationOptionsResponse attestationOptions(ServerPublicKeyCredentialCreationOptionsRequest request){
//        return null;
//    }

//    @POST
//    @Path("attestation/options/result")
//    public ServerResponse attestationOptionsResult(ServerPublicKeyCredential<ServerAuthenticatorAttestationResponse> request){
//        return null;
//    }
//
//    @GET
//    @Path("assertion/options")
//    public ServerPublicKeyCredentialGetOptionsResponse assertionOptions(ServerPublicKeyCredentialGetOptionsRequest request){
//        return null;
//    }
//
//    @POST
//    @Path("assertion/options/result")
//    public ServerResponse assertionOptionsResult(ServerPublicKeyCredential<ServerAuthenticatorAssertionResponse> request){
//        return null;
//    }

}
