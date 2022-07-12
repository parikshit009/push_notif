package com.example.dremio.demo;

import java.security.KeyFactory;
import java.security.KeyRep;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SignatureGenerator {
    String consumerId = "";

    String privateKey = "";

    long intimestamp = 0L;



    public long getIntimestamp() {
        return this.intimestamp;
    }

    public void setIntimestamp(long intimestamp) {
        this.intimestamp = intimestamp;
    }



    public SignatureGenerator() {

    }

    public String[] getToken(String env) {
        if (env.equalsIgnoreCase("qa")) {
            this.consumerId = "0c2ad45e-8c65-409f-ab63-bafbc2290248";
            this.privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDgkhfoKdtiMduc8u/aysegKaNJWFHPO3EoCAwBbEZuVTkP+3VOeR7Dnej6P+eaCmRx0Dye7pekHSfSvzV+3MY2bJsJuvb1pguGkl0i8kyPMvCefzDza20PAXHH9SR+FPzC2Tc05uy16LclZt1FyiqyoOSEFN4TXc7b0fqbW/BimF5jiTezWLusYVgQQclK7LTUS+cPnvOwksXJyNacvnVXjpG0uNs8mgCbZS8PXMWPnHkG58W1Fy4Uxn8GaVmua0up8VM38ZK807pY+DWq+/xTkIotgGxNBXPtOBPiUhUAfmMWlX3uYjgcd+dSjxlF5bVmpGeY/DDvgCu0tEpx3PuzAgMBAAECggEBAJFJ822gpu7AkvTXt5Y12zEjvyCUo1kPfAYKLro88tQvDtvoM9yxP9YPM8uzzIM8df+sWYv5kOH8euc+Db4l15cebK++5kfHRW2rA9a4LA/5RPNGFsVlmwk6RgK39wQbT+w/3Be9zydhiBwKkN22DinDLhTF62dcKZjOIPSiZsNh2m7LSyBFXr+IIK3TuV3CO9QQYb7IWorV5RhbPA9nM9iGSWTOnelgL8USG/gT1ScGYEFSocu8OyDV8077pCnrqQIN2d8qlDkSlJSpl6KEDevhxUAaj3uBrJC3oowjLcRdxjU9rXMUQU+qJpW4GArJZPKQoAutSdcSYTIhU0cHQQECgYEA8IRB7nIK8qemg4bOLgSPcWUtcCyrGbxGRTCjrtX4MWiz6No0CAooICQGa+ocoR3FJofZ8R8qrKsiqo+M3OKQvLp1oE4h3MO8lsN9EW3xhkxgA6qfFVu9yZrosiV7f7S2C3q8W7tM66jjs6K+Y2PRy17sFWlT93GvWaPTStmNq60CgYEA7wcLbbOVRydFvsPeyxWz2+7y3GwRpUk1/nvGjbnPU5Wgr4yC4GItt0gF4WrnL9//FDyreAyQsMQms/nEBxlteZmPn7UJ8UQDiuf93mrbaZELhPRLUQNWflo0y8krPrGIhRdIypNKTr8Nyc9Q6s35vHUjcFlPOBzZLNij6YTUMN8CgYEA7XWmooR1yp07CSBO76AQ/yOleNqhEwePWUBsWnFmWWHGPl8wf7+HXjQzIthA69E4lJIL2sOqnk9YKo0PxgTqYWq2N07sn8mgqDjZghvKGY3OPdB2v9+TwTfwBfJ7u9nu4eaLkQWbLA/Sv8gtsHMc7m0JKRqUi/jx5s6YDY49EQECgYBrN5i7w4MhYI9ba5bDN9/utnc/wkYEAxvyI+lDZ2aFUqe6Nuf56HAjN25T19UN14cet7weZFx+ve2ya0Wv/YrqsaQH+ngUPfLs2vU4UGxNJR+qqr4h/8JLeQEXrAv22kGnmXMwUvNQ8UgpDxJ30J7GFzEnUxhCzkXoZODZfZiEtwKBgQCPvpUga1zfoKVS1dxXPUnkK8xngXBcCGp7yrXT1BdD0GMEFR2X1Joa1w+3lpryjgIxCZoganarTtlt6sEIx3cc+H3bLuduA0sxC9LrBoyBHOYCpB6x5EAZTOLYuJhsX5EvLoXGFMPedDbF9G93F5WMRbgqFDVQAj27HLmQXSOX+g==";
        } else if (env.equalsIgnoreCase("prod")) {
            this.consumerId = "88d42ddf-401f-42ee-903d-7f949a289dde";
            this.privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDKeWMykmEGgwv/06X/lADqpZ/JCJkXGBgZLZ6VJQBP6iQnUoR7A6sBCsmRCrpVyt38l3yzFaWlrroE3aM6k/7j3tPVrKs+K2I+GmCiAX2DsARzcIHdGPLBdGZtYO7WnAtfBOx2ZEUJ/Ioke99y9/ozfwtgT5/bI3Cs8h39kLjd+THAZtfutFcC34xEx9xXzaTgPCZVbKgaJzz6MwuFc45tGlj/3OIKbXetTmhNO8xgDGYPad8qJTfTH4lHjxk6rzvcaIhnCQec1wnUxGsuNlNbrhpWrUIRgHME+PqgSrkBEVM2WMhChsxGqwZKPecqhFte2M+zoXgntAhT3dxOQBczAgMBAAECggEBAIDnn1wHDXaT4APTv49jruG06HGY475sPItrG9dgYz59lt7iDi4zO5/COmn5JoCQU5wI5f8t/PPqtct7P/x/VHvG1Z7n+23tOPLI4FTEFiDM40iLjGIzC/pWHQ/t8qD3X9Xy0IjfdYTSHzDzHGOZtNUsQRXkG6XsfkPdMXLJvKi0Z1baNtuQv0kngXPgh+/qAcLz6glF5D0bMisJiJzbFJZA/vs2rk35aLQG2qk0CeBYAhR4XGl6Zph5hneD+QmgVSFtsLvTvxWPZ9O+97nL00hJO2zjMJhtD8zwBgbRCSo4hxeLeWw1VIbayerjJi0HyY8sjvDLo4GzB6uD0wDuwgECgYEA9brP+VsS2XnjxD9et38lNLzS6bs+A18KWK337ipbXSzwa5wTwD1DVq+qKs8/bQWI9C53HkuQIHE7MydvZz7gpuwSfWwZ2gbs+5/9xsAQ1tdC2KehnLOYnyeH8q8MvfyFrhIQp8kL0cd56kcpuXDK4xTNvVisZ2PHGw+EvPsiIfMCgYEA0u/DAqrZpmko37uTVUo6O6+JdGElgSacfcVsGmVndHav2L+czKkQkGEQminKF1Hc73ufeegWZ2WfIkLo45kjh7MikIebfukY36FVaF7okOL6HmEIw/LkK17OANtU5zEzs8S5HQQaQMjfLsbL63XaaglcifdAPIpbwEo9ypmqRcECgYBFJ8oYFzV61v7u4Ba79aB28x04gmW1dS3oPy2jbXBBDMCsyEQWuicVYmK1oZXsDL66WclUUVfCiZtrjhGgMOXwlEev3Jh577EKL8CwCqWykOpTJvooq7KsN+feuL0RStwkXRkq3z3gUA9Ti6uGcOPofJZaT3ZVspL6Xqewt8ynWQKBgQCzOh+jc5HNkMq3F4U/Ufua3TLY3PZl/222e/m3j/PQhEwzydOsHfbCBod5xgo9uEQWhB7kESyAHFuTW/DP+j2zPqUe0upvchW9ZjLQpNvMx98n0uEExavTohE9rx879ZVtqLh1DBhWdZcVArsfzRqRQHUNkTVWe2UsRKiuLdT8wQKBgQCxT0QiQ7gxQGa0BkeL35fetAfKQQH1V6hNGRrE+OwF7VEDMk4dwiB/ULPvzzx89eQOenIPhBHOdt4xIHzQ8FlVrUO/CM2ku+vtSeWVyjqoHGxhTua7m5lx2F2MBaqBzznd35OFp1d9hS8IDvuO1aysxMtTbzADkZ13QgJgsCRpGw==";
        }
        String privateKeyVersion = "1";
        setIntimestamp(System.currentTimeMillis());
        Map<String, String> map = new HashMap<>();
        map.put("WM_CONSUMER.ID", this.consumerId);
        map.put("WM_CONSUMER.INTIMESTAMP", Long.toString(getIntimestamp()));
        map.put("WM_SEC.KEY_VERSION", privateKeyVersion);
        String[] array = canonicalize(map);
        String token = "";
        try {
            token = generateSignature(this.privateKey, array[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[]{token, map.get("WM_CONSUMER.INTIMESTAMP")};
    }

    private static String generateSignature(String key, String stringToSign) {
        String signatureString = null;
        try {
            Signature signatureInstance = Signature.getInstance("SHA256WithRSA");
            KeyFactory f = KeyFactory.getInstance("RSA");
            PrivateKey resolvedPrivateKey = (PrivateKey)f.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key)));
            signatureInstance.initSign(resolvedPrivateKey);
            byte[] bytesToSign = stringToSign.getBytes("UTF-8");
            signatureInstance.update(bytesToSign);
            byte[] signatureBytes = signatureInstance.sign();
            signatureString = Base64.getEncoder().encodeToString(signatureBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signatureString;
    }

    private static String[] canonicalize(Map<String, String> headersToSign) {
        StringBuffer canonicalizedStrBuffer = new StringBuffer();
        StringBuffer parameterNamesBuffer = new StringBuffer();
        Set<String> keySet = headersToSign.keySet();
        SortedSet<String> sortedKeySet = new TreeSet<>(keySet);
        for (String key : sortedKeySet) {
            Object val = headersToSign.get(key);
            parameterNamesBuffer.append(key.trim()).append(";");
            canonicalizedStrBuffer.append(val.toString().trim()).append("\n");
        }
        return new String[] { parameterNamesBuffer.toString(), canonicalizedStrBuffer.toString() };
    }
}
