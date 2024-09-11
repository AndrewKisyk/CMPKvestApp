package screen.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import permissions.Permission
import permissions.PermissionsController

class MapViewModel(
    val permissionsController: PermissionsController,
) : ViewModel() {

    fun start() {
        viewModelScope.launch {
            permissionsController.providePermission(Permission.LOCATION)
        }
    }
}
