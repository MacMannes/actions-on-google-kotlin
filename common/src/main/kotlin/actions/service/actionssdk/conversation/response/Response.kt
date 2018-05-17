package actions.service.actionssdk.conversation.response

import actions.ApiClientObjectMap
import actions.ProtoAny
import actions.service.actionssdk.api.*
import actions.service.actionssdk.conversation.InputValueSpec
import actions.service.actionssdk.conversation.IntentEnum
import actions.service.actionssdk.conversation.response.card.BasicCardOptions
import actions.service.actionssdk.conversation.response.card.TableOptions
import actions.service.actionssdk.push

sealed class Response


/**
 * Sealed class of all RichResponseItem types
 * Equivalent to TypeScript Union:
 *      type RichResponseItem =
SimpleResponse |
BasicCard |
Table |
BrowseCarousel |
MediaResponse |
OrderUpdate |
LinkOutSuggestion |
Api.GoogleActionsV2RichResponseItem

 *  String type is handled in separate functions
 */
sealed class RichResponseItem: Response()


/**
 * Class for initializing and constructing Rich Responses with chainable interface.
 * @public
 */
class RichResponse(override var items: MutableList<GoogleActionsV2RichResponseItem>? = null,
                   override var linkOutSuggestion: GoogleActionsV2UiElementsLinkOutSuggestion? = null,
                   override var suggestions: MutableList<GoogleActionsV2UiElementsSuggestion>? = null) : GoogleActionsV2RichResponse, Response() {
    /**
     * @param options RichResponse options
     * @public
     */
    constructor(options: RichResponseOptions) : this() {
        this.items = mutableListOf()
        if (options.items != null && options.items?.isNotEmpty() == true) {
            add(*options.items!!.toTypedArray())
        }
        val link = options.link
        val suggestions = options.suggestions
        this.linkOutSuggestion = link
        if (suggestions != null) {
            this.addSuggestion(*suggestions.toTypedArray())
        }
    }

    /**
     * @param items RichResponse items
     * @public
     */
    constructor(items: MutableList<RichResponseItem>) : this() {
        this.items = mutableListOf()
        TODO("IS this needed?")
    }

    /**
     * @param items RichResponse items
     * @public
     */
    constructor(vararg items: RichResponseItem) : this() {
        this.items = mutableListOf()
        this.add(*items)
    }

//    constructor(options?: RichResponseOptions | RichResponseItem[] | RichResponseItem,
//    ...items: RichResponseItem[],
//    )
//    {
//        this.items = []
//        if (!options) {
//            return
//        }
//        if (Array.isArray(options)) {
//            this.add(... options)
//            return
//        }
//        if (isOptions(options)) {
//            if (options.items) {
//                this.add(... options . items)
//            }
//            const { link, suggestions } = options
//            this.linkOutSuggestion = link
//            if (suggestions) {
//                if (Array.isArray(suggestions)) {
//                    this.addSuggestion(... suggestions)
//                } else {
//                    this.addSuggestion(suggestions)
//                }
//            }
//            return
//        }
//        this.add(options, ... items)
//    }

    constructor(options: RichResponseOptions? = null, vararg items: RichResponseItem) : this() {

    }

    constructor(options: MutableList<RichResponseItem>? = null, vararg items: RichResponseItem) : this() {

    }

    constructor(options: RichResponseItem? = null, vararg items: RichResponseItem) : this() {

    }

    fun add(vararg items: String) {
        TODO("Handle adding String to richResponse")
    }

    /**
     * Add a RichResponse item
     * @public
     */
    fun add(vararg items: RichResponseItem): RichResponse {
        if (this.items == null) {
            this.items = mutableListOf()
        }
        items.forEach {
            when (it) {
//            if (item is String) {
//                this.add(SimpleResponse(item))
//                continue
//            }
                is LinkOutSuggestion -> this.linkOutSuggestion = it

                is SimpleResponse -> this.items!!.push { simpleResponse = it }

                is BasicCard ->
                    this.items!!.push { basicCard = it }


                is Table ->
                    this.items!!.push { tableCard = it }


                is BrowseCarousel ->
                    this.items!!.push { carouselBrowse = it }


                is MediaResponse ->
                    this.items!!.push { mediaResponse = it }

                is OrderUpdate -> {
                    this.items!!.push { structuredResponse = GoogleActionsV2StructuredResponse(orderUpdate = it) }
                }
            }
        }
        return this
    }

    fun addSuggestion(vararg suggestions: String): RichResponse {
        if (this.suggestions == null) {
            this.suggestions = mutableListOf()
        }
        suggestions.forEach {
            this.suggestions?.push(GoogleActionsV2UiElementsSuggestion(it))
        }
        return this
    }

    /**
     * Adds a single suggestion or list of suggestions to list of items.
     * @public
     */
    fun addSuggestion(vararg suggestions: Suggestions): RichResponse {
        suggestions.forEach {
            this.suggestions?.push(*it.suggestions.toTypedArray())
        }
        return this
    }

    /**
     * Adds a single suggestion or list of suggestions to list of items.
     * @public
     */
    fun addSuggestion(vararg suggestions: GoogleActionsV2UiElementsSuggestion): RichResponse {
        suggestions.forEach {
            this.suggestions?.push(it)
        }
        return this
    }
}

