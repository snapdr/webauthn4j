package com.webauthn4j.data.extension.client;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.webauthn4j.data.extension.UvmEntries;
import com.webauthn4j.util.AssertUtil;

import java.util.*;

/**
 * Map containing the client extension output values for zero or more WebAuthn
 * extensions, as defined in §9 WebAuthn Extensions.
 *
 * @see <a href="https://www.w3.org/TR/webauthn-1/#dictdef-authenticationextensionsclientoutputs">§5.8. Authentication Extensions Client Outputs</a>
 */
public class AuthenticationExtensionsClientOutputs<T extends ExtensionClientOutput>{

    @JsonProperty
    private Boolean appid;
    //appidExclude doesn't exist in ExtensionsClientOutputs
    @JsonProperty
    private UvmEntries uvm;
    @JsonProperty
    private CredentialPropertiesOutput credProps;
    @JsonIgnore
    private Map<String, Object> unknowns = new HashMap<>();
    @JsonIgnore
    private Map<Class<? extends ExtensionClientOutput>, ExtensionClientOutput> extensions;

    @JsonAnySetter
    private void setUnknowns(String name, Object value){
        this.unknowns.put(name, value);
    }

    @JsonAnyGetter
    private Map<String, Object> getUnknowns(){
        return this.unknowns;
    }

    @JsonIgnore
    public Set<String> getKeys() {
        Set<String> keys = new HashSet<>();
        if(appid != null){
            keys.add("appid");
        }
        if(uvm != null){
            keys.add("uvm");
        }
        if(credProps != null){
            keys.add("credProps");
        }
        keys.addAll(getUnknownKeys());
        return keys;
    }

    @JsonIgnore
    public Set<String> getUnknownKeys() {
        return unknowns.keySet();
    }

    @JsonIgnore
    public Object getValue(String key) {
        switch (key){
            case "appid":
                return appid;
            case "uvm":
                return uvm;
            case "credProps":
                return credProps;
            default:
                return unknowns.get(key);
        }
    }

    @JsonIgnore
    public Boolean getAppid(){
        return this.appid;
    }

    @JsonIgnore
    public UvmEntries getUvm(){
        return this.uvm;
    }

    @JsonIgnore
    public CredentialPropertiesOutput getCredProps(){
        return this.credProps;
    }

    @SuppressWarnings("unchecked")
    public <E extends T> E getExtension(Class<E> tClass) {
        return (E)getExtensions().get(tClass);
    }

    @JsonIgnore
    private Map<Class<? extends ExtensionClientOutput>, ? extends ExtensionClientOutput> getExtensions(){
        if(extensions == null){
            extensions = new HashMap<>();
            if(appid != null){
                extensions.put(FIDOAppIDExtensionClientOutput.class, new FIDOAppIDExtensionClientOutput(appid));
            }
            if(uvm != null){
                extensions.put(UserVerificationMethodExtensionClientOutput.class, new UserVerificationMethodExtensionClientOutput(uvm));
            }
            if(credProps != null){
                extensions.put(CredentialPropertiesExtensionClientOutput.class, new CredentialPropertiesExtensionClientOutput(credProps));
            }
        }
        return extensions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationExtensionsClientOutputs<?> that = (AuthenticationExtensionsClientOutputs<?>) o;
        return Objects.equals(appid, that.appid) &&
                Objects.equals(uvm, that.uvm) &&
                Objects.equals(credProps, that.credProps) &&
                Objects.equals(unknowns, that.unknowns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appid, uvm, credProps, unknowns);
    }

    public static class BuilderForRegistration {

        private UvmEntries uvm;
        private CredentialPropertiesOutput credProps;

        private Map<String, Object> unknowns = Collections.emptyMap();

        public BuilderForRegistration(){}

        public AuthenticationExtensionsClientOutputs<RegistrationExtensionClientOutput> build(){
            AuthenticationExtensionsClientOutputs<RegistrationExtensionClientOutput> instance = new AuthenticationExtensionsClientOutputs<>();
            instance.uvm = this.uvm;
            instance.credProps = this.credProps;
            instance.unknowns = this.unknowns;

            return instance;
        }

        public BuilderForRegistration setUvm(UvmEntries uvm){
            this.uvm = uvm;
            return this;
        }

        public BuilderForRegistration setCredProps(CredentialPropertiesOutput credProps){
            this.credProps = credProps;
            return this;
        }

        public BuilderForRegistration setUnknowns(Map<String, Object> unknowns){
            AssertUtil.notNull(unknowns, "unknowns must not be null.");
            this.unknowns = unknowns;
            return this;
        }

    }

    public static class BuilderForAuthentication {

        private Boolean appid;
        private UvmEntries uvm;

        private Map<String, Object> unknowns = Collections.emptyMap();

        public BuilderForAuthentication(){}

        public AuthenticationExtensionsClientOutputs<AuthenticationExtensionClientOutput> build(){
            AuthenticationExtensionsClientOutputs<AuthenticationExtensionClientOutput> instance = new AuthenticationExtensionsClientOutputs<>();
            instance.appid = this.appid;
            instance.uvm = this.uvm;
            instance.unknowns = this.unknowns;

            return instance;
        }

        public AuthenticationExtensionsClientOutputs.BuilderForAuthentication setAppid(Boolean appid){
            this.appid = appid;
            return this;
        }

        public AuthenticationExtensionsClientOutputs.BuilderForAuthentication setUvm(UvmEntries uvm){
            this.uvm = uvm;
            return this;
        }

        public AuthenticationExtensionsClientOutputs.BuilderForAuthentication setUnknowns(Map<String, Object> unknowns){
            AssertUtil.notNull(unknowns, "unknowns must not be null.");
            this.unknowns = unknowns;
            return this;
        }

    }

}
