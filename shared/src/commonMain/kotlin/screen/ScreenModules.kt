package screen

import screen.detail.detailModule
import screen.map.mapModule
import screen.onboarding.onboardingModule

val screenModules
    get() = listOf(
        detailModule,
        onboardingModule,
        mapModule
    )
