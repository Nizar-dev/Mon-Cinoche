package apps.nb.working.pocmvvm.network

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun monitorNetwork(): Flow<NetworkStatus>
}

enum class NetworkStatus {
    Connected,
    Lost
}
