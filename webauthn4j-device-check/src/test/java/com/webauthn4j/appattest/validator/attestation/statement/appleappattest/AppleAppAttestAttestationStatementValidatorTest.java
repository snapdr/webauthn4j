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

package com.webauthn4j.appattest.validator.attestation.statement.appleappattest;

import com.webauthn4j.appattest.validator.DCRegistrationObject;
import com.webauthn4j.test.TestDataUtil;
import com.webauthn4j.validator.CoreRegistrationObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class AppleAppAttestAttestationStatementValidatorTest {

    private final AppleAppAttestAttestationStatementValidator target = new AppleAppAttestAttestationStatementValidator();

    @Test
    void validate_test() {
        DCRegistrationObject registrationObject = TestDataUtil.createRegistrationObjectWithAppleAppAttestAttestation();
        target.validate(registrationObject);
    }

    @Test
    void validate_CoreRegistrationObject_test(){
        assertThatThrownBy(()->{
            target.validate(mock(CoreRegistrationObject.class));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void supports_CoreRegistrationObject_test(){
        assertThat(target.supports(TestDataUtil.createRegistrationObjectWithPackedAttestation())).isFalse();
    }
}
