package di

import org.koin.core.module.Module
import org.koin.dsl.module
import permissions.permissionController
import repository.settings.dataStore

actual val platformModules: List<Module>
    get() = listOf(
        module {
            single { dataStore(context = get()) }
            single { permissionController(context = get()) }
        },
    )
