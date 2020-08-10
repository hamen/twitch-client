package helix.extensions.model.active

import helix.http.model.AbstractResource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActiveExtensions(
    @SerialName("panel")
    val panelExtensions: PanelExtensions,
    @SerialName("overlay")
    val overlayExtensions: OverlayExtensions,
    @SerialName("component")
    val componentExtension: ComponentExtensions
) : AbstractResource()