fun MutableList<GoogleActionsV2RichResponseItem>.add(init: GoogleActionsV2RichResponseItem.() -> Unit) {
    val item = GoogleActionsV2RichResponseItem()
    item.init()
    add(item)
}

/**
 * Image type shown on visual elements.
 * @public
 */
data class Image(override var accessibilityText: String? = null,
                 override var height: Int? = null,
                 override var url: String? = null,
                 override var width: Int? = null) : GoogleActionsV2UiElementsImage, Response() {
    /**
     * @param options Image options
     * @public
     */
    constructor(option: ImageOptions? = null) : this(url = option?.url,
            accessibilityText = option?.alt,
            height = option?.height,
            width = option?.width)
}


/**
 * Suggestions to show with response.
 * @public
 */
data class Suggestions(
        var suggestions: MutableList<GoogleActionsV2UiElementsSuggestion> = mutableListOf()) : Response() {

    /**
     * @param suggestions Texts of the suggestions.
     * @public
     */
    constructor(vararg suggs: String) : this(suggestions = suggs.map { GoogleActionsV2UiElementsSuggestion(title = it) }.toMutableList())


    fun add(vararg suggs: String): Suggestions {
        this.suggestions = suggs.map { GoogleActionsV2UiElementsSuggestion(title = it) }.toMutableList()
        return this
    }
}


/**
 * Class for initializing and constructing MediaObject
 * @public
 */
class MediaObject(override var contentUrl: String? = null,
                  override var description: String? = null,
                  override var icon: GoogleActionsV2UiElementsImage? = null,
                  override var largeImage: GoogleActionsV2UiElementsImage? = null,
                  override var name: String? = null) : GoogleActionsV2MediaObject, Response() {
    /**
     * @param options MediaObject options or just a string for the url
     * @public
     */
    constructor(options: MediaObjectOptions) : this(
            contentUrl = options.url,
            description = options.description,
            icon = options.icon,
            largeImage = options.image,
            name = options.name)

    constructor(options: String) : this(contentUrl = options)
}

abstract class Question(intent: IntentEnum) : GoogleActionsV2ExpectedIntent, Response() {
    override var inputValueData: ApiClientObjectMap<Any>? = null

    override var intent: String? = null

    override var parameterName: String? = null

    //TODO is this needed?

    fun _data(type: InputValueSpec, init: ProtoAny.() -> Unit) {
        val protoAny = ProtoAny()
        protoAny.`@type` = type.value
        protoAny.init()
    }
}

abstract class SoloQuestion(intent: IntentEnum): Question(intent)




/**
 * Simple Response type.
 * @public
 */

data class SimpleResponse(override var displayText: String? = null,
                          override var ssml: String? = null,
                          override var textToSpeech: String? = null) : GoogleActionsV2SimpleResponse, RichResponseItem() {
    /**
     * @param options SimpleResponse options
     * @public
     */
    constructor(options: SimpleResponseOptions) : this(textToSpeech = options.speech,
            displayText = options.text)

    constructor(options: String? = null) : this(textToSpeech = options)
}



class BasicCard(override var buttons: MutableList<GoogleActionsV2UiElementsButton>? = null,
                override var formattedText: String? = null,
                override var image: GoogleActionsV2UiElementsImage? = null,
                override var imageDisplayOptions: GoogleActionsV2UiElementsBasicCardImageDisplayOptions? = null,
                override var subtitle: String? = null,
                override var title: String? = null) : GoogleActionsV2UiElementsBasicCard, RichResponseItem() {

    constructor(options: BasicCardOptions) : this(
            title = options.title,
            subtitle = options.subtitle,
            formattedText = options.text,
            image = options.image,
            buttons = options.buttons,
            imageDisplayOptions = options.display
    )
}



