package ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import com.mapbox.geojson.Point
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.DefaultSettingsProvider.createDefault2DPuck
import com.mapbox.maps.extension.compose.DefaultSettingsProvider.defaultAttributionSettings
import com.mapbox.maps.extension.compose.DefaultSettingsProvider.defaultScaleBarSettings
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PolygonAnnotation
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.attribution.generated.AttributionSettings
import com.mapbox.maps.plugin.compass.generated.CompassSettings
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.logo.generated.LogoSettings
import com.mapbox.maps.plugin.viewport.viewport
import composemultiplatform.shared.generated.resources.Res
import composemultiplatform.shared.generated.resources.mapbox_style
import composemultiplatform.shared.generated.resources.rdr_compass
import kotlinx.collections.immutable.ImmutableList
import model.coordinate.LatLng
import model.coordinate.Polygon
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.stringResource


@OptIn(MapboxExperimental::class)
@Composable
actual fun Map(
    modifier: Modifier,
    contentPadding: PaddingValues,
    polygons: ImmutableList<Polygon>,
) = with(LocalDensity.current) {
    val context = LocalContext.current
    val vienna = LatLng(48.2082, 16.3719)

    val scaleBarSettings = remember(contentPadding) {
        defaultScaleBarSettings(context).toBuilder().apply {
            setMarginTop(marginTop + contentPadding.calculateTopPadding().toPx())
            setMarginBottom(marginBottom + contentPadding.calculateBottomPadding().toPx())
            setMarginLeft(marginLeft)
            setMarginRight(marginRight)
        }.build()
    }
    val attributionSettings = remember(contentPadding) {
        defaultAttributionSettings(context).toBuilder().apply {
            setMarginTop(marginTop + contentPadding.calculateTopPadding().toPx())
            setMarginBottom(marginBottom + contentPadding.calculateBottomPadding().toPx())
            setMarginLeft(marginLeft)
            setMarginRight(marginRight)
        }.build()
    }

    val compassLogo = imageResource(Res.drawable.rdr_compass)

    val compassSettings = CompassSettings {
        setMarginTop(marginTop + contentPadding.calculateTopPadding().toPx())
        setMarginBottom(marginBottom + contentPadding.calculateBottomPadding().toPx())
        setMarginLeft(marginLeft)
        setMarginRight(marginRight)
        setImage(ImageHolder.from(compassLogo.asAndroidBitmap()))
    }

    val mapboxStyle = stringResource(Res.string.mapbox_style)

    MapboxMap(
        modifier = Modifier.fillMaxSize(),
        mapViewportState = MapViewportState().apply {
            setCameraOptions {
                zoom(15.0)
                center(Point.fromLngLat(vienna.longitude, vienna.latitude))
                pitch(0.0)
                bearing(0.0)
            }
        },
        mapInitOptionsFactory = { factoryContext ->
            MapInitOptions(
                context = factoryContext,
                styleUri = mapboxStyle,
            )
        },
        logoSettings = LogoSettings { enabled = false },
        scaleBarSettings = scaleBarSettings,
        attributionSettings = AttributionSettings { enabled = false },
        compassSettings = compassSettings,
    ) {
        polygons.forEach { polygon ->
            PolygonAnnotation(
                points = listOf(polygon.latLngs.map { Point.fromLngLat(it.longitude, it.latitude) }),
            )
        }

        MapEffect(Unit) { mapView ->
            with(mapView) {
                location.locationPuck = createDefault2DPuck(withBearing = true)
                location.enabled = true
                location.puckBearing = PuckBearing.COURSE
                viewport.transitionTo(
                    targetState = viewport.makeFollowPuckViewportState(),
                    transition = viewport.makeImmediateViewportTransition(),
                )
            }
        }
    }
}
