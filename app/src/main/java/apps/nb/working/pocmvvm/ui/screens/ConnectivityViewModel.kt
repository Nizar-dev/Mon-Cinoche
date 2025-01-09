package apps.nb.working.pocmvvm.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apps.nb.working.pocmvvm.network.CinocheConnectivityObserver
import apps.nb.working.pocmvvm.network.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ConnectivityViewModel @Inject constructor(
    connectivityObserver: CinocheConnectivityObserver
) : ViewModel() {
    val networkStatus = connectivityObserver.monitorNetwork()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NetworkStatus.Connected
        )
}
