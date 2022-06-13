package com.kafan.clear.api

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.annotation.RawRes
import okhttp3.OkHttpClient
import java.io.*
import java.security.*
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

object SSLHelper {

    @get:SuppressLint("PrivateApi")
    private val mContext: Context?
        get() {
            var application: Application? = null
            try {
                application = Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication")
                    .invoke(null, null as Array<Any?>?) as Application
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return application
        }

    /**
     * get public key from certificate file in raw folder
     * @param certificateRes resId of certificate
     * @return instance of [PublicKey]
     */
    fun getPublicKey(context: Context, @RawRes certificateRes: Int): PublicKey? {
        try {
            val fin = context.resources.openRawResource(certificateRes)
            val f = CertificateFactory.getInstance("X.509")
            val certificate = f.generateCertificate(fin) as X509Certificate
            return certificate.publicKey
        } catch (e: CertificateException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * get public key from local certificate file
     * @param certificatePath file path of certificate
     * @return instance of [PublicKey]
     */
    fun getPublicKey(certificatePath: String?): PublicKey? {
        try {
            val fin = FileInputStream(certificatePath)
            val f = CertificateFactory.getInstance("X.509")
            val certificate = f.generateCertificate(fin) as X509Certificate
            return certificate.publicKey
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: CertificateException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * generate a instance of [X509TrustManager] config as trust all
     * @return instance of X509TrustManager
     */
    fun createTrustAllTrustManager(): TrustManager {
        return object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {
                // do nothing
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {
                // do nothing
            }
        }
    }

    /**
     * generate a https HostNameVerifier with inputted urls
     * @param hostUrls accept host urls
     */
    fun getHostnameVerifier(hostUrls: Array<String>): HostnameVerifier {
        return HostnameVerifier { hostname: String?, session: SSLSession? ->
            var ret = false
            for (host in hostUrls) {
                if (host.equals(hostname, ignoreCase = true)) {
                    ret = true
                }
            }
            ret
        }
    }

    /**
     * generate a https HostNameVerifier accept all host
     */
    val trustAllVerifier: HostnameVerifier
        get() = HostnameVerifier { hostname: String?, session: SSLSession? -> true }

    /**
     * generate a [KeyStore] Object from KeyStore file
     *
     *
     * attention: Android only support BKS format key store, which means you have to use
     * ".bks" certificate file format, you have to convert other key store format to bks
     * to use it in Android
     *
     *
     * @param keyStoreFileStream key store file input stream from local file, such as raw resource and sdcard
     * @param password key store password
     * @return [KeyStore] instance
     */
    @Throws(
        KeyStoreException::class,
        CertificateException::class,
        NoSuchAlgorithmException::class,
        IOException::class
    )
    fun createKeyStore(keyStoreFileStream: InputStream?, password: String): KeyStore? {
        if (keyStoreFileStream == null) {
            return null
        }
        val keyStore = KeyStore.getInstance("BKS")
        keyStore.load(keyStoreFileStream, password.toCharArray())
        return keyStore
    }

    /**
     * generate a [KeyStore] Object from KeyStore file
     *
     *
     * attention: Android only support BKS format key store, which means you have to use
     * ".bks" certificate file format, you have to convert other key store format to bks
     * to use it in Android
     *
     *
     * @param rawResId resId of certificate file in raw resource folder
     * @param password key store password
     * @return [KeyStore] instance
     */
    @Throws(
        CertificateException::class,
        NoSuchAlgorithmException::class,
        KeyStoreException::class,
        IOException::class
    )
    fun createKeyStore(@RawRes rawResId: Int, password: String): KeyStore? {
        val createKey = mContext?.resources?.openRawResource(rawResId)
        return createKeyStore(createKey, password)
    }

    /**
     * generate a [KeyStore] Object from KeyStore file
     *
     *
     * attention: Android only support BKS format key store, which means you have to use
     * ".bks" certificate file format, you have to convert other key store format to bks
     * to use it in Android
     *
     *
     * @param certFile certificate file object
     * @param password key store password
     * @return [KeyStore] instance
     */
    @Throws(
        CertificateException::class,
        NoSuchAlgorithmException::class,
        KeyStoreException::class,
        IOException::class
    )
    fun createKeyStore(certFile: File?, password: String): KeyStore? {
        val createKey: InputStream = FileInputStream(certFile)
        return createKeyStore(createKey, password)
    }

    /**
     * sss trust all
     */
    fun configTrustAll(builder: OkHttpClient.Builder) {
        val trustAllCerts = arrayOf(createTrustAllTrustManager())
        try {
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(HostnameVerifier { hostname: String?, session: SSLSession? -> true })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * SSL single certificate config for OkHttpClient
     */
    fun configSingleTrust(builder: OkHttpClient.Builder) {
        try {
            //this file do not exist, replace with your certificate file when you use this method
            val keyStore = createKeyStore(File("test.bks"), "123456")
            val tmf = TrustManagerFactory.getInstance("X509")
            tmf.init(keyStore)
            val trustManagers = tmf.trustManagers
            val sslContext = SSLContext.getInstance("SSL")
            val sslSocketFactory = sslContext.socketFactory
            builder.sslSocketFactory(sslSocketFactory, trustManagers[0] as X509TrustManager)
            builder.hostnameVerifier(HostnameVerifier { hostname: String?, session: SSLSession? -> true })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}