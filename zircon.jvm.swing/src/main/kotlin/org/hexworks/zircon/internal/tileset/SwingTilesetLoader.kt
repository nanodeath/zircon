package org.hexworks.zircon.internal.tileset

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.resource.TileType.CHARACTER_TILE
import org.hexworks.zircon.internal.resource.TileType.GRAPHIC_TILE
import org.hexworks.zircon.internal.resource.TileType.IMAGE_TILE
import org.hexworks.zircon.internal.resource.TilesetType.CP437_TILESET
import org.hexworks.zircon.internal.resource.TilesetType.GRAPHIC_TILESET
import org.hexworks.zircon.internal.resource.TilesetType.TRUE_TYPE_FONT
import java.awt.Graphics2D
import java.util.ServiceLoader

@Suppress("UNCHECKED_CAST")
class SwingTilesetLoader : TilesetLoader<Graphics2D>, Closeable {

    override val isClosed = false.toProperty()

    private val tilesetCache = mutableMapOf<UUID, Tileset<Graphics2D>>()

    override fun loadTilesetFrom(resource: TilesetResource): Tileset<Graphics2D> {
        return tilesetCache.getOrPut(resource.id) {
            LOADERS[resource.getLoaderKey()]?.invoke(resource)
                    ?: customLoaders[resource.getLoaderKey()]?.load(resource)
                    ?: throw IllegalArgumentException("Unknown tile type '${resource.tileType}', can't use ${resource.getLoaderKey()}.§")
        }
    }

    private val customLoaders: Map<String, CustomLoader> by lazy {
        ServiceLoader.load(CustomLoader::class.java).asSequence()
            .map { it.loaderKey to it }
            .toMap()
    }

    override fun close() {
        isClosed.value = true
        tilesetCache.clear()
    }

    interface CustomLoader {
        val loaderKey: String
        fun load(tilesetResource: TilesetResource): Tileset<Graphics2D>
    }

    companion object {

        fun TilesetResource.getLoaderKey(): String {
            val subtype = this.tilesetSubtype?.let { "-$it" }.orEmpty()
            return "${this.tileType.name}-${this.tilesetType.name}$subtype"
        }

        private val LOADERS: Map<String, (TilesetResource) -> Tileset<Graphics2D>> = mapOf(
                "$CHARACTER_TILE-$CP437_TILESET" to { resource: TilesetResource ->
                    Java2DCP437Tileset(
                            resource = resource,
                            source = ImageLoader.readImage(resource)
                    )
                },
                "$GRAPHIC_TILE-$GRAPHIC_TILESET" to { resource: TilesetResource ->
                    Java2DGraphicTileset(resource)
                },
                "$CHARACTER_TILE-$TRUE_TYPE_FONT" to { resource: TilesetResource ->
                    MonospaceAwtFontTileset(resource)
                },
                "$IMAGE_TILE-$GRAPHIC_TILESET" to { resource: TilesetResource ->
                    Java2DImageDictionaryTileset(resource)
                })
    }
}