/**
 * Creates a Table card.
 *
 * @example
 * ```javascript
 *
 * // Simple table
 * conv.ask('Simple Response')
 * conv.ask(new Table({
 *   dividers: true,
 *   columns: ['header 1', 'header 2', 'header 3'],
 *   rows: [
 *     ['row 1 item 1', 'row 1 item 2', 'row 1 item 3'],
 *     ['row 2 item 1', 'row 2 item 2', 'row 2 item 3'],
 *   ],
 * }))
 *
 * // All fields
 * conv.ask('Simple Response')
 * conv.ask(new Table({
 *   title: 'Table Title',
 *   subtitle: 'Table Subtitle',
 *   image: new Image({
 *     url: 'https://avatars0.githubusercontent.com/u/23533486',
 *     alt: 'Actions on Google'
 *   }),
 *   columns: [
 *     {
 *       header: 'header 1',
 *       align: 'CENTER',
 *     },
 *     {
 *       header: 'header 2',
 *       align: 'LEADING',
 *     },
 *     {
 *       header: 'header 3',
 *       align: 'TRAILING',
 *     },
 *   ],
 *   rows: [
 *     {
 *       cells: ['row 1 item 1', 'row 1 item 2', 'row 1 item 3'],
 *       dividerAfter: false,
 *     },
 *     {
 *       cells: ['row 2 item 1', 'row 2 item 2', 'row 2 item 3'],
 *       dividerAfter: true,
 *     },
 *     {
 *       cells: ['row 3 item 1', 'row 3 item 2', 'row 3 item 3'],
 *     },
 *   ],
 *   buttons: new Button({
 *     title: 'Button Title',
 *     url: 'https://github.com/actions-on-google'
 *   }),
 * }))
 * ```
 *
 * @public
 */
data class Table(override var buttons: MutableList<GoogleActionsV2UiElementsButton>? = null,
                 override var columnProperties: MutableList<GoogleActionsV2UiElementsTableCardColumnProperties>? = null,
                 override var image: GoogleActionsV2UiElementsImage? = null,
                 override var rows: MutableList<GoogleActionsV2UiElementsTableCardRow>? = null,
                 override var subtitle: String? = null,
                 override var title: String? = null) : GoogleActionsV2UiElementsTableCard, RichResponseItem() {

    constructor(options: TableOptions) : this(
            title = options.title,
            subtitle = options.subtitle,
            image = options.image
//            rows = options.rows.map(row => Array.isArray(row) ? {
//        cells: row.map(text => ({ text })),
//        dividerAfter: options.dividers,
//    } as Api.GoogleActionsV2UiElementsTableCardRow : {
//        cells: row.cells!.map(cell => typeof cell === 'string' ? { text: cell } : cell),
//        dividerAfter: typeof row.dividerAfter === 'undefined' ? options.dividers : row.dividerAfter,
//    } as Api.GoogleActionsV2UiElementsTableCardRow)
//    const { columnProperties, columns, buttons } = options
//    if (columnProperties) {
//        this.columnProperties = toColumnProperties(columnProperties)
//    }
//    if (typeof columns !== 'undefined') {
//        if (!this.columnProperties) {
//            this.columnProperties = []
//        }
//        const properties = typeof columns === 'number' ?
//        new Array<Api.GoogleActionsV2UiElementsTableCardColumnProperties>(columns).fill({}) :
//        toColumnProperties(columns)
//        properties.forEach((v, i) => {
//            if (!this.columnProperties![i]) {
//            this.columnProperties![i] = properties[i]
//        }
//        })
//    }
//    this.buttons = if (options.buttons)
//}
//}
    )
}


/**
 * Class for initializing and constructing MediaResponse.
 * @public
 */
class MediaResponse(): GoogleActionsV2MediaResponse, RichResponseItem() {
    override var mediaObjects: MutableList<GoogleActionsV2MediaObject>?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var mediaType: GoogleActionsV2MediaResponseMediaType?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    /*
    /**
     * @param options MediaResponse options
     * @public
     */
    constructor(options: MediaResponseOptions): this(options = options, objects = null)

    /**
     * @param objects MediaObjects
     * @public
     */
    constructor(vararg objects: MediaObject): this(options = null, objects = *objects)

    constructor(options: MediaResponseOptions? /*|
    MediaObjectString[] | MediaObjectString*/,
                vararg objects: MediaObject?) {
        this.mediaType = GoogleActionsV2MediaResponseMediaType.AUDIO

        if (options != null) {
            this.mediaObjects = mutableListOf()
            return
        }

        if (Array.isArray(options)) {
            this.mediaObjects = options.map(o => toMediaObject(o))
            return
        }

        if (isOptions(options)) {
            this.mediaType = options.type || this.mediaType
            this.mediaObjects = options.objects.map(o => toMediaObject(o))
            return
        }
        this.mediaObjects = [options].concat(objects).map(o => toMediaObject(o))
    }
    */
}


/**
 * Class for initializing and constructing OrderUpdate
 * @public
 */
