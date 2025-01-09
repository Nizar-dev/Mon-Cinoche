package apps.nb.working.pocmvvm.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CinocheConnectivityObserver @Inject constructor(
    @ApplicationContext context: Context
) : ConnectivityObserver {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    override fun monitorNetwork(): Flow<NetworkStatus> = callbackFlow {
        val callBack = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(NetworkStatus.Connected)
            }
            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(NetworkStatus.Lost)
            }
        }
        connectivityManager.registerDefaultNetworkCallback(callBack)
        awaitClose {
            connectivityManager.unregisterNetworkCallback(callBack)
        }
    }
}
