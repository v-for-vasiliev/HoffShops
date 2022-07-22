package ru.vasiliev.hoffshops.data.response

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer

@Serializable
data class ShopsResponse(
    @SerialName("data")
    val data: Data
)

@Serializable
data class Data(
    @SerialName("shops_by_cities")
    val shopsByCities: List<ShopsByCitiesItem>
)

@Serializable
data class SubwayItem(
    val color: String,
    val name: String
)

@Serializable
data class ShopsByCitiesItem(
    val name: String,
    val shops: List<ShopsItem>,
    val id: String,
)

@Serializable
data class ShopsItem(
    @SerialName("pickup_extra_time")
    val pickupExtraTime: String? = null,
    @SerialName("unload_to_preorder")
    val unloadToPreorder: String? = null,
    @SerialName("city")
    val city: String? = null,
    @SerialName("axapta_phone")
    val axaptaPhone: String? = null,
    @SerialName("link")
    val link: String? = null,
    @SerialName("cs_time")
    val csTime: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("axapta_service")
    val axaptaService: String? = null,
    @SerialName("discount_store")
    val discountStore: String? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("alert_prepayment")
    val alertPrepayment: String? = null,
    @SerialName("moving_from_cs")
    val movingFromCs: String? = null,
    @SerialName("map")
    val map: List<String>? = null,
    @SerialName("axapta")
    val axapta: String? = null,
    @SerialName("address")
    val address: String? = null,
    @SerialName("pickup_point")
    val pickupPoint: String? = null,
    @SerialName("not_available")
    val notAvailable: String? = null,
    @SerialName("sort")
    val sort: String? = null,
    @SerialName("shop_name")
    val shopName: String? = null,
    @SerialName("point_type")
    val pointType: List<String>? = null,
    @SerialName("schedule")
    val schedule: String? = null,
    @Serializable(with = SubwayListSerializer::class)
    val subway: List<SubwayItem>? = null,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("xml_id")
    val xmlId: String? = null,
    @SerialName("mark")
    val mark: String? = null,
    @SerialName("delivery_price")
    val deliveryPrice: String? = null,
    @SerialName("vacancyUrl")
    val vacancyUrl: String? = null,
    @SerialName("IS_COMBINED_DISCOUNT")
    val isCombinedDiscount: String? = null
)

object SubwayListSerializer :
    JsonTransformingSerializer<List<SubwayItem>>(ListSerializer(SubwayItem.serializer())) {
    // If subway value is incorrect (empty string instead of array) then treat it as empty subway list
    override fun transformDeserialize(element: JsonElement): JsonElement =
        if (element is JsonArray) element else JsonArray(emptyList())
}
