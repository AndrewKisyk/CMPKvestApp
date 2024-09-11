package screen.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import composemultiplatform.shared.generated.resources.Res
import composemultiplatform.shared.generated.resources.onboarding
import composemultiplatform.shared.generated.resources.onboarding_1
import composemultiplatform.shared.generated.resources.onboarding_2
import composemultiplatform.shared.generated.resources.onboarding_3
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import ui.icon.Icons
import ui.icon.icons.AccountCircle
import ui.icon.icons.Build
import ui.icon.icons.Call
import ui.icon.icons.DateRange
import ui.icon.icons.Favorite
import ui.icon.icons.Home
import ui.icon.icons.Mail
import ui.theme.App

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onOnboardingComplete: () -> Unit,
) {
    val viewModel = koinViewModel<OnboardingViewModel>()
    val isOnboardingCompleted by viewModel.isOnboardingCompleted.collectAsStateWithLifecycle(initialValue = null)
    val updatedOnOnboardingComplete by rememberUpdatedState(onOnboardingComplete)
    LaunchedEffect(isOnboardingCompleted) {
        if (isOnboardingCompleted == true) updatedOnOnboardingComplete()
    }
    AnimatedContent(
        targetState = isOnboardingCompleted,
        modifier = modifier,
    ) { onboardingCompleted ->
        when (onboardingCompleted) {
            false -> Onboarding(onClick = { viewModel.completeOnboarding() })
            null,
            true,
            -> Splash()
        }
    }
}

@Composable
private fun Splash(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize().background(color = App.colors.splash),
    ) {
        Image(
            painter = painterResource(Res.drawable.onboarding),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().aspectRatio(1f),
        )
    }
}

@Composable
private fun Onboarding(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    var imageIndex by remember { mutableIntStateOf(0) }
    val imageVector by remember(imageIndex) {
        derivedStateOf {
            when (imageIndex) {
                0 -> Res.drawable.onboarding_1
                1 -> Res.drawable.onboarding_2
                2 -> Res.drawable.onboarding_3
                else -> null
            }
        }
    }
    LaunchedEffect(imageIndex) {
        delay(1_000)
        imageIndex++
    }
    AnimatedContent(
        targetState = imageVector,
        modifier = modifier,
    ) { vector ->
        if (vector != null) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Image(
                    painterResource(vector),
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(ratio = 1f)
                        .fillMaxSize(),
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxSize(),
            ) {
                Button(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth().padding(all = 24.dp),
                ) {
                    Text(text = "Complete")
                }
            }
        }
    }
}
