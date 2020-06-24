/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webauthn4j.data.extension;

import com.webauthn4j.validator.exception.ConstraintViolationException;

import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public abstract class SingleValueExtensionOutputBase<T extends Serializable> implements ExtensionOutput {

    private final T value;

    public SingleValueExtensionOutputBase(T value) {
        this.value = value;
    }

    @Override
    public Set<String> getKeys() {
        return Collections.singleton(getIdentifier());
    }

    @Override
    public T getValue(String key) {
        if(!key.equals(getIdentifier())){
            throw new IllegalArgumentException(String.format("%s is the only valid key.", getIdentifier()));
        }
        return value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public void validate() {
        if(getValue() == null){
            throw new ConstraintViolationException("value must not be null");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleValueExtensionOutputBase<?> that = (SingleValueExtensionOutputBase<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}