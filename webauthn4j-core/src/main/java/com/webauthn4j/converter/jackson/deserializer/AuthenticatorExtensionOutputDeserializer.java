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

package com.webauthn4j.converter.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.webauthn4j.extension.authneticator.*;
import com.webauthn4j.util.exception.NotImplementedException;

import java.io.IOException;

/**
 * Jackson Deserializer for {@link AuthenticatorExtensionOutput}
 */
public class AuthenticatorExtensionOutputDeserializer extends StdDeserializer<AuthenticatorExtensionOutput> {

    public AuthenticatorExtensionOutputDeserializer() {
        super(AuthenticatorExtensionOutput.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticatorExtensionOutput deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        String currentName = p.getParsingContext().getCurrentName();

        if (SimpleTransactionAuthorizationAuthenticatorExtensionOutput.ID.equals(currentName)) {
            return ctxt.readValue(p, SimpleTransactionAuthorizationAuthenticatorExtensionOutput.class);
        } else if (UserVerificationIndexAuthenticatorExtensionOutput.ID.equals(currentName)) {
            return ctxt.readValue(p, UserVerificationIndexAuthenticatorExtensionOutput.class);
        }

        String parentName = p.getParsingContext().getParent().getCurrentName();

        switch (parentName) {
            case GenericTransactionAuthorizationAuthenticatorExtensionOutput.ID:
                return ctxt.readValue(p, GenericTransactionAuthorizationAuthenticatorExtensionOutput.class);
            case SupportedExtensionsAuthenticatorExtensionOutput.ID:
                return ctxt.readValue(p, SupportedExtensionsAuthenticatorExtensionOutput.class);
            case LocationAuthenticatorExtensionOutput.ID:
                return ctxt.readValue(p, LocationAuthenticatorExtensionOutput.class);
            case UserVerificationIndexAuthenticatorExtensionOutput.ID:
                return ctxt.readValue(p, UserVerificationIndexAuthenticatorExtensionOutput.class);
        }

        throw new NotImplementedException();
    }
}
