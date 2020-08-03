package com.enjoy.http;

import android.util.Log;

import java.net.URL;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.CertificatePinner;


public class SSLPinLookup {


    public static void lookup(String path) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(path).openConnection();
            connection.connect();
            for (Certificate certificate : connection.getServerCertificates()) {
                X509Certificate x509Certificate = (X509Certificate) certificate;
                byte[] publicKeyEncoded = x509Certificate.getPublicKey().getEncoded();
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                byte[] publicKeySha256 = messageDigest.digest(publicKeyEncoded);
                String publicKeyShaBase64 = com.enjoy.network.utils.Base64.encode(publicKeySha256);
                System.out.println(x509Certificate.getSubjectDN().getName());
                System.out.println("sha256/" + publicKeyShaBase64);
                System.out.println(CertificatePinner.pin(x509Certificate));
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
