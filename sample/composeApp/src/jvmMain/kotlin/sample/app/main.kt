import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import sample.app.App
import java.awt.Dimension

fun main() = application {
    Window(
        title = "sample",
        state = rememberWindowState(width = 400.dp, height = 800.dp),
        onCloseRequest = ::exitApplication,
    ) {
//        window.minimumSize = Dimension(350, 600)
        App()
    }
}