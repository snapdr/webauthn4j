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

import com.webauthn4j.converter.util.CborConverter;
import com.webauthn4j.converter.util.JsonConverter;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.data.client.challenge.DefaultChallenge;
import com.webauthn4j.data.extension.client.AuthenticationExtensionsClientInputs;
import com.webauthn4j.data.extension.client.RegistrationExtensionClientInput;
import com.webauthn4j.demo.app.api.endpoint.*;
import com.webauthn4j.demo.app.api.options.AttestationOptions;
import com.webauthn4j.demo.app.api.options.OptionsProvider;
import com.webauthn4j.util.Base64UrlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApiController {

    private final OptionsProvider optionsProvider;
    private final JsonConverter jsonConverter;
    private final CborConverter cborConverter;


    public ApiController(OptionsProvider optionsProvider, ObjectConverter objectConverter) {
        this.optionsProvider = optionsProvider;
        this.jsonConverter = objectConverter.getJsonConverter();
        this.cborConverter = objectConverter.getCborConverter();

    }

    @GET
    @Path("attestation/options")
    public ServerPublicKeyCredentialCreationOptionsResponse attestationOptions(ServerPublicKeyCredentialCreationOptionsRequest request, HttpServletRequest httpServletRequest){
        String username = request.getUsername();
        String displayName = request.getDisplayName();
        Challenge challenge = encodeUsername(new DefaultChallenge(), username);
        AttestationOptions attestationOptions = optionsProvider.getAttestationOptions(httpServletRequest, username, challenge);
        String userHandle;
        if (attestationOptions.getUser() == null) {
            userHandle = Base64UrlUtil.encodeToString(generateUserHandle());
        } else {
            userHandle = Base64UrlUtil.encodeToString(attestationOptions.getUser().getId());
        }
        ServerPublicKeyCredentialUserEntity user = new ServerPublicKeyCredentialUserEntity(userHandle, username, displayName, null);
        List<ServerPublicKeyCredentialDescriptor> credentials =
                attestationOptions.getCredentials().stream()
                        .map(credential -> new ServerPublicKeyCredentialDescriptor(credential.getType(), Base64UrlUtil.encodeToString(credential.getId()), credential.getTransports()))
                        .collect(Collectors.toList());
        AuthenticationExtensionsClientInputs<RegistrationExtensionClientInput> authenticationExtensionsClientInputs;
        if (request.getExtensions() != null) {
            authenticationExtensionsClientInputs = request.getExtensions();
        } else {
            authenticationExtensionsClientInputs = attestationOptions.getRegistrationExtensions();
        }

        return new ServerPublicKeyCredentialCreationOptionsResponse(
                attestationOptions.getRelyingParty(),
                user,
                Base64UrlUtil.encodeToString(attestationOptions.getChallenge().getValue()),
                attestationOptions.getPubKeyCredParams(),
                attestationOptions.getRegistrationTimeout(),
                credentials,
                request.getAuthenticatorSelection(),
                request.getAttestation(),
                authenticationExtensionsClientInputs);
    }

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

    Challenge encodeUsername(Challenge challenge, String username) {
        UsernameEncodedChallengeEnvelope envelope = new UsernameEncodedChallengeEnvelope();
        envelope.setChallenge(challenge.getValue());
        envelope.setUsername(username);
        byte[] bytes = cborConverter.writeValueAsBytes(envelope);
        return new DefaultChallenge(bytes);
    }

    String decodeUsername(Challenge challenge) {
        try {
            UsernameEncodedChallengeEnvelope envelope = cborConverter.readValue(challenge.getValue(), UsernameEncodedChallengeEnvelope.class);
            return envelope.getUsername();
        } catch (RuntimeException e) {
            return null;
        }
    }

    private byte[] generateUserHandle() {
        UUID uuid = UUID.randomUUID();
        long hi = uuid.getMostSignificantBits();
        long lo = uuid.getLeastSignificantBits();
        return ByteBuffer.allocate(16).putLong(hi).putLong(lo).array();
    }
}
