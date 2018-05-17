package actions

import actions.service.actionssdk.api.*
import actions.service.actionssdk.conversation.SurfaceCapability

const val name = "actions-on-google"


///** @hidden */
//const values = <T>(o: { [key: string]: T }) => Object.keys(o).map(k => o[k])


/**
 * All possible fields are here, though only one spec used at any given time.
 * To create a ProtoAny use _data(Spec.Type) { }
 */
data class ProtoAny(var `@type`: String? = null,
                    var dialogSpec: DialogSpecData? = null,
                    var optContext: String? = null,
                    var permissions: MutableList<GoogleActionsV2PermissionValueSpecPermissions>? = null,
                    var updatePermissionValueSpec: GoogleActionsV2UpdatePermissionValueSpec? = null,
                    var capabilities: MutableList<SurfaceCapability>? = null,
                    var context: String? = null,
                    var notificationTitle: String? = null,
                    var arguments: MutableList<GoogleActionsV2Argument>? = null,
                    var openUrlAction: GoogleActionsV2UiElementsOpenUrlAction? = null,
                    var triggerContext: GoogleActionsV2TriggerContext? = null,
                    var addressOptions: GoogleActionsV2DeliveryAddressValueSpecAddressOptions? = null,
                    var checkResult: MutableList<GoogleActionsV2TransactionRequirementsCheckResult>? = null,
                    var deliveryAddress: GoogleActionsV2Location? = null,
                    var order: GoogleActionsV2OrdersOrder? = null,
                    var userDecision: GoogleActionsV2TransactionDecisionValueUserDecision? = null,
                    var orderOptions: GoogleActionsV2OrdersOrderOptions? = null,
                    var paymentOptions: GoogleActionsV2OrdersPaymentOptions? = null) {

    fun dialogSpec(init: DialogSpecData.() -> Unit) {

    }
}

data class DialogSpecData(var `@type`: String? = null,
                          var permissionContext: String? = null,
                          var requestPrompt: String? = null,
                          var destinationName: String? = null,
                          var requestLinkReason: String? = null,
                          var requestDatetimeText: String? = null,
                          var requestDateText: String? = null,
                          var requestTimeText: String? = null,
                          var requestConfirmationText: String? = null)


/** @hidden */
class ApiClientObjectMap<TValue> : Map<String, TValue> by mutableMapOf<String, TValue>()
