package apps.nb.working.pocmvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import apps.nb.working.pocmvvm.navigation.MyNavHost
import apps.nb.working.pocmvvm.ui.theme.POCMVVMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            POCMVVMTheme {
                Surface(tonalElevation = 5.dp) {
                    // AppNavHost()
                    MyNavHost()
                }
            }
        }
    }
}
