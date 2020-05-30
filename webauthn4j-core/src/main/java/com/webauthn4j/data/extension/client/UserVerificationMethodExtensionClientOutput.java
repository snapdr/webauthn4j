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

package com.webauthn4j.data.extension.client;

import com.webauthn4j.data.extension.SingleValueExtensionOutputBase;
import com.webauthn4j.data.extension.UvmEntries;
import com.webauthn4j.validator.exception.ConstraintViolationException;

public class UserVerificationMethodExtensionClientOutput
        extends SingleValueExtensionOutputBase<UvmEntries>
        implements RegistrationExtensionClientInput, AuthenticationExtensionClientOutput {

    public static final String ID = "uvm";

    public UserVerificationMethodExtensionClientOutput(UvmEntries value) {
        super(value);
    }

    @Override
    public String getIdentifier() {
        return ID;
    }

    @Override
    public void validate() {
        if(getValue() == null){
            throw new ConstraintViolationException("value must not be null");
        }
    }

}