data class OrderUpdate(override var actionOrderId: String? = null,
                       override var cancellationInfo: GoogleActionsV2OrdersCancellationInfo? = null,
                       override var fulfillmentInfo: GoogleActionsV2OrdersFulfillmentInfo? = null,
                       override var googleOrderId: String? = null,
                       override var inTransitInfo: GoogleActionsV2OrdersInTransitInfo? = null,
                       override var infoExtension: ApiClientObjectMap<Any>? = null,
                       override var lineItemUpdates: ApiClientObjectMap<GoogleActionsV2OrdersLineItemUpdate>? = null,
                       override var orderManagementActions: MutableList<GoogleActionsV2OrdersOrderUpdateAction>? = null,
                       override var orderState: GoogleActionsV2OrdersOrderState? = null,
                       override var receipt: GoogleActionsV2OrdersReceipt? = null,
                       override var rejectionInfo: GoogleActionsV2OrdersRejectionInfo? = null,
                       override var returnInfo: GoogleActionsV2OrdersReturnInfo? = null,
                       override var totalPrice: GoogleActionsV2OrdersPrice? = null,
                       override var updateTime: String? = null,
                       override var userNotification: GoogleActionsV2OrdersOrderUpdateUserNotification? = null) : GoogleActionsV2OrdersOrderUpdate, RichResponseItem() {
    /**
     * @param options The raw {@link GoogleActionsV2OrdersOrderUpdate}
     * @public
     */
    constructor(options: GoogleActionsV2OrdersOrderUpdate? = null) : this(
            actionOrderId = options?.actionOrderId,
            cancellationInfo = options?.cancellationInfo,
            fulfillmentInfo = options?.fulfillmentInfo,
            googleOrderId = options?.googleOrderId,
            inTransitInfo = options?.inTransitInfo,
            infoExtension = options?.infoExtension,
            lineItemUpdates = options?.lineItemUpdates,
            orderManagementActions = options?.orderManagementActions,
            orderState = options?.orderState,
            receipt = options?.receipt,
            rejectionInfo = options?.rejectionInfo,
            returnInfo = options?.returnInfo,
            totalPrice = options?.totalPrice,
            updateTime = options?.updateTime,
            userNotification = options?.userNotification
    )

}


/**
 * Link Out Suggestion.
 * Used in rich response as a suggestion chip which, when selected, links out to external URL.
 * @public
 */
data class LinkOutSuggestion(override var destinationName: String? = null,
                             override var openUrlAction: GoogleActionsV2UiElementsOpenUrlAction? = null,
                             override var url: String? = null) : GoogleActionsV2UiElementsLinkOutSuggestion, RichResponseItem() {
    /**
     * @param options LinkOutSuggestion options
     * @public
     */
    constructor(options: LinkOutSuggestionOptions): this(
            destinationName = options.name,
            url = options.url)
}


data class GoogleActionsV2RichResponseItem(
        /**
         * A basic card.
         */
        var basicCard: GoogleActionsV2UiElementsBasicCard? = null,
        /**
         * Carousel browse card.
         */
        var carouselBrowse: GoogleActionsV2UiElementsCarouselBrowse? = null,
        /**
         * Response indicating a set of media to be played.
         */
        var mediaResponse: GoogleActionsV2MediaResponse? = null,
        /**
         * Voice and text-only response.
         */
        var simpleResponse: GoogleActionsV2SimpleResponse? = null,
        /**
         * Structured payload to be processed by Google.
         */
        var structuredResponse: GoogleActionsV2StructuredResponse? = null,
        /**
         * Table card.
         */
        var tableCard: GoogleActionsV2UiElementsTableCard? = null
): RichResponseItem()


/**
 * Class for initializing and constructing Browse Carousel.
 * @public
 */
class BrowseCarousel(override var imageDisplayOptions: GoogleActionsV2UiElementsCarouselBrowseImageDisplayOptions? = null,
                     override var items: MutableList<GoogleActionsV2UiElementsCarouselBrowseItem>? = null) : GoogleActionsV2UiElementsCarouselBrowse, RichResponseItem() {
    /*
    /**
     * @param options BrowseCarousel options
     * @public
     */
    constructor(options: BrowseCarouselOptions)
    /**
     * @param items BrowseCarousel items
     * @public
     */
    constructor(vararg items: GoogleActionsV2UiElementsCarouselBrowseItem)
    /**
     * @param items BrowseCarousel items
     * @public
     */
    constructor(
            options?: BrowseCarouselOptions |
            GoogleActionsV2UiElementsCarouselBrowseItem[] |
    GoogleActionsV2UiElementsCarouselBrowseItem,
    var arg items: GoogleActionsV2UiElementsCarouselBrowseItem): this() {
        if (!options) {
            this.items = []
            return
        }
        if (Array.isArray(options)) {
            this.items = options
            return
        }
        if (isOptions(options)) {
            this.imageDisplayOptions = options.display
            this.items = options.items
            return
        }
        this.items = [options].concat(items)
    }
    */
}