package screen.map

import org.koin.dsl.module

internal val mapModule
    get() = module {
        factory {
            MapViewModel(permissionsController = get())
        }
    }
