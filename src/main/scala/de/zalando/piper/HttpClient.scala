package de.zalando.piper

import com.ning.http.client.providers.netty.NettyAsyncHttpProvider
import com.ning.http.client.{AsyncHttpClient, AsyncHttpClientConfig}

/**
 * @author dpersa
 */
object HttpClient {

  def client: AsyncHttpClient = {
    val asyncHttpClientConfig = new AsyncHttpClientConfig.Builder()
      .setAcceptAnyCertificate(true)
      .setIOThreadMultiplier(50)
      .setAllowPoolingSslConnections(true)
      .setAllowPoolingConnections(true)
      .build
    val nettyAsyncHttpProvider = new NettyAsyncHttpProvider(asyncHttpClientConfig)
    new AsyncHttpClient(nettyAsyncHttpProvider)
  }
}
